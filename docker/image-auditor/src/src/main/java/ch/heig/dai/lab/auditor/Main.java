package ch.heig.dai.lab.auditor;

import ch.heig.dai.lab.auditor.Model.Musician;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
/*
static class MusicianDeserializer implements JsonDeserializer<Musician> {
    /**
     * Deserialize a musician from a JSON element.
     *
     * @param jsonElement                The JSON element.
     * @param type                       The type of the object.
     * @param jsonDeserializationContext The context.
     * @return A musician record.
     * @throws JsonParseException If the JSON is invalid.

    @Override
    public Musician deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        final UUID uuid = UUID.fromString(jsonObject.get("uuid").getAsString());
        final String sound = jsonObject.get("sound").getAsString();
        final long lastActivity = System.currentTimeMillis();

        return new Musician(uuid, sound, lastActivity);
    }
}
*/
public class Main {

    final static String IPADDRESS = "239.255.22.5";
    final static int UDP_PORT = 9904;
    final static int TCP_PORT = 2205;
    final static String INTERFACE_NAME = "eth0";
    final static long TIMEOUT_MILI = 5000;
    public static Set<Musician> orchestra = ConcurrentHashMap.newKeySet();

    public static void udpMulticastTask(){
        new Thread(()->{
            System.out.println("Thread started");
            try(MulticastSocket socket = new MulticastSocket(UDP_PORT)){
                InetSocketAddress group_address = new InetSocketAddress(IPADDRESS, UDP_PORT);
                NetworkInterface netif = NetworkInterface.getByName(INTERFACE_NAME);
                socket.joinGroup(group_address, netif);

                while (!Thread.interrupted()){
                    System.out.println(System.currentTimeMillis());
                    byte[] buffer = new byte[1024];
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    socket.receive(packet);
                    String message = new String(packet.getData(), 0, packet.getLength(), StandardCharsets.UTF_8);
                    Musician musician = new GsonBuilder().create().fromJson(message, Musician.class);
                    musician.setActivity();
                    System.out.println("New musician with mili = " + musician.lastActivity());
                    //Check if the musician was already in the set otherwise update it
                    if(!orchestra.add(musician)){
                        orchestra.remove(musician);
                        orchestra.add(musician);
                    }else {
                        System.out.println("Auditor : new musician found " + musician.lastActivity());
                    }
                }
            }catch (IOException e){
                System.out.println(e.getMessage());
            }
        }).start();
    }

    public static void tcpServerTask(){

        new Thread(()->{
            try(ServerSocket serverSocket = new ServerSocket(TCP_PORT)){
                while (!Thread.interrupted()){
                    try(Socket socket = serverSocket.accept();
                        var out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),StandardCharsets.UTF_8))){
                        orchestra.forEach((musician -> System.out.println(musician.lastActivity())));
                        orchestra.removeIf(musician-> System.currentTimeMillis() - musician.lastActivity() >= TIMEOUT_MILI);
                        String response = new GsonBuilder().create().toJson(orchestra);
                        System.out.println("new connection");
                        out.write(response);
                        out.flush();
                    }catch (IOException e){
                        System.out.println(e.getMessage());
                    }
                }
            }catch (IOException e){
                System.out.println(e.getMessage());
            }
        }).start();

    }
    public static void main(String[] args) {
        udpMulticastTask();
        tcpServerTask();
    }
}



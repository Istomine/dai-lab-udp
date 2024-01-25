package ch.heig.dai.lab.musician;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import com.google.gson.Gson;
import java.util.concurrent.TimeUnit;

public class Main {

    final static int PORT = 9904;
    final static String IPADDR = "239.255.22.5";

    final static UUID uuid = UUID.randomUUID();

    final static Map<String,String> instruments = new HashMap<>();


    private static void initMap(){
        instruments.put("piano","ti-ta-ti");
        instruments.put("trumpet","pouet");
        instruments.put("flute","trulu");
        instruments.put("violin","gzi-gzi");
        instruments.put("drum","boum-boum");
    }

    private record Instr(String instrument,UUID uuid){}


    public static void main(String[] args) {

        initMap();

        try(DatagramSocket socket = new DatagramSocket()){

            Instr i = new Instr(instruments.get(args[0]),uuid);

            byte[] payload = new Gson().toJson(i).getBytes(StandardCharsets.UTF_8);
            var dest_address = new InetSocketAddress(IPADDR,PORT);
            var packet = new DatagramPacket(payload,payload.length,dest_address);


            while(true){
                socket.send(packet);
                TimeUnit.SECONDS.sleep(3);
            }



        }catch (IOException ex){
            System.out.println("Error : " + ex.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

}

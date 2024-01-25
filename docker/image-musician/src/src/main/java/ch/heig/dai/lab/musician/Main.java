package ch.heig.dai.lab.musician;

import java.io.IOException;
import java.net.DatagramSocket;
import java.util.UUID;

public class Main {

    final static int PORT = 9904;
    final static String IPADDR = "239.255.22.5";




    public static void main(String[] args) {

        /*

        try(DatagramSocket socket = new DatagramSocket()){


            byte[] payload ;




        }catch (IOException ex){
            System.out.println("Error : " + ex.getMessage());
        }


         */


    }
}

abstract class Instrument{
    String sound;
    UUID uuid;

    Instrument(String sound){
        this.sound = sound;
        uuid = UUID.randomUUID();
    }

    public String getSound() {
        return sound;
    }

    public UUID getUuid() {
        return uuid;
    }
}

class Piano extends Instrument{
    Piano(){
        super("ti-ta-ti");
    }
}

class Flute extends Instrument{
    Flute(){
        super("trulu");
    }
}

class Violin extends Instrument{
    Violin(){
        super("gzi-gzi");
    }
}

class Drum extends Instrument{
    Drum(){
        super("boum-boum");
    }
}

class Trumpet extends Instrument{
    Trumpet(){
        super("pouet");
    }
}

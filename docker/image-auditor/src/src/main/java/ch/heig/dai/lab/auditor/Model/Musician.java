package ch.heig.dai.lab.auditor.Model;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

enum Instrument{
    piano, trumpet, flute, violin, drum
}

public class Musician {
    private static final HashMap<String, Instrument> instrumentsSound = new HashMap<>(){{
        put("ti-ta-ti", Instrument.piano);
        put("pouet", Instrument.trumpet);
        put("trulu", Instrument.flute);
        put("gzi-gzi", Instrument.violin);
        put("boum-boum", Instrument.drum);
    }};


    private Instrument instrument;

    private UUID uuid;

    private long lastActivity;


    public Musician(String sound,UUID uuid,long lastActivity){
        this.instrument = instrumentsSound.get(sound);
        this.uuid = uuid;
        this.lastActivity =lastActivity;
    }

    public long getLastActivity() {
        return lastActivity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, instrument);
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Musician)){return  false;}
        return ((Musician)o).uuid == uuid && ((Musician)o).instrument == instrument ;
    }
}

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

    private UUID uuid;
    private Instrument instrument;
    private long lastActivity;

    public Musician(String sound,UUID uuid){
        this.uuid = uuid;
        this.instrument = instrumentsSound.get(sound);
        this.lastActivity = System.currentTimeMillis();
    }

    public void setActivity(){
        lastActivity = System.currentTimeMillis();
    }

    public long lastActivity(){return lastActivity;}

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    /**
     * Check if two musicians are equal.
     *
     * @param other the reference object with which to compare.
     * @return true if this object is the same as the obj argument; false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (other == null) {
            return false;
        } else if (!(other instanceof Musician)) {
            return false;
        }
        Musician musician = (Musician) other;
        return musician.uuid.equals(this.uuid);
    }
}

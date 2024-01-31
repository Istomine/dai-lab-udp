package ch.heig.dai.lab.auditor.Model;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

enum Instrument{
    piano, trumpet, flute, violin, drum
}

public record Musician(UUID uuid, Instrument instrument, long lastActivity) {
    private static final HashMap<String, Instrument> instrumentsSound = new HashMap<>(){{
        put("ti-ta-ti", Instrument.piano);
        put("pouetflute", Instrument.trumpet);
        put("trulu", Instrument.flute);
        put("gzi-gzi", Instrument.violin);
        put("boum-boum", Instrument.drum);
    }};

    public Musician(UUID uuid, String sound, long lastActivity){
        this(uuid, instrumentsSound.get(sound), lastActivity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, instrument);
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Musician)){return  false;}
        return this.hashCode() == o.hashCode();
    }
}

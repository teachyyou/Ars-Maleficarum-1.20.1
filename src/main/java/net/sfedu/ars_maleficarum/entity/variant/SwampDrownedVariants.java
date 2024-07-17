package net.sfedu.ars_maleficarum.entity.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum SwampDrownedVariants {
    PEACEFUL(0),
    EVIL(1);

    private static final SwampDrownedVariants[] BY_ID = Arrays.stream(values()).sorted(Comparator.
            comparingInt(SwampDrownedVariants::getId)).toArray(SwampDrownedVariants[]::new);
    private final int id;

    SwampDrownedVariants(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static SwampDrownedVariants byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}

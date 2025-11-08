package com.mdotm.petapi.pet.common.utils;

import com.mdotm.petapi.pet.common.enums.Species;

import java.util.Arrays;

public final class Helpers {
    public static String getSpeciesValuesAsRegex(){
        return String.join("|",
                Arrays.stream(Species.values()).map(Enum::name).toArray(String[]::new));
    }
}

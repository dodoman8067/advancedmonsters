package kro.dodoworld.advancedmonsters.core;

import java.util.concurrent.ThreadLocalRandom;

public class RandomNumberGenerator {

    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();
    public static boolean chance (double chance){
        if(chance <= 0 || chance > 100) throw new IllegalArgumentException("Chance must be grater then 0 and smaller then 100");
        return RANDOM.nextDouble(0, 101) <= chance;
    }
}

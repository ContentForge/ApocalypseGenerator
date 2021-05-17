package ru.dragonestia.generator.factor;

import java.util.Random;

public enum RadioactiveLevel {

    ZERO(0.0),
    LOW(5 / 10000.0),
    NORMAL(10 / 1000.0),
    HIGH(50 / 1000.0),
    CRITICAL(1);

    public final double dose;
    private static final Random random = new Random();

    RadioactiveLevel(double dose){
        this.dose = dose;
    }

    public double getGroundDose(){
        return dose + (random.nextFloat() - 0.5) / 5.0;
    }

}

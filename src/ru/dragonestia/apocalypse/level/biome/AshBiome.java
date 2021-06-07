package ru.dragonestia.apocalypse.level.biome;

import ru.dragonestia.apocalypse.factor.RadioactiveLevel;
import ru.dragonestia.apocalypse.level.ApocalypseGenerator;

import java.util.Random;

public class AshBiome extends ApocalypseBiome {

    @Override
    public String getName() {
        return "Ash";
    }

    @Override
    public int getId() {
        return ApocalypseGenerator.ASH_BIOME;
    }

    @Override
    public boolean isNegativeEffectBySun() {
        return false;
    }

    @Override
    public RadioactiveLevel getRadioactiveLevel() {
        return RadioactiveLevel.HIGH;
    }

    @Override
    public double getNoiseInterpolate(Random random, double noise) {
        return noise * 1.3;
    }

}

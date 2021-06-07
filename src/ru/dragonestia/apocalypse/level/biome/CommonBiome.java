package ru.dragonestia.apocalypse.level.biome;

import ru.dragonestia.apocalypse.factor.RadioactiveLevel;
import ru.dragonestia.apocalypse.level.ApocalypseGenerator;

import java.util.Random;

public class CommonBiome extends ApocalypseBiome {

    @Override
    public String getName() {
        return "apocalypse";
    }

    @Override
    public int getId() {
        return ApocalypseGenerator.COMMON_BIOME;
    }

    @Override
    public boolean isNegativeEffectBySun() {
        return true;
    }

    @Override
    public RadioactiveLevel getRadioactiveLevel() {
        return RadioactiveLevel.LOW;
    }

    @Override
    public int getHeightInterpolate(Random random, int y) {
        if(random.nextFloat() > 0.3) y = (y + 60 + 60) / 3;
        else y = (y + 60) / 2;
        return y;
    }

}

package ru.dragonestia.generator.level.biome;

import cn.nukkit.Player;
import ru.dragonestia.generator.factor.RadioactiveLevel;
import ru.dragonestia.generator.level.ApocalypseGenerator;

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
    public void giveNegativeEffect(Player player, int level) {

    }

    @Override
    public boolean hasProtection(Player player) {
        return false;
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

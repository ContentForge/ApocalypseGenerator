package ru.dragonestia.generator.level.biome;

import cn.nukkit.Player;
import cn.nukkit.level.biome.Biome;
import ru.dragonestia.generator.factor.RadioactiveLevel;

import java.util.Random;

public abstract class ApocalypseBiome extends Biome {

    public boolean isNegativeEffectBySun(){
        return true;
    }

    public void giveNegativeEffect(Player player, int level){

    }

    public boolean hasProtection(Player player){
        return false;
    }

    public abstract RadioactiveLevel getRadioactiveLevel();

    public int getHeightInterpolate(Random random, int y){
        return y;
    }

    public double getNoiseInterpolate(Random random, double noise){
        return noise;
    }

}

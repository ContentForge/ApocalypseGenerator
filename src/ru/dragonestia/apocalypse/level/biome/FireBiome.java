package ru.dragonestia.apocalypse.level.biome;

import cn.nukkit.Player;
import ru.dragonestia.apocalypse.factor.RadioactiveLevel;
import ru.dragonestia.apocalypse.level.ApocalypseGenerator;

public class FireBiome extends ApocalypseBiome {

    @Override
    public String getName() {
        return "Fire";
    }

    @Override
    public int getId() {
        return ApocalypseGenerator.FIRE_BIOME;
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
        return RadioactiveLevel.NORMAL;
    }

}

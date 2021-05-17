package ru.dragonestia.generator.level.biome;

import cn.nukkit.Player;
import cn.nukkit.level.biome.Biome;
import ru.dragonestia.generator.factor.RadioactiveLevel;
import ru.dragonestia.generator.level.ApocalypseGenerator;

public class AshBiome extends Biome implements ApocalypseBiome {

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
    public void giveNegativeEffect(Player player, int level) {

    }

    @Override
    public boolean hasProtection(Player player) {
        return false;
    }

    @Override
    public RadioactiveLevel getRadioactiveLevel() {
        return RadioactiveLevel.HIGH;
    }

}

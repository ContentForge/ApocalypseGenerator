package ru.dragonestia.generator.level.biome;

import cn.nukkit.Player;
import ru.dragonestia.generator.factor.RadioactiveLevel;

public interface ApocalypseBiome {

    boolean isNegativeEffectBySun();
    void giveNegativeEffect(Player player, int level);
    boolean hasProtection(Player player);
    RadioactiveLevel getRadioactiveLevel();

}

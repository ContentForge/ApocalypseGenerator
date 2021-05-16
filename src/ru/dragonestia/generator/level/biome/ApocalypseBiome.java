package ru.dragonestia.generator.level.biome;

import cn.nukkit.Player;

public interface ApocalypseBiome {

    boolean isNegativeEffectBySun();
    void giveNegativeEffect(Player player, int level);
    boolean hasProtection(Player player);

}

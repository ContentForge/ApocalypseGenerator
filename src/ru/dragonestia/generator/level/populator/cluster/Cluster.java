package ru.dragonestia.generator.level.populator.cluster;

import cn.nukkit.item.Item;
import ru.dragonestia.generator.level.biome.ApocalypseBiome;

public interface Cluster {

    int getBlockId();
    Item[] getDrop(ApocalypseBiome biome, int x, int y, int z);
    int getClustersCountByBiome(ApocalypseBiome biome);
    int getMaxLevel();
    int getMinLevel();

}

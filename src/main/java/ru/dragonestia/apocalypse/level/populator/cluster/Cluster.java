package ru.dragonestia.apocalypse.level.populator.cluster;

import cn.nukkit.item.Item;
import ru.dragonestia.apocalypse.level.biome.ApocalypseBiome;

public interface Cluster {

    int getBlockId();
    Item[] getDrop(ApocalypseBiome biome, int x, int y, int z);
    int getClustersCountByBiome(ApocalypseBiome biome);
    int getMaxLevel();
    int getMinLevel();

}

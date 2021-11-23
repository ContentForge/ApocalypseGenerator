package ru.dragonestia.apocalypse.level.populator.cluster;

import cn.nukkit.item.Item;
import ru.dragonestia.apocalypse.level.biome.ApocalypseBiome;

public class RedstoneCluster implements Cluster {

    @Override
    public int getBlockId() {
        return Item.GLOWING_REDSTONE_ORE;
    }

    @Override
    public Item[] getDrop(ApocalypseBiome biome, int x, int y, int z) {
        return new Item[]{
                Item.get(Item.REDSTONE, 0, 2)
        };
    }

    @Override
    public int getClustersCountByBiome(ApocalypseBiome biome) {
        return 10;
    }

    @Override
    public int getMaxLevel() {
        return 35;
    }

    @Override
    public int getMinLevel() {
        return 10;
    }

}

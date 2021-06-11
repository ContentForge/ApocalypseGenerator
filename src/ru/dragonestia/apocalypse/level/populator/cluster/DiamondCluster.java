package ru.dragonestia.apocalypse.level.populator.cluster;

import cn.nukkit.item.Item;
import ru.dragonestia.apocalypse.level.biome.ApocalypseBiome;
import ru.dragonestia.apocalypse.level.biome.AshBiome;

public class DiamondCluster implements Cluster {

    @Override
    public int getBlockId() {
        return Item.DIAMOND_ORE;
    }

    @Override
    public Item[] getDrop(ApocalypseBiome biome, int x, int y, int z) {
        return new Item[]{
                Item.get(Item.DIAMOND)
        };
    }

    @Override
    public int getClustersCountByBiome(ApocalypseBiome biome) {
        return biome instanceof AshBiome? 10 : 0;
    }

    @Override
    public int getMaxLevel() {
        return 60;
    }

    @Override
    public int getMinLevel() {
        return 40;
    }

}

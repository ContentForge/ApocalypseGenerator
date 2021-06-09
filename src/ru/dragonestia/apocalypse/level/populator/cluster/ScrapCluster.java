package ru.dragonestia.apocalypse.level.populator.cluster;

import cn.nukkit.item.Item;
import ru.dragonestia.apocalypse.item.ApocalypseID;
import ru.dragonestia.apocalypse.level.biome.ApocalypseBiome;

public class ScrapCluster implements Cluster {

    @Override
    public int getBlockId() {
        return Item.IRON_BARS;
    }

    @Override
    public Item[] getDrop(ApocalypseBiome biome, int x, int y, int z) {
        return new Item[]{
                Item.get(ApocalypseID.SCRAP)
        };
    }

    @Override
    public int getClustersCountByBiome(ApocalypseBiome biome) {
        return 0;
    }

    @Override
    public int getMaxLevel() {
        return 0;
    }

    @Override
    public int getMinLevel() {
        return 0;
    }

}

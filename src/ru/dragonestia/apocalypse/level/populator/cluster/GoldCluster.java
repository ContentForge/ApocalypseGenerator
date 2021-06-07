package ru.dragonestia.apocalypse.level.populator.cluster;

import cn.nukkit.item.Item;
import ru.dragonestia.apocalypse.item.ApocalypseID;
import ru.dragonestia.apocalypse.level.ApocalypseGenerator;
import ru.dragonestia.apocalypse.level.biome.ApocalypseBiome;

import java.util.Random;

public class GoldCluster implements Cluster {

    private final Random random;

    public GoldCluster(Random random){
        this.random = random;
    }

    @Override
    public int getBlockId() {
        return Item.IRON_ORE;
    }

    @Override
    public Item[] getDrop(ApocalypseBiome biome, int x, int y, int z) {
        return new Item[]{
                Item.get(random.nextBoolean()? ApocalypseID.COPPER_CLUSTER : ApocalypseID.SULFUR_CLUSTER)
        };
    }

    @Override
    public int getClustersCountByBiome(ApocalypseBiome biome) {
        switch (biome.getId()){
            case ApocalypseGenerator.FIRE_BIOME:
            case ApocalypseGenerator.ASH_BIOME:
                return 25;
        }
        return 0;
    }

    @Override
    public int getMaxLevel() {
        return 55;
    }

    @Override
    public int getMinLevel() {
        return 10;
    }

}

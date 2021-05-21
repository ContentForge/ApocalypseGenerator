package ru.dragonestia.generator.level.populator;

import cn.nukkit.level.ChunkManager;
import cn.nukkit.level.biome.Biome;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.level.generator.populator.type.Populator;
import cn.nukkit.math.NukkitRandom;
import ru.dragonestia.generator.level.biome.ApocalypseBiome;
import ru.dragonestia.generator.level.populator.cluster.Cluster;

import java.util.Random;

public class Ores extends Populator {

    private final Cluster[] clusters;
    private final Random random;

    public Ores(Random random, Cluster[] clusters){
        this.clusters = clusters;
        this.random = random;
    }

    @Override
    public void populate(ChunkManager chunkManager, int chunkX, int chunkZ, NukkitRandom nukkitRandom, FullChunk chunk) {
        Biome biome = Biome.getBiome(chunk.getBiomeId(5, 5));
        if(!(biome instanceof ApocalypseBiome)) return;

        for(Cluster cluster: clusters){
            for(int i = 0, n = cluster.getClustersCountByBiome((ApocalypseBiome) biome); i < n; i++){
                int y = cluster.getMinLevel() + random.nextInt(cluster.getMaxLevel());
                chunk.setBlockId(random.nextInt(16), y, random.nextInt(16), cluster.getBlockId());
            }
        }
    }

}

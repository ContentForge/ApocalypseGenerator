package ru.dragonestia.apocalypse.level.populator;

import cn.nukkit.level.ChunkManager;
import cn.nukkit.level.biome.Biome;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.level.generator.populator.type.Populator;
import cn.nukkit.math.NukkitRandom;
import ru.dragonestia.apocalypse.level.ApocalypseGenerator;
import ru.dragonestia.apocalypse.level.biome.ApocalypseBiome;
import ru.dragonestia.apocalypse.level.populator.cluster.Cluster;

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
                int x = random.nextInt(16), z = random.nextInt(16);
                int yMin = cluster.getMinLevel(), yMax = cluster.getMaxLevel(), delta = yMax - yMin;
                int y = yMin + (random.nextInt(Math.min(delta, ApocalypseGenerator.getHeightBlockAt(x, z, chunk))));
                chunk.setBlockId(x, y, z, cluster.getBlockId());
            }
        }
    }

}

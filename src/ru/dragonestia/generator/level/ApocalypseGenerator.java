package ru.dragonestia.generator.level;

import cn.nukkit.item.Item;
import cn.nukkit.level.ChunkManager;
import cn.nukkit.level.biome.Biome;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.level.generator.Generator;
import cn.nukkit.level.generator.noise.nukkit.d.PerlinD;
import cn.nukkit.level.generator.noise.nukkit.d.SimplexD;
import cn.nukkit.level.generator.populator.type.Populator;
import cn.nukkit.math.NukkitRandom;
import cn.nukkit.math.Vector3;
import ru.dragonestia.generator.level.biome.AshBiome;
import ru.dragonestia.generator.level.biome.CommonBiome;
import ru.dragonestia.generator.level.populator.BedrockGradient;
import ru.dragonestia.generator.level.populator.Ground;
import ru.dragonestia.generator.level.populator.LavaPit;
import ru.dragonestia.generator.util.Pair;
import ru.dragonestia.generator.util.random.Fortune;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ApocalypseGenerator extends Generator {

    public static final int ASH_BIOME = 181;
    public static final int FIRE_BIOME = 179;
    public static final int BLUE_BIOME = 180;
    public static final int COMMON_BIOME = 8;

    private final Map<String, Object> settings;
    private ChunkManager chunkManager;
    private NukkitRandom nukkitRandom;
    private SimplexD simplex;
    private PerlinD perlin;
    private int[] biomeBlocks;
    private Random random;
    private Populator[] populators;
    private Fortune<Biome> biomes;
    private int biomesWeight;

    public ApocalypseGenerator(){
        settings = new HashMap<>();
    }

    public ApocalypseGenerator(Map<String, Object> settings){
        this.settings = settings;
    }

    @Override
    public int getId() {
        return 1;
    }

    @Override
    public void init(ChunkManager chunkManager, NukkitRandom nukkitRandom) {
        this.chunkManager = chunkManager;
        this.nukkitRandom = nukkitRandom;
        simplex = new SimplexD(nukkitRandom, 1F, 1F);
        perlin = new PerlinD(nukkitRandom, 1F, 1F);
        biomeBlocks = new int[]{0, 14, 4, 5, 9};
        random = new Random();
        populators = new Populator[]{
                new Ground(nukkitRandom, random),
                new LavaPit(simplex, random),
                new BedrockGradient(),
        };
        biomes = new Fortune<>(random);
        biomes.items.addAll(Arrays.asList(
                new Pair<>(Biome.getBiome(COMMON_BIOME), 9),
                new Pair<>(Biome.getBiome(ASH_BIOME), 5),
                new Pair<>(Biome.getBiome(FIRE_BIOME), 7)
        ));
        biomesWeight = biomes.getAllWeight() * 2;

        nukkitRandom.setSeed("ЕБАНУТЬСЯ".hashCode());
    }

    @Override
    public void generateChunk(int chunkX, int chunkZ) {
        FullChunk chunk = chunkManager.getChunk(chunkX, chunkZ);

        for(int x = 0; x < 16; x++){
            for(int z = 0; z < 16; z++){
                Biome biome = generateBiome(x + chunkX*16, z + chunkZ*16);
                chunk.setBiome(x, z, biome);

                double h = simplex.getNoise2D((x + chunkX*16) / 100.0, (z + chunkZ*16) / 100.0);
                int noise = 0;
                while (random.nextFloat() < (biome instanceof AshBiome? 0.35f : 0.25f) && noise < 100) noise++;
                int yMax = (int)(h * 10) + 60;
                if(biome instanceof CommonBiome){
                    if(random.nextFloat() > 0.3) yMax = (yMax + 60 + 60) / 3;
                    else yMax = (yMax + 60) / 2;
                }
                yMax += noise;

                for(int y = 0; y < yMax; y++){
                    int blockId;
                    switch (random.nextInt(4)){
                        case 0:
                            blockId = Item.GRAVEL;
                            break;
                        case 1:
                            blockId = Item.COBBLE;
                            break;
                        default:
                            blockId = 1;
                    }
                    chunk.setBlock(x, y, z, blockId);
                }
            }
        }
    }

    @Override
    public void populateChunk(int chunkX, int chunkZ) {
        FullChunk chunk = chunkManager.getChunk(chunkX, chunkZ);
        for(Populator populator: populators) populator.populate(chunkManager, chunkX, chunkZ, nukkitRandom, chunk);
    }

    @Override
    public Map<String, Object> getSettings() {
        return settings;
    }

    @Override
    public String getName() {
        return "apocalypse";
    }

    @Override
    public Vector3 getSpawn() {
        return new Vector3(0, chunkManager.getChunk(0, 0).getHighestBlockAt(0, 0), 0);
    }

    @Override
    public ChunkManager getChunkManager() {
        return chunkManager;
    }

    private Biome generateBiome(int x, int z){
        double noise = (simplex.getNoise2D(x / 2000.0, z / 2000.0) + 1) / 2;
        return biomes.roll((int)(biomesWeight * noise));
    }

}

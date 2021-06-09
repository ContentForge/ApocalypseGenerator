package ru.dragonestia.apocalypse.level;

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
import ru.dragonestia.apocalypse.Apocalypse;
import ru.dragonestia.apocalypse.level.biome.ApocalypseBiome;
import ru.dragonestia.apocalypse.level.biome.AshBiome;
import ru.dragonestia.apocalypse.level.biome.CommonBiome;
import ru.dragonestia.apocalypse.level.populator.*;
import ru.dragonestia.apocalypse.util.Pair;
import ru.dragonestia.apocalypse.util.random.Fortune;

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
    private SimplexD cityNoise;

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
        cityNoise = new SimplexD(nukkitRandom, 1f, 1);
        populators = new Populator[]{
                new Ores(random, Apocalypse.getInstance().clusters),
                new Ground(nukkitRandom, random),
                new LavaPit(simplex, new SimplexD(nukkitRandom, 1F, 1F), random),
                new UndergroundLava(random),
                new BedrockGradient(),
                new SectionPopulator(random, chunkManager, cityNoise),
                new MushroomCave(random),
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
                int globalX = x + chunkX*16, globalZ = z + chunkZ*16;
                ApocalypseBiome biome = (ApocalypseBiome) generateBiome(x + chunkX*16, z + chunkZ*16);
                chunk.setBiomeId(x, z, (biome instanceof AshBiome && globalX > 0 && globalX < 1000 && globalZ > 0 && globalZ < 1000)? FIRE_BIOME : biome.getId() );

                double h = simplex.getNoise2D(globalX / 100.0, globalZ / 100.0);
                h = biome.getNoiseInterpolate(random, h);
                int noise = 0;
                while (random.nextFloat() < (biome instanceof AshBiome? 0.35f : (biome instanceof CommonBiome? 0.10f : 0.25f)) && noise < 100) noise++;
                int yMax = (int)(h * 10) + 60;
                yMax = biome.getHeightInterpolate(random, yMax) + noise;

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

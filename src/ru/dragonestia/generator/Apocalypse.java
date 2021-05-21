package ru.dragonestia.generator;

import cn.nukkit.level.biome.Biome;
import cn.nukkit.level.generator.Generator;
import cn.nukkit.plugin.PluginBase;
import ru.dragonestia.generator.level.ApocalypseGenerator;
import ru.dragonestia.generator.level.biome.CommonBiome;
import ru.dragonestia.generator.level.biome.AshBiome;
import ru.dragonestia.generator.level.biome.FireBiome;
import ru.dragonestia.generator.level.populator.cluster.Cluster;
import ru.dragonestia.generator.listener.MainListener;

public class Apocalypse extends PluginBase {

    private static Apocalypse instance;
    public Cluster[] clusters;

    @Override
    public void onLoad(){
        instance = this;

        Biome.biomes[ApocalypseGenerator.ASH_BIOME] = new AshBiome();
        Biome.biomes[ApocalypseGenerator.FIRE_BIOME] = new FireBiome();
        Biome.biomes[ApocalypseGenerator.COMMON_BIOME] = new CommonBiome();

        clusters = new Cluster[]{

        };

        Generator.addGenerator(ApocalypseGenerator.class, "apocalypse", Generator.TYPE_INFINITE);
    }

    @Override
    public void onEnable(){
        getServer().getPluginManager().registerEvents(new MainListener(clusters), this);
    }

    public static Apocalypse getInstance() {
        return instance;
    }

}

package ru.dragonestia.generator;

import cn.nukkit.level.biome.Biome;
import cn.nukkit.level.generator.Generator;
import cn.nukkit.plugin.PluginBase;
import ru.dragonestia.generator.level.ApocalypseGenerator;
import ru.dragonestia.generator.level.biome.CommonBiome;
import ru.dragonestia.generator.level.biome.AshBiome;
import ru.dragonestia.generator.level.biome.FireBiome;
import ru.dragonestia.generator.listener.MainListener;

public class Apocalypse extends PluginBase {

    @Override
    public void onLoad(){
        Biome.biomes[ApocalypseGenerator.ASH_BIOME] = new AshBiome();
        Biome.biomes[ApocalypseGenerator.FIRE_BIOME] = new FireBiome();
        Biome.biomes[ApocalypseGenerator.COMMON_BIOME] = new CommonBiome();

        Generator.addGenerator(ApocalypseGenerator.class, "apocalypse", Generator.TYPE_INFINITE);
    }

    @Override
    public void onEnable(){
        getServer().getPluginManager().registerEvents(new MainListener(), this);
    }

}

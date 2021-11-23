package ru.dragonestia.apocalypse.task;

import cn.nukkit.scheduler.PluginTask;
import ru.dragonestia.apocalypse.Apocalypse;
import ru.dragonestia.apocalypse.player.PlayerData;

import java.util.Random;

public class IllnessTask extends PluginTask<Apocalypse> {

    private final Random random = new Random();

    public IllnessTask(Apocalypse owner) {
        super(owner);
    }

    @Override
    public void onRun(int tick) {
        for(PlayerData playerData: owner.getPlayerManager().playerData){
            if(random.nextFloat() > 0.5f) playerData.giveRadiationEffects();
        }
    }

}

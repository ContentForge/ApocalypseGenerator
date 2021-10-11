package ru.dragonestia.apocalypse.chat.sender;

import cn.nukkit.Player;
import cn.nukkit.math.Vector3;
import ru.dragonestia.apocalypse.Apocalypse;
import ru.dragonestia.apocalypse.player.PlayerData;
import ru.dragonestia.apocalypse.storms.defaults.RadioStorm;

public class PointMessage implements RadioMessage {

    private final Vector3 pos;
    private final int radius;
    private final Apocalypse main;

    public PointMessage(Vector3 pos, int radius, Apocalypse main){
        this.pos = pos;
        this.radius = radius;
        this.main = main;
    }

    @Override
    public float getQuality(float factor) {
        return 1F / factor;
    }

    @Override
    public float getLost(PlayerData target) {
        float radioDistance = radius * getQuality(main.getGlobalEvents().currentEvent instanceof RadioStorm ? 30 : 1);
        double distance = pos.distance(target.getPlayer());

        if(radioDistance >= distance) return 0F;

        float lost = radioDistance / (float) distance;
        return lost - 1F;
    }

    @Override
    public String getSender(Player target) {
        return DEFAULT_SENDER_NAME;
    }

}

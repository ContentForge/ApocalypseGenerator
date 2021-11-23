package ru.dragonestia.apocalypse.chat.sender;

import cn.nukkit.Player;
import ru.dragonestia.apocalypse.Apocalypse;
import ru.dragonestia.apocalypse.player.PlayerData;
import ru.dragonestia.apocalypse.storms.defaults.RadioStorm;

public class PlayerMessage implements RadioMessage {

    private final PlayerData playerData;
    private final Apocalypse main;

    public PlayerMessage(PlayerData playerData, Apocalypse main){
        this.playerData = playerData;
        this.main = main;
    }

    @Override
    public float getQuality(float factor) {
        return playerData.getRadio().getPower() / factor;
    }

    @Override
    public float getLost(PlayerData target) {
        float radioDistance = playerData.getRadio().getDistance() * getQuality(main.getGlobalEvents().currentEvent instanceof RadioStorm? 30 : 1);
        double distance = playerData.getPlayer().distance(target.getPlayer());

        if(radioDistance >= distance) return 0F;

        float lost = radioDistance / (float) distance;
        return lost - 1F;
    }

    @Override
    public String getSender(Player target) {
        return target.isOp()? playerData.getPlayer().getName() : DEFAULT_SENDER_NAME;
    }

}

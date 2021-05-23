package ru.dragonestia.apocalypse.chat.sender;

import cn.nukkit.Player;
import ru.dragonestia.apocalypse.player.PlayerData;

public class ServerMessage implements RadioMessage {

    @Override
    public float getQuality(float factor) {
        return 1F;
    }

    @Override
    public float getLost(PlayerData target) {
        return 1 - target.getRadioQuality();
    }

    @Override
    public String getSender(Player target) {
        return DEFAULT_SENDER_NAME;
    }

}

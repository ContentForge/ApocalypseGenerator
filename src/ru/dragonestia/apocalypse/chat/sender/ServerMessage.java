package ru.dragonestia.apocalypse.chat.sender;

import cn.nukkit.Player;
import ru.dragonestia.apocalypse.chat.Radio;

public class ServerMessage implements RadioMessage {

    @Override
    public float getQuality(float factor) {
        return 1F;
    }

    @Override
    public float getLost(Radio target) {
        return 1 - target.getQuality();
    }

    @Override
    public String getSender(Player target) {
        return DEFAULT_SENDER_NAME;
    }

}

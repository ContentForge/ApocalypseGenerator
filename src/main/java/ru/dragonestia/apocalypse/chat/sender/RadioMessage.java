package ru.dragonestia.apocalypse.chat.sender;

import cn.nukkit.Player;
import ru.dragonestia.apocalypse.player.PlayerData;

public interface RadioMessage {

    String DEFAULT_SENDER_NAME = "Радио";

    float getQuality(float factor);

    float getLost(PlayerData target);

    String getSender(Player target);

}

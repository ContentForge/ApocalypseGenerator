package ru.dragonestia.generator.listener;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.server.DataPacketSendEvent;
import cn.nukkit.level.Level;
import cn.nukkit.network.protocol.DataPacket;
import cn.nukkit.network.protocol.StartGamePacket;

public class MainListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDataPacketSend(DataPacketSendEvent event) {
        DataPacket packet = event.getPacket();
        if (packet instanceof StartGamePacket) {
            ((StartGamePacket) packet).dimension = Level.DIMENSION_THE_END;
        }
    }

}

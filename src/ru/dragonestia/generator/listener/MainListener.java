package ru.dragonestia.generator.listener;

import cn.nukkit.block.Block;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.server.DataPacketSendEvent;
import cn.nukkit.level.Level;
import cn.nukkit.level.biome.Biome;
import cn.nukkit.network.protocol.DataPacket;
import cn.nukkit.network.protocol.StartGamePacket;
import ru.dragonestia.generator.level.biome.ApocalypseBiome;
import ru.dragonestia.generator.level.populator.cluster.Cluster;

public class MainListener implements Listener {

    private final Cluster[] clusters;

    public MainListener(Cluster[] cluster){
        this.clusters = cluster;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDataPacketSend(DataPacketSendEvent event) {
        DataPacket packet = event.getPacket();
        if (packet instanceof StartGamePacket) {
            ((StartGamePacket) packet).dimension = Level.DIMENSION_THE_END;
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBreak(BlockBreakEvent event){
        Block block = event.getBlock();

        for(Cluster cluster: clusters){
            if(block.getId() == cluster.getBlockId()){
                Biome biome = Biome.getBiome(block.level.getBiomeId(block.getFloorX(), block.getFloorZ()));
                if(!(biome instanceof ApocalypseBiome)) return;

                event.setDrops(cluster.getDrop((ApocalypseBiome) biome, block.getFloorX(), block.getFloorY(), block.getFloorZ()));
                return;
            }
        }
    }

}

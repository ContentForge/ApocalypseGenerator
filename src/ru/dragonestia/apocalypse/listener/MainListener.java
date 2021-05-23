package ru.dragonestia.apocalypse.listener;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.event.server.DataPacketSendEvent;
import cn.nukkit.level.Level;
import cn.nukkit.level.biome.Biome;
import cn.nukkit.network.protocol.DataPacket;
import cn.nukkit.network.protocol.StartGamePacket;
import ru.dragonestia.apocalypse.Apocalypse;
import ru.dragonestia.apocalypse.level.biome.ApocalypseBiome;
import ru.dragonestia.apocalypse.level.populator.cluster.Cluster;

public class MainListener implements Listener {

    private final Cluster[] clusters;
    private final Apocalypse main;

    public MainListener(Apocalypse main, Cluster[] cluster){
        this.main = main;
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

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();

        main.getChatManager().initPlayer(player);
        main.initScoreboard(player);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();

        main.getChatManager().unloadRadio(player);
    }

}

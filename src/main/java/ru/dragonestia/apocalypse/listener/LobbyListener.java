package ru.dragonestia.apocalypse.listener;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.event.block.ItemFrameDropItemEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.entity.EntityExplodeEvent;
import cn.nukkit.event.player.*;
import cn.nukkit.event.server.DataPacketSendEvent;
import cn.nukkit.math.Vector3;
import cn.nukkit.network.protocol.DataPacket;
import cn.nukkit.network.protocol.StartGamePacket;
import ru.dragonestia.apocalypse.Apocalypse;

import cn.nukkit.level.Position;

public class LobbyListener implements Listener {

    private final Apocalypse main;

    public LobbyListener(Apocalypse main){
        this.main = main;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (this.isNotInLobby(player)) {
            player.setNameTagAlwaysVisible(false);
        } else {
            player.setNameTagAlwaysVisible(true);
            player.teleport(new Vector3(567.5D, 21.0D, 419.5D));
            player.setGamemode(2);

        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        if(isNotInLobby(player)) return;

        main.getLobbyMusicTask().leaveFromLobby(player);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDataPacketSend(DataPacketSendEvent event) {
        DataPacket packet = event.getPacket();
        if (packet instanceof StartGamePacket) {
            if(!isNotInLobby(event.getPlayer())) main.getLobbyMusicTask().joinToLobby(event.getPlayer(), 10);
        }
    }

    @EventHandler(
            priority = EventPriority.HIGHEST
    )
    public void onRespawn(PlayerRespawnEvent event) {
        if (!this.isNotInLobby(event.getPlayer())) {
            event.setRespawnPosition(new Position(566.5D, 21.0D, 418.5D, this.main.getServer().getLevelByName("lobby")));
            main.getLobbyMusicTask().joinToLobby(event.getPlayer());
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        event.getEntity().setSpawn(new Position(566.5D, 21.0D, 418.5D, this.main.getServer().getLevelByName("lobby")));
        event.getEntity().setGamemode(2);
        event.getEntity().setNameTagAlwaysVisible(true);

        main.getLobbyMusicTask().joinToLobby(event.getEntity());
    }

    @EventHandler
    public void onHungry(PlayerFoodLevelChangeEvent event) {
        if (!this.isNotInLobby(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(
            priority = EventPriority.HIGHEST
    )
    public void onTap(PlayerInteractEvent event) {
        if (event.getAction().equals(PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(PlayerInteractEvent.Action.PHYSICAL)) {
            Player player = event.getPlayer();
            if (!this.isNotInLobby(player)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(
            priority = EventPriority.HIGHEST
    )
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player)event.getEntity();
            if (!this.isNotInLobby(player)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(
            priority = EventPriority.HIGHEST
    )
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (!this.isNotInLobby(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(
            priority = EventPriority.HIGHEST
    )
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (!this.isNotInLobby(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(
            priority = EventPriority.HIGHEST
    )
    public void onEmptyBucket(PlayerBucketEmptyEvent event) {
        Player player = event.getPlayer();
        if (!this.isNotInLobby(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(
            priority = EventPriority.HIGHEST
    )
    public void onExplode(EntityExplodeEvent event) {
        if (!this.isNotInLobby(event.getEntity())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(
            priority = EventPriority.HIGHEST
    )
    public void onDrop(ItemFrameDropItemEvent event) {
        if (!this.isNotInLobby(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    private boolean isNotInLobby(Position pos) {
        boolean inLobby = pos.getLevel().getName().equals("lobby");
        if (pos instanceof Player) {
            Player player = (Player)pos;
            inLobby = inLobby && (!player.isOp() || !player.isCreative());
        }

        return !inLobby;
    }

}

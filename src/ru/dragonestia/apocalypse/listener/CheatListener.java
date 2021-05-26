package ru.dragonestia.apocalypse.listener;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.math.Vector3;
import ru.dragonestia.apocalypse.Apocalypse;
import ru.dragonestia.apocalypse.player.PlayerData;

public class CheatListener implements Listener {

    private final Apocalypse main;

    public CheatListener(Apocalypse main){
        this.main = main;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDamage(EntityDamageEvent event){
        if(!(event.getEntity() instanceof Player) || event.isCancelled()) return;
        Player player = (Player) event.getEntity();
        PlayerData playerData = main.getPlayerManager().get(player);

        if(!playerData.isCheater()) return;
        event.setDamage(event.getDamage() * 3);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        PlayerData playerData = main.getPlayerManager().get(player);

        if(!playerData.isCheater() || !main.getGameLevel().equals(player.getLevel())) return;
        player.teleport(new Vector3(player.x, player.getLevel().getHighestBlockAt(player.getFloorX(), player.getFloorZ()), player.z));
    }

}

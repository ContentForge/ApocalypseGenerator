package ru.dragonestia.apocalypse.task;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.level.Position;
import cn.nukkit.scheduler.PluginTask;
import ru.dragonestia.apocalypse.Apocalypse;

import java.util.HashMap;

public class TeleportingTask extends PluginTask<Apocalypse> implements Listener {

    public static final int TELEPORT_DELAY = 60;

    private final HashMap<Long, Request> players = new HashMap<>();

    public TeleportingTask(Apocalypse owner) {
        super(owner);

        owner.getServer().getPluginManager().registerEvents(this, owner);
    }

    public void addPlayer(Player player){
        players.put(player.getId(), new Request(player));
        player.sendMessage("§eЧерез 60 секунд вы будете телепортированы на стартовую локацию.");
    }

    @Override
    public void onRun(int i) {
        for(Request request: players.values()){
            request.update(owner, this);
        }
    }

    @EventHandler
    private void onQuit(PlayerQuitEvent event){
        players.remove(event.getPlayer().getId());
    }

    @EventHandler
    private void onDamageTaken(EntityDamageEvent event){
        if(event.isCancelled() || !(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();

        if(!players.containsKey(player.getId())) return;

        players.remove(player.getId());
        player.sendMessage("§cТелепортация была отменена из-за получения урона.");
    }

    public static class Request {

        private final Player player;
        private int left = TELEPORT_DELAY;

        public Request(Player player){
            this.player = player;
        }

        public void update(Apocalypse main, TeleportingTask task){
            player.sendTip("До телепортации осталось");

            if(left-- != 0) return;

            player.sendMessage("§eТелепортирование...");
            task.players.remove(player.getId());
            player.teleport(new Position(566.5D, 21.0D, 418.5D, player.getServer().getLevelByName("lobby")));
            player.setGamemode(Player.ADVENTURE);
            main.getLobbyMusicTask().joinToLobby(player, 3);
        }
    }

}

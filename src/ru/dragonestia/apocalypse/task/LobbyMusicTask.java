package ru.dragonestia.apocalypse.task;

import cn.nukkit.Player;
import cn.nukkit.network.protocol.PlaySoundPacket;
import cn.nukkit.network.protocol.StopSoundPacket;
import cn.nukkit.scheduler.PluginTask;
import ru.dragonestia.apocalypse.Apocalypse;

import java.util.HashMap;

public class LobbyMusicTask extends PluginTask<Apocalypse> {

    private final HashMap<Player, Long> players = new HashMap<>();
    private static final String MUSIC_NAME = "dragonestia.music.lobby";
    private static final int MUSIC_TIME = 133;

    public LobbyMusicTask(Apocalypse owner) {
        super(owner);
    }

    public void stopAllSounds(Player player){
        StopSoundPacket pk = new StopSoundPacket();
        pk.stopAll = true;
        pk.name = MUSIC_NAME;
        player.dataPacket(pk);
    }

    public void joinToLobby(Player player){
        joinToLobby(player, 0);
    }

    public void joinToLobby(Player player, int offset){
        if(players.containsKey(player) && players.get(player) > System.currentTimeMillis() / 1000L + offset) return;
        stopAllSounds(player);
        players.put(player, System.currentTimeMillis() / 1000L + offset);
    }

    public void leaveFromLobby(Player player){
        stopAllSounds(player);
        players.remove(player);
    }

    @Override
    public void onRun(int i) {
        for(Player player: players.keySet()){
            if(players.get(player) > System.currentTimeMillis() / 1000L) continue;
            stopAllSounds(player);

            PlaySoundPacket pk = new PlaySoundPacket();
            pk.x = player.getFloorX();
            pk.y = player.getFloorY();
            pk.z = player.getFloorZ();
            pk.volume = 0.25f;
            pk.pitch = 1f;
            pk.name = MUSIC_NAME;
            player.dataPacket(pk);

            players.put(player, System.currentTimeMillis() / 1000L + MUSIC_TIME);
        }
    }

}

package ru.dragonestia.apocalypse.player;

import cn.nukkit.Player;
import com.google.gson.Gson;
import lombok.SneakyThrows;
import ru.dragonestia.apocalypse.Apocalypse;
import ru.dragonestia.apocalypse.chat.sender.PlayerMessage;
import ru.dragonestia.apocalypse.chat.sender.ServerMessage;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashSet;
import java.util.Random;

public class PlayerManager {

    public static final short DEFAULT_CHANNEL = 1000;
    public static final String PLAYERS_DATA_PATH = "PlayerData/";

    public final HashSet<PlayerData> playerData = new HashSet<>();
    public final Apocalypse main;
    public final Random random = new Random();
    private final Gson gson = new Gson();

    public PlayerManager(Apocalypse main){
        this.main = main;

        File playersDir = new File(PLAYERS_DATA_PATH);
        if(!playersDir.exists()) playersDir.mkdir();
    }

    @SneakyThrows
    public void initPlayer(Player player){
        PlayerData playerData;
        File file = new File(PLAYERS_DATA_PATH + player.getLoginChainData().getXUID());
        if(file.exists()){
            try (FileReader reader = new FileReader(file)){
                playerData = gson.fromJson(reader, PlayerData.class);
            }
        } else playerData = new PlayerData();
        playerData.init(player);
        this.playerData.add(playerData);
    }

    public PlayerData get(Player player){
        for(PlayerData playerData : this.playerData){
            if(player.equals(playerData.getPlayer())) return playerData;
        }
        return null;
    }

    @SneakyThrows
    public void unload(Player player){
        PlayerData playerData = get(player);
        if(playerData == null) return;

        try (FileWriter writer = new FileWriter(new File(PLAYERS_DATA_PATH + playerData.getPlayer().getLoginChainData().getXUID()))){
            writer.write(gson.toJson(playerData));
        }

        this.playerData.remove(playerData);
    }

    @SneakyThrows
    public void unloadAll(){
        for(PlayerData playerData : this.playerData){
            try (FileWriter writer = new FileWriter(new File(PLAYERS_DATA_PATH + playerData.getPlayer().getLoginChainData().getXUID()))){
                writer.write(gson.toJson(playerData));
            }
        }
    }

    public void sendMessage(PlayerData sender, String text){
        PlayerMessage message = new PlayerMessage(sender, main);

        int price = (random.nextFloat() < sender.getRadio().getMaxDistance() / (float) sender.getRadio().getDistance() * (sender.getRadio().isAnonymous()? 1f : 3f))? 1 : 0;
        if(sender.getRadio().getCharge() < price){
            sender.getPlayer().sendMessage("§cСлишком низкий заряд радиопередатчика. Требуется подзарядка!");
            return;
        }

        sender.getRadio().spendCharge((short) price);
        for(PlayerData playerData : this.playerData) playerData.sendMessage(message, text);
    }

    public void sendMessage(String text){
        ServerMessage message = new ServerMessage();
        for(PlayerData playerData : this.playerData){
            if(playerData.getPlayer().getLevel().getName().equals("lobby")) continue;
            playerData.sendMessage(message, text);
        }
    }

    public void broadcastSound(String sound, float volume, float pitch){
        for(PlayerData playerData: playerData){
            playerData.playSound(sound, volume, pitch);
        }
    }

}

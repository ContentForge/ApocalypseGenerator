package ru.dragonestia.apocalypse.player;

import cn.nukkit.Player;
import ru.dragonestia.apocalypse.Apocalypse;
import ru.dragonestia.apocalypse.chat.sender.PlayerMessage;
import ru.dragonestia.apocalypse.chat.sender.ServerMessage;

import java.util.HashSet;
import java.util.Random;

public class PlayerManager {

    public static final short DEFAULT_CHANNEL = 1000;
    private final HashSet<PlayerData> playerData = new HashSet<>();
    public final Apocalypse main;
    public final Random random = new Random();

    public PlayerManager(Apocalypse main){
        this.main = main;
    }

    public PlayerData initPlayer(Player player){
        PlayerData playerData = new PlayerData(player, this);
        this.playerData.add(playerData);
        return playerData;
    }

    public PlayerData get(Player player){
        for(PlayerData playerData : this.playerData){
            if(player.equals(playerData.getPlayer())) return playerData;
        }
        return null;
    }

    public void unloadRadio(Player player){
        PlayerData playerData = get(player);
        if(playerData == null) return;
        playerData.save();
        this.playerData.remove(playerData);
    }

    public void unloadAllRadio(){
        for(PlayerData playerData : this.playerData){
            playerData.save();
        }
    }

    public void sendMessage(PlayerData sender, String text){
        PlayerMessage message = new PlayerMessage(sender, main);

        int price = (random.nextFloat() < sender.getMaxRadioDistance() / (float) sender.getRadioDistance())? 1 : 0;
        if(sender.getRadioCharge() < price){
            sender.getPlayer().sendMessage("§cСлишком низкий заряд радиопередатчика. Требуется подзарядка!");
            return;
        }

        sender.setRadioCharge((short) (sender.getRadioCharge() - price));
        for(PlayerData playerData : this.playerData) playerData.sendMessage(message, text);
    }

    public void sendMessage(String text){
        ServerMessage message = new ServerMessage();
        for(PlayerData playerData : this.playerData){
            if(playerData.getPlayer().getLevel().getName().equals("lobby")) continue;
            playerData.sendMessage(message, text);
        }
    }

}

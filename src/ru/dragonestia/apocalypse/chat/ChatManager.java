package ru.dragonestia.apocalypse.chat;

import cn.nukkit.Player;
import ru.dragonestia.apocalypse.Apocalypse;
import ru.dragonestia.apocalypse.chat.sender.PlayerMessage;
import ru.dragonestia.apocalypse.chat.sender.ServerMessage;

import java.util.HashSet;
import java.util.Random;

public class ChatManager {

    public static final short DEFAULT_CHANNEL = 1000;
    private final HashSet<Radio> radios = new HashSet<>();
    public final Apocalypse main;
    public final Random random = new Random();

    public ChatManager(Apocalypse main){
        this.main = main;
    }

    public Radio initPlayer(Player player){
        Radio radio = new Radio(player, this);
        radios.add(radio);
        return radio;
    }

    public Radio get(Player player){
        for(Radio radio: radios){
            if(player.equals(radio.getPlayer())) return radio;
        }
        return null;
    }

    public void unloadRadio(Player player){
        Radio radio = get(player);
        if(radio == null) return;
        radio.save();
        radios.remove(radio);
    }

    public void unloadAllRadio(){
        for(Radio radio: radios){
            radio.save();
        }
    }

    public void sendMessage(Radio sender, String text){
        PlayerMessage message = new PlayerMessage(sender, main);

        int price = (random.nextFloat() < sender.getMaxDistance() / (float) sender.getDistance())? 1 : 0;
        if(sender.getCharge() < price){
            sender.getPlayer().sendMessage("§cСлишком низкий заряд радиопередатчика. Требуется подзарядка!");
            return;
        }

        sender.setCharge((short) (sender.getCharge() - price));
        for(Radio radio: radios) radio.sendMessage(message, text);
    }

    public void sendMessage(String text){
        ServerMessage message = new ServerMessage();
        for(Radio radio: radios){
            if(radio.getPlayer().getLevel().getName().equals("lobby")) continue;
            radio.sendMessage(message, text);
        }
    }

}

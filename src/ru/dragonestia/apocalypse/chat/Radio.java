package ru.dragonestia.apocalypse.chat;

import cn.nukkit.Player;
import cn.nukkit.utils.Config;
import ru.dragonestia.apocalypse.Apocalypse;
import ru.dragonestia.apocalypse.chat.sender.RadioMessage;

import java.util.Random;

public class Radio {

    public static final short MAX_CHARGE = 200;
    public static final float SAVE_CHANCE = 0.7F;
    private final static char[] BAD_CHARS = {'@', '#', '$', '^', '*', '~', 'Ω', 'Σ', 'ζ', 'λ', 'ψ', '∀', '∂', 'ℶ'};

    private final Player player;
    private final ChatManager chatManager;
    private short distance;
    private short maxDistance;
    private float quality;
    private short charge;
    private short channel;

    public Radio(Player player, ChatManager chatManager){
        this.player = player;
        this.chatManager = chatManager;

        Config config = getData();
        distance = config.exists("distance")? ((short) config.getInt("distance")) : 1000;
        maxDistance = config.exists("max_distance")? ((short) config.getInt("max_distance")) : 1000;
        quality = config.exists("quality")? ((float) config.getDouble("quality")) : 0.5F;
        charge = config.exists("charge")? ((short) config.getInt("charge")) : 50;
        channel = config.exists("channel")? ((short) config.getInt("channel")) : 1000;
    }

    public void reset(){
        distance = 1000;
        maxDistance = 1000;
        quality = 0.5F;
        charge = 50;
        channel = ChatManager.DEFAULT_CHANNEL;
    }

    public void save(){
        Config config = getData();
        config.set("distance", distance);
        config.set("max_distance", maxDistance);
        config.set("quality", quality);
        config.set("charge", charge);
        config.set("channel", channel);
        config.save();
    }

    public void onDeath(){
        if(chatManager.random.nextFloat() > SAVE_CHANCE){
            reset();
            player.sendMessage("§eВсе ваши улучшения и настройки радио были сброшены.");
        }
    }

    public Player getPlayer() {
        return player;
    }

    public ChatManager getChatManager() {
        return chatManager;
    }

    public short getDistance() {
        return distance;
    }

    public short getMaxDistance() {
        return maxDistance;
    }

    public float getQuality() {
        return quality;
    }

    public short getCharge() {
        return charge;
    }

    public short getChannel() {
        return channel;
    }

    public Radio setDistance(short distance) {
        this.distance = distance;
        return this;
    }

    public Radio setMaxDistance(short maxDistance) {
        this.maxDistance = maxDistance;
        return this;
    }

    public Radio setQuality(float quality) {
        this.quality = quality;
        return this;
    }

    public Radio setCharge(short charge) {
        this.charge = charge;
        if(this.charge > MAX_CHARGE) this.charge = MAX_CHARGE;
        return this;
    }

    public Radio setChannel(short channel) {
        this.channel = channel;
        return this;
    }

    public void sendMessage(RadioMessage message, String text){
        float lost = message.getLost(this);
        if(lost > 0.75f) return;
        if(lost <= 0){
            sendMessage(message.getSender(player), text);
            return;
        }

        char[] chars = text.toCharArray();
        for(int i = 0; i < chars.length; i++){
            if(chars[i] == ' ') continue;
            if(chatManager.random.nextFloat() < lost) chars[i] = BAD_CHARS[chatManager.random.nextInt(BAD_CHARS.length)];
        }
        sendMessage(message.getSender(player), new String(chars));
    }

    private void sendMessage(String senderName, String message){
        player.sendMessage("§c§l["+senderName+"]§r§7 "+message+"§к");
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Radio){
            return this.player.getName().equals(((Radio) obj).player.getName());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return player.getName().hashCode();
    }

    private Config getData(){
        return new Config(chatManager.main.contentPath+"radios/"+player.getName().toLowerCase(), Config.YAML);
    }

}

package ru.dragonestia.apocalypse.player;

import cn.nukkit.Player;
import cn.nukkit.utils.Config;
import ru.dragonestia.apocalypse.chat.sender.RadioMessage;

public class PlayerData {

    public static final short MAX_CHARGE = 200;
    public static final float SAVE_CHANCE = 0.7F;
    private final static char[] BAD_CHARS = {'@', '#', '$', '^', '*', '~', 'Ω', 'Σ', 'ζ', 'λ', 'ψ', '∀', '∂', 'ℶ'};

    private final Player player;
    private final PlayerManager playerManager;
    private short distance;
    private short maxDistance;
    private float quality;
    private short charge;
    private short channel;

    public PlayerData(Player player, PlayerManager playerManager){
        this.player = player;
        this.playerManager = playerManager;

        Config config = getData();
        distance = config.exists("r_distance")? ((short) config.getInt("r_distance")) : 1000;
        maxDistance = config.exists("r_max_distance")? ((short) config.getInt("r_max_distance")) : 1000;
        quality = config.exists("r_quality")? ((float) config.getDouble("r_quality")) : 0.5F;
        charge = config.exists("r_charge")? ((short) config.getInt("r_charge")) : 50;
        channel = config.exists("r_channel")? ((short) config.getInt("r_channel")) : 1000;
    }

    public void resetAll(){
        resetRadio();
    }

    public void resetRadio(){
        distance = 1000;
        maxDistance = 1000;
        quality = 0.5F;
        charge = 50;
        channel = PlayerManager.DEFAULT_CHANNEL;
    }

    public void save(){
        Config config = getData();
        config.set("r_distance", distance);
        config.set("r_max_distance", maxDistance);
        config.set("r_quality", quality);
        config.set("r_charge", charge);
        config.set("r_channel", channel);
        config.save();
    }

    public void onDeath(){
        if(playerManager.random.nextFloat() > SAVE_CHANCE){
            resetRadio();
            player.sendMessage("§eВсе ваши улучшения и настройки радио были сброшены.");
        }
    }

    public Player getPlayer() {
        return player;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public short getRadioDistance() {
        return distance;
    }

    public short getMaxRadioDistance() {
        return maxDistance;
    }

    public float getRadioQuality() {
        return quality;
    }

    public short getRadioCharge() {
        return charge;
    }

    public short getRadioChannel() {
        return channel;
    }

    public PlayerData setRadioDistance(short distance) {
        this.distance = distance;
        return this;
    }

    public PlayerData setMaxRadioDistance(short maxDistance) {
        this.maxDistance = maxDistance;
        return this;
    }

    public PlayerData setRadioQuality(float quality) {
        this.quality = quality;
        return this;
    }

    public PlayerData setRadioCharge(short charge) {
        this.charge = charge;
        if(this.charge > MAX_CHARGE) this.charge = MAX_CHARGE;
        return this;
    }

    public PlayerData setRadioChannel(short channel) {
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
            if(playerManager.random.nextFloat() < lost) chars[i] = BAD_CHARS[playerManager.random.nextInt(BAD_CHARS.length)];
        }
        sendMessage(message.getSender(player), new String(chars));
    }

    private void sendMessage(String senderName, String message){
        player.sendMessage("§c§l["+senderName+"]§r§7 "+message+"§к");
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof PlayerData){
            return this.player.getName().equals(((PlayerData) obj).player.getName());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return player.getName().hashCode();
    }

    private Config getData(){
        return new Config(playerManager.main.contentPath+"players/"+player.getName().toLowerCase(), Config.YAML);
    }

}

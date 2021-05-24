package ru.dragonestia.apocalypse.player;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import cn.nukkit.level.Sound;
import cn.nukkit.network.protocol.PlaySoundPacket;
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
    private double radiation;
    public double lastRadiationLevel = 0;

    public PlayerData(Player player, PlayerManager playerManager){
        this.player = player;
        this.playerManager = playerManager;

        Config config = getData();
        distance = config.exists("r_distance")? ((short) config.getInt("r_distance")) : 1000;
        maxDistance = config.exists("r_max_distance")? ((short) config.getInt("r_max_distance")) : 1000;
        quality = config.exists("r_quality")? ((float) config.getDouble("r_quality")) : 0.5F;
        charge = config.exists("r_charge")? ((short) config.getInt("r_charge")) : 50;
        channel = config.exists("r_channel")? ((short) config.getInt("r_channel")) : 1000;
        radiation = config.exists("rad")? config.getDouble("rad") : 0;
    }

    public void resetAll(){
        resetRadio();

        radiation = 0;
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
        config.set("rad", radiation);
        config.save();
    }

    public void onDeath(){
        radiation = 0;
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
        text = text.replaceAll("^[!.]", "");
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
        playSound("dragonestia.radio.message", 0.3F, 1F);
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

    public double getRadiationLevel(){
        return radiation;
    }

    public double addRadiation(double count){
        radiation += count;
        return radiation;
    }

    public void reduceRadiationLevel(int count){
        radiation -= count;
        if(radiation < 0) radiation = 0;
    }

    public void giveRadiationEffects(){
        //TODO: Добавить эффекты радиации
    }

    public double getGroundRadiation(double rad){
        if(player.y < 60) rad = (player.y / 60) * rad;
        lastRadiationLevel = (lastRadiationLevel + rad * 3) / 4;

        if(rad > 0.6){
            PlaySoundPacket packet = new PlaySoundPacket();
            packet.name = "dragonestia.radiation";
            packet.volume = 0.2F;
            packet.pitch = 1F;
            packet.x = player.getFloorX();
            packet.y = player.getFloorY();
            packet.z = player.getFloorZ();

            if(playerManager.random.nextFloat() > 0.35f) player.dataPacket(packet);
            if(rad > 100) player.dataPacket(packet);
            if(rad > 300)player.dataPacket(packet);
            if(rad > 500)player.dataPacket(packet);
        }

        return lastRadiationLevel;
    }

    public double handleRadiation(double rad){
        double multiplier = 1;

        //Шлем
        switch (player.getInventory().getHelmet().getId()){
            case Item.CHAIN_HELMET:
                multiplier -= 0.6;
                break;
            case Item.DIAMOND_HELMET:
            case Item.IRON_HELMET:
                multiplier -= 0.2;
                break;
        }

        //Нагрудник
        switch (player.getInventory().getChestplate().getId()){
            case Item.CHAIN_CHESTPLATE:
                multiplier -= 0.2;
                break;
            case Item.DIAMOND_CHESTPLATE:
            case Item.IRON_CHESTPLATE:
                multiplier -= 0.1;
                break;
        }

        //Штаны
        switch (player.getInventory().getHelmet().getId()){
            case Item.CHAIN_LEGGINGS:
                multiplier -= 0.07;
                break;
            case Item.DIAMOND_LEGGINGS:
            case Item.IRON_LEGGINGS:
                multiplier -= 0.03;
                break;
        }

        //Ботинки
        switch (player.getInventory().getHelmet().getId()){
            case Item.CHAIN_BOOTS:
                multiplier -= 0.03;
                break;
            case Item.DIAMOND_BOOTS:
            case Item.IRON_BOOTS:
                multiplier -= 0.01;
                break;
        }

        double result = multiplier * rad;
        if(player.isSurvival()) radiation += result;
        return result;
    }

    public void playSound(String sound, float volume, float pitch){
        PlaySoundPacket packet = new PlaySoundPacket();
        packet.name = sound;
        packet.volume = volume;
        packet.pitch = pitch;
        packet.x = player.getFloorX();
        packet.y = player.getFloorY();
        packet.z = player.getFloorZ();
        player.dataPacket(packet);
    }

}

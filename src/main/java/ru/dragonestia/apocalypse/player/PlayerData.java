package ru.dragonestia.apocalypse.player;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import cn.nukkit.math.Vector3;
import cn.nukkit.network.protocol.PlaySoundPacket;
import cn.nukkit.potion.Effect;
import ru.dragonestia.apocalypse.Apocalypse;
import ru.dragonestia.apocalypse.chat.sender.RadioMessage;
import ru.dragonestia.apocalypse.player.submodules.Bed;
import ru.dragonestia.apocalypse.player.submodules.PocketMoney;
import ru.dragonestia.apocalypse.player.submodules.Radio;
import ru.dragonestia.apocalypse.player.submodules.Stats;

import java.util.ArrayList;

public class PlayerData {

    private final transient static char[] BAD_CHARS = {'@', '#', '$', '^', '*', '~', 'Ω', 'Σ', 'ζ', 'λ', 'ψ', '∀', '∂', 'ℶ'};

    private final Radio radio = new Radio();
    private final Stats stats = new Stats();
    private final PocketMoney pocketMoney = new PocketMoney();
    private Bed bed = null;

    private double radiation = 0;
    public transient double lastRadiationLevel = 0;
    private boolean takenBonus = false;

    private transient Player player;

    public void init(Player player){
        this.player = player;

        radio.init(player);
        stats.init(player);
        pocketMoney.init(player);
    }

    public Player getPlayer() {
        return player;
    }

    public void onDeath(){
        radiation = 0;
        lastRadiationLevel = 0;
        radio.downgrade();
        stats.addDeath();
        takenBonus = false;
    }

    public void onKill(PlayerData target){
        stats.addKill();

        int reward = target.pocketMoney.getMoney() / 10;
        if(reward != 0){
            target.pocketMoney.reduceMoney(reward);
            pocketMoney.addMoney(reward);
            player.sendTip("+"+reward+" карточек за убийство");
        }
    }

    public void sendMessage(RadioMessage message, String text){
        if(getRadio().isBroken()) return;

        float lost = message.getLost(this);
        if(lost > 0.75f) return;
        if(lost <= 0){
            sendMessage(message.getSender(player), text, 0);
            return;
        }

        char[] chars = text.toCharArray();
        for(int i = 0; i < chars.length; i++){
            if(chars[i] == ' ') continue;
            if(Apocalypse.random.nextFloat() < lost) chars[i] = BAD_CHARS[Apocalypse.random.nextInt(BAD_CHARS.length)];
        }
        sendMessage(message.getSender(player), new String(chars), lost);
    }

    private void sendMessage(String senderName, String message, float lost){
        playSound("dragonestia.radio.message", 0.3F, 1F - lost);
        player.sendMessage("§c§l["+senderName+"]§r§7 "+message+"§к");
    }

    @Override
    public boolean equals(Object obj) {
        if(obj != null && hashCode() == obj.hashCode() && obj instanceof PlayerData){
            return this.player.getName().equals(((PlayerData) obj).player.getName());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return player.getName().hashCode();
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
        if(radiation < 100_000) return;

        ArrayList<Effect> effects = new ArrayList<>();
        if (radiation < 250_000){
            effects.add(Effect.getEffect(Effect.HUNGER).setVisible(false).setDuration(100*20));
            effects.add(Effect.getEffect(Effect.POISON).setVisible(false).setDuration(10*20));
            effects.add(Effect.getEffect(Effect.NAUSEA).setVisible(false).setDuration(10*20));
        } else if(radiation < 400_000){
            effects.add(Effect.getEffect(Effect.HUNGER).setVisible(false).setDuration(100*20).setAmplifier(1));
            effects.add(Effect.getEffect(Effect.POISON).setVisible(false).setDuration(20*20).setAmplifier(1));
            effects.add(Effect.getEffect(Effect.NAUSEA).setVisible(false).setDuration(30*60).setAmplifier(1));
            effects.add(Effect.getEffect(Effect.SLOWNESS).setVisible(false).setDuration(180*20));
            effects.add(Effect.getEffect(Effect.WEAKNESS).setVisible(false).setDuration(180*20));
        } else if (radiation < 700_000){
            effects.add(Effect.getEffect(Effect.HUNGER).setVisible(false).setDuration(150*20).setAmplifier(2));
            effects.add(Effect.getEffect(Effect.WITHER).setVisible(false).setDuration(15*20).setAmplifier(1));
            effects.add(Effect.getEffect(Effect.NAUSEA).setVisible(false).setDuration(35*20).setAmplifier(2));
            effects.add(Effect.getEffect(Effect.SLOWNESS).setVisible(false).setDuration(320*20).setAmplifier(1));
            effects.add(Effect.getEffect(Effect.WEAKNESS).setVisible(false).setDuration(320*20).setAmplifier(1));
        } else {
            effects.add(Effect.getEffect(Effect.HUNGER).setVisible(false).setDuration(200*20).setAmplifier(3));
            effects.add(Effect.getEffect(Effect.WITHER).setVisible(false).setDuration(65*20).setAmplifier(2));
            effects.add(Effect.getEffect(Effect.SLOWNESS).setVisible(false).setDuration(40*20).setAmplifier(3));
            effects.add(Effect.getEffect(Effect.WEAKNESS).setVisible(false).setDuration(40*20).setAmplifier(3));
        }

        for(Effect effect: effects) player.addEffect(effect);
    }

    public double getGroundRadiation(double rad){
        if(player.y < 60 && player.y > 25) rad += (0.1 - rad) * (1 - (player.y - 35) / 24);
        else if(player.y <= 35) rad = player.y < 25? 0 : 0.1;
        if(rad < 0) rad = 0;
        lastRadiationLevel = (lastRadiationLevel + rad * 3) / 4;

        if(rad > 0.6){
            PlaySoundPacket packet = new PlaySoundPacket();
            packet.name = "dragonestia.radiation";
            packet.volume = 0.2F;
            packet.pitch = 1F;
            packet.x = player.getFloorX();
            packet.y = player.getFloorY();
            packet.z = player.getFloorZ();

            if(Apocalypse.random.nextFloat() > 0.35f) player.dataPacket(packet);
            if(rad > 50) player.dataPacket(packet);
            if(rad > 130) player.dataPacket(packet);
            if(rad > 300) player.dataPacket(packet);
            packet.pitch = 1.3F;
            packet.volume = 0.3F;
            if(rad > 400) player.dataPacket(packet);
            packet.pitch = 1.5F;
            packet.volume = 0.4F;
            if(rad > 500) player.dataPacket(packet);
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
        reduceRadiationLevel(Apocalypse.random.nextInt(1) + 1);
        return result;
    }

    public void takeBonus(){
        takenBonus = true;
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

    public boolean isTakenBonus() {
        return takenBonus;
    }

    public Radio getRadio() {
        return this.radio;
    }

    public Stats getStats() {
        return this.stats;
    }

    public PocketMoney getPocketMoney() {
        return this.pocketMoney;
    }

    public boolean hasBed(){
        return bed != null;
    }

    public Bed getBed(){
        return bed;
    }

    public void placeBed(Vector3 pos){
        bed = new Bed(pos);
    }

    public void removeBed(){
        bed = null;
    }

}

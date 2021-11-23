package ru.dragonestia.apocalypse.player.submodules;

import cn.nukkit.Player;
import cn.nukkit.utils.TextFormat;

import java.util.ArrayList;

public class Radio implements Submodule {

    public final transient static String DEFAULT_TAG = "Радио";
    public final transient static short DEFAULT_CHANNEL = 1000;
    public final transient static short MIN_CHANNEL_RANGE = 700;
    public final transient static short MAX_CHANNEL_RANGE = 2000;
    public final transient static short MAX_CHARGE = 300;
    public final transient static long SAVE_PERIOD = 60 * 60 * 24 * 2;

    private short charge = 50;
    private short maxCharge = 100;
    private float power = 0.5f;
    private int distance = 1000;
    private int maxDistance = 1000;
    private boolean named = false;
    private short channel = DEFAULT_CHANNEL;
    private boolean anonymous = true;
    private boolean broken = false;
    private long saveTime = System.currentTimeMillis() / 1000L + SAVE_PERIOD;

    private transient Player player;

    @Override
    public void init(Player player){
        this.player = player;
    }

    public void downgrade(){
        if(saveTime > System.currentTimeMillis() / 1000L){
            long delta = saveTime - System.currentTimeMillis() / 1000L;
            int seconds = (int) delta % 60;
            int minutes = (int) delta / 60;
            int hours = minutes / 60;
            minutes = minutes % 60;

            player.sendMessage(
                    "§eВаше радио было заменено по гарантии на такое же. До окончания гарантии вашего радио осталось "+hours+"ч "+minutes+"м "+seconds+"с.\n" +
                    "После окончания гарантии вам придется чинить радио самостоятельно!"
            );
            return;
        }
        charge = 50;
        if(maxCharge > 50) maxCharge = (short) Math.max(50, maxCharge - 5);
        if(power > 0.5f) power = Math.max(0.5f, power - 0.05f);
        if(maxDistance > 1000) maxDistance = Math.max(1000, maxDistance - 30);
        if(distance > maxDistance) distance = maxDistance;
        broken = true;

        player.sendMessage("§cВаше радио было повреждено.");
    }

    public void refuel(short value){
        charge = (short) Math.min(charge + value, maxCharge);
    }

    public void spendCharge(short value){
        charge = (short) Math.max(charge - value, 0);
    }

    public void upgradeCharge(short value){
        maxCharge += value;
    }

    public void upgradePower(float value){
        power += value;
    }

    public void setDistance(int value){
        distance = Math.min(distance + value, maxDistance);
    }

    public void upgradeDistance(int value){
        maxDistance += value;
    }

    public void turnNamed(){
        named = true;
    }

    public void setAnonymous(boolean value){
        anonymous = value;
    }

    public void breakRadio(){
        broken = true;
        player.sendMessage("§eВаше радио было сломано. Сейчас им не удастся воспользоваться. Вы можете починить радио у техника, либо исправить его вручную.");
    }

    public void repair(){
        broken = false;
    }

    public boolean isAnonymous() {
        if(named) return anonymous;
        return true;
    }

    public String getTag(){
        if(anonymous) return DEFAULT_TAG;
        return player.getName();
    }

    public static String getPrefix(String name, TextFormat color){
        return "§l"+ color +"[" + name + "] §r§7";
    }

    public short getCharge() {
        return this.charge;
    }

    public short getMaxCharge() {
        return this.maxCharge;
    }

    public float getPower() {
        return this.power;
    }

    public int getDistance() {
        return this.distance;
    }

    public int getMaxDistance() {
        return this.maxDistance;
    }

    public boolean isNamed() {
        return this.named;
    }

    public short getChannel() {
        return this.channel;
    }

    public boolean isBroken() {
        return this.broken;
    }

    public void setChannel(short channel) {
        this.channel = channel;
    }
}

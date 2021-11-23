package ru.dragonestia.apocalypse.player.submodules;

import cn.nukkit.Player;

public class PocketMoney implements Submodule {

    public final transient static int START_START_MONEY = 100;
    public final transient static double MULTIPLIER_LEVEL = 1.1;

    private int money = 0;
    private int saveMoney = 0;
    private int saveMoneyLevel = 1;
    private int secureSecondsLeft = 0;

    private transient Player player;

    @Override
    public void init(Player player) {
        this.player = player;
    }

    public void update(){
        if(secureSecondsLeft > 0) secureSecondsLeft--;
    }

    public void addMoney(int value){
        money += value;
    }

    public void reduceMoney(int value){
        money -= value;
    }

    public boolean hasMoney(int value){
        return money >= value;
    }

    public int getMoney() {
        return this.money;
    }

    public int getSaveMoney() {
        return this.saveMoney;
    }

    public int getSaveMoneyLevel() {
        return this.saveMoneyLevel;
    }

    public int getSecureSecondsLeft() {
        return this.secureSecondsLeft;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void setSaveMoney(int saveMoney) {
        this.saveMoney = saveMoney;
    }

    public void setSaveMoneyLevel(int saveMoneyLevel) {
        this.saveMoneyLevel = saveMoneyLevel;
    }

    public void setSecureSecondsLeft(int secureSecondsLeft) {
        this.secureSecondsLeft = secureSecondsLeft;
    }
}

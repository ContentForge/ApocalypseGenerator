package ru.dragonestia.apocalypse.player.submodules;

import cn.nukkit.Player;

public class Stats implements Submodule {

    private int reputation = 0;
    private int kills = 0;
    private int deaths = 0;
    private int points = 0;

    private transient Player player;

    @Override
    public void init(Player player) {
        this.player = player;
    }

    public void addKill(){
        kills++;
    }

    public void addDeath(){
        deaths++;
    }

    public void addPoints(int value){
        points += value;
    }

    public void addReputation(int value){
        reputation += reputation;
    }

    public int getReputation() {
        return this.reputation;
    }

    public int getKills() {
        return this.kills;
    }

    public int getDeaths() {
        return this.deaths;
    }

    public int getPoints() {
        return this.points;
    }
}

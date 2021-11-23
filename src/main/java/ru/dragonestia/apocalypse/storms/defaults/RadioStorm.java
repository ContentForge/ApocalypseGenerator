package ru.dragonestia.apocalypse.storms.defaults;


import cn.nukkit.Player;

public class RadioStorm extends NormalStorm {

    @Override
    public int getPeriod() {
        return 3 * 60 + 30;
    }

    @Override
    public String getName() {
        return "Геомагнитная буря";
    }

    @Override
    public String generateTitleMessage(Player player, int lastTime) {
        return "§l§gГеомагнитная буря§r [??:??]";
    }

}

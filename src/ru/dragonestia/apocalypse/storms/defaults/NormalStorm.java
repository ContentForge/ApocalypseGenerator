package ru.dragonestia.apocalypse.storms.defaults;

import cn.nukkit.Player;
import ru.dragonestia.apocalypse.storms.GlobalEventBase;

public class NormalStorm extends GlobalEventBase {

    @Override
    public void init() {

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public int getPeriod() {
        return 12 * 60;
    }

    @Override
    public String generateTitleMessage(Player player, int lastTime) {
        return null;
    }

    @Override
    public String getNoticeStartMessage() {
        return null;
    }

    @Override
    public String getStartMessage() {
        return null;
    }

    @Override
    public String getNoticeEndMessage() {
        return null;
    }

    @Override
    public String getEndMessage() {
        return null;
    }

    @Override
    public boolean condition(){
        return true;
    }

}

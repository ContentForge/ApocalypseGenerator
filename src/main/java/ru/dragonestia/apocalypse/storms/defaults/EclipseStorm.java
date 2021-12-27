package ru.dragonestia.apocalypse.storms.defaults;

import cn.nukkit.Player;
import ru.dragonestia.apocalypse.storms.GlobalEventBase;

public class EclipseStorm extends GlobalEventBase {

    @Override
    public void init() {

    }

    @Override
    public String getName() {
        return "Затмение";
    }

    @Override
    public int getPeriod() {
        return 7 * 60;
    }

    @Override
    public String generateTitleMessage(Player player, int lastTime) {
        if(lastTime-15 <= 0) return null;
        return "§l§9Затмение§r§g ["+((lastTime-15)/60)+":"+((lastTime-15)%60)+"]";
    }

    @Override
    public String getNoticeStartMessage() {
        return "Замечено резкое понижение солнечной активности.";
    }

    @Override
    public String getStartMessage() {
        return "Показатели солнечной активности входят в безоспасную отметку! Некоторое время можно безопасно ходить по поверхности.";
    }

    @Override
    public String getNoticeEndMessage() {
        return "Замечено повышение показателей солнечной активности! Заканчивайте ползать по поверхности!";
    }

    @Override
    public String getEndMessage() {
        return "Солнечная активность вновь губительная для организма! Не вылазьте на поверхность без необходимости.";
    }

    @Override
    public boolean condition() {
        return false;
    }

    @Override
    public void handle(Player player) {

    }

}

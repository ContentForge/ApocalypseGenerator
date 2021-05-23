package ru.dragonestia.apocalypse.chat.sender;

import cn.nukkit.Player;
import ru.dragonestia.apocalypse.Apocalypse;
import ru.dragonestia.apocalypse.chat.Radio;
import ru.dragonestia.apocalypse.storms.defaults.RadioStorm;

public class PlayerMessage implements RadioMessage {

    private final Radio radio;
    private final Apocalypse main;

    public PlayerMessage(Radio radio, Apocalypse main){
        this.radio = radio;
        this.main = main;
    }

    @Override
    public float getQuality(float factor) {
        return radio.getQuality() / factor;
    }

    @Override
    public float getLost(Radio target) {
        float radioDistance = radio.getDistance() * getQuality(main.getGlobalEvents().currentEvent instanceof RadioStorm? 30 : 1);
        double distance = radio.getPlayer().distance(target.getPlayer());

        if(radioDistance >= distance) return 0F;

        float lost = radioDistance / (float) distance;
        return lost - 1F;
    }

    @Override
    public String getSender(Player target) {
        return target.isOp()? radio.getPlayer().getName() : DEFAULT_SENDER_NAME;
    }

}

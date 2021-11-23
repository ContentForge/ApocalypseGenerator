package ru.dragonestia.apocalypse.storms;

import cn.nukkit.Player;
import ru.dragonestia.apocalypse.Apocalypse;
import ru.dragonestia.apocalypse.storms.defaults.*;
import ru.dragonestia.apocalypse.task.StormTask;

import java.util.Random;

public class GlobalEvents {

    private static GlobalEvents instance;
    private final GlobalEventBase defaultEvent = new NormalStorm();
    public GlobalEventBase currentEvent = null;
    public GlobalEventBase nextEvent = defaultEvent;
    public int time;
    public final GlobalEventBase[] events = new GlobalEventBase[]{
            new EclipseStorm(),
            new SunStorm(),
            new ShakeStorm(),
            new RadioStorm()
    };
    private final static int NOTICE_PERIOD = 90;
    public final Apocalypse main;
    private final Random random = new Random();

    public GlobalEvents(Apocalypse main){
        this.main = main;
    }

    public void start(){
        currentEvent = nextEvent;
        time = currentEvent.getPeriod();
        nextEvent = events[random.nextInt(events.length)];

        main.getServer().getScheduler().scheduleRepeatingTask(new StormTask(main, this), 20);
        instance = this;
    }

    public static GlobalEvents getInstance() {
        return instance;
    }

    public void handle(){
        time--;
        String msg = currentEvent.getNoticeEndMessage();
        if(time == NOTICE_PERIOD+10 && msg != null){
            main.getPlayerManager().sendMessage(msg);
        }
        msg = nextEvent.getNoticeStartMessage();
        if(time == NOTICE_PERIOD-10 && msg != null){
            main.getPlayerManager().sendMessage(msg);
        }

        if(time <= 0){
            msg = currentEvent.getEndMessage();
            if(msg != null) main.getPlayerManager().sendMessage(msg);
            if(currentEvent instanceof RadioStorm) main.getPlayerManager().broadcastSound("dragonestia.radio.found", 1F, 1F);

            currentEvent = nextEvent;
            time = currentEvent.getPeriod();
            currentEvent.init();

            msg = currentEvent.getStartMessage();
            if(msg != null) main.getPlayerManager().sendMessage(msg);
            if(currentEvent instanceof RadioStorm) main.getPlayerManager().broadcastSound("dragonestia.radio.lost", 1F, 1F);

            if(!currentEvent.equals(defaultEvent)){
                nextEvent = defaultEvent;
                return;
            }

            nextEvent = events[random.nextInt(events.length)];
            return;
        }

        if(!currentEvent.condition()) return;
        for(Player player: main.getGameLevel().getPlayers().values()){
            currentEvent.handle(player);
        }
    }

}

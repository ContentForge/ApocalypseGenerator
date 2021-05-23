package ru.dragonestia.apocalypse.task;

import cn.nukkit.scheduler.PluginTask;
import ru.dragonestia.apocalypse.Apocalypse;
import ru.dragonestia.apocalypse.storms.GlobalEvents;

public class StormTask extends PluginTask<Apocalypse> {

    private final GlobalEvents events;

    public StormTask(Apocalypse owner, GlobalEvents events) {
        super(owner);
        this.events = events;
    }

    @Override
    public void onRun(int i) {
        events.handle();
    }

}

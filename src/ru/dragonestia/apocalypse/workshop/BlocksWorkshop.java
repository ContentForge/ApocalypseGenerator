package ru.dragonestia.apocalypse.workshop;

import ru.dragonestia.expo.ExpoCraft;
import ru.dragonestia.expo.player.PlayerData;
import ru.dragonestia.expo.workshop.Workshop;

public class BlocksWorkshop extends Workshop {

    public BlocksWorkshop(ExpoCraft main) {
        super("t_blocks", "Блоки", main);
    }

    @Override
    public boolean checkOpenCondition(PlayerData playerData) {
        return true;
    }

    @Override
    protected void open(PlayerData playerData) {
        sendCraftForm(playerData);
    }

}

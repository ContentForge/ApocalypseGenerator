package ru.dragonestia.apocalypse.workshop;

import ru.dragonestia.expo.ExpoCraft;
import ru.dragonestia.expo.player.PlayerData;
import ru.dragonestia.expo.workshop.Workshop;

public class MaterialsWorkshop extends Workshop {

    public MaterialsWorkshop(ExpoCraft main) {
        super("t_materials", "Материалы", main);
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

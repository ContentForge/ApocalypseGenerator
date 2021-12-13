package ru.dragonestia.apocalypse.workbench;

import cn.nukkit.block.Block;
import ru.dragonestia.expo.player.PlayerData;
import ru.dragonestia.expo.workbench.Workbench;

public class BookWorkbench extends Workbench {

    public static final String WORKBENCH_ID = "book";

    public BookWorkbench() {
        super(WORKBENCH_ID, "Пособие по выживанию");
    }

    @Override
    public void open(PlayerData playerData, Block block) {
        sendStudyForm(playerData);
    }

}

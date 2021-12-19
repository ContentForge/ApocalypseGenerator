package ru.dragonestia.apocalypse.item;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import cn.nukkit.math.Vector3;
import ru.dragonestia.apocalypse.expo.workbench.BookWorkbench;
import ru.dragonestia.expo.Expo;
import ru.dragonestia.expo.item.AdvItem;

public class GuideItem extends Item implements AdvItem {

    public GuideItem(Integer meta, int count) {
        super(ApocalypseID.GUIDE_BOOK, meta, count, "Guide");
    }

    @Override
    public String getRuName() {
        return "Пособие по выживанию";
    }

    @Override
    public String getIcon() {
        return "textures/items/book/guide";
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public boolean onClickAir(Player player, Vector3 directionVector) {
        Expo.getInstance().getWorkbenchManager().get(BookWorkbench.WORKBENCH_ID).sendToPlayer(player);
        return true;
    }

}

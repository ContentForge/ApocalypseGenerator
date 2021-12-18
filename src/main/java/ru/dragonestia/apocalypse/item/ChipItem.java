package ru.dragonestia.apocalypse.item;

import cn.nukkit.item.Item;
import ru.dragonestia.expo.item.AdvItem;

public class ChipItem extends Item implements AdvItem {

    public ChipItem(Integer meta, int count) {
        super(ApocalypseID.CHIP, 0, count, "Chip");
    }

    @Override
    public String getRuName() {
        return "Микросхема";
    }

    @Override
    public String getIcon() {
        return "textures/items/upgrade/chip";
    }

    @Override
    public int getMaxStackSize() {
        return 4;
    }

}

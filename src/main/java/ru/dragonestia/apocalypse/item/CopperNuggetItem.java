package ru.dragonestia.apocalypse.item;

import cn.nukkit.item.Item;
import ru.dragonestia.expo.item.AdvItem;

public class CopperNuggetItem extends Item implements AdvItem {

    public CopperNuggetItem(Integer meta, int count) {
        super(ApocalypseID.COPPER_NUGGET, meta, count, "Copper nugget");
    }

    @Override
    public String getRuName() {
        return "Медный самородок";
    }

    @Override
    public String getIcon() {
        return "textures/items/nugget/copper";
    }

}

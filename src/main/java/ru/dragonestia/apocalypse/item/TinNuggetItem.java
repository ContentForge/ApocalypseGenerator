package ru.dragonestia.apocalypse.item;

import cn.nukkit.item.Item;
import ru.dragonestia.expo.item.AdvItem;

public class TinNuggetItem extends Item implements AdvItem {

    public TinNuggetItem(Integer meta, int count) {
        super(ApocalypseID.TIN_NUGGET, meta, count, "Tin nugget");
    }

    @Override
    public String getRuName() {
        return "Оловянный самородок";
    }

    @Override
    public String getIcon() {
        return "textures/items/nugget/tin";
    }

}

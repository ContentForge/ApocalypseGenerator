package ru.dragonestia.apocalypse.item;

import cn.nukkit.item.Item;
import ru.dragonestia.expo.item.AdvItem;

public class ScrapItem extends Item implements AdvItem {

    public ScrapItem(Integer meta, int count) {
        super(ApocalypseID.SCRAP, meta, count, "Scrap");
    }

    @Override
    public String getRuName() {
        return "Металлолом";
    }

    @Override
    public String getIcon() {
        return "textures/items/scrap";
    }

}

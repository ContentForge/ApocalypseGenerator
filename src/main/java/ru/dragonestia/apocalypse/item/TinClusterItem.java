package ru.dragonestia.apocalypse.item;

import cn.nukkit.item.Item;
import ru.dragonestia.expo.item.AdvItem;

public class TinClusterItem extends Item implements AdvItem {

    public TinClusterItem(Integer meta, int count) {
        super(ApocalypseID.TIN_CLUSTER, meta, count, "Tin cluster");
    }

    @Override
    public String getRuName() {
        return "Оловянная руда";
    }

    @Override
    public String getIcon() {
        return "textures/items/cluster/tin";
    }

}

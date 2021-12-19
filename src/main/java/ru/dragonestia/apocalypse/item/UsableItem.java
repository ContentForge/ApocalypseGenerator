package ru.dragonestia.apocalypse.item;

import cn.nukkit.Player;
import cn.nukkit.math.Vector3;

public interface UsableItem {

    void setCount(int value);

    int getCount();

    void use(Player player);

}

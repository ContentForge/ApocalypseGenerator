package ru.dragonestia.apocalypse.item;

import cn.nukkit.Player;
import cn.nukkit.math.Vector3;

public interface UsableItem {

    void setCount(int value);

    int getCount();

    void use(Player player);

    default boolean onClickAir(Player player, Vector3 ignore) {
        use(player);
        if(player.isCreative()) setCount(getCount() - 1);
        return true;
    }

}

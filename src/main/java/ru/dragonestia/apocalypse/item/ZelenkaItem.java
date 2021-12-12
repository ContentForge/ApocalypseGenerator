package ru.dragonestia.apocalypse.item;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import cn.nukkit.potion.Effect;
import ru.dragonestia.apocalypse.Apocalypse;
import ru.dragonestia.apocalypse.player.PlayerData;
import ru.dragonestia.expo.item.AdvItem;

public class ZelenkaItem extends Item implements AdvItem, UsableItem {

    public ZelenkaItem(Integer meta, int count) {
        super(ApocalypseID.ZELENKA, meta, count, "Zelenka");
    }

    @Override
    public String getRuName() {
        return "Радиопротектор Зеленка";
    }

    @Override
    public String getIcon() {
        return "textures/items/rad/zelenka";
    }

    @Override
    public void use(Player player) {
        PlayerData playerData = Apocalypse.getInstance().getPlayerManager().get(player);

        playerData.reduceRadiationLevel(500000);
        playerData.playSound("dragonestia.rad.zelenka", 0.3f, 1f);

        player.addEffect(Effect.getEffect(Effect.NIGHT_VISION).setDuration(20 * 5).setVisible(false));
    }

}

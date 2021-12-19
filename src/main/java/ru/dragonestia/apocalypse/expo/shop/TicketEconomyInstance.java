package ru.dragonestia.apocalypse.expo.shop;

import cn.nukkit.Player;
import ru.dragonestia.apocalypse.player.PlayerManager;
import ru.dragonestia.expo.shop.EconomyInstance;

public class TicketEconomyInstance implements EconomyInstance {

    public static final String ID = "ticket";

    private final PlayerManager playerManager;

    public TicketEconomyInstance(PlayerManager playerManager){
        this.playerManager = playerManager;
    }

    @Override
    public void addMoney(Player player, int value) {
        playerManager.get(player).getPocketMoney().addMoney(value);
    }

    @Override
    public void takeMoney(Player player, int value) {
        playerManager.get(player).getPocketMoney().reduceMoney(value);
    }

    @Override
    public int getMoney(Player player) {
        return playerManager.get(player).getPocketMoney().getMoney();
    }

}

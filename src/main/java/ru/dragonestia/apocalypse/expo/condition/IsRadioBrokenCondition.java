package ru.dragonestia.apocalypse.expo.condition;

import ru.dragonestia.apocalypse.Apocalypse;
import ru.dragonestia.expo.condition.Condition;
import ru.dragonestia.expo.player.PlayerData;

public class IsRadioBrokenCondition extends Condition {

    private final Apocalypse main;

    public IsRadioBrokenCondition(Apocalypse main) {
        super("radio_broken");

        this.main = main;
    }

    @Override
    public Result handle(PlayerData playerData, String[] strings) throws IndexOutOfBoundsException {
        return new Result("Радио сломано", main.getPlayerManager().get(playerData.getPlayer()).getRadio().isBroken());
    }

}

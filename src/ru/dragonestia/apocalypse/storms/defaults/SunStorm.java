package ru.dragonestia.apocalypse.storms.defaults;

import cn.nukkit.Player;
import cn.nukkit.level.Sound;
import cn.nukkit.math.Vector3;
import cn.nukkit.potion.Effect;
import ru.dragonestia.apocalypse.level.biome.ApocalypseBiome;
import ru.dragonestia.apocalypse.storms.GlobalEventBase;

public class SunStorm extends GlobalEventBase {

    private long soundInterval;

    @Override
    public void init() {
        soundInterval = getPeriod() - 10;
    }

    @Override
    public String getName() {
        return "Экстримальная буря";
    }

    @Override
    public int getPeriod() {
        return 5 * 60;
    }

    @Override
    public String generateTitleMessage(Player player, int lastTime) {
        return "§l§cЭкст. буря§r§4 [??:??]";
    }

    @Override
    public String getNoticeStartMessage() {
        return "ВНИМАНИЕ! Замечено сильное скопление грозовых облоков! Ищите укрытие, если жить хотите!";
    }

    @Override
    public String getStartMessage() {
        return "ВНИМАНИЕ! Идет экстримальная буря! НЕ ВЫЛАЗЬТЕ НАРУЖУ!!!";
    }

    @Override
    public String getNoticeEndMessage() {
        return "Буря начинает утихать.";
    }

    @Override
    public String getEndMessage() {
        return "Все, буря закончилась.";
    }

    @Override
    public boolean condition() {
        soundInterval--;
        return true;
    }

    @Override
    public void handle(Player player) {
        if(player.y < 30) return;

        if(!player.isSurvival()) return;
        if(soundInterval > 0) playSound(player, player.add(new Vector3(0, 2, 0)), Sound.ELYTRA_LOOP, (float) (player.y / 55), 1f);

        if(player.y < 55) return;
        player.setMotion(new Vector3((random.nextFloat() - .5f)/1.5, 0, (random.nextFloat() - .5f)/1.5));
        if(!ApocalypseBiome.checkUp(player, player.level)) return;

        player.addEffect(Effect.getEffect(Effect.WITHER)
                .setDuration(20 * 5)
                .setAmplifier(1)
                .setVisible(false));
        player.addEffect(Effect.getEffect(Effect.NAUSEA)
                .setDuration(20 * 15)
                .setVisible(false));
        player.setMotion(new Vector3((random.nextFloat() - .5f)*2, random.nextFloat() + 1, (random.nextFloat())*2));
        player.sendTip("Приятного полета :D");
    }

}

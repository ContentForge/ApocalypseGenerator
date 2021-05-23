package ru.dragonestia.apocalypse.storms.defaults;

import cn.nukkit.Player;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.level.Sound;
import cn.nukkit.math.Vector3;
import cn.nukkit.potion.Effect;
import ru.dragonestia.apocalypse.storms.GlobalEventBase;


public class ShakeStorm extends GlobalEventBase {

    @Override
    public void init() {

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public int getPeriod() {
        return 3 * 60 + 30;
    }

    @Override
    public String generateTitleMessage(Player player, int lastTime) {
        if(lastTime - 70 > 0) return "§l§cЗемлетрясение§r§4 [??:??]";

        return "§l§cЗемлетрясение§r§4 ["+(lastTime/60)+":" +(lastTime%60)+"]";
    }

    @Override
    public String getNoticeStartMessage() {
        return "Наблюдается высокая сейсмическая активность в районе! Покиньте глубокие тонели! Не забирайтесь на высокие постройки!";
    }

    @Override
    public String getStartMessage() {
        return "Держитесь! Приближается сильное землятрясение!";
    }

    @Override
    public String getNoticeEndMessage() {
        return "Сейсмическая аквтивность в районе начинает спадать. Держитесь!";
    }

    @Override
    public String getEndMessage() {
        return "Все, землетрясение закончилось.";
    }

    @Override
    public boolean condition() {
        return true;
    }

    @Override
    public void handle(Player player) {
        playSound(player, player, Sound.AMBIENT_CAVE, 3F, 0.5f);
        if(!player.isSurvival()) return;

        float force = 0f;
        if(player.y < 45){
            force = (45 / (float) player.y - 1);

            EntityDamageEvent event = new EntityDamageEvent(player, EntityDamageEvent.DamageCause.CUSTOM, force);
            player.setLastDamageCause(event);
            player.attack(event);

            player.addEffect(Effect.getEffect(Effect.NAUSEA)
                    .setDuration(20 * 15)
                    .setAmplifier((int) (force*5))
                    .setVisible(false));

            player.setMotion(new Vector3((random.nextFloat() - 0.5f) * force*5, 0.05f, (random.nextFloat() - 0.5f) * force*5));
        }else if(player.y > 65){
            force = (float) (70 / player.y - 1) * 10f;
            player.setMotion(new Vector3((random.nextFloat() - 0.5f) * force, 0.05f, (random.nextFloat() - 0.5f) * force));
            player.addEffect(Effect.getEffect(Effect.NAUSEA)
                    .setDuration(20 * 15)
                    .setAmplifier((int) force)
                    .setVisible(false));

            EntityDamageEvent event = new EntityDamageEvent(player, EntityDamageEvent.DamageCause.CUSTOM, force/10f);
            player.setLastDamageCause(event);
            player.attack(event);
        }else player.setMotion(new Vector3((random.nextFloat() - 0.5f) /10f, 0.05f, (random.nextFloat() - 0.5f) /10f));

        checkSunBurn(player);
    }

}

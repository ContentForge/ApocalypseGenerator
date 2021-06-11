package ru.dragonestia.apocalypse.listener;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.potion.Effect;

public class IllnessListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDamageTaken(EntityDamageEvent event){
        if(event.isCancelled() || !(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        if(player.getLevel().equals(player.getServer().getDefaultLevel())) return;

        Effect effect;
        fallDamage:
        if(event.getCause().equals(EntityDamageEvent.DamageCause.FALL)){
            if(event.getDamage() < 5) break fallDamage;
            effect = Effect.getEffect(Effect.SLOWNESS).setDuration(20 * 120).setVisible(false);
            if(event.getDamage() < 7){
                player.sendTip("Вы вывихнули ногу");
                player.addEffect(effect);
            }
            if(event.getDamage() < 10){
                player.sendTip("Вы сильно повредили ногу");
                player.addEffect(effect.setAmplifier(1));
            }
            if(event.getDamage() < 20){
                player.sendTip("Вы сломали себе ногу");
                player.addEffect(effect.setAmplifier(3).setDuration(20 * 180));
            }
        }
        effect = null;

        if(player.getHealth() - event.getFinalDamage() < 10){
            effect = Effect.getEffect(Effect.WEAKNESS).setAmplifier(0).setDuration(20 * 30);
        }
        if(player.getHealth() - event.getFinalDamage() < 7){
            effect = Effect.getEffect(Effect.WEAKNESS).setAmplifier(1).setDuration(20 * 40);
        }
        if(player.getHealth() - event.getFinalDamage() < 5){
            effect = Effect.getEffect(Effect.WEAKNESS).setAmplifier(2).setDuration(20 * 50);
        }

        if(event == null) return;
        player.addEffect(effect.setVisible(false));
    }
}

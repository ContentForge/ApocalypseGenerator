package ru.dragonestia.apocalypse.commands;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import ru.contentforge.formconstructor.form.ModalForm;
import ru.dragonestia.apocalypse.Apocalypse;
import ru.dragonestia.apocalypse.task.TeleportingTask;

public class SpawnCommand extends Command {

    private final Apocalypse main;

    public SpawnCommand(Apocalypse main) {
        super("spawn", "Телепортация на стартовую локацию");

        this.main = main;
        main.getServer().getScheduler().scheduleRepeatingTask(main.getTeleportingTask(), 20);
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if(commandSender instanceof Player){
            sendMainForm((Player) commandSender);
            return true;
        }

        commandSender.sendMessage("Данную команду можно водить только в игре.");
        return false;
    }

    public void sendMainForm(Player player){
        if(player.getLevel().getName().equals("lobby")){
            player.sendMessage("§cВы уже находитесь на стартовой локации.");
            return;
        }

        new ModalForm("Предупреждение", "Для телепортации на стартовую локацию вам потребуется §3"+ TeleportingTask.TELEPORT_DELAY +" секунд§f. В это время §7вы не должны получить урон§f, иначе телепортация будет отменена!\n" +
                "Также не забудьте установить спальный мешок, если хотите вернуться на это же место, иначе при выходе из стартовой локации вы появитесь в случайно месте.")
                .setPositiveButton("Начать процесс телепортации")
                .setNegativeButton("Отмена")
                .setHandler((p, data) -> {
                    if(!data) return;

                    main.getTeleportingTask().addPlayer(player);
                }).send(player);
    }

}

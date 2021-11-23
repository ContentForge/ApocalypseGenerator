package ru.dragonestia.apocalypse.commands;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import ru.dragonestia.apocalypse.player.PlayerManager;

public class JokeCommand extends Command {

    private final PlayerManager playerManager;

    public JokeCommand(PlayerManager playerManager) {
        super("joke", "Joke :D", "", new String[0]);

        this.playerManager = playerManager;
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if(commandSender instanceof Player){
            execute((Player) commandSender, strings);
            return true;
        }
        return false;
    }

    private void execute(Player player, String[] args){
        if(!player.isOp() || args.length < 2) return;

        Player target = player.getServer().getPlayer(args[0]);
        if(target == null) return;

        String song = "";
        switch (args[1]){
            case "0":
                song = "dragonestia.horror.crying";
                break;
            case "1":
                song = "dragonestia.horror.heartbeat";
                break;
            case "2":
                song = "dragonestia.horror.laugh";
                break;
        }
        playerManager.get(target).playSound(song, 0.5f, 1f);
    }

}

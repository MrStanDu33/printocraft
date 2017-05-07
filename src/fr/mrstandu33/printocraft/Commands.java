package fr.mrstandu33.printocraft;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class Commands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender Sender, Command command, String s, String[] strings) {

        if (Sender instanceof Player){
            Player p = (Player)Sender;
        }

        return false;
    }
}

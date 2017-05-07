package fr.mrstandu33.printocraft;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;


public class Commands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender Sender, Command prompt, String message, String[] args) {

        if (Sender instanceof Player){
            Player p = (Player)Sender;

            if (prompt.getName().equalsIgnoreCase("poc")){

                if (args.length == 0) {
                    if (p.hasPermission("poc.reload") && p.isOp()) {
                        p.sendMessage("/poc reload : Pour reload Print o' Craft");
                    }
                }
                if (args.length == 1) {
                    if (p.hasPermission("poc.reload") && p.isOp()){
                    if (args[0].equalsIgnoreCase("reload")) {

                        Plugin plugin = p.getServer().getPluginManager().getPlugin("printocraft");
                        p.getServer().getPluginManager().disablePlugin(plugin);
                        p.getServer().getPluginManager().enablePlugin(plugin);
                        p.sendMessage(ChatColor.GREEN+"Plugin Reload");

                    }

                }

            }

        }
    }

        return false;
    }
}

package com.Gamerboy59.bukkit.ucsystem.commands;

import com.Gamerboy59.bukkit.ucsystem.main;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;



public class TeamChatClass implements CommandExecutor {

	private main plugin;
	public TeamChatClass(main plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("tc")) {
			if (sender.hasPermission("crafted.team")) {
				if (args.length == 0) {
					sender.sendMessage("§cVerwendung:§b /tc [Nachricht]");;
				}
				if (args.length >= 1) {			
				        String message = "";
				     
				        for (int i = 0; i < args.length; i++) {
				          message = message + args[i] + " ";
				        }
				        for (Player team : Bukkit.getOnlinePlayers())
				          if ((team.hasPermission("crafted.team")) || (team.isOp())) {
				            message = message.replaceAll("&", "§");
				            team.sendMessage("§7[§cTC§7] §a" + sender.getName() + "§7:§f " + message);
				            if (this.plugin.getConfig().get("Konsole-TeamChat") != null) {
				            	boolean status = this.plugin.getConfig().getBoolean("Konsole-TeamChat");
				            	if (status == true) {
				            	System.out.println("[TeamChat] " + sender.getName() + ": " + message);
				            	} 
				            }             							
				 }	
				}
			}
		}
		
		return false;
	}
}

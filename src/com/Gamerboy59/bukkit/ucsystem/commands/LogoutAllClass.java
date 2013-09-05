package com.Gamerboy59.bukkit.ucsystem.commands;


import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;



public class LogoutAllClass implements CommandExecutor {

	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (label.equalsIgnoreCase("logoutall")) {
			if (sender.hasPermission("crafted.logoutall")) {
				if (args.length == 0) {
					sender.sendMessage("§cVerwendung:§b /logoutall [Grund]");
				}
				if (args.length >= 1) {
					   String Grund = "";
				       for (int i = 0; i < args.length; i++) {
				            Grund = Grund + args[i] + " ";
				       }       
				       for (Player target : Bukkit.getOnlinePlayers()) {
				    	  if (target == sender || target.hasPermission("crafted.team")) {
				    		  continue;
				    	  }  
				    	  target.kickPlayer("§6Du wurdest von einem §cAdmin§6 ausgeloggt. Grund: §c" + Grund);
				    	}
				       Bukkit.broadcastMessage("§b[§6UCS§b] §aEs wurden alle Spieler ausgeloggt!");     
				}
			} else {
				sender.sendMessage("§cDu hast keinen Zugriff auf diesen Befehl!");
			}
		}
		
		return false;
	}
}

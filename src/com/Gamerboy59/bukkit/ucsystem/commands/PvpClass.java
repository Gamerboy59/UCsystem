package com.Gamerboy59.bukkit.ucsystem.commands;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class PvpClass implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (label.equalsIgnoreCase("pvp")) {
			if (sender.hasPermission("crafted.pvp")) {
				if (args.length == 0) {
					sender.sendMessage("§cVerwendung:§b /pvp [An|Aus]");
				}
				if (args.length == 1) {
					if (args[0].equalsIgnoreCase("an")) {
						for (World w : Bukkit.getWorlds()) {
							w.setPVP(true);
						}
						Bukkit.broadcastMessage("§b[§6CTS§b] §aPvP wurde aktiviert!");						
					}
					if (args[0].equalsIgnoreCase("aus")) {
						for (World w : Bukkit.getWorlds()) {
							w.setPVP(false);
						}
						Bukkit.broadcastMessage("§b[§6CTS§b] §aPvP wurde deaktiviert!");						
					}
				}
			} else {
				sender.sendMessage("§cDu hast keinen Zugriff auf diesen Befehl!");
			}
		}
		
		
		return false;
	}
}

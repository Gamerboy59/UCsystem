package com.Gamerboy59.bukkit.ucsystem.commands;

import com.Gamerboy59.bukkit.ucsystem.main;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;


public class GlobalMuteClass implements CommandExecutor {

	private main plugin;
	public GlobalMuteClass(main plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	
		if (label.equalsIgnoreCase("globalmute")) {
			if (sender.hasPermission("crafted.globalmute")) {
				if (args.length == 0) {
					if (this.plugin.getConfig().get("Mute") != null) {
						boolean st = main.mute;
						if (st == true) {
							main.mute = false;
							Bukkit.broadcastMessage("§b[§6CTS§b] §cGlobalmute wurde deaktiviert!");
						} else if (st == false) {
							main.mute = true;
							Bukkit.broadcastMessage("§b[§6CTS§b] §cGlobalmute wurde aktiviert!");
						}
					} else {
							main.mute = true;
							Bukkit.broadcastMessage("§b[§6CTS§b] §cGlobalmute wurde aktiviert!");
					}
				}
			} else {
			 sender.sendMessage("§cDu hast keinen Zugriff auf diesen Befehl!");
			}
		}
		
		return false;
	}
}

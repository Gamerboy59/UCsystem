package com.Gamerboy59.bukkit.ucsystem.commands;

import java.io.File;
import java.io.IOException;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class TabPexClass implements CommandExecutor {

	
	File f = new File("plugins/CraftedSystem/", "tabname.yml");
	FileConfiguration tabname = YamlConfiguration.loadConfiguration(f);
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	
		if (label.equalsIgnoreCase("tabname")) {
			if (sender.hasPermission("crafted.tabname")) {
			if (args.length == 0) {
				sender.sendMessage("§cVerwendung:§b /tabname [Player] [Color-Code]");
			}
			if (args.length == 1) {
				sender.sendMessage("§cVerwendung:§b /tabname [Player] [Color-Code]");
			}
			if (args.length == 2) {
				
				if (f.exists() == false) {
					try {
						f.createNewFile();
					} catch (IOException e) {
						sender.sendMessage("§cKonfiguration konnte nicht erstellt werden!");
					}
				}
				
				Player user = Bukkit.getPlayer(args[0]);
				String color = ChatColor.translateAlternateColorCodes('&', args[1]);
				String name = color + user.getName();
				if (name.length() >= 16) {
					 name = name.substring(0, 16);
				}
				tabname.set(user.getName(), name);
				try {
					tabname.save(f);
				} catch (IOException e) {
					sender.sendMessage("§cDie Farbe konnte nicht gespeichert werden!");
				}
				user.setPlayerListName(name);
				user.sendMessage("§bDeine Farbe in der Player-Liste wurde geändert!");
			}
		 }
		}
		
		
		
		return false;
	}

}

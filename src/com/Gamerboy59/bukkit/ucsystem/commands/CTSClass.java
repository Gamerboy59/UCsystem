package com.Gamerboy59.bukkit.ucsystem.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import org.bukkit.ChatColor;

public class CTSClass implements CommandExecutor {


	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (label.equalsIgnoreCase("system") || label.equalsIgnoreCase("cts") || label.equalsIgnoreCase("dev") || label.equalsIgnoreCase("me")) {
			if (args.length == 0) {
				sender.sendMessage(ChatColor.GREEN + "[]==============" + ChatColor.GOLD + " System " + ChatColor.GREEN + "==========[]");
				sender.sendMessage(ChatColor.DARK_AQUA + "Programmiert von " + ChatColor.DARK_RED + "[Admin]" + ChatColor.YELLOW + " Gamerboy59");
			}
		}
		
		return false;
	}
	
}

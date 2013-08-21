package com.Gamerboy59.bukkit.ucsystem.commands;

import com.Gamerboy59.bukkit.ucsystem.main;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SlotsClass implements CommandExecutor {


	private main plugin;
	public SlotsClass(main plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (label.equalsIgnoreCase("slots")) {
			if (sender.hasPermission("crafted.slots")) {
				if (args.length == 0) {
					sender.sendMessage("§cVerwendung:§b /slots <Zahl>");
				} else if (args.length == 1) {
					int slots = Integer.parseInt(args[0]);
					this.plugin.getConfig().set("slots", slots);
					main.slots = slots;
					this.plugin.saveConfig();
					sender.sendMessage("§aDie Slots wurde erfolgreich auf§6 " + slots + "§a gesetzt!");
				}
			} else {
				sender.sendMessage("§cDu hast keinen Zugriff auf diesen Befehl!");
			}
		}
		
		return false;
	}
}

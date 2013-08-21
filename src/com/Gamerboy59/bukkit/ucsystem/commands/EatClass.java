package com.Gamerboy59.bukkit.ucsystem.commands;

import java.util.HashMap;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EatClass implements CommandExecutor { 
	

	private String KeineRechte = "§cDu hast keinen Zugriff auf diesen Befehl!";
	
	HashMap<String, Long> cooldowns = new HashMap<String, Long>();

	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (label.equalsIgnoreCase("essen")) {
			if (sender.hasPermission("crafted.essen")) {
				if (args.length == 0) {
					Player p = null;
					if (sender instanceof Player) {
						p = (Player) sender;
					} else {
						sender.sendMessage("§cDu kannst deine Essens-Anzeige nicht heilen!");
					}
					if (p == null) {
						return false;
					}
					long time = System.currentTimeMillis();
					String name = p.getName();
					if (cooldowns.containsKey(name) == true) {
						long lastusage = cooldowns.get(p.getName());
						if (lastusage + 6000*1000 < time) {
							 p.setFoodLevel(20);
							p.sendMessage("§b[§6CTS§b] §aDein Hunger wurde gestillt!");
							cooldowns.put(p.getName(), time);
						} else {
							if ((((lastusage + 6000*1000 - time) / 1000) / 100) == 60) {
								p.sendMessage("§6[CTS]§a Es verbleiben §61 §aStunde");	
							} else {
							p.sendMessage("§6[CTS]§a Es verbleiben §6" + (((lastusage + 6000*1000 - time) / 1000) / 100) + " §aMinuten");
							}
						}
					} else {
						 p.setFoodLevel(20);
						p.sendMessage("§b[§6CTS§b] §aDein Hunger wurde gestillt!");
						cooldowns.put(p.getName(), time);
					}
				} 
				} else {
					sender.sendMessage(KeineRechte);
				}
			} 
		
		return false;
	}
}
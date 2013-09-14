package com.Gamerboy59.bukkit.ucsystem.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;



public class GiveAllClass implements CommandExecutor {

	private String KeineRechte = "§cDu hast keinen Zugriff auf diesen Befehl!";
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (label.equalsIgnoreCase("giveall")) {
			if (sender.hasPermission("crafted.giveall")) {
				if (args.length == 0) {
					sender.sendMessage(ChatColor.RED + "Verwendung: " + ChatColor.AQUA + "/giveall [Id] [Anzahl]");
				}
				if (args.length == 1) {
					sender.sendMessage(ChatColor.RED + "Verwendung: " + ChatColor.AQUA + "/giveall [Id] [Anzahl]");
				}
				if (args.length == 2) {
					int id = Integer.parseInt(args[0]);
					int anzahl = Integer.parseInt(args[1]);
					
					for (Player online : Bukkit.getOnlinePlayers()) {
						try {
						online.getInventory().addItem(new ItemStack(id, anzahl));
						} catch (Exception e) {
							sender.sendMessage(ChatColor.RED + "Ungültige Item-Id!");
							return false;
						}
					}
					Bukkit.broadcastMessage(ChatColor.AQUA + "[" + ChatColor.GOLD + "UCS" + ChatColor.AQUA + "] " + ChatColor.GREEN + "Jeder Spieler hat" + ChatColor.GOLD + anzahl + ChatColor.GREEN + "mal " + ChatColor.GOLD + id + ChatColor.GREEN + " erhalten!");
				} else if (args.length == 3) {
					int id = Integer.parseInt(args[0]);
					int anzahl = Integer.parseInt(args[1]);
					int olz = Integer.parseInt(args[2]);
					
					for (Player online : Bukkit.getOnlinePlayers()) {
						try {
						online.getInventory().addItem(new ItemStack(id, anzahl, (short) olz));
						} catch (Exception e) {
							sender.sendMessage(ChatColor.RED + "Ungültige Item-Id!");
							return false;
						}
					}
					Bukkit.broadcastMessage(ChatColor.AQUA + "[" + ChatColor.GOLD + "UCS" + ChatColor.AQUA + "] " + ChatColor.GREEN + "Jeder Spieler hat " + ChatColor.GOLD + anzahl + ChatColor.GREEN + "mal " + ChatColor.GOLD + id + ChatColor.GREEN + " erhalten!");
				}
			} else {
				sender.sendMessage(KeineRechte);
			}
		}
		
	return false;	
	}
}

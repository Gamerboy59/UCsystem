package com.Gamerboy59.bukkit.ucsystem.commands;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;



public class SupportClass implements CommandExecutor {

	
	static public HashMap<String, String> support = new HashMap<String, String>();
	static public HashMap<String, String> ticket = new HashMap<String, String>();	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (label.equalsIgnoreCase("support")) {
			if (args.length == 0) {
				if (((!support.containsKey(sender.getName())) && (!ticket.containsKey(sender.getName()))) || (sender.hasPermission("crafted.team"))) {
					if (!sender.hasPermission("crafted.team")) {
						 sender.sendMessage("§6[Support] §aDeine Supportanfrage wurde verschickt.");
				         sender.sendMessage("§aDas Team wird sich schnell darum kümmern!");
				         ticket.put(sender.getName(), sender.getName());
				         for (Player team : Bukkit.getOnlinePlayers()) {
				        	 if (team.hasPermission("crafted.team")) {
				        		 team.sendMessage("§aDer Spieler§6 " + sender.getName() + "§a benötigt Support!");
				        	 }
				         }
					} else {
						if (support.containsKey(sender.getName()) == true) {
							String username = support.get(sender.getName());
							Player other = Bukkit.getPlayer(username);
					        sender.sendMessage("§6[Support] §aDu hast den SupportChat verlassen!");
					        other.sendMessage("§6[Support] §aDer SupportChat wurde geschlossen!");
					        support.remove(other.getName());
					        support.remove(sender.getName());
						} else {
							sender.sendMessage("§6[Support]§a Du bist in keinem SupportChat!");
						}
					}
				} else {
					sender.sendMessage("§6[Support]§a Du bekommst berreits Support!");
				}
			} else if (args.length == 1) {
				if (sender.hasPermission("crafted.team")) {
					if (Bukkit.getPlayer(args[0]) != null) {
						if (ticket.get(args[0]) != null) {
							Player user = Bukkit.getPlayer(args[0]);
							
					         String cc = "";
					          for (int i = 0; i < 120; i++) {
					            cc = cc + " ";
					            sender.sendMessage(cc);
					            user.sendMessage(cc);
					          }
					          user.sendMessage("§6[Support] §aDu bist nun im SupportChat mit §6" + sender.getName() + " §a!");
					          sender.sendMessage("§6[Support] §aDu bist nun im SupportChat mit §6" + user.getName() + " §a!");
					          ticket.remove(user.getName());
					          support.put(user.getName(), sender.getName());
					          support.put(sender.getName(), user.getName());
						} else {
							sender.sendMessage("§6[Support] §aDieser Spieler benötigt keinen Support!");
						}
					} else {
						sender.sendMessage("§6[Support] §aDieser Spieler ist nicht Online!");
					}
				} else {
					sender.sendMessage("§cVerwendung:§b /support");
				}
			}
		}
		
		return false;
	}


}

package com.Gamerboy59.bukkit.ucsystem.commands;

import com.Gamerboy59.bukkit.ucsystem.utils.PermissionsExHandler;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TeamClass implements CommandExecutor {

	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (label.equalsIgnoreCase("team")) {
			if (args.length == 0) {
				String[] owner = PermissionsExHandler.getOwners();
				String[] admin = PermissionsExHandler.getAdmins();
				String[] dev = PermissionsExHandler.getDevs();
				String[] mod = PermissionsExHandler.getMods();
				String[] supp = PermissionsExHandler.getSupps();
				String[] ca = PermissionsExHandler.getCA();
				
				sender.sendMessage("§a[]============§6 Team-Mitglieder§a ============[]");
				sender.sendMessage("§4Owner: " + teamOnline(owner));
				sender.sendMessage("§cAdmin: " + teamOnline(admin));
				sender.sendMessage("§3Developer" + teamOnline(dev));
				sender.sendMessage("§5Moderator: " + teamOnline(mod));
				sender.sendMessage("§aSupporter: " + teamOnline(supp));
				sender.sendMessage("§2Chat-Admin: " + teamOnline(ca));
			}
		}
		
		return false;
	}
	
	
	public String teamOnline(String[] player) {

		 String[] stringArray = player;
		    String returns = "";
		    for (int i = 0; i < stringArray.length; i++) {
		    	if (Bukkit.getPlayer(stringArray[i]) != null) {
		    		if (i >= stringArray.length-1) {
			    		returns = returns + "§e" + stringArray[i];
		    		} else {
			    		returns = returns + "§e" + stringArray[i] + " §b| ";
		    		}
		    	} else {
		    		if (i >= stringArray.length-1) {
			    		returns = returns + "§7" + stringArray[i];
		    		} else {
			    		returns = returns + "§7" + stringArray[i] + " §b| ";
		    		}
		    	}
		    }
		    return returns;
		
	}
}

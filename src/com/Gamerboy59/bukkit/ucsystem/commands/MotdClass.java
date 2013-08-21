package com.Gamerboy59.bukkit.ucsystem.commands;

import com.Gamerboy59.bukkit.ucsystem.main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MotdClass implements CommandExecutor {

	private main plugin;
	public MotdClass(main plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	
		if (label.equalsIgnoreCase("motd")) {
			if (sender.hasPermission("crafted.motd")) {
				if (args.length == 0) {
					sender.sendMessage("§cVerwendung:§b /motd [Text]");
				} else if (args.length >= 1) {
					String motd = "";
					for (int i = 0; i < args.length; i++) {
						motd += " " + args[i];
					}
					this.plugin.getConfig().set("motd", motd);
					main.motd = motd;
					this.plugin.saveConfig();
					sender.sendMessage("§aDer Motd wurde erfolgreich gesetzt!");
				}
			} else {
				sender.sendMessage("§cDu hast keinen Zugriff auf diesen Befehl!");
			}
		}
		
		return false;	
	}
}

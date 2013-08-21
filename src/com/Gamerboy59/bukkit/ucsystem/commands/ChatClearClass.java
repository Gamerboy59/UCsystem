package com.Gamerboy59.bukkit.ucsystem.commands;

import com.Gamerboy59.bukkit.ucsystem.main;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class ChatClearClass implements CommandExecutor {
	private main plugin;
	public ChatClearClass(main plugin) {
		this.plugin = plugin;
	}
	private String KeineRechte = "§cDu hast keinen Zugriff auf diesen Befehl!";
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (label.equalsIgnoreCase("cc") || label.equalsIgnoreCase("chatclear")) {
			if (sender.hasPermission("crafted.chatclear")) {
				if (args.length == 0) {
					for (Player target : this.plugin.getServer().getOnlinePlayers()) {
				        String cc = "";
				        for (int i = 0; i < 120; i++) {
				          cc = cc + " ";
				          target.sendMessage(cc);
				        }
				      }
				      this.plugin.getServer().broadcastMessage("§aDer Chat wurde von§6 " + sender.getName() + "§a geleert!");
				} else {
					sender.sendMessage("§cVerwendung:§b /cc");
				}
			} else {
				sender.sendMessage(KeineRechte);
			}
		}
		
		
		return false;
	}

}

package com.Gamerboy59.bukkit.ucsystem.commands;

import java.util.Random;


import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;



public class RandomClass implements CommandExecutor {

	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (label.equalsIgnoreCase("random")) {
			 if (sender.hasPermission("crafted.random")) {
			        Player player = null;
			        Random r = new Random();
			        try {
			          Player[] ps = Bukkit.getOnlinePlayers();
			          if (ps == null) {
			            sender.sendMessage("§bEs sind keine Spieler online!");
			            return true;
			          }
			          int i = r.nextInt(ps.length);
			          player = ps[i];
			          if ((sender.hasPermission("crafted.random.ausgeschlossen")) && (!sender.isOp())) {
			            Bukkit.broadcastMessage("§b[§6UCS§b] §a" + player.getName() + "§a hätte gewonnen, ist aber ausgeschlossen.");
			            return false;
			          }
			        } catch (Exception e) {
			          e.printStackTrace();
			          sender.sendMessage("§b[§6UCS§b] §cEs ist ein Fehler aufgetreten.");
			          return true;
			        }
			        Bukkit.broadcastMessage("§b[§6UCS§b] §6" + player.getName() + "§a hat die Verlosung gewonnen!");
			        return true;
			      }
			      sender.sendMessage("§cDu hast keinen Zugriff auf diesen Befehl!");
			      return true;
			    }
		
		return false;
	}
}

package com.Gamerboy59.bukkit.ucsystem.commands;

import java.util.HashMap;
import java.util.List;

import com.Gamerboy59.bukkit.ucsystem.main;
import com.Gamerboy59.bukkit.ucsystem.mysql.MySQL;
import com.Gamerboy59.bukkit.ucsystem.utils.PermissionsExHandler;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClanClass implements CommandExecutor {

	private main plugin;
	public ClanClass(main plugin) {	
		this.plugin = plugin;

		MySQL sql = this.plugin.getMySQL();
		sql.queryUpdate("CREATE TABLE IF NOT EXISTS clans (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(16), clantag VARCHAR(100), clanusers VARCHAR(100), owner VARCHAR(100))");
		sql.queryUpdate("CREATE TABLE IF NOT EXISTS clanusers (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(16), clantag VARCHAR(100), chattag VARCHAR(100))");
	}
	
	HashMap<Integer, List<Player>> request = new HashMap<Integer, List<Player>>(); // unknown
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		
		if (cmd.getName().equalsIgnoreCase("clan") || label.equalsIgnoreCase("c")) {
			if (args.length == 0) {
				 sender.sendMessage(ChatColor.AQUA + "-------------- Clan-Befehle | Seite 1 --------------");
			     sender.sendMessage(ChatColor.GOLD + "Clan-Befehle auflisten: " + ChatColor.DARK_AQUA + "/clan " + ChatColor.GOLD + "und: " + ChatColor.DARK_AQUA + "/c");
			     sender.sendMessage(ChatColor.GOLD + "/c erstellen [Name] [Tag] :" + ChatColor.AQUA + " erstellt einen Clan");
			     sender.sendMessage(ChatColor.GOLD + "/c verlassen : " + ChatColor.AQUA + "Verlässt den Clan");
			     sender.sendMessage(ChatColor.GOLD + "/c list [Tag] : " + ChatColor.AQUA + "Zeigt die Mitglieder des Clans");
			   //  sender.sendMessage(ChatColor.GOLD + "/c color [colorcode] : " + ChatColor.AQUA + "Changes the Clan color");
			   //  sender.sendMessage(ChatColor.GOLD + "/c invite [Name] : " + ChatColor.AQUA + "Invites somebody to the Clan");
			} else if (args.length == 1) {
				if (args[0].equalsIgnoreCase("verlassen")) {
					MySQL sql = plugin.getMySQL();
					if (sql.isClan(sender.getName())) {
						String clantag = sql.getClanTag(sender.getName());
						if (sql.isClanOwner(clantag, sender.getName())) {
							String clanusers = sql.getClanUsers(clantag);
							String[] list = clanusers.split(",");
							for (String p : list) {
								Player x = Bukkit.getPlayer(p);
								if (x != null) {
									if (x == (Player) sender) {
										sql.removeUser(x);
										continue;
									} else {
										sql.removeUser(x);
									x.sendMessage(ChatColor.AQUA + "Der Clan§6 " + clantag + ChatColor.AQUA + " wurde gelöscht!");
									}
								}
							}
							sender.sendMessage(ChatColor.AQUA + "Du hast erfolgreich den Clan gelöscht!");
							sql.removeClan(clantag);
						}
					} else {
						sender.sendMessage(ChatColor.RED + " Du bist in keinem Clan!");
					}
				} else if (args[0].equalsIgnoreCase("setbase")) {
					MySQL sql = plugin.getMySQL();
					if (sql.isClan(sender.getName())) {
						String tag = sql.getChatTag(sender.getName());
						if (sql.isClanOwner(tag, sender.getName())) {
							//Weis nicht, ob ich das mit MySQL richtig gemacht habe...
							
							Location loc = ((Player)sender).getLocation();
							
							//Hier musst du es dann halt in MySQL speichern.. Da kann ich nicht fiel helfen :(
						}
					}
					
				} else if (args[0].equalsIgnoreCase("base") || args[0].equalsIgnoreCase("bs")){
					MySQL sql = plugin.getMySQL();
					if (sql.isClan(sender.getName())) {
						
						//Dann musst du halt wieder alles aus MySQL auslesen ;)
					}
					
				}
			} else if (args.length == 2) {
				MySQL sql = plugin.getMySQL();
				if (args[0].equalsIgnoreCase("list")) {
					String clantag = args[1];
					String users = sql.getClanUsers(clantag);
					if (users == null) {
						sender.sendMessage(ChatColor.RED + "Der Clan §6" + clantag + ChatColor.RED + " existiert nicht!");
					} else {
					String[] list = users.split(",");
					sender.sendMessage(ChatColor.AQUA + "------------- Clan List | " + clantag + " ---------------");
					for (String ps : list) {
						sender.sendMessage(ChatColor.GOLD + "- " + ps);
					}
					}
				} else if (args[0].equalsIgnoreCase("color")) {
					if (PermissionsExHandler.isOwner((Player) sender) || sender.hasPermission("crafted.team")) {
						String colorc = args[1];
						String color = ChatColor.translateAlternateColorCodes('&', colorc);
						if (sql.isClan(sender.getName())) {
							String tag = sql.getClanTag(sender.getName());
							if (sql.isClanOwner(tag, sender.getName())) {
								
								String users = sql.getClanUsers(tag);
								String[] ausers = users.split(",");
								for (String u : ausers) {
									sql.setChatTag(color + tag, u);
								}
								sender.sendMessage(ChatColor.AQUA + "Die Clan-Farbe wurde erfolgreich geändert!");
								
							} else {
								sender.sendMessage(ChatColor.RED + "Du bist kein Admin des Clans!");
							}
						} else {
							sender.sendMessage(ChatColor.RED + "Du bist in keinem Clan!");
						}
					} else {
						sender.sendMessage(ChatColor.RED + "Du hast keinen Zugriff auf diesen Befehel!");
					}
				} else if (args[0].equalsIgnoreCase("invite")) {
					if (sql.isClan(sender.getName())) {
						String tag = sql.getChatTag(sender.getName());
						if (sql.isClanOwner(tag, sender.getName())) {
							
							
							@SuppressWarnings("unused")
							Player target = Bukkit.getPlayer(args[1]);
							
							
							//more later
							
							
						} else {
							sender.sendMessage(ChatColor.RED + "Du bist kein Admin des Clans!");
						}
					} else {
						sender.sendMessage(ChatColor.RED + "Du bist in keinem Clan!");
					}
				}
			} else if (args.length == 3) {
				if (args[0].equalsIgnoreCase("erstellen")) {
					MySQL sql = plugin.getMySQL();
					if (sql.isClan(sender.getName()) == false) {
						String clanname = args[1];
						String clantag = args[2];
						if (clantag.length() > 4) {
							sender.sendMessage(ChatColor.RED + "Der Clantag ist zu lang , Maximal: " + ChatColor.GOLD + "4 Zeichen");
							return false;
						}
						if (sql.alreadyClan(clantag)) {
							sender.sendMessage(ChatColor.RED + "Diesen Clan gibt es bereits!");
						} else {
						sql.createClan(clanname, clantag, sender.getName());
						if (sql.getClanTag(sender.getName()) != null) {
							sql.queryUpdate("UPDATE clanusers SET clantag='" + clantag + "' chattag='"+clantag+"' WHERE name='" + sender.getName() + "'");
						} else {
							sql.queryUpdate("INSERT INTO clanusers (name, clantag, chattag) VALUES ('" + sender.getName() + "', '"+ clantag +"', '"+ clantag +"')");
						}
						sender.sendMessage(ChatColor.AQUA + "You successfully create the Clan " + clanname + ChatColor.AQUA + " !");
						}
					} else {
						sender.sendMessage(ChatColor.RED + "Du bist bereits in einem Clan!");
					}
				}
			}
		}
		
		return false;
	}

	
}

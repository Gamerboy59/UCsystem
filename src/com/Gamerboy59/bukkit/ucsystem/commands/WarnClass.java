package com.Gamerboy59.bukkit.ucsystem.commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.Gamerboy59.bukkit.ucsystem.main;
import com.Gamerboy59.bukkit.ucsystem.mysql.MySQL;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WarnClass implements CommandExecutor {

	private main plugin;
	public WarnClass(main plugin) {
		this.plugin = plugin;
		
		MySQL sql = this.plugin.getMySQL();
		sql.queryUpdate("CREATE TABLE IF NOT EXISTS warns (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(16), anzahl INT)");

	}
	private String KeineRechte = "§cDu hast keinen Zugriff auf diesen Befehl!";
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	
	if (label.equalsIgnoreCase("warn")) {
		if (sender.hasPermission("crafted.warn")) {
			if (args.length == 0) {
				sender.sendMessage("§cVerwendung:§b /warn [Player] [Grund]");
			} else if (args.length == 1) {
			} else if (args.length >= 2) {
				if (args[0].equalsIgnoreCase("reset")) {
					String name = args[1];
					setWarn(name, 0);
					sender.sendMessage("§9Die Warns wurden erfolgreich von§6 " + name + "§a gelöscht!");
				}
				Player opfer = Bukkit.getPlayer(args[0]);
				String Grund = "";
			    for (int i = 1; i < args.length; i++) {
			       Grund = Grund + args[i] + " ";
			    }    
			    if (opfer != null) {
			    	Bukkit.broadcastMessage("§9Der Spieler §7" + opfer.getName() + " §9wurde von §7" + sender.getName() + " §9gewarnt. Grund: §b" + Grund);	
			    	addWarn(opfer.getName(), 1);
			    	checkWarns(opfer, Grund);
			    } else {
			    	sender.sendMessage("§b[§6CTS§b]§c Dieser Spieler ist nicht Online!");
			    }
			}
		} else {
			sender.sendMessage(KeineRechte);
		}
	}
	
	if (label.equalsIgnoreCase("warns")) {
		if (args.length == 0) {
			int warnungen = getWarns(sender.getName());			
			sender.sendMessage("§a[]================= §6 Warnungen§a ===============[]");
			sender.sendMessage("§aWarns: §e" + warnungen);
		} else if (args.length == 1) {
			int warnungen = getWarns(args[0]);
			if (warnungen == -1) {
				warnungen = 0;
			}
			sender.sendMessage("§a[]============= §6 Warnungen§a ===============[]");
			sender.sendMessage("§aWarns von§7 "+ args[0] + "§a :§6 " + warnungen);
		}
	}
	
	
		return false;
	}
	private void checkWarns(Player opfer, String reason) {
		int warns = getWarns(opfer.getName());
		 switch (warns) {
	       case 1:
	    	   opfer.sendMessage("§b[§6CTS§b]§a Du wurdest gewarnt, Deine Verwarnungen: §6" + warns);
	    	   break;
	       case 2:
	    	   opfer.sendMessage("§b[§6CTS§b]§a Du wurdest gewarnt, Deine Verwarnungen: §6" + warns);
	    	   opfer.kickPlayer("§6Du wurdest von §b§lCrafted §6für §c10 Minuten §6gebannt. Grund: §c" + reason);
	    	   plugin.getServer().dispatchCommand(Bukkit.getConsoleSender(),"tempban " + opfer.getName() + " 600");
	    	   Bukkit.broadcastMessage("§b[§6CTS§b]§a Der Spieler§6 " + opfer.getName() + "§a wurde für 10 Minuten gebannt!");
	    	   break;
	       case 3:
	    	   opfer.sendMessage("§b[§6CTS§b]§a Du wurdest gewarnt, Deine Verwarnungen: §6" + warns);
	    	   break;
	       case 4:
	    	   opfer.sendMessage("§b[§6CTS§b]§a Du wurdest gewarnt, Deine Verwarnungen: §6" + warns);
	    	   opfer.kickPlayer("§6Du wurdest von §b§lCrafted §6für §c1 Stunde§6 gebannt. Grund: §c" + reason);
	    	   plugin.getServer().dispatchCommand(Bukkit.getConsoleSender(),"tempban " + opfer.getName() + " 3600");
	    	   Bukkit.broadcastMessage("§b[§6CTS§b]§a Der Spieler§6 " + opfer.getName() + "§a wurde für 1 Stunde gebannt!");

	    	   break;
	       case 5:
	    	   opfer.sendMessage("§b[§6CTS§b]§a Du wurdest gewarnt, Deine Verwarnungen: §6" + warns);
	    	   break;
	       case 6:
	    	   opfer.sendMessage("§b[§6CTS§b]§a Du wurdest gewarnt, Deine Verwarnungen: §6" + warns);
	    	   opfer.kickPlayer("§6Du wurdest von §b§lCrafted §6für §c12 Stunden§6 gebannt. Grund: §c" + reason);
	    	   plugin.getServer().dispatchCommand(Bukkit.getConsoleSender(),"tempban " + opfer.getName() + " 43200");
	    	   Bukkit.broadcastMessage("§b[§6CTS§b]§a Der Spieler§6 " + opfer.getName() + "§a wurde für 12 Stunden gebannt!");

	    	   break;
	       case 7:
	    	   opfer.sendMessage("§b[§6CTS§b]§a Du wurdest gewarnt, Deine Verwarnungen: §6" + warns);
	    	   break;
	       case 8:
	    	   opfer.sendMessage("§b[§6CTS§b]§a Du wurdest gewarnt, Deine Verwarnungen: §6" + warns);
	    	   opfer.kickPlayer("§6Du wurdest von §b§lCrafted §6für §c2 Tage§6 gebannt. Grund: §c" + reason);
	    	   plugin.getServer().dispatchCommand(Bukkit.getConsoleSender(),"tempban " + opfer.getName() + " 172800");
	    	   Bukkit.broadcastMessage("§b[§6CTS§b]§a Der Spieler§6 " + opfer.getName() + "§a wurde für 2 Tage gebannt!");

	    	   break;
	       case 9:
	    	   opfer.sendMessage("§b[§6CTS§b]§a Du wurdest gewarnt, Deine Verwarnungen: §6" + warns);
	    	   opfer.sendMessage("§b[§6CTS§b]§4§l Achtung:§c Bei deinem nächsten Warn wirst du Permanent gebannt!");
	    	   break;
	       case 10:
	    	   opfer.kickPlayer("§6Du wurdest von §b§lCrafted §6Permanent gebannt. Grund: §c 10 Warns");
	    	   Bukkit.broadcastMessage("§b[§6CTS§b]§a Der Spieler§6 " + opfer.getName() + "§a wurde Permanent gebannt!");

	    	   plugin.getServer().dispatchCommand(Bukkit.getConsoleSender(),"ban " + opfer.getName() + " 10 Warnungen");
	    	   break;
	       }		
	}
	private void addWarn(String player, int i) {
		MySQL sql = this.plugin.getMySQL();
		if (player.length() > 16) {
			player = player.substring(0, 16);
		} if (getWarns(player) == 0) {
			sql.queryUpdate("INSERT INTO warns (name, anzahl) VALUES ('" + player + "', '" + i + "')");
		} else {
			sql.queryUpdate("UPDATE warns SET anzahl='" + (getWarns(player) + i) + "' WHERE name='" + player + "'");
		}
			
	}
	private int getWarns(String player) {
		MySQL sql = this.plugin.getMySQL();
		Connection conn = sql.getConnection();
		ResultSet rs = null;
		PreparedStatement st = null;
		int anzahl = 0;
		try {
			st = conn.prepareStatement("SELECT * FROM warns WHERE name=?");
			st.setString(1, player);
			rs = st.executeQuery();
			rs.last();
			if (rs.getRow() != 0) {
				rs.first();
				anzahl = rs.getInt("anzahl");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			sql.closeRessources(rs, st);
		}
		return anzahl;
	}
	private void setWarn(String player, int i) {
		MySQL sql = this.plugin.getMySQL();
		if (player.length() > 16) {
			player = player.substring(0, 16);
		}
			sql.queryUpdate("INSERT INTO warns (name, anzahl) VALUES ('" + player + "', '" + i + "')");
			
	}
	
	
}

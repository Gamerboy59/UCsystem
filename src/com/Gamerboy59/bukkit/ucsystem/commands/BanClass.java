package com.Gamerboy59.bukkit.ucsystem.commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.Gamerboy59.bukkit.ucsystem.main;
import com.Gamerboy59.bukkit.ucsystem.mysql.MySQL;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;


public class BanClass implements CommandExecutor, Listener {

	private main plugin;

	private String reason = "Leer!";

	public BanClass(main plugin) {
		this.plugin = plugin;

		MySQL sql = this.plugin.getMySQL();
		sql.queryUpdate("CREATE TABLE IF NOT EXISTS ban (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(16), reason VARCHAR(100))");
	}
	
	private String KeineRechte = "§cDu hast keinen Zugriff auf diesen Befehl!";
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		
		if (label.equalsIgnoreCase("isbanned")) {
			if (sender.hasPermission("crafted.ban")) {
 			 if (args.length > 0) {
				String reason = this.getBanReason(args[0]);
				if (reason == null) {
					sender.sendMessage("§b[]============= §6 Status§b ============[]");
					sender.sendMessage("§bGebannt:§6 Nein§7 |§b Grund:§6 --");
				    sender.sendMessage("§b[]====================================[]");
				} else {
					sender.sendMessage("§b[]============= §6 Status§b ============[]");
					sender.sendMessage("§bGebannt:§6 Ja§7 |§b Grund:§6 " + reason);
				    sender.sendMessage("§b[]====================================[]");
				}
			} else {
				sender.sendMessage("§cVerwendung:§b /isbanned [Spieler]");
			}
		  } else {
			  sender.sendMessage(KeineRechte);
		  }
		} 
		if (label.equalsIgnoreCase("unban")) {
			if (sender.hasPermission("crafted.ban")) {
			  if (args.length > 0) {
				if (this.getBanReason(args[0]) != null) {
					this.unban(args[0]);
					sender.sendMessage("§bDer Spieler§6 " + args[0] + "§b wurde entbannt.");
				} else {
					sender.sendMessage("§bDer Spieler§6 §6" + args[0] + "§b ist nicht gebannt.");
				}
			} else {
				sender.sendMessage("§cVerwendung:§b /unban [Spieler]");
			}
		  } else {
			  sender.sendMessage(KeineRechte);
		  }
		}

		if (label.equalsIgnoreCase("kick")) {
			if (sender.hasPermission("crafted.kick")) {
				if (args.length == 0) {
					sender.sendMessage("§cVerwendung:§b /kick [Player] [Grund]");
				}
				if (args.length >= 2) {
					Player opfer = Bukkit.getPlayer(args[0]);
					reason = " ";
					for (int i = 1; i < args.length; i++) {
						reason += " " + args[i];
					}
					reason = ChatColor.translateAlternateColorCodes('&', reason);
				
					if (opfer != null) {
						if (opfer.hasPermission("crafted.team") || opfer.isOp()) {
							sender.sendMessage("§cDu kannst diesen Spieler nicht kicken!");
						} else {
							opfer.kickPlayer("§6Du wurdest von§b§l Crafted§6 gekickt. Grund:§c " + reason);
							Bukkit.broadcastMessage("§9Der Spieler §7" + opfer.getName() + "§9 wurde von §7" + sender.getName() + " §9geckickt. §9Grund: §b" + reason);
						}
					} else {
						sender.sendMessage("§cDieser Spieler ist nicht Online!");
					}
				}
			} else {
				sender.sendMessage(KeineRechte);
			}
		}
		
		
		if (label.equalsIgnoreCase("ban")) {
		 if (sender.hasPermission("crafted.ban")) {	
		  if (args.length > 0) {
			String player = args[0];
			String reason = this.reason;
			if (args.length > 1) {
				reason = args[1];
				for (int i = 2; i < args.length; i++) {
					reason += " " + args[i];
				}
				reason = ChatColor.translateAlternateColorCodes('&', reason);
			}
			player = setBanned(player, reason);
			
			if (Bukkit.getPlayer(player) != null) {
				Bukkit.getPlayer(player).kickPlayer("§6Du wurdest Permanent von§b§l Crafted§6 gebannt. Grund:§c " + reason);
			}
			Bukkit.broadcastMessage("§9Der Spieler §7" + player + " §9wurde von §7" + sender.getName() + " §9gebannt Grund: §b" + reason);
		} else {
			sender.sendMessage("§cVerwendung:§b /ban [Spieler] [Grund]");
		}
	   } else {
		   sender.sendMessage(KeineRechte);
	   }
     }
		return true;
	}
	public String getBanReason(String player) {
		MySQL sql = this.plugin.getMySQL();
		Connection conn = sql.getConnection();
		ResultSet rs = null;
		PreparedStatement st = null;
		String reason = null;
		try {
			st = conn.prepareStatement("SELECT * FROM ban WHERE name=?");
			st.setString(1, player);
			rs = st.executeQuery();
			rs.last();
			if (rs.getRow() != 0) {
				rs.first();
				reason = rs.getString("reason");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			sql.closeRessources(rs, st);
		}
		return reason;
	}

	public String setBanned(String player, String reason) {
		MySQL sql = this.plugin.getMySQL();
		if (player.length() > 16) {
			player = player.substring(0, 16);
		}
		if (this.getBanReason(player) != null) {
			sql.queryUpdate("UPDATE ban SET reason='" + reason + "' WHERE name='" + player + "'");
		} else {
			sql.queryUpdate("INSERT INTO ban (name, reason) VALUES ('" + player + "', '" + reason + "')");
		}
		int players = this.plugin.getConfig().getInt("Banned-Players");
		this.plugin.getConfig().set("Banned-Players", players + 1);
		this.plugin.saveConfig();
		return player;
	}

	public void unban(String player) {
		MySQL sql = this.plugin.getMySQL();
		sql.queryUpdate("DELETE FROM ban WHERE name='" + player + "'");
	}

	@EventHandler
	public void onLogin(PlayerLoginEvent e) {
		Player p = e.getPlayer();
		String reason = this.getBanReason(p.getName());
		if (reason != null) {
			e.setResult(Result.KICK_BANNED);
			e.setKickMessage("§6Du bist Permanent von§b§l Crafted§r§6 gebannt. Grund:§c " + reason);
		}
	}
}

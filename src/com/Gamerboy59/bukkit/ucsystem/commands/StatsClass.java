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
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;


public class StatsClass implements CommandExecutor, Listener {
	
	private main plugin;
	public StatsClass(main plugin) {
		this.plugin = plugin;
		
		MySQL sql = this.plugin.getMySQL();
		sql.queryUpdate("CREATE TABLE IF NOT EXISTS stats (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(16), kills INT, tode INT)");

	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (label.equalsIgnoreCase("stats")) {
			if (args.length == 0) {
				sender.sendMessage("§a[]==============§6 Stats§a ==============[]");
				int kills = getKills(sender.getName());
				int tode = getTode(sender.getName());
				int kdr = 0;
				if (tode == 0) {
					kdr = 0;
				} else {
					kdr = (kills /tode);
				}
				sender.sendMessage("§aKills: §6" + kills + "§7 | §aTode:§6 " + tode + "§7 | §aKdr:§6 " + kdr);
			}
			if (args.length == 1) {
				String name = args[0];
				sender.sendMessage("§a[]==============§6 " + name + " §a ==============[]");
				int kills = getKills(name);
				int tode = getTode(name);
				int kdr = 0;
				if (tode == 0) {
					kdr = 0;
				} else {
					kdr = (kills /tode);
				}
				sender.sendMessage("§aKills: §6" + kills + "§7 | §aTode:§6 " + tode + "§7 | §aKdr:§6 " + kdr);
			}
		}
		
		return false;
	}

	private int getKills(String name) {
		MySQL sql = this.plugin.getMySQL();
		Connection conn = sql.getConnection();
		ResultSet rs = null;
		PreparedStatement st = null;
		int kills = 0;
		try {
			st = conn.prepareStatement("SELECT * FROM stats WHERE name=?");
			st.setString(1, name);
			rs = st.executeQuery();
			rs.last();
			if (rs.getRow() != 0) {
				rs.first();
				kills = rs.getInt("kills");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			sql.closeRessources(rs, st);
		}
		return kills;
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		
		Player killer = null;
		Player p = null;
		if (e.getEntity() instanceof Player) {
			p = (Player) e.getEntity();
		} 
		if (p == null) {
			return;
		}
		if (e.getEntity().getKiller() instanceof Player) {
			killer = (Player) e.getEntity().getKiller();
		} else {
			addTode(p.getName());
			return;
		}
		if (killer == null) {
			return;
		}
		
		
		addKill(killer.getName());
		addTode(p.getName());
		killer.sendMessage("§bDu hast §6" + p.getName() + "§b getötet!");
		p.sendMessage("§bDu wurdest von §6" + killer.getName() + "§b getötet!");
		
		
		int kdr1 = 0;
		int kdr2 = 0;
		
		int kills1 = getKills(killer.getName());
		int tode1 = getTode(killer.getName());
		
		if (tode1 == 0) {
			kdr1 = 0;
		} else {
			kdr1 = (kills1 /tode1);
		}
		
		Scoreboard board1 = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective obj1 = board1.getObjective("scoreboard");
		if(obj1 == null){
			obj1 = board1.registerNewObjective("scoreboard", "dummy");
			obj1.setDisplaySlot(DisplaySlot.SIDEBAR);
			obj1.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Scoreboard");
			
		}
		
		obj1.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Kills:")).setScore(kills1);
		obj1.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Tode:")).setScore(tode1);
		obj1.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "KDR:")).setScore(kdr1);
		
		killer.setScoreboard(board1);
		
		
		
		int kills2 = getKills(p.getName());
		int tode2 = getTode(p.getName());
		
		if (tode2 == 0) {
			kdr2 = 0;
		} else {
			kdr2 = (kills2 /tode2);
		}
		
		Scoreboard board2 = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective obj2 = board1.getObjective("scoreboard");
		if(obj2 == null){
			obj2 = board1.registerNewObjective("scoreboard", "dummy");
			obj2.setDisplaySlot(DisplaySlot.SIDEBAR);
			obj2.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Scoreboard");
			
		}
		
		obj2.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Kills:")).setScore(kills2);
		obj2.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Tode:")).setScore(tode2);
		obj2.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Tode:")).setScore(kdr2);
		
		p.setScoreboard(board2);
		
		
	}

	private void addKill(String player) {
		MySQL sql = this.plugin.getMySQL();
		if (player.length() > 16) {
			player = player.substring(0, 16);
		} if (getKills(player) == 0) {
			sql.queryUpdate("INSERT INTO stats (name, kills, tode) VALUES ('" + player + "', '" + 1 + "', '" + getTode(player) + "')");
		} else {
			sql.queryUpdate("UPDATE stats SET kills='" + (getKills(player) + 1) + "' WHERE name='" + player + "'");
		}
		
	}
	private void addTode(String player) {
		MySQL sql = this.plugin.getMySQL();
		if (player.length() > 16) {
			player = player.substring(0, 16);
		} if (getTode(player) == 0) {
			sql.queryUpdate("INSERT INTO stats (name, kills, tode) VALUES ('" + player + "', '" + getKills(player) + "', '" + 1 + "')");
		} else {
			sql.queryUpdate("UPDATE stats SET tode='" + (getTode(player) + 1) + "' WHERE name='" + player + "'");
		}
			
		
	}

	private int getTode(String player) {
		MySQL sql = this.plugin.getMySQL();
		Connection conn = sql.getConnection();
		ResultSet rs = null;
		PreparedStatement st = null;
		int tode = 0;
		try {
			st = conn.prepareStatement("SELECT * FROM stats WHERE name=?");
			st.setString(1, player);
			rs = st.executeQuery();
			rs.last();
			if (rs.getRow() != 0) {
				rs.first();
				tode = rs.getInt("tode");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			sql.closeRessources(rs, st);
		}
		return tode;
	}
	
}	

package com.Gamerboy59.bukkit.ucsystem.globallistener;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.Gamerboy59.bukkit.ucsystem.main;
import com.Gamerboy59.bukkit.ucsystem.mysql.MySQL;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

	File f = new File("plugins/CraftedSystem/", "tabname.yml");
	FileConfiguration tabname = YamlConfiguration.loadConfiguration(f);
	private main plugin;
	public JoinListener(main main) {
		this.plugin = main;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		
		e.setJoinMessage(null);
		
		if (f.exists() == true) {
				try {
					tabname.load(f);
				} catch (IOException| InvalidConfigurationException e1) {
					e1.printStackTrace();
				}
			if (tabname.get(p.getName()) != null) {
				String colorname = tabname.getString(p.getName());
				p.setPlayerListName(colorname);
			}
			else {
				String namecolor = "§f" + p.getName();
				if (namecolor.length() >= 16) {
					namecolor = namecolor.substring(0, 16);
				}
				p.setPlayerListName(namecolor);
			}
		} else {
			try {
				f.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			String namecolor = "§f" + p.getName();
			if (namecolor.length() >= 16) {
				namecolor = namecolor.substring(0, 16);
			}
			p.setPlayerListName(namecolor);
		}
	}	
	
	public int getTode(String player) {
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
	public int getKills(String name) {
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
	
	
}

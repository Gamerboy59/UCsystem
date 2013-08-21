package com.Gamerboy59.bukkit.ucsystem.mysql;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class MySQL {

	private String host;
	private int port;
	private String user;
	private String password;
	private String database;

	private Connection conn;

	public MySQL() throws Exception {
		File file = new File("plugins/CraftedSystem/", "database.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

		String db = "database.";
		cfg.addDefault(db + "host", "localhost");
		cfg.addDefault(db + "port", 3306);
		cfg.addDefault(db + "user", "user");
		cfg.addDefault(db + "password", "password");
		cfg.addDefault(db + "database", "database");
		cfg.options().copyDefaults(true);
		try {
			cfg.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.host = cfg.getString(db + "host");
		this.port = cfg.getInt(db + "port");
		this.user = cfg.getString(db + "user");
		this.password = cfg.getString(db + "password");
		this.database = cfg.getString(db + "database");

		this.openConnection();
	}

	public Connection openConnection() throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database, this.user, this.password);
		this.conn = conn;
		return conn;
	}

	public Connection getConnection() {
		return this.conn;
	}

	public boolean hasConnection() {
		try {
			return this.conn != null || this.conn.isValid(1);
		} catch (SQLException e) {
			return false;
		}
	}

	public void queryUpdate(String query) {
		Connection conn = this.conn;
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(query);
			st.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Failed to send update '" + query + "'.");
			e.printStackTrace();
		} finally {
			this.closeRessources(null, st);
		}
	}

	public void closeRessources(ResultSet rs, PreparedStatement st) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
			}
		}
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
			}
		}
	}

	public void closeConnection() {
		try {
			this.conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.conn = null;
		}
	}
	
	/* Clan 
	 * Abfragen
	 */
	public void setChatTag(String string, String u) {
		if (getChatTag(u) != null) {
			this.queryUpdate("UPDATE clanusers SET chattag='" + string + "' WHERE name='" + u + "'");
		} else {
			this.queryUpdate("INSERT INTO clanusers (name, clantag, chattag) VALUES ('" + u + "', '"+ ChatColor.stripColor(u) +"', '"+ u +"')");
		}
	}

	public String getChatTag(String name) {
		

		Connection conn = this.getConnection();
		ResultSet rs = null;
		PreparedStatement st = null;
		String tag = null;
		try {
			st = conn.prepareStatement("SELECT * FROM clanusers WHERE name=?");
			st.setString(1, name);
			rs = st.executeQuery();
			rs.last();
			if (rs.getRow() != 0) {
				rs.first();
				tag = rs.getString("chattag");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeRessources(rs, st);
		}
		
		return tag;
	}
	
	public boolean alreadyClan(String clantag) {
		boolean status = false;

		Connection conn = this.getConnection();
		ResultSet rs = null;
		PreparedStatement st = null;
		String clan = null;
		try {
			st = conn.prepareStatement("SELECT * FROM clans WHERE clantag=?");
			st.setString(1, clantag);
			rs = st.executeQuery();
			rs.last();
			if (rs.getRow() != 0) {
				rs.first();
				clan = rs.getString("clantag");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeRessources(rs, st);
		}
		
		if (clan != null) {
			status = true;
		}
		
		return status;
	}

	public void removeUser(Player x) {

		this.queryUpdate("DELETE FROM clanusers WHERE name='" + x.getName() + "'");
	}

	public void createClan(String clanname, String clantag, String name) {

		this.queryUpdate("INSERT INTO clans (name, clantag, owner, clanusers) VALUES ('" + clanname + "', '" + clantag + "', '"+ name +"', '"+ name +"')");
	}

	public void removeClan(String clantag) {

		this.queryUpdate("DELETE FROM clans WHERE clantag='" + clantag + "'");
	}

	public String getClanUsers(String clantag) {
		

		Connection conn = this.getConnection();
		ResultSet rs = null;
		PreparedStatement st = null;
		String users = null;
		try {
			st = conn.prepareStatement("SELECT * FROM clans WHERE clantag=?");
			st.setString(1, clantag);
			rs = st.executeQuery();
			rs.last();
			if (rs.getRow() != 0) {
				rs.first();
				users = rs.getString("clanusers");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeRessources(rs, st);
		}
		
		return users;
	}

	public boolean isClanOwner(String clanname, String name) {
		boolean status = false;
		String owner = getClanOwner(clanname);
		if (owner.equalsIgnoreCase(name)) {
			status = true;
			}
		return status;
	}

	public String getClanOwner(String clantag) {
	

		Connection conn = this.getConnection();
		ResultSet rs = null;
		PreparedStatement st = null;
		String owner = null;
		try {
			st = conn.prepareStatement("SELECT * FROM clans WHERE clantag=?");
			st.setString(1, clantag);
			rs = st.executeQuery();
			rs.last();
			if (rs.getRow() != 0) {
				rs.first();
				owner = rs.getString("owner");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeRessources(rs, st);
		}
		
		return owner;
	}

	public String getClanTag(String name) {
		

		Connection conn = this.getConnection();
		ResultSet rs = null;
		PreparedStatement st = null;
		String tag = null;
		try {
			st = conn.prepareStatement("SELECT * FROM clanusers WHERE name=?");
			st.setString(1, name);
			rs = st.executeQuery();
			rs.last();
			if (rs.getRow() != 0) {
				rs.first();
				tag = rs.getString("clantag");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeRessources(rs, st);
		}
		
		return tag;
	}

	public boolean isClan(String name) {
		boolean status = true;
		
		Connection conn = this.getConnection();
		ResultSet rs = null;
		PreparedStatement st = null;
		String cname = null;
		try {
			st = conn.prepareStatement("SELECT * FROM clanusers WHERE name=?");
			st.setString(1, name);
			rs = st.executeQuery();
			rs.last();
			if (rs.getRow() != 0) {
				rs.first();
				cname = rs.getString("clantag");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeRessources(rs, st);
		}
		if (cname == null) {
			status = false;
		}
		
		return status;
	}
}

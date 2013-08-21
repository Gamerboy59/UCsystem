package com.Gamerboy59.bukkit.ucsystem;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.Gamerboy59.bukkit.ucsystem.commands.BanClass;
import com.Gamerboy59.bukkit.ucsystem.commands.ChatClearClass;
import com.Gamerboy59.bukkit.ucsystem.commands.ClanClass;
import com.Gamerboy59.bukkit.ucsystem.commands.CTSClass;
import com.Gamerboy59.bukkit.ucsystem.commands.EatClass;
import com.Gamerboy59.bukkit.ucsystem.commands.FriedenClass;
import com.Gamerboy59.bukkit.ucsystem.commands.GameClass;
import com.Gamerboy59.bukkit.ucsystem.commands.GiveAllClass;
import com.Gamerboy59.bukkit.ucsystem.commands.GlobalMuteClass;
import com.Gamerboy59.bukkit.ucsystem.commands.LogoutAllClass;
import com.Gamerboy59.bukkit.ucsystem.commands.MotdClass;
import com.Gamerboy59.bukkit.ucsystem.commands.PvpClass;
import com.Gamerboy59.bukkit.ucsystem.commands.RandomClass;
import com.Gamerboy59.bukkit.ucsystem.commands.SlotsClass;
import com.Gamerboy59.bukkit.ucsystem.commands.StatsClass;
import com.Gamerboy59.bukkit.ucsystem.commands.SupportClass;
import com.Gamerboy59.bukkit.ucsystem.commands.TabPexClass;
import com.Gamerboy59.bukkit.ucsystem.commands.TeamChatClass;
import com.Gamerboy59.bukkit.ucsystem.commands.TeamClass;
import com.Gamerboy59.bukkit.ucsystem.commands.WarnClass;
import com.Gamerboy59.bukkit.ucsystem.globallistener.ArrowListener;
import com.Gamerboy59.bukkit.ucsystem.globallistener.ChatListener;
import com.Gamerboy59.bukkit.ucsystem.globallistener.DamageListener;
import com.Gamerboy59.bukkit.ucsystem.globallistener.FunMoveListener;
import com.Gamerboy59.bukkit.ucsystem.globallistener.JoinListener;
import com.Gamerboy59.bukkit.ucsystem.globallistener.NameTag;
import com.Gamerboy59.bukkit.ucsystem.globallistener.PingSlotsListener;
import com.Gamerboy59.bukkit.ucsystem.globallistener.QuitListener;
import com.Gamerboy59.bukkit.ucsystem.mysql.MySQL;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;



public class main extends JavaPlugin {

	private MySQL sql;
	static Logger log = Bukkit.getLogger();
	final String crafted = "[Panel] ";
	public static String motd;
	public static int slots;
	public static boolean mute;
	public void onEnable() {
		
		if (!(this.getServer().getPluginManager().getPlugin("PermissionsEx") != null)) {
			log.info(crafted + "Das Plugin 'PermissionsEx' ist nicht installiert!");
			this.getServer().getPluginManager().disablePlugin(this);
		} else if (!(this.getServer().getPluginManager().getPlugin("TagAPI") != null)) {
			log.info(crafted + "Das Plugin 'TagAPI' ist nicht installiert!");
			this.getServer().getPluginManager().disablePlugin(this);
		} else {
		
		try {
			this.sql = new MySQL();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Commands
		this.getCommand("ban").setExecutor(new BanClass(this));
		this.getCommand("unban").setExecutor(new BanClass(this));
		this.getCommand("kick").setExecutor(new BanClass(this));
		this.getCommand("isbanned").setExecutor(new BanClass(this));
		this.getCommand("cc").setExecutor(new ChatClearClass(this));
		this.getCommand("chatclear").setExecutor(new ChatClearClass(this));
		this.getCommand("stats").setExecutor(new StatsClass(this));
		this.getCommand("tc").setExecutor(new TeamChatClass(this));
		this.getCommand("logoutall").setExecutor(new LogoutAllClass());
		this.getCommand("random").setExecutor(new RandomClass());
		this.getCommand("pvp").setExecutor(new PvpClass());
		this.getCommand("globalmute").setExecutor(new GlobalMuteClass(this));
		this.getCommand("support").setExecutor(new SupportClass());
		this.getCommand("tabname").setExecutor(new TabPexClass());
		this.getCommand("giveall").setExecutor(new GiveAllClass());
		this.getCommand("essen").setExecutor(new EatClass());
		this.getCommand("warns").setExecutor(new WarnClass(this));
		this.getCommand("warn").setExecutor(new WarnClass(this));
		this.getCommand("slots").setExecutor(new SlotsClass(this));
		this.getCommand("motd").setExecutor(new MotdClass(this));
		this.getCommand("frieden").setExecutor(new FriedenClass());
		this.getCommand("friede").setExecutor(new FriedenClass());
		this.getCommand("team").setExecutor(new TeamClass());
		this.getCommand("system").setExecutor(new CTSClass());
		this.getCommand("dev").setExecutor(new CTSClass());
		this.getCommand("clan").setExecutor(new ClanClass(this));
		this.getCommand("c").setExecutor(new ClanClass(this));


		
		//Events
		this.getServer().getPluginManager().registerEvents(new BanClass(this), this);
		this.getServer().getPluginManager().registerEvents(new JoinListener(this), this);
		this.getServer().getPluginManager().registerEvents(new ChatListener(this), this);
		this.getServer().getPluginManager().registerEvents(new StatsClass(this), this);
		this.getServer().getPluginManager().registerEvents(new FunMoveListener(), this);
		this.getServer().getPluginManager().registerEvents(new QuitListener(), this);
		this.getServer().getPluginManager().registerEvents(new GameClass(this), this);
		this.getServer().getPluginManager().registerEvents(new DamageListener(this), this);
		this.getServer().getPluginManager().registerEvents(new PingSlotsListener(), this);
		this.getServer().getPluginManager().registerEvents(new ArrowListener(), this);
		this.getServer().getPluginManager().registerEvents(new NameTag(), this);
		
		this.getConfig().addDefault("CombatLog.TagCommands.1", "/sudo spawn Gamerboy59");
		this.getConfig().addDefault("CombatLog.TagCommands.2", "/example");
		this.getConfig().addDefault("CombatLog.TagCommands.3", "/example");
		this.getConfig().addDefault("CombatLog.TagCommands.4", "/example");
		this.getConfig().addDefault("CombatLog.TagCommands.5", "/example");
		
		this.getConfig().addDefault("CombatLog.EntTagCommands.1", "/sudo %player fly off");
		this.getConfig().addDefault("CombatLog.EntTagCommands.2", "/example");
		this.getConfig().addDefault("CombatLog.EntTagCommands.3", "/example");
		this.getConfig().addDefault("CombatLog.EntTagCommands.4", "/example");
		this.getConfig().addDefault("CombatLog.EntTagCommands.5", "/example");
		
		this.getConfig().options().copyDefaults(true);
		this.saveConfig();
		
		if (!(this.getConfig().get("Standard") != null)) {
			this.getConfig().set("Standard", true);	
			this.getConfig().set("Konsole-TeamChat", false);
			this.getConfig().set("Mute", false);
			this.saveConfig();
			log.info(crafted + "Standards wurden erstellt");
		}
		
		slots = getConfig().getInt("slots");
		motd = getConfig().getString("motd");
		mute = getConfig().getBoolean("Mute");
		System.out.println(crafted + "wurde aktiviert");
		}
	}
	public void onDisable() {
		getConfig().set("Mute", mute);
		System.out.println(crafted + "wurde deaktviert");
	}	
	
	public MySQL getMySQL() {
		return this.sql;
	}
	 
	public WorldGuardPlugin getWorldGuard() {
	    Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");
	    if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
	        return null;
	    }
	 
	    return (WorldGuardPlugin) plugin;
	}
	
}

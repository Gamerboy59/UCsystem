package com.Gamerboy59.bukkit.ucsystem.commands;

import java.io.File;
import java.util.HashMap;

import com.Gamerboy59.bukkit.ucsystem.main;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;

public class GameClass implements CommandExecutor, Listener {



	
	static public HashMap<String, String> hashmap = new HashMap<String, String>();
	static public HashMap<Location, Block> blocks = new HashMap<Location, Block>();
	static public HashMap<String, String> players = new HashMap<String, String>();
	static public HashMap<String, Location> locations = new HashMap<String, Location>();


	File f = new File("plugins/CraftedSystem/", "game.yml");
	FileConfiguration game = YamlConfiguration.loadConfiguration(f);

	
	static Block red;
	static Block blue;
	@SuppressWarnings("unused")
	private main plugin;
	
	
	public GameClass(main main) {
		this.plugin = main;
		
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		
		
		return false;
	}
	
	@SuppressWarnings("unused")
	private void loadDefaults() {	
		Location red_loc = getLocation("red");
		Location blue_loc = getLocation("blue");
		red = red_loc.getBlock();
		blue = blue_loc.getBlock();
	}

	private Location getLocation(String string) {
		double x = (double) game.get("blocks."+string+".x");
		double y = (double) game.get("blocks."+string+".y");
		double z = (double) game.get("blocks."+string+".z");
		double yaw = (double) game.get("blocks.red.yaw");
		double pitch = (double) game.get("blocks.red.pitch");
		Location loc = new Location(Bukkit.getWorld("world"), x, y, z, (float)yaw, (float)pitch);
		return loc;
	}
	
}
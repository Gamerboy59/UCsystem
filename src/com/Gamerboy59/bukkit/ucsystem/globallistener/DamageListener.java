package com.Gamerboy59.bukkit.ucsystem.globallistener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.Gamerboy59.bukkit.ucsystem.main;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;


public class DamageListener implements Listener {
	
	File friedenfile = new File("plugins/CraftedSystem/", "frieden.yml");
	FileConfiguration frieden = YamlConfiguration.loadConfiguration(friedenfile);
	
	File cfgfile = new File("plugins/CraftedSystem/" , "config.yml");
	FileConfiguration cfg = YamlConfiguration.loadConfiguration(cfgfile);
	
	public Map<String, Integer> players = new HashMap<String, Integer>();
	  
	private main plugin;

	public DamageListener(main main) {
		this.plugin = main;
	}
	  
	@EventHandler
	public void onDmg(EntityDamageByEntityEvent e) {
		loadFrieden();
		Player p = null;
		Player opfer = null;
		if (e.getDamager() instanceof Player) {
			p = (Player) e.getDamager();
		} 
		if (e.getEntity() instanceof Player) {
			opfer = (Player) e.getEntity();
		}
		
		if (opfer == null || p == null) {
			return;
		} else {
			if (frieden.get("players." + p.getName()) != null) {
				List<String> list = frieden.getStringList("players." + p.getName());
				boolean status = false;
				for (String players : list) {
					if (players.equalsIgnoreCase(opfer.getName())) {
						status = true;
					}
				}
				if (status == true) {
					e.setCancelled(true);
				}
			} else {
				
				p.performCommand(cfg.getString("CombatLog.TagCommands.1").replace("&player", p.getName()));
				p.performCommand(cfg.getString("CombatLog.TagCommands.2").replace("&player", p.getName()));
				p.performCommand(cfg.getString("CombatLog.TagCommands.3").replace("&player", p.getName()));
				p.performCommand(cfg.getString("CombatLog.TagCommands.4").replace("&player", p.getName()));
				p.performCommand(cfg.getString("CombatLog.TagCommands.5").replace("&player", p.getName()));
				
				opfer.performCommand(cfg.getString("CombatLog.TagCommands.1").replace("&player", opfer.getName()));
				opfer.performCommand(cfg.getString("CombatLog.TagCommands.2").replace("&player", opfer.getName()));
				opfer.performCommand(cfg.getString("CombatLog.TagCommands.3").replace("&player", opfer.getName()));
				opfer.performCommand(cfg.getString("CombatLog.TagCommands.4").replace("&player", opfer.getName()));
				opfer.performCommand(cfg.getString("CombatLog.TagCommands.5").replace("&player", opfer.getName()));
				
				startTask(p);
			    startTask(opfer);
			}
		}
	}
	
	@EventHandler
	public void onArrow(EntityDamageByEntityEvent e) {
		loadFrieden();
		if ((e.getDamager() instanceof Arrow)) {
		      Entity shooter = ((Arrow)e.getDamager()).getShooter();
		      if (((shooter instanceof Player)) && ((e.getEntity() instanceof Player))) {
		    	  if (!(frieden.get("players." + ((Player) shooter).getName()) != null)) {
					  
					  ((Player) shooter).performCommand(cfg.getString("CombatLog.Commands.1").replace("&player", ((Player) shooter).getName()));
					  ((Player) shooter).performCommand(cfg.getString("CombatLog.Commands.2").replace("&player", ((Player) shooter).getName()));
					  ((Player) shooter).performCommand(cfg.getString("CombatLog.Commands.3").replace("&player", ((Player) shooter).getName()));
					  ((Player) shooter).performCommand(cfg.getString("CombatLog.Commands.4").replace("&player", ((Player) shooter).getName()));
					  ((Player) shooter).performCommand(cfg.getString("CombatLog.Commands.5").replace("&player", ((Player) shooter).getName()));
					  
					  ((Player) e.getEntity()).performCommand(cfg.getString("CombatLog.Commands.1").replace("&player", ((Player) e.getEntity()).getName()));
					  ((Player) e.getEntity()).performCommand(cfg.getString("CombatLog.Commands.2").replace("&player", ((Player) e.getEntity()).getName()));
					  ((Player) e.getEntity()).performCommand(cfg.getString("CombatLog.Commands.3").replace("&player", ((Player) e.getEntity()).getName()));
					  ((Player) e.getEntity()).performCommand(cfg.getString("CombatLog.Commands.4").replace("&player", ((Player) e.getEntity()).getName()));
					  ((Player) e.getEntity()).performCommand(cfg.getString("CombatLog.Commands.5").replace("&player", ((Player) e.getEntity()).getName()));
					  
					  
					  startTask((Player)shooter);
					  startTask((Player) e.getEntity());
					  
					  
		    	  } else {
		    		  List<String> list = frieden.getStringList("players." + ((Player)shooter).getName());
				        if ((list.contains(((Player)e.getEntity()).getName()))) {
				        	e.setCancelled(true);
					          return;
				        }
		    	  }
		      }
		}
	}
	
	public void startTask(final Player player) {
		int task = Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			@Override
			public void run() {
				players.remove(player.getName());
			}
			
		}, 20L*10);
		players.put(player.getName(), task);
		
		player.performCommand(cfg.getString("CombatLog.EntTagCommands.1").replace("&player", player.getName()));
		player.performCommand(cfg.getString("CombatLog.EntTagCommands.2").replace("&player", player.getName()));
		player.performCommand(cfg.getString("CombatLog.EntTagCommands.3").replace("&player", player.getName()));
		player.performCommand(cfg.getString("CombatLog.EntTagCommands.4").replace("&player", player.getName()));
		player.performCommand(cfg.getString("CombatLog.EntTagCommands.5").replace("&player", player.getName()));
	}

	private void loadFrieden() {
		if (friedenfile.exists() == false) {
				try {
					friedenfile.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	{
		try {
			frieden.load(friedenfile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}
	}
}

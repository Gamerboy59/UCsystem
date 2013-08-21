package com.Gamerboy59.bukkit.ucsystem.globallistener;

import com.Gamerboy59.bukkit.ucsystem.main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class PingSlotsListener implements Listener {

	
	@EventHandler
	public void onPing(ServerListPingEvent e) {
		 try {
			 if (main.motd == null) {
				 main.motd = "§a§lCraftedeu.de";
			 } 
			 if (main.slots == 0) {
				 main.slots = Bukkit.getMaxPlayers();
			 }			
		     e.setMotd(ChatColor.translateAlternateColorCodes('&', main.motd));
		     e.setMaxPlayers(main.slots); 
		      } catch (NullPointerException localNullPointerException) {
		    }
	
		 
	}
	
}

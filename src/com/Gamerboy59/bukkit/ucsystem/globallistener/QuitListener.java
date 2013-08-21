package com.Gamerboy59.bukkit.ucsystem.globallistener;

import com.Gamerboy59.bukkit.ucsystem.commands.GameClass;
import com.Gamerboy59.bukkit.ucsystem.commands.SupportClass;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {

	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		
		e.setQuitMessage(null);
		
		if (SupportClass.support.containsKey(p.getName()) == true ) {
			String username = SupportClass.support.get(p.getName());
			Player other = Bukkit.getPlayer(username);
	        other.sendMessage("§6[Support] §aDer SupportChat wurde geschlossen!");
	        SupportClass.support.remove(other.getName());
	        SupportClass.support.remove(p.getName());
		}
		
		if (GameClass.players.containsKey(p.getName())) {
			Location loc = GameClass.locations.get(p.getName());
			p.teleport(loc);
		}
		
	}
}

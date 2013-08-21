package com.Gamerboy59.bukkit.ucsystem.globallistener;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.kitteh.tag.PlayerReceiveNameTagEvent;

import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class NameTag implements Listener {

	@EventHandler
	public void onName(PlayerReceiveNameTagEvent e) {
		
	 PermissionManager pex = PermissionsEx.getPermissionManager();

		    String c = "" + ChatColor.WHITE;

		    for (PermissionGroup pg : pex.getUser(e.getNamedPlayer()).getGroups()) {
		      if (pg.getName().equalsIgnoreCase("besitzer"))
		    	  	c = "" + ChatColor.DARK_RED+ChatColor.BOLD;
		      if (pg.getName().equalsIgnoreCase("Admin"))
			        c = "" + ChatColor.DARK_RED+ChatColor.BOLD;
		      if (pg.getName().equalsIgnoreCase("Entwickler"))
			        c = "" + ChatColor.DARK_AQUA+ChatColor.BOLD;
		      if (pg.getName().equalsIgnoreCase("Moderator"))
			        c = "" + ChatColor.DARK_PURPLE+ChatColor.BOLD;
		      if (pg.getName().equalsIgnoreCase("Supporter"))
			        c = "" + ChatColor.GREEN+ChatColor.BOLD;
		      if (pg.getName().equalsIgnoreCase("chatadmin")) {
			        c = "" + ChatColor.DARK_GREEN+ChatColor.BOLD;
		      }
		    }
		      e.setTag(c + e.getNamedPlayer().getName());
	  }
}	

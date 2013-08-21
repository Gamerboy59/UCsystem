package com.Gamerboy59.bukkit.ucsystem.utils;

import org.bukkit.entity.Player;

import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;



public class PermissionsExHandler {

	static PermissionsEx ex = new PermissionsEx();
	static PermissionManager m = PermissionsEx.getPermissionManager();
	
	
	static public boolean isOwner(Player p) {
		boolean st = false;
		String group = null;
		for (PermissionGroup g : m.getUser(p).getGroups()) {
			group = g.getName();
		}
		if (group.equalsIgnoreCase("Admin")) {
			st = true;
		} 		
		
		return st;
	}
	
	static public String[] getOwners() {
		PermissionUser[] usersa = m.getGroup("Admin").getUsers();
		String users = "";
		for (PermissionUser user : usersa) {
			users += "," + user.getName();
		}
		String[] list = users.split(",");
		return list;
	}
	
	static public String[] getAdmins() {
		PermissionUser[] usersa = m.getGroup("SuperModerator").getUsers();
		String users = "";
		for (PermissionUser user : usersa) {
			users += "," + user.getName();
		}
		String[] list = users.split(",");
		return list;
	}
	
	static public String[] getDevs() {
		PermissionUser[] usersa = m.getGroup("Developer").getUsers();
		String users = "";
		for (PermissionUser user : usersa) {
			users += "," + user.getName();
		}
		String[] list = users.split(",");
		return list;
	}
	
	static public String[] getMods() {
		PermissionUser[] usersa = m.getGroup("Moderator").getUsers();
		String users = "";
		for (PermissionUser user : usersa) {
			users += "," + user.getName();
		}
		String[] list = users.split(",");
		return list;
	}

	static public String[] getSupps() {
		PermissionUser[] usersa = m.getGroup("Supporter").getUsers();
		String users = "";
		for (PermissionUser user : usersa) {
			users += "," + user.getName();
		}

		String[] list = users.split(",");
		return list;
	}
	static public String[] getCA() {
		PermissionUser[] usersa = m.getGroup("ChatSupporter").getUsers();
		String users = "";
		for (PermissionUser user : usersa) {
			users += "," + user.getName();
		}
		String[] list = users.split(",");
		return list;
	}
	
	
}

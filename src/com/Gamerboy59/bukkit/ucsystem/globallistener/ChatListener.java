package com.Gamerboy59.bukkit.ucsystem.globallistener;

import java.util.List;

import com.Gamerboy59.bukkit.ucsystem.main;
import com.Gamerboy59.bukkit.ucsystem.commands.SupportClass;
import com.Gamerboy59.bukkit.ucsystem.mysql.MySQL;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.google.common.collect.Lists;

import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.bukkit.PermissionsEx;



public class ChatListener implements Listener {

	private main plugin;
	public ChatListener(main plugin) {
		this.plugin = plugin;
	}
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		
		if (main.mute) {
			boolean st = this.plugin.getConfig().getBoolean("Mute");
			if (st == true) {
				if (p.isOp() || p.hasPermission("crafted.globalmute")) {
					e.setCancelled(false);
				} else {
				e.setCancelled(true);
				p.sendMessage("§cDu kannst nichts schreiben während Globalmute aktiviert ist!");
				return;
				}
			}
		}
		if(e.getMessage().startsWith("#") && plugin.getMySQL().isClan(p.getName())) //TODO Dein Zeichen
		{
			MySQL sql = plugin.getMySQL();
			String clanusers = sql.getClanUsers(sql.getClanTag(p.getName()));
			String[] list = clanusers.split(",");
			for (String pl : list) {
				Player x = Bukkit.getPlayer(pl);
				if (x != null) {
					x.sendMessage("§6§l[ClanChat]§7 " + p.getName() + ": " + e.getMessage().substring(1)); //TODO Dein Formatting
				}
			}
			e.setCancelled(true);
			return;
		}
		if (SupportClass.support.containsKey(p.getName()) == true ) {
			e.setCancelled(true);
			String user_string = SupportClass.support.get(p.getName());
			Player user = Bukkit.getPlayer(user_string);
			user.sendMessage("§7" + p.getName() + ":§6 " + e.getMessage());
			p.sendMessage("§7" + p.getName() + ":§6 " + e.getMessage());
			// e.getRecipients().remove(p);
			// e.getRecipients().remove(user); siehe //XXX, irgendwie verstehe ich die Logik nicht ;(
		}
		if (SupportClass.support.isEmpty() == false) {
			for (Player online : Bukkit.getOnlinePlayers()) {
				if (SupportClass.support.containsKey(online.getName())) {
					String teamplayer = SupportClass.support.get(online.getName());
					Player team = Bukkit.getPlayer(teamplayer);
					if (team == null) {
						return;
					}
					e.getRecipients().remove(online); //XXX Wäre es nicht besser, am Ende removeAll zu nehmen? Und, wenn es schon String sein muss, die erstmal zu sammeln?
					e.getRecipients().remove(team);
				}
			}
		}
		
		PermissionManager m = PermissionsEx.getPermissionManager();
		String group =  null;
		List<String> g = Lists.newArrayListWithCapacity(1);
		for (PermissionGroup pg : m.getUser(p).getGroups()) {
			g.add(pg.getPrefix());
		}
		for (String x : g) {
			group = x;
		}
		
		if (plugin.getMySQL().isClan(p.getName())) {
			String tag = plugin.getMySQL().getChatTag(p.getName());
			String go = ChatColor.translateAlternateColorCodes('&', group);
			e.setFormat(go + "§a§l " + tag + "§7*§f" + p.getName() + ": " + e.getMessage());
		}
		
	String mg = ChatColor.translateAlternateColorCodes('$', e.getMessage());
	if (p.hasPermission("crafted.team")) {
		e.setMessage(mg);
	}
		
	}

	
}

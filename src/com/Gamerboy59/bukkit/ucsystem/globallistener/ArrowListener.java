package com.Gamerboy59.bukkit.ucsystem.globallistener;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ArrowListener implements Listener {

	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
			if (p.hasPermission("crafted.minigun")) {
				if (p.getItemInHand().equals(Material.BOW)) {
			 Arrow arrow = e.getPlayer().launchProjectile(Arrow.class);
             p.getPlayer().playSound(p.getLocation(), Sound.ITEM_BREAK, 10000, 2000);
             arrow.setShooter(e.getPlayer());
             arrow.setVelocity(e.getPlayer().getLocation().getDirection().multiply(3));
             
             p.getInventory().setItemInHand(new ItemStack(Material.AIR));
             p.getInventory().setItemInHand(new ItemStack(Material.BOW));
			}
			}
		}
	}
	
}

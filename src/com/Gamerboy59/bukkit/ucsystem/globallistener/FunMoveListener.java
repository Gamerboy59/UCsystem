package com.Gamerboy59.bukkit.ucsystem.globallistener;


import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class FunMoveListener implements Listener {

	  @EventHandler
	  public void onPlayerMove(PlayerMoveEvent ev) {
	    if (!ev.getFrom().getBlock().getLocation().equals(ev.getTo().getBlock().getLocation())) {
	      Player player = ev.getPlayer();
	      Block block = player.getLocation().getBlock().getRelative(BlockFace.DOWN);
	      if ((block.getType() == Material.ICE) && (player.isSneaking())) {
	        Vector dir = player.getLocation().getDirection().multiply(1);
	        Vector vec = new Vector(dir.getX(), dir.getY(), dir.getZ());
	        player.setVelocity(vec);
	      }
	    } 
	    if (!ev.getFrom().getBlock().getLocation().equals(ev.getTo().getBlock().getLocation())) {
	    	Player player = ev.getPlayer();
	    	 Block block = player.getLocation().getBlock().getRelative(BlockFace.DOWN);
	    	 Block under = block.getRelative(BlockFace.DOWN);
	    	 if (block.getType() == Material.WOOD && under.getType() == Material.BEDROCK) {
	 	        Vector dir = player.getLocation().getDirection().multiply(2);
		        Vector vec = new Vector(dir.getX(), dir.getY(), dir.getZ());
		        player.setVelocity(vec);

	    	 }
	    }
	    
	  }
	
}

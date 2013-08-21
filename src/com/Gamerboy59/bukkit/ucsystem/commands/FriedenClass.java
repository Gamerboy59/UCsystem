package com.Gamerboy59.bukkit.ucsystem.commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class FriedenClass implements CommandExecutor {


	File friedenfile = new File("plugins/CraftedSystem/" , "frieden.yml");
	FileConfiguration frieden = YamlConfiguration.loadConfiguration(friedenfile);
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	
		if (label.equalsIgnoreCase("friede") || label.equalsIgnoreCase("frieden")) {
			if (args.length == 0) {
				sender.sendMessage("§a[]===============§6 Frieden§a ===============[]");
				sender.sendMessage("§6/friede [Player] §b Sendet ein Friedens-Angebot");
				sender.sendMessage("§6/friede auflösen <Player> §b Lößt den Frieden!");
				sender.sendMessage("§6/friede liste§b Zeigt deinen Freund an!");
				sender.sendMessage("§a[]=======================================[]");
			} else if (args.length == 1) {
				loadFrieden();
				if (args[0].equalsIgnoreCase("liste")) {
					if (frieden.get("players." + sender.getName()) != null) {
						List<String> list = frieden.getStringList("players." + sender.getName());
						sender.sendMessage("§a[]=============§6 Liste§a ===========[]");
						for (String players : list) {
							sender.sendMessage("§b- §6" + players);
						}
					} else {
						sender.sendMessage("§aDu hast mit keinem Frieden!");
					}
					return false;
				} 
				
				String player = args[0];
				Player target = Bukkit.getPlayer(player);
				if (target != null) {
					if (frieden.get("players." + sender.getName()) != null) {
						List<String> list = frieden.getStringList("players." + sender.getName());
						boolean status = false;
						for (String players : list) {
							if (players.equalsIgnoreCase(player)) {
								status = true;
							}
						}
						if (status == true) {
							sender.sendMessage("§aDu hast mit diesem Spieler schon Frieden!");
						} else {
							target.sendMessage("§aDer Spieler§6 " + sender.getName() + "§a hat dir ein Friedens-Angebot gestellt!");
							target.sendMessage("§aTippe §6/friede annehmen " + sender.getName() + "§a um anzunehmen!");
							sender.sendMessage("§aDu hast dem Spieler§6 " + target.getName() + "§a ein Friedens-Angebot gestellt!");
							addRequest(sender.getName(), target.getName());
						}
					} else {
					target.sendMessage("§aDer Spieler§6 " + sender.getName() + "§a hat dir ein Friedens-Angebot gestellt!");
					target.sendMessage("§aTippe §6/friede annehmen " + sender.getName() + "§a um anzunehmen!");
					sender.sendMessage("§aDu hast dem Spieler§6 " + target.getName() + "§a ein Friedens-Angebot gestellt!");
					addRequest(sender.getName(), target.getName());
					}
				} else {
					sender.sendMessage("§aDieser Spieler ist nicht Online!");
				}
			} else if (args.length == 2) {
				loadFrieden();
				if (args[0].equalsIgnoreCase("auflösen")) {
					if (frieden.get("players." + sender.getName()) != null || frieden.get("players." + args[1]) != null) {
						String player = args[1];
						List<String> list = frieden.getStringList("players." + sender.getName());
						List<String> list1 = frieden.getStringList("players." + args[1]);
						boolean status = false;
						for (String players : list) {
							if (players.equalsIgnoreCase(player)) {
								status = true;
							}
						}
						if (status == true) {			
							list.remove(player);
							list1.remove(sender.getName());
							frieden.set("players." + sender.getName(), list);
							frieden.set("players." + args[1], list1);
							saveFrieden();
							Player target = Bukkit.getPlayer(player);
							if (target != null) {
								target.sendMessage("§aDer Spieler§6" + sender.getName() + "§a hat den Frieden aufgelößt.");
								sender.sendMessage("§aDu hast den Frieden mit §6" + player + "§a aufgelößt.");
							} else {
								sender.sendMessage("§aDu hast den Frieden mit §6" + player + "§a aufgelößt.");
							}
						} else {
							sender.sendMessage("§aDu hast mit diesem Spieler keinen Frieden!");
						}
						
					} else {
						sender.sendMessage("§aDu hast mit keinem Spieler Frieden!");
					}
				} else if (args[0].equalsIgnoreCase("annehmen")) {
					loadFrieden();
					String player = args[1];
					Player target = Bukkit.getPlayer(player);
					if (target != null) {
						if (frieden.get("einladungen." + target.getName()) != null) {
							String name = frieden.getString("einladungen." + target.getName());
							if (name.equalsIgnoreCase(sender.getName())) {	
								if (frieden.get("players." + sender.getName()) != null) {
									List<String> list = frieden.getStringList("players." + sender.getName());
									list.add(target.getName());
									frieden.set("players." + sender.getName(), list);
									saveFrieden();	
									removeRequest(sender.getName() , target.getName());
									if (frieden.get("players." + target.getName()) != null) {
										List<String> list1 = frieden.getStringList("players." + target.getName());
										list1.add(sender.getName());
										frieden.set("players." + target.getName(), list1);
										saveFrieden();
										removeRequest(sender.getName() , target.getName());
									} else {
										List<String> list1 = new ArrayList<String>();
										list1.add(sender.getName());
										frieden.set("players." + target.getName(), list1);
										saveFrieden();
										removeRequest(sender.getName() , target.getName());
									}
								} else {
									List<String> list = new ArrayList<String>();
									list.add(target.getName());
									frieden.set("players." + sender.getName(), list);
									saveFrieden();
									if (frieden.get("players." + target.getName()) != null) {
										List<String> list1 = frieden.getStringList("players." + target.getName());
										list1.add(sender.getName());
										frieden.set("players." + target.getName(), list1);
										saveFrieden();
										removeRequest(sender.getName() , target.getName());
									} else {
										List<String> list1 = new ArrayList<String>();
										list1.add(sender.getName());
										frieden.set("players." + target.getName(), list1);
										saveFrieden();
										removeRequest(sender.getName() , target.getName());
									}
								}		
								target.sendMessage("§aDeine Anfrage von§6 " + sender.getName() + "§a wurde angenommen!");
								sender.sendMessage("§aDu hast die Anfrage von§6 " + target.getName() + "§a angenommen!");
							} else {
								sender.sendMessage("§aDieser Spieler hat dir keine Anfrage gesendet!");
							}
						} else {
							sender.sendMessage("§aDieser Spieler hat dir keine Anfrage gesendet!");
						}
					} else {
						sender.sendMessage("§aDieser Spieler ist nicht Online!");
					}
				}
			}
		}
		
		
		return false;
	}

	private void removeRequest(String name, String name2) {
		frieden.set("einladungen." + name, null);
		saveFrieden();
	}

	private void addRequest(String player, String target) {
		frieden.set("einladungen." + player, target);
		saveFrieden();		
	}

	private void saveFrieden() {
		try {
			frieden.save(friedenfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void loadFrieden() {
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

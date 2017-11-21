package com.nity6000.explosives.commands.utils;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import com.nity6000.explosives.items.Medkit;

public class HealFeed implements CommandExecutor, Listener {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		// Permission checker
		if (!sender.hasPermission("medkit.heal")) {
			sender.sendMessage(ChatColor.RED + "You are lacking permissions");
			return true;
		}
		// Make sure console can't use command
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "You must be a player to use this command!");
			return true;
		}
		Player player = (Player) sender;
		if (args.length == 0) {

			if (Medkit.isMedkitPlaced() == true && Medkit.isMedkitUsed() == false) {
				player.setHealth(20);
				player.setFoodLevel(20);
				player.sendMessage(
						ChatColor.BOLD.toString() + ChatColor.LIGHT_PURPLE + "You have been healed and fed.");
				Medkit.setMedkitUsed(true);
			} else {
				if (Medkit.isMedkitUsed() == true) {
					player.sendMessage(ChatColor.WHITE + "That medkit has already been used");
					player.sendMessage(ChatColor.DARK_RED + "You need to place down another one");
					Medkit.setMedkitUsed(false);
					Medkit.setMedkitPlaced(false);
				} else {
					player.sendMessage(ChatColor.RED + "You must deploy a medkit first");
				}

			}

		}
		return false;

	}
}

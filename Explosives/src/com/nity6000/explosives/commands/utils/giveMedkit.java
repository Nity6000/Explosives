package com.nity6000.explosives.commands.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.nity6000.explosives.items.Medkit;

public class giveMedkit implements CommandExecutor {

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		// Permission checker
		if (!sender.hasPermission("medkit.give")) {
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
			player.sendMessage(ChatColor.WHITE + "Here is your medkit " + ChatColor.RED + "Use it wisely");
			Medkit mk = new Medkit();
			mk.giveItems(player);
			player.playSound(player.getLocation(), Sound.BLOCK_NOTE_HARP, 10, 10);

			return true;
		}
		Player target = Bukkit.getServer().getPlayer(args[0]);
		if (target == null) {
			player.sendMessage(ChatColor.RED + "Player Not Found");
			return false;

		}
		player.sendMessage(ChatColor.DARK_PURPLE + "You may only give your self a medkit");

		return false;
	}

}

package com.nity6000.explosives.commands.bombs;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;

public class NormalBomb implements CommandExecutor {

	private void placeTNT(int x, int y, int z, Player player) {
		TNTPrimed tntPlayer = player.getWorld().spawn(player.getLocation().add(x, y, z), TNTPrimed.class);
		tntPlayer.setFuseTicks(0);
	}

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		// Permission checker
		if (!sender.hasPermission("bomb.normal") && !sender.hasPermission("bomb")) {
			sender.sendMessage(ChatColor.RED + "You are lacking permissions");
			return true;
		}
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "You must be a player to use this command!");
			return true;
		}
		Player player = (Player) sender;
		if (args.length == 0) {

			player.sendMessage(ChatColor.GREEN + "Bombs Away!");

			Location blockLoc = player.getEyeLocation().add(0, -1, 0);
			if (blockLoc.getBlock().getType().equals(Material.AIR)
					|| blockLoc.getBlock().getType().equals(Material.LONG_GRASS)) {
				placeTNT(0, 0, 0, player);
				placeTNT(0, 0, 1, player);
				placeTNT(0, 0, -1, player);
				placeTNT(1, 0, 0, player);
				placeTNT(-1, 0, 0, player);
				placeTNT(0, 1, 0, player);
				player.sendMessage(ChatColor.GREEN + "Boom! ");
			} else {
				player.sendMessage(ChatColor.RED + "Bomb had trouble detonating, "
						+ (ChatColor.GOLD + "make sure the block you are standing in is air! "));
				return false;
			}

			player.playSound(player.getLocation(), Sound.ENTITY_CREEPER_PRIMED, 10, 10);
			return true;
		}
		Player target = Bukkit.getServer().getPlayer(args[0]);
		if (target == null) {
			player.sendMessage(ChatColor.RED + "Player Not Found");
			return false;
		}
		player.sendMessage(ChatColor.GREEN + "Bombs Away!");
		target.sendMessage(ChatColor.GREEN + "Bombs Away!");

		Location blockLoc = target.getEyeLocation().add(0, -1, 0);
		if (blockLoc.getBlock().getType().equals(Material.AIR)
				|| blockLoc.getBlock().getType().equals(Material.LONG_GRASS)) {
			placeTNT(0, 0, 0, target);
			placeTNT(0, 0, 1, target);
			placeTNT(0, 0, -1, target);
			placeTNT(1, 0, 0, target);
			placeTNT(-1, 0, 0, target);
			placeTNT(0, 1, 0, target);
			target.sendMessage(ChatColor.GREEN + "Boom! ");
		} else {
			player.sendMessage(ChatColor.RED + "Bomb had trouble detonating, "
					+ (ChatColor.GOLD + "make sure the block " + player.getName() + " is standing in is air! "));
			target.sendMessage(ChatColor.RED + "Bomb had trouble detonating, "
					+ (ChatColor.GOLD + "make sure the block you are standing in is air! "));
			return false;
		}

		target.playSound(target.getLocation(), Sound.ENTITY_CREEPER_PRIMED, 10, 10);
		return true;

	}

}

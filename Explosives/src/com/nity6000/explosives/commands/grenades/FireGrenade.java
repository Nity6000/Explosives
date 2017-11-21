package com.nity6000.explosives.commands.grenades;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_12_R1.EnumParticle;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldParticles;

public class FireGrenade implements CommandExecutor {

	private void placeFire(int x, int y, int z, Player player) {
		Location blockLoc = player.getLocation().add(x, y, z);
		if (blockLoc.getBlock().getType().equals(Material.AIR)
				|| blockLoc.getBlock().getType().equals(Material.LONG_GRASS)) {
			blockLoc.getBlock().setType(Material.FIRE);
		}
	}

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		// Permission checker
		if (!sender.hasPermission("grenade.fire") && !sender.hasPermission("grenade")) {
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

			player.sendMessage(ChatColor.RED + "Grenade Out!");

			Location blockLoc = player.getEyeLocation().add(0, -1, 0);
			if (blockLoc.getBlock().getType().equals(Material.AIR)
					|| blockLoc.getBlock().getType().equals(Material.LONG_GRASS)) {
				blockLoc.getBlock().setType(Material.FIRE);
				player.sendMessage(ChatColor.RED + "Sizzle! ");

				placeFire(1, 0, 0, player);
				placeFire(-1, 0, 0, player);
				placeFire(0, 0, 1, player);
				placeFire(0, 0, -1, player);

			} else {
				player.sendMessage(ChatColor.RED + "Grenade had trouble detonating, "
						+ (ChatColor.GOLD + "make sure the block you are standing in is air! "));
				return false;
			}
			Location location = player.getLocation();
			float red = 0;
			float green = 0;
			float blue = 0;
			PacketPlayOutWorldParticles particles = new PacketPlayOutWorldParticles(EnumParticle.EXPLOSION_LARGE, true,
					(float) location.getX(), (float) location.getY(), (float) location.getZ(), red, green, blue, 5, 5,
					5);
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(particles);
			player.playSound(player.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 10, 10);
			return true;
		}
		Player target = Bukkit.getServer().getPlayer(args[0]);
		if (target == null) {
			player.sendMessage(ChatColor.RED + "Player Not Found");
			return false;
		}
		player.sendMessage(ChatColor.RED + "Grenade Out!");
		target.sendMessage(ChatColor.RED + "Grenade Out!");

		Location blockLoc = target.getEyeLocation().add(0, -1, 0);
		if (blockLoc.getBlock().getType().equals(Material.AIR)
				|| blockLoc.getBlock().getType().equals(Material.LONG_GRASS)) {
			blockLoc.getBlock().setType(Material.FIRE);
			target.sendMessage(ChatColor.RED + "Bubble! ");

			placeFire(1, 0, 0, target);
			placeFire(-1, 0, 0, target);
			placeFire(0, 0, 1, target);
			placeFire(0, 0, -1, target);

		} else {
			player.sendMessage(ChatColor.RED + "Grenade had trouble detonating, "
					+ (ChatColor.GOLD + "make sure the block " + player.getName() + " is standing in is air! "));
			target.sendMessage(ChatColor.RED + "Grenade had trouble detonating, "
					+ (ChatColor.GOLD + "make sure the block you are standing in is air! "));
			return false;
		}

		Location location = player.getLocation();
		float red = 0;
		float green = 0;
		float blue = 0;
		PacketPlayOutWorldParticles particles = new PacketPlayOutWorldParticles(EnumParticle.EXPLOSION_LARGE, true,
				(float) location.getX(), (float) location.getY(), (float) location.getZ(), red, green, blue, 10, 10,
				10);
		((CraftPlayer) target).getHandle().playerConnection.sendPacket(particles);
		target.playSound(target.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 10, 10);
		return true;

	}

}

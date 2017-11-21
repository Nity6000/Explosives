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

public class WaterGrenade implements CommandExecutor {

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		// Permission checker
		if (!sender.hasPermission("grenade.water") && !sender.hasPermission("grenade")) {
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

			player.sendMessage(ChatColor.BLUE + "Grenade Out!");

			Location blockLoc = player.getEyeLocation().add(0, -1, 0);
			if (blockLoc.getBlock().getType().equals(Material.AIR)
					|| blockLoc.getBlock().getType().equals(Material.LONG_GRASS)) {
				blockLoc.getBlock().setType(Material.WATER);
				player.sendMessage(ChatColor.BLUE + "Splash! ");
			} else {
				player.sendMessage(ChatColor.RED + "Grenade had trouble detonating, "
						+ (ChatColor.GOLD + "make sure the block you are standing in is air! "));
				return false;
			}

			Location location = player.getLocation();
			float red = 0;
			float green = 0;
			float blue = 0;
			PacketPlayOutWorldParticles particles = new PacketPlayOutWorldParticles(EnumParticle.WATER_BUBBLE, true,
					(float) location.getX(), (float) location.getY(), (float) location.getZ(), red, green, blue, 50, 50,
					50);
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(particles);
			player.playSound(player.getLocation(), Sound.BLOCK_WATER_AMBIENT, 10, 10);
			return true;
		}
		Player target = Bukkit.getServer().getPlayer(args[0]);
		if (target == null) {
			player.sendMessage(ChatColor.RED + "Player Not Found");
			return false;
		}
		player.sendMessage(ChatColor.BLUE + "Grenade Out!");
		target.sendMessage(ChatColor.BLUE + "Grenade Out!");

		Location blockLoc = target.getEyeLocation().add(0, -1, 0);
		if (blockLoc.getBlock().getType().equals(Material.AIR)
				|| blockLoc.getBlock().getType().equals(Material.LONG_GRASS)) {
			blockLoc.getBlock().setType(Material.WATER);
			target.sendMessage(ChatColor.BLUE + "Splash! ");
		} else {
			player.sendMessage(ChatColor.RED + "Grenade had trouble detonating, "
					+ (ChatColor.GOLD + "make sure the block " + player.getName() + " is standing in is air! "));
			target.sendMessage(ChatColor.RED + "Grenade had trouble detonating, "
					+ (ChatColor.GOLD + "make sure the block you are standing in is air! "));
			return false;
		}

		Location location = target.getLocation();
		float red = 0;
		float green = 0;
		float blue = 0;
		PacketPlayOutWorldParticles particles = new PacketPlayOutWorldParticles(EnumParticle.WATER_BUBBLE, true,
				(float) location.getX(), (float) location.getY(), (float) location.getZ(), red, green, blue, 10, 10,
				10);
		((CraftPlayer) target).getHandle().playerConnection.sendPacket(particles);
		target.playSound(target.getLocation(), Sound.BLOCK_WATER_AMBIENT, 10, 10);
		return true;

	}

}

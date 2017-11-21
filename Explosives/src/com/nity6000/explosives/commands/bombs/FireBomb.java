package com.nity6000.explosives.commands.bombs;

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

public class FireBomb implements CommandExecutor {

	private void placeFire(int x, int y, int z, Player player) {
		Location blockLoc = player.getEyeLocation().add(x, y, z);
		if (blockLoc.getBlock().getType().equals(Material.AIR)
				|| blockLoc.getBlock().getType().equals(Material.LONG_GRASS)) {
			blockLoc.getBlock().setType(Material.FIRE);
		}
	}

	private void placeNetherrack(int x, int y, int z, Player player) {
		Location blockLoc = player.getEyeLocation().add(x, y, z);
		if (blockLoc.getBlock().getType().equals(Material.AIR)
				|| blockLoc.getBlock().getType().equals(Material.LONG_GRASS)) {
			blockLoc.getBlock().setType(Material.NETHERRACK);
		}
	}

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!sender.hasPermission("bomb.fire") && !sender.hasPermission("bomb")) {
			sender.sendMessage(ChatColor.RED + "You are lacking permissions");
			return true;
		}
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "You must be a player to use this command!");
			return true;
		}
		Player player = (Player) sender;
		if (args.length == 0) {

			player.sendMessage(ChatColor.RED + "Bombs Away!");

			Location blockLoc = player.getEyeLocation().add(0, -1, 0);
			if (blockLoc.getBlock().getType().equals(Material.AIR)
					|| blockLoc.getBlock().getType().equals(Material.LONG_GRASS)) {
				blockLoc.getBlock().setType(Material.FIRE);
				player.sendMessage(ChatColor.RED + "Sizzle! ");
			} else {
				player.sendMessage(ChatColor.DARK_RED + "Grenade had trouble detonating, "
						+ (ChatColor.GOLD + "make sure the block you are standing in is air! "));
				return false;
			}
			// -------------------------------------------------------------------------------------------------------------------------------\\
			blockLoc = player.getEyeLocation().add(0, -2, 0);
			if (blockLoc.getBlock().getType().equals(Material.SAND)
					|| blockLoc.getBlock().getType().equals(Material.GRASS)) {
				blockLoc.getBlock().setType(Material.MAGMA);
			}
			// -------------------------------------------------------------------------------------------------------------------------------\\

			placeNetherrack(1, -1, 0, player);

			placeNetherrack(-1, -1, 0, player);

			placeNetherrack(0, -1, -1, player);

			placeNetherrack(0, -1, 1, player);

			// -------------------------------------------------------------------------------------------------------------------------------\\

			placeFire(0, 0, -1, player);
			placeFire(0, 0, 1, player);
			placeFire(1, 0, 0, player);
			placeFire(-1, 0, 0, player);
			placeFire(-2, -1, 0, player);
			placeFire(2, -1, 0, player);			
			placeFire(0, -1, 2, player);
			placeFire(0, -1, -2, player);
			placeFire(1, -1, 1, player);
			placeFire(1, -1, -1, player);
			placeFire(-1, -1, -1, player);
			placeFire(-1, -1, 1, player);
			Location location = player.getLocation();
			float red = 0;
			float green = 0;
			float blue = 0;
			PacketPlayOutWorldParticles particles = new PacketPlayOutWorldParticles(EnumParticle.EXPLOSION_HUGE, true,
					(float) location.getX(), (float) location.getY(), (float) location.getZ(), red, green, blue, 10, 10,
					10);
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(particles);
			player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 10, 10);
			return true;
		}
		Player target = Bukkit.getServer().getPlayer(args[0]);
		if (target == null) {
			player.sendMessage(ChatColor.RED + "Player Not Found");
			return false;
		}
		player.sendMessage(ChatColor.RED + "Bombs Away!");
		target.sendMessage(ChatColor.RED + "Bombs Away!");

		Location blockLoc = target.getEyeLocation().add(0, -1, 0);
		if (blockLoc.getBlock().getType().equals(Material.AIR)
				|| blockLoc.getBlock().getType().equals(Material.LONG_GRASS)) {
			blockLoc.getBlock().setType(Material.FIRE);
			target.sendMessage(ChatColor.RED + "Sizzle! ");
		} else {
			player.sendMessage(ChatColor.RED + "Grenade had trouble detonating, "
					+ (ChatColor.GOLD + "make sure the block " + player.getName() + " is standing in is air! "));
			target.sendMessage(ChatColor.RED + "Grenade had trouble detonating, "
					+ (ChatColor.GOLD + "make sure the block you are standing in is air! "));
			return false;
		}
		// -------------------------------------------------------------------------------------------------------------------------------\\
		blockLoc = player.getEyeLocation().add(0, -2, 0);
		if (blockLoc.getBlock().getType().equals(Material.SAND)
				|| blockLoc.getBlock().getType().equals(Material.GRASS)) {
			blockLoc.getBlock().setType(Material.MAGMA);
		}
		// -------------------------------------------------------------------------------------------------------------------------------\\

		placeNetherrack(1, -1, 0, target);

		placeNetherrack(-1, -1, 0, target);

		placeNetherrack(0, -1, -1, target);

		placeNetherrack(0, -1, 1, target);

		// -------------------------------------------------------------------------------------------------------------------------------\\

		placeFire(0, 0, -1, target);
		placeFire(0, 0, 1, target);
		placeFire(1, 0, 0, target);
		placeFire(-1, 0, 0, target);
		placeFire(-2, -1, 0, target);
		placeFire(2, -1, 0, target);
		placeFire(0, -1, 2, target);
		placeFire(0, -1, -2, target);
		placeFire(1, -1, 1, target);
		placeFire(1, -1, -1, target);
		placeFire(-1, -1, -1, target);
		placeFire(-1, -1, 1, target);
		Location location = target.getLocation();
		float red = 0;
		float green = 0;
		float blue = 0;
		PacketPlayOutWorldParticles particles = new PacketPlayOutWorldParticles(EnumParticle.EXPLOSION_HUGE, true,
				(float) location.getX(), (float) location.getY(), (float) location.getZ(), red, green, blue, 10, 10, 10);
		((CraftPlayer) target).getHandle().playerConnection.sendPacket(particles);
		target.playSound(target.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 10, 10);
		return true;

	}

}

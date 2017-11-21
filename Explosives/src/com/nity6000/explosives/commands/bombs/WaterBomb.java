package com.nity6000.explosives.commands.bombs;

//Importing API
import org.bukkit.Bukkit;

//Importing Assets
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

//Importing command parts
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

// Importing Particle Effects
import net.minecraft.server.v1_12_R1.EnumParticle;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldParticles;

//Main Class
public class WaterBomb implements CommandExecutor {

	// Method that places down water blocks
	private void placeWater(int x, int y, int z, Player player) {
		Location blockLoc = player.getLocation().add(x, y, z);
		if (blockLoc.getBlock().getType().equals(Material.AIR)
				|| blockLoc.getBlock().getType().equals(Material.LONG_GRASS)) {
			blockLoc.getBlock().setType(Material.WATER);
		}
	}

	// Command
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		// Permission checker
		if (!sender.hasPermission("bomb.normal") && !sender.hasPermission("bomb")) {
			sender.sendMessage(ChatColor.RED + "You are lacking permissions");
			return true;
		}
		// Make sure console can't use command
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "You must be a player to use this command!");
			return true;
		}
		// If no player args wwere entered
		Player player = (Player) sender;
		if (args.length == 0) {

			// Sending the bomb
			player.sendMessage(ChatColor.BLUE + "Bombs Away!");

			// Selecting player's location
			Location blockLoc = player.getEyeLocation().add(0, -1, 0);
			// Anti-Grief check
			if (blockLoc.getBlock().getType().equals(Material.AIR)
					|| blockLoc.getBlock().getType().equals(Material.LONG_GRASS)) {
				// Setting first block
				blockLoc.getBlock().setType(Material.WATER);
				player.sendMessage(ChatColor.BLUE + "Splash! ");
			} else {
				// Error message
				player.sendMessage(ChatColor.RED + "Bomb had trouble detonating, "
						+ (ChatColor.GOLD + "make sure the block you are standing in is air! "));
				return false;
			}
			// Placing down the water
			placeWater(1, 0, 0, player);
			placeWater(-1, 0, 0, player);
			placeWater(0, 0, -1, player);
			placeWater(0, 0, 1, player);
			placeWater(0, 3, 0, player);
			// Casting Player to Location
			Location location = player.getLocation();
			float red = 0;
			float green = 0;
			float blue = 0;
			// The Particle Effects
			PacketPlayOutWorldParticles particles = new PacketPlayOutWorldParticles(EnumParticle.WATER_BUBBLE, true,
					(float) location.getX(), (float) location.getY(), (float) location.getZ(), red, green, blue, 50, 50,
					50);
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(particles);
			// The Sound Effect
			player.playSound(player.getLocation(), Sound.WEATHER_RAIN_ABOVE, 10, 10);
			return true;
		}
		// If you enter a name of the player after
		Player target = Bukkit.getServer().getPlayer(args[0]);
		if (target == null) {
			// Error Message
			player.sendMessage(ChatColor.RED + "Player Not Found");
			return false;
		}
		// Sending the bombs
		player.sendMessage(ChatColor.BLUE + "Bombs Away!");
		target.sendMessage(ChatColor.BLUE + "Bombs Away!");

		// Selecting the position of target
		Location blockLoc = target.getEyeLocation().add(0, -1, 0);
		// Anti-Grief Checker
		if (blockLoc.getBlock().getType().equals(Material.AIR)
				|| blockLoc.getBlock().getType().equals(Material.LONG_GRASS)) {
			blockLoc.getBlock().setType(Material.WATER);

			target.sendMessage(ChatColor.BLUE + "Splash! ");
		} else {
			// Error message for player
			player.sendMessage(ChatColor.RED + "Bomb had trouble detonating, "
					+ (ChatColor.GOLD + "make sure the block " + player.getName() + " is standing in is air! "));
			// Error message for target
			target.sendMessage(ChatColor.RED + "Bomb had trouble detonating, "
					+ (ChatColor.GOLD + "make sure the block you are standing in is air! "));
			return false;
		}
		// Placing the water
		placeWater(1, 0, 0, target);
		placeWater(-1, 0, 0, target);
		placeWater(0, 0, -1, target);
		placeWater(0, 0, 1, target);
		placeWater(0, 3, 0, target);
		// Casting the location to target
		Location location = target.getLocation();
		float red = 0;
		float green = 0;
		float blue = 0;
		// Particle Effect
		PacketPlayOutWorldParticles particles = new PacketPlayOutWorldParticles(EnumParticle.WATER_BUBBLE, true,
				(float) location.getX(), (float) location.getY(), (float) location.getZ(), red, green, blue, 10, 10,
				10);
		// Play Particle Effect
		((CraftPlayer) target).getHandle().playerConnection.sendPacket(particles);
		// Sound Effect
		target.playSound(target.getLocation(), Sound.WEATHER_RAIN_ABOVE, 10, 10);
		return true;

	}

}

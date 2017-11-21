package com.nity6000.explosives.events.block;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import com.nity6000.explosives.items.Medkit;

public class BlockPlace implements Listener {
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onBlockPlaced(BlockPlaceEvent e) {

		Player player = e.getPlayer();

		if (e.getBlockPlaced().getType() == Material.IRON_PLATE) {

			if (e.getPlayer().getItemInHand().getItemMeta().getDisplayName().toString().toLowerCase()
					.contains(ChatColor.RED + "Medkit")) {

				player.sendMessage("Sauce");

			} else {

				player.sendMessage(ChatColor.GOLD + "You can now heal your self");
				player.playSound(player.getLocation(), Sound.BLOCK_NOTE_XYLOPHONE, 10, 10);
				Medkit.medkitPlaced = true;
				Medkit.setMedkitUsed(false);

			}

		}
	}
}

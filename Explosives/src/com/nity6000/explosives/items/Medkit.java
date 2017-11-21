package com.nity6000.explosives.items;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Medkit implements Listener {

	public static boolean medkitPlaced= true;

	public static boolean isMedkitPlaced() {
		return medkitPlaced;
	}

	public static void setMedkitPlaced(boolean medkitPlaced) {
		Medkit.medkitPlaced = medkitPlaced;
	}

	public static boolean isMedkitUsed() {
		return medkitUsed;
	}

	public static void setMedkitUsed(boolean medkitUsed) {
		Medkit.medkitUsed = medkitUsed;
	}

	public static boolean medkitUsed;

	public void giveItems(Player player) {

		ItemStack item = new ItemStack(Material.IRON_PLATE, 1, (short) 5);
		ItemMeta meta = item.getItemMeta();

		
		meta.setDisplayName(ChatColor.RED + "Medkit");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(ChatColor.WHITE + "Heals and Feeds");
		meta.setLore(lore);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		item.setItemMeta(meta);

		player.getInventory().addItem(item);

	}

	
}
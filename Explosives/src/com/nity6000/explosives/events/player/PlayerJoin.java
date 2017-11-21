package com.nity6000.explosives.events.player;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.nity6000.explosives.Main;

public class PlayerJoin implements Listener {

	private Main plugin;

	public PlayerJoin(Main pl) {
		plugin = pl;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		String versionIdentifier = ChatColor.translateAlternateColorCodes('&',
				plugin.getConfig().getString("Version Identifier"));

		Player player = event.getPlayer();
		player.sendMessage(versionIdentifier);
	}
}

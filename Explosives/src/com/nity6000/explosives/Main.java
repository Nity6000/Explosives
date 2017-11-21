package com.nity6000.explosives;

import java.util.logging.Logger;

import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.nity6000.explosives.commands.bombs.FireBomb;
import com.nity6000.explosives.commands.bombs.LavaBomb;
import com.nity6000.explosives.commands.bombs.NormalBomb;
import com.nity6000.explosives.commands.bombs.WaterBomb;
import com.nity6000.explosives.commands.grenades.FireGrenade;
import com.nity6000.explosives.commands.grenades.LavaGrenade;
import com.nity6000.explosives.commands.grenades.NormalGrenade;
import com.nity6000.explosives.commands.grenades.WaterGrenade;
import com.nity6000.explosives.commands.utils.HealFeed;
import com.nity6000.explosives.commands.utils.giveMedkit;
import com.nity6000.explosives.events.block.BlockBreak;
import com.nity6000.explosives.events.block.BlockPlace;
import com.nity6000.explosives.events.player.PlayerChat;
import com.nity6000.explosives.events.player.PlayerJoin;

public class Main extends JavaPlugin implements Listener {

	public void onEnable() {
		PluginDescriptionFile pdfFile = getDescription();
		Logger logger = getLogger();
		this.getServer().getPluginManager().registerEvents(this, this);
		registerCommands();
		registerEvents();
		registerConfig();

		logger.info(pdfFile.getName() + " has been enabled (V." + pdfFile.getVersion() + ")");
	}

	public void onDisable() {
		PluginDescriptionFile pdfFile = getDescription();
		Logger logger = getLogger();

		logger.info(pdfFile.getName() + " has been disabled (V." + pdfFile.getVersion() + ")");
	}

	public void registerCommands() {
		getCommand("firegrenade").setExecutor(new FireGrenade());
		getCommand("watergrenade").setExecutor(new WaterGrenade());
		getCommand("grenade").setExecutor(new NormalGrenade());
		getCommand("lavagrenade").setExecutor(new LavaGrenade());
		getCommand("firebomb").setExecutor(new FireBomb());
		getCommand("waterbomb").setExecutor(new WaterBomb());
		getCommand("medkit").setExecutor(new giveMedkit());
		getCommand("medkitheal").setExecutor(new HealFeed());
		getCommand("lavabomb").setExecutor(new LavaBomb());
		getCommand("bomb").setExecutor(new NormalBomb());

	}

	public void registerEvents() {
		PluginManager pm = getServer().getPluginManager();

		pm.registerEvents(new BlockBreak(), this);
		pm.registerEvents(new PlayerChat(), this);
		pm.registerEvents(new BlockPlace(), this);
		pm.registerEvents(new PlayerJoin(this), this);

	}

	private void registerConfig() {
		getConfig().options().copyDefaults(true);
		saveDefaultConfig();
	}
}

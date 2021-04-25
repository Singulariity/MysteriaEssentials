package com.mysteria.essentials.listeners;

import com.mysteria.essentials.EssentialsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerSpawnListeners implements Listener {

	public PlayerSpawnListeners() {
		Bukkit.getPluginManager().registerEvents(this, EssentialsPlugin.getInstance());
	}

	@EventHandler
	private void onPlayerFirstJoin(PlayerJoinEvent e) {

		if (!e.getPlayer().hasPlayedBefore()) {
			EssentialsPlugin.getPlayerManager().teleportSpawn(e.getPlayer());
		}

	}

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent e) {
		Location bedLoc = e.getPlayer().getBedSpawnLocation();
		if (bedLoc != null) {
			e.setRespawnLocation(bedLoc);
			return;
		}

		Location spawnLoc = EssentialsPlugin.getPlayerManager().getSpawnLocation();
		if (spawnLoc != null) {
			e.setRespawnLocation(spawnLoc);
		}

	}

}

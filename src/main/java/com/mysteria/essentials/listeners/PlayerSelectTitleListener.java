package com.mysteria.essentials.listeners;

import com.mysteria.essentials.EssentialsPlugin;
import com.mysteria.titles.events.PlayerSelectTitleEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerSelectTitleListener implements Listener {

	public PlayerSelectTitleListener() {
		Bukkit.getPluginManager().registerEvents(this, EssentialsPlugin.getInstance());
	}

	@EventHandler
	private void onTitleSelect(PlayerSelectTitleEvent e) {

		EssentialsPlugin.getPlayerManager().updateListName(e.getPlayer());

	}

}

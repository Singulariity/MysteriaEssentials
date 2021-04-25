package com.mysteria.essentials.listeners;

import com.mysteria.essentials.EssentialsPlugin;
import com.mysteria.utils.NamedColor;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

	public PlayerQuitListener() {
		Bukkit.getPluginManager().registerEvents(this, EssentialsPlugin.getInstance());
	}

	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		Player p = e.getPlayer();

		Component message = Component.text()
				.append(Component.text("[", NamedColor.WIZARD_GREY))
				.append(Component.text("-", NamedColor.TURBO))
				.append(Component.text("] ", NamedColor.WIZARD_GREY))
				.append(Component.text(p.getName(), NamedColor.SOARING_EAGLE))
				.build();
		e.quitMessage(message);
	}
}

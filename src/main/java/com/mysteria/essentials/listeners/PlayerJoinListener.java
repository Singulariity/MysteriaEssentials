package com.mysteria.essentials.listeners;

import com.mysteria.essentials.EssentialsPlugin;
import com.mysteria.essentials.playermanager.PlayerManager;
import com.mysteria.utils.NamedColor;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

	public PlayerJoinListener() {
		Bukkit.getPluginManager().registerEvents(this, EssentialsPlugin.getInstance());
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();

		PlayerManager manager = EssentialsPlugin.getPlayerManager();

		manager.updateListName(p);
		manager.updateHeader(p);
		manager.updateFooter(p);

		Component message = Component.text()
				.append(Component.text("[", NamedColor.WIZARD_GREY))
				.append(Component.text("+", NamedColor.DOWNLOAD_PROGRESS))
				.append(Component.text("] ", NamedColor.WIZARD_GREY))
				.append(Component.text(p.getName(), NamedColor.SOARING_EAGLE))
				.build();
		e.joinMessage(message);

	}

}

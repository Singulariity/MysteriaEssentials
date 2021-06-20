package com.mysteria.essentials.listeners;

import com.mysteria.essentials.EssentialsPlugin;
import com.mysteria.utils.MysteriaUtils;
import com.mysteria.utils.NamedColor;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.HashMap;

public class ChatListener implements Listener {

	private final HashMap<Player, Long> globalMessageCoolDowns = new HashMap<>();
	private final HashMap<Player, Long> messageCoolDowns = new HashMap<>();

	public ChatListener() {
		Bukkit.getPluginManager().registerEvents(this, EssentialsPlugin.getInstance());
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onPlayerChat(AsyncChatEvent e) {

		Player p = e.getPlayer();

		if (MysteriaUtils.translateToString(e.message()).charAt(0) == '!') {

			long cooldown = globalMessageCoolDowns.getOrDefault(p, 0L);
			if (MysteriaUtils.checkCooldown(cooldown) || p.hasPermission("essentialsm.cdbypass.globalchat")) {
				globalMessageCoolDowns.put(p, MysteriaUtils.createCooldown(30));
			} else {
				Component message = Component.translatable(
						"mystery.message.chat.cooldown_global", NamedColor.CARMINE_PINK,
						Component.text(MysteriaUtils.cooldownString(cooldown), NamedColor.TURBO));
				MysteriaUtils.sendMessage(p, message);
				e.setCancelled(true);
				return;
			}

		} else {

			long cooldown = messageCoolDowns.getOrDefault(p, 0L);
			if (MysteriaUtils.checkCooldown(cooldown) || p.hasPermission("essentialsm.cdbypass.chat")) {
				messageCoolDowns.put(p, MysteriaUtils.createCooldown(2));
				e.viewers().removeIf(viewer -> {
					if (viewer instanceof Player) {
						Player viewerP = (Player) viewer;
						return p.getWorld() != viewerP.getWorld() || p.getLocation().distance(viewerP.getLocation()) > 75;
					}
					return false;
				});
			} else {
				Component message = Component.translatable(
						"mystery.message.chat.cooldown_normal", NamedColor.CARMINE_PINK,
						Component.text(MysteriaUtils.cooldownString(cooldown), NamedColor.TURBO));
				MysteriaUtils.sendMessage(p, message);
				e.setCancelled(true);
				return;
			}

		}

		e.renderer(EssentialsPlugin.getChatManager().getdefaultRenderer());

	}
}

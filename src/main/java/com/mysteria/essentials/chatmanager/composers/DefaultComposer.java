package com.mysteria.essentials.chatmanager.composers;

import com.mysteria.essentials.EssentialsPlugin;
import com.mysteria.essentials.chatmanager.ChatManager;
import com.mysteria.essentials.playermanager.PlayerManager;
import com.mysteria.utils.MysteriaUtils;
import com.mysteria.utils.NamedColor;
import io.papermc.paper.chat.ChatComposer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DefaultComposer implements ChatComposer {

	public DefaultComposer() {
		ChatManager chatManager = EssentialsPlugin.getChatManager();
		if (chatManager != null && chatManager.getDefaultComposer() != null) {
			throw new IllegalStateException();
		}
	}

	private final Component globalMessagePREFIX = Component.text()
			.append(Component.text("[", NamedColor.WIZARD_GREY))
			.append(Component.text("G", NamedColor.QUINCE_JELLY).decorate(TextDecoration.BOLD))
			.append(Component.text("] ", NamedColor.WIZARD_GREY))
			.hoverEvent(HoverEvent.hoverEvent(
					HoverEvent.Action.SHOW_TEXT,
					Component.translatable("mystery.message.chat.global_message.hover", NamedColor.SOARING_EAGLE)))
			.build();

	@Override
	public @NotNull Component composeChat(@NotNull Player p, @NotNull Component displayName, @NotNull Component message) {

		PlayerManager manager = EssentialsPlugin.getPlayerManager();

		String messageString = MysteriaUtils.translateToString(message);
		boolean isGlobal;

		if (messageString.charAt(0) == '!') {
			isGlobal = true;
			messageString = messageString.substring(1);
		} else {
			isGlobal = false;
		}

		// Date "formatter.format(date)"
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		String dateString = formatter.format(date);

		Component globalComponent = Component.text("");
		if (isGlobal) {
			globalComponent = globalMessagePREFIX;
		}
		Component playerTitle = Component.text()
				.append(manager.getTitle(p))
				.color(manager.getTitleColor(p))
				.build();
		Component playerName = Component.text(" " + p.getName(), NamedTextColor.GRAY);
		TextColor playerMessageColor = manager.getChatColor(p);
		Component hoverMessage = Component.text(dateString, NamedTextColor.GRAY)
				.append(Component.text("\n\n"))
				.append(Component.translatable("mystery.message.chat.send_message.hover", NamedColor.SKIRRET_GREEN));

		String commandSuggest = "/w " + p.getName() + " ";

		Component finalMessage = Component.text(messageString, playerMessageColor);

		ItemStack heldItem = p.getInventory().getItemInMainHand();
		if (messageString.contains("%held%")) {
			Component replacement = null;
			if (heldItem.getType() != Material.AIR) {
				if (heldItem.getType() == Material.COMPASS && !p.hasPermission("survivalplus.bypass")) {
					Component msg = Component.translatable("mystery.message.chat.mirror.compass_not_allowed",
							NamedColor.CARMINE_PINK);
					MysteriaUtils.sendMessage(p, msg);
				} else {
					replacement = MysteriaUtils.showItemComponent(heldItem);
				}
			} else {
				Component msg = Component.translatable("mystery.message.chat.mirror.empty_hand",
						NamedColor.CARMINE_PINK);
				MysteriaUtils.sendMessage(p, msg);
			}
			finalMessage = finalMessage.replaceText(TextReplacementConfig.builder()
					.match("%held%")
					.replacement(replacement)
					.once()
					.build());
		}

		return Component.text()
				.append(globalComponent)
				.append(playerTitle
						.append(playerName)
						.hoverEvent(HoverEvent.hoverEvent(HoverEvent.Action.SHOW_TEXT, hoverMessage))
						.clickEvent(ClickEvent.suggestCommand(commandSuggest))
				)
				.append(Component.text(": ", NamedTextColor.DARK_GRAY))
				.append(finalMessage)
				.build();
	}
}
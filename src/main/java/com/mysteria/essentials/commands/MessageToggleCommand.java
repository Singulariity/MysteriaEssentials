package com.mysteria.essentials.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.mysteria.essentials.EssentialsPlugin;
import com.mysteria.utils.MysteriaUtils;
import com.mysteria.utils.NamedColor;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

@CommandAlias("msgtoggle|mtoggle|messagetoggle")
public class MessageToggleCommand extends BaseCommand {

	@Default
	@Description("Toggle on/off private messages.")
	public void onCommand(Player player) {
		// UUID of sender
		UUID uuid = player.getUniqueId();
		// Message toggles
		List<UUID> toggles = EssentialsPlugin.getPlayerManager().getMsgToggles();

		if (toggles.contains(uuid)) {
			toggles.remove(uuid);
			Component message = Component.translatable("mystery.message.command.whisper.toggle_off", NamedColor.TURBO);
			MysteriaUtils.sendMessage(player, message);
		} else {
			toggles.add(uuid);
			Component message = Component.translatable("mystery.message.command.whisper.toggle_on", NamedColor.TURBO);
			MysteriaUtils.sendMessage(player, message);
		}
	}

}

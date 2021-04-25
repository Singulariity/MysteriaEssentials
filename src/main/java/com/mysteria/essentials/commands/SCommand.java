package com.mysteria.essentials.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.mysteria.utils.MysteriaUtils;
import com.mysteria.utils.NamedColor;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@CommandAlias("s")
public class SCommand extends BaseCommand {

	@Default
	@CommandPermission("essentials.teleportothers")
	@CommandCompletion("@players @nothing")
	@Syntax("<player>")
	@Description("Teleport a players to you.")
	public void onCommand(Player player, String[] args) {

		// Returns if no args
		if (args.length != 1) {
			Component message = Component.translatable("mystery.message.command.usage", NamedColor.TURBO,
					Component.text("/s <player>", NamedColor.SOARING_EAGLE));
			MysteriaUtils.sendMessage(player, message);
			return;
		}

		// Returns if player is not found
		Player found = Bukkit.getPlayer(args[0]);
		if (found == null) {
			Component message = Component.translatable("mystery.message.command.not_found_player", NamedColor.CARMINE_PINK);
			MysteriaUtils.sendMessage(player, message);
			return;
		}

		found.teleport(player);
	}

}

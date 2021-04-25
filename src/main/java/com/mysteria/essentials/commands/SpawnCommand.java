package com.mysteria.essentials.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.mysteria.essentials.EssentialsPlugin;
import com.mysteria.utils.MysteriaUtils;
import com.mysteria.utils.NamedColor;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;

@CommandAlias("spawn")
public class SpawnCommand extends BaseCommand {

	@Default
	@CommandPermission("essentials.spawn")
	@CommandCompletion("@players @nothing")
	@Description("Teleports to the spawn.")
	public void onCommand(Player sender, String[] args) {
		Player p;

		if (args.length >= 1) {
			p = Bukkit.getPlayer(args[0]);

			// Returns if player is not found
			if (p == null) {
				Component message = Component.translatable("mystery.message.command.not_found_player", NamedColor.CARMINE_PINK);
				MysteriaUtils.sendMessage(sender, message);
				return;
			}
		} else {
			p = sender;
		}

		if (!EssentialsPlugin.getPlayerManager().teleportSpawn(p)) {
			MysteriaUtils.sendMessageRed(sender, "Spawn is not set yet.");
		}

	}

}

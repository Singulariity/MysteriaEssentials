package com.mysteria.essentials.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.mysteria.utils.MysteriaUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;

@CommandAlias("spawnmob")
public class SpawnMobCommand extends BaseCommand {

	@Default
	@CommandPermission("essentials.spawnmob")
	@CommandCompletion("@mobs @nothing")
	@Description("Spawns a mob.")
	public void onCommand(Player player, String[] args) {

		if (args.length != 1) {
			return;
		}

		try	{
			EntityType type = EntityType.valueOf(args[0].toUpperCase());
			player.getWorld().spawnEntity(player.getLocation(), type, CreatureSpawnEvent.SpawnReason.NATURAL);
		} catch (IllegalArgumentException ignored) {
			MysteriaUtils.sendMessageRed(player, "Entity type is not valid.");
		}

	}

}

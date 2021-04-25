package com.mysteria.essentials.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import com.mysteria.essentials.EssentialsPlugin;
import com.mysteria.utils.MysteriaUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

@CommandAlias("setspawn")
public class SetSpawnCommand extends BaseCommand {

	@Default
	@CommandPermission("essentials.setspawn")
	@Description("Sets server's default spawn point.")
	public void onCommand(Player player) {

		FileConfiguration config = EssentialsPlugin.getInstance().getConfig();

		config.set("spawn.world", player.getWorld().getName());
		config.set("spawn.x", player.getLocation().getX());
		config.set("spawn.y", player.getLocation().getY());
		config.set("spawn.z", player.getLocation().getZ());
		config.set("spawn.yaw", player.getLocation().getYaw());
		config.set("spawn.pitch", player.getLocation().getPitch());
		EssentialsPlugin.getInstance().saveConfig();

		MysteriaUtils.sendMessageGreen(player, "Spawn set.");

	}

}

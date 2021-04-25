package com.mysteria.essentials.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.mysteria.utils.MysteriaUtils;
import com.mysteria.utils.NamedColor;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

@CommandAlias("applygamerules|agr")
public class ApplyGameRulesCommand extends BaseCommand {

	@Default
	@CommandPermission("essentials.applygamerules")
	@CommandCompletion("@worlds @nothing")
	@Syntax("<world>")
	@Description("Applying game rules on specified world.")
	public void onCommand(CommandSender sender, String[] args) {

		if (args.length != 1) {
			Component message = Component.translatable("mystery.message.command.usage", NamedColor.TURBO,
					Component.text("/applygamerules <world>", NamedColor.SOARING_EAGLE));
			MysteriaUtils.sendMessage(sender, message);
			return;
		}

		World world = Bukkit.getWorld(args[0]);
		if (world == null) {
			Component message = Component.translatable("mystery.message.command.not_found_world", NamedColor.CARMINE_PINK);
			MysteriaUtils.sendMessage(sender, message);
			return;
		}

		world.setGameRule(GameRule.SPECTATORS_GENERATE_CHUNKS, false);
		world.setGameRule(GameRule.UNIVERSAL_ANGER, true);
		//world.setGameRule(GameRule.PLAYERS_SLEEPING_PERCENTAGE, 75);
		world.setGameRule(GameRule.FORGIVE_DEAD_PLAYERS, false);
		world.setGameRule(GameRule.DISABLE_RAIDS, true);
		world.setGameRule(GameRule.DISABLE_ELYTRA_MOVEMENT_CHECK, true);
		world.setGameRule(GameRule.REDUCED_DEBUG_INFO, true);
		world.setGameRule(GameRule.DO_FIRE_TICK, false);

		Component message = Component.translatable("mystery.message.command.success", NamedColor.SKIRRET_GREEN);
		MysteriaUtils.sendMessage(sender, message);
	}

}

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

import java.util.ArrayList;

@CommandAlias("applygamerules|agr")
public class ApplyGameRulesCommand extends BaseCommand {

	@Default
	@CommandPermission("essentials.applygamerules")
	@CommandCompletion("@worlds @nothing")
	@Syntax("[world]")
	@Description("Applying game rules on worlds.")
	public void onCommand(CommandSender sender, String[] args) {

		ArrayList<World> worlds = new ArrayList<>();

		if (args.length >= 1) {
			World world = Bukkit.getWorld(args[0]);
			if (world == null) {
				Component message = Component.translatable("mystery.message.command.not_found_world", NamedColor.CARMINE_PINK);
				MysteriaUtils.sendMessage(sender, message);
				return;
			} else {
				worlds.add(world);
			}
		} else {
			worlds.addAll(Bukkit.getWorlds());
//			Component message = Component.translatable("mystery.message.command.usage", NamedColor.TURBO,
//					Component.text("/applygamerules <world>", NamedColor.SOARING_EAGLE));
//			MysteriaUtils.sendMessage(sender, message);
//			return;
		}

		for (World w : worlds) {
			w.setGameRule(GameRule.SPECTATORS_GENERATE_CHUNKS, false);
			w.setGameRule(GameRule.UNIVERSAL_ANGER, true);
			w.setGameRule(GameRule.PLAYERS_SLEEPING_PERCENTAGE, 75);
			w.setGameRule(GameRule.FORGIVE_DEAD_PLAYERS, false);
			w.setGameRule(GameRule.DISABLE_RAIDS, true);
			w.setGameRule(GameRule.DO_TRADER_SPAWNING, false);
			w.setGameRule(GameRule.DISABLE_ELYTRA_MOVEMENT_CHECK, true);
			w.setGameRule(GameRule.REDUCED_DEBUG_INFO, true);
			w.setGameRule(GameRule.DO_FIRE_TICK, false);
			w.setGameRule(GameRule.MAX_ENTITY_CRAMMING, 3);
			//w.setDifficulty(Difficulty.HARD);
		}

		Component message = Component.translatable("mystery.message.command.success", NamedColor.SKIRRET_GREEN);
		MysteriaUtils.sendMessage(sender, message);
	}

}

package com.mysteria.essentials.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.mysteria.utils.MysteriaUtils;
import com.mysteria.utils.NamedColor;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

@CommandAlias("heal")
public class HealCommand extends BaseCommand {

	@Default
	@CommandPermission("essentials.heal")
	@CommandCompletion("@players @nothing")
	@Syntax("[player]")
	@Description("Heal.")
	public void onCommand(CommandSender sender, String[] args) {
		Player player;

		if (args.length == 1) {
			player = Bukkit.getPlayer(args[0]);

			// Returns if player is not found
			if (player == null) {
				Component message = Component.translatable("mystery.message.command.not_found_player", NamedColor.CARMINE_PINK);
				MysteriaUtils.sendMessage(sender, message);
				return;
			}
		} else {

			// Returns if sender is not a player
			if (!(sender instanceof Player)) {
				Component message = Component.translatable("mystery.message.command.player_only", NamedColor.HARLEY_DAVIDSON_ORANGE);
				MysteriaUtils.sendMessage(sender, message);
				return;
			}
			player = (Player) sender;
		}

		AttributeInstance attributeInstance = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
		if (attributeInstance == null) return;
		player.setHealth(attributeInstance.getBaseValue());
		player.setFoodLevel(20);
		player.setFireTicks(0);
		for (PotionEffect effect : player.getActivePotionEffects()) {
			player.removePotionEffect(effect.getType());
		}

		MysteriaUtils.sendMessageGreen(sender, player.getName() + " healed.");
	}


}

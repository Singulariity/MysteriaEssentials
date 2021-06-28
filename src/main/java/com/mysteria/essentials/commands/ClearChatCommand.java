package com.mysteria.essentials.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import com.mysteria.utils.MysteriaUtils;
import com.mysteria.utils.NamedColor;
import net.kyori.adventure.text.Component;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

@CommandAlias("clearchat|cc")
public class ClearChatCommand extends BaseCommand {

	@Default
	@CommandPermission("essentials.clearchat")
	@Description("Clear chat from all players.")
	public void onCommand(CommandSender sender) {

		ArrayList<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
		players.removeIf(p -> p.hasPermission("essentials.bypass"));

		Component empty = Component.text(StringUtils.repeat(" \n", 100));
		Component info = Component.translatable("mystery.message.chat.clean",
				NamedColor.BEEKEEPER, Component.text(sender.getName(), NamedColor.CARMINE_PINK));
		for (Player p : players) {
			p.sendMessage(empty);
		}
		MysteriaUtils.broadcastMessage(info);
		MysteriaUtils.broadcastMessage(Component.text(" "));

	}

}

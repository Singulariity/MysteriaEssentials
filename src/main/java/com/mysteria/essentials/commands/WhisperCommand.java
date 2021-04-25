package com.mysteria.essentials.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.mysteria.essentials.EssentialsPlugin;
import com.mysteria.utils.MysteriaUtils;
import com.mysteria.utils.NamedColor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

@CommandAlias("whisper|w|t|tell|m|msg|message")
public class WhisperCommand extends BaseCommand {

	@Default
	@Syntax("<player> <message>")
	@CommandCompletion("@players @nothing")
	@Description("Sends private message to another player.")
	public void onCommand(CommandSender sender, String[] args) {

		// Returns if length of args less than 2
		if (args.length < 2) {
			Component message = Component.translatable("mystery.message.command.usage", NamedColor.TURBO,
					Component.text("/w <player> <message>", NamedColor.SOARING_EAGLE));
			MysteriaUtils.sendMessage(sender, message);
			return;
		}

		// Returns if receiver is not found
		Player receiver = Bukkit.getPlayer(args[0]);
		if (receiver == null) {
			Component message = Component.translatable("mystery.message.command.not_found_player", NamedColor.CARMINE_PINK);
			MysteriaUtils.sendMessage(sender, message);
			return;
		}

		// Returns if receiver is sender
		if (receiver == sender) {
			Component message = Component.translatable("mystery.message.command.whisper.blocked_himself", NamedColor.CARMINE_PINK);
			MysteriaUtils.sendMessage(sender, message);
			return;
		}

		// Returns if receiver does not accept private messages
		if (EssentialsPlugin.getPlayerManager().getMsgToggles().contains(receiver.getUniqueId())) {
			// Do not returns if sender has "essentialsm.bypass.msgtoggle"
			if (!sender.hasPermission("essentialsm.bypass.msgtoggle")) {
				Component arg = Component.text(receiver.getName(), NamedColor.TURBO);
				Component message = Component.translatable("mystery.message.command.whisper.blocked", NamedColor.CARMINE_PINK, arg);
				MysteriaUtils.sendMessage(sender, message);
				return;
			}
		}

		// Returns if sender does not accept private messages
		if (sender instanceof Player && EssentialsPlugin.getPlayerManager().getMsgToggles().contains(((Player) sender).getUniqueId())) {
			// Do not returns if receiver has "essentialsm.bypass.msgtoggle"
			if (!receiver.hasPermission("essentialsm.bypass.msgtoggle")) {
				Component message = Component.translatable("mystery.message.command.whisper.blocked_toggle", NamedColor.CARMINE_PINK);
				MysteriaUtils.sendMessage(sender, message);
				return;
			}
		}

		// Message
		StringBuilder message = new StringBuilder();
		for (int i = 1; i < args.length; i++){
			message.append(" ").append(args[i]);
		}

		// Date "formatter.format(date)"
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		String dateString = formatter.format(date);


		Component chatMessage;
		String commandSuggest;
		Component hoverMessage = Component.text(dateString, NamedTextColor.GRAY)
				.append(Component.text("\n\n"))
				.append(Component.translatable("mystery.message.chat.whisper.hover", NamedColor.SKIRRET_GREEN));
		Component format;

		// Message to sender
		format = Component.translatable(
				"mystery.message.chat.whisper.format", NamedTextColor.GRAY,
				Component.translatable("mystery.message.chat.whisper.you", NamedColor.TURBO),
				Component.text(receiver.getName(), NamedColor.TURBO));
		chatMessage = Component.text()
				.append(Component.text("[", NamedTextColor.DARK_GRAY))
				.append(format)
				.append(Component.text("]", NamedTextColor.DARK_GRAY))
				.append(Component.text(message.toString(), NamedTextColor.GREEN))
				.build();
		commandSuggest = "/w " + receiver.getName() + " ";
		Component msgToSender = Component.text()
				.append(chatMessage)
				.hoverEvent(HoverEvent.hoverEvent(HoverEvent.Action.SHOW_TEXT, hoverMessage))
				.clickEvent(ClickEvent.suggestCommand(commandSuggest))
				.build();
		sender.sendMessage(msgToSender);

		// Message to receiver
		format = Component.translatable(
				"mystery.message.chat.whisper.format", NamedTextColor.GRAY,
				Component.text(sender.getName(), NamedColor.TURBO),
				Component.translatable("mystery.message.chat.whisper.you", NamedColor.TURBO));
		chatMessage = Component.text()
				.append(Component.text("[", NamedTextColor.DARK_GRAY))
				.append(format)
				.append(Component.text("]", NamedTextColor.DARK_GRAY))
				.append(Component.text(message.toString(), NamedTextColor.GREEN))
				.build();
		commandSuggest = "/w " + sender.getName() + " ";
		Component msgToReceiver = Component.text()
				.append(chatMessage)
				.hoverEvent(HoverEvent.hoverEvent(HoverEvent.Action.SHOW_TEXT, hoverMessage))
				.clickEvent(ClickEvent.suggestCommand(commandSuggest))
				.build();
		if (sender instanceof Player) {
			receiver.sendMessage(((Player) sender).identity(), msgToReceiver);
		} else {
			receiver.sendMessage(msgToReceiver);
		}

		// Setting last messages if sender is a player
		if (sender instanceof Player) {
			HashMap<UUID, UUID> lastMessages = EssentialsPlugin.getPlayerManager().getLastMessages();
			lastMessages.put(receiver.getUniqueId(), ((Player) sender).getUniqueId());
			lastMessages.put(((Player) sender).getUniqueId(), receiver.getUniqueId());
		}


	}

}

package com.mysteria.essentials.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.mysteria.essentials.EssentialsPlugin;
import com.mysteria.utils.MysteriaUtils;
import org.bukkit.command.CommandSender;

@CommandAlias("essreload")
public class ReloadCommand extends BaseCommand {

	@Default
	@CommandPermission("essentials.reload")
	@Description("Reloads Essentials plugin.")
	public void onCommand(CommandSender sender) {
		EssentialsPlugin.getInstance().reloadConfig();
		MysteriaUtils.sendMessageGreen(sender, "Plugin reloaded.");
	}

}

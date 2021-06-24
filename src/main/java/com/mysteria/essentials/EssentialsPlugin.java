package com.mysteria.essentials;

import co.aikar.commands.PaperCommandManager;
import com.mysteria.essentials.chatmanager.ChatManager;
import com.mysteria.essentials.commands.*;
import com.mysteria.essentials.listeners.*;
import com.mysteria.essentials.playermanager.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class EssentialsPlugin extends JavaPlugin {

	private static EssentialsPlugin instance;
	private static ChatManager chatManager;
	private static PlayerManager playerManager;
	private static PaperCommandManager commandManager;

	public EssentialsPlugin() {
		if (instance != null) throw new IllegalStateException();
		instance = this;
	}

	@Override
	public void onEnable() {
		if (!Bukkit.getPluginManager().isPluginEnabled("MysteriaUtils")) {
			getLogger().severe("*** MysteriaUtils is not installed or not enabled. ***");
			getLogger().severe("*** This plugin will be disabled. ***");
			this.setEnabled(false);
			return;
		}

		saveDefaultConfig();

		chatManager = new ChatManager();
		playerManager = new PlayerManager();
		commandManager = new PaperCommandManager(getInstance());

		registerListeners();
		registerCommands();
	}

	private void registerListeners() {
		new ChatListener();
		new PlayerJoinListener();
		new PlayerQuitListener();
		new PlayerSelectTitleListener();
		new PlayerSpawnListeners();
	}

	private void registerCommands() {
		getCommandManager().registerCommand(new ApplyGameRulesCommand());
		getCommandManager().registerCommand(new ClearChatCommand());
		getCommandManager().registerCommand(new HealCommand());
		getCommandManager().registerCommand(new MessageToggleCommand());
		getCommandManager().registerCommand(new ReloadCommand());
		getCommandManager().registerCommand(new SCommand());
		getCommandManager().registerCommand(new SetSpawnCommand());
		getCommandManager().registerCommand(new SpawnCommand());
		getCommandManager().registerCommand(new SpawnMobCommand());
		getCommandManager().registerCommand(new WhisperCommand());
	}

	public static EssentialsPlugin getInstance() {
		if (instance == null) throw new IllegalStateException();
		return instance;
	}

	public static ChatManager getChatManager() {
		return chatManager;
	}

	public static PlayerManager getPlayerManager() {
		return playerManager;
	}

	public static PaperCommandManager getCommandManager() {
		return commandManager;
	}
}

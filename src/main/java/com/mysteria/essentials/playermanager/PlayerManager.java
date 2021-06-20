package com.mysteria.essentials.playermanager;

import com.mysteria.customapi.CustomAPIPlugin;
import com.mysteria.customapi.effects.EffectManager;
import com.mysteria.essentials.EssentialsPlugin;
import com.mysteria.titles.PlayerTitlesPlugin;
import com.mysteria.titles.enums.Title;
import com.mysteria.utils.NamedColor;
import com.mysteria.utils.enums.Icon;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public class PlayerManager {

	private final List<UUID> msgToggles;
	private final HashMap<UUID, UUID> lastMessages;

	public PlayerManager() {
		if (EssentialsPlugin.getPlayerManager() != null) {
			throw new IllegalStateException();
		}
		msgToggles = new ArrayList<>();
		lastMessages = new HashMap<>();
		new BukkitRunnable() {
			@Override
			public void run() {
				for (Player p : Bukkit.getOnlinePlayers()) {
					updateListName(p);
					updateFooter(p);
				}
			}
		}.runTaskTimer(EssentialsPlugin.getInstance(), 0, 20);
	}

	@Nonnull
	public List<UUID> getMsgToggles() {
		return msgToggles;
	}

	@Nonnull
	public HashMap<UUID, UUID> getLastMessages() {
		return lastMessages;
	}

	public boolean teleportSpawn(Player p) {
		Location loc = getSpawnLocation();

		if (loc == null) return false;

		return p.teleport(loc);
	}

	@Nullable
	public Location getSpawnLocation() {
		FileConfiguration config = EssentialsPlugin.getInstance().getConfig();

		String config_worldName = config.getString("spawn.world");
		if (config_worldName == null) return null;

		World world = Bukkit.getWorld(config_worldName);
		double x = config.getDouble("spawn.x");
		double y = config.getDouble("spawn.y");
		double z = config.getDouble("spawn.z");
		float yaw = config.getLong("spawn.yaw");
		float pitch = config.getLong("spawn.pitch");

		if (world == null) return null;
		if (x == 0 && y == 0 && z == 0) return null;

		return new Location(world, x, y, z, yaw, pitch);
	}

	@Nonnull
	public TextColor getChatColor(@Nonnull Player p) {
		LuckPerms luckPerms = LuckPermsProvider.get();

		User user = luckPerms.getUserManager().getUser(p.getUniqueId());
		String userGroup;
		TextColor chatColor = NamedColor.HINT_OF_PENSIVE;

		if (user == null) {
			return chatColor;
		} else {
			userGroup = user.getPrimaryGroup();
		}

		if (userGroup.equals("admin")) {
			chatColor = NamedColor.PINK_GLAMOUR;
		} else if (userGroup.equals("moderator")) {
			chatColor = NamedColor.EXODUS_FRUIT;
		}

		return chatColor;
	}

	@Nonnull
	public TextColor getTitleColor(@Nonnull Player p) {
		LuckPerms luckPerms = LuckPermsProvider.get();
		User user = luckPerms.getUserManager().getUser(p.getUniqueId());
		TextColor titleColor = NamedColor.RISE_N_SHINE;

		if (user != null) {
			String userGroup = user.getPrimaryGroup();
			if (userGroup.equals("admin")) {
				titleColor = NamedTextColor.DARK_RED;
			} else if (userGroup.equals("moderator")) {
				titleColor = NamedColor.VANADYL_BLUE;
			}
		}
		return titleColor;
	}

	@Nonnull
	public Component getTitle(@Nonnull Player p) {

		LuckPerms luckPerms = LuckPermsProvider.get();

		Title title = PlayerTitlesPlugin.getTitleManager().getPlayerTitle(p);
		String titleString;

		if (title == null) {
			User user = luckPerms.getUserManager().getUser(p.getUniqueId());
			if (user == null) {
				titleString = luckPerms.getGroupManager().getGroup("default").getDisplayName();
			} else {
				titleString = luckPerms.getGroupManager().getGroup(user.getPrimaryGroup()).getDisplayName();
			}
		} else {
			titleString = title.getDisplayName();
		}

		return Component.text("[" + titleString + "]");
	}

	public void updateListName(@Nonnull Player p) {
		Component listName = Component.text()
				.append(getTitle(p))
				.color(getTitleColor(p))
				.append(Component.text(" " + p.getName(), NamedTextColor.GRAY))
				.build();
		p.playerListName(listName);
	}

	public void updateHeader(@Nonnull Player p) {
		Component header = Component.text()
				.append(Component.newline())
				.append(Component.text("            "))
				.append(Component.text("    ", NamedColor.CARMINE_PINK).decorate(TextDecoration.STRIKETHROUGH))
				.append(Component.text("( ", NamedColor.TURBO).decorate(TextDecoration.STRIKETHROUGH))
				.append(Component.text(" ✦ ", NamedColor.SILVER))
				.append(Component.text("Mystery", NamedColor.PROTOSS_PYLON).decorate(TextDecoration.BOLD))
				.append(Component.space())
				.append(Component.text("Universe", NamedColor.BEEKEEPER)
						.decorate(TextDecoration.BOLD)
						.decorate(TextDecoration.ITALIC))
				.append(Component.text(" ✦ ", NamedColor.SILVER))
				.append(Component.text(" )", NamedColor.TURBO).decorate(TextDecoration.STRIKETHROUGH))
				.append(Component.text("    ", NamedColor.CARMINE_PINK).decorate(TextDecoration.STRIKETHROUGH))
				.append(Component.text("            "))
				.append(Component.newline().append(Component.newline()))
				.append(Component.text("Your unforgettable")
						.append(Component.text(" enhanced ", NamedColor.HINT_OF_ICE_PACK))
						.append(Component.text("and mysterious"))
						.append(Component.newline())
						.append(Component.text("minecraft adventure..."))
						.colorIfAbsent(NamedColor.SOARING_EAGLE).decorate(TextDecoration.ITALIC))
				.append(Component.newline())
				.build();
		p.sendPlayerListHeader(header);
	}

	public void updateFooter(@Nonnull Player p) {
		TextComponent.Builder footer = Component.text()
				.append(Component.newline())
				.append(Component.text("Online Players: ", NamedColor.CARMINE_PINK))
				.append(Component.text(Bukkit.getOnlinePlayers().size(), NamedColor.SOARING_EAGLE))
				.append(Component.newline())
				.append(Component.text("Ping: ", NamedColor.CARMINE_PINK))
				.append(Component.text(p.getPing() + "ms", NamedColor.SOARING_EAGLE))
				.append(Component.newline())
				.append(Component.newline());

		Collection<PotionEffect> effects = p.getActivePotionEffects();
		if (effects.size() > 0) {
			EffectManager effectManager = CustomAPIPlugin.getEffectManager();
			for (PotionEffect effect : effects) {
				footer.append(Component.newline());
				Icon icon = Icon.getIcon(effect.getType().getName());
				if (icon != null) {
					footer.append(Component.text(icon.toString())).append(Component.space());
				}
				footer.append(Component.text()
						.append(Component.translatable("potion.withDuration",
								Component.translatable("potion.withAmplifier",
										Component.translatable("effect.minecraft." + effect.getType().getName().toLowerCase()),
										Component.translatable("potion.potency." + effect.getAmplifier())),
								Component.text(getDurationText(effect.getDuration()))
								)
						)
						.color(effectManager.getBuffType(effect.getType()).getColor())
						.build()
				);
			}
		} else {
			footer.append(Component.newline())
					.append(Component.translatable("effect.none", NamedColor.SOARING_EAGLE));
		}
		footer.append(Component.newline());


		p.sendPlayerListFooter(footer.build());
	}

	private String getDurationText(int ticks) {
		int seconds = (ticks / 20) % 60;
		int minutes = ticks / (60 * 20);
		String secondsText = seconds < 10 ? "0" + seconds : String.valueOf(seconds);
		return minutes + ":" + secondsText;
	}

}

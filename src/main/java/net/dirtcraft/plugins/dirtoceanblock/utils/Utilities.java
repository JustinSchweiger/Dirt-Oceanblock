package net.dirtcraft.plugins.dirtoceanblock.utils;

import net.dirtcraft.plugins.dirtoceanblock.DirtOceanblock;
import net.dirtcraft.plugins.dirtoceanblock.commands.BaseCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Utilities {
	public static void registerCommands() {
		DirtOceanblock.getPlugin().getCommand("island").setExecutor(new BaseCommand());
		DirtOceanblock.getPlugin().getCommand("island").setTabCompleter(new BaseCommand());
	}

	public static String format(String message) {
		return net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', message);
	}

	public static boolean isOnlinePlayer(String username) {
		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			if (player.getName().equalsIgnoreCase(username)) {
				return true;
			}
		}

		return false;
	}
}

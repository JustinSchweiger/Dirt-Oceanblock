package net.dirtcraft.plugins.dirtoceanblock;

import net.dirtcraft.plugins.dirtoceanblock.utils.Utilities;
import org.bukkit.plugin.java.JavaPlugin;

public final class DirtOceanblock extends JavaPlugin {
	private static DirtOceanblock plugin;

	public static DirtOceanblock getPlugin() {
		return plugin;
	}

	@Override
	public void onEnable() {
		plugin = this;
		Utilities.registerCommands();
	}
}

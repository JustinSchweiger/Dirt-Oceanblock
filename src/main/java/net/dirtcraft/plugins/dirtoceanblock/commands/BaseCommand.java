package net.dirtcraft.plugins.dirtoceanblock.commands;

import net.dirtcraft.plugins.dirtoceanblock.utils.Strings;
import net.dirtcraft.plugins.dirtoceanblock.utils.Utilities;
import net.dirtcraft.plugins.dirtoceanblock.utils.gradient.GradientHandler;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BaseCommand implements CommandExecutor, TabCompleter {

	private static final List<String> options = Arrays.asList("create", "home", "setHome", "trust", "untrust", "listAllies", "invite", "kick");

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(Strings.NO_CONSOLE);
		}

		if (args.length == 0 || (args.length == 1 && args[0].equalsIgnoreCase("help"))) {
			List<TextComponent> listings = getListings();
			Color red = new Color(247, 0, 0);
			Color orange = new Color(251, 121, 0);
			BaseComponent[] bar1 = TextComponent.fromLegacyText(GradientHandler.hsvGradient("----------------", orange, red, GradientHandler::linear, ChatColor.STRIKETHROUGH));
			BaseComponent[] bar2 = TextComponent.fromLegacyText(GradientHandler.hsvGradient("----------------", red, orange, GradientHandler::linear, ChatColor.STRIKETHROUGH));
			BaseComponent[] text = TextComponent.fromLegacyText(ChatColor.RESET + "" + ChatColor.GRAY + "[ " + ChatColor.RED + "DirtCraft " + Utilities.format("&x&4&5&9&3&f&fOceanblock") + ChatColor.RESET + ChatColor.GRAY + " ]");
			TextComponent topbar = new TextComponent(bar1);
			topbar.addExtra(new TextComponent(text));
			topbar.addExtra(new TextComponent(bar2));

			sender.spigot().sendMessage(topbar);
			for (TextComponent listing : listings) {
				sender.spigot().sendMessage(listing);
			}
			sender.sendMessage(Strings.BAR_BOTTOM);

			return true;
		}

		String arg = args[0].toLowerCase();
		switch (arg) {
			case "trust":
				sender.getServer().dispatchCommand(sender, "/ftbteams party allies add " + args[1]);
				break;
			case "untrust":
				sender.getServer().dispatchCommand(sender, "/ftbteams party allies remove " + args[1]);
				break;
			case "invite":
				sender.getServer().dispatchCommand(sender, "/ftbteams party invite " + args[1]);
				break;
			case "kick":
				sender.getServer().dispatchCommand(sender, "/ftbteams party kick " + args[1]);
				break;
		}
		
		return true;
	}

	private List<TextComponent> getListings() {
		List<TextComponent> listings = new ArrayList<>();

		TextComponent create = new TextComponent(ChatColor.DARK_AQUA + "  /island " + ChatColor.AQUA + "create");
		create.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.GOLD + "Click to create a new island!")));
		create.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/ftbteamislands create"));
		listings.add(create);

		TextComponent home = new TextComponent(ChatColor.DARK_AQUA + "  /island " + ChatColor.AQUA + "home");
		home.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.GOLD + "Click to teleport to your island!")));
		home.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/ftbteamislands home"));
		listings.add(home);

		TextComponent setHome = new TextComponent(ChatColor.DARK_AQUA + "  /island " + ChatColor.AQUA + "setHome");
		setHome.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.GOLD + "Click to change your island home spawn point!")));
		setHome.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/ftbteamislands change_spawn"));
		listings.add(setHome);

		TextComponent trust = new TextComponent(ChatColor.DARK_AQUA + "  /island " + ChatColor.AQUA + "trust <player>");
		trust.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.GOLD + "Click to trust a player!")));
		trust.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/island trust <player>"));
		listings.add(trust);

		TextComponent untrust = new TextComponent(ChatColor.DARK_AQUA + "  /island " + ChatColor.AQUA + "untrust <player>");
		untrust.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.GOLD + "Click to untrust a player!")));
		untrust.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/island untrust <player>"));
		listings.add(untrust);

		TextComponent listAllies = new TextComponent(ChatColor.DARK_AQUA + "  /island " + ChatColor.AQUA + "listAllies");
		listAllies.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.GOLD + "Click to show a list of all trusted players!")));
		listAllies.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/ftbteams party allies list"));
		listings.add(listAllies);

		TextComponent invite = new TextComponent(ChatColor.DARK_AQUA + "  /island " + ChatColor.AQUA + "invite <player>");
		invite.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.GOLD + "Click to invite someone to your island!")));
		invite.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/island invite <player>"));
		listings.add(invite);

		TextComponent kick = new TextComponent(ChatColor.DARK_AQUA + "  /island " + ChatColor.AQUA + "kick <player>");
		kick.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.GOLD + "Click to kick someone from your island!")));
		kick.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/island kick <player>"));
		listings.add(kick);

		return listings;
	}

	@Nullable
	@Override
	public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
		List<String> arguments = new ArrayList<>();

		if (args.length == 1) {
			arguments.addAll(options);
		} else if (args.length == 2 && (args[0].equalsIgnoreCase("trust") || args[0].equalsIgnoreCase("untrust") || args[0].equalsIgnoreCase("invite") || args[0].equalsIgnoreCase("kick"))) {
			for (Player player : sender.getServer().getOnlinePlayers()) {
				arguments.add(player.getName());
			}
		}

		List<String> tabResults = new ArrayList<>();
		for (String argument : arguments) {
			if (argument.toLowerCase().startsWith(args[args.length - 1].toLowerCase())) {
				tabResults.add(argument);
			}
		}

		return tabResults;
	}
}

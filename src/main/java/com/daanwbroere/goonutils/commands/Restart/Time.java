package com.daanwbroere.goonutils.commands.Restart;

import com.daanwbroere.goonutils.GoonUtils;
import com.daanwbroere.goonutils.config.Config;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import javax.annotation.Nonnull;

public class Time implements CommandExecutor{

    private final GoonUtils plugin;

    public Time(@Nonnull final GoonUtils goonutils) {
        plugin = goonutils;
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        if(!(src instanceof Player) && !(src instanceof ConsoleSource)) {
            throw new CommandException(Text.of(TextColors.RED, "Must be a player or console"), false);
        }

        String message = plugin.restartModule.timeMessage(plugin.restartModule.timeTillRestart);
        src.sendMessage(Text.builder("[Info] ").color(TextColors.AQUA)
                .append(Text.builder("Server will restart in " + message).color(TextColors.GREEN).build())
                .build());

        return CommandResult.success();

    }

}

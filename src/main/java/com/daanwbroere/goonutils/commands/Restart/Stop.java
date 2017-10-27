package com.daanwbroere.goonutils.commands.Restart;

import com.daanwbroere.goonutils.GoonUtils;
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

public class Stop implements CommandExecutor {

    private final GoonUtils plugin;

    public Stop(@Nonnull final GoonUtils goonutils) {
        plugin = goonutils;
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        if(!(src instanceof Player) && !(src instanceof ConsoleSource)) {
            throw new CommandException(Text.of(TextColors.RED, "Must be a player or console"), false);
        }

        plugin.restartModule.stop();
        src.sendMessage(Text.builder("Stopped current server restart timer").build());
        return CommandResult.success();
    }
}

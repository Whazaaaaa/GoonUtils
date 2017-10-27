package com.daanwbroere.goonutils.commands.Restart;

import com.daanwbroere.goonutils.GoonUtils;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;

import javax.annotation.Nonnull;

public class Stop implements CommandExecutor {

    private final GoonUtils plugin;

    public Stop(@Nonnull final GoonUtils goonutils) {
        plugin = goonutils;
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        plugin.restartModule.stop();
        src.sendMessage(Text.builder("Stopped current server restart timer").build());
        return CommandResult.success();
    }
}

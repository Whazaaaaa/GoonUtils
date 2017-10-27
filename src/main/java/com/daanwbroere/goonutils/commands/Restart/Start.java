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

public class Start implements CommandExecutor {

    private final GoonUtils plugin;

    public Start(@Nonnull final GoonUtils goonutils) {
        plugin = goonutils;
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        if(!(src instanceof Player) && !(src instanceof ConsoleSource)) {
            throw new CommandException(Text.of(TextColors.RED, "Must be a player or console"), false);
        }

        int time = 0;
        if(args.<Integer>getOne("time").isPresent()) {
            time = args.<Integer>getOne("time").get();
        }

        if(!plugin.restartModule.isRunning) {
            plugin.restartModule.start(time * 60);
            src.sendMessage(Text.builder("Starting new timer").build());
            return CommandResult.success();
        }
        else {
            src.sendMessage(Text.builder("Unable to start timer. Previous timer has not been stopped. Use '/goon restart stop' to be able to start a new timer.").build());
            return CommandResult.empty();
        }

    }
}

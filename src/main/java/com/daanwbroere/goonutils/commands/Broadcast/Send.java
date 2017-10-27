package com.daanwbroere.goonutils.commands.Broadcast;

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

public class Send implements CommandExecutor {

    private final GoonUtils plugin;

    public Send(@Nonnull final GoonUtils goonutils) {
        plugin = goonutils;
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        if(!(src instanceof Player) && !(src instanceof ConsoleSource)) {
            throw new CommandException(Text.of(TextColors.RED, "Must be a player or console"), false);
        }

        String message = args.<String>getOne("message").get();
        plugin.game.getServer().getBroadcastChannel()
                .send(Text.builder("[Broadcast] ").color(TextColors.RED)
                        .append(Text.builder(message).color(TextColors.GOLD).build())
                        .build());
        return CommandResult.success();
    }
}

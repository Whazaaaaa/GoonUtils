package com.daanwbroere.goonutils.commands.Broadcast;

import com.daanwbroere.goonutils.GoonUtils;
import com.daanwbroere.goonutils.config.Config;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.pagination.PaginationList;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.HoverAction;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

import javax.annotation.Nonnull;
import java.util.ArrayList;

public class List implements CommandExecutor {

    private final GoonUtils plugin;

    public List(@Nonnull final GoonUtils goonutils) {
        plugin = goonutils;
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        if(!(src instanceof Player) && !(src instanceof ConsoleSource)) {
            throw new CommandException(Text.of(TextColors.RED, "Must be a player or console"), false);
        }

        java.util.List<Text> contents = new ArrayList();
        java.util.List<String> messages = Config.broadcastMessages;

        for(int i = 0; i < messages.size(); i++) {
            contents.add(Text.builder("[" + Integer.toString(i) + "]").color(TextColors.GOLD)
                        .onHover(TextActions.showText(Text.builder("Click to REMOVE this broadcast")
                                .build()))
                                .onClick(TextActions.runCommand("/goon broadcast remove " + i))
                    .append(Text.builder(messages.get(i)).color(TextColors.GREEN)
                            .onHover(TextActions.showText(Text.builder("Click to SEND this broadcast")
                                .build()))
                                .onClick(TextActions.runCommand("/goon broadcast send " + messages.get(i)))
                            .build())
                    .build());
        }

        PaginationList.builder()
                .title(Text.of(TextColors.GREEN, "Broadcast Messages"))
                .contents(contents)
                .padding(Text.of("="))
                .sendTo(src);

        return CommandResult.success();
    }
}

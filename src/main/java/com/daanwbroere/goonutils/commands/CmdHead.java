package com.daanwbroere.goonutils.commands;

import com.daanwbroere.goonutils.GoonUtils;
import com.daanwbroere.goonutils.config.Config;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import javax.annotation.Nonnull;

public class CmdHead implements CommandExecutor {
    // /give @p minecraft:skull 1 3 {SkullOwner: Whazaaaaa}

    private final GoonUtils plugin;


    public CmdHead(@Nonnull final GoonUtils goonutils) {
        plugin = goonutils;
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        if(!(src instanceof Player)) {
            throw new CommandException(Text.of(TextColors.RED, "Must be a player"), false);
        }
        String playername = src.getName();

        String head = args.<String>getOne("head").get();
        Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "give " + playername + " minecraft:skull 1 3 {SkullOwner: " + head + "}");
        return CommandResult.success();
    }

}

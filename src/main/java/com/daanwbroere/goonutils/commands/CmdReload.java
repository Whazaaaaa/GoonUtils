package com.daanwbroere.goonutils.commands;

import com.daanwbroere.goonutils.GoonUtils;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;

import javax.annotation.Nonnull;
import java.io.IOException;

public class CmdReload implements CommandExecutor {

    private final GoonUtils plugin;

    public CmdReload(@Nonnull final GoonUtils goonutils) {
        plugin = goonutils;
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        try {
            plugin.reload();
            src.sendMessage(Text.builder("Reloading Goon Utils configs").build());
            src.sendMessage(Text.builder("Note that the server restart has not been reset. You will need to do '/goon restart stop' and '/goon restart start' in order to reset that.").build());
        }
        catch (IOException e) {

        }
        catch (ObjectMappingException o) {

        }

        return CommandResult.success();
    }
}

package com.daanwbroere.goonutils.commands;

import com.daanwbroere.goonutils.GoonUtils;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

import javax.annotation.Nonnull;
import java.io.IOException;

public class CmdWhere implements CommandExecutor {

    private final GoonUtils plugin;

    public CmdWhere(@Nonnull final GoonUtils goonutils) {
        plugin = goonutils;
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        if(src instanceof Player) {
            Player player = (Player) src;
            src.sendMessage(Text.of(TextSerializers.FORMATTING_CODE.deserialize("&7You are in the world: &6" + player.getWorld().getName())));
        }

        return CommandResult.success();
    }
}

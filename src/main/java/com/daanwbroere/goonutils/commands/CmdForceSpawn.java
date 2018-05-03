package com.daanwbroere.goonutils.commands;

import com.daanwbroere.goonutils.GoonUtils;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import javax.annotation.Nonnull;

public class CmdForceSpawn implements CommandExecutor {

    private final GoonUtils plugin;

    public CmdForceSpawn(@Nonnull final GoonUtils goonutils) {
        plugin = goonutils;
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        Player player = args.<Player>getOne("player").get();
        if (player.respawnPlayer()) {
            src.sendMessage(Text.of("The player has been respawned"));
        }
        else {
            src.sendMessage(Text.of("No need for a respawn, the player is alive"));
        }

        return CommandResult.success();
    }

}

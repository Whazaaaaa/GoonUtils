package com.daanwbroere.goonutils.commands.Broadcast;

import com.daanwbroere.goonutils.GoonUtils;
import com.daanwbroere.goonutils.config.Config;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import org.spongepowered.api.Sponge;
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
import java.io.IOException;

public class Remove implements CommandExecutor {

    private final GoonUtils plugin;

    public Remove(@Nonnull final GoonUtils goonutils) {
        plugin = goonutils;
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {


        if(!(src instanceof Player) && !(src instanceof ConsoleSource)) {
            throw new CommandException(Text.of(TextColors.RED, "Must be a player or console"), false);
        }

        try {
            int id = args.<Integer>getOne("id").get();
            if(id > (Config.broadcastMessages.size() - 1) || id < 0) {
                throw new CommandException(Text.of(TextColors.RED, "Invalid index. Try from 0 to " + (Config.broadcastMessages.size() - 1)), false);
            }
            Config.broadcastMessages.remove(id);

            CommentedConfigurationNode config;
            config = plugin.configManager.load();
            config.getNode("broadcast", "broadcastMessages").setValue(Config.broadcastMessages);
            plugin.configManager.save(config);

            src.sendMessage(Text.of("Broadcast succesfully removed"));
            Sponge.getCommandManager().process(src, "goon broadcast list");
        }
        catch (IOException e) {
            src.sendMessage(Text.of("Unable to remove broadcast"));
        }
        return CommandResult.success();
    }

}

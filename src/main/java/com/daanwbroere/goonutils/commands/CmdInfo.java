package com.daanwbroere.goonutils.commands;

import com.daanwbroere.goonutils.GoonUtils;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.BookView;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import javax.annotation.Nonnull;
import java.util.Optional;

public class CmdInfo implements CommandExecutor {

    private final GoonUtils plugin;

    public CmdInfo(@Nonnull final GoonUtils goonutils) {
        plugin = goonutils;
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        if(src instanceof Player) {
            Player player = (Player) src;
            /*
            BookView bookView = BookView.builder()
                    .title(Text.of("Goon Utils"))
                    .author(Text.of("Whazaaaaa"))
                    .addPage(Text.of("Created by Whazaaaaa\n\n" + "For more information about this plugin please visit.\n" + ""))
                    .build();
            player.sendBookView(bookView);
            */

            Optional<ItemStack> optionalItemStack = player.getItemInHand(HandTypes.MAIN_HAND);
            if (!optionalItemStack.isPresent()) {
                throw new CommandException(Text.of(TextColors.YELLOW, "No item in hand"), false);
            }
            ItemStack itemStack = optionalItemStack.get();

            DataContainer container = itemStack.toContainer();
            DataQuery query = DataQuery.of('/', "UnsafeDamage");

            int unsafeDamage = Integer.parseInt(container.get(query).get().toString());

            String item = itemStack.getType().getId();

            if (unsafeDamage != 0) {
                item = item + ":" + unsafeDamage;
            }

            player.sendMessage(Text.of(TextColors.YELLOW, item));

            return CommandResult.success();

        }

        return CommandResult.success();
    }

}

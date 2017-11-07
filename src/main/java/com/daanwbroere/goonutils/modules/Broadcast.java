package com.daanwbroere.goonutils.modules;

import com.daanwbroere.goonutils.GoonUtils;
import com.daanwbroere.goonutils.config.Config;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.serializer.TextSerializers;

import javax.annotation.Nonnull;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Broadcast {

    private static GoonUtils plugin;
    private List<String> messages;
    private int messageCounter = 0;

    private Task taskBroadcast;

    public Broadcast(@Nonnull final GoonUtils goonutils) {
        plugin = goonutils;
        plugin.logger.info("Initializing Broadcast Module.");
        startBroadcasting();
    }

    public void startBroadcasting() {
        int interval = Config.broadcastTimeBetweenMessagesSeconds;
        taskBroadcast = Task.builder()
                .execute(this::sendBroadcast)
                .delay(500, TimeUnit.MILLISECONDS)
                .interval(interval, TimeUnit.SECONDS)
                .name("broadcast")
                .submit(plugin);
    }

    public void sendBroadcast() {
        messages = Config.broadcastMessages;

        if(Config.broadcastRandomizeMessages) {
            Random r = new Random();
            int count = r.nextInt(messages.size() - 1);
            if(count >= messageCounter){
                if(count >= messages.size()-1) {
                    messageCounter--;
                }
                else {
                    messageCounter = messages.size() - 1;
                }
            }
            else {
                messageCounter = count;
            }
        }
        else {
            if(messageCounter >= messages.size() - 1) {
                messageCounter = 0;
            }
            else {
                messageCounter++;
            }
        }

        Text text = processText(messages.get(messageCounter));
        plugin.game.getServer().getBroadcastChannel()
                .send(Text.builder("[Broadcast] ").color(TextColors.RED)
                        .append(Text.of(text))
                        .build());
    }

    public void reset() {
        taskBroadcast.cancel();
        messageCounter = 0;
        startBroadcasting();
    }

    public Text processText(String msg) {
        Text message = Text.EMPTY;

        while (msg.contains("&u")) {
            message = Text.join(message, TextSerializers.FORMATTING_CODE.deserialize(msg.substring(0, msg.indexOf("&u{")).replace("&u{", "")));

            String work = msg.substring(msg.indexOf("&u{"), msg.indexOf("}")).replaceFirst("&u\\{", "").replaceFirst("}", "");

            message = Text.join(message, getLink(work));

            msg = msg.substring(msg.indexOf("}"), msg.length()).replaceFirst("}", "");
        }

        return Text.of(message, TextSerializers.FORMATTING_CODE.deserialize(msg));
    }

    private Text getLink(String link) {
        Text.Builder builder = Text.builder();
        String[] work = link.split(";");

        if (work.length != 3) {
            return Text.of(TextColors.RED, "Invalid TextAction detected");
        }

        if (work[0].equalsIgnoreCase("url")) {
            if (!work[1].toLowerCase().contains("http://") && !work[1].toLowerCase().contains("https://")) {
                work[1] = "http://" + work[1];
            }

            URL url = null;
            try {
                url = new URL(work[1]);
                builder.onClick(TextActions.openUrl(url)).append(TextSerializers.FORMATTING_CODE.deserialize(work[2]));
            } catch (MalformedURLException e) {
                return Text.of(TextColors.RED, "Invalid URL detected");
            }
        } else if (work[0].equalsIgnoreCase("cmd")) {
            builder.onClick(TextActions.runCommand(work[1])).append(TextSerializers.FORMATTING_CODE.deserialize(work[2]));
        } else if (work[0].equalsIgnoreCase("suggest")) {
            builder.onClick(TextActions.suggestCommand(work[1])).append(TextSerializers.FORMATTING_CODE.deserialize(work[2]));
        } else if (work[0].equalsIgnoreCase("hover")) {
            builder.onHover(TextActions.showText(TextSerializers.FORMATTING_CODE.deserialize(work[1]))).append(TextSerializers.FORMATTING_CODE.deserialize(work[2]));
        } else {
            return Text.of(TextColors.RED, "Invalid TextAction detected");
        }

        return builder.build();
    }

}

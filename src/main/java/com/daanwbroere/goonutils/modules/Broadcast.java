package com.daanwbroere.goonutils.modules;

import com.daanwbroere.goonutils.GoonUtils;
import com.daanwbroere.goonutils.config.Config;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import javax.annotation.Nonnull;
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

        plugin.game.getServer().getBroadcastChannel()
                .send(Text.builder("[Broadcast] ").color(TextColors.RED)
                        .append(Text.builder(messages.get(messageCounter)).color(TextColors.GOLD).build())
                        .build());
    }

    public void reset() {
        taskBroadcast.cancel();
        messageCounter = 0;
        startBroadcasting();
    }

}

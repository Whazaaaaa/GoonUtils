package com.daanwbroere.goonutils.modules;

import com.daanwbroere.goonutils.GoonUtils;
import com.daanwbroere.goonutils.config.Config;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Restart {

    private static GoonUtils plugin;

    private Task taskRestartTimer;
    public int timeTillRestart;
    private List<Integer> warningTimes;
    public boolean isRunning;

    public Restart(@Nonnull final GoonUtils goonutils) {
        plugin = goonutils;
        plugin.logger.info("Initializing Restart Module.");

        start(0);
    }

    public void startRestartTimer() {
        isRunning = true;
        taskRestartTimer = Task.builder()
                .execute(this::checkTimer)
                .delay(500, TimeUnit.MILLISECONDS)
                .interval(1, TimeUnit.SECONDS)
                .name("restart")
                .submit(plugin);
    }

    public void checkTimer() {
        timeTillRestart--;
        for(Integer time : warningTimes) {
            if(time == timeTillRestart && Config.restartNotifyPlayers) {
                plugin.game.getServer().getBroadcastChannel()
                        .send(Text.builder("[Info] ").color(TextColors.AQUA)
                                .append(Text.builder("Restart in: " + timeMessage(time)).color(TextColors.GREEN).build())
                                .build());
            }
        }

        if(timeTillRestart <= 0) {
            taskRestartTimer.cancel();
            isRunning = false;
            plugin.game.getServer().getBroadcastChannel()
                    .send(Text.builder("[Info] ").color(TextColors.AQUA)
                            .append(Text.builder(Config.restartMessage).color(TextColors.GREEN).build())
                            .build());
            Sponge.getCommandManager().process(Sponge.getServer().getConsole(), Config.restartCommand);
        }

    }

    public String timeMessage(int seconds) {
        int hours = seconds / 3600;
        int remainder = seconds - hours * 3600;
        int mins = remainder / 60;
        int secs = remainder - mins * 60;
        /*
        String message = "";
        if(hours > 0) {
            message += (hours + " hour(s), ");
        }
        if(mins > 0 || hours > 0) {
            message += (mins + " minute(s) and ");
        }
        message += (secs + " seconds(s)");
        */

        String message = "";
        if(hours > 0) {
            message += (hours + " hour");
            if(hours > 1) message += "s";
        }
        if((hours > 0 && mins > 0 && secs < 1) || (hours > 0 && secs > 0 && mins < 1)) message += " and ";
        else if(hours > 0 && mins > 0) message += ", ";
        if(mins > 0) {
            message += (mins + " minute");
            if(mins > 1) message += "s";
        }
        if(secs > 0) {
            if(mins > 0) message += " and ";
            message += (secs + " second");
            if(secs > 1) message += "s";
        }

        return message;
    }

    public void stop() {
        taskRestartTimer.cancel();
        isRunning = false;
    }

    public boolean start(Integer time) {
        if(time <= 0) {
            time = Config.restartTimeMinutes * 60;
        }
        timeTillRestart = time;
        warningTimes = Config.restartTimesToMessageSeconds;
        startRestartTimer();
        plugin.logger.info("The server will restart in: " + timeMessage(time));
        return true;
    }
}

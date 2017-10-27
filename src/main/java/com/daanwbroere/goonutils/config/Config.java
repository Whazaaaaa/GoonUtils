package com.daanwbroere.goonutils.config;

import com.daanwbroere.goonutils.GoonUtils;
import com.google.common.reflect.TypeToken;
import javax.annotation.Nonnull;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Config {

    private final GoonUtils plugin;

    public static CommentedConfigurationNode config;

    public Config(@Nonnull final GoonUtils goonutils) throws IOException, ObjectMappingException {
        plugin = goonutils;
        checkConfig();
    }

    private String[] messages = {
            "Check out our website at: www.myminecraftwebsite.com",
            "Enjoy your stay on our server!",
            "Dont do anything stupid."
    };

    private Integer[] times = {
            5,
            10,
            30,
            60,
            120,
            180,
            240,
            300
    };

    public static boolean restartModuleEnabled;
    public static int restartTimeMinutes;
    public static boolean restartNotifyPlayers;
    public static String restartMessage;
    public static String restartCommand;
    public static List<Integer> restartTimesToMessageSeconds;

    public static boolean broadcastModuleEnabled;
    public static int broadcastTimeBetweenMessagesSeconds;
    public static boolean broadcastRandomizeMessages;
    public static List<String> broadcastMessages;

    public void checkConfig() throws IOException, ObjectMappingException {
            config = plugin.configManager.load();
            if (!plugin.defaultConfig.toFile().exists()) {
                plugin.defaultConfig.toFile().createNewFile();
                config.getNode("restart").setComment("Restart module is for automatic server restart");
                config.getNode("broadcast").setComment("Broadcast module is for timed broadcasts serverwide");
            }

            restartModuleEnabled = check(config.getNode("restart", "enabled"), true, "If you want to have the Restart module enabled").getBoolean();
            restartTimeMinutes = check(config.getNode("restart", "restartTimeMinutes"), 300, "Time in MINUTES until the server restarts from server startup (default 300 min = 5 hours)").getInt();
            restartNotifyPlayers = check(config.getNode("restart", "notifyPlayers"), true, "Send messages to players about the restart").getBoolean();
            restartTimesToMessageSeconds = checkList(config.getNode("restart", "restartMessagesTimesSeconds"), times, "Times in SECONDS that the players will be notified of the restart").getList(TypeToken.of(Integer.class));
            restartMessage = check(config.getNode("restart", "restartMessage"), "Restarting server. We'll be right back!", "Message to send to the players when the server actually restarts").getString();
            restartCommand = check(config.getNode("restart", "restartCommand"), "stop", "Command to run when the server needs to restart. Note: NO / needed in front of the command").getString();

            broadcastModuleEnabled = check(config.getNode("broadcast", "enabled"), true, "If you want to have the Broadcast module enabled").getBoolean();
            broadcastTimeBetweenMessagesSeconds = check(config.getNode("broadcast", "timeBetweenMessagesSeconds"), 120, "The time in SECONDS between broadcasts").getInt();
            broadcastRandomizeMessages = check(config.getNode("broadcast", "randomizeMessages"), false, "If you want the messages to randomly show or follow the list top to bottom").getBoolean();
            broadcastMessages = checkList(config.getNode("broadcast", "broadcastMessages"), messages, "The messages that will be broadcasted.").getList(TypeToken.of(String.class));


            plugin.configManager.save(config);
    }

    private CommentedConfigurationNode check(CommentedConfigurationNode node, Object defaultValue, String comment) {
        if (node.isVirtual()) {
            node.setValue(defaultValue).setComment(comment);
        }
        return node;
    }
    private CommentedConfigurationNode checkList(CommentedConfigurationNode node, Integer[] defaultValue, String comment) {
        if (node.isVirtual()) {
            node.setValue(Arrays.asList(defaultValue)).setComment(comment);
        }
        return node;
    }
    private CommentedConfigurationNode checkList(CommentedConfigurationNode node, String[] defaultValue, String comment) {
        if (node.isVirtual()) {
            node.setValue(Arrays.asList(defaultValue)).setComment(comment);
        }
        return node;
    }

}

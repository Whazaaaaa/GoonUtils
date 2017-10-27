package com.daanwbroere.goonutils;

import com.daanwbroere.goonutils.commands.Commands;
import com.daanwbroere.goonutils.config.Config;
import com.daanwbroere.goonutils.modules.Broadcast;
import com.daanwbroere.goonutils.modules.Restart;
import com.google.inject.Inject;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;

import java.io.IOException;
import java.nio.file.Path;


@Plugin(id = "goonutils", name = "GoonUtils", version = "1.0", authors = {"Whazaaaaa"})
public class GoonUtils {

    @Inject
    @DefaultConfig(sharedRoot = false)
    public Path defaultConfig;

    @Inject
    @DefaultConfig(sharedRoot = false)
    public ConfigurationLoader<CommentedConfigurationNode> configManager;

    @Inject
    @ConfigDir(sharedRoot = false)
    public Path privateConfigDir;

    @Inject
    private Metrics metrics;

    @Inject
    public Game game;
    @Inject
    public Logger logger;

    private Config config;
    private Commands commands;

    public Restart restartModule;
    public Broadcast broadcastModule;


    @Listener
    public void preInit(GamePreInitializationEvent event) throws IOException, ObjectMappingException
    {
        config = new Config(this);
        commands = new Commands(this);
    }

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
        logger.info("GoonUtils started.");
        if(config.restartModuleEnabled) {
            restartModule = new Restart(this);
        }
        else {
            logger.info("Restart module disabled. There will not be an automatic restart");
        }

        if(config.broadcastModuleEnabled) {
            broadcastModule = new Broadcast(this);
        }
        else {
            logger.info("Broadcast module disabled. There will not be timed messages");
        }

        metrics.addCustomChart(new Metrics.SimplePie("broadcast", () -> Boolean.toString(config.broadcastModuleEnabled)));
    }

    @Listener
    public void onPluginReload(GameReloadEvent event) throws IOException, ObjectMappingException {
        reload();
    }

    public void reload() throws IOException, ObjectMappingException {
        logger.info("Reloading GoonUtils.");
        config.checkConfig();
        if(config.restartModuleEnabled) {
            //restartModule.reset();
        }
        if(config.broadcastModuleEnabled) {
            broadcastModule.reset();
        }
    }

}

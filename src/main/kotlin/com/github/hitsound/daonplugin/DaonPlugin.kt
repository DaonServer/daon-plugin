package com.github.hitsound.daonplugin

import com.github.hitsound.daonplugin.command.*
import com.github.hitsound.daonplugin.listener.TimeCommandWatcher
import com.github.hitsound.daonplugin.listener.cb.CommandBlockPlaceListener
import com.github.hitsound.daonplugin.listener.chat.GiveMeOp
import org.bukkit.plugin.java.JavaPlugin

class DaonPlugin : JavaPlugin() {
    // TODO: dynmapでレンダーする前にセーブする
    init {
        INSTANCE = this
    }

    companion object {
        lateinit var INSTANCE: DaonPlugin
        private set
    }

    override fun onEnable() {
        // Plugin startup logic
        registerEventListeners()
        registerCommands()
        this.dataFolder.mkdirs()
        logger.info("Exit: onEnable")
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

    private fun registerEventListeners() {
        logger.info("Register Events")
        setOf(
                TimeCommandWatcher,
                CommandBlockPlaceListener,
                GiveMeOp,
        ).forEach {
            this.server.pluginManager.registerEvents(it, this)
        }
        logger.info("Done")
    }

    private fun registerCommands() {
        logger.info("Register Commands")
        mapOf(
                "color" to ColorCommand,
                "focus" to FocusCommand,
                "daon" to DaonCommand,
                "userrights" to UserRightsCommand,
                "lookup" to LookupEditHistory,
        ).forEach { (key, executor) ->
            x@do {
                logger.info("Register command: {name: $key, instance: $executor}")
                (this.getCommand(key) ?: run {
                    logger.severe("command $key was not found!")
                    null
                })?.executor = executor
            } while (false);
        }
        logger.info("Done")
    }
}
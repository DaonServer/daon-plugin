package com.github.hitsound.daonplugin.command

import com.github.hitsound.daonplugin.DaonPlugin
import com.github.hitsound.daonplugin.effect.SendMessageEffect
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

object DaonCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender?, command: Command?, label: String?, args: Array<out String>?): Boolean {
        args!!
        if (args.size == 1) {
            when (val subcommand = args[0]) {
                "version" -> {
                    SendMessageEffect(sender!!, "[Daon] version: " + DaonPlugin.INSTANCE.description.version)()
                }

                // TODO
                "help" -> {
                    SendMessageEffect(sender!!, "[Daon] TODO: help message")
                }
            }
        }
        return true
    }
}
package com.github.hitsound.daonplugin.command

import com.github.hitsound.daonplugin.effect.SendMessageEffect
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object FocusCommand : CommandExecutor {
    private val focus = mutableMapOf<String, String>()
    override fun onCommand(sender: CommandSender?, command: Command?, label: String?, args: Array<out String>?): Boolean {
        if (sender !is Player) return true
        command!!
        label!!
        args!!
        if (args.size == 1) {
            focus[sender.name] = (Bukkit.getOnlinePlayers().find { it.name == args[0] } ?: run {
                SendMessageEffect(sender, "player '${args[0]}' was not found.")()
                return true
            }).name
        } else if (args.isEmpty()) {
            focus.remove(sender.name)
        }
        return true
    }
}
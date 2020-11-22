package com.github.hitsound.daonplugin.effect

import org.bukkit.command.CommandSender
import org.bukkit.event.player.AsyncPlayerChatEvent

class SendMessageEffect(private val cs: CommandSender, private val message: String): Effect<SendMessageToEveryoneEffect>, Sync {
    override fun invoke() {
        cs.sendMessage(message)
    }
}
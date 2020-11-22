package com.github.hitsound.daonplugin.effect

import org.bukkit.entity.Player

class SendMessageToEveryoneEffect(
        val players: Collection<Player>,
        val message: String
) : Effect<SendMessageToEveryoneEffect>, Sync {
    override fun invoke() {
        players.forEach {
            it.sendMessage(message)
        }
    }
}
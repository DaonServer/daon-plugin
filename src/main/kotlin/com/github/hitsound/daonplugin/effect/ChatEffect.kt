package com.github.hitsound.daonplugin.effect

import org.bukkit.entity.Player

class ChatEffect(val p: Player, val message: String) : Effect<ChatEffect>, Async {
    override fun invoke() {
        p.chat(message)
    }
}
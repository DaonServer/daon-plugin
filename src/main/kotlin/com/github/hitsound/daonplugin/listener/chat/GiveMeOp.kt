package com.github.hitsound.daonplugin.listener.chat

import com.github.hitsound.daonplugin.effect.ChatEffect
import com.github.hitsound.daonplugin.effect.SendMessageEffect
import com.github.hitsound.daonplugin.effect.SequentialEffect
import com.github.hitsound.daonplugin.listener.SingleEventListener
import com.github.hitsound.daonplugin.util.MinecraftNative
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.player.AsyncPlayerChatEvent

object GiveMeOp : SingleEventListener<AsyncPlayerChatEvent> {
    @EventHandler
    override fun onEvent(event: AsyncPlayerChatEvent) {
        val content = event.message
        val p = event.player
        if (content == "OPください") {
            val effect = SequentialEffect(
                    listOf(
                            // send message from fake bot
                            SendMessageEffect(p, "DaonBot: OPです、どうぞ！"),
                            // send fake-opped message
                            // TODO: translate
                            SendMessageEffect(p, "" + ChatColor.GRAY + ChatColor.ITALIC + "[CONSOLE: Opped " + p.name + "]"),
                    )
            )
            effect()
        }
    }

}
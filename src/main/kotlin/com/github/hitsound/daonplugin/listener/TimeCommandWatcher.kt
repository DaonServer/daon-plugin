package com.github.hitsound.daonplugin.listener

import com.github.hitsound.daonplugin.DaonPlugin
import com.github.hitsound.daonplugin.effect.LambdaEffect
import com.github.hitsound.daonplugin.effect.SendMessageToEveryoneEffect
import com.github.hitsound.daonplugin.effect.SequentialEffect
import com.github.hitsound.daonplugin.extension.pipe
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerCommandPreprocessEvent
import org.bukkit.event.world.WorldEvent

object TimeCommandWatcher : SingleEventListener<PlayerCommandPreprocessEvent> {
    @EventHandler
    override fun onEvent(event: PlayerCommandPreprocessEvent) {
        val xx = event.message.split(' ')
        if (xx.isEmpty() || !xx[0].startsWith('/')) {
            return
        }

        val command = xx[0]
        val p = event.player
        when (command) {
            "day" -> "day"
            "night" -> "night"
            else -> return
        }.pipe {
            SequentialEffect(
                    listOf(
                            SendMessageToEveryoneEffect(Bukkit.getOnlinePlayers(), "${p.name}: $it"),
                            LambdaEffect(it, DaonPlugin.INSTANCE.logger::info),
                    )
            )()
        }
    }
}
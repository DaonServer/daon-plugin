package com.github.hitsound.daonplugin

import com.github.hitsound.daonplugin.effect.Effect
import org.bukkit.Bukkit
import kotlin.collections.ArrayDeque

object QueueEffect {
    private val queue = ArrayDeque<Effect<*>>()
    fun addLast(e: Effect<*>) {
        queue.addLast(e)
    }

    fun executeOneSync() {
        val first = queue.firstOrNull() ?: return
        first.invoke()
        queue.removeFirst()
    }

    fun executeOneAsync() {
        Bukkit.getScheduler().runTaskAsynchronously(null) {
            val first = queue.firstOrNull() ?: return@runTaskAsynchronously
            first.invoke()
            queue.removeFirst()
        }
    }
}
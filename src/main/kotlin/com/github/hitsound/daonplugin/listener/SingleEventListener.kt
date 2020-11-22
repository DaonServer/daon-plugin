package com.github.hitsound.daonplugin.listener

import org.bukkit.event.Event
import org.bukkit.event.Listener

interface SingleEventListener<E : Event> : Listener {
    fun onEvent(event: E)
}
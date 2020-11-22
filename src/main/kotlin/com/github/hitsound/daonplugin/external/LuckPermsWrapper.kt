package com.github.hitsound.daonplugin.external

import com.github.hitsound.daonplugin.annotations.AnyTypeOf
import net.luckperms.api.LuckPerms
import net.luckperms.api.LuckPermsProvider

object LuckPermsWrapper {
    fun api() = LuckPermsProvider.get()
}

inline fun <@AnyTypeOf(org.bukkit.entity.Player::class) reified E> LuckPerms.getPlayerAdapter() = this.getPlayerAdapter(E::class.java)
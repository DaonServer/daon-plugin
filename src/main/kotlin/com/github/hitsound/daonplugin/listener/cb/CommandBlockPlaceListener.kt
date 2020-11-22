package com.github.hitsound.daonplugin.listener.cb

import com.github.hitsound.daonplugin.effect.SendMessageEffect
import com.github.hitsound.daonplugin.listener.SingleEventListener
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.block.Action
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.player.PlayerInteractEvent

object CommandBlockPlaceListener : SingleEventListener<PlayerInteractEvent> {
    @EventHandler
    override fun onEvent(event: PlayerInteractEvent) {
        // OPはコマンドブロックを置けるので必要ない
        val p = event.player
        if (p.isOp) return

        when(event.action) {
            Action.RIGHT_CLICK_BLOCK -> {
                when (val mat = event.material) {
                    Material.COMMAND, Material.COMMAND_CHAIN, Material.COMMAND_REPEATING -> {
                        // event.clickedBlock.getRelative(event.blockFace).type = mat
                        // TODO
                        SendMessageEffect(p, "TODO: Place Command Block")()
                    }
                    else -> return
                }
            }
            else -> {
                return
            }
        }
    }
}
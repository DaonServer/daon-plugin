package com.github.hitsound.daonplugin.command

import com.boydti.fawe.FaweAPI
import com.boydti.fawe.`object`.FaweLocation
import com.boydti.fawe.`object`.FawePlayer
import com.boydti.fawe.`object`.changeset.DiskStorageHistory
import com.github.hitsound.daonplugin.effect.SendMessageEffect
import com.github.hitsound.daonplugin.effect.SequentialEffect
import net.coreprotect.CoreProtect
import net.coreprotect.CoreProtectAPI
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object LookupEditHistory : CommandExecutor {
    override fun onCommand(sender: CommandSender?, command: Command?, label: String?, args: Array<out String>?): Boolean {
        sender!!
        command!!
        label!!
        args!!

        if (args.size != 3) return false
        if (sender !is Player) return false
        val (x, y, z) = args.map(String::toInt)
        val w = sender.world

        SendMessageEffect(sender, "Looking up history for block[$x $y $z]...")
        // TODO CoreProtect
        val cpAPI = ((Bukkit.getPluginManager().getPlugin("CoreProtect") as? CoreProtect) ?: run {
            SendMessageEffect(sender, "CoreProtect is not loaded!")()
            return true
        }).api

        val cpLookup = cpAPI.blockLookup(w.getBlockAt(x, y, z), 99999999)
                .map(cpAPI::parseResult)

        // TODO FAWE
        val joinedUUID = Bukkit.getOfflinePlayers()
        val vgh = joinedUUID.map {
            FaweAPI.getBDFiles(FaweLocation(w.name, x, y, z), it.uniqueId, 1, 99999999, false)
        }.reduce { acc, e ->
            ArrayList<DiskStorageHistory>(acc.size + e.size).apply {
                addAll(acc)
                addAll(e)
            }
        }

        SequentialEffect(
                listOf(
                        SendMessageEffect(sender, "CoreProtect log:"),
                        SendMessageEffect(sender, cpLookup.joinToString("\n") { "${it.player}: ${it.actionString} ${it.type} @ ${it.time}" }),
                        SendMessageEffect(sender, vgh.joinToString("\n") { "${it.uuid}: *Block(Modify)* ${it.blockIS.readNBT().tag} @ ${ワグなit.bdFile.lastModified()}" })
                )
        )()
        return true
    }
}
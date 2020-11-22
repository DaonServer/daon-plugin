package com.github.hitsound.daonplugin.command

import com.github.hitsound.daonplugin.DaonPlugin
import com.github.hitsound.daonplugin.effect.SendMessageEffect
import net.luckperms.api.LuckPermsProvider
import net.luckperms.api.model.data.DataMutateResult
import net.luckperms.api.model.group.Group
import net.luckperms.api.node.Node
import net.luckperms.api.node.types.InheritanceNode
import net.luckperms.api.query.QueryOptions
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object ColorCommand : CommandExecutor {
    // TODO: きちんと動作しない
    override fun onCommand(sender: CommandSender?, command: Command?, label: String?, args: Array<out String>?): Boolean {
        if (sender !is Player) return true
        command!!
        label!!
        args!!
        val api = LuckPermsProvider.get()
        val uuid = sender.uniqueId
        val groups: Set<Group> = api.groupManager.loadedGroups
        if (args.size == 1) {
            val color = args[0]
            val lpUser = api.userManager.getUser(uuid) ?: return true
            val userGroups: Collection<Group> =
                    lpUser.getInheritedGroups(QueryOptions.nonContextual())
            DaonPlugin.INSTANCE.logger.info("lpGroups for " + sender.name + ": " + userGroups.joinToString(", ") { it.name })
            val prevColors = groups.filter { it.name.startsWith("color") && it.name != "color" && userGroups.contains(it) }
            DaonPlugin.INSTANCE.logger.info(prevColors.joinToString(", ") { it.name })
            // https://luckperms.net/wiki/Developer-API-Usage#modifying-usergroup-data
            val allWereSuccessful = prevColors.map {
                lpUser.data().remove(InheritanceNode.builder(it).build())
            }.all(DataMutateResult::wasSuccessful)
            if (!allWereSuccessful) {
                SendMessageEffect(sender, "LP<group.remove> wasn't successful")()
                return true
            }

            val addSuccessful = lpUser.data().add(InheritanceNode.builder(groups.single { it.name == "color$color" }).build()).wasSuccessful()

            if (addSuccessful) {
                SendMessageEffect(sender, "color change successful")
            } else {
                SendMessageEffect(sender, "LP<group.add> wasn't successful")
            }()
            lpUser.cachedData.invalidate()

        }
        return true
    }
}
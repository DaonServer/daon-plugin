package com.github.hitsound.daonplugin.command

import com.github.hitsound.daonplugin.effect.SendMessageEffect
import com.github.hitsound.daonplugin.external.LuckPermsWrapper
import net.luckperms.api.model.group.Group
import net.luckperms.api.model.user.User
import net.luckperms.api.query.QueryOptions
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*

object UserRightsCommand : CommandExecutor {
    override fun onCommand(p: CommandSender?, command: Command?, label: String?, args: Array<out String>?): Boolean {
        p!!
        command!!
        label!!
        args!!

        if (p !is Player) return true
        val api = LuckPermsWrapper.api()
        val user: User

        when (args.size) {
            0 -> {
                user = api.userManager.getUser(p.uniqueId) ?: run {
                    SendMessageEffect(p, "Your permission data is not loaded. Please try again.")()
                    return true
                }
            }
            1 -> {
                user = api.userManager.getUser(UUID.fromString(args[0])) ?: run {
                    SendMessageEffect(p, "`${args[0]}`'s permission data is not loaded. Please try again.")()
                    return true
                }
            }
            else -> {
                return true
            }
        }
        user.cachedData.metaData().reload().get()
        val group = user.getInheritedGroups(QueryOptions.nonContextual()) as Collection<Group>
        val no = "" + ChatColor.GRAY + ChatColor.STRIKETHROUGH
        val find = {s: String -> group.any { it.name == s }}
        val bc = "" + if (find("Bureaucrat")) {
            ChatColor.DARK_RED
        } else {
            no
        } + "B"
        val mapper = "" + if (find("Mapper")) {
            ChatColor.BLUE
        } else {
            no
        } + "M"
        val dev = "" + if (find("Developer")) {
            ChatColor.GOLD
        } else {
            no
        } + "D"

        val announcer = "" + if (find("Announcer")) {
            ChatColor.GREEN
        } else {
            no
        } + "A"
        val mes = bc + mapper + dev + announcer
        SendMessageEffect(p, mes)()
        return true


        return true
    }
}
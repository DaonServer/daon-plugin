package com.github.hitsound.daonplugin.util

import com.github.hitsound.daonplugin.DaonPlugin
import com.github.hitsound.daonplugin.extension.pipe
import org.bukkit.Bukkit
import org.intellij.lang.annotations.Language
import java.io.File
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.CompletableFuture

object MinecraftNative {
    init {
        println("Daon plugin: " + Bukkit.getVersion())
    }

    @JvmOverloads
    fun getTranslation(lang: String, key: String, arguments: List<String> = emptyList()): String {
        val serverMajorVersion = 12
        val index = File(DaonPlugin.INSTANCE.dataFolder, "index/1.$serverMajorVersion.json")
        val theJson = if (!index.exists()) {
            val res = asyncHttpGet("https://launchermeta.mojang.com/v1/packages/1584b57c1a0b5e593fad1f5b8f78536ca640547b/1.$serverMajorVersion.json")
            index.createNewFile()
            index.bufferedWriter().write(res)
            res
        } else {
            index.readLines().joinToString("")
        }


        // parse json

        return "???"
    }

    fun <T> future(x: () -> T): CompletableFuture<T> {
        return CompletableFuture.supplyAsync(x)
    }

    private fun asyncHttpGet(@Language("url") url: String): String {
        val req = URL(url).openConnection() as HttpURLConnection
        req.connect()
        return req.inputStream.bufferedReader().lineSequence().joinToString("")
    }
}
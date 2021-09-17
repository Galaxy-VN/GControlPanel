package io.github.galaxyvn.gcontrolpanel.module.internal.service

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import io.github.galaxyvn.gcontrolpanel.GControlPanel
import org.bukkit.event.player.PlayerJoinEvent
import taboolib.common.LifeCycle
import taboolib.common.env.DependencyDownloader
import taboolib.common.platform.Awake
import taboolib.common.platform.SkipTo
import taboolib.common.platform.event.EventPriority
import taboolib.common.platform.event.SubscribeEvent
import taboolib.common.platform.function.console
import taboolib.common.platform.function.submit
import taboolib.module.lang.sendLang
import taboolib.platform.util.sendLang
import java.io.BufferedInputStream
import java.net.URL
import java.nio.charset.StandardCharsets
import java.util.*

@SkipTo(LifeCycle.ENABLE)
class Updater {

    private val API_URL = "https://minecraftvn.net/resource_version.api?id=3178"
    private val DESCRIPTION = GControlPanel.plugin.description
    private var NOTIFY = false
    val CURRENT_VERSION = DESCRIPTION.version.split("-")[0].toDoubleOrNull() ?: -1.0
    var LATEST_VERSION = -1.0
    val NOTIFIED = mutableSetOf<UUID>()

    @Awake(LifeCycle.INIT)
    fun init() {
        submit(delay = 20, period = (10 * 60 * 20), async = true) {
            grabInfo()
        }
    }

    private fun grabInfo() {
        if (LATEST_VERSION > 0) {
            return
        }
        val read: String
        try {
            URL(API_URL).openStream().use { inputStream ->
                BufferedInputStream(inputStream).use { bufferedInputStream ->
                    read = DependencyDownloader.readFully(bufferedInputStream, StandardCharsets.UTF_8)
                    val json = JsonParser().parse(read) as JsonObject
                    val latestVersion = json.get("tag_name").asDouble
                    if (latestVersion > CURRENT_VERSION) {
                        LATEST_VERSION = latestVersion
                        if (!NOTIFY) {
                            NOTIFY = true
                            console().sendLang("Plugin-Update", LATEST_VERSION)
                        }
                    }
                }
            }
        } catch (e: Throwable) { }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    fun onJoin(e: PlayerJoinEvent) {
        val player = e.player

        if (player.isOp && LATEST_VERSION > CURRENT_VERSION && !NOTIFIED.contains(player.uniqueId)) {
            player.sendLang("Plugin-Update", LATEST_VERSION)
            NOTIFIED.add(player.uniqueId)
        }
    }
}
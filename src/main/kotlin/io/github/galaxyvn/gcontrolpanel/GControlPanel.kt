package io.github.galaxyvn.gcontrolpanel

import io.github.galaxyvn.gcontrolpanel.module.internal.hook.HookPlugin
import org.bukkit.Bukkit
import taboolib.common.platform.*
import taboolib.common.platform.function.console
import taboolib.module.lang.Language
import taboolib.module.lang.sendLang
import taboolib.platform.BukkitPlugin

object GControlPanel : Plugin() {

    val plugin by lazy { BukkitPlugin.getInstance() }

    override fun onLoad() {
        Language.default = "vi_VN"
        console().sendLang("Plugin-Loading", Bukkit.getVersion())
    }

    override fun onEnable() {
        console().sendLang("Plugin-Enabled", plugin.description.version)
        HookPlugin.printInfo()
    }
}
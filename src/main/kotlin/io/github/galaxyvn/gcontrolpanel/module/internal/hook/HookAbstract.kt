package io.github.galaxyvn.gcontrolpanel.module.internal.hook

import org.bukkit.Bukkit
import org.bukkit.Bukkit.getPluginManager
import org.bukkit.plugin.Plugin
import taboolib.common.platform.function.console
import taboolib.module.lang.sendLang

abstract class HookAbstract {

    open val name by lazy { getPluginName() }

    val plugin: Plugin? by lazy {
        Bukkit.getPluginManager().getPlugin(name)
    }

    val isHooked by lazy {
        plugin != null && plugin!!.isEnabled
    }

    open fun getPluginName(): String {
        return javaClass.simpleName.substring(4)
    }

    fun checkHooked(): Boolean {
        return if (isHooked) true else false.also { reportAbuse() }
    }

    fun reportAbuse() {
        console().sendLang("Plugin-Dependency-Abuse", name)
    }
}
package io.github.galaxyvn.gcontrolpanel.module.internal.hook

import io.github.galaxyvn.gcontrolpanel.module.internal.hook.impl.HookSkinsRestorer
import taboolib.common.platform.function.console
import taboolib.module.lang.sendLang

object HookPlugin {

    fun printInfo() {
        registry.filter { it.isHooked }.forEach {
            console().sendLang("Plugin-Dependency-Hooked", it.name)
        }
    }

    private val registry: Array<HookAbstract> = arrayOf(
        HookSkinsRestorer()
    )

    fun getSkinsRestorer(): HookSkinsRestorer {
        return registry[0] as HookSkinsRestorer
    }
}
package io.github.galaxyvn.gcontrolpanel.module.internal.hook.impl

import com.mojang.authlib.properties.Property
import io.github.galaxyvn.gcontrolpanel.module.internal.hook.HookAbstract
import net.skinsrestorer.api.SkinsRestorerAPI

class HookSkinsRestorer : HookAbstract() {

    private val skinsRestorerAPI: SkinsRestorerAPI? =
        if (isHooked) {
            SkinsRestorerAPI.getApi()
        } else {
            null
        }
        get() {
            if (field == null) reportAbuse()
            return field
    }

    fun getPlayerSkinTexture(name: String): String? {
        skinsRestorerAPI?.let {
            if (it.getSkinData(name) == null) {
                return null
            }

            val skinData = it.getSkinData(name)
            return (skinData as Property).value
        }
        return null
    }
}
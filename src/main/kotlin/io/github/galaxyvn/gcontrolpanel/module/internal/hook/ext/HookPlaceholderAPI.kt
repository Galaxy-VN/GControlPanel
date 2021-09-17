package io.github.galaxyvn.gcontrolpanel.module.internal.hook.ext

import org.bukkit.entity.Player
import taboolib.platform.compat.PlaceholderExpansion

object HookPlaceholderAPI : PlaceholderExpansion {

    override val identifier = "gcontrolpanel"

    override fun onPlaceholderRequest(player: Player, params: String): String {
        if (player.isOnline) {
            val args = params.split("_")

            return when (args[0].lowercase()) {
                "name" -> player.name
                "uuid" -> player.uniqueId.toString()
                "has_played_before" -> player.hasPlayedBefore().toString()
                "online" -> player.isOnline.toString()
                "is_whitelisted" -> player.isWhitelisted.toString()
                "is_banned" -> player.isBanned.toString()
                "is_op" -> player.isOp.toString()
                "first_played" -> player.firstPlayed.toString()
                "first_join" -> player.firstPlayed.toString()
                else -> ""
            }.toString()
        }

        return "__"
    }
}
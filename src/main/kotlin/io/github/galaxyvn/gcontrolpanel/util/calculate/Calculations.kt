package io.github.galaxyvn.gcontrolpanel.util.calculate

import org.bukkit.entity.Player

object Calculations {

    fun levelToExp(level: Int): Int {
        return if (level <= 15) {
            (level * level + 6 * level).toInt()
        } else if (level <= 30) {
            (2.5 * level * level - 40.5 * level + 360).toInt()
        } else {
            (4.5 * level * level - 162.5 * level + 2220).toInt()
        }
    }

    fun deltaLevelToExp(level: Int): Int {
        return if (level <= 15) {
            2 * level + 7
        } else if (level <= 30) {
            5 * level - 38
        } else {
            9 * level - 158
        }
    }

    fun currentlevelxpdelta(player: Player): Int {
        return (deltaLevelToExp(player.level) - (levelToExp(player.level) + deltaLevelToExp(player.level) * player.exp - levelToExp(player.level))).toInt()
    }

    fun getPlayerExperience(player: Player): Int {
        return (levelToExp(player.level) + deltaLevelToExp(player.level) * player.exp).toInt()
    }
}
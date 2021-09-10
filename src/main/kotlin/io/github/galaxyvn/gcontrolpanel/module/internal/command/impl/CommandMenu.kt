package io.github.galaxyvn.gcontrolpanel.module.internal.command.impl

import io.github.galaxyvn.gcontrolpanel.module.internal.command.CommandExpresser
import io.github.galaxyvn.gcontrolpanel.module.internal.menus.MenuControlPanel
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import taboolib.common.platform.command.SimpleCommandBody
import taboolib.common.platform.command.subCommand

object CommandMenu : CommandExpresser {

    override val command = subCommand {
        execute<Player> { sender, _, _ ->
            MenuControlPanel.displayFor(sender)
        }
    }
}
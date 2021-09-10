package io.github.galaxyvn.gcontrolpanel.module.internal.command.impl

import io.github.galaxyvn.gcontrolpanel.module.internal.command.CommandExpresser
import org.bukkit.command.CommandSender
import taboolib.common.platform.command.SimpleCommandBody
import taboolib.common.platform.command.subCommand

object CommandReload : CommandExpresser {

    override val command = subCommand {
        execute<CommandSender> { sender, context, argument ->

        }
    }
}
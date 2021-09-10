package io.github.galaxyvn.gcontrolpanel.module.internal.command

import io.github.galaxyvn.gcontrolpanel.GControlPanel
import io.github.galaxyvn.gcontrolpanel.module.internal.command.impl.CommandMenu
import io.github.galaxyvn.gcontrolpanel.module.internal.menus.MenuControlPanel
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import taboolib.common.platform.command.CommandBody
import taboolib.common.platform.command.CommandHeader
import taboolib.common.platform.command.mainCommand
import taboolib.common.platform.command.subCommand
import taboolib.common.platform.function.adaptCommandSender
import taboolib.module.chat.TellrawJson
import taboolib.module.nms.MinecraftVersion
import taboolib.platform.util.asLangText

@CommandHeader(name = "gcontrolpanel", aliases = ["gcp", "gcontrolp", "gcontrol"], permission = "gcontrolpanel.access")
object CommandHandler {

    @CommandBody(permission = "gcontrolpanel.command.menu", optional = true)
    val menu = CommandMenu.command

    @CommandBody
    val help = subCommand {
        execute<CommandSender> { sender, _, _ ->
            generateMainHelper(sender)
        }
    }

    @CommandBody
    val main = mainCommand {
        execute<CommandSender> { sender, _, argument ->
            if (argument.isEmpty()) {
                generateMainHelper(sender)
                return@execute
            }
            sender.sendMessage("§8[§6GControlPanel§8] §cLỖI §3| Args §6$argument §3không tìm thấy.")
            TellrawJson()
                .append("§8[§6GControlPanel§8] §bTHÔNG TIN §3| Nhập ").append("§f/gcontrolpanel help")
                .hoverText("§f/gcontrolpanel help §8- §7để có thêm trợ giúp...")
                .suggestCommand("/gcontrolpanel help")
                .append("§3 cho trợ giúp.")
                .sendTo(adaptCommandSender(sender))
        }
    }

    fun generateMainHelper(sender: CommandSender) {
        val proxySender = adaptCommandSender(sender)
        proxySender.sendMessage("")
        TellrawJson()
            .append("  ").append("§6GControlPanel")
            .hoverText("§7Advanced control player thought a gui")
            .append("  ").append("§f${GControlPanel.plugin.description.version}")
            .hoverText("""
                §7Plugin version: §2${GControlPanel.plugin.description.version}
                §7NMS version: §2${MinecraftVersion.minecraftVersion}
            """.trimIndent()).sendTo(proxySender)
        proxySender.sendMessage("")
        TellrawJson()
            .append("  §7${sender.asLangText("Command-Help-Type")}: ").append("§f/gcontrolpanel §8[...]")
            .hoverText("§f/gcontrolpanel §8[...]")
            .suggestCommand("/gcontrolpanel ")
            .sendTo(proxySender)
        proxySender.sendMessage("  §7${sender.asLangText("Command-Help-Args")}")

        fun displayArg(name: String, desc: String) {
            TellrawJson()
                .append("    §8- ").append("§f$name")
                .hoverText("§/gcontrolpanel $name §8- §7$desc")
                .suggestCommand("/gcontrolpanel $name ")
                .sendTo(proxySender)
            proxySender.sendMessage("      §7$desc")
        }
        displayArg("menu", sender.asLangText("Command-ControlPanel-Description"))
        proxySender.sendMessage("")
    }
}
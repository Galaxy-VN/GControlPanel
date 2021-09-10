package io.github.galaxyvn.gcontrolpanel.module.internal.menus

import io.github.galaxyvn.gcontrolpanel.util.bukkit.Heads
import org.bukkit.entity.Player
import org.bukkit.inventory.meta.SkullMeta
import sun.audio.AudioPlayer.player
import taboolib.common.platform.ProxyPlayer
import taboolib.common.platform.function.onlinePlayers
import taboolib.library.xseries.XMaterial
import taboolib.library.xseries.XSkull
import taboolib.module.kether.action.game.modify
import taboolib.module.ui.ClickEvent
import taboolib.module.ui.openMenu
import taboolib.module.ui.receptacle.Receptacle
import taboolib.module.ui.type.Basic
import taboolib.module.ui.type.Linked
import taboolib.platform.util.ItemBuilder
import taboolib.platform.util.asLangText
import taboolib.platform.util.buildItem
import taboolib.platform.util.inventoryCenterSlots

object MenuControlPanel {

    fun displayFor(player: Player) {
        player.openMenu<Linked<Player>>("§0ControlPanel » Main") {
            rows(6)
            slots(inventoryCenterSlots)
            elements { onlinePlayers().map { it.cast() } }
            setPreviousPage(47) { _, hasPreviousPage ->
                if (hasPreviousPage) {
                    buildItem(XMaterial.ARROW) {
                        name = "§f${player.asLangText("Menu-Internal-Page-Previous")}"
                    }
                } else {
                    buildItem(XMaterial.BLACK_STAINED_GLASS_PANE) {
                        name = "§8${player.asLangText("Menu-Internal-Page-Previous")}"
                    }
                }
            }
            setNextPage(51) { _, hasNextPage ->
                if (hasNextPage) {
                    buildItem(XMaterial.ARROW) {
                        name = "§f${player.asLangText("Menu-Internal-Page-Next")}"
                    }
                } else {
                    buildItem(XMaterial.BLACK_STAINED_GLASS_PANE) {
                        name = "§8${player.asLangText("Menu-Internal-Page-Next")}"
                    }
                }
            }
            onGenerate { _, element, _, _ ->
                buildItem(XMaterial.PLAYER_HEAD) {
                    skullOwner = element.name
                    name = "&e${element.name}"
                    lore += listOf(
                        "",
                        "&a➦ Nhấp vào Quản lý",
                        ""
                    )
                    colored()
                }
            }
            onClick() { event, element ->
                each(event.clicker, element)
            }
        }
    }

    private fun each(player: Player, target: Player) {
        player.openMenu<Basic>("§0ControlPanel » ${target.name}") {
            rows(5)
            map(
                "<###P####",
                "         ",
                " 1 2 3 4 ",
                " 5 6 7 8 "
            )
            set('#', XMaterial.BLACK_STAINED_GLASS_PANE) {
                name = " "
            }
            set('P', XMaterial.PLAYER_HEAD) {
                skullOwner = target.name
                name = "&e${target.name}"
                lore += listOf(
                    "",
                    "&aTên Người Chơi: &7${target.name}",
                    "&aUUID Người Chơi: &7${target.uniqueId}",
                    ""
                )
                colored()
            }
            set('1', XMaterial.ENDER_PEARL) {
                name = "&bDịch Chuyển"
                lore += listOf(
                    "",
                    "&7Dịch chuyển đến",
                    "&7người chơi ${target.name}",
                    "",
                    "&8[&2Chuột Phải&8]&3 ➥&7 Dịch chuyển người chơi đến bạn",
                    "&8[&2Chuột Trái&8]&3 ➥&7 Dịch chuyển đến người chơi"
                )
                colored()
            }
            set('2', XMaterial.CHEST) {
                name = "&6Kho Đồ"
                lore += listOf(
                    "",
                    "&7Quản lí kho đồ của",
                    "&7người chơi ${target.name}",
                    "",
                    "&8[&cShift Chuột Phải&8]&3 ➥&7 Xóa kho đồ của người chơi",
                    "&8[&2Chuột Trái&8]&3 ➥&7 Mở kho đồ của người chơi"
                )
                colored()
            }
            set('3', XMaterial.ENDER_CHEST) {
                name = "&5Rương Đồ Ender"
                lore += listOf(
                    "",
                    "&7Quản lí rương đồ ender của",
                    "&7người chơi ${target.name}",
                    "",
                    "&8[&cShift Chuột Trái&8]&3 ➥&7 Xóa rương đồ ender của người chơi",
                    "&8[&2Chuột Phải&8]&3 ➥&7 Mở rương đồ ender của người chơi"
                )
                colored()
            }
            set('4', XMaterial.DIAMOND_SWORD) {
                name = "&cGiết Người Chơi"
                lore += listOf(
                    "",
                    "&7Giết chết người chơi",
                    "&7${target.name}",
                    "",
                    "&3➥&7 Giết người chơi",
                )
                colored()
            }
            set('<', XMaterial.ARROW) {
                name = "&cQuay Lại"
                colored()
            }
            onClick(lock = true) { clickEvent ->
                when (clickEvent.slot) {
                    '<' -> {
                        MenuControlPanel.displayFor(player)
                    }
                    '1' -> {
                        if (clickEvent.clickEvent().isRightClick) {
                            player.teleport(target)
                            player.sendMessage(player.asLangText("Player-Teleport-Successfully", target.name))
                        }
                        if (clickEvent.clickEvent().isLeftClick) {
                           target.teleport(player)
                           player.sendMessage(player.asLangText("Target-Teleport-Successfully", target.name))
                        }
                    }
                    '2' -> {
                        if (clickEvent.clickEvent().isLeftClick) {
                            player.openInventory(target.inventory)
                        }
                        if (clickEvent.clickEvent().isRightClick && clickEvent.clickEvent().isShiftClick) {
                            player.closeInventory()
                            target.inventory.clear()
                            player.sendMessage(player.asLangText("Player-Clear-Inventory-Successfully"), target.name)
                        }
                    }
                    '3' -> {
                        if (clickEvent.clickEvent().isLeftClick) {
                            player.openInventory(target.enderChest)
                        }
                        if (clickEvent.clickEvent().isRightClick && clickEvent.clickEvent().isShiftClick) {
                            player.closeInventory()
                            target.enderChest.clear()
                            player.sendMessage(player.asLangText("Player-Clear-EnderChest-Successfully", target.name))
                        }
                    }
                    '4' -> {
                        player.closeInventory()
                        player.health = 0.0
                        player.sendMessage(player.asLangText("Player-Kill-Successfully", target.name))
                    }
                }
            }
        }
    }
}
package io.github.galaxyvn.gcontrolpanel.module.internal.menus

import io.github.galaxyvn.gcontrolpanel.GControlPanel.plugin
import org.bukkit.BanList
import org.bukkit.ChatColor
import org.bukkit.Color
import org.bukkit.entity.Player
import org.bukkit.potion.PotionData
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import taboolib.common.platform.function.onlinePlayers
import taboolib.common.platform.function.submit
import taboolib.common5.Coerce
import taboolib.library.xseries.XMaterial
import taboolib.library.xseries.XPotion
import taboolib.module.ui.openMenu
import taboolib.module.ui.type.Basic
import taboolib.module.ui.type.Linked
import taboolib.platform.util.*

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
                    "&aIp Người Chơi: &7${target.address}",
                    "&aThế Giới Người Chơi: &7${target.world.name}",
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
            set('5', XMaterial.IRON_BOOTS) {
                name = "&cĐá Người Chơi"
                lore += listOf(
                    "",
                    "&7Đá người chơi ${target.name}",
                    "&7ra khỏi máy chủ.",
                    "",
                    "&3➥&7 Đá người chơi",
                )
                colored()
            }
            if (!plugin.server.getBanList(BanList.Type.NAME).isBanned(target.name)) {
                set('6', XMaterial.BARRIER) {
                    name = "&cCấm Người Chơi"
                    lore += listOf(
                        "",
                        "&7Cấm người chơi ${target.name}",
                        "&7khỏi máy chủ.",
                        "",
                        "&3➥&7 Cấm người chơi",
                    )
                    colored()
                }
            } else {
                set('6', XMaterial.BARRIER) {
                    name = "&cXóa Cấm Người Chơi"
                    lore += listOf(
                        "",
                        "&7Xóa cấm người chơi ${target.name}",
                        "&7trong máy chủ.",
                        "",
                        "&3➥&7Xóa cấm người chơi",
                    )
                    colored()
                }
            }
            set('7', XMaterial.POTION) {
                name = "&cQuản Lí Thuốc Người Chơi"
                lore += listOf(
                    "",
                    "&7Quản lí thuốc người chơi ${target.name}",
                    "&7trong máy chủ.",
                    "",
                    "&3➥&7 Quản lí thuốc",
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
                            target.teleport(player)
                            player.sendMessage(player.asLangText("Target-Teleport-Successfully", target.name))
                        }
                        if (clickEvent.clickEvent().isLeftClick) {
                            player.teleport(target)
                            player.sendMessage(player.asLangText("Player-Teleport-Successfully", target.name))
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
                        target.damage(target.health)
                        player.sendMessage(player.asLangText("Player-Kill-Successfully", target.name))
                    }
                    '5' -> {
                        player.closeInventory()
                        player.sendMessage(player.asLangText("Player-Kick-Reason"))
                        player.nextChat {
                            if (Coerce.asString(it).isPresent) {
                                submit {
                                    target.kickPlayer(Coerce.toString(it))
                                }
                                player.sendMessage(player.asLangText("Player-Kick-Successfully"))
                            }
                        }
                    }
                    '6' -> {
                        if (!plugin.server.getBanList(BanList.Type.NAME).isBanned(target.name)) {
                            player.closeInventory()
                            player.sendMessage(player.asLangText("Player-Ban-Reason"))
                            player.nextChat {
                                plugin.server.getBanList(BanList.Type.NAME).addBan(target.name, Coerce.toString(it), null, player.name)
                            }
                            player.sendMessage(player.asLangText("Player-Ban-Successfully"))
                        } else {
                            player.closeInventory()
                            plugin.server.getBanList(BanList.Type.NAME).pardon(target.name)
                            player.sendMessage(player.asLangText("Player-Unban-Successfully"))
                        }
                    }
                    '7' -> {
                        player.openMenu<Basic>("§0${target.name} » Potion") {
                            rows(6)
                            map(
                                "<###@####",
                                "123456789",
                                "abcdefghi",
                                "jklmnopqr",
                                "stuvw    ",
                                "#########"
                            )
                            set('#', XMaterial.BLACK_STAINED_GLASS_PANE) {
                                name = " "
                            }
                            set('<', XMaterial.ARROW) {
                                name = "&cQuay Lại"
                                colored()
                            }
                            set('@', XMaterial.PLAYER_HEAD) {
                                skullOwner = target.name
                                name = "&cXóa Tất Cả Hiệu Ứng"
                                lore += listOf(
                                    "",
                                    "&7Nhấn để xóa!",
                                    ""
                                )
                                colored()
                            }
                            set('1', XMaterial.POTION) {
                                name = "&eAbsorption"
                                lore += listOf(
                                    "",
                                    "&7Hiệu ứng absorption",
                                    "",
                                    "&8[&2Chuột Trái&8]&3 ➥&7 Áp dụng hiệu ứng cho người chơi",
                                    "&8[&2Chuột Phải&8]&3 ➥&7 Xóa hiệu hiệu ứng của người chơi"
                                )
                                color = Color.YELLOW
                                shiny()
                                colored()
                            }
                            set('2', XMaterial.POTION) {
                                name = "&2Bad Omen"
                                lore += listOf(
                                    "",
                                    "&7Hiệu ứng bad omen",
                                    "",
                                    "&8[&2Chuột Trái&8]&3 ➥&7 Áp dụng hiệu ứng cho người chơi",
                                    "&8[&2Chuột Phải&8]&3 ➥&7 Xóa hiệu hiệu ứng của người chơi"
                                )
                                color = Color.GREEN
                                shiny()
                                colored()
                            }
                            set('3', XMaterial.POTION) {
                                name = "&8Blindness"
                                lore += listOf(
                                    "",
                                    "&7Hiệu ứng blindness",
                                    "",
                                    "&8[&2Chuột Trái&8]&3 ➥&7 Áp dụng hiệu ứng cho người chơi",
                                    "&8[&2Chuột Phải&8]&3 ➥&7 Xóa hiệu hiệu ứng của người chơi"
                                )
                                color = Color.BLACK
                                shiny()
                                colored()
                            }
                            set('4', XMaterial.POTION) {
                                name = "&bConduit Power"
                                lore += listOf(
                                    "",
                                    "&7Hiệu ứng conduit power",
                                    "",
                                    "&8[&2Chuột Trái&8]&3 ➥&7 Áp dụng hiệu ứng cho người chơi",
                                    "&8[&2Chuột Phải&8]&3 ➥&7 Xóa hiệu hiệu ứng của người chơi"
                                )
                                color = Color.fromRGB(224,255,255)
                                shiny()
                                colored()
                            }
                            set('5', XMaterial.POTION) {
                                name = "&bConfusion"
                                lore += listOf(
                                    "",
                                    "&7Hiệu ứng conduit power",
                                    "",
                                    "&8[&2Chuột Trái&8]&3 ➥&7 Áp dụng hiệu ứng cho người chơi",
                                    "&8[&2Chuột Phải&8]&3 ➥&7 Xóa hiệu hiệu ứng của người chơi"
                                )
                                color = Color.fromRGB(224,255,255)
                                shiny()
                                colored()
                            }
                            set('6', XMaterial.POTION) {
                                name = "&bDamage Resistance"
                                lore += listOf(
                                    "",
                                    "&7Hiệu ứng conduit power",
                                    "",
                                    "&8[&2Chuột Trái&8]&3 ➥&7 Áp dụng hiệu ứng cho người chơi",
                                    "&8[&2Chuột Phải&8]&3 ➥&7 Xóa hiệu hiệu ứng của người chơi"
                                )
                                color = Color.fromRGB(224,255,255)
                                shiny()
                                colored()
                            }
                            set('7', XMaterial.POTION) {
                                name = "&bDolphin Grace"
                                lore += listOf(
                                    "",
                                    "&7Hiệu ứng conduit power",
                                    "",
                                    "&8[&2Chuột Trái&8]&3 ➥&7 Áp dụng hiệu ứng cho người chơi",
                                    "&8[&2Chuột Phải&8]&3 ➥&7 Xóa hiệu hiệu ứng của người chơi"
                                )
                                color = Color.fromRGB(224,255,255)
                                shiny()
                                colored()
                            }
                            set('8', XMaterial.POTION) {
                                name = "&bFast Digging"
                                lore += listOf(
                                    "",
                                    "&7Hiệu ứng conduit power",
                                    "",
                                    "&8[&2Chuột Trái&8]&3 ➥&7 Áp dụng hiệu ứng cho người chơi",
                                    "&8[&2Chuột Phải&8]&3 ➥&7 Xóa hiệu hiệu ứng của người chơi"
                                )
                                color = Color.fromRGB(224,255,255)
                                shiny()
                                colored()
                            }
                            set('9', XMaterial.POTION) {
                                name = "&bFire Resistance"
                                lore += listOf(
                                    "",
                                    "&7Hiệu ứng conduit power",
                                    "",
                                    "&8[&2Chuột Trái&8]&3 ➥&7 Áp dụng hiệu ứng cho người chơi",
                                    "&8[&2Chuột Phải&8]&3 ➥&7 Xóa hiệu hiệu ứng của người chơi"
                                )
                                color = Color.fromRGB(224,255,255)
                                shiny()
                                colored()
                            }
                            set('a', XMaterial.POTION) {
                                name = "&bGlowing"
                                lore += listOf(
                                    "",
                                    "&7Hiệu ứng conduit power",
                                    "",
                                    "&8[&2Chuột Trái&8]&3 ➥&7 Áp dụng hiệu ứng cho người chơi",
                                    "&8[&2Chuột Phải&8]&3 ➥&7 Xóa hiệu hiệu ứng của người chơi"
                                )
                                color = Color.fromRGB(224,255,255)
                                shiny()
                                colored()
                            }
                            set('b', XMaterial.POTION) {
                                name = "&bHarm"
                                lore += listOf(
                                    "",
                                    "&7Hiệu ứng conduit power",
                                    "",
                                    "&8[&2Chuột Trái&8]&3 ➥&7 Áp dụng hiệu ứng cho người chơi",
                                    "&8[&2Chuột Phải&8]&3 ➥&7 Xóa hiệu hiệu ứng của người chơi"
                                )
                                color = Color.fromRGB(224,255,255)
                                shiny()
                                colored()
                            }
                            set('c', XMaterial.POTION) {
                                name = "&bHeal"
                                lore += listOf(
                                    "",
                                    "&7Hiệu ứng conduit power",
                                    "",
                                    "&8[&2Chuột Trái&8]&3 ➥&7 Áp dụng hiệu ứng cho người chơi",
                                    "&8[&2Chuột Phải&8]&3 ➥&7 Xóa hiệu hiệu ứng của người chơi"
                                )
                                color = Color.fromRGB(224,255,255)
                                shiny()
                                colored()
                            }
                            set('d', XMaterial.POTION) {
                                name = "&bHealth Boost"
                                lore += listOf(
                                    "",
                                    "&7Hiệu ứng conduit power",
                                    "",
                                    "&8[&2Chuột Trái&8]&3 ➥&7 Áp dụng hiệu ứng cho người chơi",
                                    "&8[&2Chuột Phải&8]&3 ➥&7 Xóa hiệu hiệu ứng của người chơi"
                                )
                                color = Color.fromRGB(224,255,255)
                                shiny()
                                colored()
                            }
                            set('e', XMaterial.POTION) {
                                name = "&bHero Of The Village"
                                lore += listOf(
                                    "",
                                    "&7Hiệu ứng conduit power",
                                    "",
                                    "&8[&2Chuột Trái&8]&3 ➥&7 Áp dụng hiệu ứng cho người chơi",
                                    "&8[&2Chuột Phải&8]&3 ➥&7 Xóa hiệu hiệu ứng của người chơi"
                                )
                                color = Color.fromRGB(224,255,255)
                                shiny()
                                colored()
                            }
                            set('f', XMaterial.POTION) {
                                name = "&bHunger"
                                lore += listOf(
                                    "",
                                    "&7Hiệu ứng conduit power",
                                    "",
                                    "&8[&2Chuột Trái&8]&3 ➥&7 Áp dụng hiệu ứng cho người chơi",
                                    "&8[&2Chuột Phải&8]&3 ➥&7 Xóa hiệu hiệu ứng của người chơi"
                                )
                                color = Color.fromRGB(224,255,255)
                                shiny()
                                colored()
                            }
                            set('g', XMaterial.POTION) {
                                name = "&bIncrease Damage"
                                lore += listOf(
                                    "",
                                    "&7Hiệu ứng conduit power",
                                    "",
                                    "&8[&2Chuột Trái&8]&3 ➥&7 Áp dụng hiệu ứng cho người chơi",
                                    "&8[&2Chuột Phải&8]&3 ➥&7 Xóa hiệu hiệu ứng của người chơi"
                                )
                                color = Color.fromRGB(224,255,255)
                                shiny()
                                colored()
                            }
                            set('h', XMaterial.POTION) {
                                name = "&bInvisibility"
                                lore += listOf(
                                    "",
                                    "&7Hiệu ứng conduit power",
                                    "",
                                    "&8[&2Chuột Trái&8]&3 ➥&7 Áp dụng hiệu ứng cho người chơi",
                                    "&8[&2Chuột Phải&8]&3 ➥&7 Xóa hiệu hiệu ứng của người chơi"
                                )
                                color = Color.fromRGB(224,255,255)
                                shiny()
                                colored()
                            }
                            set('i', XMaterial.POTION) {
                                name = "&bJump"
                                lore += listOf(
                                    "",
                                    "&7Hiệu ứng conduit power",
                                    "",
                                    "&8[&2Chuột Trái&8]&3 ➥&7 Áp dụng hiệu ứng cho người chơi",
                                    "&8[&2Chuột Phải&8]&3 ➥&7 Xóa hiệu hiệu ứng của người chơi"
                                )
                                color = Color.fromRGB(224,255,255)
                                shiny()
                                colored()
                            }
                            set('j', XMaterial.POTION) {
                                name = "&bLevitation"
                                lore += listOf(
                                    "",
                                    "&7Hiệu ứng conduit power",
                                    "",
                                    "&8[&2Chuột Trái&8]&3 ➥&7 Áp dụng hiệu ứng cho người chơi",
                                    "&8[&2Chuột Phải&8]&3 ➥&7 Xóa hiệu hiệu ứng của người chơi"
                                )
                                color = Color.fromRGB(224,255,255)
                                shiny()
                                colored()
                            }
                            set('k', XMaterial.POTION) {
                                name = "&bLuck"
                                lore += listOf(
                                    "",
                                    "&7Hiệu ứng conduit power",
                                    "",
                                    "&8[&2Chuột Trái&8]&3 ➥&7 Áp dụng hiệu ứng cho người chơi",
                                    "&8[&2Chuột Phải&8]&3 ➥&7 Xóa hiệu hiệu ứng của người chơi"
                                )
                                color = Color.fromRGB(224,255,255)
                                shiny()
                                colored()
                            }
                            set('l', XMaterial.POTION) {
                                name = "&bNight Vision"
                                lore += listOf(
                                    "",
                                    "&7Hiệu ứng conduit power",
                                    "",
                                    "&8[&2Chuột Trái&8]&3 ➥&7 Áp dụng hiệu ứng cho người chơi",
                                    "&8[&2Chuột Phải&8]&3 ➥&7 Xóa hiệu hiệu ứng của người chơi"
                                )
                                color = Color.fromRGB(224,255,255)
                                shiny()
                                colored()
                            }
                            set('m', XMaterial.POTION) {
                                name = "&bPoison"
                                lore += listOf(
                                    "",
                                    "&7Hiệu ứng conduit power",
                                    "",
                                    "&8[&2Chuột Trái&8]&3 ➥&7 Áp dụng hiệu ứng cho người chơi",
                                    "&8[&2Chuột Phải&8]&3 ➥&7 Xóa hiệu hiệu ứng của người chơi"
                                )
                                color = Color.fromRGB(224,255,255)
                                shiny()
                                colored()
                            }
                            set('n', XMaterial.POTION) {
                                name = "&bRegeneration"
                                lore += listOf(
                                    "",
                                    "&7Hiệu ứng conduit power",
                                    "",
                                    "&8[&2Chuột Trái&8]&3 ➥&7 Áp dụng hiệu ứng cho người chơi",
                                    "&8[&2Chuột Phải&8]&3 ➥&7 Xóa hiệu hiệu ứng của người chơi"
                                )
                                color = Color.fromRGB(224,255,255)
                                shiny()
                                colored()
                            }
                            set('o', XMaterial.POTION) {
                                name = "&bSaturation"
                                lore += listOf(
                                    "",
                                    "&7Hiệu ứng conduit power",
                                    "",
                                    "&8[&2Chuột Trái&8]&3 ➥&7 Áp dụng hiệu ứng cho người chơi",
                                    "&8[&2Chuột Phải&8]&3 ➥&7 Xóa hiệu hiệu ứng của người chơi"
                                )
                                color = Color.fromRGB(224,255,255)
                                shiny()
                                colored()
                            }
                            set('p', XMaterial.POTION) {
                                name = "&bSlow"
                                lore += listOf(
                                    "",
                                    "&7Hiệu ứng conduit power",
                                    "",
                                    "&8[&2Chuột Trái&8]&3 ➥&7 Áp dụng hiệu ứng cho người chơi",
                                    "&8[&2Chuột Phải&8]&3 ➥&7 Xóa hiệu hiệu ứng của người chơi"
                                )
                                color = Color.fromRGB(224,255,255)
                                shiny()
                                colored()
                            }
                            set('q', XMaterial.POTION) {
                                name = "&bSlow Digging"
                                lore += listOf(
                                    "",
                                    "&7Hiệu ứng conduit power",
                                    "",
                                    "&8[&2Chuột Trái&8]&3 ➥&7 Áp dụng hiệu ứng cho người chơi",
                                    "&8[&2Chuột Phải&8]&3 ➥&7 Xóa hiệu hiệu ứng của người chơi"
                                )
                                color = Color.fromRGB(224,255,255)
                                shiny()
                                colored()
                            }
                            set('r', XMaterial.POTION) {
                                name = "&bSlow Falling"
                                lore += listOf(
                                    "",
                                    "&7Hiệu ứng conduit power",
                                    "",
                                    "&8[&2Chuột Trái&8]&3 ➥&7 Áp dụng hiệu ứng cho người chơi",
                                    "&8[&2Chuột Phải&8]&3 ➥&7 Xóa hiệu hiệu ứng của người chơi"
                                )
                                color = Color.fromRGB(224,255,255)
                                shiny()
                                colored()
                            }
                            set('s', XMaterial.POTION) {
                                name = "&bSpeed"
                                lore += listOf(
                                    "",
                                    "&7Hiệu ứng conduit power",
                                    "",
                                    "&8[&2Chuột Trái&8]&3 ➥&7 Áp dụng hiệu ứng cho người chơi",
                                    "&8[&2Chuột Phải&8]&3 ➥&7 Xóa hiệu hiệu ứng của người chơi"
                                )
                                color = Color.fromRGB(224,255,255)
                                shiny()
                                colored()
                            }
                            set('t', XMaterial.POTION) {
                                name = "&bUnluck"
                                lore += listOf(
                                    "",
                                    "&7Hiệu ứng conduit power",
                                    "",
                                    "&8[&2Chuột Trái&8]&3 ➥&7 Áp dụng hiệu ứng cho người chơi",
                                    "&8[&2Chuột Phải&8]&3 ➥&7 Xóa hiệu hiệu ứng của người chơi"
                                )
                                color = Color.fromRGB(224,255,255)
                                shiny()
                                colored()
                            }
                            set('u', XMaterial.POTION) {
                                name = "&bUnluck"
                                lore += listOf(
                                    "",
                                    "&7Hiệu ứng conduit power",
                                    "",
                                    "&8[&2Chuột Trái&8]&3 ➥&7 Áp dụng hiệu ứng cho người chơi",
                                    "&8[&2Chuột Phải&8]&3 ➥&7 Xóa hiệu hiệu ứng của người chơi"
                                )
                                color = Color.fromRGB(224,255,255)
                                shiny()
                                colored()
                            }
                            set('v', XMaterial.POTION) {
                                name = "&bUnluck"
                                lore += listOf(
                                    "",
                                    "&7Hiệu ứng conduit power",
                                    "",
                                    "&8[&2Chuột Trái&8]&3 ➥&7 Áp dụng hiệu ứng cho người chơi",
                                    "&8[&2Chuột Phải&8]&3 ➥&7 Xóa hiệu hiệu ứng của người chơi"
                                )
                                color = Color.fromRGB(224,255,255)
                                shiny()
                                colored()
                            }
                            set('w', XMaterial.POTION) {
                                name = "&bWither"
                                lore += listOf(
                                    "",
                                    "&7Hiệu ứng conduit power",
                                    "",
                                    "&8[&2Chuột Trái&8]&3 ➥&7 Áp dụng hiệu ứng cho người chơi",
                                    "&8[&2Chuột Phải&8]&3 ➥&7 Xóa hiệu hiệu ứng của người chơi"
                                )
                                color = Color.fromRGB(224,255,255)
                                shiny()
                                colored()
                            }
                            onClick(lock = true) { clickEvent ->
                                when (clickEvent.slot) {
                                    '<' -> {
                                        MenuControlPanel.each(player, target)
                                    }
                                    '@' -> {
                                        if (target.activePotionEffects.isNotEmpty()) {
                                            for (PotionEffect in target.activePotionEffects) {
                                                target.removePotionEffect(PotionEffect.type)
                                            }
                                            player.sendLang("Player-Effect-Remove-All", target.name)
                                        } else {
                                            player.sendLang("Player-Effect-Fail-All", target.name)
                                        }
                                    }
                                    '1' -> {
                                        if (clickEvent.clickEvent().isLeftClick) {
                                            player.closeInventory()
                                            player.sendMessage("&7Vui lòng nhập thời gian tồn tại (giây)")
                                        }
                                        if (clickEvent.clickEvent().isRightClick) {
                                            if (target.hasPotionEffect(XPotion.ABSORPTION.parsePotionEffectType()!!)) {
                                                target.removePotionEffect(XPotion.ABSORPTION.parsePotionEffectType()!!)
                                                player.sendLang("Player-Effect-Remove", XPotion.ABSORPTION.name, target.name)
                                            } else {
                                                player.sendLang("Player-Effect-Fail", target.name)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
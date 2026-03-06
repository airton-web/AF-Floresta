package airton4563.floresta.Floresta;

import airton4563.floresta.Database.Account;
import airton4563.floresta.Database.MySQL;
import airton4563.floresta.Database.Skins.*;
import airton4563.floresta.Util;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static airton4563.floresta.Util.cryptItem;
import static airton4563.floresta.Util.encryptItem;

public class FlorestaUtil {

    public static ItemStack getMachado(Player player) throws IOException {
        Account account = new Account(player);
        String code = account.getColumn("last_axe");
        if (code == null) {
            Wood variavel = new Wood(player);
            variavel.create();
            ItemStack item = variavel.getAxe();
            account.setColumn("last_axe", "wood");
            return item;
        } else {
            ItemStack item = null;
            int eficiencia = 0;
            if (code.equalsIgnoreCase("wood")) {
                Wood variavel = new Wood(player);
                item = variavel.getAxe();
                eficiencia = variavel.getEfficiency();
                if (eficiencia > 0) {
                    Util.addEnchant(item, eficiencia);
                    variavel.setColumn("axe", Util.cryptItem(item));
                }
            } else if (code.equalsIgnoreCase("stone")) {
                Stone variavel = new Stone(player);
                item = variavel.getAxe();
                eficiencia = variavel.getEfficiency();
                if (eficiencia > 0) {
                    Util.addEnchant(item, eficiencia);
                    variavel.setColumn("axe", Util.cryptItem(item));
                }
            } else if (code.equalsIgnoreCase("iron")) {
                Iron variavel = new Iron(player);
                item = variavel.getAxe();
                eficiencia = variavel.getEfficiency();
                if (eficiencia > 0) {
                    Util.addEnchant(item, eficiencia);
                    variavel.setColumn("axe", Util.cryptItem(item));
                }
            } else if (code.equalsIgnoreCase("gold")) {
                Gold variavel = new Gold(player);
                item = variavel.getAxe();
                eficiencia = variavel.getEfficiency();
                if (eficiencia > 0) {
                    Util.addEnchant(item, eficiencia);
                    variavel.setColumn("axe", Util.cryptItem(item));
                }
            } else if (code.equalsIgnoreCase("diamond")) {
                Diamond variavel = new Diamond(player);
                item = variavel.getAxe();
                eficiencia = variavel.getEfficiency();
                if (eficiencia > 0) {
                    Util.addEnchant(item, eficiencia);
                    variavel.setColumn("axe", Util.cryptItem(item));
                }
            }
            item.getItemMeta().addItemFlags(ItemFlag.HIDE_ENCHANTS);
            return item;
        }
    }

    public static void updateMachado(Player player, ItemStack item) {
        Account account = new Account(player);
        String lastAxe = account.getLastAxe();
        ItemStack lastAxeItem = null;
        if (lastAxe.contains("WOOD")) {
            Wood variavel = new Wood(player);
            lastAxeItem = variavel.getAxe();
        } else if (lastAxe.contains("STONE")) {
            Stone variavel = new Stone(player);
            lastAxeItem = variavel.getAxe();
        } else if (lastAxe.contains("IRON")) {
            Iron variavel = new Iron(player);
            lastAxeItem = variavel.getAxe();
        } else if (lastAxe.contains("GOLD")) {
            Gold variavel = new Gold(player);
            lastAxeItem = variavel.getAxe();
        } else if (lastAxe.contains("DIAMOND")) {
            Diamond variavel = new Diamond(player);
            lastAxeItem = variavel.getAxe();
        }
        player.getInventory().remove(lastAxeItem);
        if (item.getType() == Material.STONE_AXE) {
            if (lastAxe != null) {
                if (lastAxe.equalsIgnoreCase("stone")) {
                    player.sendMessage("§cVocê já está usando essa skin!");
                    return;
                }
            }
            account.setColumn("last_axe", "stone");
            Stone variavel = new Stone(player);
            ItemStack newAxeItem = variavel.getAxe();
            player.getInventory().addItem(newAxeItem);
            player.sendMessage("§aSkin habilitada: §eMachado de Pedra");
        } else if (item.getType() == Material.IRON_AXE) {
            if (lastAxe != null) {
                if (lastAxe.equalsIgnoreCase("iron")) {
                    player.sendMessage("§cVocê já está usando essa skin!");
                    return;
                }
            }
            account.setColumn("last_axe", "iron");
            Iron variavel = new Iron(player);
            ItemStack newAxeItem = variavel.getAxe();
            player.getInventory().addItem(newAxeItem);
            player.sendMessage("§aSkin habilitada: §eMachado de Ferro");
        } else if (item.getType() == Material.GOLD_AXE) {
            if (lastAxe != null) {
                if (lastAxe.equalsIgnoreCase("gold")) {
                    player.sendMessage("§cVocê já está usando essa skin!");
                    return;
                }
            }
            account.setColumn("last_axe", "gold");
            Gold variavel = new Gold(player);
            ItemStack newAxeItem = variavel.getAxe();
            player.getInventory().addItem(newAxeItem);
            player.sendMessage("§aSkin habilitada: §eMachado de Ouro");
        } else if (item.getType() == Material.DIAMOND_AXE) {
            if (lastAxe != null) {
                if (lastAxe.equalsIgnoreCase("diamond")) {
                    player.sendMessage("§cVocê já está usando essa skin!");
                    return;
                }
            }
            account.setColumn("last_axe", "diamond");
            Diamond variavel = new Diamond(player);
            ItemStack newAxeItem = variavel.getAxe();
            player.getInventory().addItem(newAxeItem);
            player.sendMessage("§aSkin habilitada: §eMachado de Diamante");
        }
    }

    private static final Set<Material> WOODEN_TOOLS = EnumSet.of(
            Material.WOOD_AXE, Material.WOOD_HOE, Material.WOOD_PICKAXE, Material.WOOD_SPADE, Material.WOOD_SWORD
    );
    private static final Set<Material> STONE_TOOLS = EnumSet.of(
            Material.STONE_AXE, Material.STONE_HOE, Material.STONE_PICKAXE, Material.STONE_SPADE, Material.STONE_SWORD
    );
    private static final Set<Material> IRON_TOOLS = EnumSet.of(
            Material.IRON_AXE, Material.IRON_HOE, Material.IRON_PICKAXE, Material.IRON_SPADE, Material.IRON_SWORD
    );
    private static final Set<Material> GOLDEN_TOOLS = EnumSet.of(
            Material.GOLD_AXE, Material.GOLD_HOE, Material.GOLD_PICKAXE, Material.GOLD_SPADE, Material.GOLD_SWORD
    );
    private static final Set<Material> DIAMOND_TOOLS = EnumSet.of(
            Material.DIAMOND_AXE, Material.DIAMOND_HOE, Material.DIAMOND_PICKAXE, Material.DIAMOND_SPADE, Material.DIAMOND_SWORD
    );
    private static final Material OAK_LOG = Material.LOG;
    private static final Material SPRUCE_LOG = Material.LOG;
    private static final Material BIRCH_LOG = Material.LOG;
    private static final Material JUNGLE_LOG = Material.LOG;
    private static final Material ACACIA_LOG = Material.LOG_2;
    public static boolean isCorrectToolForBlock(ItemStack item, Block block) {
        if (item == null || block == null) {
            return false;
        }

        Material itemType = item.getType();
        Material blockType = block.getType();
        byte blockData = block.getData();

        if (itemType == Material.WOOD_AXE) {
            return blockType == OAK_LOG && blockData == 0 || blockData == 4 || blockData == 8;
        } else if (itemType == Material.STONE_AXE) {
            return (blockType == OAK_LOG && blockData == 0 || blockData == 4 || blockData == 8) ||
                    (blockType == SPRUCE_LOG && blockData == 1 || blockData == 5 || blockData == 9);
        }
        else if (IRON_TOOLS.contains(itemType)) {
            return (blockType == OAK_LOG && blockData == 0 || blockData == 4 || blockData == 8) ||
                    (blockType == SPRUCE_LOG && blockData == 1 || blockData == 5 || blockData == 9) ||
                    (blockType == BIRCH_LOG && blockData == 2 || blockData == 6 || blockData == 10);
        }
        else if (GOLDEN_TOOLS.contains(itemType)) {
            return (blockType == OAK_LOG && blockData == 0 || blockData == 4 || blockData == 8) ||
                    (blockType == SPRUCE_LOG && blockData == 1 || blockData == 5 || blockData == 9) ||
                    (blockType == BIRCH_LOG && blockData == 2 || blockData == 6 || blockData == 10) ||
                    (blockType == ACACIA_LOG && blockData == 0 || blockData == 4 || blockData == 8);
        }
        else if (DIAMOND_TOOLS.contains(itemType)) {
            return (blockType == OAK_LOG && blockData == 0 || blockData == 4 || blockData == 8) ||
                    (blockType == SPRUCE_LOG && blockData == 1 || blockData == 5 || blockData == 9) ||
                    (blockType == BIRCH_LOG && blockData == 2 || blockData == 6 || blockData == 10) ||
                    (blockType == ACACIA_LOG && blockData == 0 || blockData == 4 || blockData == 8) ||
                    (blockType == JUNGLE_LOG && blockData == 3 || blockData == 7 || blockData == 11);
        }

        return false;
    }
}
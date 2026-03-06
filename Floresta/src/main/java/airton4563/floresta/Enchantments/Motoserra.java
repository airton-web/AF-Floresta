package airton4563.floresta.Enchantments;

import airton4563.floresta.Database.Account;
import airton4563.floresta.Database.Skins.*;
import airton4563.floresta.Util;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Motoserra {

    public static int price;

    public static ItemStack getEnchantedBook(Player player) {
        Account account = new Account(player);
        String last = account.getLastAxe();
        String status = "false";
        if (last == null) {
            Wood variavel = new Wood(player);
            status = variavel.getMotoserra();
        } else if (last.equalsIgnoreCase("wood")) {
            Wood variavel = new Wood(player);
            status = variavel.getMotoserra();
        } else if (last.equalsIgnoreCase("stone")) {
            Stone variavel = new Stone(player);
            status = variavel.getMotoserra();
        } else if (last.equalsIgnoreCase("iron")) {
            Iron variavel = new Iron(player);
            status = variavel.getMotoserra();
        } else if (last.equalsIgnoreCase("gold")) {
            Gold variavel = new Gold(player);
            status = variavel.getMotoserra();
        } else if (last.equalsIgnoreCase("diamond")) {
            Diamond variavel = new Diamond(player);
            status = variavel.getMotoserra();
        }

        if (status.equalsIgnoreCase("false")) {
            List<String> lore = new ArrayList<>();
            lore.add("§7Serre uma árvore inteira");
            lore.add("§7quando usar este encatamento!");
            lore.add("");
            lore.add("§fCusto: §b" + Util.formatNumber(price) + " Lenhas");
            lore.add("");
            lore.add("§eClique para evoluir.");
            ItemStack item = Util.createItem(Material.ENCHANTED_BOOK, "§aMotoserra", lore);
            item.getItemMeta().getItemFlags().add(ItemFlag.HIDE_ENCHANTS);
            return item;
        } else if (status.equalsIgnoreCase("true")) {
            List<String> lore = new ArrayList<>();
            lore.add("§7Serre uma árvore inteira");
            lore.add("§7quando usar este encatamento!");
            lore.add("");
            lore.add("§fCusto: §b" + Util.formatNumber(price) + " Lenhas");
            lore.add("");
            lore.add("§cJá possui.");
            ItemStack item = Util.createItem(Material.ENCHANTED_BOOK, "§cMotoserra", lore);
            item.getItemMeta().getItemFlags().add(ItemFlag.HIDE_ENCHANTS);
            return item;
        } else {
            return null;
        }
    }

    public static ItemStack getEnchantBook() {
        ItemStack item = null;
        item = Util.createItem(Material.ENCHANTED_BOOK, "§aMotoserra", null);
        item.getItemMeta().addItemFlags(ItemFlag.HIDE_ENCHANTS);
        return item;
    }


}

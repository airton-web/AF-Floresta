package airton4563.floresta.Enchantments;

import airton4563.floresta.Database.Account;
import airton4563.floresta.Database.Skins.*;
import airton4563.floresta.Util;
import airton4563.floresta.Variables.GetYML;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class Derrubador {
    private static Map<Integer, Integer> leveis;

    public static Map<Integer, Integer> getNiveis() {
        return leveis;
    }

    public static int getLenhas(int nivel) {
        return leveis.get(nivel);
    }

    public static int getLevelMax() {
        if (leveis == null || leveis.isEmpty()) {
            return 0;
        }
        return Collections.max(leveis.keySet());
    }

    public static ItemStack getEnchantedBook(Player player) {
        Account account = new Account(player);
        String last_axe = account.getLastAxe();
        int level = 0;
        if (last_axe == null) {
            Wood variavel = new Wood(player);
            variavel.create();
            level = variavel.getDerrubador();
        } else if (last_axe.equalsIgnoreCase("wood")) {
            Wood variavel = new Wood(player);
            variavel.create();
            level = variavel.getDerrubador();
        } else if (last_axe.equalsIgnoreCase("stone")) {
            Stone variavel = new Stone(player);
            variavel.create();
            level = variavel.getDerrubador();
        } else if (last_axe.equalsIgnoreCase("iron")) {
            Iron variavel = new Iron(player);
            variavel.create();
            level = variavel.getDerrubador();
        } else if (last_axe.equalsIgnoreCase("gold")) {
            Gold variavel = new Gold(player);
            variavel.create();
            level = variavel.getDerrubador();
        } else if (last_axe.equalsIgnoreCase("diamond")) {
            Diamond variavel = new Diamond(player);
            variavel.create();
            level = variavel.getDerrubador();
        }
        boolean level0 = false;
        if (level == 0) {
            level0 = true;
            level++;
        }
        List<String> lore = new ArrayList<>();
        lore.add("§7Afete uma área de madeiras");
        lore.add("§7ao ativar este encatamento!");
        lore.add("");
        if (level0) {
            lore.add("§fCusto: §b" + Util.formatNumber(getLenhas(1)) + " Lenhas");
            lore.add("§fNível: §70§8/§7" + getLevelMax());
        } else {
            lore.add("§fCusto: §b" + Util.formatNumber(getLenhas(level++)) + " Lenhas");
            lore.add("§fNível: §7" + level + "§8/§7" + getLevelMax());
        }
        lore.add("");
        lore.add("§eClique para evoluir");
        ItemStack item = Util.createItem(Material.ENCHANTED_BOOK, "§aDerrubador", lore);
        item.getItemMeta().getItemFlags().add(ItemFlag.HIDE_ENCHANTS);
        return item;
    }

    public static void load() {
        FileConfiguration config = GetYML.getConfiguration("encantamentos/Derrubador.yml");
        ConfigurationSection enchantmentsSection = config.getConfigurationSection("Leveis");
        Map<Integer, Integer> niveis = new HashMap<>();
        if (enchantmentsSection != null) {
            for (String levelString : enchantmentsSection.getKeys(false)) {
                int level = Integer.parseInt(levelString);
                int lenhas = GetYML.getInt("Leveis." + levelString, "encantamentos/Derrubador.yml");
                niveis.put(level, lenhas);
            }
        }
        leveis = niveis;
    }
}
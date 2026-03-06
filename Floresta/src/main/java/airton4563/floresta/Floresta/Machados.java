package airton4563.floresta.Floresta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;

@AllArgsConstructor
@Getter
public enum Machados {

    WOOD(Material.WOOD_AXE, "§aMachado de Madeira", "§7Essa skin lhe permite quebrar|§7os seguintes tipos de madeiras:||§b§l⤞ §bMadeira de Carvalho"),
    STONE(Material.STONE_AXE, "§aMachado de Pedra", "§7Essa skin lhe permite quebrar|§7os seguintes tipos de madeiras:||§b§l⤞ §bMadeira de Carvalho|§b§l⤞ §bMadeira de Pinheiro"),
    IRON(Material.IRON_AXE, "§aMachado de Ferro", "§7Essa skin lhe permite quebrar|§7os seguintes tipos de madeiras:||§b§l⤞ §bMadeira de Carvalho|§b§l⤞ §bMadeira de Pinheiro|§b§l⤞ §bMadeira de Eucalipto"),
    GOLD(Material.GOLD_AXE, "§aMachado de Ouro", "§7Essa skin lhe permite quebrar|§7os seguintes tipos de madeiras:||§b§l⤞ §bMadeira de Carvalho|§b§l⤞ §bMadeira de Pinheiro|§b§l⤞ §bMadeira de Eucalipto|§b§l⤞ §bMadeira de Acácia"),
    DIAMOND(Material.DIAMOND_AXE, "§aMachado de Diamante", "§7Essa skin lhe permite quebrar|§7os seguintes tipos de madeiras:||§b§l⤞ §bMadeira de Carvalho|§b§l⤞ §bMadeira de Pinheiro|§b§l⤞ §bMadeira de Eucalipto|§b§l⤞ §bMadeira de Acácia|§b§l⤞ §bMadeira de Selva");

    private Material material;
    private String name;
    private String lore;

    public static Machados[] getAll() {
        return values();
    }
}
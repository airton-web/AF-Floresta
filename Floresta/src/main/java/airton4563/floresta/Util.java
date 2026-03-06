package airton4563.floresta;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.io.*;
import java.util.Base64;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {

    public static boolean addEnchant(ItemStack itemToEnchant, int level) {
        if (itemToEnchant == null || level <= 0) {
            return false;
        }
        itemToEnchant.addUnsafeEnchantment(Enchantment.DIG_SPEED, level);
        return true;
    }
    public static ItemStack createPlayerHead(String playerName, String customName, List<String> lore) {
        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        OfflinePlayer player = Bukkit.getOfflinePlayer(playerName);
        meta.setOwner(player.getName());
        if (customName != null) {
            meta.setDisplayName(customName);
        }
        if (lore != null) {
            meta.setLore(lore);
        }
        head.setItemMeta(meta);
        return head;
    }
    public static ItemStack createItem(Material material, String name, List<String> lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
    public static String hex(String message) {
        if (message == null) {
            return null;
        }
        if (message.isEmpty()) {
            return null;
        }
        Pattern pattern = Pattern.compile("(#[a-fA-F0-9]{6})");
        Matcher matcher = pattern.matcher(message);
        while (matcher.find()) {
            String hexCode = message.substring(matcher.start(), matcher.end());
            String replaceSharp = hexCode.replace('#', 'x');

            char[] ch = replaceSharp.toCharArray();
            StringBuilder builder = new StringBuilder("");
            for (char c : ch) {
                builder.append("&" + c);
            }

            message = message.replace(hexCode, builder.toString());
            matcher = pattern.matcher(message);
        }
        return ChatColor.translateAlternateColorCodes('&', message).replace('&', '§');
    }
    public static String removeLetters(String input) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (!Character.isLetter(c)) {
                sb.append(c);
            }
        }
        return sb.toString();
    }
    public static boolean chance(int chance) {
        if (chance >= 0 && chance <= 100) {
            int randomValue = (int) (Math.random() * 100) + 1;
            if (randomValue <= chance) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    public static void sendActionBar(Player player, String message) {
        CraftPlayer craftPlayer = (CraftPlayer) player;
        IChatBaseComponent chatComponent = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + message + "\"}");
        PacketPlayOutChat packet = new PacketPlayOutChat(chatComponent, (byte) 2);
        craftPlayer.getHandle().playerConnection.sendPacket(packet);
    }
    public static String getTimer(long totalSeconds) {
        int hours = (int) (totalSeconds / 3600);
        int minutes = (int) ((totalSeconds % 3600) / 60);
        int seconds = (int) (totalSeconds % 60);

        StringBuilder timeString = new StringBuilder();
        if (hours > 0) {
            timeString.append(hours).append("h ");
        }
        if (minutes > 0) {
            timeString.append(minutes).append("min ");
        }
        if (seconds > 0 || timeString.length() == 0) {
            timeString.append(seconds).append("s");
        }

        return timeString.toString();
    }
    public static String formatNumber(long number) {
        if (number >= 1_000_000_000) {
            return String.format("%.1fB", number / 1_000_000_000.0);
        } else if (number >= 1_000_000) {
            return String.format("%.1fM", number / 1_000_000.0);
        } else if (number >= 1_000) {
            return String.format("%.1fK", number / 1_000.0);
        } else {
            return String.valueOf(number);
        }
    }
    public static String cryptItem(ItemStack itemStack) throws IOException {
        YamlConfiguration yaml = new YamlConfiguration();
        yaml.set("item", itemStack);
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
        objectStream.writeObject(yaml.saveToString());
        objectStream.flush();
        return Base64.getEncoder().encodeToString(byteStream.toByteArray());
    }
    public static ItemStack encryptItem(String serializedItemStack) throws IOException {
        byte[] data = Base64.getDecoder().decode(serializedItemStack);
        ByteArrayInputStream byteStream = new ByteArrayInputStream(data);
        ObjectInputStream objectStream = new ObjectInputStream(byteStream);
        try {
            String yamlString = (String) objectStream.readObject();
            YamlConfiguration yaml = new YamlConfiguration();
            yaml.loadFromString(yamlString);
            return yaml.getItemStack("item");
        } catch (ClassNotFoundException | IOException | InvalidConfigurationException e) {
            throw new IOException("Falha ao desserializar ItemStack", e);
        }
    }
    public static void removeItem(Player player, ItemStack item) {
        Inventory inv = player.getInventory();
        for (ItemStack itemInv : inv.getContents()) {
            if (itemInv != null && itemInv.isSimilar(item)) {
                itemInv.setAmount(itemInv.getAmount() - 1);
                break;
            }
        }
    }

}

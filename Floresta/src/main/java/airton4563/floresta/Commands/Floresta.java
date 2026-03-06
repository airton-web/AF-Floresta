package airton4563.floresta.Commands;

import airton4563.floresta.Database.Account;
import airton4563.floresta.Enchantments.Motoserra;
import airton4563.floresta.Floresta.FlorestaUtil;
import airton4563.floresta.Main;
import airton4563.floresta.Util;
import airton4563.floresta.Variables.Config;
import airton4563.floresta.Variables.GetYML;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Floresta implements CommandExecutor {

    public static List<Player> inFloresta = new ArrayList<>();
    public static Map<Player, Location> lastLocation = new HashMap<>();
    public static Map<Player, Integer> task = new HashMap<>();
    public static Map<Player, Integer> timer = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String [] args) {

        if (sender instanceof Player) {

            Player player = (Player) sender;

            if (args.length == 1) {
                if (player.hasPermission("floresta.manager")) {
                    if (args[0].equalsIgnoreCase("setspawn")) {
                        if (player.getWorld().getName().equalsIgnoreCase(Config.World)) {
                            GetYML.setString("Spawn.World", player.getLocation().getWorld().getName(), "config.yml");
                            GetYML.setString("Spawn.X", String.valueOf(player.getLocation().getX()), "config.yml");
                            GetYML.setString("Spawn.Y", String.valueOf(player.getLocation().getY()), "config.yml");
                            GetYML.setString("Spawn.Z", String.valueOf(player.getLocation().getZ()), "config.yml");
                            GetYML.setString("Spawn.Yaw", String.valueOf(player.getLocation().getYaw()), "config.yml");
                            GetYML.setString("Spawn.Pitch", String.valueOf(player.getLocation().getPitch()), "config.yml");
                            Config.Spawn = player.getLocation();
                            player.sendMessage("§eSpawn setado com sucesso!");
                            return true;
                        } else {
                            Util.sendActionBar(player, "§cMundo na §econfig.yml§c não é igual ao seu atual.");
                            return true;
                        }
                    } else if (args[0].equalsIgnoreCase("info")) {
                        Account account = new Account(player);
                        account.create();

                        int lenhas = account.getLenhas();
                        int timeSeconds = account.getTime();
                        String time = Util.getTimer(timeSeconds);

                        Inventory menu = Bukkit.getServer().createInventory(null, 27, "Informações de: " + player.getName());
                        List<String> lore = new ArrayList<>();
                        lore.add("§aLenhas: §f" + lenhas);
                        lore.add("§aTempo: §f" + time);
                        menu.setItem(13, Util.createPlayerHead(player.getName(), "§b" + player.getName(), lore));
                        player.openInventory(menu);
                        return true;
                    }
                } else {
                    player.chat("/floresta");
                    return true;
                }
            } else if (args.length == 2) {
                if (args[0].equalsIgnoreCase("info")) {
                    OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                    if (target == null) {
                        player.sendMessage("§cJogador §e" + args[0] + "§c não encontrado.");
                        return true;
                    }

                    Account account = new Account((Player) target);
                    account.create();

                    int lenhas = account.getLenhas();
                    int timeSeconds = account.getTime();
                    String time = Util.getTimer(timeSeconds);

                    Inventory menu = Bukkit.getServer().createInventory(null, 27, "Informações de: " + target.getName());
                    List<String> lore = new ArrayList<>();
                    lore.add("");
                    lore.add("§aLenhas: §f" + lenhas);
                    lore.add("§aTempo: §f" + time);
                    menu.setItem(13, Util.createPlayerHead(target.getName(), "§b" + target.getName(), lore));
                    player.openInventory(menu);
                    return true;
                 }
            }

            if (Config.Spawn == null) {
                player.sendMessage("§cTeleporte indisponível no momento.");
                return true;
            }
            if (!inFloresta.contains(player)) {
                if (dontHasItem(player)) {
                    inFloresta.add(player);
                    lastLocation.put(player, player.getLocation());
                    player.teleport(Config.Spawn);
                    player.sendTitle("§b§lFLORESTA", "§eUse seu §nMachado§e para quebrar as lenhas!");
                    try {
                        player.setItemInHand(FlorestaUtil.getMachado(player));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    task.put(player, Integer.valueOf(Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.plugin, new Runnable() {
                        @Override
                        public void run() {
                            int valueFinal = 0;
                            if (timer.containsKey(player)) {
                                valueFinal = timer.get(player) + 1;
                            } else {
                                valueFinal = 1;
                            }
                            timer.remove(player);
                            timer.put(player, valueFinal);
                        }
                    }, 20L, 20L)));
                } else {
                    player.sendMessage("§cLimpe seu inventário primeiro, depois use o comando novamente!");
                    return true;
                }
            } else {
                player.teleport(lastLocation.get(player));
                player.sendTitle("§b§lFLORESTA", "§cVocê saiu da floresta!");
                lastLocation.remove(player);
                inFloresta.remove(player);
                cancelTask(player);
                player.getInventory().clear();
                if (timer.containsKey(player)) {
                    int value = timer.get(player);
                    timer.remove(player);
                    Account account = new Account(player);
                    account.setColumn("time", value);
                }
            }
            return true;
        } else {
            sender.sendMessage("§cComando indisponível no console.");
            return true;
        }
    }

    public static void cancelTask(Player player) {
        if (task.containsKey(player)) {
            int taskID = task.get(player);
            Bukkit.getServer().getScheduler().cancelTask(taskID);
            task.remove(player);
        }
    }

    public static boolean dontHasItem(Player player) {
        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null) {
                if (item.getType() != Material.AIR) {
                    if (item != Motoserra.getEnchantBook()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

}

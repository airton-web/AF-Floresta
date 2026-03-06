package airton4563.floresta.Commands;

import airton4563.floresta.Database.Account;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class Lenhas implements CommandExecutor {

    public static List<String> TopLenhas;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String [] args) {

        if (sender instanceof Player) {

            Player player = (Player) sender;

            if (args.length == 0) {
                Account account = new Account(player);
                int value = account.getLenhas();
                player.sendMessage("§aVocê tem o total de §b" + value + " lenhas§a!");
                return true;
            } else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("top")) {
                    int amount = TopLenhas.size();
                    int topNumber = 1;
                    player.sendMessage("");
                    player.sendMessage("§b§lTOP 10§a jogadores com mais lenhas:");
                    player.sendMessage("");
                    for (String topLenha : TopLenhas) {
                        if (topLenha != null) {
                            String data = topLenha;
                            String[] info = data.split(";");
                            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(info[0]));
                            if (topNumber == 1) {
                                player.sendMessage(" §b§l" + topNumber + ". §6§l⚘ §e" + offlinePlayer.getName() + " §b§l⤞ §a" + info[1] + " Lenhas");
                            } else {
                                player.sendMessage(" §b§l" + topNumber + ". §e" + offlinePlayer.getName() + " §b§l⤞ §a" + info[1] + " Lenhas");
                            }
                            topNumber++;
                        }
                    }
                    player.sendMessage("");
                    return true;
                }

                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);
                if (offlinePlayer == null) {
                    player.sendMessage("§cJogador §e" + args[0] + "§c não encontrado.");
                    return true;
                }

                Account account = new Account((Player) offlinePlayer);
                int value = account.getLenhas();
                player.sendMessage("§aO jogador §e" + offlinePlayer.getName() + " §apossui §b" + value + " lenhas§a!");
                return true;
            } else if (args.length == 3) {
                String action = args[0];
                OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                int amount = Integer.parseInt(args[2]);

                if (target == null) {
                    player.sendMessage("§cJogador §e" + args[1] + "§c não encontrado.");
                    return true;
                }
                if (amount < 0) {
                    player.sendMessage("§cValor tem que ser maior que §e0§c ou igual.");
                    return true;
                }
                if (!player.hasPermission("floresta.lenhas")) {
                    player.sendMessage("§cVocê não tem permissão para fazer isso.");
                    return true;
                }
                if (action.equalsIgnoreCase("set")) {
                    Account account = new Account((Player) target);
                    account.setLenhas(amount);
                    player.sendMessage("§aLenhas do jogador §b" + target.getName() + "§a setada para §e" + amount + "§a.");
                    return true;
                } else if (action.equalsIgnoreCase("add")) {
                    Account account = new Account((Player) target);
                    int now = account.getLenhas();
                    int finalValue = now + amount;
                    account.setLenhas(finalValue);
                    player.sendMessage("§aAdicionado §e" + amount + "§a lenhas para o jogador §b" + target.getName() + "§a.");
                    return true;
                } else if (action.equalsIgnoreCase("remove")) {
                    Account account = new Account((Player) target);
                    int now = account.getLenhas();
                    int finalValue = now + amount;
                    account.setLenhas(finalValue);
                    player.sendMessage("§cRemovido §e" + amount + "§c lenhas para o jogador §b" + target.getName() + "§c.");
                    return true;
                } else {
                    player.sendMessage("§cOpção não encontrada.");
                    return true;
                }
            }
            return true;
        } else {
            sender.sendMessage("§cComando indisponível no console.");
            return true;
        }
    }

}

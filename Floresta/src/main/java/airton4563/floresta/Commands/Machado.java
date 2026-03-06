package airton4563.floresta.Commands;

import airton4563.floresta.Database.Skins.*;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Locale;

public class Machado implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String [] args) {

        if (sender instanceof Player) {

            Player player = (Player) sender;

            if (args.length == 3) {
                if (args[0].equalsIgnoreCase("addskin")) {
                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[1]);
                    if (offlinePlayer == null) {
                        player.sendMessage("§cJogador §e" + args[1] + "§c não encontrado.");
                        return true;
                    }

                    if (args[2].equalsIgnoreCase("wood") || args[2].equalsIgnoreCase("stone") || args[2].equalsIgnoreCase("iron") || args[2].equalsIgnoreCase("gold") || args[2].equalsIgnoreCase("diamond")) {
                        if (args[2].equalsIgnoreCase("wood")) {
                            Wood varaiavel = new Wood((Player) offlinePlayer);
                            varaiavel.create();
                        } else if (args[2].equalsIgnoreCase("stone")) {
                            Stone variavel = new Stone((Player) offlinePlayer);
                            variavel.create();
                        } else if (args[2].equalsIgnoreCase("iron")) {
                            Iron variavel = new Iron((Player) offlinePlayer);
                            variavel.create();
                        } else if (args[2].equalsIgnoreCase("gold")) {
                            Gold variavel = new Gold((Player) offlinePlayer);
                            variavel.create();
                        } else if (args[2].equalsIgnoreCase("diamond")) {
                            Diamond variavel = new Diamond((Player) offlinePlayer);
                            variavel.create();
                        }

                        player.sendMessage("§aVocê adicionou a skin §e" + args[2].toUpperCase() + "§a ao jogador §b" + offlinePlayer.getName());
                        return true;
                    } else {
                        player.sendMessage("§cSkin §e" + args[2].toUpperCase() + "§c não encotrada.");
                        return true;
                    }
                }
            }

            return true;
        } else {
            sender.sendMessage("§cComando indisponível no console.");
            return true;
        }
    }

}

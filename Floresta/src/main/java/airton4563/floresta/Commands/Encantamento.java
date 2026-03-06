package airton4563.floresta.Commands;

import airton4563.floresta.Enchantments.Motoserra;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Encantamento implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String [] args) {

        if (sender instanceof Player) {

            Player player = (Player) sender;

            if (args.length == 2) {
                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    player.sendMessage("§cJogador §e" + args[0] + "§c não encontrado.");
                    return true;
                }
                if (args[0].equalsIgnoreCase("give")) {
                    ItemStack item = Motoserra.getEnchantBook();
                    target.getInventory().addItem(item);
                    target.sendMessage("§aVocê recebeu o livro de encantamento Motoserra!");
                    player.sendMessage("§aVocê deu o livro de encantamento Motoserra ao jogador §e" + target.getName());
                    return true;
                } else {
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

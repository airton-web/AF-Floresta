package airton4563.floresta.Commands;

import airton4563.floresta.Database.Account;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.ArrayList;
import java.util.List;

public class Listener implements org.bukkit.event.Listener {

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        String command = event.getMessage();
        String[] commandParts = command.split(" ");
        String baseCommand = commandParts[0].toLowerCase();
        if (Floresta.inFloresta.contains(event.getPlayer())) {
            if (baseCommand.equalsIgnoreCase("/spawn")) {
                event.getPlayer().sendTitle("§b§lFLORESTA", "§cVocê saiu da floresta!");
                Floresta.lastLocation.remove(event.getPlayer());
                Floresta.inFloresta.remove(event.getPlayer());
                Floresta.cancelTask(event.getPlayer());
                event.getPlayer().getInventory().clear();
                if (Floresta.timer.containsKey(event.getPlayer())) {
                    int value = Floresta.timer.get(event.getPlayer());
                    Floresta.timer.remove(event.getPlayer());
                    Account account = new Account(event.getPlayer());
                    account.setColumn("time", value);
                }
            } else {
                List<String> commands = new ArrayList<>();
                commands.add("/lenhas");
                commands.add("/floresta");
                if (!commands.contains(baseCommand)) {
                    event.getPlayer().sendMessage("§cComando indisponível dentro da Floresta.");
                    event.setCancelled(true);
                }
            }
        }
    }

}

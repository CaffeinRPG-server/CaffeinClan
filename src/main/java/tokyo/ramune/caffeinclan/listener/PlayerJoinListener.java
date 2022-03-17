package tokyo.ramune.caffeinclan.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import tokyo.ramune.caffeinclan.CaffeinClan;
import tokyo.ramune.caffeinclan.database.SQLDate;
import tokyo.ramune.caffeinclan.database.player.PlayerManager;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        PlayerManager.addPlayer(e.getPlayer());
        try {
            PlayerManager.getPlayerStatus(e.getPlayer()).setStatus(true);
        }catch (Exception ignored) {
        }
        Bukkit.getScheduler().runTaskLater(CaffeinClan.getInstance(), () -> {
            if (!PlayerManager.getPlayerStatus(e.getPlayer()).getUsername().equals(e.getPlayer().getName())) {
                PlayerManager.getPlayerStatus(e.getPlayer()).setUsername(e.getPlayer().getName());
            }
            Bukkit.getScheduler().runTaskLater(CaffeinClan.getInstance(), () -> {
                if (!PlayerManager.getPlayerStatus(e.getPlayer()).getLatestJoinDate().equals(new SQLDate().getDateNow())) {
                    PlayerManager.getPlayerStatus(e.getPlayer()).setLatestJoinDate(new SQLDate());
                }
            }, 10);
        }, 10);
    }
}

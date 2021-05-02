package me.skarless.manhunt.Listeners;

import me.skarless.manhunt.Manhunt;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {

        if (!Manhunt.getInstance().getConfig().getBoolean("DropCompassOnDeath")) {
            for (int i = 0; i <= e.getDrops().size(); i++) {
                try {
                    if (e.getDrops().get(i).getType() == Material.COMPASS) {
                        e.getDrops().remove(i);
                    }
                } catch (Exception ignored) {
                }
            }
        }
    }
}
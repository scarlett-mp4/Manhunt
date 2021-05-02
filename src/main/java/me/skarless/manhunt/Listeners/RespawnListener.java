package me.skarless.manhunt.Listeners;

import me.skarless.manhunt.Manhunt;
import me.skarless.manhunt.Utils.ManHuntUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

public class RespawnListener implements Listener {

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        Player p = e.getPlayer();
        if (Manhunt.getInstance().getConfig().getBoolean("SpawnWithCompass")) {
            if (ManHuntUtils.isHunter(p)) {
                p.getInventory().addItem(new ItemStack(Material.COMPASS));
            }
        }
    }
}

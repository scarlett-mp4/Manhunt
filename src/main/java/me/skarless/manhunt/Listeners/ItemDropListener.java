package me.skarless.manhunt.Listeners;

import me.skarless.manhunt.Utils.ManHuntUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class ItemDropListener implements Listener {

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent e) {
        Player p = e.getPlayer();

        if (ManHuntUtils.isHunter(p)) {
            if (e.getItemDrop().getItemStack().getType() == Material.COMPASS) {
                e.setCancelled(true);
            }
        }
    }
}

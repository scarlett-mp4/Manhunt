package me.skarless.manhunt.Listeners;

import me.skarless.manhunt.Manhunt;
import me.skarless.manhunt.Utils.ManHuntUtils;
import me.skarless.manhunt.Utils.PluginUtil;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.List;

public class MoveListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Manhunt instance = Manhunt.getInstance();
        Player p = e.getPlayer();

        if (ManHuntUtils.isSpeedrunner(p)) {
            List<Block> nearbyBlocks = PluginUtil.getNearbyBlocks(p.getLocation(), 2);

            for (Block b : nearbyBlocks) {
                if (b.getType().equals(Material.NETHER_PORTAL)) {

                    if (p.getLocation().getWorld().getEnvironment() == World.Environment.NORMAL) {
                        instance.overworldPortalMap.put(p.getUniqueId(), p.getLocation());
                    } else if (p.getLocation().getWorld().getEnvironment() == World.Environment.NETHER) {
                        instance.netherPortalMap.put(p.getUniqueId(), p.getLocation());
                    }
                }

                if (b.getType().equals(Material.END_PORTAL)) {
                    if (p.getLocation().getWorld().getEnvironment() == World.Environment.NORMAL) {
                        instance.endMap.put(p.getUniqueId(), p.getLocation());
                    }
                }
            }
        }
    }
}

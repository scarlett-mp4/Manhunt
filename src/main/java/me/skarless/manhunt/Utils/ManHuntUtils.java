package me.skarless.manhunt.Utils;

import me.skarless.manhunt.Manhunt;
import org.bukkit.entity.Player;

public class ManHuntUtils {

    public static boolean isHunter(Player p) {
        Manhunt instance = Manhunt.getInstance();
        return instance.hunterList.contains(p.getUniqueId());
    }

    public static boolean isSpeedrunner(Player p) {
        Manhunt instance = Manhunt.getInstance();
        return instance.speedrunnerList.contains(p.getUniqueId());
    }
}

package me.skarless.manhunt.Commands.ManhuntCommand.SubCommands;

import me.skarless.manhunt.Commands.SubCommand;
import me.skarless.manhunt.Manhunt;
import me.skarless.manhunt.Utils.ManHuntUtils;
import me.skarless.manhunt.Utils.PluginUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.regex.Pattern;

public class AddHunter extends SubCommand {

    @Override
    public String getName() {
        return "addhunter";
    }

    @Override
    public void perform(Player p, String[] args) {
        Manhunt instance = Manhunt.getInstance();
        Pattern playerPattern = Pattern.compile("[PLAYER]", Pattern.LITERAL);

        try {
            Player t = Bukkit.getPlayerExact(args[1]);
            if (!ManHuntUtils.isSpeedrunner(t)) {
                if (!instance.hunterList.contains(p)) {
                    instance.hunterList.add(t.getUniqueId());
                    t.sendMessage(PluginUtil.getString("NowAHunter"));
                    p.sendMessage(playerPattern.matcher(PluginUtil.getString("HunterSet")).replaceAll(t.getName()));
                    if (instance.getConfig().getBoolean("SpawnWithCompass")) {
                        t.getInventory().addItem(new ItemStack(Material.COMPASS));
                    }
                } else {
                    p.sendMessage(playerPattern.matcher(PluginUtil.getString("AlreadyAHunter")).replaceAll(t.getName()));
                }
            } else {
                p.sendMessage(playerPattern.matcher(PluginUtil.getString("AlreadyASpeedrunner")).replaceAll(t.getName()));
            }
        } catch (Exception e) {
            p.sendMessage(PluginUtil.getString("NotOnline"));
        }


    }
}

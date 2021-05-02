package me.skarless.manhunt.Commands.ManhuntCommand.SubCommands;

import me.skarless.manhunt.Commands.SubCommand;
import me.skarless.manhunt.Manhunt;
import me.skarless.manhunt.Utils.ManHuntUtils;
import me.skarless.manhunt.Utils.PluginUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.regex.Pattern;

public class AddSpeedrunner extends SubCommand {

    @Override
    public String getName() {
        return "addspeedrunner";
    }

    @Override
    public void perform(Player p, String[] args) {
        Manhunt instance = Manhunt.getInstance();
        Pattern playerPattern = Pattern.compile("[PLAYER]", Pattern.LITERAL);

        try {
            Player t = Bukkit.getPlayerExact(args[1]);

            if (!ManHuntUtils.isHunter(t)) {
                if (!(instance.speedrunnerList.size() == 1)) {
                    if (!instance.speedrunnerList.contains(p)) {
                        instance.speedrunnerList.add(t.getUniqueId());
                        t.sendMessage(PluginUtil.getString("NowASpeedrunner"));
                        p.sendMessage(playerPattern.matcher(PluginUtil.getString("SpeedrunnerSet")).replaceAll(t.getName()));
                    } else {
                        p.sendMessage(playerPattern.matcher(PluginUtil.getString("AlreadyASpeedrunner")).replaceAll(t.getName()));
                    }
                } else {
                    p.sendMessage(PluginUtil.getString("SpeedrunnerAlreadySet"));
                }
            } else {
                p.sendMessage(playerPattern.matcher(PluginUtil.getString("AlreadyAHunter")).replaceAll(t.getName()));
            }
        } catch (Exception e) {
            p.sendMessage(PluginUtil.getString("NotOnline"));
        }


    }
}

package me.skarless.manhunt.Commands.ManhuntCommand.SubCommands;

import me.skarless.manhunt.Commands.SubCommand;
import me.skarless.manhunt.Manhunt;
import me.skarless.manhunt.Utils.PluginUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.regex.Pattern;

public class RemoveSpeedrunner extends SubCommand {

    @Override
    public String getName() {
        return "removespeedrunner";
    }

    @Override
    public void perform(Player p, String[] args) {
        Manhunt instance = Manhunt.getInstance();
        Pattern playerPattern = Pattern.compile("[PLAYER]", Pattern.LITERAL);

        try {
            Player t = Bukkit.getPlayerExact(args[1]);
            if (instance.speedrunnerList.contains(t.getUniqueId())) {
                instance.speedrunnerList.remove(t.getUniqueId());
                t.sendMessage(PluginUtil.getString("NoLongerASpeedrunner"));
                p.sendMessage(playerPattern.matcher(PluginUtil.getString("SpeedrunnerRemoved")).replaceAll(t.getName()));
            } else {
                p.sendMessage(playerPattern.matcher(PluginUtil.getString("NotASpeedrunner")).replaceAll(t.getName()));
            }
        } catch (Exception e) {
            p.sendMessage(PluginUtil.getString("NotOnline"));
        }


    }
}

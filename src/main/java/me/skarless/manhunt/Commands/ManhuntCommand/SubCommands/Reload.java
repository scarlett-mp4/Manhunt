package me.skarless.manhunt.Commands.ManhuntCommand.SubCommands;

import me.skarless.manhunt.Commands.SubCommand;
import me.skarless.manhunt.Manhunt;
import me.skarless.manhunt.Utils.PluginUtil;
import org.bukkit.entity.Player;

public class Reload extends SubCommand {

    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public void perform(Player p, String[] args) {
        Manhunt instance = Manhunt.getInstance();
        instance.reloadConfig();
        instance.saveConfig();
        instance.languageConfig.reloadConfig();
        instance.languageConfig.saveConfig();
        p.sendMessage(PluginUtil.getString("Reloaded"));
    }
}

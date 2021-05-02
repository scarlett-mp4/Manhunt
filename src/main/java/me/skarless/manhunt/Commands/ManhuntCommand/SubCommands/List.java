package me.skarless.manhunt.Commands.ManhuntCommand.SubCommands;

import me.skarless.manhunt.Commands.SubCommand;
import me.skarless.manhunt.Manhunt;
import me.skarless.manhunt.Utils.PluginUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.regex.Pattern;

public class List extends SubCommand {

    @Override
    public String getName() {
        return "list";
    }

    @Override
    public void perform(Player p, String[] args) {
        Manhunt instance = Manhunt.getInstance();
        Pattern speedrunnerPattern = Pattern.compile("[SPEEDRUNNERS]", Pattern.LITERAL);
        Pattern hunterPattern = Pattern.compile("[HUNTERS]", Pattern.LITERAL);
        StringBuilder hunters = new StringBuilder();
        StringBuilder speedrunners = new StringBuilder();

        if (instance.hunterList.isEmpty()) {
            hunters.append("None");
        } else {
            for (UUID loopPlayer : instance.hunterList) {
                try {
                    hunters.append(Bukkit.getOfflinePlayer(loopPlayer).getName()).append(", ");
                } catch (Exception ex) {
                    hunters.append("Error, ");
                }
            }
            hunters.delete(hunters.length() - 2, hunters.length());
        }

        if (instance.speedrunnerList.isEmpty()) {
            speedrunners.append("None");
        } else {
            for (UUID loopPlayer : instance.speedrunnerList) {
                try {
                    speedrunners.append(Bukkit.getOfflinePlayer(loopPlayer).getName()).append(", ");
                } catch (Exception ex) {
                    speedrunners.append("Error, ");
                }
            }
            speedrunners.delete(speedrunners.length() - 2, speedrunners.length());
        }


        for (String s : PluginUtil.getStringList("List")) {
            s = hunterPattern.matcher(s).replaceAll(hunters.toString());
            s = speedrunnerPattern.matcher(s).replaceAll(speedrunners.toString());

            p.sendMessage(s);
        }
    }
}

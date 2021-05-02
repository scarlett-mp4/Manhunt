package me.skarless.manhunt.Commands.ManhuntCommand;

import me.skarless.manhunt.Commands.ManhuntCommand.SubCommands.*;
import me.skarless.manhunt.Commands.SubCommand;
import me.skarless.manhunt.Utils.PluginUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class ManhuntCommand implements CommandExecutor {

    private final ArrayList<SubCommand> subcommands = new ArrayList<>();

    public ManhuntCommand() {
        subcommands.add(new AddHunter());
        subcommands.add(new RemoveHunter());
        subcommands.add(new AddSpeedrunner());
        subcommands.add(new RemoveSpeedrunner());
        subcommands.add(new List());
        subcommands.add(new Reload());
    }

    public ArrayList<SubCommand> getSubcommands() {
        return subcommands;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;

            if (args.length > 0) {
                int ii = 0;
                for (int i = 0; i < getSubcommands().size(); i++) {
                    if (args[0].equalsIgnoreCase(getSubcommands().get(i).getName())) {
                        getSubcommands().get(i).perform(p, args);
                        return true;
                    }
                    ii++;
                    if (ii == getSubcommands().size()) {
                        for (String s : PluginUtil.getStringList("Help")) {
                            p.sendMessage(s);
                        }
                    }
                }
            } else {
                for (String s : PluginUtil.getStringList("Help")) {
                    p.sendMessage(s);
                }
            }
        }
        return true;
    }
}

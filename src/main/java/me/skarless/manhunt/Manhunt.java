package me.skarless.manhunt;

import me.skarless.manhunt.Commands.ManhuntCommand.ManhuntCommand;
import me.skarless.manhunt.Listeners.DeathListener;
import me.skarless.manhunt.Listeners.ItemDropListener;
import me.skarless.manhunt.Listeners.MoveListener;
import me.skarless.manhunt.Listeners.RespawnListener;
import me.skarless.manhunt.Utils.ConfigGenerator;
import me.skarless.manhunt.Utils.PluginUtil;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public final class Manhunt extends JavaPlugin {
    private static Manhunt manhunt;
    public final HashMap<UUID, Location> netherPortalMap = new HashMap<>();
    public final HashMap<UUID, Location> overworldPortalMap = new HashMap<>();
    public final HashMap<UUID, Location> endMap = new HashMap<>();
    public final ArrayList<UUID> hunterList = new ArrayList<>();
    public final ArrayList<UUID> speedrunnerList = new ArrayList<>();
    public ConfigGenerator languageConfig = new ConfigGenerator("language.yml");

    public static Manhunt getInstance() {
        return manhunt;
    }

    @Override
    public void onEnable() {
        manhunt = this;

        loadConfiguration();
        loadCommands();
        loadListeners();

        runCompassUpdate();
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes
                ('&', "&8[&cManhunt&8] &aHas successfully loaded!"));
    }

    /**
     * Loads the configuration files
     */
    public void loadConfiguration() {
        try {
            if (!this.getDataFolder().exists())
                this.getDataFolder().mkdirs();

            saveDefaultConfig();
            languageConfig.saveDefaultConfig();
            getLogger().info("[Manhunt] Configuration files have successfully loaded!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the commands
     */
    public void loadCommands() {
        try {
            Objects.requireNonNull(getCommand("manhunt")).setExecutor(new ManhuntCommand());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the listeners
     */
    public void loadListeners() {
        try {
            getServer().getPluginManager().registerEvents(new MoveListener(), this);
            getServer().getPluginManager().registerEvents(new DeathListener(), this);
            getServer().getPluginManager().registerEvents(new RespawnListener(), this);
            getServer().getPluginManager().registerEvents(new ItemDropListener(), this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the compass
     */
    public void runCompassUpdate() {
        Bukkit.getScheduler().runTaskTimer(this, () -> {
            try {
                UUID speedrunnerUUID = speedrunnerList.get(0);
                Player speedrunner = Bukkit.getPlayer(speedrunnerUUID);
                World.Environment speedrunnerWorld = speedrunner.getLocation().getWorld().getEnvironment();
                for (UUID hunterUUID : hunterList) {
                    try {
                        Player hunter = Bukkit.getPlayer(hunterUUID);
                        World.Environment hunterWorld = hunter.getLocation().getWorld().getEnvironment();
                        ItemStack compass = hunter.getInventory().getItem(hunter.getInventory().first(Material.COMPASS));
                        CompassMeta compassMeta = (CompassMeta) new ItemStack(Material.COMPASS).getItemMeta();

                        compassMeta.setDisplayName(PluginUtil.getString("TrackerName"));
                        compassMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
                        compassMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                        compass.setItemMeta(compassMeta);

                        if (speedrunnerWorld == hunterWorld) {
                            if (hunterWorld == World.Environment.NORMAL) {
                                hunter.setCompassTarget(speedrunner.getLocation());
                            } else if (hunterWorld == World.Environment.NETHER) {
                                if (Manhunt.getInstance().getConfig().getBoolean("TracksInNether")) {
                                    compassMeta.setLodestoneTracked(false);
                                    compassMeta.setLodestone(speedrunner.getLocation());
                                    compass.setItemMeta(compassMeta);
                                }
                            }
                        } else {
                            if(hunterWorld == World.Environment.NETHER){
                                if (Manhunt.getInstance().getConfig().getBoolean("TracksInNether")) {
                                    compassMeta.setLodestoneTracked(false);
                                    compassMeta.setLodestone(netherPortalMap.get(speedrunnerUUID));
                                    compass.setItemMeta(compassMeta);
                                }
                            }else if (hunterWorld == World.Environment.NORMAL) {
                                if(speedrunnerWorld == World.Environment.NETHER){
                                    hunter.setCompassTarget(overworldPortalMap.get(speedrunner.getUniqueId()));
                                }else if (speedrunnerWorld == World.Environment.THE_END){
                                    hunter.setCompassTarget(endMap.get(speedrunner.getUniqueId()));
                                }
                            }
                        }
                    } catch (Exception ignored) {
                    }
                }
            } catch (Exception ignored) {
            }
        }, 0L, 10L);
    }
}

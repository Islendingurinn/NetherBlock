package me.islend.netherblock;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import sun.security.krb5.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NetherBlock extends JavaPlugin implements Listener {

    private List<Player> inNether = new ArrayList<>();

    @Override
    public void onEnable(){
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new AntiPigmen(), this);

        ConfigManager.setConfig(getConfig());
        ConfigManager.runDefault();
        saveConfig();
    }

    @Override
    public void onDisable(){}

    private Location getSpawn(){
        return Objects.requireNonNull(Bukkit.getServer().getWorld(ConfigManager.getWorld())).getSpawnLocation();
    }

    private void loop(){
        new BukkitRunnable(){
            @Override
            public void run(){
                if(inNether.isEmpty()) cancel();

                for(Player checked : inNether)
                    if(checked.getLocation().getY() >= 128)
                        tpToSpawn(checked);
            }
        }.runTaskTimer(this, 20, 300);
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent login){
        new BukkitRunnable(){
            @Override
            public void run(){
                if(login.getPlayer().getWorld().getName().endsWith(ConfigManager.getNetherEnding())){
                    inNether.add(login.getPlayer());
                    loop();
                }
            }
        }.runTaskLater(this, 20);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent quit){
        inNether.remove(quit.getPlayer());
    }

    @EventHandler
    public void onPlayerChangeWorld(PlayerChangedWorldEvent changed) {
        if (changed.getPlayer().getWorld().getName().endsWith(ConfigManager.getNetherEnding())) {
            inNether.add(changed.getPlayer());
            loop();
        } else if (changed.getFrom().getName().endsWith(ConfigManager.getNetherEnding()))
            inNether.remove(changed.getPlayer());
    }

    private void tpToSpawn(Player tp) {
        tp.teleport(getSpawn());
        tp.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4&lGoing on top of nether is not allowed."));

    }

}

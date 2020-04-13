package me.islend.netherblock;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalEvent;

import java.util.Random;

public class AntiPigmen implements Listener {

    @EventHandler
    public void onEntityEnter(EntityPortalEvent event){

        if(event.getEntityType().equals(EntityType.PIG_ZOMBIE) && event.getEntity().getWorld().getName().endsWith(ConfigManager.getNetherEnding())){
            boolean probability = new Random().nextInt(100)+1 <= ConfigManager.getPigmenProbability();
            event.setCancelled(probability);
        }

    }

}

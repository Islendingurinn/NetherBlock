package me.islend.netherblock;

import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {

    private static FileConfiguration config;

    public static void setConfig(FileConfiguration cfg){
        config = cfg;
    }

    public static void runDefault(){
        config.addDefault("netherblock.world", "world");
        config.addDefault("netherblock.netherendswith", "_nether");
        config.addDefault("antipigmen.probability", "33");
        config.options().copyDefaults(true);

    }

    public static String getWorld(){
        return config.getString("netherblock.world");
    }

    public static String getNetherEnding(){
        return config.getString("netherblock.netherendswith");
    }

    public static int getPigmenProbability(){
        return config.getInt("antipigmen.probability");
    }

}

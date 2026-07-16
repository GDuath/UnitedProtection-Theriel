package org.unitedlands.protection.utils;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public record Config(
    int protectedY,
    String breakDenyMessage,
    String placeDenyMessage,
    List<String> protectedWorlds,
    List<String> whitelistedBlocks
) {

    private static Config instance;
    public static Config getConfig() {
        return instance;
    }

    public static void load(FileConfiguration config) {
        instance = new Config(
            config.getInt("protect-anything-below"),
            config.getString("break-deny-message"),
            config.getString("place-deny-message"),
            config.getStringList("protected-worlds"),
            config.getStringList("block-whitelist")
        );
    }

}

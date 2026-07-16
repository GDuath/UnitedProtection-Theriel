package org.unitedlands.protection;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.popcraft.bolt.BoltAPI;
import org.popcraft.bolt.event.LockBlockEvent;
import org.popcraft.bolt.event.LockEntityEvent;
import org.unitedlands.protection.listeners.BlockListener;
import org.unitedlands.protection.listeners.TownyListener;
import org.unitedlands.protection.utils.Config;
import org.unitedlands.protection.utils.Utils;

public final class UnitedProtection extends JavaPlugin {

    private static UnitedProtection instance;
    public static UnitedProtection getPlugin() {
        return instance;
    }

    private static BoltAPI boltAPI;
    public static BoltAPI getBoltAPI() {
        return boltAPI;
    }

    @Override
    public void onEnable() {
        instance = this;
        boltAPI = getServer().getServicesManager().load(BoltAPI.class);

        saveDefaultConfig();
        Config.load(getConfig());

        registerListeners(getServer().getPluginManager());
    }

    private void registerListeners(PluginManager manager) {
        manager.registerEvents(new BlockListener(), this);

        if (boltAPI == null)
            return;

        manager.registerEvents(new TownyListener(), this);

        boltAPI.registerListener(LockBlockEvent.class, event -> {
            if (!Utils.shouldBeLocked(event.getPlayer(), event.getBlock().getLocation()))
                event.setCancelled(true);
        });

        boltAPI.registerListener(LockEntityEvent.class, event -> {
            if (!Utils.shouldBeLocked(event.getPlayer(), event.getEntity().getLocation()))
                event.setCancelled(true);
        });
    }

}

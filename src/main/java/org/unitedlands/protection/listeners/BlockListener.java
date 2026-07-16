package org.unitedlands.protection.listeners;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.unitedlands.protection.utils.Config;
import org.unitedlands.protection.utils.Utils;

@SuppressWarnings("deprecation")
public class BlockListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreak(BlockBreakEvent event) {
        var player = event.getPlayer();
        if (player.hasPermission("united.protection.bypass"))
            return;

        var block = event.getBlock();
        if (!Utils.isProtectedBlock(block))
            return;

        event.setCancelled(true);
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.getConfig().breakDenyMessage()));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockPlace(BlockPlaceEvent event) {
        var player = event.getPlayer();
        if (player.hasPermission("united.protection.bypass"))
            return;

        var block = event.getBlock();
        if (!Utils.isProtectedBlock(block))
            return;

        event.setCancelled(true);
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.getConfig().placeDenyMessage()));
    }

}

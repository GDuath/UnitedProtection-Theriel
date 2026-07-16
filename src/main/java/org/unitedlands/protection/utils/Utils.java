package org.unitedlands.protection.utils;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.TownySettings;
import com.palmergames.bukkit.towny.object.WorldCoord;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.popcraft.bolt.protection.Protection;
import org.unitedlands.protection.UnitedProtection;

@SuppressWarnings({"RedundantIfStatement", "BooleanMethodIsAlwaysInverted", "DataFlowIssue", "RedundantIfStatement", "BooleanMethodIsAlwaysInverted"})
public class Utils {

    public static boolean isWhitelistedBlock(Block block) {
        var blockName = block.getType().toString();

        return Config.getConfig().whitelistedBlocks().stream()
                .anyMatch(whitelistedBlock -> blockName.toLowerCase().contains(whitelistedBlock.toLowerCase()));
    }

    public static boolean isProtectedWorld(World world) {
        return Config.getConfig().protectedWorlds().contains(world.getName());
    }

    public static boolean isProtectedBlock(Block block) {
        if (!isProtectedWorld(block.getWorld())) return false;
        if (Utils.isWhitelistedBlock(block)) return false;

        var blockY = block.getLocation().getBlockY();
        if (blockY < Config.getConfig().protectedY() && TownyAPI.getInstance().isWilderness(block.getLocation()))
            return true;

        return false;
    }

    public static void removeProtections(WorldCoord worldCoord) {
        if (worldCoord == null)
            return;

        var world = worldCoord.getBukkitWorld();
        if (world == null)
            return;

        var townBlockHeight = world.getMaxHeight() - 1;
        var townBlockSize = TownySettings.getTownBlockSize();
        var bolt = UnitedProtection.getBoltAPI();

        for (int x = 0; x < townBlockSize; ++x) {
            for (int z = 0; z < townBlockSize; ++z) {
                for (int y = townBlockHeight; y > world.getMinHeight(); --y) {
                    var blockX = worldCoord.getX() * townBlockSize + x;
                    var blockZ = worldCoord.getZ() * townBlockSize + z;
                    var block = world.getBlockAt(blockX, y, blockZ);

                    if (!bolt.isProtectable(block))
                        continue;

                    var protection = bolt.findProtection(block);
                    if (protection != null) {
                        bolt.removeProtection(protection);
                    }
                }
            }
        }
    }

    public static boolean shouldBeLocked(Player player, Location position) {
        if (player.isOp() || player.hasPermission("towny.admin"))
            return true;

        if (!TownyAPI.getInstance().isTownyWorld(position.getWorld()))
            return true;

        if (TownyAPI.getInstance().isWilderness(position))
            return false;

        var resident = TownyAPI.getInstance().getResident(player);
        var town = TownyAPI.getInstance().getTownBlock(position).getTownOrNull();
        if (resident == null || town == null)
            return false;

        if (town.hasResident(resident) || town.hasTrustedResident(resident))
            return true;

        return false;
    }

}

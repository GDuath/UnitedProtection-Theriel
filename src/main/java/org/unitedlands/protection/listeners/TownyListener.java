package org.unitedlands.protection.listeners;

import com.palmergames.bukkit.towny.event.PlotClearEvent;
import com.palmergames.bukkit.towny.event.town.TownRuinedEvent;
import com.palmergames.bukkit.towny.event.town.TownUnclaimEvent;
import com.palmergames.bukkit.towny.object.TownBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.unitedlands.protection.utils.Utils;

public class TownyListener implements Listener {

    @EventHandler
    public void onTownUnclaim(final TownUnclaimEvent event) {
        Utils.removeProtections(event.getWorldCoord());
    }

    @EventHandler
    public void onPlotClear(final PlotClearEvent event) {
        final TownBlock townBlock = event.getTownBlock();
        if (townBlock == null)
            return;

        Utils.removeProtections(townBlock.getWorldCoord());
    }

    @EventHandler
    public void onTownRuin(final TownRuinedEvent event) {
        for (final TownBlock townBlock : event.getTown().getTownBlocks()) {
            Utils.removeProtections(townBlock.getWorldCoord());
        }
    }

}

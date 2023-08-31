/*
 * XLTournaments Plugin
 * Copyright (c) 2020 - 2022 Lewis D (ItsLewizzz). All rights reserved.
 */

package fun.lewisdev.tournaments.action.actions;

import fun.lewisdev.tournaments.XLTournamentsPlugin;
import fun.lewisdev.tournaments.action.Action;
import fun.lewisdev.tournaments.utility.universal.XSound;
import org.bukkit.entity.Player;

public class SoundAction implements Action {

    @Override
    public String getIdentifier() {
        return "SOUND";
    }

    @Override
    public void execute(XLTournamentsPlugin plugin, Player player, String data) {
        try {
            player.playSound(player.getLocation(), XSound.matchXSound(data).get().parseSound(), 1L, 1L);
        } catch (Exception ex) {
            plugin.getLogger().warning("Invalid sound name in action: " + data.toUpperCase());
        }
    }
}

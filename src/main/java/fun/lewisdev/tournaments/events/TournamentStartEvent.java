/*
 * XLTournaments Plugin
 * Copyright (c) 2020 - 2022 Lewis D (ItsLewizzz). All rights reserved.
 */

package fun.lewisdev.tournaments.events;

import fun.lewisdev.tournaments.tournament.Tournament;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TournamentStartEvent extends Event {

    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private boolean cancelled = false;

    private Tournament tournament;

    public TournamentStartEvent(Tournament tournament) {
        this.tournament = tournament;
    }

    public Tournament getTournament() {
        return tournament;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }
}

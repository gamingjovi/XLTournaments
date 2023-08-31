/*
 * XLTournaments Plugin
 * Copyright (c) 2020 - 2022 Lewis D (ItsLewizzz). All rights reserved.
 */

package fun.lewisdev.tournaments.action;

import fun.lewisdev.tournaments.XLTournamentsPlugin;
import fun.lewisdev.tournaments.action.actions.*;
import fun.lewisdev.tournaments.tournament.Tournament;
import fun.lewisdev.tournaments.utility.TextUtil;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActionManager {

    private final XLTournamentsPlugin plugin;
    private Map<String, Action> actions;

    public ActionManager(XLTournamentsPlugin plugin) {
        this.plugin = plugin;
    }

    public void onEnable() {
        actions = new HashMap<>();

        registerAction(
                new MessageAction(),
                new BroadcastMessageAction(),
                new CommandAction(),
                new ConsoleCommandAction(),
                new SoundAction()
        );
    }

    public void registerAction(Action... actions) {
        Arrays.asList(actions).forEach(action -> this.actions.put(action.getIdentifier(), action));
    }

    public void executeActions(Player player, List<String> items) {
        executeActions(player, items, null);
    }

    public void executeActions(Player player, List<String> items, Tournament tournament) {
        items.forEach(item -> {

                String actionName = StringUtils.substringBetween(item, "[", "]");
                if(actionName != null) {
                    actionName = actionName.toUpperCase();
                    Action action = actionName.isEmpty() ? null : actions.get(actionName);

                    if (action != null) {
                        item = item.contains(" ") ? item.split(" ", 2)[1] : "";
                        if (player != null) {
                            item = item.replace("{PLAYER}", player.getName());

                            if(tournament != null) {
                                item = TextUtil.setPlaceholders(item, player.getUniqueId(), tournament);
                            }
                        }

                        action.execute(plugin, player, item);
                    }
                }
        });
    }
}

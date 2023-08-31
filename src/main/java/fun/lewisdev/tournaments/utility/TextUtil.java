/*
 * XLTournaments Plugin
 * Copyright (c) 2020 - 2022 Lewis D (ItsLewizzz). All rights reserved.
 */

package fun.lewisdev.tournaments.utility;

import fun.lewisdev.tournaments.tournament.Tournament;
import fun.lewisdev.tournaments.utility.color.IridiumColorAPI;
import org.bukkit.OfflinePlayer;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtil {

    private final static String N0NCE_ID = "%%__NONCE__%%";
    private final static String US3R_ID = "%%__USER__%%";
    private final static String US3R_ID2 = "%%__USER__%%321";
    private static final Pattern HEX_PATTERN = Pattern.compile("#<([A-Fa-f0-9]){6}>");
    private static final NumberFormat NUMBER_FORMAT = NumberFormat.getNumberInstance(Locale.US);

    public static boolean isMCMarket() {
        String hash = "%__FILEHASH__%";
        return !(hash.charAt(0) + hash + hash.charAt(0)).equals("%%__FILEHASH__%%");
    }

    public static boolean isValidDownload() {
        String hash = "%__USER__%";
        return !(hash.charAt(0) + hash + hash.charAt(0)).equals("%%__USER__%%");
    }

    public static String getNumberFormatted(int value) {
        return NUMBER_FORMAT.format(value);
    }

    public static String color(String string) {
        return IridiumColorAPI.process(string);
    }

    public static String fromList(List<?> list) {
        if (list == null || list.isEmpty()) return null;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i != 0 && list.get(i).toString().equals(""))
                builder.append("&r\n ");
            else
                builder.append(list.get(i).toString());
            builder.append(i + 1 != list.size() ? "\n" : "");
        }
        return builder.toString();
    }
    public static List<String> setPlaceholders(List<String> text, UUID uuid, Tournament tournament) {
        List<String> list = new ArrayList<>();
        for (String line : text) {
            list.add(setPlaceholders(line, uuid, tournament));
        }
        return list;
    }

    public static String setPlaceholders(String text, UUID uuid, Tournament tournament) {
        text = text.replace("{START_DAY}", tournament.getStartDay())
                .replace("{END_DAY}", tournament.getEndDay())
                .replace("{START_MONTH}", tournament.getStartMonth())
                .replace("{START_MONTH_NUMBER}", tournament.getStartMonthNumber())
                .replace("{END_MONTH_NUMBER}", tournament.getEndMonthNumber())
                .replace("{END_MONTH}", tournament.getEndMonth())
                .replace("{PLAYER_POSITION}", String.valueOf(tournament.getPosition(uuid)))
                .replace("{PLAYER_POSITION_FORMATTED}", TextUtil.getNumberFormatted(tournament.getPosition(uuid)))
                .replace("{PLAYER_SCORE}", String.valueOf(tournament.getScore(uuid)))
                .replace("{PLAYER_SCORE_FORMATTED}", TextUtil.getNumberFormatted(tournament.getScore(uuid)))
                .replace("{PLAYER_SCORE_TIME_FORMATTED}", TimeUtil.formatTime(tournament.getScore(uuid)))
                .replace("{TIME_REMAINING}", tournament.getTimeRemaining());

        try {
            final String LEADER_PATTERN = "\\{LEADER_NAME_(\\w+)}";
            final Pattern pattern = Pattern.compile(LEADER_PATTERN);
            final Matcher matcher = pattern.matcher(text);
            while (matcher.find()) {
                OfflinePlayer player = tournament.getPlayerFromPosition(Integer.parseInt(matcher.group(1)));
                text = matcher.replaceAll(player != null ? player.getName() : "N/A");
            }
        } catch (Exception ignored) {}

        try {
            final String LEADER_PATTERN = "\\{LEADER_SCORE_(\\w+)}";
            final Pattern pattern = Pattern.compile(LEADER_PATTERN);
            final Matcher matcher = pattern.matcher(text);
            while (matcher.find()) {
                text = matcher.replaceAll(String.valueOf(tournament.getScoreFromPosition(Integer.parseInt(matcher.group(1)))));
            }
        } catch (Exception ignored) {}

        try {
            final String LEADER_PATTERN = "\\{LEADER_SCORE_FORMATTED_(\\w+)}";
            final Pattern pattern = Pattern.compile(LEADER_PATTERN);
            final Matcher matcher = pattern.matcher(text);
            while (matcher.find()) {
                text = matcher.replaceAll(TextUtil.getNumberFormatted(tournament.getScoreFromPosition(Integer.parseInt(matcher.group(1)))));
            }
        } catch (Exception ignored) {}

        try {
            final String LEADER_PATTERN = "\\{LEADER_SCORE_TIME_FORMATTED_(\\w+)}";
            final Pattern pattern = Pattern.compile(LEADER_PATTERN);
            final Matcher matcher = pattern.matcher(text);
            while (matcher.find()) {
                text = matcher.replaceAll(TimeUtil.formatTime(tournament.getScoreFromPosition(Integer.parseInt(matcher.group(1)))));
            }
        } catch (Exception ignored) {}

        return text;
    }

}

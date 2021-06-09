package me.PauMAVA.TTR.lang;

import me.PauMAVA.TTR.TTRCore;

public enum PluginString {

    ALLY_CAGE_ENTER_OUTPUT("gameplay.on_ally_cage_enter"),
    DISABLED_ON_STARTUP_NOTICE("other.disabled_on_startup"),
    SCORE_OUTPUT("gameplay.on_score"),
    WIN_OUTPUT("gameplay.on_win"),
    ERROR_EXPECTED_INTEGER("commands.error_expected_integer"),
    ON_PLAYER_JOIN_OUTPUT("events.on_player_join"),
    ON_PLAYER_LEAVE_OUTPUT("events.on_player_leave"),
    ON_PLACE_BLOCK_ERROR("events.on_place_block_error"),
    ON_BREAK_BLOCK_ERROR("events.on_break_block_error"),
    TOTAL_TIME_LABEL("other.total_time_label"),
    TRANSLATION_MADE_BY("other.translation_made_by"),
    TTR_ENABLE_OUTPUT("commands.on_plugin_enable"),
    TTR_DISABLE_OUTPUT("commands.on_plugin_disable");

    private final String path;

    PluginString(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return TTRCore.getInstance().getLanguageManager().getString(this);
    }
}

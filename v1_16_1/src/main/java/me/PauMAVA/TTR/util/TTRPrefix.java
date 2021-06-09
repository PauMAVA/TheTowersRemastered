/*
 * TheTowersRemastered (TTR)
 * Copyright (c) 2019-2021  Pau Machetti Vallverdú
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package me.PauMAVA.TTR.util;

import me.PauMAVA.TTR.TTRCore;

public enum TTRPrefix {

    TTR_GAME("chat.game_prefix"),
    TTR_GAME_DARK("chat.game_prefix_dark"),
    TTR_GLOBAL("chat.global_prefix"),
    TTR_TEAM("chat.team_prefix");

    private final String path;

    TTRPrefix(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return TTRCore.getInstance().getLanguageManager().getStringByPath(this.path);
    }

}

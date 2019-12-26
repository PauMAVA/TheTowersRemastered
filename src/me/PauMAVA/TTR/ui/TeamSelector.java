package me.PauMAVA.TTR.ui;

import org.bukkit.entity.Player;

public class TeamSelector extends CustomUI {

    private Player owner;

    public TeamSelector(Player player) {
        super(27, "Team Selection");
        this.owner = player;
    }

    public void openSelector() {
        super.openUI(this.owner);
    }

    public void closeSelector() {
        super.closeUI(this.owner);
    }

}

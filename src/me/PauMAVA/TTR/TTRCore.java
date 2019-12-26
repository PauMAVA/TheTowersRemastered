package me.PauMAVA.TTR;

import me.PauMAVA.TTR.match.TTRMatch;
import me.PauMAVA.TTR.util.EventListener;
import org.bukkit.plugin.java.JavaPlugin;

public class TTRCore extends JavaPlugin {

    private static TTRCore instance;
    private boolean enabled = false;
    private TTRMatch match;

    @Override
    public void onEnable() {
        instance = this;
        if(this.getConfig().getBoolean("enableOnStart")) {
            enabled = true;
        }
        this.match = new TTRMatch();
        this.getServer().getPluginManager().registerEvents(new EventListener(), this);
    }

    @Override
    public void onDisable() {

    }

    public static TTRCore getInstance() {
        return instance;
    }

    public boolean enabled() {
        return this.enabled;
    }

    public TTRMatch getCurrentMatch() {
        return this.match;
    }
}

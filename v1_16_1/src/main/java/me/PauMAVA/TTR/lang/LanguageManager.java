package me.PauMAVA.TTR.lang;

import org.bukkit.configuration.file.FileConfiguration;
import me.PauMAVA.TTR.TTRCore;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.HashMap;

public class LanguageManager {

    private final TTRCore plugin;
    private Locale selectedLocale;
    private FileConfiguration languageFile;

    public LanguageManager(TTRCore plugin) {
        this.plugin = plugin;
        setUpLocales();
        extractLanguageFiles();
        String shortName = plugin.getConfigManager().getLocale();
        if (!setLocale(LocaleRegistry.getLocaleByShortName(shortName))) {
            plugin.getLogger().warning("Couldn't load lang " + shortName + "!");
            plugin.getLogger().warning("Loading default language lang_en...");
            if (!setLocale(LocaleRegistry.getLocaleByShortName("en"))) {
                plugin.getLogger().severe("Failed to load default language! Plugin won't work properly!");
            } else {
                plugin.getLogger().info("Successfully loaded lang_en.yml!");
            }
        } else {
            plugin.getLogger().info("Successfully loaded '" + shortName + "' locale!");
        }
    }

    private void setUpLocales() {
        LocaleRegistry.registerLocale(new Locale("ENGLISH", "en", "PauMAVA"));
    }

    private void extractLanguageFiles() {
        HashMap<File, InputStream> streams = new HashMap<>();
        for (Locale locale: LocaleRegistry.getLocales()) {
            streams.put(
                    new File(plugin.getDataFolder().getPath() + "/lang_" + locale.getShortName() + ".yml"),
                    LanguageManager.class.getResourceAsStream("/lang_" + locale.getShortName() + ".yml")
                    );
        }
        for (File destination: streams.keySet()) {
            try {
                if (!destination.exists()) {
                    destination.createNewFile();
                    InputStream in = streams.get(destination);
                    byte[] buffer = new byte[in.available()];
                    in.read(buffer);
                    OutputStream out = new FileOutputStream(destination);
                    out.write(buffer);
                    in.close();
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean setLocale(Locale locale) {
        File targetFile = new File(plugin.getDataFolder().toString() + "/lang_" + locale.getShortName() + ".yml");
        if (targetFile.exists()) {
            this.selectedLocale = locale;
            this.languageFile = YamlConfiguration.loadConfiguration(targetFile);
            return true;
        }
        return false;
    }

    public Locale getSelectedLocale() {
        return selectedLocale;
    }

    public String getString(PluginString string) {
        if (this.languageFile.isSet(string.getPath())) {
            String unprocessed = this.languageFile.getString(string.getPath());
            if (unprocessed == null) {
                return "";
            }
            return unprocessed.replace("&", "§");
        }
        return "";
    }

}

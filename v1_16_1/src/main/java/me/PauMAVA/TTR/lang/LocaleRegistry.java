package me.PauMAVA.TTR.lang;

import java.util.ArrayList;
import java.util.List;

public class LocaleRegistry {

    private static List<Locale> locales = new ArrayList<>();

    public static void registerLocale(Locale locale) {
        if (!locales.contains(locale)) {
            locales.add(locale);
        }
    }

    public static void unregisterLocale(Locale locale) {
        locales.remove(locale);
    }

    public static List<Locale> getLocales() {
        return locales;
    }

    public static Locale getLocaleByLongName(String longName) {
        for (Locale locale: locales) {
            if (locale.getLanguageName().equalsIgnoreCase(longName)) {
                return locale;
            }
        }
        return new Locale("Unknown", "Unknown", "Unknown");
    }

    public static Locale getLocaleByShortName(String shortName) {
        for (Locale locale: locales) {
            if (locale.getShortName().equalsIgnoreCase(shortName)) {
                return locale;
            }
        }
        return new Locale("Unknown", "Unknown", "Unknown");
    }

}

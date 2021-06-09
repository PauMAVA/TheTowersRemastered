package me.PauMAVA.TTR.lang;

public class Locale {

    private final String languageName;
    private final String shortName;
    private final String author;

    public Locale(String languageName, String shortName, String author) {
        this.languageName = languageName;
        this.shortName = shortName;
        this.author = author;
    }

    public String getLanguageName() {
        return languageName;
    }

    public String getShortName() {
        return shortName;
    }

    public String getAuthor() {
        return author;
    }

}

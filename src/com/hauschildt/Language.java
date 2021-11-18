package com.hauschildt;

import java.util.Locale;
import java.util.ResourceBundle;

public class Language {
    public enum Option {US, FRANCE};
    private ResourceBundle messages;
    private Locale locale;

    public Language() {
        locale = Locale.getDefault();
        messages = ResourceBundle.getBundle("messages", locale);
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Option language) {
        Locale result;
        switch (language) {
            case US -> locale = Locale.US;
            case FRANCE -> locale = Locale.FRANCE;
            default -> locale = Locale.getDefault();
        }
        messages = ResourceBundle.getBundle("messages", locale);
    }

    public ResourceBundle getMessages() {
        return messages;
    }
}

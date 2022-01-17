package com.hauschildt;

import java.util.*;

public class Language {
    public enum Option {
        US("US English"), FRANCE("French");

        private String language;

        Option(String language) {
            this.language = language;
        }

        public String getLanguage() {
            return language;
        }
    };
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

    public void changeLanguage(Scanner in, ResourceBundle messages) {
        String menuTitle = "Languages Available";
        String prompt = "Select a language";
        List<String> languages = new ArrayList<>();
        for (Option option : Option.values()) {
            languages.add(option.getLanguage());
        }
        String[] menuOptions = languages.toArray(new String[0]);
        int choice = 0;
        while (true) {
            choice = UIUtility.showMenuOptions(menuTitle, prompt, menuOptions, in, messages);
            if (choice == 0) {
                continue;
            } else if (choice == 1) {
                this.messages = ResourceBundle.getBundle("messages", Locale.US);
                System.out.println("\nLanguage changed.");
                break;
            } else if (choice == 2) {
                this.messages = ResourceBundle.getBundle("messages", Locale.FRANCE);
                System.out.println("\nLanguage changed.");
                break;
            } else if (choice == menuOptions.length + 1) {
                System.out.println("\nLanguage change canceled.");
                break;
            }
        }
    }

}

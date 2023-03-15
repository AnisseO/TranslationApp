package com.example.translationapp;

// https://en.wikipedia.org/wiki/Regional_indicator_symbol

public class Language {

    private String lang;
    private String name;

    public Language(String lang, String name)
    {
        this.lang = lang;
        this.name = name;
    }

    public String getLanguage()
    {
        return lang;
    }

    public String getName()
    {
        return name;
    }

    @Override
    public String toString(){

        String countryCode = this.lang;

        // Changement des code pays ne correspondant pas
        switch (countryCode){
            case "EN":
                countryCode = "GB";
                break;
            case "JA":
                countryCode = "JP";
                break;
            case "KO":
                countryCode = "KP";
                break;
            case "DA":
                countryCode = "DK";
                break;
            case "EL":
                countryCode = "GR";
                break;
            case "CS":
                countryCode = "CZ";
                break;
            case "ZH":
                countryCode = "CN";
                break;
            case "UK":
                countryCode = "UA";
                break;
            case "NB":
                countryCode = "NO";
                break;
            case "ET":
                countryCode = "EE";
                break;
            case "SL":
                countryCode = "SI";
                break;
            case "SV":
                countryCode = "SE";
                break;
            default:
                break;
        }

        //Conversion en emojis drapeau correspondants en utilisant les points de code Unicode.
        int firstLetter = Character.codePointAt(countryCode, 0) - 0x41 + 0x1F1E6;
        int secondLetter = Character.codePointAt(countryCode, 1) - 0x41 + 0x1F1E6;

        String drapeau = new String(Character.toChars(firstLetter)) + new String(Character.toChars(secondLetter));
        return  (name + " " + drapeau);
    }

}

package com.example.translationapp;

public class Traductions {
    private  String textToTrad;
    private  String textTranslate;

    public Traductions(String textToTrad, String textTranslate){
        this.textToTrad = textToTrad;
        this.textTranslate = textTranslate;
    }

    public String getTextToTrad(){
        return  textToTrad;
    }

    public  String getTextTranslate(){
        return textTranslate;
    }

    public String toString(){
        return textTranslate;
    }
}

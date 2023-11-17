package com.example.mydictionary.search;

public class Word {
    private  String word;
    private  String explain;
    public Word(){
        this.word = "";
        this.explain = "";
    }

    /**
     * contructor 1 tu moi.
     * @param word  english word
     * @param explain nghia cua word.
     */

    public Word(String word, String explain) {
        this.word = word;
        this.explain = explain;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }
}

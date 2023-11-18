package com.example.mydictionary.basic;

public class Word {
    /**
     * word_target: english word
     * word_explain: vietnamese explain
     */
    private String target;
    private String explain;


    public Word() {
        this.explain = "";
        this.target = "";
    }

    /**
     * constructor
     *
     * @param word_target  is english
     * @param word_explain is vietnamese
     */
    public Word(String word_target, String word_explain) {
        this.target = word_target;
        this.explain = word_explain;
    }

    public boolean check_start(String s) {
        if (s.length() > this.target.length()) return false;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != this.target.charAt(i)) return false;
        }
        return true;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }
}

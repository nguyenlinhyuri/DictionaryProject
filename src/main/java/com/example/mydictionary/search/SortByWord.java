package com.example.mydictionary.search;

import java.util.Comparator;

public class SortByWord implements Comparator<Word> {

    @Override
    public int compare(Word o1, Word o2) {
        return o1.getWord().compareTo(o2.getWord());
    }
}

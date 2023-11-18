package com.example.mydictionary.basic;

import java.util.Comparator;

public class SortByWord implements Comparator<com.example.mydictionary.basic.Word> {

    @Override
    public int compare(com.example.mydictionary.basic.Word o1, Word o2) {
        return o1.getTarget().compareTo(o2.getTarget());
    }
}

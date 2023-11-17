package com.example.mydictionary.search;

import java.util.*;

public class Trie {
    private Map<Character, Trie> children;
    private String content;  //nội dung của cây

    private boolean endOfWord = false;

    public void insert(String word) {
        if (word.isEmpty()) {
            System.out.println("gia tri cua entries la NULL");
        }
        Trie temp = this;
        for (char c : word.toCharArray()) {
            if (!temp.children.containsKey(c)) {
                temp.add(c);
            }
           temp = temp.children.get(c);

        }
        temp.endOfWord = true;
    }

    public void add(char ch) {
        String s;
        if (this.content == null)
        {
            s = Character.toString(ch);}
        else
        { s = this.content + ch;}
        children.put(ch, new Trie((s)));

    }


    public List<String> Complete(String prefix) {
//        if (prefix.isEmpty()) {
//            return new ArrayList<>();
//        }
       Trie temp = this;
        for (int i = 0; i < prefix.length(); i++) {
            if (!temp.children.containsKey(prefix.charAt(i)))
            {
                return null;
            }
            temp = temp.children.get(prefix.charAt(i));
        }
        return temp.allPrefixes();
    }
    public List<String> allPrefixes()
    {
        List<String> resultWord = new ArrayList<String>();
        if (this.endOfWord)
        {
            resultWord.add(this.content);
        }
        for (Map.Entry<Character, Trie> e : children.entrySet())
        {
            Trie child = e.getValue();
            Collection<String> childPrefixes = child.allPrefixes();
            resultWord.addAll(childPrefixes);
        }
        return  resultWord;
    }


    public Trie() {
        this(null);
    }

    private Trie(String content) {
        this.content = content;
        children = new HashMap<Character, Trie>();
    }
}

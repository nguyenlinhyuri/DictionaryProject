package com.example.mydictionary.search;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class dictionaryFunction {
    private static final String SPLITTING_CHARACTERS = "<html>";
    private Trie trie = new Trie();

    /**
     * load từ file.
     * @param path đường dẫn file.
     */

    public void readFromFile(Dictionary diction, String path) {
        try {
            FileReader fileReader = new FileReader(path);
            BufferedReader buf = new BufferedReader(fileReader);

            String line;
            while ((line = buf.readLine()) != null) {
                String[] parts = line.split(SPLITTING_CHARACTERS);
                String word = parts[0];
                String deifinition = SPLITTING_CHARACTERS + parts[1];
                Word wordObj = new Word();
                wordObj.setWord(word.trim());
                wordObj.setExplain(deifinition.trim());
                diction.add(wordObj);

            }
            buf.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public ObservableList<String> lookUp(Dictionary diction , String key)
    {
        ObservableList<String> list = FXCollections.observableArrayList();
        try
        {
            List<String> result = trie.Complete(key);
            if (result != null)
            {
                int length = Math.min(result.size() , 15);
                for (int i = 0 ; i < length ; i++)
                {
                    list.add(result.get(i));
                }

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return list;
    }



    /**
     * tim kiem word.
     * return : nghia cua tu
     */
    public int searchWord(Dictionary diction , String key) {
           diction.sort(new SortByWord());
           int left = 0;
           int right = diction.size() - 1;

           while (left <= right)
           {
               int  mid = left + (right - left) / 2;
              // System.out.println(mid);
               int ans = diction.get(mid).getWord().compareTo(key);
               //System.out.println(diction.get(mid).getWord());
               if (ans == 0)
               {
                   return mid;
               }
               if (ans <= 0)
               {
                   left = mid + 1;
               }
               else {
                   right = mid - 1;
               }

          }


      return -1;

    }

    /**
     * chèn diction vào trie.
     * @param diction
     */
    public void setTrie(Dictionary diction)
    {
        try
        {
            for (Word w : diction)
            {
                trie.insert(w.getWord());
            }
            System.out.println("insert trie thanh cong");
        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }
    }
}

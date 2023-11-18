package com.example.mydictionary.basic;

import com.example.mydictionary.search.Trie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.List;

public class DictionaryManagement {
    private Dictionary dictionary = new Dictionary();

    public Dictionary getDictionary() {
        return dictionary;
    }

    public void setDictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    private static final String SPLITTING_CHARACTERS = "<html>";
    private Trie trie = new Trie();


    /**
     * insertFromCommandline() có chức năng nhập liệu:
     * ● Nhập vào bàn phím số lượng từ vựng (Word).
     * ● Định dạng nhập dữ liệu từ điển Anh – Việt:
     * ○ Dòng 1: Nhập từ tiếng Anh.
     * ○ Dòng 2: Nhập giải thích bằng tiếng Việt
     */
    public void insertFromCommandline(String target, String explain) {
        dictionary.addWord(target, explain);
    }

    /**
     * insertFromFile() nhập dữ liệu từ điển từ tệp dictionaries.txt
     */
    void insertFromFile() throws FileNotFoundException {
        File file = new File("basic/dict.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while(true){
            try{
                if ((line = br.readLine()) == null) break;
            } catch(IOException e){
                throw new RuntimeException(e);
            }
            String[] part = line.split("\t");
            dictionary.addWord(part[0], part[1]);
        }
        System.out.println("Imported successfully!");

    }

    /**
     * dictionaryLookup() có chức năng
     * tra cứu từ điển bằng dòng lệnh
     */
    void dictionaryLookup(String keyword) {
        for (int i=0; i<dictionary.dict_size(); i++){
            if (dictionary.get_Target(i).getTarget().equals(keyword)){
                System.out.println("Meaning: " + dictionary.getExplain(i));
                return;
            }
        }
        System.out.println("Not found!");
    }

    void addWord(String target, String explain) {
        dictionary.addWord(target, explain);
    }

    void updateWord(String word, String update_meaning) {
        dictionary.updateWord(word, update_meaning);
    }

    void deleteWord(String word) {
        dictionary.deleteWord(word);
    }


    /**
     * load từ file.
     * @param path đường dẫn file.
     */

    public void readFromFile(com.example.mydictionary.search.Dictionary diction, String path) {
        try {
            FileReader fileReader = new FileReader(path);
            BufferedReader buf = new BufferedReader(fileReader);

            String line;
            while ((line = buf.readLine()) != null) {
                String[] parts = line.split(SPLITTING_CHARACTERS);
                String word = parts[0];
                String deifinition = SPLITTING_CHARACTERS + parts[1];
                Word wordObj = new Word();
                wordObj.setTarget(word.trim());
                wordObj.setExplain(deifinition.trim());
                diction.add(wordObj);

            }
            buf.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public ObservableList<String> lookUp(com.example.mydictionary.search.Dictionary diction , String key)
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
    public int searchWord(com.example.mydictionary.search.Dictionary diction , String key) {
        diction.sort(new SortByWord());
        int left = 0;
        int right = diction.size() - 1;

        while (left <= right)
        {
            int  mid = left + (right - left) / 2;
            // System.out.println(mid);
            int ans = diction.get(mid).getTarget().compareTo(key);
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
    public void setTrie(com.example.mydictionary.search.Dictionary diction)
    {
        try
        {
            for (Word w : diction)
            {
                trie.insert(w.getTarget());
            }
            System.out.println("insert trie thanh cong");
        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * dictionaryExportToFile() có chức
     * năng xuất dữ liệu từ điển hiện tại ra tệp.
     */
    void dictionaryExportToFile() throws IOException {
        FileWriter fileWriter = new FileWriter("basic/data.txt");
        BufferedWriter bw = new BufferedWriter(fileWriter);
        for (Word x : dictionary.getDict()){
            bw.write(x.getTarget() + '\t' + x.getExplain() + '\n');
        }
        System.out.println("Exported successfully!");
        bw.close();
    }


}

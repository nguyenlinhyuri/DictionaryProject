package com.example.mydictionary.basic;

import java.io.*;

public class DictionaryManagement {
    private Dictionary dictionary = new Dictionary();

    public Dictionary getDictionary() {
        return dictionary;
    }

    public void setDictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

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
        File file = new File("E:\\Java\\intellijJava\\OOPtemp\\MyDictionary\\src\\main\\java\\com\\example\\mydictionary\\basic\\dict.txt");
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
     * dictionaryExportToFile() có chức
     * năng xuất dữ liệu từ điển hiện tại ra tệp.
     */
    void dictionaryExportToFile() throws IOException {
        FileWriter fileWriter = new FileWriter("E:\\Java\\intellijJava\\OOPtemp\\MyDictionary\\src\\main\\java\\com\\example\\mydictionary\\basic\\data.txt");
        BufferedWriter bw = new BufferedWriter(fileWriter);
        for (Word x : dictionary.getDict()){
            bw.write(x.getTarget() + '\t' + x.getExplain() + '\n');
        }
        System.out.println("Exported successfully!");
        bw.close();
    }

}

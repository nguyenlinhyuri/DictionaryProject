package com.example.mydictionary.basic;

import java.io.IOException;
import java.util.Scanner;

public class DictionaryCommand_Line {
    DictionaryCommandline dict_cli = new DictionaryCommandline();
    /**
     * Hàm dictionaryBasic() có chức năng gọi hàm insertFromCommandline() và
     * showAllWords().
     */
//    void dictionaryBasic(){
//        dict_cli.getDict_manage().insertFromCommandline();
//        dict_cli.showAllWords();
//    }

    /**
     *
     */
    public void dictionaryAdvanced() throws IOException {
        Scanner sc = new Scanner(System.in);
        int act = 0;
        do {
            System.out.println("""
                    ---Welcome to My Application!---
                    [0] Exit
                    [1] Add
                    [2] Remove
                    [3] Update
                    [4] Display
                    [5] Lookup
                    [6] Search
                    [7] Game
                    [8] Import from file
                    [9] Export to file
                    Your action:\s""");

            act = sc.nextInt();
            switch (act){
                case 0: // exit
                    System.out.println("Exiting...");
                    break;
                case 1: // add
                    System.out.println("Enter the number of words: ");
                    int num = sc.nextInt();
                    while(num-- > 0){
                        System.out.print("Enter English word: ");
                        String target = sc.next();
                        sc.nextLine();
                        System.out.print("Enter Vietnamese meaning: ");
                        String explain = sc.nextLine();
                        dict_cli.getDict_manage().insertFromCommandline(target, explain);
                    }

                    break;
                case 2:  // remove
                    System.out.print("Enter the word to remove: ");
                    String wordToRemove = sc.next();
                    dict_cli.getDict_manage().deleteWord(wordToRemove);
                    break;
                case 3://update
                    System.out.print("Enter the word need to be updated: ");
                    String wordToUpdate = sc.next();
                    sc.nextLine();
                    System.out.println("New update: ");
                    String update_meaning = sc.nextLine();
                    dict_cli.getDict_manage().updateWord(wordToUpdate, update_meaning);
                    break;
                case 4: // display
                    dict_cli.showAllWords();
                    break;
                case 5: // lookup
                    System.out.print("Look Up: ");
                    String wordToLookup = sc.next();
                    sc.nextLine();
                    dict_cli.getDict_manage().dictionaryLookup(wordToLookup);
                    break;
                case 6: // search
                    System.out.println("Enter the word to search: ");
                    String wordToSearch = sc.next();
                    dict_cli.dictionarySearcher(wordToSearch);
                    break;
                case 7: // game
                    break;
                case 8: // insert from file
                    dict_cli.getDict_manage().insertFromFile();
                    break;
                case 9: // export to file
                    dict_cli.getDict_manage().dictionaryExportToFile();
                    break;
                default:
                    System.out.println("Action not supported.");
                    break;
            }

        } while (act != 0);

    }
}

import java.util.ArrayList;

public class Dictionary {
    private ArrayList<Word> dict = new ArrayList<>();

    public ArrayList<Word> getDict() {
        return dict;
    }

    public void setDict(ArrayList<Word> dict) {
        this.dict = dict;
    }

    /**
     * add word
     *
     * @param target  english
     * @param explain vietnamese
     */
    public void addWord(String target, String explain) {
        Word word = new Word(target, explain);
        dict.add(word);
    }

    /**
     * update word
     * @param word word
     * @param update_meaning new meaning
     */
    public void updateWord(String word, String update_meaning) {
        boolean check = false;
        for (Word x : dict) {
            if (x.getWord_target().equals(word)) {
                check = true;
                x.setWord_explain(update_meaning);
                System.out.println("Updated successfully!");
                break;
            }
        }
        if (!check) System.out.println("Not found!");
    }

    /**
     * delete word
     *
     * @param word delete the word
     */
    public void deleteWord(String word) {
        dict.removeIf(x -> x.getWord_target().equals(word));
        System.out.println("Deleted successfully!");
    }

    /**
     * get size
     *
     * @return size
     */
    public int dict_size() {
        return dict.size();
    }

    public Word get_Target(int i) {
        return dict.get(i);
    }

    public String getExplain(int i) {
        return dict.get(i).getWord_explain();
    }

    public String getTarget(int i){
        return dict.get(i).getWord_target();
    }



}

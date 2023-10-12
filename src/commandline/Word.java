package commandline;

public class Word {
    /**
     * word_target: english word
     * word_explain: vietnamese explain
     */
    private String word_target;
    private String word_explain;

    /**
     * constructor
     * @param word_target is english
     * @param word_explain is vietnamese
     */
    public Word(String word_target, String word_explain) {
        this.word_target = word_target;
        this.word_explain = word_explain;
    }

    public boolean check_start(String s){
        if (s.length() > this.word_target.length()) return false;
        for (int i=0; i<s.length(); i++){
            if (s.charAt(i) != this.word_target.charAt(i)) return false;
        }
        return true;
    }

    public String getWord_target() {
        return word_target;
    }

    public void setWord_target(String word_target) {
        this.word_target = word_target;
    }

    public String getWord_explain() {
        return word_explain;
    }

    public void setWord_explain(String word_explain) {
        this.word_explain = word_explain;
    }
}

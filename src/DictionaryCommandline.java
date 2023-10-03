public class DictionaryCommandline {
    private DictionaryManagement dict_manage = new DictionaryManagement();

    public DictionaryManagement getDict_manage() {
        return dict_manage;
    }

    public void setDict_manage(DictionaryManagement dict_manage) {
        this.dict_manage = dict_manage;
    }

    /**
     * search
     * @param word need to be search
     */
    void dictionarySearcher(String word){
        for (Word x : dict_manage.getDictionary().getDict()){
            if (x.check_start(word)) {
                System.out.println(x.getWord_target() + " | " + x.getWord_explain());
            }
        }
    }

    /**
     * Hàm
     * showAllWords() có chức năng hiển thị kết quả danh sách dữ liệu từ vựng trên màn
     * hình theo thứ tự alphabet. Ví dụ như sau:
     * No | English | Vietnamese
     * 1 | Hello | Xin chao
     * 2 | House | Ngoi nha
     * 3 | Love | Yeu thuong
     */
    void showAllWords(){
        int len = dict_manage.getDictionary().dict_size();
        System.out.println("No | English | Vietnamese");
        for (int i=0; i<len; i++){
            System.out.println(i+1 + " | " + dict_manage.getDictionary().getTarget(i) +
                                    " | " + dict_manage.getDictionary().getExplain(i));
        }

    }
}

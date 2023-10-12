package commandline;

import java.io.IOException;
import java.io.InputStream;

public class Main {
    public static void main(String[] args) throws IOException {
        DictionaryCommand_Line dict = new DictionaryCommand_Line();
        dict.dictionaryAdvanced();
    }
}

package logic;

import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: platkovskyas
 * Date: 01.11.13
 * Time: 10:33
 */
public class ParseFile {
    static String set = "mark";
    WorkFile workFile;
    String[] text;

    public ParseFile(File fileName) {
        workFile = new WorkFile(fileName);
    }

    public void getResult() throws IOException {
        text = workFile.readLines();
        for (int i = 0; i < text.length - 1; i++)
            for (int k = i+1; k < text.length; k++)
                if (text[i].equals(text[k]))
                    text[k] = doResult(text[i]);
        workFile.writeFile(text);
    }

    public static String doResult(String str){
        switch (set) {
            case "mark":
                return "*" + str;
            case "delete":
                return "";
        }
        return str;
    }

}

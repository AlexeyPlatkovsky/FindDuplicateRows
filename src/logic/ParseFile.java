package logic;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: platkovskyas
 * Date: 01.11.13
 * Time: 10:33
 */
public class ParseFile {
    String setting = "";
    WorkFile workFile;
    List<String> text;

    public ParseFile(File fileName, String setting, String deleteSetting) {
        this.setting = setting;
        workFile = new WorkFile(fileName, deleteSetting);
    }

    public void doParse() throws IOException {
        text = workFile.readLines();
        for (int i = 0; i < text.size() - 1; i++)
            for (int k = i+1; k < text.size(); k++)
                if (text.get(i).equals(text.get(k)))
                    text.set(k, applyMarkSettings(text.get(k)));
        workFile.writeFile(text);
    }

    public String applyMarkSettings(String str){
        switch (setting.toLowerCase()) {
            case "mark":
                return "*" + str;
            case "delete":
                return "";
        }
        return str;
    }

}

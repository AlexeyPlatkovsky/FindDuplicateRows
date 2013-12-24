package logic;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: platkovskyas
 * Date: 01.11.13
 * Time: 11:49
 */
public class WorkFile {
    File fileName;
    String setting = "";

    public WorkFile(File fileName, String deleteSetting){
            this.fileName = fileName;
        setting = deleteSetting;
    }

    public List<String> readLines() throws IOException {
        List<String> str = new ArrayList<>();
        BufferedReader file = new BufferedReader(new FileReader(fileName));
        String line;

        while((line = file.readLine()) !=null){
            str.add(line);
        }

        file.close();
        switch (setting.toLowerCase()){
            case "all punctuation": return deleteAll(str);
            case "part of symbols": return deletePart(str);
            case "nothing": return str;
        }
        return str;
    }

    public List<String> deleteAll(List<String> str) throws IOException {
        int k = 0;
        for (String line : str){
            str.set(k, line.replaceAll("\\p{Punct}", ""));
            k++;
        }
        return str;
    }

    public List<String> deletePart(List<String> str) throws IOException {
        int k = 0;
        for (String line : str){
            str.set(k, line.replaceAll(",", "").replaceAll("\\\\", "").replaceAll("\"", ""));
            k++;
        }
        return str;
    }

    public void writeFile(List<String> str) throws IOException {
        BufferedWriter file = new BufferedWriter(new FileWriter("result.txt"));

        for (String i: str)
            file.write(i + "\n");
        file.close();
    }
}

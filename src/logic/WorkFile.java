package logic;

import java.io.*;

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

    public int rowCount() throws IOException {
        BufferedReader file = new BufferedReader(new FileReader(fileName));
        int k = 0;
        String line = file.readLine();
        while (line!=null){
            k++;
            line = file.readLine();
        }
        file.close();
        return k;
    }



    public String[] readLines() throws IOException {
        String result[] = new String[rowCount()];
        BufferedReader file = new BufferedReader(new FileReader(fileName));
        String line = file.readLine();
        int k = 0;

        while(line!=null){
            result[k] = line;
            k++;
            line = file.readLine();
        }
        file.close();
        switch (setting.toLowerCase()){
            case "all punctuation": return deleteAll(result);
            case "part of symbols": return deletePart(result);
            case "nothing": return result;
        }
        return result;
    }

    public String[] deleteAll(String[] strs) throws IOException {
        String result[] = new String[rowCount()];
        int k = 0;
        for (String line : strs){
            result[k] = line.replaceAll("\\p{Punct}", "");
            k++;
        }
        return result;
    }

    public String[] deletePart(String[] strs) throws IOException {
        String result[] = new String[rowCount()];
        int k = 0;
        for (String line : strs){
            result[k] = line.replaceAll(",", "").replaceAll("\\\\", "").replaceAll("\"", "");
            k++;
        }
        return result;
    }

    public void writeFile(String[] strings) throws IOException {
        BufferedWriter file = new BufferedWriter(new FileWriter("result.txt"));

        for (String i: strings)
            file.write(i + "\n");
        file.close();
    }
}

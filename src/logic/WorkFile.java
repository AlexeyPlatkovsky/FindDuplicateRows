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

    public WorkFile(File fileName){
            this.fileName = fileName;
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
        return deleteAll(result);
    }

    public String[] deleteAll(String[] strs) throws IOException {
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

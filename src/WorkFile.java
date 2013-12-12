import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: platkovskyas
 * Date: 01.11.13
 * Time: 11:49
 */
public class WorkFile {
    String fileName;

    public WorkFile(String fileName){
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

    /*public String[] deleteSymbols(String[] strings){
        for (each )
    }*/


    public String[] readLines() throws IOException {
        String result[] = new String[rowCount()];
        BufferedReader file = new BufferedReader(new FileReader(fileName));
        String line = file.readLine();
        int k = 0;
        while(line!=null){
            result[k] = line.replaceAll("\\p{Punct}", "");
            k++;
            line = file.readLine();
        }
        file.close();
        return result;
    }

    public void writeFile(String[] strings) throws IOException {
        BufferedWriter file = new BufferedWriter(new FileWriter("result.txt"));

        for (String i: strings)
            file.write(i + "\n");
        file.close();
    }
}
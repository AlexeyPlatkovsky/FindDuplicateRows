package logic;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: platkovskyas
 * Date: 01.11.13
 * Time: 10:33
 */
public class LoadApp {
    static String set = "mark";

    public static void main (String[] args) throws IOException {
        WorkFile file = new WorkFile("test.txt");
        String result[] = file.readLines();

        for (int i = 0; i < result.length-1; i++)
            for (int k = i+1; k< result.length; k++)
                if (result[i].equals(result[k]) && !result[i].equals("*"))
                    result[k] = doResult(result[k]);

        file.writeFile(result);
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

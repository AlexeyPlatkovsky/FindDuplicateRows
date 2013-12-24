package sources;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: platkovskyas
 * Date: 24.12.13
 * Time: 15:44
 */
public class Localization {
    public static String frame = "";
    public static String settingsLabel = "";
    public static String delete = "";
    public static String mark = "";
    public static String parseButton = "";
    public static String chooseButton = "";
    public static String fileNameLabel = "";
    public static String chooseFile = "";
    public static String deleteAll = "";
    public static String deleteNothing = "";
    public static String deleteSome = "";
    public static String deleteLabel = "";
    public static String fileFilter = "";
    static Properties settings;
    public static String selectedFile = "";

    public static void loadProperties(File file) throws IOException {
        settings = new Properties();
        settings.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(file.getPath()));

        frame = settings.getProperty("frame");
        settingsLabel = settings.getProperty("settingsLabel");
        delete = settings.getProperty("delete");
        mark = settings.getProperty("mark");
        parseButton = settings.getProperty("parseButton");
        chooseButton = settings.getProperty("chooseButton");
        fileNameLabel = settings.getProperty("fileNameLabel");
        chooseFile = settings.getProperty("chooseFile");
        deleteAll = settings.getProperty("deleteAll");
        deleteNothing = settings.getProperty("deleteNothing");
        deleteSome = settings.getProperty("deleteSome");
        deleteLabel = settings.getProperty("deleteLabel");
        fileFilter = settings.getProperty("fileFilter");
        selectedFile = settings.getProperty("selectedFile");
    }
}

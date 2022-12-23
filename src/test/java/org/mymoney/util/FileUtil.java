package org.mymoney.util;

import java.io.File;
import java.io.IOException;

public class FileUtil {

    public static String createNoReadAccessFile() {
        String filepath = "FILEPATH_WITH_NO_READ_ACCESS";
        // TODO : programatically create file with no read access
        return filepath;
    }

    public static String createNullFilePath() {
        return null;
    }

    public static String createEmptyFile() throws IOException {
        String emptyfilepath = "FILEPATH_WITH_NO_CONTENT";
        // TODO : programatically create file which is empty
        File tempFile = File.createTempFile("MyMoneyFileUtil-",null);
        emptyfilepath = tempFile.getPath();
        return emptyfilepath;
    }
}

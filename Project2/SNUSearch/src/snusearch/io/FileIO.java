package snusearch.io;

import java.io.*;
import java.util.List;

public abstract class FileIO {
    protected String filePath;

    public FileIO(String filePath) {
        this.filePath = filePath;
    }

    public abstract List<String> readLines();

    public abstract boolean writeLines(List<String> lines);

    public abstract boolean appendLine(String line);

    protected boolean fileExists() { // check if file exists
        File file = new File(filePath);
        return file.exists();
    }

    protected void createFile() { // create file
        try {
            File file = new File(filePath);
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package snusearch.io;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
public class FileIOModule extends FileIO {
    private static final String DATA_DIRECTORY = "data/";
    public FileIOModule(String filePath) {
        super(DATA_DIRECTORY + filePath);
        createDirectoryIfNotExists();
    }
    private void createDirectoryIfNotExists() {
        File directory = new File(DATA_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }
    @Override
    public List<String> readLines() { // read lines from file and return as List<String>
        List<String> lines = new ArrayList<>();
        try {
            if (!fileExists()) {
                createFile();
            }
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    @Override
    public boolean writeLines(List<String> lines) { // write lines to file
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
            writer.close();
            return true; // Write operation succeeded
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false; // Write operation failed
    }

    @Override
    public boolean appendLine(String line) { // append line to file
        try {
            if (!fileExists()) {
                createFile();
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
            writer.write(line);
            writer.newLine();
            writer.close();
            return true; // Append operation succeeded
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false; // Append operation failed
    }
}
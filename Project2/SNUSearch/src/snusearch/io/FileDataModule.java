package snusearch.io;

import java.util.List;
public abstract class FileDataModule {
    protected FileIOModule fileIO;
    protected String filePath;

    public FileDataModule(String filePath) {
        this.filePath = filePath;
        fileIO = new FileIOModule(filePath);
    }
    public List<String> readLines() { // read lines from file
        return fileIO.readLines();
    }

    public boolean appendLine(String line) { // append line to file
        return fileIO.appendLine(line);
    }

    public void writeLines(List<String> lines) { // write lines to file
        fileIO.writeLines(lines);
    }
}
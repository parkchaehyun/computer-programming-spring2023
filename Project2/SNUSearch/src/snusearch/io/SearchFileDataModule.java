package snusearch.io;

import snusearch.utility.UtilityModule;

import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Comparator;

public class SearchFileDataModule extends FileDataModule {
    public SearchFileDataModule() {
        super("users.txt");
    }

    public boolean joinUser(String id, String passwd) {
        // Logic for joining a user
        // check if id already exists
        id = id.toLowerCase(); //id is case-insensitive
        List<String> lines = readLines();
        boolean exists = false;

        for (String line : lines) {
            String[] userData = line.split(",");
            if (userData[0].equals(id)) {
                exists = true;
            }
        }

        if (exists) {
            return false;
        } else {

            if (!UtilityModule.isValidPassword(passwd)) { // check if passwd is valid
                return false;
            }

            // add to users.txt file
            String userData = id + "," + passwd;
            return appendLine(userData);
        }
    }
    public boolean loginUser(String id, String passwd) {
        // Logic for logging in a user
        id = id.toLowerCase(); //id is case-insensitive
        List<String> lines = readLines();
        boolean login = false;

        for (String line : lines) {
            String[] userData = line.split(",");
            if (userData[0].equals(id) && userData[1].equals(passwd)) {
                login = true;
                // overwrite logged_in_user.txt
                FileIOModule loggedin_fileIO = new FileIOModule("logged_in_user.txt");
                List<String> loggedin_lines = new ArrayList<String>();
                loggedin_lines.add(id);
                loggedin_fileIO.writeLines(loggedin_lines);
            }
        }

        return login;
    }

    public boolean logoutUser(String id) {
        // Logic for logging out a user
        // check logged_in_user.txt file
        id = id.toLowerCase(); //id is case-insensitive
        FileIOModule loggedin_fileIO = new FileIOModule("logged_in_user.txt");
        List<String> loggedin_lines = loggedin_fileIO.readLines();
        Iterator<String> iterator = loggedin_lines.iterator();
        boolean logout = false;

        while (iterator.hasNext()) {
            String line = iterator.next();
            if (line.equals(id)) { // if id is found in logged_in_user.txt, remove from file
                iterator.remove();
                logout = true;
                loggedin_fileIO.writeLines(loggedin_lines);
                break;
            }
        }

        return logout;
    }

    public boolean leaveUser(String id, String passwd) {
        // Logic for leaving a user
        id = id.toLowerCase(); //id is case insensitive
        List<String> lines = readLines();
        boolean leave = false;

        Iterator<String> iterator = lines.iterator();
        while (iterator.hasNext()) {
            String line = iterator.next();
            String[] userData = line.split(",");
            if (userData[0].equals(id) && userData[1].equals(passwd)) {
                leave = true;
                // remove from users.txt file
                iterator.remove();
                writeLines(lines);
                // add to left user file
                FileIOModule left_fileIO = new FileIOModule("left_users.txt");
                left_fileIO.appendLine(line);

                break;
            }
        }

        return leave;
    }

    public boolean recoverUser(String id, String passwd) {
        // Logic for recovering a user
        // check left_users.txt file
        id = id.toLowerCase(); //id is case insensitive
        FileIOModule left_fileIO = new FileIOModule("left_users.txt");
        List<String> left_lines = left_fileIO.readLines();
        boolean recover = false;

        Iterator<String> iterator = left_lines.iterator();
        while (iterator.hasNext()) {
            String line = iterator.next();
            String[] userData = line.split(",");
            if (userData[0].equals(id) && userData[1].equals(passwd)) {
                recover = true;
                // remove from left user file
                iterator.remove();
                left_fileIO.writeLines(left_lines);
                // add to users.txt file
                appendLine(line);

                break;
            }
        }

        return recover;
    }

    public String loadUsers() {
        // Logic for loading users
        String userString = "";
        List<String> userLines = readLines();

        if (userLines.size() > 0) {
            // load users.txt file and save each user and end with \n
            for (String line : userLines) {
                userString += line.split(",")[0] + "\n";
            }
        }

        return userString;
    }

    public boolean saveData(String id, String query) {
        // Logic for saving data
        try {
            // append to data.txt file
            FileIOModule data_fileIO = new FileIOModule("data.txt");
            data_fileIO.appendLine(id + "," + query);

            // Data saving was successful
            return true;
        } catch (Exception e) {
            // Data saving failed
            e.printStackTrace();
            return false;
        }
    }

    public String loadData(String id) {
        // Logic for loading data
        // read data.txt file
        String dataString = "";
        FileIOModule data_fileIO = new FileIOModule("data.txt");
        List<String> dataLines = data_fileIO.readLines();

        if (dataLines.size() > 0) {
            // load data.txt file and save each data and end with \n
            for (String line : dataLines) {
                String[] data = line.split(",");
                if (data[0].equals(id)) {
                    dataString += data[1] + "\n";
                }
            }
        }

        return dataString;
    }
    public String[] loadData() {
        // Logic for loading data
        // read data.txt file
        String dataString = "";
        FileIOModule data_fileIO = new FileIOModule("data.txt");
        List<String> dataLines = data_fileIO.readLines();

        if (dataLines.size() > 0) {
            // load data.txt file and save each data and end with \n
            for (String line : dataLines) {
                String[] data = line.split(",");
                dataString += data[1] + "\n";
            }
        }

        return dataString.split("\n");
    }

    public String loadHotData() {
        // Load data using the loadData() method
        String[] data = loadData();

        // Check if there is any data
        if (data.length == 0) {
            return ""; // Return an empty string if there is no data
        }

        // Count the frequency of each string
        Map<String, Integer> frequencyMap = new HashMap<>();
        for (String str : data) {
            frequencyMap.put(str, frequencyMap.getOrDefault(str, 0) + 1); // Increment the frequency of the string
        }

        // Sort the strings based on frequency
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(frequencyMap.entrySet());
        entries.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        // Build the result string with the top 10 most frequent strings
        StringBuilder result = new StringBuilder();
        int count = Math.min(10, entries.size());
        for (int i = 0; i < count; i++) {
            result.append(entries.get(i).getKey() + "\n");
        }

        return result.toString();
    }

    public void saveLog(String log) {
        // Logic for saving log
        // append to log.txt file
        FileIOModule log_fileIO = new FileIOModule("log.txt");
        log_fileIO.appendLine(log);
    }

    public String loadLog() {
        // Logic for loading log
        // read log.txt file
        String logString = "";
        FileIOModule log_fileIO = new FileIOModule("log.txt");
        List<String> logLines = log_fileIO.readLines();

        if (logLines.size() > 0) {
            // load log.txt file and save each log and end with \n
            for (String line : logLines) {
                logString += line + "\n";
            }
        }

        return logString;
    }
}

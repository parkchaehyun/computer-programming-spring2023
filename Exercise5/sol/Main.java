import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Bank bank = new Bank();
        String filename = "data.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] userData = line.split("\\|\\|");
                String id = userData[0];
                String pw = userData[1];
                String accountNumber = userData[2];
                int balance = Integer.parseInt(userData[3]);
                boolean hasCard = userData[4].equalsIgnoreCase("y");
                int cash = Integer.parseInt(userData[5]);

                User user = new User(id, pw, accountNumber, balance, hasCard, cash);
                bank.addUser(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        bank.login();
    }
}
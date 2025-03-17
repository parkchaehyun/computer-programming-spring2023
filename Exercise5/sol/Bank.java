import java.util.ArrayList;
import java.util.Scanner;
public class Bank {
    private ArrayList<User> users;

    public Bank() {
        users = new ArrayList<>();
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public User getUser(String id) throws UserNotFoundException {
        for (User user : users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        throw new UserNotFoundException("존재하지 않는 회원입니다.");
    }
    public User getUserByAccountNumber(String accountNumber) throws UserNotFoundException {
        for (User user : users) {
            if (user.getAccountNumber().equals(accountNumber)) {
                return user;
            }
        }
        throw new UserNotFoundException("해당 계좌는 없습니다.");
    }
    public void login() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("아이디를 입력하세요: ");
        String id = scanner.nextLine();
        try {
            User user = getUser(id);

            System.out.print("비밀번호를 입력하세요: ");
            String pw = scanner.nextLine();

            if (user.getPw().equals(pw)) {
                System.out.println("로그인되었습니다.");
                //사용자가 인출, 입금, 송금, 물건 구매 기능을 선택해 수행하게 함
                System.out.println("1. 인출");
                System.out.println("2. 입금");
                System.out.println("3. 송금");
                System.out.println("4. 물건 구매");

                System.out.print("원하는 기능의 번호를 입력하세요: ");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch(choice) {
                    case 1:
                        System.out.print("인출할 금액을 입력하세요: ");
                        int amount = scanner.nextInt();
                        try {
                            user.withdraw(amount);
                            System.out.println(user.getId() + "님 " + amount + "원이 인출되었습니다.");
                            user.printBalance();
                            user.printCash();
                        } catch (NegativeBalanceException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 2:
                        System.out.print("입금할 금액을 입력하세요: ");
                        int amount2 = scanner.nextInt();
                        try {
                            user.deposit(amount2);
                            System.out.println(user.getId() + "님 " + amount2 + "원이 입금되었습니다.");
                            System.out.println("잔고: " + user.getBalance() + "원");
                        } catch (NegativeBalanceException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 3:
                        System.out.print("송금할 상대방 계좌를 입력하세요: ");
                        String accountNumber = scanner.nextLine();
                        try {
                            User user2 = getUserByAccountNumber(accountNumber);
                            System.out.print("송금할 금액을 입력하세요: ");
                            int amount3 = scanner.nextInt();
                            try {
                                user.transfer(amount3, user2);
                            } catch (NegativeBalanceException e) {
                                System.out.println(e.getMessage());
                            }
                        } catch (UserNotFoundException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 4:
                        System.out.print("구매할 물건의 금액을 입력하세요: ");
                        int amount4 = scanner.nextInt();
                        try {
                            user.purchase(amount4);
                        } catch (NegativeBalanceException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                }
            } else {
                throw new InvalidPasswordException();
            }
        } catch (UserNotFoundException e){
            System.out.println(e.getMessage());
        } catch (InvalidPasswordException e) {
            System.out.println(e.getMessage());
        }
    }

}

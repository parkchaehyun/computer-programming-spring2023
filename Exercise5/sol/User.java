public class User {
    private String id;
    private String pw;
    private String accountNumber;
    private int balance;
    private boolean hasCard;
    private int cash;

    public User(String id, String pw, String accountNumber, int balance, boolean hasCard, int cash) {
        this.id = id;
        this.pw = pw;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.hasCard = hasCard;
        this.cash = cash;
    }

    public void withdraw(int amount) throws NegativeBalanceException {
        if (amount < 0) {
            throw new NegativeBalanceException("잘못된 금액");
        }

        if (balance < amount) {
            throw new NegativeBalanceException("잔고 부족");
        }
        balance -= amount;
        cash += amount;
    }
    public void deposit(int amount) throws NegativeBalanceException {
        if (amount < 0) {
            throw new NegativeBalanceException("음수 입금액");
        } else if (amount > cash) {
            throw new NegativeBalanceException("현금 부족");
        }

        balance += amount;
        cash -= amount;
    }

    public void transfer(int amount, User user) throws NegativeBalanceException {
        if (amount < 0) {
            throw new NegativeBalanceException("잘못된 금액");
        }

        try {
            withdraw(amount);
        } catch (NegativeBalanceException e) {
            System.out.println(e.getMessage());
            return;
        }

        try {
            user.deposit(amount);
            System.out.println(user.getId()+ "님 " + amount + "원이 입금되었습니다.");
            user.printBalance();

            System.out.println(id + "님 " + amount + "원이 송금되었습니다.");
            printBalance();
        } catch (NegativeBalanceException e) {
            System.out.println(e.getMessage());
        }
    }

    public void purchase(int amount) throws NegativeBalanceException {
        if (amount < 0) {
            throw new NegativeBalanceException("잘못된 금액");
        }

        if (hasCard) {
            try {
                purchaseWithCard(amount);
            } catch (NegativeBalanceException e) {
                System.out.println(e.getMessage());
                try {
                    purchaseWithCardAndCash(amount);
                } catch (NegativeCashException e1) {
                    System.out.println(e1.getMessage());
                }
            }
        } else {
            try {
                purchaseWithCash(amount);
            } catch (NegativeCashException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void purchaseWithCard(int amount) throws NegativeBalanceException {
        if (balance >= amount) {
            balance -= amount;
            System.out.println("물건이 구매되었습니다. (카드 결제)");
            printBalance();
            printCash();
        } else {
            throw new NegativeBalanceException("잔고 부족 (카드 단독 결제 실패)");
        }
    }

    private void purchaseWithCardAndCash(int amount) throws NegativeCashException {
        if (amount <= balance + cash) {
            amount -= balance;
            balance = 0;
            cash -= amount;
            System.out.println("물건이 구매되었습니다. (카드 + 현금 결제)");
            printBalance();
            printCash();
        } else {
            throw new NegativeCashException("(카드 + 현금 결제 실패)");
        }
    }

    private void purchaseWithCash(int amount) throws NegativeCashException {
        if (amount <= cash) {
            cash -= amount;
            System.out.println("물건이 구매되었습니다. (현금 결제)");
            printBalance();
            printCash();
        } else {
            throw new NegativeCashException("(현금 결제 실패)");
        }
    }

    public void printBalance() {
        System.out.println("잔고: " + balance + "원");
    }

    public void printCash() {
        System.out.println("현금: " + cash + "원");
    }

    public String getId() {
        return id;
    }

    public String getPw() {
        return pw;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public int getBalance() {
        return balance;
    }

    public boolean getHasCard() {
        return hasCard;
    }

    public int getCash() {
        return cash;
    }
}

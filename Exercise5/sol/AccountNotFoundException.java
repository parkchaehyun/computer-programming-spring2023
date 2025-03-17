public class AccountNotFoundException extends Exception {
    public AccountNotFoundException() {
        super("해당 계좌는 없습니다.");
    }
}

public class InvalidPasswordException extends Exception {
    public InvalidPasswordException() {
        super("잘못된 비밀번호입니다.");
    }
}

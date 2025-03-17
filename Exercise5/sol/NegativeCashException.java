public class NegativeCashException extends Exception {
    public NegativeCashException() {
        super("현금 부족");
    }
    public NegativeCashException(String message) {
        super("현금 부족 " + message);
    }
}

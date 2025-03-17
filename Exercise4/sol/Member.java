public class Member {
    String id;
    String pw;
    public Member(String id, String pw){
        this.id = id;
        this.pw = pw;
    }

    public String getId() {
        return id;
    }
    public String getPw() {
        return pw;
    }

    @Override
    public String toString() {
        return "id : " + id + " pw : " + pw;
    }
}

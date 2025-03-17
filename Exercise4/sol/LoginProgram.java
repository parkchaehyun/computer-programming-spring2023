import java.util.ArrayList;

public class LoginProgram {
    Member member;
    ArrayList<Member> members;

    public LoginProgram() {
        members = new ArrayList<>();
        Admin admin = new Admin("admin", "admin", members);
        members.add(admin);
        member = null;
    }
    public void signUp(String id, String pw) {
        if (idIsGood(id) && pwIsGood(pw)) {
            Member newMember = new Member(id, pw);
            members.add(newMember);
            System.out.println(id + "님 회원 가입을 축하합니다.");
        }
    }
    public boolean idExists(String id) {
        for (Member member : members) {
            if (member.getId().equalsIgnoreCase(id)) {
                return true;
            }
        }
        return false;
    }
    public boolean containsSpecialCharacters(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isLetterOrDigit(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }
    public boolean idIsGood(String id) {
        boolean bool = true;

        if (idExists(id)) {
            System.out.println("이미 존재하는 회원 아이디입니다.");
            System.out.println("다시 입력해주세요.");
            bool = false;
        }

        if (containsSpecialCharacters(id)) {
            System.out.println("특수 문자는 포함할 수 없습니다.");
            System.out.println("알파벳 대소문자와 숫자로만 입력해주세요.");
            bool = false;
        }

        return bool;
    }
    public boolean pwIsGood(String pw) {
        boolean bool = true;

        if (pw.length() < 10) {
            System.out.println("길이가 너무 짧습니다.");
            bool = false;
        }

        if (containsSpecialCharacters(pw)) {
            System.out.println("특수 문자는 포함할 수 없습니다.");
            System.out.println("알파벳 대소문자와 숫자로만 입력해주세요.");
            bool = false;
        }

        return bool;
    }
    public boolean login(String id, String pw) {
        for (Member member : members) {
            if (member.getId().equalsIgnoreCase(id)) {
                if (member.getPw().equalsIgnoreCase(pw)) {
                    this.member = member;
                    return true;
                } else {
                    System.out.println("비밀 번호가 일치하지 않습니다.");
                    return false;
                }
            }
        }
        System.out.println("존재하지 않는 회원 아이디입니다.");
        System.out.println("회원가입을 진행해 주시길 바랍니다.");
        return false;
    }
    public void logout() {
        member = null;
    }
    public void deleteMember() {
        if (member!= null){
            members.remove(member);
            member = null;
            System.out.println("탈퇴가 완료되었습니다.");
            System.out.println("이용해 주셔서 감사합니다.");
        }
    }

}

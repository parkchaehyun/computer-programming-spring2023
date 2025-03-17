import java.util.ArrayList;

public class Admin extends Member{
    ArrayList<Member> members;
    public Admin(String id, String pw, ArrayList<Member> members){
        super(id, pw);
        this.members = members;
    }
    public void printMembers(){
        System.out.println("현재 등록된 모든 회원들의 정보입니다.");
        for(Member member : members){
            if(member.getId().equalsIgnoreCase("admin"))
                continue;
            System.out.println(member);
        }
        System.out.println("전체 회원 수 : " + (members.size()-1));
    }
}

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("어서오세요. 간단한 로그인 프로그램입니다.");

        int menu = 0;

        LoginProgram loginProgram = new LoginProgram();

        while(true){
            System.out.println("원하시는 업무를 선택해 주세요.");
            System.out.println("1. 로그인 2. 회원가입 3. 종료");
            System.out.print("선택하기 : ");
            String input = sc.nextLine();

            System.out.println("**********************");
            System.out.println("");

            String id;
            String pw;

            try {
                menu = Integer.parseInt(input);
                switch (menu) {
                    case 1:
                        System.out.print("아이디를 입력하세요 : ");
                        id = sc.nextLine();
                        System.out.print("비밀번호를 입력하세요 : ");
                        pw = sc.nextLine();
                        if (loginProgram.login(id, pw) == true) {
                            System.out.println("**********************");
                            System.out.println("");
                            System.out.println(loginProgram.member.getId() + " 계정으로 로그인 되었습니다.");
                            if (loginProgram.member instanceof Admin) {
                                loop:
                                while (true) {
                                    System.out.println("원하시는 업무를 선택해 주세요.");
                                    System.out.println("1. 전체 회원 조회 2. 로그아웃");
                                    System.out.print("선택하기 : ");
                                    input = sc.nextLine();
                                    System.out.println("**********************");
                                    System.out.println("");
                                    try {
                                        menu = Integer.parseInt(input);
                                        switch (menu) {
                                            case 1:
                                                ((Admin) loginProgram.member).printMembers();
                                                break;
                                            case 2:
                                                loginProgram.logout();
                                                System.out.println("로그아웃 되었습니다.");
                                                System.out.println("**********************");
                                                System.out.println("");
                                                break loop;
                                            default:
                                                System.out.println("잘못 입력하셨습니다. 1 ~ 2 숫자를 입력해 주세요");
                                                break;
                                        }
                                    } catch (NumberFormatException e) {
                                        System.out.println("잘못 입력하셨습니다. 1 ~ 2 숫자를 입력해 주세요");
                                    }
                                    System.out.println("**********************");
                                    System.out.println("");
                                }
                            } else {
                                loop:
                                while (true) {
                                    System.out.println("원하시는 업무를 선택해 주세요.");
                                    System.out.println("1. 탈퇴하기 2. 로그아웃");
                                    System.out.print("선택하기 : ");
                                    input = sc.nextLine();
                                    System.out.println("**********************");
                                    System.out.println("");
                                    try {
                                        menu = Integer.parseInt(input);
                                        switch (menu) {
                                            case 1:
                                                loginProgram.deleteMember();
                                                System.out.println("**********************");
                                                System.out.println("");
                                                break loop;
                                            case 2:
                                                loginProgram.logout();
                                                System.out.println("로그아웃 되었습니다.");
                                                System.out.println("**********************");
                                                System.out.println("");
                                                break loop;
                                            default:
                                                System.out.println("잘못 입력하셨습니다. 1 ~ 2 숫자를 입력해 주세요");
                                                System.out.println("**********************");
                                                System.out.println("");
                                                break;
                                        }
                                    } catch (NumberFormatException e) {
                                        System.out.println("잘못 입력하셨습니다. 1 ~ 2 숫자를 입력해 주세요");
                                        System.out.println("**********************");
                                        System.out.println("");
                                    }
                                }
                            }
                        }
                        break;
                    case 2:
                        System.out.println("회원가입을 진행합니다.");
                        System.out.println("** 주의 ** 아이디와 비밀번호는 알파벳 대소문자와 숫자로만 이뤄져야 합니다.");
                        System.out.println("** 주의 ** 아이디는 대소문자를 구분하지 않습니다.");
                        System.out.println("** 주의 ** 비밀번호는 10자 이상이여야 합니다.");
                        while (true) {
                            System.out.print("아이디를 입력하세요 : ");
                            id = sc.nextLine();
                            if (loginProgram.idIsGood(id)) {
                                break;
                            }
                        }
                        while (true) {
                            System.out.print("비밀번호를 입력하세요 : ");
                            pw = sc.nextLine();
                            if (loginProgram.pwIsGood(pw)) {
                                break;
                            }
                            System.out.println("");
                        }
                        loginProgram.signUp(id, pw);
                        break;
                    case 3:
                        System.out.println("**********************");
                        System.out.println("");
                        System.out.println("로그인 프로그램을 종료합니다.");
                        System.out.println("**********************");
                        System.out.println("");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("잘못 입력하셨습니다. 1 ~ 3 숫자를 입력해 주세요");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("잘못 입력하셨습니다. 1 ~ 3 숫자를 입력해 주세요");
            }
            System.out.println("**********************");
            System.out.println("");
        }
    }
}
// View - BoardView
package View;

public class BoardView {

    private static BoardView instance;


    private BoardView() {}

   // 싱글톤
    public static synchronized BoardView getInstance() {
        if (instance == null) {
            instance = new BoardView();
        }
        return instance;
    }

    public void showMainMenu(String loggedInUserId) {
        System.out.println("\n===== 영화 리뷰 시스템 =====");

        if (loggedInUserId != null) {  // 로그인 상태일 때
            System.out.println("\t" + loggedInUserId + "님 환영합니다!");
        }

        if (loggedInUserId != null) {
            System.out.println("\n1. 로그아웃");
            System.out.println("2. 리뷰관리");
        } else {
            System.out.println("\n1. 회원가입");
            System.out.println("2. 로그인");
        }

        System.out.println("3. 종료");
        System.out.println("\n==========================");
        System.out.print("선택: ");
    }

    // 회원가입 정보 요청
    public void requestRegistrationInfo() {
        System.out.println("\n==========================");
        System.out.println("\n회원가입을 진행합니다.");
        System.out.println("\n==========================");
    }

    // 로그인 정보 요청
    public void promptForLogin() {
        System.out.println("\n==========================");
        System.out.println("\n로그인 정보를 입력하세요.");
        System.out.println("\n==========================");
    }

    // 리뷰 관리 메뉴 출력
    public void showReviewMenu() {
        System.out.println("\n======== 리뷰 관리 ========");
        System.out.println("\n1. 리뷰 작성");
        System.out.println("2. 리뷰 조회");
        System.out.println("3. 리뷰 수정");
        System.out.println("4. 리뷰 삭제");
        System.out.println("5. 모든 리뷰 보기");
        System.out.println("\n==========================");
        System.out.print("선택: ");
    }

    // 오류 메시지 출력
    public void showErrorMessage(String message) {
        System.out.println("\n[오류] " + message);
    }

    // 성공 메시지 출력
    public void showSuccessMessage(String message) {
        System.out.println("\n[성공] " + message);
    }
}

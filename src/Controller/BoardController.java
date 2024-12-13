// Controller - BoardController
package Controller;

import Model.ReviewDao;
import Model.ReviewDto;
import Model.UserDao;
import Model.UserDto;
import View.BoardView;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class BoardController {
    private final ReviewDao reviewDao;
    private final UserDao userDao;
    private final BoardView view;
    private String loggedInUserId = null;

    public BoardController(Connection connection) {
        this.userDao = new UserDao(connection);
        this.reviewDao = new ReviewDao(connection);
        this.view = BoardView.getInstance();
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            view.showMainMenu(loggedInUserId);
            int choice = scanner.nextInt();
            scanner.nextLine();

            try {

                if (loggedInUserId != null) {
                    switch (choice) {
                        case 1 -> handleLogout(); // 로그아웃 처리
                        case 2 -> handleReviewManagement(scanner); // 리뷰 관리
                        case 3 -> {
                            System.out.println("프로그램 종료!");
                            return;
                        }
                        default -> System.out.println("올바른 선택을 해주세요!");
                    }
                } else {
                    switch (choice) {
                        case 1 -> handleUserRegistration(scanner); // 회원가입
                        case 2 -> handleUserLogin(scanner); // 로그인
                        case 3 -> {
                            System.out.println("프로그램 종료!");
                            return;
                        }
                        default -> System.out.println("올바른 선택을 해주세요!");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 1. 회원가입
    private void handleUserRegistration(Scanner scanner) throws SQLException {
        view.requestRegistrationInfo();
        System.out.print("아이디: ");
        String id = scanner.nextLine();
        System.out.print("비밀번호: ");
        String password = scanner.nextLine();
        System.out.print("이메일: ");
        String email = scanner.nextLine();
        System.out.print("닉네임: ");
        String nickname = scanner.nextLine();

        if (userDao.isUsernameTaken(id)) {
            System.out.println("이미 사용 중인 아이디입니다. 다른 아이디를 사용해주세요.");
            return;
        }

        UserDto newuser = new UserDto(id, password, email, nickname);
        userDao.registerUser(newuser);
        System.out.println("회원가입 완료!");
    }

    // 2. 로그인
    private void handleUserLogin(Scanner scanner) throws SQLException {

        view.promptForLogin();
        System.out.print("아이디: ");
        String id = scanner.nextLine();
        System.out.print("비밀번호: ");
        String password = scanner.nextLine();



        if (userDao.loginUser(id, password)) {
            loggedInUserId = userDao.findUserId(id); // 로그인한 사용자 ID 저장
            System.out.println("로그인 성공!");
        } else {
            System.out.println("로그인 실패! 정보를 확인하세요.");
        }
    }

    // 2-1. 로그아웃 처리


    private void handleLogout() {
        if (loggedInUserId != null) {
            view.showSuccessMessage(loggedInUserId + " 님, 로그아웃되었습니다.");
            loggedInUserId = null;  // 로그아웃 처리
        } else {
            view.showErrorMessage("\n로그인 상태가 아닙니다.");
        }
    }



    // 3. 리뷰 관리

    private void handleReviewManagement(Scanner scanner) throws SQLException {


        view.showReviewMenu();
        int reviewChoice = scanner.nextInt();
        scanner.nextLine();

        switch (reviewChoice) {
            case 1 -> {
                System.out.print("영화 제목: ");
                String movieTitle = scanner.nextLine();
                System.out.print("영화 장르: ");
                String movieGenre = scanner.nextLine();

                System.out.print("평점(1~5): ");
                int rating = scanner.nextInt();
                scanner.nextLine();
                if (rating < 1 || rating > 5) { // 평점 유효성 검사
                    System.out.println("평점은 1~5 사이의 값이어야 합니다.");
                    return;
                }

                System.out.print("리뷰 내용: ");
                String reviewText = scanner.nextLine();


                ReviewDto review = new ReviewDto(0 , loggedInUserId , movieTitle ,movieGenre, rating , reviewText );
                reviewDao.addReview(review);
                System.out.println("\n리뷰 작성 완료!");
            }
            case 2 -> {
                System.out.print("조회할 영화 제목: ");
                String movieTitle = scanner.nextLine();
                reviewDao.getReviews(movieTitle);
            }
            case 3 -> {


                System.out.println("\n작성한 리뷰 목록을 조회합니다...");
                reviewDao.getReviewsByUserId(loggedInUserId);


                System.out.print("\n수정할 리뷰 번호를 입력하세요: ");
                int reviewChoiceToEdit = scanner.nextInt();
                scanner.nextLine(); // 개행 문자 제거


                System.out.print("새 평점(1~5): ");
                int newRating = scanner.nextInt();
                scanner.nextLine(); // 개행 문자 제거
                System.out.print("새 리뷰 내용: ");
                String newReviewText = scanner.nextLine();


                reviewDao.updateReview(new ReviewDto(reviewChoiceToEdit, loggedInUserId, null, null, newRating, newReviewText));
                System.out.println("\n리뷰 수정 완료!");


            }
            case 4 -> { // 리뷰 삭제

                String userId = "현재 로그인한 사용자 ID";

                System.out.print("\n삭제할 리뷰 번호를 입력하세요: ");
                int reviewId = scanner.nextInt();


                if (reviewDao.isUserReview(loggedInUserId,reviewId)) {

                    reviewDao.deleteReview(reviewId);

                } else {

                    System.out.println("\n본인의 리뷰만 삭제할 수 있습니다.");
                }


            }
            case 5 -> {

                System.out.println("\n전체 리뷰 목록을 조회합니다..");
                reviewDao.getAllReviews();
            }
            default -> System.out.println("\n올바른 번호를 입력해주세요.");
        }
    }
}

// Model - ReviewDao
package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ReviewDao {

    private final Connection connection;

    public ReviewDao(Connection connection) {
        this.connection = connection;
    }

    // 리뷰 작성
    public void addReview(ReviewDto review) throws SQLException {
        System.out.println("Adding review for user: " + review.getUserId());
        String sql = "INSERT INTO Reviews (user_id,movie_title, movie_genre, rating, review_text) VALUES (?,?,?,?,?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, review.getUserId()); // 사용자 ID
            pstmt.setString(2, review.getMovieTitle()); // 영화 제목
            pstmt.setString(3, review.getMovieGenre()); // 영화 장르
            pstmt.setInt(4, review.getRating()); // 평점
            pstmt.setString(5, review.getReviewText()); // 리뷰 내용
            pstmt.executeUpdate();
        }
    }


    public void getReviews(String movieTitle) throws SQLException {
        String sql = "SELECT r.id, r.rating, r.review_text, u.nickname FROM Reviews r JOIN Users u ON r.user_id = u.id WHERE r.movie_title = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, movieTitle);
            ResultSet rs = pstmt.executeQuery();

            if (!rs.next()) {
                System.out.println("\n이 영화에 대한 리뷰가 없습니다.");
                return;
            }

            do {

                System.out.println("----------------------------");
                System.out.printf("작성자: %s | 평점: %d\n", rs.getString("nickname"), rs.getInt("rating"));
                System.out.printf("리뷰 내용: %s\n", rs.getString("review_text"));
                System.out.println("----------------------------");
            } while (rs.next());
        }
    }



    // 리뷰 수정
    public void getReviewsByUserId(String userId) throws SQLException {
        String sql = "SELECT * FROM Reviews WHERE user_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, userId);  // user_id로 필터링
            ResultSet rs = pstmt.executeQuery();

            int count = 1;
            while (rs.next()) {
                int reviewId = rs.getInt("id");
                String movieTitle = rs.getString("movie_title");
                String movieGenre = rs.getString("movie_genre");
                int rating = rs.getInt("rating");
                String reviewText = rs.getString("review_text");


                System.out.println("\n==========================");
                System.out.println(count + ". 리뷰 번호 : " + reviewId);
                System.out.println("영화 제목: " + movieTitle);
                System.out.println("영화 장르: " + movieGenre);
                System.out.println("평점: " + rating);
                System.out.println("리뷰 내용: " + reviewText);
                System.out.println("\n==========================");
                count++;
            }


        }
    }

    public void updateReview(ReviewDto review) throws SQLException {
        String sql = "UPDATE Reviews SET rating = ?, review_text = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, review.getRating());
            pstmt.setString(2, review.getReviewText());
            pstmt.setInt(3, review.getId());
            pstmt.executeUpdate();
        }
    }

    // 리뷰 삭제
    public void deleteReview(int reviewId) throws SQLException {
        String sql = "DELETE FROM Reviews WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, reviewId);
            int rowsAffected = pstmt.executeUpdate();
            pstmt.executeUpdate();




// 리뷰 삭제 여부 확인
            if (rowsAffected > 0) {
                System.out.println("리뷰 삭제 완료!");
            } else {
                System.out.println("해당 리뷰 번호는 존재하지 않습니다.");
            }

        }
    }

    //////리뷰 삭제 (본인 확인)
    public boolean isUserReview(String userId, int reviewId) {
        String sql = "SELECT * FROM Reviews WHERE user_id = ? AND id = ?"; // user_id와 id가 일치하는지 확인

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, userId);  // 로그인한 사용자 ID
            pstmt.setInt(2, reviewId);   // 리뷰 ID
            ResultSet rs = pstmt.executeQuery();

            return rs.next();  // 일치하는 리뷰가 있으면 true, 없으면 false
        } catch (SQLException e) {
            System.out.println("리뷰 확인 중 오류 발생: " + e.getMessage());
            return false;
        }
    }



    // 모든 리뷰 조회
    public void getAllReviews() {
        String query = "SELECT * FROM Reviews";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            int count = 1;  // 리뷰 번호 출력용
            while (resultSet.next()) {
                int reviewId = resultSet.getInt("id");
                String userId = resultSet.getString("user_id");
                String movieTitle = resultSet.getString("movie_title");
                String movieGenre = resultSet.getString("movie_genre");
                int rating = resultSet.getInt("rating");
                String reviewText = resultSet.getString("review_text");

                // 리뷰 목록을 출력
                System.out.println("============================");
                System.out.println("리뷰 번호: " + reviewId);
                System.out.println("작성자: " + userId);
                System.out.println("영화 제목: " + movieTitle);
                System.out.println("영화 장르: " + movieGenre);
                System.out.println("평점: " + rating);
                System.out.println("리뷰 내용: " + reviewText);
                System.out.println("\n==========================");
                count++;
            }
        } catch (SQLException e) {
            System.out.println("전체 리뷰 조회 중 오류 발생: " + e.getMessage());

        }
    }
}



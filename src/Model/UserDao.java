// Model - UserDao
package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    private final Connection connection;

    public UserDao(Connection connection) {
        this.connection = connection;
    }

    // 회원가입
    public void registerUser(UserDto user) throws SQLException {
        String sql = "INSERT INTO Users (id, password, email, nickname) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, user.getId());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getNickname());
            pstmt.executeUpdate();
        }
    }
    // 중복확인
    public boolean isUsernameTaken(String username) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Users WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }


    // 로그인
    public boolean loginUser(String id, String password) throws SQLException {
        String sql = "SELECT * FROM Users WHERE id = ? AND password = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        }
    }

    // 사용자 ID 조회
    public String findUserId(String username) throws SQLException {
        String sql = "SELECT id FROM Users WHERE id = ?";


        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("id");
            } else {
                System.out.println("해당 사용자가 존재하지 않습니다: " + username);
                return null;
            }
        }catch (SQLException e) {
            System.out.println("사용자 ID 조회중 오류 발생 : " + e.getMessage());
            throw e;
        }

    }
}

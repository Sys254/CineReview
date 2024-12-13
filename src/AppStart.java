
import Controller.BoardController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AppStart {
    public static void main(String[] args) {

        Connection conn = getConnection();

        if (conn == null) {
            System.out.println("데이터베이스 연결에 실패했습니다.");
            return;
        }


        BoardController controller = new BoardController(conn);


        System.out.println("영화 리뷰 게시판 프로그램 시작!");
        controller.start();
    }


    private static Connection getConnection() {
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");


            String url = "jdbc:mysql://127.0.0.1:3306/miniproject";
            String username = "root";
            String password = "";

            // DB 연결
            return DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}


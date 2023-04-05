package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcUtil {

	static { // 클래스당 1번 초기화
		// 클래스 동적 바인딩
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			System.out.println("오라클 드라이버 바인딩 실패.");
			e.printStackTrace();
		}
	}
	
	public static void commit(Connection conn) {
		try {
			System.out.println("커밋 성공!");
			conn.commit();
		} catch (SQLException e) {
			System.out.println("커밋 실패!");
			e.printStackTrace();
		}
	}

	public static void rollback(Connection conn) {
		try {
			System.out.println("롤백 성공!");
			conn.rollback();
		} catch (SQLException e) {
			System.out.println("롤백 실패!");
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() {
		try {
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "ICIA", "1234");
			// setAutoCommit이 DB의 커밋 여부를 결정.
			// setAutoCommit의 기본값(true).
			conn.setAutoCommit(false);
			return conn;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public static void close(Connection conn) {
		try {
			if(conn != null) {conn.close();
			System.out.println("CONN 종료!");}
		} catch (SQLException e) {
			System.out.println("DB 접속 종료 실패!");
			e.printStackTrace();
		}

	}
	
	public static void close(ResultSet rs) {
		try {
			if(rs != null) {rs.close();
			System.out.println("RS 종료!");}
		} catch (SQLException e) {
			System.out.println("DB 접속 종료 실패!");
			e.printStackTrace();
		}

	}
	
	public static void close(PreparedStatement pstmt) {
		try {
			if(pstmt != null) {pstmt.close();
			System.out.println("PSTMT 종료!");}
		} catch (SQLException e) {
			System.out.println("DB 접속 종료 실패!");
			e.printStackTrace();
		}

	}
}

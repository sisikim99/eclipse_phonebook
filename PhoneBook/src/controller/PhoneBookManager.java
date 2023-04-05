package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

import bean.PhneCompanyInfo;
import bean.PhoneInfo;
import bean.PhoneUnivInfo;
import common.*;

public class PhoneBookManager implements INIT_MENU {
	Connection conn;
	Statement stmt;
	PreparedStatement pstmt;

	Scanner sc = new Scanner(System.in);

	// 메뉴 선택 입력 받아 phashSet에 추가.
	public void inputData(int menu) {
		ResultSet rs = null;

		// NORMAL
		String name;
		String phoneNumber;
		String kind;

		// UNIVERSITY
		String major;
		int year;

		// COMPANY
		String company;

		conn = JdbcUtil.getConnection();
		String sql;
		int res = 0;
		sql = "INSERT INTO PHONEMEMBER(NAME, PHONENUMBER, MAJOR, YEAR, COMPANY, KIND) VALUES(?, ?, ?, ?, ?, ?)";

		System.out.print("이름 입력 : ");
		name = sc.next();

		System.out.print("전화번호 입력 : ");
		phoneNumber = sc.next();

		try {
			pstmt = conn.prepareStatement(sql);

			pstmt.setNString(1, name);
			pstmt.setNString(2, phoneNumber);

			if (menu == NORMAL) {
				kind = "N";
				pstmt.setNull(3, java.sql.Types.NVARCHAR);
				pstmt.setNull(4, java.sql.Types.INTEGER);
				pstmt.setNull(5, java.sql.Types.NVARCHAR);
				pstmt.setNString(6, kind);
			}
			if (menu == UNIVERSITY) {
				kind = "U";
				System.out.println("전공 입력 : ");
				major = sc.next();
				System.out.println("학년 입력 : ");
				year = sc.nextInt();

				pstmt.setNString(3, major);
				pstmt.setInt(4, year);
				pstmt.setNull(5, java.sql.Types.NVARCHAR);
				pstmt.setNString(6, kind);
			}
			if (menu == COMPANY) {
				kind = "C";
				System.out.println("회사명 입력: ");
				company = sc.next();

				pstmt.setNull(3, java.sql.Types.NVARCHAR);
				pstmt.setNull(4, java.sql.Types.INTEGER);
				pstmt.setNString(5, company);
				pstmt.setNString(6, kind);
			}

			res = pstmt.executeUpdate();

			if (res != 0) {
				JdbcUtil.commit(conn);
				System.out.println("전화번호부 저장이 되었습니다.");

				JdbcUtil.close(conn);
				JdbcUtil.close(pstmt);
				JdbcUtil.close(rs);
			} else {
				JdbcUtil.rollback(conn);

				JdbcUtil.close(conn);
				JdbcUtil.close(pstmt);
				JdbcUtil.close(rs);

				System.out.println("전화번호부 저장에 실패했습니다.");
			}

		} catch (SQLException e) {
			JdbcUtil.rollback(conn);

			JdbcUtil.close(conn);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(rs);

			e.printStackTrace();
		}
	}

	// 입력 받은 이름 검색하기.
	public void searchData() {

		conn = JdbcUtil.getConnection();
		ResultSet rs = null;
		PhoneInfo p = new PhoneInfo(null, null);

		System.out.print("검색 이름 : ");
		String name = sc.next();

		try {
			stmt = conn.createStatement();

			String sql = "SELECT * FROM PHONEMEMBER WHERE NAME ='" + name + "'";
			rs = stmt.executeQuery(sql);

			if (rs.next()) {
				p.setName(rs.getString("NAME"));
				p.setPhoneNum(rs.getString("PHONENUMBER"));
				
				System.out.println(p);
				
				JdbcUtil.close(conn);
				JdbcUtil.close(pstmt);
				JdbcUtil.close(rs);

			} else {
				JdbcUtil.close(conn);
				JdbcUtil.close(pstmt);
				JdbcUtil.close(rs);

				System.out.println("해당하는 전화번호가 없습니다.");
			}

		} catch (Exception e) {
			JdbcUtil.rollback(conn);

			JdbcUtil.close(conn);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(rs);

			System.out.println("검색 예외!");

			e.printStackTrace();
		}
	}

	// 입력 받은 이름 삭제하기.
	public void deleteData() {
		conn = JdbcUtil.getConnection();
		ResultSet rs = null;

		int res = 0;
		String sql = "DELETE FROM PHONEMEMBER WHERE NAME = ?";

		System.out.println("삭제할 이름 : ");
		String name = sc.next();

		try {
			conn.prepareStatement(sql);
			pstmt = conn.prepareStatement(sql);
			pstmt.setNString(1, name);

			res = pstmt.executeUpdate();

			if (res != 0) {
				JdbcUtil.commit(conn);
				System.out.println("전화번호부 삭제가 되었습니다.");

				JdbcUtil.close(conn);
				JdbcUtil.close(pstmt);
				JdbcUtil.close(rs);
			} else {
				JdbcUtil.rollback(conn);
				System.out.println("전화번호부 삭제에 실패했습니다.");
			}
		} catch (Exception e) {
			JdbcUtil.rollback(conn);

			JdbcUtil.close(conn);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(rs);

			e.printStackTrace();
		}
	}

	public void showDataList() {
		conn = JdbcUtil.getConnection();
		ResultSet rs = null;
		ArrayList<PhoneInfo> pList = new ArrayList<>();

		System.out.println("===========출력 타입 설정===========");
		System.out.println("1. 전체 출력");
		System.out.println("2. 일반 출력");
		System.out.println("3. 학교 출력");
		System.out.println("4. 회사 출력");
		System.out.println("=================================");
		System.out.print("출력 타입 입력 : ");
		int menu = sc.nextInt();

		if (menu == 1) {
			String sql = "SELECT * FROM PHONEMEMBER";
			try {
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				while (rs.next()) {
					PhoneInfo allP = new PhoneInfo(null, null);

					allP.setName(rs.getNString("NAME"));
					allP.setPhoneNum(rs.getNString("PHONENUMBER"));

					pList.add(allP);
				}
			} catch (SQLException e) {
				JdbcUtil.rollback(conn);

				JdbcUtil.close(conn);
				JdbcUtil.close(pstmt);
				JdbcUtil.close(rs);

				e.printStackTrace();
			}

		} else if (menu == 2) {
			String sql = "SELECT * FROM PHONEMEMBER WHERE KIND = 'N'";
			try {
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				while (rs.next()) {
					PhoneInfo normalP = new PhoneInfo(null, null);
					normalP.setName(rs.getNString("NAME"));
					normalP.setPhoneNum(rs.getNString("PHONENUMBER"));

					pList.add(normalP);
				}
			} catch (SQLException e) {
				JdbcUtil.rollback(conn);

				JdbcUtil.close(conn);
				JdbcUtil.close(pstmt);
				JdbcUtil.close(rs);

				e.printStackTrace();
			}

		} else if (menu == 3) {
			String sql = "SELECT * FROM PHONEMEMBER WHERE KIND = 'U'";
			try {
				rs = stmt.executeQuery(sql);
				while (rs.next()) {
					PhoneUnivInfo univP = new PhoneUnivInfo(null, null, null, 0);
					univP.setName(rs.getNString("NAME"));
					univP.setPhoneNum(rs.getNString("PHONENUMBER"));
					univP.setMajor(rs.getNString("MAJOR"));
					univP.setYear(rs.getInt("YEAR"));

					pList.add(univP);
				}
			} catch (SQLException e) {
				JdbcUtil.rollback(conn);

				JdbcUtil.close(conn);
				JdbcUtil.close(pstmt);
				JdbcUtil.close(rs);

				e.printStackTrace();
				e.printStackTrace();
			}

		} else if (menu == 4) {
			String sql = "SELECT * FROM PHONEMEMBER WHERE KIND = 'C'";
			;
			try {
				rs = stmt.executeQuery(sql);
				while (rs.next()) {
					PhneCompanyInfo companyP = new PhneCompanyInfo(null, null, null);
					companyP.setName(rs.getNString("NAME"));
					companyP.setName(rs.getNString("PHONENUMBER"));
					companyP.setCompany(rs.getNString("COMPANY"));

					pList.add(companyP);
				}
			} catch (SQLException e) {
				JdbcUtil.rollback(conn);

				JdbcUtil.close(conn);
				JdbcUtil.close(pstmt);
				JdbcUtil.close(rs);

				e.printStackTrace();
			}

		}
		pList.stream().forEach(m -> System.out.println(m));
	}

	public void updateData() {
		conn = JdbcUtil.getConnection();
		ResultSet rs = null;

		int res = 0;
		String sql = "";

		System.out.print("수정할 이름 : ");
		String name = sc.next();

		System.out.println("==============수정 항목==============");
		System.out.println("1. 이름");
		System.out.println("2. 전화번호");
		System.out.println("3. 전공");
		System.out.println("4. 학년");
		System.out.println("5. 회사명");
		System.out.println("===================================");
		System.out.print("입력 : ");
		int menu = sc.nextInt();

		try {
			if (menu == U_NAME) {
				sql = "UPDATE PHONEMEMBER SET NAME = ? WHERE NAME = ?";
				System.out.println("수정할 내용 입력 : ");
				String u_name = sc.next();

				conn.prepareStatement(sql);
				pstmt = conn.prepareStatement(sql);

				pstmt.setNString(1, u_name);
				pstmt.setNString(2, name);
			} else if (menu == U_PHONENUMBER) {
				sql = "UPDATE PHONEMEMBER SET PHONENUMBER = ? WHERE NAME = ?";
				System.out.println("수정할 내용 입력 : ");
				String u_phoneNumber = sc.next();

				conn.prepareStatement(sql);
				pstmt = conn.prepareStatement(sql);
				conn.prepareStatement(sql);

				pstmt.setNString(2, u_phoneNumber);
				pstmt.setNString(3, name);
			} else if (menu == U_MAJOR) {
				sql = "UPDATE PHONEMEMBER SET MAJOR = ? WHERE NAME = ?";
				System.out.println("수정할 내용 입력 : ");
				String u_major = sc.next();

				conn.prepareStatement(sql);
				pstmt = conn.prepareStatement(sql);
				conn.prepareStatement(sql);

				pstmt.setNString(1, u_major);
				pstmt.setNString(2, name);
			}

			else if (menu == U_YEAR) {
				sql = "UPDATE PHONEMEMBER SET YEAR = ? WHERE NAME = ?";
				System.out.println("수정할 내용 입력 : ");
				int u_year = sc.nextInt();

				conn.prepareStatement(sql);
				pstmt = conn.prepareStatement(sql);
				conn.prepareStatement(sql);

				pstmt.setInt(2, u_year);
				pstmt.setNString(3, name);
			} else if (menu == U_COMPANY) {
				sql = "UPDATE PHONEMEMBER SET COMPANY = ? WHERE NAME = ?";

				System.out.println("수정할 내용 입력 : ");
				String u_company = sc.next();

				conn.prepareStatement(sql);
				pstmt = conn.prepareStatement(sql);
				conn.prepareStatement(sql);

				pstmt.setNString(1, "COMPANY");
				pstmt.setNString(2, u_company);
				pstmt.setNString(3, name);
			}

			res = pstmt.executeUpdate();

			if (res != 0) {
				JdbcUtil.commit(conn);
				System.out.println("전화번호부 수정이 되었습니다.");

				JdbcUtil.close(conn);
				JdbcUtil.close(pstmt);
				JdbcUtil.close(rs);
			} else {
				JdbcUtil.rollback(conn);
				System.out.println("전화번호부 수정에 실패했습니다.");
			}
		} catch (Exception e) {
			JdbcUtil.rollback(conn);

			JdbcUtil.close(conn);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(rs);

			e.printStackTrace();
		}
	}
}

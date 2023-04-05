package common;

import java.util.InputMismatchException;
import java.util.Scanner;
import controller.PhoneBookManager;
import exception.InputNotMatchException;

public class Menu implements INIT_MENU {
	Scanner sc = new Scanner(System.in);
	PhoneBookManager pbm = new PhoneBookManager();

	public void showMenu() throws InputNotMatchException {
		int i = 0;

		for (;;) {
			System.out.println("==============메뉴==============");
			System.out.println("1. 전화번호 입력");
			System.out.println("2. 전화번호 검색");
			System.out.println("3. 전화번호 수정");
			System.out.println("4. 전화번호 삭제");
			System.out.println("5. 전화번호 전체 출력");
			System.out.println("6. 프로그램 종료");
			System.out.println("===============================");
			
			try {
				i = InputCheck();
			} catch (Exception e) {
				showMenu();
			}
			
			if (i == INIT_MENU.INSERT)
				selectGroup();
			else if (i == INIT_MENU.SELECT)
				pbm.searchData();
			else if (i == UPDATE)
				pbm.updateData();
			else if (i == INIT_MENU.DELETE)
				pbm.deleteData();
			else if (i == INIT_MENU.PRINT)
				pbm.showDataList();
			else
				System.out.println("프로그램 종료!");
				return;
		}
	}

	public void selectGroup() throws InputNotMatchException {
		int s_num; // 그룹 메뉴 선택 변수

		System.out.println("데이터 입력을 시작합니다.");
		System.out.println("1. 일반, 2. 대학, 3. 회사");
		System.out.print("선택 >> ");
		
		try {
			s_num = sc.nextInt();
		} catch (InputMismatchException e) {
			sc.nextLine();
			System.out.println("정수를 입력해주세요.");
			selectGroup();
			return;
		}

		if (s_num >= 4) {
			InputNotMatchException e = new InputNotMatchException(s_num);
			showMenu();
			return;
		}

		pbm.inputData(s_num);
	}

	public int InputCheck() throws InputNotMatchException {
		int s_num = 0;
		System.out.print("입력 : ");
		
		try {
			s_num = sc.nextInt();
		} catch (InputMismatchException e) {
			sc.nextLine();
			System.out.println("정수를 입력해주세요.");
			showMenu();
			return 0;
		}
		
		if (s_num > 6 || s_num <=0) {
			System.out.println("다른 수");
			throw new InputNotMatchException(s_num);
		}
		
		return s_num;
	}

}

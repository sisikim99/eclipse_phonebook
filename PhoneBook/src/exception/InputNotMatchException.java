package exception;

// 잘못된 메뉴 입력 시 오류 발생.
public class InputNotMatchException extends Exception{
	public InputNotMatchException(int menu_num) {
		System.out.println(menu_num + "에 해당하는 선택은 존재하지 않습니다. \n메뉴 선택을 처음부터 다시 진행합니다.");
	}
}

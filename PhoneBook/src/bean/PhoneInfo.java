package bean;

import java.math.BigDecimal;

public class PhoneInfo {
	// 이름, 전화번호, 생년월일
	private String name;
	private String phoneNum;
	
	public PhoneInfo(String name, String phoneNum) {
		this.name = name;
		this.phoneNum = phoneNum;
	}

	public String getName() {
		return name;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	@Override
	public String toString() {
		String str = "==================================";
		return str + "\n이름 : " + name + "\n전화번호 : " + phoneNum;
	}
	
	@Override
	public int hashCode() {
//		BigDecimal bDc1= new BigDecimal(phoneNum);
//		return bDc1.intValue();
		return name.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		
		PhoneInfo cmpP = (PhoneInfo)obj;
		
		if(this.phoneNum.equals(cmpP.phoneNum)) {
			System.out.println("이미 저장된 이름입니다.");
			return true;
		}
		
		return false;
	}
}

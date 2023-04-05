package bean;

public class PhoneUnivInfo extends PhoneInfo{
	// 필드 선언
	private String major; // 학과
	private int year; // 학년
	
	public PhoneUnivInfo(String name, String phoneNum, String major, int year) {
		super(name, phoneNum);
		this.major = major;
		this.year = year;
	}
	
	
	
	public String getMajor() {
		return major;
	}



	public int getYear() {
		return year;
	}



	public void setMajor(String major) {
		this.major = major;
	}



	public void setYear(int year) {
		this.year = year;
	}



	@Override
	public String toString() {
		return super.toString() + "\n전공 : " + major + "\n학년 : " + year;
	}
}

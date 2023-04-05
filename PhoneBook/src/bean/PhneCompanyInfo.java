package bean;

public class PhneCompanyInfo extends PhoneInfo{
	private String company; // 회사
	
	public PhneCompanyInfo(String name, String phoneNum, String company) {
		super(name, phoneNum);
		this.company = company;
	}
	
	
	
	public String getCompany() {
		return company;
	}



	public void setCompany(String company) {
		this.company = company;
	}



	@Override
	public String toString() {
		
		return super.toString() + "\n회사명 : " + company;
	}
}

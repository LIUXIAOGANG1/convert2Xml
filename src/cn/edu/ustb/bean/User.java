package cn.edu.ustb.bean;

public class User {
	private String name;
	private String passWord;
	private String returnCode;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	
	public String getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}
	@Override
	public String toString() {
		return "User [name=" + name + ", passWord=" + passWord
				+ ", returnCode=" + returnCode + "]";
	}
}

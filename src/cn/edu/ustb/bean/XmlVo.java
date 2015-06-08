package cn.edu.ustb.bean;

public class XmlVo {
	public String xmlPath;
	public String returnCode;
	public String getXmlPath() {
		return xmlPath;
	}
	public void setXmlPath(String xmlPath) {
		this.xmlPath = xmlPath;
	}
	public String getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}
	@Override
	public String toString() {
		return "XmlVo [xmlPath=" + xmlPath + ", returnCode=" + returnCode + "]";
	}
}

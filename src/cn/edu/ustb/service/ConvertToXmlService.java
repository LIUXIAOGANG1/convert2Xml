package cn.edu.ustb.service;

import cn.edu.ustb.exception.DocumentFormatException;
import cn.edu.ustb.bean.User;

public interface ConvertToXmlService{
	/*�û���½*/
	public Integer confirmUser(User user);
	
	/*���ϴ����ĵ�ת��ΪXML*/
	public String convert2Xml(String fileName) throws DocumentFormatException;
	
	/*��ת����XML�ĵ���ԭ�ļ�������*/
	public void exportXml(String fileName, String xml);
}

package cn.edu.ustb.service;

import cn.edu.ustb.exception.DocumentFormatException;
import cn.edu.ustb.bean.User;

public interface ConvertToXmlService{
	/*用户登陆*/
	public Integer confirmUser(User user);
	
	/*把上传的文档转换为XML*/
	public String convert2Xml(String fileName) throws DocumentFormatException;
	
	/*把转换的XML文档按原文件名导出*/
	public void exportXml(String fileName, String xml);
}

package cn.edu.ustb.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.commons.lang3.StringUtils;

import cn.edu.ustb.bean.User;
import cn.edu.ustb.bean.XmlVo;
import cn.edu.ustb.exception.DocumentFormatException;
import cn.edu.ustb.service.ConvertToXmlService;

import com.google.gson.Gson;

@Controller
public class ConvertToXmlController {
	@Resource
	private ConvertToXmlService convertToXmlService;

	@RequestMapping(value = "/")
    public String index(Model model) {
		return "index";
    }
	
	@RequestMapping("/login.html")
	public String login(Model model, User user) {
		String name = user.getName();
		String passWord = user.getPassWord();
		if(StringUtils.isBlank(name)){
			model.addAttribute("msg", "用户名不能为空！");
			return "index";
		}
		
		if(StringUtils.isBlank(passWord)){
			model.addAttribute("msg", "密码不能为空！");
			return "index";
		}
		
		Integer count = convertToXmlService.confirmUser(user);
		if(count <= 0){
			model.addAttribute("user", user);
			model.addAttribute("msg", "此用户不存在或者输入错误！");
			return "index";
		}
		return "userPage";
	}
	
	@RequestMapping("/readXml.html")
	public String readXml(Model model) {
		model.addAttribute("readXml", new XmlVo());
		return "readXml";
	}
	
	@ResponseBody
	@RequestMapping("/deal.html")
	public String convertToXml(XmlVo xmlVo) {
		String filePath = xmlVo.getXmlPath();
		if(StringUtils.isBlank(filePath)){
			//没有选择转换文件
			xmlVo.setReturnCode("2");
			return new Gson().toJson(xmlVo);
		}
		//为截取文件名做准备
		int index = filePath.replaceAll("\\\\","/").lastIndexOf("/");
		String fileName = filePath.substring(index+1, filePath.length()-5);
		try {
			String xml = convertToXmlService.convert2Xml(filePath);
			convertToXmlService.exportXml(fileName, xml);
		} catch (DocumentFormatException e) {
			//转换文件格式异常
			xmlVo.setReturnCode("1");
			return new Gson().toJson(xmlVo);
		}
		//转换成功
		xmlVo.setReturnCode("0");
		return new Gson().toJson(xmlVo);
	}
}

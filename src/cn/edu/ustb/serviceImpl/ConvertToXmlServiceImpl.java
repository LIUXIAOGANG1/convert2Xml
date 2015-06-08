package cn.edu.ustb.serviceImpl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.swing.filechooser.FileSystemView;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import cn.edu.ustb.bean.Control;
import cn.edu.ustb.bean.User;
import cn.edu.ustb.exception.DocumentFormatException;
import cn.edu.ustb.service.ConvertToXmlService;

@Service
public class ConvertToXmlServiceImpl implements ConvertToXmlService {
	@Resource
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public String convert2Xml(String filePath) throws DocumentFormatException {
		String allXml = null;
		try {
			SAXReader reader = new SAXReader();

			//new File(filePath)解决中文路劲不能解析问题
			Document dom = reader.read(new File(filePath));
			Element root = dom.getRootElement();
			Element specification = (Element) root.elementIterator(
					"specification").next();
			Element decomposition = (Element) specification.elements(
					"decomposition").get(0);
			Element processControlElements = (Element) decomposition.elements(
					"processControlElements").get(0);
			List vertexList = processControlElements.elements();
			String outputName = "";
			Control outputControl = new Control();
			String inputName = "";
			Control inputControl = new Control();
			List linkControlList = new ArrayList();
			List containElemList = new ArrayList();
			List linkElemList = new ArrayList();
			Control linkControl = null;
			for (Iterator iterator = vertexList.iterator(); iterator.hasNext();) {
				Object ele = iterator.next();
				Element eleParam = (Element) ele;
				if (eleParam.getName().equals("inputCondition")) {
					outputControl.setName(eleParam.attributeValue("id")
							.toString());
					outputName = eleParam.attributeValue("id").toString();
				}
				if (eleParam.getName().equals("task")) {
					linkControl = new Control();
					linkControl.setName(eleParam.attributeValue("id")
							.toString());
					linkControlList.add(linkControl);
					linkElemList.add(eleParam.attributeValue("id").toString());
				}
				if (eleParam.getName().equals("outputCondition")) {
					inputControl.setName(eleParam.attributeValue("id")
							.toString());
					inputName = eleParam.attributeValue("id").toString();
				}
				if (eleParam.getName().equals("condition"))
					containElemList.add(eleParam.attributeValue("id")
							.toString());
			}

			Element layout2 = (Element) root.elementIterator("layout").next();
			Element specification2 = (Element) layout2.elementIterator(
					"specification").next();
			Element net2 = (Element) specification2.elements("net").get(0);
			List containerList = net2.elements("container");
			Control containControl = null;
			List containList = new ArrayList(containerList.size());
			List linkList = new ArrayList();
			for (Iterator iterator1 = containerList.iterator(); iterator1
					.hasNext();) {
				Object ele = iterator1.next();
				containControl = new Control();
				Element eleParam = (Element) ele;
				if (eleParam.attributeValue("id").equals(outputName)) {
					outputControl.setX(eleParam.element("vertex")
							.element("attributes").element("bounds")
							.attributeValue("x"));
					outputControl.setY(eleParam.element("vertex")
							.element("attributes").element("bounds")
							.attributeValue("y"));
				} else if (eleParam.attributeValue("id").equals(inputName)) {
					inputControl.setX(eleParam.element("vertex")
							.element("attributes").element("bounds")
							.attributeValue("x"));
					inputControl.setY(eleParam.element("vertex")
							.element("attributes").element("bounds")
							.attributeValue("y"));
				} else if (linkElemList.contains(eleParam.attributeValue("id"))) {
					containControl.setName(eleParam.attributeValue("id"));
					containControl.setX(eleParam.element("vertex")
							.element("attributes").element("bounds")
							.attributeValue("x"));
					containControl.setY(eleParam.element("vertex")
							.element("attributes").element("bounds")
							.attributeValue("y"));
					linkList.add(containControl);
				} else {
					containControl.setName(eleParam.attributeValue("id"));
					containControl.setX(eleParam.element("vertex")
							.element("attributes").element("bounds")
							.attributeValue("x"));
					containControl.setY(eleParam.element("vertex")
							.element("attributes").element("bounds")
							.attributeValue("y"));
					containList.add(containControl);
				}
			}

			for (Iterator iterator2 = linkControlList.iterator(); iterator2
					.hasNext();) {
				Control linkParam = (Control) iterator2.next();
				for (Iterator iterator3 = linkList.iterator(); iterator3
						.hasNext();) {
					Control containParam = (Control) iterator3.next();
					if (containParam.getName().equals(linkParam.getName())) {
						linkParam.setX(containParam.getX());
						linkParam.setY(containParam.getY());
					}
				}
			}

			String startElem = startPlace(outputControl);
			StringBuffer midEleStrBuffer = new StringBuffer();
			Control controlParam;
			for (Iterator iterator4 = containList.iterator(); iterator4
					.hasNext(); midEleStrBuffer
					.append(endPlace(controlParam)))
				controlParam = (Control) iterator4.next();

			String linkStrBuffer = createTransition(linkControlList);
			String endElem = startPlace(inputControl);
			Element layout = (Element) root.elementIterator("layout").next();
			Element spec = (Element) layout.elements("specification").get(0);
			Element net = (Element) spec.elements("net").get(0);
			List flowList = net.elements("flow");
			StringBuffer linkRefStrBuffer = new StringBuffer();
			String refArray[];
			for (Iterator iterator5 = flowList.iterator(); iterator5.hasNext(); linkRefStrBuffer
					.append(createArc(refArray))) {
				Object obj = iterator5.next();
				Element eleParam = (Element) obj;
				refArray = new String[2];
				refArray[0] = eleParam.attributeValue("source");
				refArray[1] = eleParam.attributeValue("target");
			}

			StringBuffer xmlStart = new StringBuffer(
					"<?xml version='1.0' encoding='utf-8'?><pnml><net id='Net-One' type='P/T net'><token id='Default' enabled='true' red='0' green='0' blue='0'/>");
			StringBuffer xmlEnd = new StringBuffer("</net></pnml>");
			allXml = xmlStart.append(startElem).append(midEleStrBuffer)
					.append(endElem).append(linkStrBuffer)
					.append(linkRefStrBuffer).append(xmlEnd).toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DocumentFormatException();
		}
		return allXml;
	}

	@Override
	public void exportXml(String fileName, String xml){
		PrintWriter out = null;
		try {
			/* 获得桌面路劲 */
			FileSystemView fileSystemView = FileSystemView.getFileSystemView();
			File deskFile = fileSystemView.getHomeDirectory();

			StringBuffer deskPath = new StringBuffer(deskFile.getPath());
			String outPath = deskPath.append("\\").append(fileName).append(".xml").toString();

			/* 导出到桌面 */
			File outFile = new File(outPath);
			out = new PrintWriter(new FileOutputStream(outFile), true);

			out.write(xml);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String startPlace(Control control) {
		String ele = (new StringBuilder("<place id='"))
				.append(control.getName())
				.append("'><graphics><position x='")
				.append(control.getX())
				.append("' y='")
				.append(control.getY())
				.append("'/></graphics><name><value>")
				.append(control.getName())
				.append("</value><graphics><offset x='0.0' y='0.0'/></graphics></name><initialMarking><value>0</value><graphics><offset x='0.0' y='0.0'/></graphics></initialMarking><capacity><value>0</value></capacity></place>")
				.toString();
		return ele;
	}

	public static String endPlace(Control control) {
		String ele = (new StringBuilder("<place id='"))
				.append(control.getName())
				.append("'><graphics><position x='")
				.append(control.getX())
				.append("' y='")
				.append(control.getY())
				.append("'/></graphics><name><value>")
				.append(control.getName())
				.append("</value><graphics><offset x='0.0' y='0.0'/></graphics></name><initialMarking><value>0</value><graphics><offset x='0.0' y='0.0'/></graphics></initialMarking><capacity><value>0</value></capacity></place>")
				.toString();
		return ele;
	}
	
	public static String createTransition(List linkControlList) {
		StringBuffer linkBuffer = new StringBuffer();
		Control control;
		for (Iterator iterator = linkControlList.iterator(); iterator.hasNext(); linkBuffer
				.append((new StringBuilder("<transition id='"))
						.append(control.getName())
						.append("'><graphics><position x='")
						.append(control.getX())
						.append("' y='")
						.append(control.getY())
						.append("'/></graphics><name><value>")
						.append(control.getName())
						.append("</value><graphics><offset x='-5.0' y='35.0'/></graphics></name><orientation><value>0</value></orientation><rate><value>1.0</value></rate><timed><value>true</value></timed><infiniteServer><value>false</value></infiniteServer><priority><value>1</value></priority></transition>")
						.toString()))
			control = (Control) iterator.next();

		return linkBuffer.toString();
	}
	
	public static String createArc(String linkRefArray[]) {
		String ele = (new StringBuilder("<arc id='"))
				.append(linkRefArray[0])
				.append(" TO ")
				.append(linkRefArray[1])
				.append("' source='")
				.append(linkRefArray[0])
				.append("' target='")
				.append(linkRefArray[1])
				.append("'><graphics/><inscription><value>1</value><graphics/></inscription><tagged><value>false</value></tagged><arcpath id='000' x='120' y='177' curvePoint='false'/><arcpath id='001' x='120' y='177' curvePoint='false'/><type value='normal'/></arc>")
				.toString();
		return ele;
	}

	@Override
	public Integer confirmUser(User user) {
		String sql = "SELECT COUNT(*) FROM user WHERE name=? AND passWord=?";
		Object[] args = new Object[]{user.getName(), user.getPassWord()};
		return jdbcTemplate.queryForInt(sql, args);
	}
}

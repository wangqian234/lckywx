package com.mvc.service.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.service.LoginService;
import com.mvc.dao.LoginDao;
import com.mvc.entityReport.ReceiveXmlEntity;

@Service("loginServiceImpl")
public class LoginServiceImpl implements LoginService {
	
	@Autowired
	LoginDao loginDao;
	
	public String wechatProcess(String xml){
		
		xml = "<xml><ToUserName><![CDATA[gh_680bdefc8c5d]]></ToUserName><FromUserName><![CDATA[oIDrpjqASyTPnxRmpS9O_ruZGsfk]]></FromUserName>";
		xml = xml + "<CreateTime>1359028446</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[你好]]></Content></xml>";
		 
		/** 解析xml数据 */
		ReceiveXmlEntity xmlEntity = getMsgEntity(xml);
		
		String result = "这是来自小仙女的一条消息";
		
		result = formatXmlAnswer(xmlEntity.getFromUserName(), xmlEntity.getToUserName(), result);
		
		return result;
	}
	
	public ReceiveXmlEntity getMsgEntity(String strXml){
		ReceiveXmlEntity msg = null; 
		try {
			if (strXml.length() <= 0 || strXml == null)
				return null;
			 
			// 将字符串转化为XML文档对象
			Document document = DocumentHelper.parseText(strXml);
			// 获得文档的根节点
			Element root = document.getRootElement();
			// 遍历根节点下所有子节点
			Iterator<?> iter = root.elementIterator();
			
			// 遍历所有结点
			msg = new ReceiveXmlEntity();
			//利用反射机制，调用set方法
			//获取该实体的元类型
			Class<?> c = Class.forName("com.mvc.entityReport.ReceiveXmlEntity");
			msg = (ReceiveXmlEntity)c.newInstance();//创建这个实体的对象
			
			while(iter.hasNext()){
				Element ele = (Element)iter.next();
				//获取set方法中的参数字段（实体类的属性）
				Field field = c.getDeclaredField(ele.getName());
				//获取set方法，field.getType())获取它的参数数据类型
				Method method = c.getDeclaredMethod("set"+ele.getName(), field.getType());
				//调用set方法
				method.invoke(msg, ele.getText());
			}
		} catch (Exception e) {
			System.out.println("xml 格式异常: "+ strXml);
			e.printStackTrace();
		}
		return msg;
	}
	
	/**
	 * 封装文字类的返回消息
	 * @param to
	 * @param from
	 * @param content
	 * @return
	 */
	public String formatXmlAnswer(String to, String from, String content) {
		StringBuffer sb = new StringBuffer();
		Date date = new Date();
		sb.append("<xml><ToUserName><![CDATA[");
		sb.append(to);
		sb.append("]]></ToUserName><FromUserName><![CDATA[");
		sb.append(from);
		sb.append("]]></FromUserName><CreateTime>");
		sb.append(date.getTime());
		sb.append("</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[");
		sb.append(content);
		sb.append("]]></Content><FuncFlag>0</FuncFlag></xml>");
		return sb.toString();
	}

}

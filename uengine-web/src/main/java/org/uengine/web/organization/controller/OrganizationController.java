/**
 * 
 */
/*
 * Copyright yysvip.tistory.com.,LTD.
 * All rights reserved.
 * 
 * This software is the confidential and proprietary information
 * of yysvip.tistory.com.,LTD. ("Confidential Information").
 */
package org.uengine.web.organization.controller; 

import java.io.StringWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.uengine.web.login.vo.LoginVO;
import org.uengine.web.organization.service.OrganizationService;
import org.uengine.web.organization.vo.OrganizationVO;
import org.uengine.web.organization.vo.UserVO;
import org.uengine.web.util.CommonUtil;
import org.w3c.dom.Document;

import com.google.gson.Gson;

@Controller public class OrganizationController {

	private Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name = "organizationService")
	private OrganizationService organizationService;
	
	private static Gson gson = new Gson();
	
	@RequestMapping(value="/organization/popup/approvalLineEditor.do")
	public ModelAndView approvalLineEditor(LoginVO sessionVO, HttpServletRequest request) throws Exception{
        ModelAndView mv = new ModelAndView("/organization/popup/approvalLineEditor");
        
        return mv;
    }
	
	@RequestMapping(value="/organization/ajax/selectOrganizationTreeByComCode.do")
	public void selectOrganizationChart(LoginVO sessionVO, HttpServletRequest request, HttpServletResponse response) throws Exception{

		String comCode = sessionVO.getComCode();
		List<OrganizationVO> organizationChartList = organizationService.selectOrganizationTreeByComCode(comCode);
		
//		Iterator<OrganizationVO> iter = organizationChartList.iterator();
//		int i = 1;
//		while ( iter.hasNext() ) {
//			OrganizationVO vo = iter.next();
//			vo.setLft(i);
//			vo.setRgt(i+organizationChartList.size());
//			i++;
//		}
		
		List treeList = CommonUtil.getTreeObjectByListForJSTree(organizationChartList, "level", "partCode", "partName", 1, "#", "objType");
		
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().print(gson.toJson(treeList));
    }
	
	@RequestMapping(value="/organization/ajax/selectUserListByPartCode.do")
	public void selectUserListByPartCode(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		String partCode = request.getParameter("partCode");
		List<UserVO> userList = organizationService.selectUserListByPartCode(partCode);
		
		for ( int i = 0; i < userList.size() && i % 2 == 0; i++) {
			userList.get(i).setAccessable(true);
		}
		
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(gson.toJson(userList));
	}
	

	@RequestMapping(value = "/organization/get/xml/organization/chart/{comCode}")
	public void getXmlOrganizationChart(HttpServletResponse response, @PathVariable String comCode) throws Exception {
		
		response.setContentType("text/xml; charset=UTF-8");
		response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
		response.setHeader("Pragma","no-cache"); //HTTP 1.0
		response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
		
		Document xmlDoc = organizationService.createOrgChartXmlDocument(comCode);
		
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
		
		StringWriter writer = new StringWriter();
		transformer.transform(new DOMSource(xmlDoc), new StreamResult(writer));
		
		String result = writer.getBuffer().toString();
		System.out.println(result);
		response.getWriter().write(result);
	}
	
	@RequestMapping(value = "/organization/get/xml/group/chart/{comCode}")
	public void getXmlGroupChart(HttpServletResponse response, @PathVariable String comCode) throws Exception {
		
		response.setContentType("text/xml; charset=UTF-8");
		response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
		response.setHeader("Pragma","no-cache"); //HTTP 1.0
		response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
		
		Document xmlDoc = organizationService.createGroupChartXmlDocument(comCode);
		
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
		
		StringWriter writer = new StringWriter();
		transformer.transform(new DOMSource(xmlDoc), new StreamResult(writer));
		
		String result = writer.getBuffer().toString();
		System.out.println(result);
		response.getWriter().write(result);
	}
	
	@RequestMapping(value = "/organization/get/xml/role/chart/{comCode}")
	public void getXmlRoleChart(HttpServletResponse response, @PathVariable String comCode) throws Exception {
		
		response.setContentType("text/xml; charset=UTF-8");
		response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
		response.setHeader("Pragma","no-cache"); //HTTP 1.0
		response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
		
		Document xmlDoc = organizationService.createRoleChartXmlDocument(comCode);
		
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
		
		StringWriter writer = new StringWriter();
		transformer.transform(new DOMSource(xmlDoc), new StreamResult(writer));
		
		String result = writer.getBuffer().toString();
		System.out.println(result);
		response.getWriter().write(result);
	}
	

}

package com.hncis.common.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hncis.businessVehicles.vo.BgabGascbv01Dto;
import com.hncis.common.message.HncisMessageSource;
import com.hncis.common.vo.BgabGascZ011Dto;
import com.hncis.shuttleBus.vo.BgabGascsb01;
import com.hncis.taxi.vo.BgabGasctx04;
import com.hncis.trafficTicket.vo.BgabGascTm01;

// TODO: Auto-generated Javadoc
/**
 * The Class Submit. - 메일 주소, body 설정 후 메일 발송하는 클래스
 */
public class Submit {
    private transient static Log logger = LogFactory.getLog(Submit.class.getClass());
	
	/** The area. - 운영인지, 개발인지 판단하는 변수 */
	private static String area = StringUtil.getSystemArea().toUpperCase();
	
	/** The Constant sPathReal. - 운영서버 경로*/
	private static final String sPathReal = ".cloud-gas.com/";
	
	/** The Constant sPathDev. - 개발서버 경로*/
//	private static final String sPathDev = "http://tstgasc.hyundai-brasil.com/gasc/";
	private static final String sPathDev = ".cloud.com:8080/";

//	private static final String sTestMail = "TIETEAM@HYUNDAI.COM";
	private static final String sTestMail = "tobeplain08@naver.com";

	private static final String hostMail = "hncis@hncis.co.kr";
	private static final String strLocal = "LOCAL";
	private static final String strTest = "TEST";
	private static final String strReal = "REAL";
	private static final String strHttp = "http://";
	
	private static final String textHead = " <table cellpadding='0' cellspacing='0'>"
			+ "<tr><td style='width:710px; background-color:#f8f8f8; border:1px solid #bebfc4'>"
			+ "<table cellpadding='0' cellspacing='0'>"
			+ "<tr><td style='width:690px; height:40px; background-color:#004b8e; padding-left:20px;'>"
			+ "</td></tr>"
			+ "<tr><td style='padding:20px;'><table cellpadding='0' cellspacing='0'>"
			+ "<tr> <td style='background-color:#FFF; border:1px solid #dddde5; padding:20px;'>"
			+ "<table cellpadding='0' cellspacing='0'>"
			+ "<tr><td style='font-size:12px; width:670px; color:#000; line-height:130%; font-family:Verdana, Geneva, sans-serif;'>";
	
	/**
	 * 신청 메일 발송
	 */
	public static boolean sendEmail(String a1, String a2, String a3, String mailAdr, String func, String title, String corp_cd){
		try{
			
			String from    = hostMail;
			
			String mailto  = "";
			String sPath =  "";
			
			if(area.equals(strLocal) || area.equals(strTest)){
				mailto  = sTestMail;
				sPath = strHttp+corp_cd+sPathDev;
			}else{
				mailto  = mailAdr;
				sPath = strHttp+corp_cd+sPathReal;
			}
			
			String subject = HncisMessageSource.getMessage("MAIL.0004");
			
			String text = textHead;
					
				text	+= HncisMessageSource.getMessage("MAIL.0004") + "<br /><br />";
				
				text	+= HncisMessageSource.getMessage("MAIL.0005") + " : "+title+"<br />";
				text	+= HncisMessageSource.getMessage("MAIL.0006") + " : "+ a1 + " " + a2+"<br /><br /><br />";
				text	+= HncisMessageSource.getMessage("MAIL.0007") + "<br /><br />";
				
				text	+= "<a href='"+sPath+"' target='_blank'>" + HncisMessageSource.getMessage("MAIL.0008") + "</a>";
				
				text	+= "</td></tr></table></td></tr></table></td></tr></table></td></tr></table>";
				
				logger.info(text);
				SendMail oMail = new SendMail();
				if(area.equals(strReal)){
					oMail.sendMail(mailto, from, subject, text, 1, false);
				}
				
				
			
		}catch (Exception ex){
			logger.error("messege", ex);
		}
		return true;
	}//end method
	
	/**
	 * 반려 메일 발송
	 */
	public static boolean returnEmail(String a1, String a2, String a3, String func, String title, String rtnText, String corp_cd){
		try{
			
			String from    = hostMail;
			
			String mailto  = "";
			String sPath =  "";
			
			if(area.equals(strLocal) || area.equals(strTest)){
				mailto  = sTestMail;
				sPath = strHttp+corp_cd+sPathDev;
			}else{
				mailto  = a3;
				sPath = strHttp+corp_cd+sPathReal;
			}
			
			String subject = HncisMessageSource.getMessage("MAIL.0012");

			String text = textHead;
					
				text	+= HncisMessageSource.getMessage("MAIL.0012") + "<br /><br />";
				
				text	+= HncisMessageSource.getMessage("MAIL.0005") + " : " + title + "<br />";
				text	+= HncisMessageSource.getMessage("MAIL.0010") + " : " + a1 + " " + a2 + "<br />";
				text	+= HncisMessageSource.getMessage("MAIL.0011") + " : " + rtnText + "<br /><br />";
				text	+= "<a href='"+sPath+"' target='_blank'>" + HncisMessageSource.getMessage("MAIL.0008") + "</a>";
				
				text	+= "</td></tr></table></td></tr></table></td></tr></table></td></tr></table>";
			
			SendMail oMail = new SendMail();
			if(area.equals(strReal)){
				oMail.sendMail(mailto, from, subject, text, 1, false);
			}
			
		}catch (Exception ex){
			logger.error("messege", ex);
		}
		return true;
	}
	
	/**
	 * 대기상태로 변경시 메일 발송
	 */
	public static boolean sendStandByConfirmEmail(String fromEeno, String fromStepNm, String toEeno, String func, String title, String corp_cd){
		try{
			
			String from    = hostMail;
			
			String mailto  = "";
			String sPath =  "";
			
			if(area.equals(strLocal) || area.equals(strTest)){
				mailto  = sTestMail;
				sPath = strHttp+corp_cd+sPathDev;
			}else{
				mailto  = toEeno;
				sPath = sPathReal;
				sPath = strHttp+corp_cd+sPathReal;
			}
			
			String subject = HncisMessageSource.getMessage("MAIL.0009");

			String text = textHead;
					
				text	+= HncisMessageSource.getMessage("MAIL.0009") + "<br /><br />";
				text	+= HncisMessageSource.getMessage("MAIL.0005") + " : " + title + "<br />";
				text	+= HncisMessageSource.getMessage("MAIL.0010") + " : " + fromEeno + " " + fromStepNm + "<br /><br /><br />";
				text	+= HncisMessageSource.getMessage("MAIL.0007") + "<br /><br />";
				text	+= "<a href='"+sPath+"' target='_blank'>" + HncisMessageSource.getMessage("MAIL.0008") + "</a>";
				
				text	+= "</td></tr></table></td></tr></table></td></tr></table></td></tr></table>";
				SendMail oMail = new SendMail();
				if(area.equals(strReal)){
					oMail.sendMail(mailto, from, subject, text, 1, false);
				}
		}catch (Exception ex){
			logger.error("messege", ex);
		}
		return true;
	}//end method
	
	/**
	 * submit confirm mail. - 결재자가 컨펌 시 메일 발송하는 메서드
	 * Rejecting docuemnt, it send mail.
	 * @param a1 - fromEeno 보낸사람 이름
	 * @param a2 - fromStepNm 보낸사람 직급
	 * @param a3 - toEeno 받는사람 사번
	 * @param title the title - 제목 
	 * @param rtnText the reject reason text - 결재가 입력 리젝 사유, 
	 * @return true, if successful - 리턴 값
	 */
	public static boolean confirmEmail(String a1, String a2, String a3, String title){
		try{
			
			String from    = hostMail;
			
			String mailto  = "";
			String sPath =  "";
			
			if(area.equals(strLocal) || area.equals(strTest)){
				mailto  = sTestMail;
				//mailto  = a3;
				sPath = sPathDev;
			}else{
				mailto  = a3;
				sPath = sPathReal;
			}
			
			String subject = "Confirmed request in HMB GA Support Center.";
			
			String text = " <table cellpadding='0' cellspacing='0'>"
						+ "<tr><td style='width:710px; background-color:#f8f8f8; border:1px solid #bebfc4'>"
						+ "<table cellpadding='0' cellspacing='0'>"
						+ "<tr><td style='width:690px; height:40px; background-color:#004b8e; padding-left:20px;'>"
						+ " <img src='"+sPath+"images/mail_logo.jpg' />"
						+ "</td></tr>"
						+ "<tr><td style='padding:20px;'><table cellpadding='0' cellspacing='0'>"
						+ "<tr> <td style='background-color:#FFF; border:1px solid #dddde5; padding:20px;'>"
						+ "<table cellpadding='0' cellspacing='0'>"
						+ "<tr><td style='font-size:12px; width:670px; color:#000; line-height:130%; font-family:Verdana, Geneva, sans-serif;'>";
					
				text	+= "Your request is confirmed in GA Support Center.<br /><br />";
				
				text	+= "Title : "+title+"<br />";
				text	+= "From : "+ a1 + " " + a2+"<br /><br />";
				text	+= "Please login to the GASC system to check your data.<br /><br />";
				text	+= "<a href='"+sPath+"index.htm' target='_blank'>General Affairs Support Center</a>";
				
				text	+= "</td></tr></table></td></tr></table></td></tr></table></td></tr></table>";
			
			SendMail oMail = new SendMail();
			if(area.equals(strReal)){
				oMail.sendMail(mailto, from, subject, text, 1, false);
			}
			
		}catch (Exception ex){
			logger.error("messege", ex);
		}
		return true;
	}
	
	/**
	 * submit confirm cancel mail. - 결재자가 컨펌취소 시 메일 발송하는 메서드
	 * Rejecting docuemnt, it send mail.
	 * @param a1 - fromEeno 보낸사람 이름
	 * @param a2 - fromStepNm 보낸사람 직급
	 * @param a3 - toEeno 받는사람 사번
	 * @param title the title - 제목 
	 * @param rtnText the reject reason text - 결재가 입력 리젝 사유, 
	 * @return true, if successful - 리턴 값
	 */
	public static boolean confirmCancelEmail(String a1, String a2, String a3, String title, String rtnText){
		try{
			
			String from    = hostMail;
			
			String mailto  = "";
			String sPath =  "";
			
			if(area.equals(strLocal) || area.equals(strTest)){
				mailto  = sTestMail;
				//mailto  = a3;
				sPath = sPathDev;
			}else{
				mailto  = a3;
				sPath = sPathReal;
			}
			
			String subject = "Canceled request in HMB GA Support Center.";
			

			String text = " <table cellpadding='0' cellspacing='0'>"
						+ "<tr><td style='width:710px; background-color:#f8f8f8; border:1px solid #bebfc4'>"
						+ "<table cellpadding='0' cellspacing='0'>"
						+ "<tr><td style='width:690px; height:40px; background-color:#004b8e; padding-left:20px;'>"
						+ " <img src='"+sPath+"images/mail_logo.jpg' />"
						+ "</td></tr>"
						+ "<tr><td style='padding:20px;'><table cellpadding='0' cellspacing='0'>"
						+ "<tr> <td style='background-color:#FFF; border:1px solid #dddde5; padding:20px;'>"
						+ "<table cellpadding='0' cellspacing='0'>"
						+ "<tr><td style='font-size:12px; width:670px; color:#000; line-height:130%; font-family:Verdana, Geneva, sans-serif;'>";
					
				text	+= "Your request is canceled in GA Support Center.<br /><br />";
				
				text	+= "Title : "+title+"<br />";
				text	+= "From : "+ a1 + " " + a2+"<br />";
				text	+= "Reason: "+rtnText+"<br /><br />";
				text	+= "Please login to the GASC system to check your data.<br /><br />";
				text	+= "<a href='"+sPath+"index.htm' target='_blank'>General Affairs Support Center</a>";
				
				text	+= "</td></tr></table></td></tr></table></td></tr></table></td></tr></table>";
			
			SendMail oMail = new SendMail();
			if(area.equals(strReal)){
				oMail.sendMail(mailto, from, subject, text, 1, false);
			}
			
		}catch (Exception ex){
			logger.error("messege", ex);
		}
		return true;
	}
	
	public static boolean sendEmailForAsVehicle(List <BgabGascbv01Dto> mailList){
		try{
			
			String from    = hostMail;
			String mailto  = "";
			String sPath =  "";
			String toEeno = "";
			
			if(area.equals(strLocal) || area.equals(strTest)){
				mailto  = sTestMail;
				sPath = sPathDev;
			}else{
				mailto  = sTestMail;
				sPath = sPathReal;
			}
			
			SendMail oMail = new SendMail(); 
			
			List <BgabGascbv01Dto> sendMailList = null;
			
			String subject = "Please be advised that the vehicle should be checked for warranty.";
			
			String text1 = " <table cellpadding='0' cellspacing='0'>"
							+ "<tr><td style='width:710px; background-color:#f8f8f8; border:1px solid #bebfc4'>"
							+ "<table cellpadding='0' cellspacing='0'>"
							+ "<tr><td style='width:690px; height:40px; background-color:#004b8e; padding-left:20px;'>"
							+ " <img src='"+sPath+"images/mail_logo.jpg' />"
							+ "</td></tr>"
							+ "<tr><td style='padding:20px;'><table cellpadding='0' cellspacing='0'>"
							+ "<tr> <td style='background-color:#FFF; border:1px solid #dddde5; padding:20px;'>"
							+ "<table cellpadding='0' cellspacing='0'>"
							+ "<tr><td style='font-size:12px; width:670px; color:#000; line-height:130%; font-family:Verdana, Geneva, sans-serif;'>";
					text1	+= "[Check the target vehicle]<br /><br />";
			
			String text2 = "";
			
			String text3	= "Please check as soon as possible.<br /><br />";
				   text3	+= "</td></tr></table></td></tr></table></td></tr></table></td></tr></table>";
			
			for(int i = 0 ; i < mailList.size() ; i++){
				if(i == 0){
					sendMailList = new ArrayList <BgabGascbv01Dto>();
					
					toEeno = mailList.get(i).getCrgr_eeno();
					//mailto = mailList.get(i).getEeno_email();
					logger.info("Eeno_email:::"+mailList.get(i).getEeno_email());
					sendMailList.add(mailList.get(i));
				}
				else if(i == (mailList.size()-1)){
					
					sendMailList.add(mailList.get(i));
					
					for(int n = 0 ; n < sendMailList.size() ; n++){
						text2	+= "Plate No. : "+sendMailList.get(n).getCar_no()+"<br />";
						if(sendMailList.get(n).getAs_type().equals("Y")){
							text2	+= "Check the reason : It's been "+sendMailList.get(n).getFxt_ins_infm_nos()+" years since the purchase<br /><br /><br />";
						}
						else{
							text2	+= "Check the reason : Mileage exceed "+sendMailList.get(n).getFxt_ins_infm_nos()+"0000km.<br /><br /><br />";
						}
					}
					
					String text = text1+text2+text3;
					
					logger.info(text);
					if(area.equals(strReal)){
						oMail.sendMail(mailto, from, subject, text, 1, false);
					}
					
				}
				else{
					if(toEeno.equals(mailList.get(i).getCrgr_eeno())){
						
						sendMailList.add(mailList.get(i));
						
					}
					else{
						
						for(int n = 0 ; n < sendMailList.size() ; n++){
							text2	+= "Plate No. : "+sendMailList.get(n).getCar_no()+"<br />";
							if(sendMailList.get(n).getAs_type().equals("Y")){
								text2	+= "Check the reason : It's been "+sendMailList.get(n).getFxt_ins_infm_nos()+" years since the purchase<br /><br /><br />";
							}
							else{
								text2	+= "Check the reason : Mileage exceed "+sendMailList.get(n).getFxt_ins_infm_nos()+"0000km.<br /><br /><br />";
							}
						}
						
						String text = text1+text2+text3;
						
						logger.info(text);
						if(area.equals(strReal)){
							oMail.sendMail(mailto, from, subject, text, 1, false);
						}
						
						
						text2 = "";
						sendMailList = new ArrayList <BgabGascbv01Dto>();
						sendMailList.add(mailList.get(i));
						toEeno = mailList.get(i).getCrgr_eeno();
						//mailto = mailList.get(i).getEeno_email();
						logger.info("Eeno_email:::"+mailList.get(i).getEeno_email());
						
					}
				}
				
			}
		}catch (Exception ex){
			logger.error("messege", ex);
		}
		return true;
	}//end method
	
	public static boolean sendEmailConfirm(String a1, String a2, String a3, String func, String title, String corp_cd){
		try{
			String from    = hostMail;
			String mailto  = "";
			String sPath =  "";
			
			if(area.equals(strLocal) || area.equals(strTest)){
				mailto  = sTestMail;
				sPath = strHttp+corp_cd+sPathDev;
			}else{
				mailto  = a3;
				sPath = strHttp+corp_cd+sPathReal;
			}
			
			String subject = HncisMessageSource.getMessage("MAIL.0014");

			String text = " <table cellpadding='0' cellspacing='0'>"
						+ "<tr><td style='width:710px; background-color:#f8f8f8; border:1px solid #bebfc4'>"
						+ "<table cellpadding='0' cellspacing='0'>"
						+ "<tr><td style='width:690px; height:40px; background-color:#004b8e; padding-left:20px;'>"
						+ "</td></tr>"
						+ "<tr><td style='padding:20px;'><table cellpadding='0' cellspacing='0'>"
						+ "<tr> <td style='background-color:#FFF; border:1px solid #dddde5; padding:20px;'>"
						+ "<table cellpadding='0' cellspacing='0'>"
						+ "<tr><td style='font-size:12px; width:670px; color:#000; line-height:130%; font-family:Verdana, Geneva, sans-serif;'>";
				text	+= HncisMessageSource.getMessage("MAIL.0014") + "<br /><br />";
				text	+= HncisMessageSource.getMessage("MAIL.0013") + " : "+title+"<br />";
				text	+= HncisMessageSource.getMessage("MAIL.0006") + " : "+ a1 + " " + a2+"<br /><br /><br />";
			
				text	+= "</td></tr></table></td></tr></table></td></tr></table></td></tr></table>";
			
			SendMail oMail = new SendMail();
			if(area.equals(strReal)){
				oMail.sendMail(mailto, from, subject, text, 1, false);
			}
		}catch (Exception ex){
			logger.error("messege", ex);
		}
		return true;
	}
	
	public static boolean sendEmailTrafficTicket(List<BgabGascTm01> list){
		try{
			String imgPath = "http://gasc.hyundai-brasil.com/gasc/";
			if(area.equals(strLocal) || area.equals(strTest)){
				imgPath = "http://tstgasc.hyundai-brasil.com/gasc/";
			}
			
			String from    = hostMail;
			String mailto  = "";
			
			String subject = "There is Traffic ticket for you in GASC.";
			String text = " <table cellpadding='0' cellspacing='0'>"
						+ "<tr><td style='width:710px; background-color:#f8f8f8; border:1px solid #bebfc4'>"
						+ "<table cellpadding='0' cellspacing='0'>"
						+ "<tr><td style='width:690px; height:40px; background-color:#004b8e; padding-left:20px;'>"
						+ " <img src='"+imgPath+"images/mail_logo.jpg' />"
						+ "</td></tr>"
						+ "<tr><td style='padding:20px;'><table cellpadding='0' cellspacing='0'>"
						+ "<tr> <td style='background-color:#FFF; border:1px solid #dddde5; padding:20px;'>"
						+ "<table cellpadding='0' cellspacing='0'>"
						+ "<tr><td style='font-size:12px; width:670px; color:#000; line-height:130%; font-family:Verdana, Geneva, sans-serif;'>";
					
				text	+= "The Traffic ticket. Your GASC.<br /><br />";
				text	+= "</td></tr></table></td></tr></table></td></tr></table></td></tr></table>";
			
				//mail process;;;
				int idx = 0;
				for(BgabGascTm01 targetVo : list){
					//
				}
				
			SendMail oMail = new SendMail();
			if(area.equals(strReal)){
				oMail.sendMail(mailto, from, subject, text, 1, false);
			}
		}catch (Exception ex){
			logger.error("messege", ex);
		}
		return true;
	}
	
	public static boolean sendEmailTrafficTicketForEmailSend(String a1, String a2, String a3, String func, String title){
		try{
			String imgPath = "http://gasc.hyundai-brasil.com/gasc/";
			if(area.equals(strLocal) || area.equals(strTest)){
				imgPath = "http://tstgasc.hyundai-brasil.com/gasc/";
			}
			
			String from    = hostMail;
			String mailto  = a3;
			
			String subject = "There is Traffic ticket for you in GASC.";
			String text = " <table cellpadding='0' cellspacing='0'>"
						+ "<tr><td style='width:710px; background-color:#f8f8f8; border:1px solid #bebfc4'>"
						+ "<table cellpadding='0' cellspacing='0'>"
						+ "<tr><td style='width:690px; height:40px; background-color:#004b8e; padding-left:20px;'>"
						+ " <img src='"+imgPath+"images/mail_logo.jpg' />"
						+ "</td></tr>"
						+ "<tr><td style='padding:20px;'><table cellpadding='0' cellspacing='0'>"
						+ "<tr> <td style='background-color:#FFF; border:1px solid #dddde5; padding:20px;'>"
						+ "<table cellpadding='0' cellspacing='0'>"
						+ "<tr><td style='font-size:12px; width:670px; color:#000; line-height:130%; font-family:Verdana, Geneva, sans-serif;'>";
					
				text	+= "Title : "+title+"<br /><br />";
				
				text	+= "You received a traffic ticket, please follow the instructions below:<br /><br />";
				
				text	+= "1. Accept Traffic ticket at GASC<br />";
				text	+= "2. Print and sign the Payroll discount form<br />";
				text	+= "3. Go to General Affairs department to deliver the signed form and receive the original ticket";
			
				text	+= "</td></tr></table></td></tr></table></td></tr></table></td></tr></table>";
			
			SendMail oMail = new SendMail();
			if(area.equals(strReal)){
				oMail.sendMail(mailto, from, subject, text, 1, false);
			}
		}catch (Exception ex){
			logger.error("messege", ex);
		}
		return true;
	}
	
	public static boolean sendEmailTrafficTicketForPayment(String a1, String a2, String a3, String func, String title){
		try{
			String imgPath = "http://gasc.hyundai-brasil.com/gasc/";
			if(area.equals(strLocal) || area.equals(strTest)){
				imgPath = "http://tstgasc.hyundai-brasil.com/gasc/";
			}
			
			String from    = hostMail;
			String mailto  = a3;
			
			String subject = "There is Traffic ticket for you in GASC.";
			String text = " <table cellpadding='0' cellspacing='0'>"
						+ "<tr><td style='width:710px; background-color:#f8f8f8; border:1px solid #bebfc4'>"
						+ "<table cellpadding='0' cellspacing='0'>"
						+ "<tr><td style='width:690px; height:40px; background-color:#004b8e; padding-left:20px;'>"
						+ " <img src='"+imgPath+"images/mail_logo.jpg' />"
						+ "</td></tr>"
						+ "<tr><td style='padding:20px;'><table cellpadding='0' cellspacing='0'>"
						+ "<tr> <td style='background-color:#FFF; border:1px solid #dddde5; padding:20px;'>"
						+ "<table cellpadding='0' cellspacing='0'>"
						+ "<tr><td style='font-size:12px; width:670px; color:#000; line-height:130%; font-family:Verdana, Geneva, sans-serif;'>";
					
				
				
				text	+= "The Traffic ticket payment. Your GASC.<br /><br />";
				text	+= "Title : "+title+"<br />";
				text	+= "From : "+ a1 + " " + a2+"<br /><br />";
			
				text	+= "</td></tr></table></td></tr></table></td></tr></table></td></tr></table>";
			
			SendMail oMail = new SendMail();
			if(area.equals(strReal)){
				oMail.sendMail(mailto, from, subject, text, 1, false);
			}
		}catch (Exception ex){
			logger.error("messege", ex);
		}
		return true;
	}
	
	public static boolean sendEmailGeneralServiceForConfirm(String fromEenm, String fromStepNm, String toEeno, String mode, String title, String comment){
		try{
			String imgPath = "http://gasc.hyundai-brasil.com/gasc/";
			if(area.equals(strLocal) || area.equals(strTest)){
				imgPath = "http://tstgasc.hyundai-brasil.com/gasc/";
			}
			
			String from    = hostMail;
			String mailto  = toEeno;
			
			String subject = "There is " + title + " for you in GASC.";
			String text = " <table cellpadding='0' cellspacing='0'>"
						+ "<tr><td style='width:710px; background-color:#f8f8f8; border:1px solid #bebfc4'>"
						+ "<table cellpadding='0' cellspacing='0'>"
						+ "<tr><td style='width:690px; height:40px; background-color:#004b8e; padding-left:20px;'>"
						+ " <img src='"+imgPath+"images/mail_logo.jpg' />" + title
						+ "</td></tr>"
						+ "<tr><td style='padding:20px;'><table cellpadding='0' cellspacing='0'>"
						+ "<tr> <td style='background-color:#FFF; border:1px solid #dddde5; padding:20px;'>"
						+ "<table cellpadding='0' cellspacing='0'>"
						+ "<tr><td style='font-size:12px; width:670px; color:#000; line-height:130%; font-family:Verdana, Geneva, sans-serif;'>";
				
				text	+= "The " + title + " Confirm. Your GASC.<br /><br />";
				text	+= "Title : "+title+"<br />";
				text	+= "From : "+ fromEenm + " " + fromStepNm+"<br /><br />";
				text	+= "Comment : "+ comment +"<br /><br />";
			
				text	+= "</td></tr></table></td></tr></table></td></tr></table></td></tr></table>";
			
			SendMail oMail = new SendMail();
			if(area.equals(strReal)){
				oMail.sendMail(mailto, from, subject, text, 1, false);
			}
		}catch (Exception ex){
			logger.error("messege", ex);
		}
		return true;
	}
	
	public static boolean sendEmailTaxiForRequest(String fromEenm, String fromStepNm, String toEeno, String mode, String title, BgabGasctx04 dto){
		try{
			String imgPath = "http://gasc.hyundai-brasil.com/gasc/";
			if(area.equals(strLocal) || area.equals(strTest)){
				imgPath = "http://tstgasc.hyundai-brasil.com/gasc/";
			}
			
			String from    = hostMail;
			String mailto  = toEeno;
			
			String subject = "There is " + title + " for you in GASC.";
			String text = " <table cellpadding='0' cellspacing='0'>"
						+ "<tr><td style='width:710px; background-color:#f8f8f8; border:1px solid #bebfc4'>"
						+ "<table cellpadding='0' cellspacing='0'>"
						+ "<tr><td style='width:690px; height:40px; background-color:#004b8e; padding-left:20px;'>"
						+ " <img src='"+imgPath+"images/mail_logo.jpg' />"
						+ "</td></tr>"
						+ "<tr><td style='padding:20px;'><table cellpadding='0' cellspacing='0'>"
						+ "<tr> <td style='background-color:#FFF; border:1px solid #dddde5; padding:20px;'>"
						+ "<table cellpadding='0' cellspacing='0'>"
						+ "<tr><td style='font-size:12px; width:670px; color:#000; line-height:130%; font-family:Verdana, Geneva, sans-serif;'>";
				
				text    += "<table cellpadding='0' cellspacing='0' style='border-left:1px solid #ddd; border-top:1px solid #ddd;'>";
				text    += "<tr>";
				text    += "	<td style='height:25px; border-right:1px solid #ddd; border-bottom:1px solid #ddd; background-color: rgb(191, 191, 191); text-align:center; width:200px;'>Date<td>";
				text    += "	<td style='height:25px; border-right:1px solid #ddd; border-bottom:1px solid #ddd;width:400px;'> " + dto.getStap_ymd() + "<td>";
				text    += "</tr>";
				text    += "<tr>";
				text    += "	<td style='height:25px; border-right:1px solid #ddd; border-bottom:1px solid #ddd; background-color: rgb(191, 191, 191); text-align:center'>Name<td>";
				text    += "	<td style='height:25px; border-right:1px solid #ddd; border-bottom:1px solid #ddd;'> " + dto.getEenm() + "<td>";
				text    += "</tr>";
				text    += "<tr>";
				text    += "	<td style='height:25px; border-right:1px solid #ddd; border-bottom:1px solid #ddd; background-color: rgb(191, 191, 191); text-align:center'>HMC<td>";
				text    += "	<td style='height:25px; border-right:1px solid #ddd; border-bottom:1px solid #ddd;'> " + dto.getEeno() + "<td>";
				text    += "</tr>";
				text    += "<tr>";
				text    += "	<td style='height:25px; border-right:1px solid #ddd; border-bottom:1px solid #ddd; background-color: rgb(191, 191, 191); text-align:center'>COST C. / Department<td>";
				text    += "	<td style='height:25px; border-right:1px solid #ddd; border-bottom:1px solid #ddd;'> " + dto.getCost_cd() + "/" + dto.getOps_nm() + "<td>";
				text    += "</tr>";
				text    += "<tr>";
				text    += "	<td style='height:25px; border-right:1px solid #ddd; border-bottom:1px solid #ddd; background-color: rgb(191, 191, 191); text-align:center'>From / Detail<td>";
				text    += "	<td style='height:25px; border-right:1px solid #ddd; border-bottom:1px solid #ddd;'> " + dto.getStap_cd() + "/" + dto.getStap_adr() + "<td>";
				text    += "</tr>";
				text    += "<tr>";
				text    += "	<td style='height:25px; border-right:1px solid #ddd; border-bottom:1px solid #ddd; background-color: rgb(191, 191, 191); text-align:center'>To / Detail<td>";
				text    += "	<td style='height:25px; border-right:1px solid #ddd; border-bottom:1px solid #ddd;'> " + dto.getArvp_cd() + "/" + dto.getArvp_adr() + "<td>";
				text    += "</tr>";
				text    += "<tr>";
				text    += "	<td style='height:25px; border-right:1px solid #ddd; border-bottom:1px solid #ddd; background-color: rgb(191, 191, 191); text-align:center'>Amount<td>";
				text    += "	<td style='height:25px; border-right:1px solid #ddd; border-bottom:1px solid #ddd;'> " + dto.getSvca_amt() + "<td>";
				text    += "</tr>";
				text    += "<tr>";
				text    += "	<td style='height:25px; border-right:1px solid #ddd; border-bottom:1px solid #ddd; background-color: rgb(191, 191, 191); text-align:center'>Description / Motive<td>";
				text    += "	<td style='height:25px; border-right:1px solid #ddd; border-bottom:1px solid #ddd;'> " + dto.getRem_sbc() + "<td>";
				text    += "</tr>";
				text    += "</table>";
			
				text	+= "</td></tr></table></td></tr></table></td></tr></table></td></tr></table>";
				
			SendMail oMail = new SendMail();
			if(area.equals(strReal)){
				oMail.sendMail(mailto, from, subject, text, 1, false);
			}
		}catch (Exception ex){
			logger.error("messege", ex);
		}
		return true;
	}
	
	public static boolean sendEmailShuttleBusForRequest(String fromEenm, String fromStepNm, String toEeno, String mode, String title, BgabGascsb01 dto){
		try{
			String imgPath = "http://gasc.hyundai-brasil.com/gasc/";
			if(area.equals(strLocal) || area.equals(strTest)){
				imgPath = "http://tstgasc.hyundai-brasil.com/gasc/";
			}
			
			String from    = hostMail;
			String mailto  = toEeno;
			
			String subject = "There is " + title + " for you in GASC.";
			String text = " <table cellpadding='0' cellspacing='0'>"
						+ "<tr><td style='width:710px; background-color:#f8f8f8; border:1px solid #bebfc4'>"
						+ "<table cellpadding='0' cellspacing='0'>"
						+ "<tr><td style='width:690px; height:40px; background-color:#004b8e; padding-left:20px;'>"
						+ " <img src='"+imgPath+"images/mail_logo.jpg' />"
						+ "</td></tr>"
						+ "<tr><td style='padding:20px;'><table cellpadding='0' cellspacing='0'>"
						+ "<tr> <td style='background-color:#FFF; border:1px solid #dddde5; padding:20px;'>"
						+ "<table cellpadding='0' cellspacing='0'>"
						+ "<tr><td style='font-size:12px; width:670px; color:#000; line-height:130%; font-family:Verdana, Geneva, sans-serif;'>";
			
				text	+= "Shuttle Bus Infomation Change Request<br />";
				text    += "<table cellpadding='0' cellspacing='0' style='border-left:1px solid #ddd; border-top:1px solid #ddd;'>";
				text    += "<tr>";
				text    += "	<td style='height:25px; border-right:1px solid #ddd; border-bottom:1px solid #ddd; background-color: rgb(191, 191, 191); text-align:center; width:200px;'>Line<td>";
				text    += "	<td style='height:25px; border-right:1px solid #ddd; border-bottom:1px solid #ddd; width:400px'> " + dto.getLine_nm() + "<td>";
				text    += "</tr>";
				text    += "<tr>";
				text    += "	<td style='height:25px; border-right:1px solid #ddd; border-bottom:1px solid #ddd; background-color: rgb(191, 191, 191); text-align:center'>Bus Stop<td>";
				text    += "	<td style='height:25px; border-right:1px solid #ddd; border-bottom:1px solid #ddd;'> " + dto.getBus_nm() + "<td>";
				text    += "</tr>";
				text    += "<tr>";
				text    += "	<td style='height:25px; border-right:1px solid #ddd; border-bottom:1px solid #ddd; background-color: rgb(191, 191, 191); text-align:center'>Address<td>";
				text    += "	<td style='height:25px; border-right:1px solid #ddd; border-bottom:1px solid #ddd;'> " + dto.getAddress() + "<td>";
				text    += "</tr>";
				text    += "<tr>";
				text    += "	<td style='height:25px; border-right:1px solid #ddd; border-bottom:1px solid #ddd; background-color: rgb(191, 191, 191); text-align:center'>Nº / Complement<td>";
				text    += "	<td style='height:25px; border-right:1px solid #ddd; border-bottom:1px solid #ddd;'> " + dto.getNumb() + "/" + dto.getComplement() + "<td>";
				text    += "</tr>";
				text    += "<tr>";
				text    += "	<td style='height:25px; border-right:1px solid #ddd; border-bottom:1px solid #ddd; background-color: rgb(191, 191, 191); text-align:center'>District / City<td>";
				text    += "	<td style='height:25px; border-right:1px solid #ddd; border-bottom:1px solid #ddd;'> " + dto.getDistrict() + "/" + dto.getCity() + "<td>";
				text    += "</tr>";
				text    += "<tr>";
				text    += "	<td style='height:25px; border-right:1px solid #ddd; border-bottom:1px solid #ddd; background-color: rgb(191, 191, 191); text-align:center'>Zip Code<td>";
				text    += "	<td style='height:25px; border-right:1px solid #ddd; border-bottom:1px solid #ddd;'> " + dto.getZip_code() + "<td>";
				text    += "</tr>";
				text    += "<tr>";
				text    += "	<td style='height:25px; border-right:1px solid #ddd; border-bottom:1px solid #ddd; background-color: rgb(191, 191, 191); text-align:center'>Work Shift<td>";
				text    += "	<td style='height:25px; border-right:1px solid #ddd; border-bottom:1px solid #ddd;'> " + dto.getWork_shift() + "<td>";
				text    += "</tr>";
				text    += "</table>";
			
				text	+= "</td></tr></table></td></tr></table></td></tr></table></td></tr></table>";
				
			SendMail oMail = new SendMail();
			if(area.equals(strReal)){
				oMail.sendMail(mailto, from, subject, text, 1, false);
			}
		}catch (Exception ex){
			logger.error("messege", ex);
		}
		return true;
	}

	public static boolean sendEmailBusinessTripForConfirm(String fromEenm, String fromStepNm, String toEeno, String mode, String title, List<BgabGascZ011Dto> rsList) {
		try{
			String imgPath = "http://gasc.hyundai-brasil.com/gasc/";
			if(area.equals(strLocal) || area.equals(strTest)){
				imgPath = "http://tstgasc.hyundai-brasil.com/gasc/";
			}
			
			String from    = hostMail;
			String mailto  = toEeno;
			
			String subject = "There is " + title + " for you in GASC.";
			String text = " <table cellpadding='0' cellspacing='0'>"
						+ "<tr><td style='width:710px; background-color:#f8f8f8; border:1px solid #bebfc4'>"
						+ "<table cellpadding='0' cellspacing='0'>"
						+ "<tr><td style='width:690px; height:40px; background-color:#004b8e; padding-left:20px;'>"
						+ " <img src='"+imgPath+"images/mail_logo.jpg' />" + title
						+ "</td></tr>"
						+ "<tr><td style='padding:20px;'><table cellpadding='0' cellspacing='0'>"
						+ "<tr> <td style='background-color:#FFF; border:1px solid #dddde5; padding:20px;'>"
						+ "<table cellpadding='0' cellspacing='0'>"
						+ "<tr><td style='font-size:12px; width:670px; color:#000; line-height:130%; font-family:Verdana, Geneva, sans-serif;'>";
				
				text	+= "The " + title + " confirmation. Your GASC.<br /><br />";
				text	+= "Title : "+title+"<br />";
				text	+= "From : "+ fromEenm + " " + fromStepNm+"<br /><br />";
				
				text	+= "</td></tr></table></td></tr></table></td></tr></table></td></tr></table>";
			
			SendMail oMail = new SendMail();
			/*
			 * 파일 첨부도 할수 있는 메일보내기
			 * 
			 * @param to       - 받는사람 메일주소
			 * @param from     - 보내는 사람 메일주소
			 * @param fromName - 보내는 사람 메일주소
			 * @param subject  - 메일 제목
			 * @param body     - 메일 내용
			 * @param filUrl   - 파일 경로
			 * @param flag     - 메일 보낼 형식 text:0, html:1
			 * @param debug    - 디버그 여부 디버그:false, 안함:true
			 * @return success - 성공여부, 성공:true, 실패:false
			 * public boolean sendMailFileGlobal(String to, String from, String fromStr, String subject, String body, String fileUrl, int flag, boolean debug)
			 */
			if(area.equals(strReal)){
				oMail.sendMailFileConfrimation(mailto, from, "", subject, text, rsList, 1, false);
			}
		}catch (Exception ex){
			logger.error("messege", ex);
		}
		return true;
	}
	
	public static boolean sendEmailShuttleBusForHRmail(String fromEenm, String fromStepNm, String toEeno, String mode, String title, List<BgabGascZ011Dto> rsList, BgabGascsb01 dto) {
		try{
			String imgPath = "http://gasc.hyundai-brasil.com/gasc/";
			if(area.equals(strLocal) || area.equals(strTest)){
				imgPath = "http://tstgasc.hyundai-brasil.com/gasc/";
			}
			
			String from    = hostMail;
			String mailto  = toEeno;
			
			String subject = "There is " + title + " for you in GASC.";
			String text = " <table cellpadding='0' cellspacing='0'>"
						+ "<tr><td style='width:710px; background-color:#f8f8f8; border:1px solid #bebfc4'>"
						+ "<table cellpadding='0' cellspacing='0'>"
						+ "<tr><td style='width:690px; height:40px; background-color:#004b8e; padding-left:20px;'>"
						+ " <img src='"+imgPath+"images/mail_logo.jpg' />"
						+ "</td></tr>"
						+ "<tr><td style='padding:20px;'><table cellpadding='0' cellspacing='0'>"
						+ "<tr> <td style='background-color:#FFF; border:1px solid #dddde5; padding:20px;'>"
						+ "<table cellpadding='0' cellspacing='0'>"
						+ "<tr><td style='font-size:12px; width:670px; color:#000; line-height:130%; font-family:Verdana, Geneva, sans-serif;'>";
				
				text	+= "Shuttle Bus Address Change<br />";
				text    += "<table cellpadding='0' cellspacing='0' style='border-left:1px solid #ddd; border-top:1px solid #ddd;'>";
				text    += "<tr>";
				text    += "	<td style='height:25px; border-right:1px solid #ddd; border-bottom:1px solid #ddd; background-color: rgb(191, 191, 191); text-align:center; width:200px;'>Line<td>";
				text    += "	<td style='height:25px; border-right:1px solid #ddd; border-bottom:1px solid #ddd; width:400px'> " + dto.getLine_nm() + "<td>";
				text    += "</tr>";
				text    += "<tr>";
				text    += "	<td style='height:25px; border-right:1px solid #ddd; border-bottom:1px solid #ddd; background-color: rgb(191, 191, 191); text-align:center'>Bus Stop<td>";
				text    += "	<td style='height:25px; border-right:1px solid #ddd; border-bottom:1px solid #ddd;'> " + dto.getBus_nm() + "<td>";
				text    += "</tr>";
				text    += "<tr>";
				text    += "	<td style='height:25px; border-right:1px solid #ddd; border-bottom:1px solid #ddd; background-color: rgb(191, 191, 191); text-align:center'>Address<td>";
				text    += "	<td style='height:25px; border-right:1px solid #ddd; border-bottom:1px solid #ddd;'> " + dto.getAddress() + "<td>";
				text    += "</tr>";
				text    += "<tr>";
				text    += "	<td style='height:25px; border-right:1px solid #ddd; border-bottom:1px solid #ddd; background-color: rgb(191, 191, 191); text-align:center'>Nº / Complement<td>";
				text    += "	<td style='height:25px; border-right:1px solid #ddd; border-bottom:1px solid #ddd;'> " + dto.getNumb() + "/" + dto.getComplement() + "<td>";
				text    += "</tr>";
				text    += "<tr>";
				text    += "	<td style='height:25px; border-right:1px solid #ddd; border-bottom:1px solid #ddd; background-color: rgb(191, 191, 191); text-align:center'>District / City<td>";
				text    += "	<td style='height:25px; border-right:1px solid #ddd; border-bottom:1px solid #ddd;'> " + dto.getDistrict() + "/" + dto.getCity() + "<td>";
				text    += "</tr>";
				text    += "<tr>";
				text    += "	<td style='height:25px; border-right:1px solid #ddd; border-bottom:1px solid #ddd; background-color: rgb(191, 191, 191); text-align:center'>Zip Code<td>";
				text    += "	<td style='height:25px; border-right:1px solid #ddd; border-bottom:1px solid #ddd;'> " + dto.getZip_code() + "<td>";
				text    += "</tr>";
				text    += "<tr>";
				text    += "	<td style='height:25px; border-right:1px solid #ddd; border-bottom:1px solid #ddd; background-color: rgb(191, 191, 191); text-align:center'>Work Shift<td>";
				text    += "	<td style='height:25px; border-right:1px solid #ddd; border-bottom:1px solid #ddd;'> " + dto.getWork_shift() + "<td>";
				text    += "</tr>";
				text    += "</table>";
				
				text	+= "</td></tr></table></td></tr></table></td></tr></table></td></tr></table>";
			
			SendMail oMail = new SendMail();
			/*
			 * 파일 첨부도 할수 있는 메일보내기
			 * 
			 * @param to       - 받는사람 메일주소
			 * @param from     - 보내는 사람 메일주소
			 * @param fromName - 보내는 사람 메일주소
			 * @param subject  - 메일 제목
			 * @param body     - 메일 내용
			 * @param filUrl   - 파일 경로
			 * @param flag     - 메일 보낼 형식 text:0, html:1
			 * @param debug    - 디버그 여부 디버그:false, 안함:true
			 * @return success - 성공여부, 성공:true, 실패:false
			 * public boolean sendMailFileGlobal(String to, String from, String fromStr, String subject, String body, String fileUrl, int flag, boolean debug)
			 */
			if(area.equals(strReal)){
				oMail.sendMailShuttleBusFile(mailto, from, "", subject, text, rsList, 1, false);
			}
		}catch (Exception ex){
			logger.error("messege", ex);
		}
		return true;
	}
}//end class

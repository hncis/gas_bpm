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
	private static final String strMessege = "messege";
	
	private static final String textHead_01 = " <table cellpadding='0' cellspacing='0'>";
	private static final String textHead_02 = "<tr><td style='width:710px; background-color:#f8f8f8; border:1px solid #bebfc4'>";
	private static final String textHead_03 = "<table cellpadding='0' cellspacing='0'>";
	private static final String textHead_04 = "<tr><td style='width:690px; height:40px; background-color:#004b8e; padding-left:20px;'>";
	private static final String textHead_05 = "</td></tr>";
	private static final String textHead_06 = "<tr><td style='padding:20px;'><table cellpadding='0' cellspacing='0'>";
	private static final String textHead_07 = "<tr> <td style='background-color:#FFF; border:1px solid #dddde5; padding:20px;'>";
	private static final String textHead_08 = "<tr><td style='font-size:12px; width:670px; color:#000; line-height:130%; font-family:Verdana, Geneva, sans-serif;'>";
	
	private static final String textHead = textHead_01 + textHead_02 + textHead_03 
			+ textHead_04 + textHead_05 + textHead_06 
			+ textHead_07 + textHead_08;
	
	private static final String html_colon = " : ";
	private static final String html_tag_br1 = "<br />";
	private static final String html_tag_br2 = "<br /><br />";
	private static final String html_tag_img = " <img src='";
	private static final String html_tag_a_close = "</a>";
	private static final String html_tag_tr = "<tr>";
	private static final String html_tag_tr_close = "</tr>";
	private static final String html_tag_td = "<td>";
	private static final String html_tag_td_style = "	<td style='height:25px; border-right:1px solid #ddd; border-bottom:1px solid #ddd;'> ";
	private static final String html_str_Title = "Title : ";
	private static final String html_str_From = "From : ";
	private static final String html_str_Thereis = "There is ";
	private static final String html_str_foryouinGASC = " for you in GASC.";
	private static final String html_template_01 = "</td></tr></table></td></tr></table></td></tr></table></td></tr></table>";
	private static final String html_filename_mailLogo = "images/mail_logo.jpg' />";
	private static final String sendEmail_imgPath_01 = "http://gasc.hyundai-brasil.com/gasc/";
	private static final String sendEmail_imgPath_02 = "http://tstgasc.hyundai-brasil.com/gasc/";
	
	
	
	/*private static final String textHead = " <table cellpadding='0' cellspacing='0'>"
			+ "<tr><td style='width:710px; background-color:#f8f8f8; border:1px solid #bebfc4'>"
			+ "<table cellpadding='0' cellspacing='0'>"
			+ "<tr><td style='width:690px; height:40px; background-color:#004b8e; padding-left:20px;'>"
			+ "</td></tr>"
			+ "<tr><td style='padding:20px;'><table cellpadding='0' cellspacing='0'>"
			+ "<tr> <td style='background-color:#FFF; border:1px solid #dddde5; padding:20px;'>"
			+ "<table cellpadding='0' cellspacing='0'>"
			+ "<tr><td style='font-size:12px; width:670px; color:#000; line-height:130%; font-family:Verdana, Geneva, sans-serif;'>";*/
	
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
					
				text	+= HncisMessageSource.getMessage("MAIL.0004") + html_tag_br2;
				
				text	+= HncisMessageSource.getMessage("MAIL.0005") + html_colon+title+html_tag_br1;
				text	+= HncisMessageSource.getMessage("MAIL.0006") + html_colon+ a1 + " " + a2+"<br /><br /><br />";
				text	+= HncisMessageSource.getMessage("MAIL.0007") + html_tag_br2;
				
				text	+= "<a href='"+sPath+"' target='_blank'>" + HncisMessageSource.getMessage("MAIL.0008") + html_tag_a_close;
				
				text	+= html_template_01;
				
				logger.info(text);
				SendMail oMail = new SendMail();
				if(area.equals(strReal)){
					oMail.sendMail(mailto, from, subject, text, 1, false);
				}
				
				
			
		}catch (Exception ex){
			logger.error(strMessege, ex);
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
					
				text	+= HncisMessageSource.getMessage("MAIL.0012") + html_tag_br2;
				
				text	+= HncisMessageSource.getMessage("MAIL.0005") + html_colon + title + html_tag_br1;
				text	+= HncisMessageSource.getMessage("MAIL.0010") + html_colon + a1 + " " + a2 + html_tag_br1;
				text	+= HncisMessageSource.getMessage("MAIL.0011") + html_colon + rtnText + html_tag_br2;
				text	+= "<a href='"+sPath+"' target='_blank'>" + HncisMessageSource.getMessage("MAIL.0008") + html_tag_a_close;
				
				text	+= html_template_01;
			
			SendMail oMail = new SendMail();
			if(area.equals(strReal)){
				oMail.sendMail(mailto, from, subject, text, 1, false);
			}
			
		}catch (Exception ex){
			logger.error(strMessege, ex);
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
					
				text	+= HncisMessageSource.getMessage("MAIL.0009") + html_tag_br2;
				text	+= HncisMessageSource.getMessage("MAIL.0005") + html_colon + title + html_tag_br1;
				text	+= HncisMessageSource.getMessage("MAIL.0010") + html_colon + fromEeno + " " + fromStepNm + "<br /><br /><br />";
				text	+= HncisMessageSource.getMessage("MAIL.0007") + html_tag_br2;
				text	+= "<a href='"+sPath+"' target='_blank'>" + HncisMessageSource.getMessage("MAIL.0008") + html_tag_a_close;
				
				text	+= html_template_01;
				SendMail oMail = new SendMail();
				if(area.equals(strReal)){
					oMail.sendMail(mailto, from, subject, text, 1, false);
				}
		}catch (Exception ex){
			logger.error(strMessege, ex);
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
			
			String text = textHead_01
						+ textHead_02
						+ textHead_03
						+ textHead_04
						+ html_tag_img+sPath+html_filename_mailLogo
						+ textHead_05
						+ textHead_06
						+ textHead_07
						+ textHead_03
						+ textHead_08;
					
				text	+= "Your request is confirmed in GA Support Center.<br /><br />";
				
				text	+= html_str_Title+title+"<br />";
				text	+= html_str_From+ a1 + " " + a2+html_tag_br2;
				text	+= "Please login to the GASC system to check your data.<br /><br />";
				text	+= "<a href='"+sPath+"index.htm' target='_blank'>General Affairs Support Center</a>";
				
				text	+= html_template_01;
			
			SendMail oMail = new SendMail();
			if(area.equals(strReal)){
				oMail.sendMail(mailto, from, subject, text, 1, false);
			}
			
		}catch (Exception ex){
			logger.error(strMessege, ex);
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
			

			String text = textHead_01
						+ textHead_02
						+ textHead_03
						+ textHead_04
						+ html_tag_img+sPath+html_filename_mailLogo
						+ textHead_05
						+ textHead_06
						+ textHead_07
						+ textHead_03
						+ textHead_08;
					
				text	+= "Your request is canceled in GA Support Center.<br /><br />";
				
				text	+= html_str_Title+title+html_tag_br1;
				text	+= html_str_From+ a1 + " " + a2+html_tag_br1;
				text	+= "Reason: "+rtnText+html_tag_br2;
				text	+= "Please login to the GASC system to check your data.<br /><br />";
				text	+= "<a href='"+sPath+"index.htm' target='_blank'>General Affairs Support Center</a>";
				
				text	+= html_template_01;
			
			SendMail oMail = new SendMail();
			if(area.equals(strReal)){
				oMail.sendMail(mailto, from, subject, text, 1, false);
			}
			
		}catch (Exception ex){
			logger.error(strMessege, ex);
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
			
			String text1 = textHead_01
							+ textHead_02
							+ textHead_03
							+ textHead_04
							+ html_tag_img+sPath+html_filename_mailLogo
							+ textHead_05
							+ textHead_06
							+ textHead_07
							+ textHead_03
							+ textHead_08;
					text1	+= "[Check the target vehicle]<br /><br />";
			
			String text2 = "";
			
			String text3	= "Please check as soon as possible.<br /><br />";
				   text3	+= html_template_01;
			
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
						text2	+= "Plate No. : "+sendMailList.get(n).getCar_no()+html_tag_br1;
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
							text2	+= "Plate No. : "+sendMailList.get(n).getCar_no()+html_tag_br1;
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
			logger.error(strMessege, ex);
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

			String text = textHead_01
						+ textHead_02
						+ textHead_03
						+ textHead_04
						+ textHead_05
						+ textHead_06
						+ textHead_07
						+ textHead_03
						+ textHead_08;
				text	+= HncisMessageSource.getMessage("MAIL.0014") + html_tag_br2;
				text	+= HncisMessageSource.getMessage("MAIL.0013") + html_colon+title+html_tag_br1;
				text	+= HncisMessageSource.getMessage("MAIL.0006") + html_colon+ a1 + " " + a2+"<br /><br /><br />";
			
				text	+= html_template_01;
			
			SendMail oMail = new SendMail();
			if(area.equals(strReal)){
				oMail.sendMail(mailto, from, subject, text, 1, false);
			}
		}catch (Exception ex){
			logger.error(strMessege, ex);
		}
		return true;
	}
	
	public static boolean sendEmailTrafficTicket(List<BgabGascTm01> list){
		try{
			String imgPath = sendEmail_imgPath_01;
			if(area.equals(strLocal) || area.equals(strTest)){
				imgPath = sendEmail_imgPath_02;
			}
			
			String from    = hostMail;
			String mailto  = "";
			
			String subject = "There is Traffic ticket for you in GASC.";
			String text = textHead_01
						+ textHead_02
						+ textHead_03
						+ textHead_04
						+ html_tag_img+imgPath+html_filename_mailLogo
						+ textHead_05
						+ textHead_06
						+ textHead_07
						+ textHead_03
						+ textHead_08;
					
				text	+= "The Traffic ticket. Your GASC.<br /><br />";
				text	+= html_template_01;
			
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
			logger.error(strMessege, ex);
		}
		return true;
	}
	
	public static boolean sendEmailTrafficTicketForEmailSend(String a1, String a2, String a3, String func, String title){
		try{
			String imgPath = sendEmail_imgPath_01;
			if(area.equals(strLocal) || area.equals(strTest)){
				imgPath = sendEmail_imgPath_02;
			}
			
			String from    = hostMail;
			String mailto  = a3;
			
			String subject = "There is Traffic ticket for you in GASC.";
			String text = textHead_01
						+ textHead_02
						+ textHead_03
						+ textHead_04
						+ html_tag_img+imgPath+html_filename_mailLogo
						+ textHead_05
						+ textHead_06
						+ textHead_07
						+ textHead_03
						+ textHead_08;
					
				text	+= html_str_Title+title+html_tag_br2;
				
				text	+= "You received a traffic ticket, please follow the instructions below:<br /><br />";
				
				text	+= "1. Accept Traffic ticket at GASC<br />";
				text	+= "2. Print and sign the Payroll discount form<br />";
				text	+= "3. Go to General Affairs department to deliver the signed form and receive the original ticket";
			
				text	+= html_template_01;
			
			SendMail oMail = new SendMail();
			if(area.equals(strReal)){
				oMail.sendMail(mailto, from, subject, text, 1, false);
			}
		}catch (Exception ex){
			logger.error(strMessege, ex);
		}
		return true;
	}
	
	public static boolean sendEmailTrafficTicketForPayment(String a1, String a2, String a3, String func, String title){
		try{
			String imgPath = sendEmail_imgPath_01;
			if(area.equals(strLocal) || area.equals(strTest)){
				imgPath = sendEmail_imgPath_02;
			}
			
			String from    = hostMail;
			String mailto  = a3;
			
			String subject = "There is Traffic ticket for you in GASC.";
			String text = textHead_01
						+ textHead_02
						+ textHead_03
						+ textHead_04
						+ html_tag_img+imgPath+html_filename_mailLogo
						+ textHead_05
						+ textHead_06
						+ textHead_07
						+ textHead_03
						+ textHead_08;
					
				
				
				text	+= "The Traffic ticket payment. Your GASC.<br /><br />";
				text	+= html_str_Title+title+html_tag_br1;
				text	+= html_str_From+ a1 + " " + a2+html_tag_br2;
			
				text	+= html_template_01;
			
			SendMail oMail = new SendMail();
			if(area.equals(strReal)){
				oMail.sendMail(mailto, from, subject, text, 1, false);
			}
		}catch (Exception ex){
			logger.error(strMessege, ex);
		}
		return true;
	}
	
	public static boolean sendEmailGeneralServiceForConfirm(String fromEenm, String fromStepNm, String toEeno, String mode, String title, String comment){
		try{
			String imgPath = sendEmail_imgPath_01;
			if(area.equals(strLocal) || area.equals(strTest)){
				imgPath = sendEmail_imgPath_02;
			}
			
			String from    = hostMail;
			String mailto  = toEeno;
			
			String subject = html_str_Thereis + title + html_str_foryouinGASC;
			String text = textHead_01
						+ textHead_02
						+ textHead_03
						+ textHead_04
						+ html_tag_img+imgPath+html_filename_mailLogo + title
						+ textHead_05
						+ textHead_06
						+ textHead_07
						+ textHead_03
						+ textHead_08;
				
				text	+= "The " + title + " Confirm. Your GASC.<br /><br />";
				text	+= html_str_Title+title+html_tag_br1;
				text	+= html_str_From+ fromEenm + " " + fromStepNm+html_tag_br2;
				text	+= "Comment : "+ comment +html_tag_br2;
			
				text	+= html_template_01;
			
			SendMail oMail = new SendMail();
			if(area.equals(strReal)){
				oMail.sendMail(mailto, from, subject, text, 1, false);
			}
		}catch (Exception ex){
			logger.error(strMessege, ex);
		}
		return true;
	}
	
	public static boolean sendEmailTaxiForRequest(String fromEenm, String fromStepNm, String toEeno, String mode, String title, BgabGasctx04 dto){
		try{
			String imgPath = sendEmail_imgPath_01;
			if(area.equals(strLocal) || area.equals(strTest)){
				imgPath = sendEmail_imgPath_02;
			}
			
			String from    = hostMail;
			String mailto  = toEeno;
			
			String subject = html_str_Thereis + title + html_str_foryouinGASC;
			String text = textHead_01
						+ textHead_02
						+ textHead_03
						+ textHead_04
						+ html_tag_img+imgPath+html_filename_mailLogo
						+ textHead_05
						+ textHead_06
						+ textHead_07
						+ textHead_03
						+ textHead_08;
				
				text    += "<table cellpadding='0' cellspacing='0' style='border-left:1px solid #ddd; border-top:1px solid #ddd;'>";
				text    += html_tag_tr;
				text    += "	<td style='height:25px; border-right:1px solid #ddd; border-bottom:1px solid #ddd; background-color: rgb(191, 191, 191); text-align:center; width:200px;'>Date<td>";
				text    += "	<td style='height:25px; border-right:1px solid #ddd; border-bottom:1px solid #ddd;width:400px;'> " + dto.getStap_ymd() + html_tag_td;
				text    += html_tag_tr_close;
				text    += html_tag_tr;
				text    += "	<td style='height:25px; border-right:1px solid #ddd; border-bottom:1px solid #ddd; background-color: rgb(191, 191, 191); text-align:center'>Name<td>";
				text    += html_tag_td_style + dto.getEenm() + html_tag_td;
				text    += html_tag_tr_close;
				text    += html_tag_tr;
				text    += "	<td style='height:25px; border-right:1px solid #ddd; border-bottom:1px solid #ddd; background-color: rgb(191, 191, 191); text-align:center'>HMC<td>";
				text    += html_tag_td_style + dto.getEeno() + html_tag_td;
				text    += html_tag_tr_close;
				text    += html_tag_tr;
				text    += "	<td style='height:25px; border-right:1px solid #ddd; border-bottom:1px solid #ddd; background-color: rgb(191, 191, 191); text-align:center'>COST C. / Department<td>";
				text    += html_tag_td_style + dto.getCost_cd() + "/" + dto.getOps_nm() + html_tag_td;
				text    += html_tag_tr_close;
				text    += html_tag_tr;
				text    += "	<td style='height:25px; border-right:1px solid #ddd; border-bottom:1px solid #ddd; background-color: rgb(191, 191, 191); text-align:center'>From / Detail<td>";
				text    += html_tag_td_style + dto.getStap_cd() + "/" + dto.getStap_adr() + html_tag_td;
				text    += html_tag_tr_close;
				text    += html_tag_tr;
				text    += "	<td style='height:25px; border-right:1px solid #ddd; border-bottom:1px solid #ddd; background-color: rgb(191, 191, 191); text-align:center'>To / Detail<td>";
				text    += html_tag_td_style + dto.getArvp_cd() + "/" + dto.getArvp_adr() + html_tag_td;
				text    += html_tag_tr_close;
				text    += html_tag_tr;
				text    += "	<td style='height:25px; border-right:1px solid #ddd; border-bottom:1px solid #ddd; background-color: rgb(191, 191, 191); text-align:center'>Amount<td>";
				text    += html_tag_td_style + dto.getSvca_amt() + html_tag_td;
				text    += html_tag_tr_close;
				text    += html_tag_tr;
				text    += "	<td style='height:25px; border-right:1px solid #ddd; border-bottom:1px solid #ddd; background-color: rgb(191, 191, 191); text-align:center'>Description / Motive<td>";
				text    += html_tag_td_style + dto.getRem_sbc() + html_tag_td;
				text    += html_tag_tr_close;
				text    += "</table>";
			
				text	+= html_template_01;
				
			SendMail oMail = new SendMail();
			if(area.equals(strReal)){
				oMail.sendMail(mailto, from, subject, text, 1, false);
			}
		}catch (Exception ex){
			logger.error(strMessege, ex);
		}
		return true;
	}
	
	public static boolean sendEmailShuttleBusForRequest(String fromEenm, String fromStepNm, String toEeno, String mode, String title, BgabGascsb01 dto){
		try{
			String imgPath = sendEmail_imgPath_01;
			if(area.equals(strLocal) || area.equals(strTest)){
				imgPath = sendEmail_imgPath_02;
			}
			
			String from    = hostMail;
			String mailto  = toEeno;
			
			String subject = html_str_Thereis + title + html_str_foryouinGASC;
			String text = textHead_01
						+ textHead_02
						+ textHead_03
						+ textHead_04
						+ html_tag_img+imgPath+html_filename_mailLogo
						+ textHead_05
						+ textHead_06
						+ textHead_07
						+ textHead_03
						+ textHead_08;
			
				text	+= "Shuttle Bus Infomation Change Request<br />";
				text    += "<table cellpadding='0' cellspacing='0' style='border-left:1px solid #ddd; border-top:1px solid #ddd;'>";
				text    += html_tag_tr;
				text    += "	<td style='height:25px; border-right:1px solid #ddd; border-bottom:1px solid #ddd; background-color: rgb(191, 191, 191); text-align:center; width:200px;'>Line<td>";
				text    += "	<td style='height:25px; border-right:1px solid #ddd; border-bottom:1px solid #ddd; width:400px'> " + dto.getLine_nm() + html_tag_td;
				text    += html_tag_tr_close;
				text    += html_tag_tr;
				text    += "	<td style='height:25px; border-right:1px solid #ddd; border-bottom:1px solid #ddd; background-color: rgb(191, 191, 191); text-align:center'>Bus Stop<td>";
				text    += html_tag_td_style + dto.getBus_nm() + html_tag_td;
				text    += html_tag_tr_close;
				text    += html_tag_tr;
				text    += "	<td style='height:25px; border-right:1px solid #ddd; border-bottom:1px solid #ddd; background-color: rgb(191, 191, 191); text-align:center'>Address<td>";
				text    += html_tag_td_style + dto.getAddress() + html_tag_td;
				text    += html_tag_tr_close;
				text    += html_tag_tr;
				text    += "	<td style='height:25px; border-right:1px solid #ddd; border-bottom:1px solid #ddd; background-color: rgb(191, 191, 191); text-align:center'>Nº / Complement<td>";
				text    += html_tag_td_style + dto.getNumb() + "/" + dto.getComplement() + html_tag_td;
				text    += html_tag_tr_close;
				text    += html_tag_tr;
				text    += "	<td style='height:25px; border-right:1px solid #ddd; border-bottom:1px solid #ddd; background-color: rgb(191, 191, 191); text-align:center'>District / City<td>";
				text    += html_tag_td_style + dto.getDistrict() + "/" + dto.getCity() + html_tag_td;
				text    += html_tag_tr_close;
				text    += html_tag_tr;
				text    += "	<td style='height:25px; border-right:1px solid #ddd; border-bottom:1px solid #ddd; background-color: rgb(191, 191, 191); text-align:center'>Zip Code<td>";
				text    += html_tag_td_style + dto.getZip_code() + html_tag_td;
				text    += html_tag_tr_close;
				text    += html_tag_tr;
				text    += "	<td style='height:25px; border-right:1px solid #ddd; border-bottom:1px solid #ddd; background-color: rgb(191, 191, 191); text-align:center'>Work Shift<td>";
				text    += html_tag_td_style + dto.getWork_shift() + html_tag_td;
				text    += html_tag_tr_close;
				text    += "</table>";
			
				text	+= html_template_01;
				
			SendMail oMail = new SendMail();
			if(area.equals(strReal)){
				oMail.sendMail(mailto, from, subject, text, 1, false);
			}
		}catch (Exception ex){
			logger.error(strMessege, ex);
		}
		return true;
	}

	public static boolean sendEmailBusinessTripForConfirm(String fromEenm, String fromStepNm, String toEeno, String mode, String title, List<BgabGascZ011Dto> rsList) {
		try{
			String imgPath = sendEmail_imgPath_01;
			if(area.equals(strLocal) || area.equals(strTest)){
				imgPath = sendEmail_imgPath_02;
			}
			
			String from    = hostMail;
			String mailto  = toEeno;
			
			String subject = html_str_Thereis + title + html_str_foryouinGASC;
			String text = textHead_01
						+ textHead_02
						+ textHead_03
						+ textHead_04
						+ html_tag_img+imgPath+html_filename_mailLogo + title
						+ textHead_05
						+ textHead_06
						+ textHead_07
						+ textHead_03
						+ textHead_08;
				
				text	+= "The " + title + " confirmation. Your GASC.<br /><br />";
				text	+= html_str_Title+title+html_tag_br1;
				text	+= html_str_From+ fromEenm + " " + fromStepNm+html_tag_br2;
				
				text	+= html_template_01;
			
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
			logger.error(strMessege, ex);
		}
		return true;
	}
	
	public static boolean sendEmailShuttleBusForHRmail(String fromEenm, String fromStepNm, String toEeno, String mode, String title, List<BgabGascZ011Dto> rsList, BgabGascsb01 dto) {
		try{
			String imgPath = sendEmail_imgPath_01;
			if(area.equals(strLocal) || area.equals(strTest)){
				imgPath = sendEmail_imgPath_02;
			}
			
			String from    = hostMail;
			String mailto  = toEeno;
			
			String subject = html_str_Thereis + title + html_str_foryouinGASC;
			String text = textHead_01
						+ textHead_02
						+ textHead_03
						+ textHead_04
						+ html_tag_img+imgPath+html_filename_mailLogo
						+ textHead_05
						+ textHead_06
						+ textHead_07
						+ textHead_03
						+ textHead_08;
				
				text	+= "Shuttle Bus Address Change<br />";
				text    += "<table cellpadding='0' cellspacing='0' style='border-left:1px solid #ddd; border-top:1px solid #ddd;'>";
				text    += html_tag_tr;
				text    += "	<td style='height:25px; border-right:1px solid #ddd; border-bottom:1px solid #ddd; background-color: rgb(191, 191, 191); text-align:center; width:200px;'>Line<td>";
				text    += "	<td style='height:25px; border-right:1px solid #ddd; border-bottom:1px solid #ddd; width:400px'> " + dto.getLine_nm() + html_tag_td;
				text    += html_tag_tr_close;
				text    += html_tag_tr;
				text    += "	<td style='height:25px; border-right:1px solid #ddd; border-bottom:1px solid #ddd; background-color: rgb(191, 191, 191); text-align:center'>Bus Stop<td>";
				text    += html_tag_td_style + dto.getBus_nm() + html_tag_td;
				text    += html_tag_tr_close;
				text    += html_tag_tr;
				text    += "	<td style='height:25px; border-right:1px solid #ddd; border-bottom:1px solid #ddd; background-color: rgb(191, 191, 191); text-align:center'>Address<td>";
				text    += html_tag_td_style + dto.getAddress() + html_tag_td;
				text    += html_tag_tr_close;
				text    += html_tag_tr;
				text    += "	<td style='height:25px; border-right:1px solid #ddd; border-bottom:1px solid #ddd; background-color: rgb(191, 191, 191); text-align:center'>Nº / Complement<td>";
				text    += html_tag_td_style + dto.getNumb() + "/" + dto.getComplement() + html_tag_td;
				text    += html_tag_tr_close;
				text    += html_tag_tr;
				text    += "	<td style='height:25px; border-right:1px solid #ddd; border-bottom:1px solid #ddd; background-color: rgb(191, 191, 191); text-align:center'>District / City<td>";
				text    += html_tag_td_style + dto.getDistrict() + "/" + dto.getCity() + html_tag_td;
				text    += html_tag_tr_close;
				text    += html_tag_tr;
				text    += "	<td style='height:25px; border-right:1px solid #ddd; border-bottom:1px solid #ddd; background-color: rgb(191, 191, 191); text-align:center'>Zip Code<td>";
				text    += html_tag_td_style + dto.getZip_code() + html_tag_td;
				text    += html_tag_tr_close;
				text    += html_tag_tr;
				text    += "	<td style='height:25px; border-right:1px solid #ddd; border-bottom:1px solid #ddd; background-color: rgb(191, 191, 191); text-align:center'>Work Shift<td>";
				text    += html_tag_td_style + dto.getWork_shift() + html_tag_td;
				text    += html_tag_tr_close;
				text    += "</table>";
				
				text	+= html_template_01;
			
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
			logger.error(strMessege, ex);
		}
		return true;
	}
}//end class

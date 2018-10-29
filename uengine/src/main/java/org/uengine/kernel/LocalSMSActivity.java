package org.uengine.kernel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.metaworks.FieldDescriptor;
import org.metaworks.Type;
import org.uengine.util.dao.DefaultConnectionFactory;

import com.daou.BizSend;
import com.daou.entity.SendMsgEntity;

public class LocalSMSActivity extends DefaultActivity {

	/**
	 * 
	 */
	private static final long serialVersionUID = GlobalContext.SERIALIZATION_UID;
	
	public LocalSMSActivity(){
		setName("SMS Activity");
	}
	
	public static void metaworksCallback_changeMetadata(Type type){
		FieldDescriptor fd;
		
		fd = type.getFieldDescriptor("Contents");

/*		fd.setValidators(new Validator[]{new Validator(){

			public String validate(Object arg0, Instance arg1) {
				String contents = (String)arg0;
				if(contents.length() > 30)
					return "30 초 내에 메시지가 도착합니다.";
				return null;
			}
			
			
		}});
*/		
		fd = type.getFieldDescriptor("ToRole");
		fd.setDisplayName("Recipient");
		
		type.setName("Messaging Properties");
	}
	
	
	String contents;
		public String getContents() {
			return contents;
		}
		public void setContents(String value) {
			contents = value;
		}

	Role toRole;
		public Role getToRole() {
			return toRole;
		}
		public void setToRole(Role value) {
			toRole = value;
		}
	
	
	public void executeActivity(ProcessInstance instance) throws Exception{

		String actualContent = evaluateContent(instance, getContents()).toString();

		if(getToRole()==null)
			throw new UEngineException("Receiver is not set.");
			
		RoleMapping roleMapping = getToRole().getMapping(instance, getTracingTag());
				
		if(roleMapping==null)
			throw new UEngineException("Actual target receiver is not set yet.");
		
		roleMapping.fill(instance);
		String empCode = roleMapping.getEndpoint();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String empName = null;
		String mobileno = null;
		String comname = null;
		
		try {
			String sql = " select a.mobileno, a.empName, b.comname from emptable a, comtable b where a.globalcom = b.comcode and empcode = ? ";
			conn = DefaultConnectionFactory.create().getConnection();
			
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, empCode);
			
			rs = stmt.executeQuery();
			
			if ( rs.next() )
			{
				mobileno = rs.getString("mobileno");
				empName = rs.getString("empName");
				comname = rs.getString("comname");			
				
			}
			
		} finally {
    		if (rs != null) {
    			try { rs.close(); } catch (Exception e) { }
    		}
    		if (stmt != null) {
    			try { stmt.close(); } catch (Exception e) { }
    		}
    		if (conn != null) {
    			try { conn.close(); } catch (Exception e) { }
    		}
		}
		
		
		BizSend bs = new BizSend();
		SendMsgEntity sme = null;
		
		/* Console 로그 설정 */
		// Console 에서 로그를 확인할 경우 설정
		bs.setLogEnabled(true);
		
		/* 서버 연결 시작 & 인증 */
		// ip   = biz.ppurio.com = 211.189.43.25
		// port = 5000 / 15001
		
		String smsServer = GlobalContext.getPropertyString("sms.server", "biz.ppurio.com");
		String smsPort = GlobalContext.getPropertyString("sms.port", "5000");
		String smsUser = GlobalContext.getPropertyString("sms.user", "uengine");
		String smsPwd = GlobalContext.getPropertyString("sms.pwd", "18925ung");
		//bs.doBegin("biz.ppurio.com", 5000, "uengine", "18925ung");
		bs.doBegin(smsServer, Integer.parseInt(smsPort), smsUser, smsPwd);
		/* 메시지 정의 */
		// 다음의 setter 를 사용하여 필요한 정보를 정의
		// ex. SMS 의 경우, MSG_TYPE, DEST_PHONE, SEND_PHONE, MSG_BODY 정보를 정의
		sme = new SendMsgEntity();
		//sme.setCMID      ("");    // 데이터 id
		sme.setMSG_TYPE  (0 );    // 데이터 타입 (SMS 0 / WAP 1 / FAX 2 / PHONE 3 / SMS_INBOUND 4 / MMS 5)
		//sme.setSEND_TIME ("");    // 발송 (예약) 시간 (Unix Time, 정의하지 않을 경우 즉시 발송)
		sme.setDEST_PHONE(mobileno);    // 받는 사람 전화 번호
		sme.setDEST_NAME (empName);    // 받는 사람 이름
		//sme.setSEND_PHONE("025943345");    // 보내는 사람 전화 번호
		sme.setSEND_NAME (comname);    // 보내는 사람 이름
		//sme.setSUBJECT   ("");    // (FAX/MMS) 제목, (SMS_INBOUND) 데이터 내용
		sme.setMSG_BODY  (actualContent);    // 데이터 내용
		//sme.setWAP_URL   ("");    // (WAP) URL 주소
		//sme.setCOVER_FLAG(0 );    // (FAX) 표지 발송 옵션
		//sme.setSMS_FLAG  (0 );    // (PHONE) PHONE 실패 시 문자 전송 옵션
		//sme.setREPLY_FLAG(0 );    // (PHONE) 응답 받기 선택
		//sme.setRETRY_CNT (0 );    // (FAX/PHONE) 재시도 회수 (5~10분 간격: 최대 3회)
		//sme.setFAX_FILE  ("");    // (PHONE/FAX/MMS) 파일 전송시 파일 이름
		//sme.setVXML_FILE ("");    // (PHONE) 음성 시나리오 파일 이름

		/* 메시지 전송 */
		try {
			String msgid = "";
			msgid = bs.sendMsg(sme);    // @param  sme       SendMsgEntity
			                            // @retrun String    다우기술의 서버에서 정의한 message id

			System.out.println("msgid=" + msgid);
		} catch (Exception ex) {
			System.out.println("Failed to Send a Msg" +
					", " + ex.getMessage());
		}

		/* 서버 연결 종료 */
		bs.doEnd();
		
		fireComplete(instance);
		
	}

}

package com.hncis.common.application;

import com.hncis.common.util.StringUtil;
import com.hncis.common.vo.RfcPoCreateVo;
import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.IRepository;
import com.sap.mw.jco.JCO;
import com.sap.mw.jco.JCO.Client;
import com.sap.mw.jco.JCO.Function;
import com.sap.mw.jco.JCO.ParameterList;
import com.sap.mw.jco.JCO.Repository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RfcPoCreate {
    private transient Log logger = LogFactory.getLog(getClass());

	private String targetServer;
	private String host;
	private String client;
	private String user;
	private String passwd;
	private String lang = "EN";
	private String r3name;
	private String group;
	private String sysnr;

	private static final String userInfo = "GASCRFC1";
	private static final String poNp = "PO_NO";
	
	public RfcPoCreate(){
		targetServer = StringUtil.getSystemArea();
		this.initEaiConnections();
	}
	
	/**
	 * SAP와 JCO 커넥션 맺기
	 */
	public Client getConnection() throws JCO.Exception {

		Client connection = null;
		try{
			
			connection = JCO.createClient(
					client,
					user,
					passwd,
					lang,
					host,
					r3name,
					group);
			
			connection.connect();
		}catch (Exception e) {
			logger.info("RFC JCO connect 중 오류발생");
			logger.error("messege", e);
		}
		return connection;
	}
	
	/**
	 * Connection 객체 반환 
	 * (사용 후 반드시 호출 해야 함 try{ ... } catch(Exception e){ .. } finally{ release() }  )
	 * @param connection
	 */
	public void release(Client connection) {
		JCO.releaseClient(connection);
	}
	
	/**
	 * 커넥션 정보 세팅
	 */
	public void initEaiConnections(){
		if( targetServer.equals("REAL") ){
			host       = "10.121.241.14";
			client     = "300";
			user       = userInfo;
			passwd     = userInfo; 
			r3name     = "EPB"; 
			group      = "HMB_PRD"; 
			sysnr      = "00";
		}else{
			host    = "10.121.241.106";
			client 	= "300";
			user	= userInfo;
			passwd 	= userInfo; 
			r3name 	= "EQB"; 
			group 	= "HMB_QAS";
			sysnr   = "00";
		}
	}
	
	public RfcPoCreateVo getResult(RfcPoCreateVo i_PoInfo) throws Exception{
		
		RfcPoCreateVo rfVal = new RfcPoCreateVo();
		RfcPoCreateVo deletResult = new RfcPoCreateVo();
		
		if(!StringUtil.isNullToString(i_PoInfo.getI_po_no()).equals("")){
			deletResult = doPoDelete(i_PoInfo);
		}else{
			deletResult.setO_if_result("Z");
		}
		
		if(StringUtil.isNullToString(deletResult.getO_if_result()).equals("Z")){
			rfVal = doPoCreate(i_PoInfo);
		}else{
			// 삭제오류일 경우 => "D"
			deletResult.setO_if_result("D");
			rfVal = deletResult;
		}
		return rfVal;
	}
	
	public RfcPoCreateVo doPoDelete(RfcPoCreateVo i_PoInfo) throws Exception{
		Client client = getConnection();
		RfcPoCreateVo rfVal = new RfcPoCreateVo();
		
		try {
			IRepository repository = new Repository("MYRepository", client);
	
			IFunctionTemplate ftemplate = repository.getFunctionTemplate("ZHBR_MM_GASC_DELETE_PO");
			Function function = new Function(ftemplate);
			ParameterList in_params = function.getImportParameterList();
			ParameterList out_params = function.getExportParameterList();
			
			in_params.setValue(i_PoInfo.getI_date(),"DATE");
			in_params.setValue(i_PoInfo.getI_po_no(),poNp);
			in_params.setValue(i_PoInfo.getI_po_desc(),"PO_DESC");
		
			logger.info("ZHBR_MM_GASC_DELETE_PO 호출 시작");
			client.execute(function);
			
			logger.info("UPDATE_DT:"+out_params.getValue("UPDATE_DT"));
			logger.info("IF_RESULT:"+out_params.getValue("IF_RESULT"));
			logger.info("IF_FAIL_MSG:"+out_params.getValue("IF_FAIL_MSG"));
			
			rfVal.setO_if_result(out_params.getValue("IF_RESULT").toString());
			rfVal.setO_if_fail_msg(out_params.getValue("IF_FAIL_MSG").toString());
			
		}catch(Exception e){
			logger.info("RFC 호출 중 문제가 발생하였습니다.");
			rfVal.setO_if_result("E");
			rfVal.setO_if_fail_msg(e.toString());
			logger.error("messege", e);
		} finally {
			release(client);
			rfVal.setO_if_result("Z");
		}
		return rfVal;
	}
	
	public RfcPoCreateVo doPoCreate(RfcPoCreateVo i_PoInfo) throws Exception{
		Client client = getConnection();
		RfcPoCreateVo rfVal = new RfcPoCreateVo();
		try {
			JCO.Table output = null;
			IRepository repository = new Repository("MYRepository", client);
			IFunctionTemplate ftemplate = repository.getFunctionTemplate("ZHBR_MM_GASC_PURCHASE_ORDER");
			Function function = new Function(ftemplate);
	
			JCO.Structure in_header = function.getImportParameterList().getStructure("I_PO_HEADER");
			in_header.setValue(i_PoInfo.getI_date(), "DATE");
			in_header.setValue(i_PoInfo.getI_vendor_code(),"VENDOR_CODE");
			in_header.setValue(i_PoInfo.getI_vendor_name(),"VENDOR_NAME");
			in_header.setValue(i_PoInfo.getI_pur_org_code(),"PUR_ORG_CODE");
			in_header.setValue(i_PoInfo.getI_pur_group(),"PUR_GROUP");
			in_header.setValue(i_PoInfo.getI_company_code(),"COMPANY_CODE");
			
			JCO.Table itemTable = function.getTableParameterList().getTable("T_PO_ITEM"); 
			itemTable.appendRow(); 
			itemTable.setValue(i_PoInfo.getI_account_category(), "ACCOUNT_CATEGORY"); 
			itemTable.setValue(i_PoInfo.getI_material_code(), "MATERIAL_CODE"); 
			itemTable.setValue(i_PoInfo.getI_material_desc(),"MATERIAL_DESC"); 
			itemTable.setValue(i_PoInfo.getI_mat_group(),"MAT_GROUP"); 
			itemTable.setValue(i_PoInfo.getI_qty(),"QTY"); 
			itemTable.setValue(i_PoInfo.getI_price(),"PRICE"); 
			itemTable.setValue(i_PoInfo.getI_delivery_date(),"DELIVERY_DATE"); 
			itemTable.setValue(i_PoInfo.getI_cost_cd(),"COST_CD"); 
			itemTable.setValue(i_PoInfo.getI_wbs_cd(),"WBS_CODE"); 
			itemTable.setValue(i_PoInfo.getI_io_cd(),"IO_CODE"); 
			itemTable.setValue(i_PoInfo.getI_account_code(),"ACCOUNT_CODE"); 
			itemTable.setValue(i_PoInfo.getI_wrkplc_cd(),"WRKPLC_CD"); 
			itemTable.setValue(i_PoInfo.getI_usn(),"USN"); 
			
			logger.info("getI_date : " + i_PoInfo.getI_date());
			logger.info("getI_vendor_code : " + i_PoInfo.getI_vendor_code());
			logger.info("getI_vendor_name : " + i_PoInfo.getI_vendor_name());
			logger.info("getI_pur_org_code : " + i_PoInfo.getI_pur_org_code());
			logger.info("getI_pur_group : " + i_PoInfo.getI_pur_group());
			logger.info("getI_company_code : " + i_PoInfo.getI_company_code());
			logger.info("###########################################");
			logger.info("getI_account_category : " + i_PoInfo.getI_account_category());
			logger.info("getI_material_code : " + i_PoInfo.getI_material_code());
			logger.info("getI_material_desc : " + i_PoInfo.getI_material_desc());
			logger.info("getI_mat_group : " + i_PoInfo.getI_mat_group());
			logger.info("getI_qty : " + i_PoInfo.getI_qty());
			logger.info("getI_price : " + i_PoInfo.getI_price());
			logger.info("getI_delivery_date : " + i_PoInfo.getI_delivery_date());
			logger.info("getI_cost_cd : " + i_PoInfo.getI_cost_cd());
			logger.info("getI_wbs_cd : " + i_PoInfo.getI_wbs_cd());
			logger.info("getI_io_cd : " + i_PoInfo.getI_io_cd());
			logger.info("getI_account_code : " + i_PoInfo.getI_account_code());
			logger.info("getI_wrkplc_cd : " + i_PoInfo.getI_wrkplc_cd());
			logger.info("getI_usn : " + i_PoInfo.getI_usn());
			
			ParameterList out_params = function.getExportParameterList();
//			RfcPoCreateVo rfVal = new RfcPoCreateVo();
		
			logger.info("ZHBR_MM_GASC_PURCHASE_ORDER 호출 시작");
			client.execute(function);
			
			
//			rfVal.setO_zspack(output.getValue("ZSPACK").toString());
//			rfVal.setO_if_result(output.getValue("IFRESULT").toString());
//			rfVal.setO_if_fail_msg(output.getValue("IFFAILMSG").toString());
//			rfVal.setO_po_no(out_params.getValue("PO_NO").toString());
			
			if(StringUtil.isNullToString(out_params.getValue(poNp)).equals("")){
				
				output = function.getTableParameterList().getTable("T_IFFAILMSG");
				
				logger.info("ZSPACK:"+output.getValue("ZSPACK"));
				logger.info("IFRESULT:"+output.getValue("IFRESULT"));
				logger.info("IFFAILMSG:"+output.getValue("IFFAILMSG"));
				logger.info("PO_NO:"+out_params.getValue(poNp));
				
				rfVal.setO_zspack(output.getValue("ZSPACK").toString());
				rfVal.setO_if_result(output.getValue("IFRESULT").toString());
				rfVal.setO_if_fail_msg(output.getValue("IFFAILMSG").toString());
				rfVal.setO_po_no(out_params.getValue(poNp).toString());
			}else{
				logger.info("PO_NO:"+out_params.getValue(poNp));
				
				rfVal.setO_po_no(out_params.getValue(poNp).toString());
				rfVal.setO_if_result("Z");
			}
			
//			logger.info("ZSPACK:"+output.getValue("ZSPACK"));
//			logger.info("IFRESULT:"+output.getValue("IFRESULT"));
//			logger.info("IFFAILMSG:"+output.getValue("IFFAILMSG"));
//			logger.info("PO_NO:"+out_params.getValue("PO_NO"));
			
		}catch(Exception e){
			logger.info("RFC 호출 중 문제가 발생하였습니다.");
			rfVal.setO_if_result("E");
			rfVal.setO_if_fail_msg(e.toString());
			logger.error("messege", e);
		} finally {
			release(client);
//			rfVal.setO_if_result("Z");
		}
		return rfVal;
	}
	
	

}

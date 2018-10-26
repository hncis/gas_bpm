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

public class RfcPoCreate {

	private String targetServer;
	private String host;
	private String client;
	private String user;
	private String passwd;
	private String lang = "EN";
	private String r3name;
	private String group;
	private String sysnr;
	
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
			System.out.println("RFC JCO connect 중 오류발생");
			e.printStackTrace();
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
			user       = "GASCRFC1";
			passwd     = "GASCRFC1"; 
			r3name     = "EPB"; 
			group      = "HMB_PRD"; 
			sysnr      = "00";
		}else{
			host    = "10.121.241.106";
			client 	= "300";
			user	= "GASCRFC1";
			passwd 	= "GASCRFC1"; 
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
			in_params.setValue(i_PoInfo.getI_po_no(),"PO_NO");
			in_params.setValue(i_PoInfo.getI_po_desc(),"PO_DESC");
		
			System.out.println("ZHBR_MM_GASC_DELETE_PO 호출 시작");
			client.execute(function);
			
			System.out.println("UPDATE_DT:"+out_params.getValue("UPDATE_DT"));
			System.out.println("IF_RESULT:"+out_params.getValue("IF_RESULT"));
			System.out.println("IF_FAIL_MSG:"+out_params.getValue("IF_FAIL_MSG"));
			
			rfVal.setO_if_result(out_params.getValue("IF_RESULT").toString());
			rfVal.setO_if_fail_msg(out_params.getValue("IF_FAIL_MSG").toString());
			
		}catch(Exception e){
			System.out.println("RFC 호출 중 문제가 발생하였습니다.");
			rfVal.setO_if_result("E");
			rfVal.setO_if_fail_msg(e.toString());
			e.printStackTrace();
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
			
			System.out.println("getI_date : " + i_PoInfo.getI_date());
			System.out.println("getI_vendor_code : " + i_PoInfo.getI_vendor_code());
			System.out.println("getI_vendor_name : " + i_PoInfo.getI_vendor_name());
			System.out.println("getI_pur_org_code : " + i_PoInfo.getI_pur_org_code());
			System.out.println("getI_pur_group : " + i_PoInfo.getI_pur_group());
			System.out.println("getI_company_code : " + i_PoInfo.getI_company_code());
			System.out.println("###########################################");
			System.out.println("getI_account_category : " + i_PoInfo.getI_account_category());
			System.out.println("getI_material_code : " + i_PoInfo.getI_material_code());
			System.out.println("getI_material_desc : " + i_PoInfo.getI_material_desc());
			System.out.println("getI_mat_group : " + i_PoInfo.getI_mat_group());
			System.out.println("getI_qty : " + i_PoInfo.getI_qty());
			System.out.println("getI_price : " + i_PoInfo.getI_price());
			System.out.println("getI_delivery_date : " + i_PoInfo.getI_delivery_date());
			System.out.println("getI_cost_cd : " + i_PoInfo.getI_cost_cd());
			System.out.println("getI_wbs_cd : " + i_PoInfo.getI_wbs_cd());
			System.out.println("getI_io_cd : " + i_PoInfo.getI_io_cd());
			System.out.println("getI_account_code : " + i_PoInfo.getI_account_code());
			System.out.println("getI_wrkplc_cd : " + i_PoInfo.getI_wrkplc_cd());
			System.out.println("getI_usn : " + i_PoInfo.getI_usn());
			
			ParameterList out_params = function.getExportParameterList();
//			RfcPoCreateVo rfVal = new RfcPoCreateVo();
		
			System.out.println("ZHBR_MM_GASC_PURCHASE_ORDER 호출 시작");
			client.execute(function);
			
			
//			rfVal.setO_zspack(output.getValue("ZSPACK").toString());
//			rfVal.setO_if_result(output.getValue("IFRESULT").toString());
//			rfVal.setO_if_fail_msg(output.getValue("IFFAILMSG").toString());
//			rfVal.setO_po_no(out_params.getValue("PO_NO").toString());
			
			if(StringUtil.isNullToString(out_params.getValue("PO_NO")).equals("")){
				
				output = function.getTableParameterList().getTable("T_IFFAILMSG");
				
				System.out.println("ZSPACK:"+output.getValue("ZSPACK"));
				System.out.println("IFRESULT:"+output.getValue("IFRESULT"));
				System.out.println("IFFAILMSG:"+output.getValue("IFFAILMSG"));
				System.out.println("PO_NO:"+out_params.getValue("PO_NO"));
				
				rfVal.setO_zspack(output.getValue("ZSPACK").toString());
				rfVal.setO_if_result(output.getValue("IFRESULT").toString());
				rfVal.setO_if_fail_msg(output.getValue("IFFAILMSG").toString());
				rfVal.setO_po_no(out_params.getValue("PO_NO").toString());
			}else{
				System.out.println("PO_NO:"+out_params.getValue("PO_NO"));
				
				rfVal.setO_po_no(out_params.getValue("PO_NO").toString());
				rfVal.setO_if_result("Z");
			}
			
//			System.out.println("ZSPACK:"+output.getValue("ZSPACK"));
//			System.out.println("IFRESULT:"+output.getValue("IFRESULT"));
//			System.out.println("IFFAILMSG:"+output.getValue("IFFAILMSG"));
//			System.out.println("PO_NO:"+out_params.getValue("PO_NO"));
			
		}catch(Exception e){
			System.out.println("RFC 호출 중 문제가 발생하였습니다.");
			rfVal.setO_if_result("E");
			rfVal.setO_if_fail_msg(e.toString());
			e.printStackTrace();
		} finally {
			release(client);
//			rfVal.setO_if_result("Z");
		}
		return rfVal;
	}
	
	

}

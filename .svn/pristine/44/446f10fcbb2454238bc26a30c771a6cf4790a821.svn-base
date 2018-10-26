package com.hncis.common.application;

import com.hncis.common.util.CurrentDateTime;
import com.hncis.common.util.StringUtil;
import com.hncis.common.vo.RfcPoCreateVo;
import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.IRepository;
import com.sap.mw.jco.JCO;
import com.sap.mw.jco.JCO.Client;
import com.sap.mw.jco.JCO.Function;
import com.sap.mw.jco.JCO.ParameterList;
import com.sap.mw.jco.JCO.Repository;

public class RfcFiCreate {

	private String targetServer;
	private String host;
	private String client;
	private String user;
	private String passwd;
	private String lang = "EN";
	private String r3name;
	private String group;
	private String sysnr;
	
	public RfcFiCreate(){
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
		Client client = getConnection();
		
		try {
			System.out.println("getResult Start");
			
			JCO.Table output = null;
			IRepository repository = new Repository("MYRepository", client);
			IFunctionTemplate ftemplate = repository.getFunctionTemplate("ZHBR_GASC_FI_ACC_CREATE");
			Function function = new Function(ftemplate);
/*
		JCO.Structure in_header = function.getImportParameterList().getStructure("I_PO_HEADER");
		in_header.setValue(i_PoInfo.getI_date(), "DATE");
		in_header.setValue(i_PoInfo.getI_vendor_code(),"VENDOR_CODE");
		in_header.setValue(i_PoInfo.getI_vendor_name(),"VENDOR_NAME");
		in_header.setValue(i_PoInfo.getI_pur_org_code(),"PUR_ORG_CODE");
		in_header.setValue(i_PoInfo.getI_pur_group(),"PUR_GROUP");
		in_header.setValue(i_PoInfo.getI_company_code(),"COMPANY_CODE");
		*/
//		JCO.Table itemTable = function.getTableParameterList().getTable("T_GASC_ITEMS"); 
//		itemTable.appendRow(); 
//		itemTable.setValue("20140206164937937100074F", "BKTXT"); 	// tempCashList.get(j).getDoc_no()+tempCashList.get(j).getEeno()+"F"
//		itemTable.setValue("20160223", "BLDAT"); 					// CurrentDateTime.getDate()
//		itemTable.setValue("20160229", "BUDAT"); 					// CurrentDateTime.getDate(CurrentDateTime.getDate(), 5)
//		itemTable.setValue("37102488","LIFNR"); 					// tempCashList.get(j).getEeno()
//		itemTable.setValue("1","BUZEI"); 							// Integer.toString(j+1)
//		itemTable.setValue("51021030","HKONT"); 					// tempCashList.get(j).getBudg_no()
//		itemTable.setValue("","KOSTL"); 							// tempCashList.get(j).getCost_cd()
//		itemTable.setValue("","AUFNR"); 							// Internal Order
//		itemTable.setValue("S-15820.0002","PROJK"); 				// WBS
//		itemTable.setValue("Taxi (Taxi)","SGTXT"); 					// tempCardList.get(j).getPrvs_dtl_nm()
//		itemTable.setValue("743.06","DMBTR"); 						// df.format(Double.parseDouble(tempCashList.get(j).getNatv_curr_amt()))
		
			JCO.Table itemTable = function.getTableParameterList().getTable("T_GASC_ITEMS"); 
			itemTable.appendRow(); 
			
			itemTable.setValue(i_PoInfo.getI_po_no(), "BKTXT"); 		// tempCashList.get(j).getDoc_no()+tempCashList.get(j).getEeno()+"F"
			itemTable.setValue(CurrentDateTime.getDate(), "BLDAT"); 									// CurrentDateTime.getDate()
			itemTable.setValue(CurrentDateTime.getDate(), "BUDAT"); 		// CurrentDateTime.getDate(CurrentDateTime.getDate(), 5)
			itemTable.setValue(i_PoInfo.getI_usn(),"LIFNR"); 			// tempCashList.get(j).getEeno()
			itemTable.setValue(i_PoInfo.getI_qty(),"BUZEI"); 							// Integer.toString(j+1)
			itemTable.setValue("00" + i_PoInfo.getI_account_code(),"HKONT"); 	// tempCashList.get(j).getBudg_no()
			if("".equals(i_PoInfo.getI_wbs_cd()) && "".equals(i_PoInfo.getI_io_cd())){
				itemTable.setValue(i_PoInfo.getI_cost_cd(),"KOSTL"); 		// tempCashList.get(j).getCost_cd()
			}else{
				itemTable.setValue("","KOSTL");
			}
			itemTable.setValue(i_PoInfo.getI_io_cd(),"AUFNR"); 			// Internal Order
			itemTable.setValue(i_PoInfo.getI_wbs_cd(),"PROJK"); 		// WBS
			itemTable.setValue(i_PoInfo.getI_vendor_name(),"SGTXT"); 	// tempCardList.get(j).getPrvs_dtl_nm()
			itemTable.setValue(i_PoInfo.getI_price(),"DMBTR"); 			// df.format(Double.parseDouble(tempCashList.get(j).getNatv_curr_amt()))
			
			System.out.println("###############################");
			System.out.println(" BKTXT : " + i_PoInfo.getI_po_no());
			System.out.println(" LIFNR : " + i_PoInfo.getI_usn());
			System.out.println(" BUZEI : " + i_PoInfo.getI_qty());
			System.out.println(" BLDAT : " + CurrentDateTime.getDate());
			System.out.println(" BUDAT : " + CurrentDateTime.getDate(CurrentDateTime.getDate(), 5));
			System.out.println(" HKONT : " + i_PoInfo.getI_account_code());
			System.out.println(" KOSTL : " + i_PoInfo.getI_cost_cd());
			System.out.println(" AUFNR : " + i_PoInfo.getI_io_cd());
			System.out.println(" PROJK : " + i_PoInfo.getI_wbs_cd());
			System.out.println(" SGTXT : " + i_PoInfo.getI_vendor_name());
			System.out.println(" DMBTR : " + i_PoInfo.getI_price());
			System.out.println("###############################");
			
			ParameterList out_params = function.getExportParameterList();
		
			System.out.println("ZHBR_GASC_FI_ACC_CREATE 호출 시작");
			client.execute(function);
			
			
			rfVal.setO_if_result(out_params.getValue("IF_RESULT").toString());
			rfVal.setO_if_fail_msg(out_params.getValue("IF_FAIL_MSG").toString());
			rfVal.setO_po_no(out_params.getValue("E_BELNR").toString());
			
			System.out.println("E_BELNR:"+out_params.getValue("E_BELNR"));
			System.out.println("IFRESULT:"+out_params.getValue("IF_RESULT"));
			System.out.println("IFFAILMSG:"+out_params.getValue("IF_FAIL_MSG"));
			
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

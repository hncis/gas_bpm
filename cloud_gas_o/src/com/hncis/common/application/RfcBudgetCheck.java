package com.hncis.common.application;

import com.hncis.common.util.StringUtil;
import com.hncis.common.vo.RfcBudgetCheckVo;
import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.IRepository;
import com.sap.mw.jco.JCO;
import com.sap.mw.jco.JCO.Client;
import com.sap.mw.jco.JCO.Function;
import com.sap.mw.jco.JCO.ParameterList;
import com.sap.mw.jco.JCO.Repository;

public class RfcBudgetCheck {

	private String targetServer;
	private String host;
	private String client;
	private String user;
	private String passwd;
	private String lang = "EN";
	private String r3name;
	private String group;
	private String sysnr;
	
	public RfcBudgetCheck(String gubn){
		targetServer = StringUtil.getSystemArea();
		this.initEaiConnections(gubn);
	}
	
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
	public void initEaiConnections(String gubn){
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
	
	public RfcBudgetCheckVo getResult(RfcBudgetCheckVo i_BudgetInfo) throws Exception{
		
		RfcBudgetCheckVo rfVal = new RfcBudgetCheckVo();
		switch(i_BudgetInfo.getI_gubn()) {
	    	case "D" :
	    		rfVal = getBudgetCheckByDept(i_BudgetInfo);
	            break;
	    	case "W" :
	    		rfVal = getBudgetCheckByWBS(i_BudgetInfo);
	             break;
	    	case "I" :
	    		rfVal = getBudgetCheckByIO(i_BudgetInfo);
	             break;
	    }
		return rfVal;
	}
	
	public RfcBudgetCheckVo getBudgetCheckByDept(RfcBudgetCheckVo i_BudgetInfo) throws Exception{
		Client client = getConnection();
		RfcBudgetCheckVo rfVal = new RfcBudgetCheckVo();

		try {
			IRepository repository = new Repository("MYRepository", client);
	
			IFunctionTemplate ftemplate = repository.getFunctionTemplate("ZHBR_FM_BUDGET_CHECK");
			Function function = new Function(ftemplate);
			ParameterList in_params = function.getImportParameterList();
			ParameterList out_params = function.getExportParameterList();
	
			in_params.setValue("H301","I_FIKRS");
			in_params.setValue(i_BudgetInfo.getI_gjahr(),"I_GJAHR");
			in_params.setValue("000","I_VERSN");
			in_params.setValue(i_BudgetInfo.getI_kostl(),"I_KOSTL");
			in_params.setValue(i_BudgetInfo.getI_hkont(),"I_HKONT");
		
			System.out.println("I_GJAHR : " + i_BudgetInfo.getI_gjahr());
			System.out.println("I_KOSTL : " + i_BudgetInfo.getI_kostl());
			System.out.println("I_HKONT : " + i_BudgetInfo.getI_hkont());
			
			System.out.println("ZHBR_FM_BUDGET_CHECK 호출 시작");
			client.execute(function);
			
			System.out.println("GS_MSG:"+out_params.getValue("GS_MSG"));
			System.out.println("O_ACTUAL:"+out_params.getValue("O_ACTUAL"));
			System.out.println("O_BALANCE:"+out_params.getValue("O_BALANCE"));
			System.out.println("O_BUDGET:"+out_params.getValue("O_BUDGET"));
			System.out.println("O_COMMITMENT:"+out_params.getValue("O_COMMITMENT"));
			System.out.println("O_WAERS:"+out_params.getValue("O_WAERS"));

			
			rfVal.setGs_msg(out_params.getValue("GS_MSG").toString());
			rfVal.setO_actual(out_params.getValue("O_ACTUAL").toString());
			rfVal.setO_balance(out_params.getValue("O_BALANCE").toString());
			rfVal.setO_budget(out_params.getValue("O_BUDGET").toString());
			rfVal.setO_commitment(out_params.getValue("O_COMMITMENT").toString());
			rfVal.setO_waers(out_params.getValue("O_WAERS").toString());
			
		}catch(Exception e){
			System.out.println("RFC 호출 중 문제가 발생하였습니다.");
			e.printStackTrace();
		} finally {
			release(client);
		}
		return rfVal;
	}
	
	public RfcBudgetCheckVo getBudgetCheckByIO(RfcBudgetCheckVo i_BudgetInfo) throws Exception{
		Client client = getConnection();
		RfcBudgetCheckVo rfVal = new RfcBudgetCheckVo();

		try {
			IRepository repository = new Repository("MYRepository", client);
	
			IFunctionTemplate ftemplate = repository.getFunctionTemplate("ZHBR_MM_GASC_BUDGET_BY_IO");
			Function function = new Function(ftemplate);
			ParameterList in_params = function.getImportParameterList();
			ParameterList out_params = function.getExportParameterList();
			
			//in_params.setValue(i_BudgetInfo.getI_date(),"DATE");
			in_params.setValue(i_BudgetInfo.getI_gjahr(),"DATE");
			in_params.setValue(i_BudgetInfo.getI_hkont(),"IO_CODE");
		
			System.out.println("ZHBR_MM_GASC_BUDGET_BY_IO 호출 시작");
			client.execute(function);
			
			System.out.println("UPDATE_DT:"+out_params.getValue("UPDATE_DT"));
			System.out.println("IF_RESULT:"+out_params.getValue("IF_RESULT"));
			System.out.println("IF_FAIL_MSG:"+out_params.getValue("IF_FAIL_MSG"));
			System.out.println("BUDGET:"+out_params.getValue("BUDGET"));
			System.out.println("DESC:"+out_params.getValue("DESC"));
			
			rfVal.setGs_msg(out_params.getValue("IF_FAIL_MSG").toString());
			rfVal.setO_actual(out_params.getValue("IF_RESULT").toString());
			rfVal.setO_balance(out_params.getValue("BUDGET").toString());
			rfVal.setO_commitment(out_params.getValue("DESC").toString());
			
		}catch(Exception e){
			System.out.println("RFC 호출 중 문제가 발생하였습니다.");
			e.printStackTrace();
		} finally {
			release(client);
		}
		return rfVal;
	}
	
	public RfcBudgetCheckVo getBudgetCheckByWBS(RfcBudgetCheckVo i_BudgetInfo) throws Exception{
		Client client = getConnection();
		RfcBudgetCheckVo rfVal = new RfcBudgetCheckVo();

		try {
			IRepository repository = new Repository("MYRepository", client);
	
			IFunctionTemplate ftemplate = repository.getFunctionTemplate("ZHBR_MM_GASC_BUDGET_BY_WBS");
			Function function = new Function(ftemplate);
			ParameterList in_params = function.getImportParameterList();
			ParameterList out_params = function.getExportParameterList();
			
			//in_params.setValue(i_BudgetInfo.getI_date(),"DATE");
			in_params.setValue(i_BudgetInfo.getI_gjahr(),"DATE");
			in_params.setValue(i_BudgetInfo.getI_hkont(),"WBS_CODE");
		
			System.out.println("ZHBR_MM_GASC_BUDGET_BY_WBS 호출 시작");
			client.execute(function);
			
			System.out.println("UPDATE_DT:"+out_params.getValue("UPDATE_DT"));
			System.out.println("IF_RESULT:"+out_params.getValue("IF_RESULT"));
			System.out.println("IF_FAIL_MSG:"+out_params.getValue("IF_FAIL_MSG"));
			System.out.println("BUDGET:"+out_params.getValue("BUDGET"));
			System.out.println("DESC:"+out_params.getValue("DESC"));

			rfVal.setGs_msg(out_params.getValue("IF_FAIL_MSG").toString());
			rfVal.setO_actual(out_params.getValue("IF_RESULT").toString());
			rfVal.setO_balance(out_params.getValue("BUDGET").toString());
			rfVal.setO_commitment(out_params.getValue("DESC").toString());
			
		}catch(Exception e){
			System.out.println("RFC 호출 중 문제가 발생하였습니다.");
			e.printStackTrace();
		} finally {
			release(client);
		}
		return rfVal;
	}
}

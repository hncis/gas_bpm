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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RfcBudgetCheck {
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

	private static final String strResult = "IF_RESULT";
	private static final String strFailMsg = "IF_FAIL_MSG";
	private static final String strBudget = "BUDGET";
	private static final String strDesc = "DESC";
	
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
			
			logger.info("I_GJAHR : " + i_BudgetInfo.getI_gjahr());
			logger.info("I_KOSTL : " + i_BudgetInfo.getI_kostl());
			logger.info("I_HKONT : " + i_BudgetInfo.getI_hkont());
			
			logger.info("ZHBR_FM_BUDGET_CHECK 호출 시작");
			client.execute(function);
			
			logger.info("GS_MSG:"+out_params.getValue("GS_MSG"));
			logger.info("O_ACTUAL:"+out_params.getValue("O_ACTUAL"));
			logger.info("O_BALANCE:"+out_params.getValue("O_BALANCE"));
			logger.info("O_BUDGET:"+out_params.getValue("O_BUDGET"));
			logger.info("O_COMMITMENT:"+out_params.getValue("O_COMMITMENT"));
			logger.info("O_WAERS:"+out_params.getValue("O_WAERS"));

			
			rfVal.setGs_msg(out_params.getValue("GS_MSG").toString());
			rfVal.setO_actual(out_params.getValue("O_ACTUAL").toString());
			rfVal.setO_balance(out_params.getValue("O_BALANCE").toString());
			rfVal.setO_budget(out_params.getValue("O_BUDGET").toString());
			rfVal.setO_commitment(out_params.getValue("O_COMMITMENT").toString());
			rfVal.setO_waers(out_params.getValue("O_WAERS").toString());
			
		}catch(Exception e){
			logger.info("RFC 호출 중 문제가 발생하였습니다.");
			logger.error("messege", e);
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
		
			logger.info("ZHBR_MM_GASC_BUDGET_BY_IO 호출 시작");
			client.execute(function);
			
			logger.info("UPDATE_DT:"+out_params.getValue("UPDATE_DT"));
			logger.info("IF_RESULT:"+out_params.getValue(strResult));
			logger.info("IF_FAIL_MSG:"+out_params.getValue(strFailMsg));
			logger.info("BUDGET:"+out_params.getValue(strBudget));
			logger.info("DESC:"+out_params.getValue(strDesc));
			
			rfVal.setGs_msg(out_params.getValue(strFailMsg).toString());
			rfVal.setO_actual(out_params.getValue(strResult).toString());
			rfVal.setO_balance(out_params.getValue(strBudget).toString());
			rfVal.setO_commitment(out_params.getValue(strDesc).toString());
			
		}catch(Exception e){
			logger.info("RFC 호출 중 문제가 발생하였습니다.");
			logger.error("messege", e);
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
		
			logger.info("ZHBR_MM_GASC_BUDGET_BY_WBS 호출 시작");
			client.execute(function);
			
			logger.info("UPDATE_DT:"+out_params.getValue("UPDATE_DT"));
			logger.info("IF_RESULT:"+out_params.getValue(strResult));
			logger.info("IF_FAIL_MSG:"+out_params.getValue(strFailMsg));
			logger.info("BUDGET:"+out_params.getValue(strBudget));
			logger.info("DESC:"+out_params.getValue(strDesc));

			rfVal.setGs_msg(out_params.getValue(strFailMsg).toString());
			rfVal.setO_actual(out_params.getValue(strResult).toString());
			rfVal.setO_balance(out_params.getValue(strBudget).toString());
			rfVal.setO_commitment(out_params.getValue(strDesc).toString());
			
		}catch(Exception e){
			logger.info("RFC 호출 중 문제가 발생하였습니다.");
			logger.error("messege", e);
		} finally {
			release(client);
		}
		return rfVal;
	}
}

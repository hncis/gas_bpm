package kr.go.nyj.util; 

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import kr.go.nyj.components.activityfilters.AtMessengerCommunicator;

import org.apache.log4j.Logger;
import org.uengine.kernel.Activity;
import org.uengine.kernel.ComplexActivity;
import org.uengine.kernel.HumanActivity;
import org.uengine.kernel.ProcessDefinition;
import org.uengine.kernel.ProcessInstance;
import org.uengine.kernel.RoleMapping;
import org.uengine.kernel.RoleParameterContext;
import org.uengine.kernel.SubProcessActivity;
import org.uengine.processmanager.ProcessManagerBean;
import org.uengine.processmanager.ProcessManagerRemote;
import org.uengine.processmanager.ProcessTransactionContext;
import org.uengine.processmanager.TransactionContext;
import org.uengine.util.ActivityForLoop;
import org.uengine.util.UEngineUtil;
import org.uengine.util.dao.ConnectiveDAO;
import org.uengine.util.dao.IDAO;

/**
 * <pre>
 * kr.go.nyj.uti 
 *    |_ CommonUtil.java
 * 
 * </pre>
 * @date : 2016. 11. 11. 오후 3:40:45
 * @version : 
 * @author : freshka
 */
/**
 * <pre>
 * kr.go.nyj.uti 
 * CommonUtil.java
 * 
 * </pre>
 * @date : 2016. 11. 11. 오후 3:40:45
 * @version : 
 * @author : freshka
 */
public class CommonUtil {

	private static final Logger logger = Logger.getLogger(CommonUtil.class);
	
	/**
	 * <pre>
	 * 1. 개요 : 프로세스 디자이너에서 디플로이 처리 후 프로세스 정의에 대한 정보를 아래 처리내용과 같이 처리
	 * 2. 처리내용 : 1. 프로세스 정보가 신규 프로세스 일 경우
	 * 					1-1. NYJ_PROCESS 테이블에 신규 프로세스 정보 INSERT 처리
	 * 				 2. 프로세스 정보가 신규 프로세스가 아닐 경우
	 * 					2-1. 메소드 파라미터 중 전달 받은 최신 버전 자동 설정 여부("autoProduction")에 값이 "true" 일 경우
	 * 						2-1-1. NYJ_PROCESS 테이블에서 기존 프로세스 정보 UPDATE 처리
	 * 				 3. 메소드 파라미터 중 전달 받은 최신 버전 자동 설정 여부("autoProduction")에 값이 "true" 일 경우
	 * 					3-1. NYJ_ACTIVITY 테이블에서 기존 액티비티 정보 DELETE 처리
	 * 					3-2. NYJ_ACTIVITY 테이블에 신규 액티비티 정보 INSERT 처리
	 * 				 4. 프로세스 승인자 정보 처리
	 * 				 5. 프로세스 모델러 정보 처리
	 * 				 6. 프로세스 검토자 정보 처리
	 * </pre>
	 *
	 * @Method Name : setProcessDefinitionToNYJ
	 * @date : 2016. 11. 11.
	 * @author : freshka
	 * @history :
	 *          ----------------------------------------------------------------
	 *          ------- 변경일 작성자 변경내용 ----------- -------------------
	 *          --------------------------------------- 2016. 11. 11. freshka
	 *          최초 작성
	 *          ------------------------------------------------------------
	 *          -----------
	 * 
	 * @param parameterMap
	 * @param pmr
	 * @param defVerId
	 * @return
	 * @throws Exception
	 */
	public static void setProcessDefinitionToNYJ(Map<String,String> parameterMap, ProcessManagerBean pmr, String defVerId)
			throws Exception {
		TransactionContext tc = ((ProcessManagerBean)pmr).getTransactionContext();
		PreparedStatement psmt = null;
		
		defVerId = defVerId.split("@")[1];
		String alias = parameterMap.get("alias"); //프로세스코드
		String definitionName = parameterMap.get("definitionName"); //프로세스 명
		String folderId = parameterMap.get("folderId"); //폴더 ID
		String author = parameterMap.get("author"); //관리자 ID
		boolean isAutoProduction = "true".equals(parameterMap.get("autoProduction")); //최신버전자동설정여부
		String belongingDefinitionId = parameterMap.get("defId"); //프로세스정의 ID
		String folderName = null; //상위폴더 명
		IDAO idao = null;
		
		if (UEngineUtil.isNotEmpty(folderId)) {
			StringBuffer folderFindSql = new StringBuffer();
			folderFindSql = folderFindSql.append("SELECT name FROM bpm_procdef WHERE defid = ?defid");
			idao = ConnectiveDAO.createDAOImpl(tc, folderFindSql.toString(), IDAO.class);
			idao.set("defid", folderId);
			idao.select();
			
			if (idao.next()) {
				folderName = idao.getString("name");
			}
		}
		boolean isNew = false; //신규프로세스여부
		StringBuffer defFindSql = new StringBuffer();
		defFindSql = defFindSql.append("SELECT COUNT(*) AS totalcount FROM nyj_process WHERE process_id = ?process_id");
		idao = ConnectiveDAO.createDAOImpl(tc, defFindSql.toString(), IDAO.class);
		idao.set("process_id", alias);
		idao.select();
		
		if (idao.next()) {
			isNew = (idao.getInt("totalcount") == 0);
		}
		
		if (isNew) {
			StringBuffer sqlDef = new StringBuffer();
			sqlDef.append(" INSERT INTO nyj_process			\n");
			sqlDef.append("			 (						\n");
			sqlDef.append("			   process_id,			\n");
			sqlDef.append("		       upper_task_name,		\n");
			sqlDef.append("   	       process_name,     	\n");
			sqlDef.append("   	       process_no,       	\n");
			sqlDef.append("   		   activate_yn,      	\n");
			sqlDef.append("   		   register_date,    	\n");
			sqlDef.append("   		   register_user_id, 	\n");
			sqlDef.append("   		   update_date,     	\n");
			sqlDef.append("   		   update_user_id		\n");
			sqlDef.append("			 )						\n");
			sqlDef.append("		   VALUES					\n");
			sqlDef.append("		     (						\n");
			sqlDef.append("     	   ?, 					\n");
			sqlDef.append("            ?, 					\n");
			sqlDef.append("   		   ?, 					\n");
			sqlDef.append("   		   ?,					\n");
			sqlDef.append("   		   ?, 					\n");
			sqlDef.append("   		   SYSDATE, 			\n");
			sqlDef.append("   		   ?, 					\n");
			sqlDef.append("   		   SYSDATE, 			\n");
			sqlDef.append("   		   ?					\n");
			sqlDef.append("			 )						\n");
			
			try {
				int idx = 0;
				psmt = tc.getConnection().prepareStatement(sqlDef.toString());
				psmt.setString(++idx, alias); //프로세스코드 (process_id)
				psmt.setString(++idx, folderName); //상위폴더 명 (upper_task_name)
				psmt.setString(++idx, definitionName); //프로세스 명 (process_name)
				psmt.setString(++idx, belongingDefinitionId); //프로세스정의 ID (process_no)
				psmt.setString(++idx, "Y"); //활성화여부 (activate_yn)
				psmt.setString(++idx, author); //관리자 ID (register_user_id)
				psmt.setString(++idx, author); //관리자 ID (update_user_id)
				psmt.executeUpdate();
			} catch (Exception e) {
				logger.error("error Message From setProcessDefinitionToNYJ : " + e);
				e.printStackTrace();
				try {
					throw new Exception("error Message From setProcessDefinitionToNYJ : " + e);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			} finally {
				if (psmt != null) {
					psmt.close();
				}
			}
		} else {
			if (isAutoProduction) {
				StringBuffer sqlDef = new StringBuffer();
				sqlDef.append(" UPDATE nyj_process				\n");
				sqlDef.append("	   SET upper_task_name = ?,		\n");
				sqlDef.append("   	   process_name = ?,    	\n");
				sqlDef.append("   	   process_no = ?,         	\n");
				sqlDef.append("   	   activate_yn = ?,         \n");
				sqlDef.append("   	   update_date = SYSDATE,   \n");
				sqlDef.append("   	   update_user_id = ?   	\n");
				sqlDef.append("  WHERE process_id = ? 			\n");
				
				try {
					int idx = 0;
					psmt = tc.getConnection().prepareStatement(sqlDef.toString());
					psmt.setString(++idx, folderName); //상위폴더 명 (upper_task_name)
					psmt.setString(++idx, definitionName); //프로세스 명 (process_name)
					psmt.setString(++idx, belongingDefinitionId); //프로세스정의 ID (process_no)
					psmt.setString(++idx, "Y"); //활성화여부 (activate_yn)
					psmt.setString(++idx, author); //관리자 ID (update_user_id)
					psmt.setString(++idx, alias); //프로세스코드 (process_id)
					psmt.executeUpdate();
				} catch (Exception e) {
					logger.error("error Message From setProcessDefinitionToNYJ : " + e);
					e.printStackTrace();
					try {
						throw new Exception("error Message From setProcessDefinitionToNYJ : " + e);
					} catch (Exception e1) {
						e1.printStackTrace(); 
					}
				} finally {
					if (psmt != null) {
						psmt.close();
					}
				}
			}
		}
		
		if (isAutoProduction) {
			idao = ConnectiveDAO.createDAOImpl(tc, "DELETE FROM nyj_activity WHERE process_id = ?process_id", IDAO.class);
			idao.set("process_id", alias);
			idao.update();
			
			StringBuffer activitySql = new StringBuffer();
			activitySql.append(" INSERT INTO nyj_activity			\n");
			activitySql.append("		  (                         \n");
			activitySql.append("    		process_id,				\n");
			activitySql.append("   			activity_id,        	\n");
			activitySql.append("   			activity_name,      	\n");
			activitySql.append("   			activity_sequence,  	\n");
			activitySql.append("   			activity_owner,       	\n");
			activitySql.append("   			description,            \n");
			activitySql.append("   			activate_yn,            \n");
			activitySql.append("   			status,        		    \n");
			activitySql.append("   			is_sub_process,         \n");
			activitySql.append("   			register_date,     	    \n");
			activitySql.append("   			register_user_id, 		\n");
			activitySql.append("   			update_date,     	    \n");
			activitySql.append("   			update_user_id  		\n");
			activitySql.append("		  )     					\n");
			activitySql.append("	    VALUES                      \n");
			activitySql.append("	      (                         \n");
			activitySql.append("	        ?,                      \n");
			activitySql.append("			?,                      \n");
			activitySql.append("			?,                      \n");
			activitySql.append("			?,                      \n");
			activitySql.append("			?,                      \n");
			activitySql.append("			?,                      \n");
			activitySql.append("			?,                      \n");
			activitySql.append("			?,                      \n");
			activitySql.append("			?,                      \n");
			activitySql.append("			SYSDATE,                \n");
			activitySql.append("			?,                      \n");
			activitySql.append("			SYSDATE,                \n");
			activitySql.append("			?                       \n");
			activitySql.append("          )                         \n");
			
			try {
				psmt = tc.getConnection().prepareStatement(activitySql.toString());
				List<Activity> activities = getAllChildHumanActivitiesAndSubProcessActivity(pmr.getProcessDefinition(defVerId), pmr);
				for (int i = 0; i < activities.size(); i++) {
					Activity currAct = activities.get(i);
					HumanActivity currHumanAct = null;
					SubProcessActivity currSubAct = null;
					int idx = 0;
					psmt.clearParameters();
					
					if (currAct instanceof HumanActivity) {
						currHumanAct = (HumanActivity)currAct;
						psmt.setString(++idx, alias); //프로세스코드 (process_id)
						psmt.setString(++idx, currHumanAct.getExtValue1()); //액티비티코드 (activity_id)
						psmt.setString(++idx, currHumanAct.getName().getText()); //액티비티 명 (activity_name)
						psmt.setString(++idx, Integer.toString((i+1))); //액티비티번호 (activity_sequence)
						psmt.setString(++idx, currHumanAct.getRole().getDisplayName().getText()); //역할 명 (activity_owner)
						psmt.setString(++idx, currHumanAct.getDescription().getText()); //설명 (description)
						psmt.setString(++idx, "Y"); //활성화여부 (activate_yn)
						psmt.setString(++idx, currHumanAct.getStatusCode()); //상태코드 (status)
						psmt.setString(++idx, "0"); //서브프로세스여부 (is_sub_process)
						psmt.setString(++idx, author); //관리자 ID (register_user_id)
						psmt.setString(++idx, author); //관리자 ID (update_user_id)
					} else if (currAct instanceof SubProcessActivity) {
						currSubAct = (SubProcessActivity)currAct;
						//서브프로세스 액티비티에 설명 ("Description") 값을 ","로 Split 처리하여, 서브프로세스 액티비티코드와 설명을 각각 세팅
						String[] splitVar = currSubAct.getDescription().getText().split(",");
						psmt.setString(++idx, alias); //프로세스코드 (process_id)
						psmt.setString(++idx, splitVar[0]); //액티비티코드 (activity_id)
						psmt.setString(++idx, currSubAct.getName().getText()); //액티비티 명 (activity_name)
						psmt.setString(++idx, Integer.toString((i+1))); //액티비티번호 (activity_sequence)
						String SubProcessActivityOwner = null;
						Iterator<RoleParameterContext> r = Arrays.asList(currSubAct.getRoleBindings()).iterator();
						
						while (r.hasNext()) {
							RoleParameterContext context = r.next();
							if (context.getRole() != null) {
								SubProcessActivityOwner = context.getRole().getDisplayName().getText();
								if (!UEngineUtil.isNotEmpty(SubProcessActivityOwner)) {
									continue;
								}
							}
						}
						psmt.setString(++idx, SubProcessActivityOwner); //역할 명 (activity_owner)
						psmt.setString(++idx, splitVar[1]); //설명 (description)
						psmt.setString(++idx, "Y"); //활성화여부 (activate_yn)
						psmt.setString(++idx, currSubAct.getStatusCode()); //상태코드 (status)
						psmt.setString(++idx, "1"); //서브프로세스여부 (is_sub_process)
						psmt.setString(++idx, author); //관리자 ID (register_user_id)
						psmt.setString(++idx, author); //관리자 ID (update_user_id)
					}
					psmt.addBatch();
				}
				psmt.executeBatch();
			} catch (Exception e) {
				logger.error("error Message From setProcessDefinitionToNYJ : " + e);
				e.printStackTrace();
				try {
					throw new Exception("error Message From setProcessDefinitionToNYJ : " + e);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			} finally {
				if (psmt != null) {
					psmt.close();
				}
			}
		}
		setProcessOwnerToNYJ(pmr, alias, folderId, author, isAutoProduction, isNew); //프로세스 승인자 정보 처리
		setProcessModellerToNYJ(pmr, alias, folderId, author, isAutoProduction, isNew); //프로세스 모델러 정보 처리
		setProcessReviewerToNYJ(pmr, alias, folderId, author, isAutoProduction, isNew); //프로세스 검토자 정보 처리
	}
	
	/**
	 * <pre>
	 * 1. 개요 : 프로세스 승인자에 대한 정보를 아래 처리내용과 같이 처리
 	 * 2. 처리내용 : 1. 해당 프로세스 주관부서에 직급이 과장인 사용자 정보가 있을 경우
 	 * 					1-1. 프로세스 승인자 정보가 신규 일 경우
	 * 						1-1-1. NYJ_PROCESS_OWNER 테이블에 신규 프로세스 승인자 정보 INSERT 처리(승인자 ID)
	 * 					1-2. 프로세스 승인자 정보가 신규가 아닐 경우
	 * 						1-2-1. 메소드 파라미터 중 전달 받은 최신 버전 자동 설정 여부("autoProduction")에 값이 "true" 일 경우
	 * 							1-2-1-1. NYJ_PROCESS_OWNER 테이블에서 기존 프로세스 승인자 정보 UPDATE 처리(승인자 ID)
	 * 				 2. 해당 프로세스 주관부서에 직급이 과장인 사용자 정보가 없을 경우
	 * 					2-1. 프로세스 승인자 정보가 신규 일 경우
	 * 						2-1-1. NYJ_PROCESS_OWNER 테이블에 신규 프로세스 승인자 정보 INSERT 처리(관리자 ID)
	 * 					2-2. 프로세스 승인자 정보가 신규가 아닐 경우
	 * 						2-2-1. 메소드 파라미터 중 전달 받은 최신 버전 자동 설정 여부("autoProduction")에 값이 "true" 일 경우
	 * 							2-2-1-1. NYJ_PROCESS_OWNER 테이블에서 기존 프로세스 승인자 정보 UPDATE 처리(관리자 ID)
	 * </pre>
	 *
	 * @Method Name : setProcessOwnerToNYJ
	 * @date : 2017. 1. 16.
	 * @author : chonk_Enki
	 * @history :
	 *          ----------------------------------------------------------------
	 *          ------- 변경일 작성자 변경내용 ----------- -------------------
	 *          --------------------------------------- 2017. 1. 16. chonk_Enki
	 *          최초 작성
	 *          ------------------------------------------------------------
	 *          -----------
	 * 
	 * @param pmr
	 * @param alias
	 * @param folderId
	 * @param author
	 * @param isAutoProduction
	 * @param isNew
	 * @return
	 * @throws Exception
	 */
	public static void setProcessOwnerToNYJ(ProcessManagerBean pmr, String alias, String folderId, String author, boolean isAutoProduction, boolean isNew)
			throws Exception {
		TransactionContext tc = ((ProcessManagerBean)pmr).getTransactionContext();
		PreparedStatement psmt = null;
		
		StringBuffer defOwnerFindSql = new StringBuffer();
		defOwnerFindSql = defOwnerFindSql.append("SELECT COUNT(*) AS totalcount FROM nyj_process_owner WHERE process_id = ?process_id");
		
		IDAO idao = ConnectiveDAO.createDAOImpl(tc, defOwnerFindSql.toString(), IDAO.class);
		idao.set("process_id", alias);
		idao.select();
		
		if (idao.next()) {
			isNew = (idao.getInt("totalcount") == 0);
		}
		String processDefaultUserId = null;
		StringBuffer defOwnerIDFindSql = new StringBuffer();
		defOwnerIDFindSql = defOwnerIDFindSql.append("SELECT logon_id FROM nyj_user WHERE posit_code = '0013600' AND orgin_blg_dep_code IN (SELECT alias FROM bpm_procdef WHERE defid = ?defid)");
		idao = ConnectiveDAO.createDAOImpl(tc, defOwnerIDFindSql.toString(), IDAO.class);
		idao.set("defid", folderId);
		idao.select();
		
		if (idao.next()) {
			processDefaultUserId = idao.getString("logon_id");
		}
		
		if (UEngineUtil.isNotEmpty(processDefaultUserId)) {
			if (isNew) {
				StringBuffer sqlDef = new StringBuffer();
				sqlDef.append(" INSERT INTO nyj_process_owner		\n");
				sqlDef.append("			 (							\n");
				sqlDef.append("     	   user_id,	 				\n");
				sqlDef.append("   		   process_id, 				\n");
				sqlDef.append("   		   activate_yn, 			\n");
				sqlDef.append("            register_date,			\n");
				sqlDef.append("            register_user_id,		\n");
				sqlDef.append("            update_date,				\n");
				sqlDef.append("            update_user_id			\n");
				sqlDef.append("			 )							\n");
				sqlDef.append("		   VALUES						\n");
				sqlDef.append("		     (							\n");
				sqlDef.append("            ?, 						\n");
				sqlDef.append("            ?,						\n");
				sqlDef.append("            ?, 						\n");
				sqlDef.append("            SYSDATE, 				\n");
				sqlDef.append("            ?, 						\n");
				sqlDef.append("            SYSDATE, 				\n");
				sqlDef.append("            ? 						\n");
				sqlDef.append("		     )							\n");
				
				try {
					int idx = 0;
					psmt = tc.getConnection().prepareStatement(sqlDef.toString());
					psmt.setString(++idx, processDefaultUserId); //승인자 ID (user_id)
					psmt.setString(++idx, alias); //프로세스코드 (process_id)
					psmt.setString(++idx, "Y"); //활성화여부 (activate_yn)
					psmt.setString(++idx, author); //관리자 ID (register_user_id)
					psmt.setString(++idx, author); //관리자 ID (update_user_id)
					psmt.executeUpdate();
				} catch (Exception e) {
					logger.error("error Message From setProcessOwnerToNYJ : " + e);
					e.printStackTrace();
					try {
						throw new Exception("error Message From setProcessOwnerToNYJ : " + e);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} finally {
					if (psmt != null) {
						psmt.close();
					}
				}
			} else {
				if (isAutoProduction) {
					try {
						StringBuffer sqlDef = new StringBuffer();
						sqlDef.append(" UPDATE nyj_process_owner		\n");
						sqlDef.append("	   SET user_id = ?,       		\n");
						sqlDef.append("    	   process_id = ?,         	\n");
						sqlDef.append("    	   activate_yn = ?,         \n");
						sqlDef.append("   	   update_date = SYSDATE,   \n");
						sqlDef.append("   	   update_user_id = ?   	\n");
						sqlDef.append("	 WHERE process_id = ? 			\n");
						
						int idx = 0;
						psmt = tc.getConnection().prepareStatement(sqlDef.toString());
						psmt.setString(++idx, processDefaultUserId); //승인자 ID (user_id)
						psmt.setString(++idx, alias); //프로세스코드 (process_id)
						psmt.setString(++idx, "Y"); //활성화여부 (activate_yn)
						psmt.setString(++idx, author); //관리자 ID (update_user_id)
						psmt.setString(++idx, alias); //프로세스코드 (process_id)
						psmt.executeUpdate();
					} catch (Exception e) {
						logger.error("error Message From setProcessOwnerToNYJ : " + e);
						e.printStackTrace();
						try {
							throw new Exception("error Message From setProcessOwnerToNYJ : " + e);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					} finally {
						if (psmt != null) {
							psmt.close();
						}
					}
				}
			}
		} else {
			if (isNew) {
				StringBuffer sqlDef = new StringBuffer();
				sqlDef.append(" INSERT INTO nyj_process_owner		\n");
				sqlDef.append("			 (							\n");
				sqlDef.append("     	   user_id,					\n");
				sqlDef.append("   	 	   process_id,				\n");
				sqlDef.append("    		   activate_yn,				\n");
				sqlDef.append("    	   	   register_date,			\n");
				sqlDef.append("            register_user_id,		\n");
				sqlDef.append("            update_date,             \n");
				sqlDef.append("   		   update_user_id			\n");
				sqlDef.append("			 )							\n");
				sqlDef.append("		   VALUES						\n");
				sqlDef.append("		     (							\n");
				sqlDef.append("            ?, 						\n");
				sqlDef.append("            ?,						\n");
				sqlDef.append("            ?, 						\n");
				sqlDef.append("            SYSDATE, 				\n");
				sqlDef.append("            ?, 						\n");
				sqlDef.append("            SYSDATE, 				\n");
				sqlDef.append("            ? 						\n");
				sqlDef.append("		     )							\n");
				
				try {				
					int idx = 0;
					psmt = tc.getConnection().prepareStatement(sqlDef.toString());
					psmt.setString(++idx, author); //관리자 ID (user_id)
					psmt.setString(++idx, alias); //프로세스코드 (process_id)
					psmt.setString(++idx, "Y"); //활성화여부 (activate_yn)
					psmt.setString(++idx, author); //관리자 ID (register_user_id)
					psmt.setString(++idx, author); //관리자 ID (update_user_id)
					psmt.executeUpdate();
				} catch (Exception e) {
					logger.error("error Message From setProcessOwnerToNYJ : " + e);
					e.printStackTrace();
					try {
						throw new Exception("error Message From setProcessOwnerToNYJ : " + e);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} finally {
					if (psmt != null) {
						psmt.close();
					}
				}
			} else {
				if (isAutoProduction) {
					try {
						StringBuffer sqlDef = new StringBuffer();
						sqlDef.append(" UPDATE nyj_process_owner		\n");
						sqlDef.append("	   SET user_id = ?,       		\n");
						sqlDef.append("   	   process_id = ?,         	\n");
						sqlDef.append("   	   activate_yn = ?,         \n");
						sqlDef.append("   	   update_date = SYSDATE,   \n");
						sqlDef.append("   	   update_user_id = ?   	\n");
						sqlDef.append("	 WHERE process_id = ? 			\n");
						
						int idx = 0;
						psmt = tc.getConnection().prepareStatement(sqlDef.toString());
						psmt.setString(++idx, author);  //관리자 ID (user_id)
						psmt.setString(++idx, alias); //프로세스코드 (process_id)
						psmt.setString(++idx, "Y"); //활성화여부 (activate_yn)
						psmt.setString(++idx, author); //관리자 ID (update_user_id)
						psmt.setString(++idx, alias); //프로세스코드 (process_id)
						psmt.executeUpdate();
					} catch (Exception e) {
						logger.error("error Message From setProcessOwnerToNYJ : " + e);
						e.printStackTrace();
						try {
							throw new Exception("error Message From setProcessOwnerToNYJ : " + e);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					} finally {
						if (psmt != null) {
							psmt.close();
						}
					}
				}
			}
		}
	}
	
	/**
	 * <pre>
	 * 1. 개요 : 프로세스 모델러에 대한 정보를 아래 처리내용과 같이 처리
	 * 2. 처리내용 : 1. 해당 프로세스 주관부서에 직급이 과장인 사용자 정보가 있을 경우
 	 * 					1-1. 프로세스 모델러 정보가 신규 일 경우
	 * 						1-1-1. NYJ_PROCESS_MODELLER 테이블에 신규 프로세스 모델러 정보 INSERT 처리(모델러 ID)
	 * 					1-2. 프로세스 모델러 정보가 신규가 아닐 경우
	 * 						1-2-1. 메소드 파라미터 중 전달 받은 최신 버전 자동 설정 여부("autoProduction")에 값이 "true" 일 경우
	 * 							1-2-1-1. NYJ_PROCESS_MODELLER 테이블에서 기존 프로세스 모델러 정보 UPDATE 처리(모델러 ID)
	 * 				 2. 해당 프로세스 주관부서에 직급이 과장인 사용자 정보가 없을 경우
	 * 					2-1. 프로세스 모델러 정보가 신규 일 경우
	 * 						2-1-1. NYJ_PROCESS_MODELLER 테이블에 신규 프로세스 모델러 정보 INSERT 처리(관리자 ID)
	 * 					2-2. 프로세스 모델러 정보가 신규가 아닐 경우
	 * 						2-2-1. 메소드 파라미터 중 전달 받은 최신 버전 자동 설정 여부("autoProduction")에 값이 "true" 일 경우
	 * 							2-2-1-1. NYJ_PROCESS_MODELLER 테이블에서 기존 프로세스 모델러 정보 UPDATE 처리(관리자 ID)
	 * </pre>
	 *
	 * @Method Name : setProcessModellerToNYJ
	 * @date : 2017. 1. 16.
	 * @author : chonk_Enki
	 * @history :
	 *          ----------------------------------------------------------------
	 *          ------- 변경일 작성자 변경내용 ----------- -------------------
	 *          --------------------------------------- 2017. 1. 16. chonk_Enki
	 *          최초 작성
	 *          ------------------------------------------------------------
	 *          -----------
	 * 
	 * @param pmr
	 * @param alias
	 * @param folderId
	 * @param author
	 * @param isAutoProduction
	 * @param isNew
	 * @return
	 * @throws Exception
	 */
	public static void setProcessModellerToNYJ(ProcessManagerBean pmr, String alias, String folderId, String author, boolean isAutoProduction, boolean isNew)
			throws Exception {
		TransactionContext tc = ((ProcessManagerBean)pmr).getTransactionContext();
		PreparedStatement psmt = null;

		StringBuffer defModellerFindSql = new StringBuffer();
		defModellerFindSql = defModellerFindSql.append("SELECT COUNT(*) AS totalcount FROM nyj_process_modeller WHERE process_id = ?process_id");
		
		IDAO idao = ConnectiveDAO.createDAOImpl(tc, defModellerFindSql.toString(), IDAO.class);
		idao.set("process_id", alias);
		idao.select();
		
		if (idao.next()) {
			isNew = (idao.getInt("totalcount") == 0);
		}
		String processDefaultUserId = null;
		StringBuffer defModellerIDFindSql = new StringBuffer();
		defModellerIDFindSql = defModellerIDFindSql.append("SELECT logon_id FROM nyj_user WHERE posit_code = '0013600' AND orgin_blg_dep_code IN (SELECT alias FROM bpm_procdef WHERE defid = ?defid)");
		idao = ConnectiveDAO.createDAOImpl(tc, defModellerIDFindSql.toString(), IDAO.class);
		idao.set("defid", folderId);
		idao.select();
		
		if (idao.next()) {
			processDefaultUserId = idao.getString("logon_id");
		}
		
		if (UEngineUtil.isNotEmpty(processDefaultUserId)) {
			if (isNew) {
				StringBuffer sqlDef = new StringBuffer();
				sqlDef.append(" INSERT INTO nyj_process_modeller	\n");
				sqlDef.append("		     (							\n");
				sqlDef.append("     	   modeller_id,	 			\n");
				sqlDef.append("            process_id, 				\n");
				sqlDef.append("            activate_yn, 			\n");
				sqlDef.append("            register_date, 			\n");
				sqlDef.append("            register_user_id, 		\n");
				sqlDef.append("            update_date, 			\n");
				sqlDef.append("            update_user_id			\n");
				sqlDef.append("		     )							\n");
				sqlDef.append("		   VALUES						\n");
				sqlDef.append("			 (							\n");
				sqlDef.append("     	   ?,						\n");
				sqlDef.append("            ?, 						\n");
				sqlDef.append("            ?, 						\n");
				sqlDef.append("            SYSDATE, 				\n");
				sqlDef.append("            ?, 						\n");
				sqlDef.append("            SYSDATE, 				\n");
				sqlDef.append("            ? 						\n");
				sqlDef.append("		     )							\n");
				
				try {
					int idx = 0;
					psmt = tc.getConnection().prepareStatement(sqlDef.toString());
					psmt.setString(++idx, processDefaultUserId); //모델러 ID (modeller_id)
					psmt.setString(++idx, alias); //프로세스코드 (process_id)
					psmt.setString(++idx, "Y"); //활성화여부 (activate_yn)
					psmt.setString(++idx, author); //관리자 ID (register_user_id)
					psmt.setString(++idx, author); //관리자 ID (update_user_id)
					psmt.executeUpdate();
				} catch (Exception e) {
					logger.error("error Message From setProcessModellerToNYJ : " + e);
					e.printStackTrace();
					try {
						throw new Exception("error Message From setProcessModellerToNYJ : " + e);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} finally {
					if (psmt != null) {
						psmt.close();
					}
				}
			} else {
				if (isAutoProduction) {
					try {
						StringBuffer sqlDef = new StringBuffer();
						sqlDef.append(" UPDATE nyj_process_modeller		\n");
						sqlDef.append("	   SET modeller_id = ?,       	\n");
						sqlDef.append("   	   process_id = ?,         	\n");
						sqlDef.append("   	   activate_yn = ?,         \n");
						sqlDef.append("   	   update_date = SYSDATE,   \n");
						sqlDef.append("   	   update_user_id = ?   	\n");
						sqlDef.append("	 WHERE process_id = ? 			\n");
						
						int idx = 0;
						psmt = tc.getConnection().prepareStatement(sqlDef.toString());
						psmt.setString(++idx, processDefaultUserId); //모델러 ID (modeller_id)
						psmt.setString(++idx, alias); //프로세스코드 (process_id)
						psmt.setString(++idx, "Y"); //활성화여부 (activate_yn)
						psmt.setString(++idx, author); //관리자 ID (update_user_id)
						psmt.setString(++idx, alias); //프로세스코드 (process_id)
						psmt.executeUpdate();
					} catch (Exception e) {
						logger.error("error Message From setProcessModellerToNYJ : " + e);
						e.printStackTrace();
						try {
							throw new Exception("error Message From setProcessModellerToNYJ : " + e);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					} finally {
						if (psmt != null) {
							psmt.close();
						}
					}
				}
			}
		} else {
			if (isNew) {
				StringBuffer sqlDef = new StringBuffer();
				sqlDef.append(" INSERT INTO nyj_process_modeller	\n");
				sqlDef.append("		     (							\n");
				sqlDef.append("            modeller_id,				\n");
				sqlDef.append("            process_id, 				\n");
				sqlDef.append("   	   	   activate_yn, 			\n");
				sqlDef.append("            register_date, 			\n");
				sqlDef.append("            register_user_id, 		\n");
				sqlDef.append("            update_date, 			\n");
				sqlDef.append("            update_user_id 			\n");
				sqlDef.append("		     )							\n");
				sqlDef.append("		   VALUES						\n");
				sqlDef.append("			 (							\n");
				sqlDef.append("            ?,						\n");
				sqlDef.append("            ?,						\n");
				sqlDef.append("            ?,						\n");
				sqlDef.append("            SYSDATE,					\n");
				sqlDef.append("            ?,						\n");
				sqlDef.append("   		   SYSDATE,					\n");
				sqlDef.append("   		   ?	 					\n");
				sqlDef.append("			 )							\n");
				
				try {
					int idx = 0;
					psmt = tc.getConnection().prepareStatement(sqlDef.toString());
					psmt.setString(++idx, author); //관리자 ID (modeller_id)
					psmt.setString(++idx, alias); //프로세스코드 (process_id)
					psmt.setString(++idx, "Y"); //활성화여부 (activate_yn)
					psmt.setString(++idx, author); //관리자 ID (register_user_id)
					psmt.setString(++idx, author); //관리자 ID (update_user_id)
					psmt.executeUpdate();
				} catch (Exception e) {
					logger.error("error Message From setProcessModellerToNYJ : " + e);
					e.printStackTrace();
					try {
						throw new Exception("error Message From setProcessModellerToNYJ : " + e);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} finally {
					if (psmt != null) {
						psmt.close();
					}
				}
			} else {
				if (isAutoProduction) {
					try {
						StringBuffer sqlDef = new StringBuffer();
						sqlDef.append(" UPDATE nyj_process_modeller		\n");
						sqlDef.append("	   SET modeller_id = ?,       	\n");
						sqlDef.append("   	   process_id = ?,         	\n");
						sqlDef.append("   	   activate_yn = ?,         \n");
						sqlDef.append("   	   update_date = SYSDATE,   \n");
						sqlDef.append("   	   update_user_id = ?   	\n");
						sqlDef.append("	 WHERE process_id = ? 			\n");
						
						int idx = 0;
						psmt = tc.getConnection().prepareStatement(sqlDef.toString());
						psmt.setString(++idx, author);  //관리자 ID (modeller_id)
						psmt.setString(++idx, alias); //프로세스코드 (process_id)
						psmt.setString(++idx, "Y"); //활성화여부 (activate_yn)
						psmt.setString(++idx, author); //관리자 ID (update_user_id)
						psmt.setString(++idx, alias); //프로세스코드 (process_id)
						psmt.executeUpdate();
					} catch (Exception e) {
						logger.error("error Message From setProcessModellerToNYJ : " + e);
						e.printStackTrace();
						try {
							throw new Exception("error Message From setProcessModellerToNYJ : " + e);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					} finally {
						if (psmt != null) {
							psmt.close();
						}
					}
				}
			}
		}
	}
	
	/**
	 * <pre>
	 * 1. 개요 : 프로세스 검토자에 대한 정보를 아래 처리내용과 같이 처리
	 * 2. 처리내용 : 1. 해당 프로세스 주관부서에 직급이 과장인 사용자 정보가 있을 경우
 	 * 					1-1. 프로세스 검토자 정보가 신규 일 경우
	 * 						1-1-1. NYJ_PROCESS_REVIEWER 테이블에 신규 프로세스 검토자 정보 INSERT 처리(검토자 ID)
	 * 					1-2. 프로세스 검토자 정보가 신규가 아닐 경우
	 * 						1-2-1. 메소드 파라미터 중 전달 받은 최신 버전 자동 설정 여부("autoProduction")에 값이 "true" 일 경우
	 * 							1-2-1-1. NYJ_PROCESS_REVIEWER 테이블에서 기존 프로세스 검토자 정보 UPDATE 처리(검토자 ID)
	 * 				 2. 해당 프로세스 주관부서에 직급이 과장인 사용자 정보가 없을 경우
	 * 					2-1. 프로세스 검토자 정보가 신규 일 경우
	 * 						2-1-1. NYJ_PROCESS_REVIEWER 테이블에 신규 프로세스 검토자 정보 INSERT 처리(관리자 ID)
	 * 					2-2. 프로세스 검토자 정보가 신규가 아닐 경우
	 * 						2-2-1. 메소드 파라미터 중 전달 받은 최신 버전 자동 설정 여부("autoProduction")에 값이 "true" 일 경우
	 * 							2-2-1-1. NYJ_PROCESS_REVIEWER 테이블에서 기존 프로세스 검토자 정보 UPDATE 처리(관리자 ID)
	 * </pre>
	 *
	 * @Method Name : setProcessReviewerToNYJ
	 * @date : 2017. 1. 16.
	 * @author : chonk_Enki
	 * @history :
	 *          ----------------------------------------------------------------
	 *          ------- 변경일 작성자 변경내용 ----------- -------------------
	 *          --------------------------------------- 2017. 1. 16. chonk_Enki
	 *          최초 작성
	 *          ------------------------------------------------------------
	 *          -----------
	 * 
	 * @param pmr
	 * @param alias
	 * @param folderId
	 * @param author
	 * @param isAutoProduction
	 * @param isNew
	 * @return
	 * @throws Exception
	 */
	public static void setProcessReviewerToNYJ(ProcessManagerBean pmr, String alias, String folderId, String author, boolean isAutoProduction, boolean isNew)
			throws Exception {
		TransactionContext tc = ((ProcessManagerBean)pmr).getTransactionContext();
		PreparedStatement psmt = null;

		StringBuffer defReviewerFindSql = new StringBuffer();
		defReviewerFindSql = defReviewerFindSql.append("SELECT COUNT(*) AS totalcount FROM nyj_process_reviewer WHERE process_id = ?process_id");
		
		IDAO idao = ConnectiveDAO.createDAOImpl(tc, defReviewerFindSql.toString(), IDAO.class);
		idao.set("process_id", alias);
		idao.select();
		
		if (idao.next()) {
			isNew = (idao.getInt("totalcount") == 0);
		}
		String processDefaultUserId = null;
		StringBuffer defReviewerIDFindSql = new StringBuffer();
		defReviewerIDFindSql = defReviewerIDFindSql.append("SELECT logon_id FROM nyj_user WHERE posit_code = '0013600' AND orgin_blg_dep_code IN (SELECT alias FROM bpm_procdef WHERE defid = ?defid)");
		idao = ConnectiveDAO.createDAOImpl(tc, defReviewerIDFindSql.toString(), IDAO.class);
		idao.set("defid", folderId);
		idao.select();
		
		if (idao.next()) {
			processDefaultUserId = idao.getString("logon_id");
		}
		
		if (UEngineUtil.isNotEmpty(processDefaultUserId)) {
			if (isNew) {
				StringBuffer sqlDef = new StringBuffer();
				sqlDef.append(" INSERT INTO nyj_process_reviewer	\n");
				sqlDef.append("		     (							\n");
				sqlDef.append("            reviewer_id, 			\n");
				sqlDef.append("            process_id, 				\n");
				sqlDef.append("            activate_yn, 			\n");
				sqlDef.append("            register_date, 			\n");
				sqlDef.append("   		   register_user_id, 		\n");
				sqlDef.append("            update_date, 			\n");
				sqlDef.append("            update_user_id 			\n");
				sqlDef.append("		     )							\n");
				sqlDef.append("		   VALUES						\n");
				sqlDef.append("		     (							\n");
				sqlDef.append("            ?,						\n");
				sqlDef.append("            ?,						\n");
				sqlDef.append("            ?,						\n");
				sqlDef.append("            SYSDATE,					\n");
				sqlDef.append("            ?,						\n");
				sqlDef.append("            SYSDATE,					\n");
				sqlDef.append("            ?						\n");
				sqlDef.append("		     )							\n");
				
				try {
					int idx = 0;
					psmt = tc.getConnection().prepareStatement(sqlDef.toString());
					psmt.setString(++idx, processDefaultUserId); //검토자 ID (reviewer_id)
					psmt.setString(++idx, alias); //프로세스코드 (process_id)
					psmt.setString(++idx, "Y"); //활성화여부 (activate_yn)
					psmt.setString(++idx, author); //관리자 ID (register_user_id)
					psmt.setString(++idx, author); //관리자 ID (update_user_id)
					psmt.executeUpdate();
				} catch (Exception e) {
					logger.error("error Message From setProcessReviewerToNYJ : " + e);
					e.printStackTrace();
					try {
						throw new Exception("error Message From setProcessReviewerToNYJ : " + e);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} finally {
					if (psmt != null) {
						psmt.close();
					}
				}
			} else {
				if (isAutoProduction) {
					try {
						StringBuffer sqlDef = new StringBuffer();
						sqlDef.append(" UPDATE nyj_process_reviewer		\n");
						sqlDef.append("	   SET reviewer_id = ?,       	\n");
						sqlDef.append("   	   process_id = ?,         	\n");
						sqlDef.append("   	   activate_yn = ?,         \n");
						sqlDef.append("   	   update_date = SYSDATE,   \n");
						sqlDef.append("   	   update_user_id = ?   	\n");
						sqlDef.append("	 WHERE process_id = ? 			\n");
						
						int idx = 0;
						psmt = tc.getConnection().prepareStatement(sqlDef.toString());
						psmt.setString(++idx, processDefaultUserId); //검토자 ID (reviewer_id)
						psmt.setString(++idx, alias); //프로세스코드 (process_id)
						psmt.setString(++idx, "Y"); //활성화여부 (activate_yn)
						psmt.setString(++idx, author); //관리자 ID (update_user_id)
						psmt.setString(++idx, alias); //프로세스코드 (process_id)
						psmt.executeUpdate();
					} catch (Exception e) {
						logger.error("error Message From setProcessReviewerToNYJ : " + e);
						e.printStackTrace();
						try {
							throw new Exception("error Message From setProcessReviewerToNYJ : " + e);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					} finally {
						if (psmt != null) {
							psmt.close();
						}
					}
				}
			}
		} else {
			if (isNew) {
				StringBuffer sqlDef = new StringBuffer();
				sqlDef.append(" INSERT INTO nyj_process_reviewer	\n");
				sqlDef.append("		     (							\n");
				sqlDef.append("            reviewer_id, 			\n");
				sqlDef.append("            process_id, 				\n");
				sqlDef.append("            activate_yn, 			\n");
				sqlDef.append("            register_date, 			\n");
				sqlDef.append("            register_user_id, 		\n");
				sqlDef.append("            update_date, 			\n");
				sqlDef.append("            update_user_id 			\n");
				sqlDef.append("		     )							\n");
				sqlDef.append("		   VALUES						\n");
				sqlDef.append("		     (							\n");
				sqlDef.append("            ?, 						\n");
				sqlDef.append("            ?, 						\n");
				sqlDef.append("            ?, 						\n");
				sqlDef.append("            SYSDATE, 				\n");
				sqlDef.append("            ?, 						\n");
				sqlDef.append("            SYSDATE, 				\n");
				sqlDef.append("            ? 						\n");
				sqlDef.append("			 )							\n");
				
				try {
					int idx = 0;
					psmt = tc.getConnection().prepareStatement(sqlDef.toString());
					psmt.setString(++idx, author); //관리자 ID (reviewer_id)
					psmt.setString(++idx, alias); //프로세스코드 (process_id)
					psmt.setString(++idx, "Y"); //활성화여부 (activate_yn)
					psmt.setString(++idx, author); //관리자 ID (register_user_id)
					psmt.setString(++idx, author); //관리자 ID (update_user_id)
					psmt.executeUpdate();
				} catch (Exception e) {
					logger.error("error Message From setProcessReviewerToNYJ : " + e);
					e.printStackTrace();
					try {
						throw new Exception("error Message From setProcessReviewerToNYJ : " + e);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} finally {
					if (psmt != null) {
						psmt.close();
					}
				}
			} else {
				if (isAutoProduction) {
					try {
						StringBuffer sqlDef = new StringBuffer();
						sqlDef.append(" UPDATE nyj_process_reviewer		\n");
						sqlDef.append("	   SET reviewer_id = ?,       	\n");
						sqlDef.append("   	   process_id = ?,        	\n");
						sqlDef.append("   	   activate_yn = ?,         \n");
						sqlDef.append("   	   update_date = SYSDATE,   \n");
						sqlDef.append("   	   update_user_id = ?    	\n");
						sqlDef.append("	 WHERE process_id = ? 			\n");
						
						int idx = 0;
						psmt = tc.getConnection().prepareStatement(sqlDef.toString());
						psmt.setString(++idx, author); //관리자 ID (reviewer_id)
						psmt.setString(++idx, alias); //프로세스코드 (process_id)
						psmt.setString(++idx, "Y"); //활성화여부 (activate_yn)
						psmt.setString(++idx, author); //관리자 ID (update_user_id)
						psmt.setString(++idx, alias); //프로세스코드 (process_id)
						psmt.executeUpdate();
					} catch (Exception e) {
						logger.error("error Message From setProcessReviewerToNYJ : " + e);
						e.printStackTrace();
						try {
							throw new Exception("error Message From setProcessReviewerToNYJ : " + e);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					} finally {
						if (psmt != null) {
							psmt.close();
						}
					}
				}
			}
		}
	}

	/**
	 * 이 메소드는 내부적으로 BPM API를 호출한다.
	 * process definition으로부터 존재하는 모든 HumanActivity 와 SubProcessActivity 를 List에 담아 반환한다.
	 * 
	 * @param activity
	 * @param pmr
	 * @return childActivities
	 * @throws RemoteException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List getAllChildHumanActivitiesAndSubProcessActivity(ComplexActivity activity, ProcessManagerBean pmr) throws RemoteException {
		List<Activity> childActivities = new ArrayList<Activity>();
		Iterator<Activity> i = activity.getChildActivities().iterator();
		while ( i.hasNext() ) {
			Activity act = i.next();
			if ( (act instanceof HumanActivity) ) {
				childActivities.add(act);
			}
			if ( act instanceof ComplexActivity && !(act instanceof SubProcessActivity) ) {
				childActivities.addAll( getAllChildHumanActivitiesAndSubProcessActivity((ComplexActivity) act, pmr) );
			}
			else if ( act instanceof SubProcessActivity ) {
				SubProcessActivity subAct = (SubProcessActivity)act;
				childActivities.add(subAct);
			}
		}
		return childActivities;		
	}
	
	/**
	 * 이 메소드는 내부적으로 BPM API를 호출한다.
	 * process definition으로부터 존재하는 모든 Human Activity를 List에 담아 반환한다.
	 * 
	 * @param activity
	 * @param pmr
	 * @return childActivities
	 * @throws RemoteException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List getAllChildHumanActivities(ComplexActivity activity, ProcessManagerBean pmr) throws RemoteException {
		List<Activity> childActivities = new ArrayList<Activity>();
		Iterator<Activity> i = activity.getChildActivities().iterator();
		while ( i.hasNext() ) {
			Activity act = i.next();
			if ( (act instanceof HumanActivity) ) {
				childActivities.add(act);
			}
			if ( act instanceof ComplexActivity && !(act instanceof SubProcessActivity) ) {
				childActivities.addAll( getAllChildHumanActivities((ComplexActivity) act, pmr) );
			}
			else if ( act instanceof SubProcessActivity ) {
				SubProcessActivity subAct = (SubProcessActivity)act;
				String defVerId = null;
				if ( subAct.getVersionSelectOption() == ProcessDefinition.VERSIONSELECTOPTION_JUST_SELECTED ) {
					defVerId = ((SubProcessActivity)act).getDefinitionId().split("@")[1];
				} else {
					String procAlias = ((SubProcessActivity)act).getDefinitionId().split("@")[0].substring(1, ((SubProcessActivity)act).getDefinitionId().split("@")[0].length()-1);
					defVerId = pmr.getProcessDefinitionProductionVersionByAlias(procAlias);
				}
				ProcessDefinition pd = pmr.getProcessDefinition( defVerId );
				childActivities.addAll( getAllChildHumanActivities(pd, pmr) );
			}
		}
		return childActivities;		
	}
	
	/**
	 * 이 메소드는 내부적으로 BPM API를 호출한다.
	 * process definition으로부터 모든 하위 액티비티의 정보를 List객체에 담아서 반환한다.
	 * 
	 * @param activity
	 * @param tc
	 * @return childActivities
	 * @throws RemoteException
	 */
	public static List<Activity> getAllChildActivities(ComplexActivity activity, ProcessTransactionContext tc) throws RemoteException {
		List<Activity> childActivities = new ArrayList<Activity>();
		Iterator<Activity> i = activity.getChildActivities().iterator();
		while ( i.hasNext() ) {
			Activity act = i.next();
			childActivities.add(act);
			if ( act instanceof ComplexActivity && !(act instanceof SubProcessActivity) ) {
				childActivities.addAll( getAllChildActivities((ComplexActivity) act, tc) );
			}
			else if ( act instanceof SubProcessActivity ) {
				SubProcessActivity subAct = (SubProcessActivity)act;
				String defVerId = null;
				if ( subAct.getVersionSelectOption() == ProcessDefinition.VERSIONSELECTOPTION_JUST_SELECTED ) {
					defVerId = ((SubProcessActivity)act).getDefinitionId().split("@")[1];
				} else {
					String procAlias = ((SubProcessActivity)act).getDefinitionId().split("@")[0].substring(1, ((SubProcessActivity)act).getDefinitionId().split("@")[0].length()-1);
					defVerId = tc.getProcessManager().getProcessDefinitionProductionVersionByAlias(procAlias);
				}
				ProcessDefinition pd = tc.getProcessManager().getProcessDefinition( defVerId );
				childActivities.addAll( getAllChildActivities(pd, tc) );
			}
		}
		return childActivities;
	}

	public static Vector getActivitiesDeeply(final ProcessManagerRemote pm, ProcessInstance pi) throws Exception {
		return getActivitiesDeeply(pm, pi, null);
	}
	
	public static Vector getActivitiesDeeply(final ProcessManagerRemote pm, ProcessInstance pi, Activity rootActivity) throws Exception {
		final ProcessInstance finalThis = pi;
		final Vector runningActivities = new Vector();

		ActivityForLoop forLoop = new ActivityForLoop(){
			public void logic(Activity act){
				try{
					ProcessInstance instancehere = finalThis;

					//if(	act instanceof SubProcessActivity && (Activity.STATUS_RUNNING.equals(act.getStatus(instancehere)) || Activity.STATUS_TIMEOUT.equals(act.getStatus(instancehere)))) {
					if(	act instanceof SubProcessActivity ) {
						SubProcessActivity spAct = (SubProcessActivity)act;
						List<String> spInstanceIds = spAct.getSubprocessIds(instancehere);
						
						if(spInstanceIds.size() == 0){
						//	throw new UEngineException("Activity in the running subprocess cannot be found.");
						} else {
							for(String spInstanceId : spInstanceIds) {
								ProcessInstance instance = (ProcessInstance) pm.getProcessInstance(spInstanceId);
								if (instancehere == instance) {
									continue;
								//} else if("Running".equals(instance.getStatus())){
								} else {
									runningActivities.addAll(getActivitiesDeeply(pm,instance));
								}
							}
						}
					//} else if (	!(act instanceof ComplexActivity) && (Activity.STATUS_RUNNING.equals(act.getStatus(instancehere)) || Activity.STATUS_TIMEOUT.equals(act.getStatus(instancehere)))) {
					//} else if (	!(act instanceof ComplexActivity) ) {
					} else if (	!(act instanceof ProcessDefinition) ) {
						ActivityInstanceContext aic = new ActivityInstanceContext(act,instancehere);
						runningActivities.add(aic);
					}
				}catch(Exception e){
					throw new RuntimeException(e);
				}
			}
		};
		
		if (rootActivity == null) forLoop.run(pi.getProcessDefinition());
		else forLoop.run(rootActivity); //2015.05.18 freshka

		return runningActivities;
	}
	
	/**
	 * 파라미터 정보를 통해 관련 instance가 존재하는지를 체크해서 존재하면 해당 instance를 반환한다.
	 * 
	 * @param pmr
	 * @param processCode
	 * @param instanceKey
	 * @param endPoint
	 * @param actionCode
	 * @return instance
	 * @throws Exception 해당하는 instance가 없으면 null을 반환한다.
	 */
/*	public static ProcessInstance getExistingProcessInstance(ProcessManagerRemote pmr, String processCode, String instanceKey, String endPoint, String actionCode) throws Exception{
		ProcessInstance instance = null;
		if ( pmr == null) {
			throw new Exception("pmr is null!");
		}
		
		ProcessTransactionContext ptc = ((ProcessManagerBean)pmr).getTransactionContext();
		
		StringBuffer sql = new StringBuffer();
		sql.append("select a.instid from bpm_procinst a, bpm_procdef b \n");
		sql.append("	where a.name=?name \n");
		sql.append("		and a.defid=b.defid \n");
		sql.append("		and b.alias=?alias \n");
		sql.append("		and a.isdeleted=0 \n");
		//sql.append("		and a.issubprocess=0 \n");
		
		if ( !UEngineUtil.isNotEmpty(actionCode) ) {
			sql.append("		and UPPER(a.status) in ('RUNNING','COMPLETED','STOPPED','CANCELLED','FAILED') \n");
		}else if (actionCode.equals("01") || actionCode.equals("02")
				|| actionCode.equals("03") || actionCode.equals("04")
				|| actionCode.equals("05") || actionCode.equals("06")
				|| actionCode.equals("07") || actionCode.equals("08")
				|| actionCode.equals("09")
				|| actionCode.equals("12")
				|| actionCode.equals("13") || actionCode.equals("14")) {
			sql.append("		and UPPER(a.status) in ('RUNNING') \n");
		} else if (actionCode.equals("10")) {
			sql.append("		and UPPER(a.status) in ('RUNNING','COMPLETED','STOPPED','CANCELLED','FAILED') \n");
		} else if (actionCode.equals("11")) {
			sql.append("		and UPPER(a.status) in ('RUNNING','COMPLETED','SKIPPED') \n");
		} 
		
		logger.info("getExistingProcessInstance [" + sql.toString() + "]");
		
		IDAO idao = ConnectiveDAO.createDAOImpl(ptc, sql.toString(), IDAO.class);
		idao.set("name", instanceKey);
		idao.set("alias", processCode);
		idao.select();
		
		if (idao.size() > 1 && UEngineUtil.isNotEmpty(endPoint)) {
			while ( idao.next() ) {
				
				//멀티플인스턴스(서브프로세스)를 위해 참여자 조건 추가 //2016-11-28 freshka
				StringBuffer sql2 = new StringBuffer();
				sql2.append("SELECT INST.INSTID											\n");
				sql2.append("	FROM BPM_PROCINST INST,								\n");
				sql2.append("		 BPM_PROCDEF DEF,								\n");
				sql2.append("		 BPM_ROLEMAPPING RM								\n");
				sql2.append("WHERE INST.NAME=?NAME										\n");
				sql2.append("  AND INST.DEFID=DEF.DEFID								\n");
				sql2.append("  AND INST.ISDELETED=0								\n");
				sql2.append("  AND DEF.ALIAS=?ALIAS										\n");
				sql2.append("  AND INST.INSTID=RM.INSTID										\n");
				sql2.append("  AND RM.ENDPOINT=?ENDPOINT										\n");
				
				if ( !UEngineUtil.isNotEmpty(actionCode) ) {
					sql2.append("  AND UPPER(INST.STATUS) IN ('RUNNING','COMPLETED','STOPPED','CANCELLED','FAILED')					\n");
				}else if (actionCode.equals("01") || actionCode.equals("02")
						|| actionCode.equals("03") || actionCode.equals("04")
						|| actionCode.equals("05") || actionCode.equals("06")
						|| actionCode.equals("07") || actionCode.equals("08")
						|| actionCode.equals("09")
						|| actionCode.equals("12")
						|| actionCode.equals("13") || actionCode.equals("14")) {
					sql2.append("  AND UPPER(INST.STATUS) IN ('RUNNING')					\n");
				} else if (actionCode.equals("10")) {
					sql2.append("  AND UPPER(INST.STATUS) IN ('RUNNING','COMPLETED','STOPPED','CANCELLED','FAILED')					\n");
				} else if (actionCode.equals("11")) {
					sql2.append("  AND UPPER(INST.STATUS) IN ('RUNNING','COMPLETED','SKIPPED')					\n");
				} 
				
				logger.info("getExistingProcessInstance [" + sql2.toString() + "]");
				
				IDAO idao2 = ConnectiveDAO.createDAOImpl(ptc, sql2.toString(), IDAO.class);
				idao2.set("NAME", instanceKey);
				idao2.set("ALIAS", processCode);
				idao2.set("ENDPOINT", endPoint);
				idao2.select();
				
				if ( idao2.next() ) {
					instance = pmr.getProcessInstance(idao2.getString("instid"));
					//instance = pmr.getProcessInstance(idao2.getString("instid")).getRootProcessInstance();
					logger.info("getExistingProcessInstance [" + instance.getInstanceId() + "]");			
					break;
				}
			}			
		}
		else {
			if ( idao.next() ) {
				instance = pmr.getProcessInstance(idao.getString("instid"));
				//instance = pmr.getProcessInstance(idao.getString("instid")).getRootProcessInstance();
				logger.info("getExistingProcessInstance [" + instance.getInstanceId() + "]");
			}
		}

		return instance;
	}*/

	/**
	 * TPS NO를 통해서 해당 인스턴스가 존재하는지 체크하고 존재한다면 BPM의 instanceId를 반환한다.
	 * @param instanceKey
	 * @param pmr
	 * @return instanceId
	 * @throws Exception
	 */
	public static String getExistingProcessInstanceIdByTPSNumber(String instanceKey, ProcessManagerRemote pmr) throws Exception{

		String instanceId = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT instid FROM BPM_PROCINST  \n");
		sql.append(" 	WHERE name='"+instanceKey+"'  \n");
		sql.append("	  AND isdeleted='0' 		  \n");
		//sql.append("	  AND issubprocess='0' 		  \n");
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		try {
			conn = ((ProcessManagerBean)pmr).getTransactionContext().getConnection();
			psmt = conn.prepareStatement(sql.toString());
			rs = psmt.executeQuery();
			logger.info("getExistingProcessInstanceIdByTPSNumber [" + sql.toString() + "]");
			if ( rs.next()) {
				instanceId = rs.getString("instid");
				logger.info("getExistingProcessInstanceIdByTPSNumber [" + instanceId + "]");				
			}
		} catch (Exception e) { // NOPMD - normally used
			logger.error("error Message From getExistingProcessInstanceIdByTPSNumber : " + e);
			e.printStackTrace(); // NOPMD - for java program detail log
		} finally {
			if ( rs != null ) { rs.close(); }
			if ( psmt != null )	{ psmt.close(); }
		}
		return instanceId;
	}

	/**
	 * TPS NO를 통해서 해당 인스턴스가 존재하는지 체크하고 존재한다면 BPM의 instanceId를 반환한다.
	 * @param instanceKey
	 * @param pmr
	 * @return instanceId
	 * @throws Exception
	 */
	public static boolean isSendMessage(String userId, ProcessTransactionContext processTransactionContext) throws Exception{

		boolean isSendMessage = true;
		String isSendMessageYN = "";
		
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT receive_message_yn FROM nyj_user  \n");
		sql.append(" 	WHERE logon_id='" + userId + "'  \n");
		sql.append("	  AND isdeleted='0' 		  \n");
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		try {
			conn = processTransactionContext.getConnection();
			psmt = conn.prepareStatement(sql.toString());
			rs = psmt.executeQuery();
			if ( rs.next()) {
				isSendMessageYN = rs.getString("receive_message_yn");
				if (("N").equals(isSendMessageYN)){
					isSendMessage = false; 
				}
			}
		} catch (Exception e) { // NOPMD - normally used
			e.printStackTrace(); // NOPMD - for java program detail log
		} finally {
			if ( rs != null ) { rs.close(); }
			if ( psmt != null )	{ psmt.close(); }
		}
		return isSendMessage;
	}	
	
	/**
	 * <pre>
	 * 1. 개요 : StoryZone 메신저로 신규 업무 배정 알림 메시지 전송을 아래 처리내용과 같이 처리
	 * 2. 처리내용 : 1. 메소드 파라미터 중 전달 받은 Activity가 일반업무("HumanActivity") 유형인지 체크
	 * 				 2. Activity 속성 중 업무전달알림메일("isSendEmailWorkitem") 값이 "true" 인지 체크
	 * 				 3. Activity 역할에 매핑된 처리자 ID 수만큼 send() 처리
	 * </pre>
	 * 
	 * @Method Name : sendStoryZoneMessageToNYJ
	 * @date : 2017. 3. 24.
	 * @author : chonk_Enki
	 * @history :
	 *          ----------------------------------------------------------------
	 *          ------- 변경일 작성자 변경내용 ----------- -------------------
	 *          --------------------------------------- 2017. 3. 24. chonk_Enki
	 *          최초 작성
	 *          ------------------------------------------------------------
	 *          -----------
	 * 
	 * @param activity
	 * @param instance
	 * @return
	 * @throws Exception
	 */
	public static void sendStoryZoneMessageToNYJ(Activity activity, ProcessInstance instance)
			throws Exception {
		final String ip = "105.13.1.12"; //메신저 서버 IP
		final int port = 15000; //메신저 서버 포트
		final int time = 2; //메신저 서버 연결 실패 시 유지시간
		
		//Activity가 HumanActivity 일 경우
		if (activity instanceof HumanActivity) {
			//HumanActivity가 업무전달알림메일 일 경우
			if (((HumanActivity) activity).isSendEmailWorkitem()) {
				String processName = instance.getProcessDefinition().getName().getText(); //프로세스 명
				String instanceName = instance.getName(); //인스턴스 명
				String activitiesName = ((HumanActivity) activity).getExtValue2(); //액티비티 명
				RoleMapping rm = ((HumanActivity) activity).getRole().getMapping(instance); //롤 매핑
				rm.beforeFirst();
				do {
					if (isSendMessage(rm.getEndpoint(), instance.getProcessTransactionContext())) {
						String recipientUId = rm.getEndpoint(); //수신자 ID
						String senderNm = "스마트내비관리자"; //발신자 명
						String content = processName+"에 해당 ["+instanceName+"]접수번호로<br>["+activitiesName+"]업무가 배정되었습니다."; //메시지 내용
						String url = ""; //url 주소
						String showMessage = "[스마트내비]<br>신규업무배정 알림"; //메시지 표시
						String type = "SWN"; //메시지 유형
						
						final AtMessengerCommunicator atmc = new AtMessengerCommunicator(ip, port, time); //객체 생성
						atmc.addMessage(recipientUId, senderNm, content, url, showMessage, type); //알림 메시지 생성
	
						Thread sender = new Thread() {
							public void run() {
								try {
									atmc.send(); //알림 메시지 전송
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						};
						sender.start();
					}
				} while (rm.next()); //역할에 매핑된 처리자 ID 만큼
			}
		}
	}
}

package kr.go.nyj.components.activityfilters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.uengine.kernel.Activity;
import org.uengine.kernel.ActivityFilter;
import org.uengine.kernel.GlobalContext;
import org.uengine.kernel.HumanActivity;
import org.uengine.kernel.ProcessDefinition;
import org.uengine.kernel.ProcessInstance;
import org.uengine.kernel.Role;
import org.uengine.kernel.RoleMapping;
import org.uengine.util.UEngineUtil;
import org.uengine.util.dao.ConnectiveDAO;
import org.uengine.util.dao.IDAO;

/**
 * 
 * runtime시에 해당 업무에 대한 유저들의 정보를 가져오고
 * 그 유저들을 역할에 세팅을 하는 액티비티 필터이다.
 * 
 * @author chonk_Enki
 *
 */
public class NYJRoleBindingActivityFilter implements ActivityFilter {
	
	private static final long serialVersionUID = GlobalContext.SERIALIZATION_UID;

	private static Logger logger = Logger.getLogger(NYJRoleBindingActivityFilter.class);
	private String className = UEngineUtil.getClassNameOnly(this.getClass());

	@Override
	public void beforeExecute(Activity activity, ProcessInstance instance) throws Exception {
		if (!(activity instanceof HumanActivity)) {
			return;
		}
		
		if (instance.isNew() && instance.isSubProcess() && !instance.getInstanceId().equals(instance.getRootProcessInstanceId())) {
			//Process Alias가 프로세스 개선요청 검토회신, 협의기관회신, 협의 일 경우 리턴함(Role Base), 2017-02-23 chonk
			if (   "NYJPSWN09111000".equals(instance.getProcessDefinition().getAlias()) 
				|| "NYJPSWN00140000".equals(instance.getProcessDefinition().getAlias()) 
				|| "NYJPSWN00150000".equals(instance.getProcessDefinition().getAlias())) {
				return;
			}
			
			String partCode = null;
			String consultCirculationUserIds = null;
			
			//2017-02-23 chonk
			//Process Alias가 개발행위검토, 농지전용검토, 산지전용검토, 협의부서회신 일 경우 부서코드를 세팅함
			//Process Alias가 협의부서회신 팀별회람회신 일 경우 협의요청검토담당을 세팅함
			if ("NYJPSWN01110000".equals(instance.getProcessDefinition().getAlias())) {
				partCode = (String) instance.get("", "ConsultType1"); //협의유형1
				logger.info("[" + className + "] ConsultType1 [" + partCode + "]");
			} else if ("NYJPSWN00310000".equals(instance.getProcessDefinition().getAlias())) {
				partCode = (String) instance.get("", "ConsultType2"); //협의유형2
				logger.info("[" + className + "] ConsultType2 [" + partCode + "]");
			} else if ("NYJPSWN00410000".equals(instance.getProcessDefinition().getAlias())) {
				partCode = (String) instance.get("", "ConsultType3"); //협의유형3
				logger.info("[" + className + "] ConsultType3 [" + partCode + "]");
			} else if ("NYJPSWN00110000".equals(instance.getProcessDefinition().getAlias())) {
				partCode = (String) instance.get("", "ConsultType4"); //협의유형4
				logger.info("[" + className + "] ConsultType4 [" + partCode + "]");
			} else {
				consultCirculationUserIds = (String) instance.get("", "ConsultCirculationUser"); //협의요청검토담당
				logger.info("[" + className + "] ConsultCirculationUser [" + consultCirculationUserIds + "]");
			}
			
			HumanActivity humAct = (HumanActivity) activity;
			Role thisRole = humAct.getRole();
			
			if (thisRole.getRoleResolutionContext() != null) {
				logger.info("[" + className + "] thisRole.getRoleResolutionContext() [" + thisRole.getRoleResolutionContext().toString() + "]");
				return;
			}
			
			String roleName = thisRole.getName();
			logger.info("[" + className + "] roleName [" + roleName + "]");
			
			if (!UEngineUtil.isNotEmpty(roleName)) {
				return;
			}
			
			StringBuffer sql = new StringBuffer();
			RoleMapping rm = null;
			String userId = null;
			
			//부서코드가 세팅되어 있을 경우 해당 부서에 재직 중인 서무자 ID를 조회하여 액티비티 담당자 ID("userId")에 세팅
			if (UEngineUtil.isNotEmpty(partCode)) {
				sql.append(" SELECT DISTINCT EMPCODE FROM ( \n");
				sql.append("   SELECT empcode	\n");
				sql.append("     FROM emptable     \n");  		
				sql.append("    WHERE partcode = ?partcode1 \n");
				sql.append("      AND isteam IN ('0', '1')	\n");
				sql.append("      AND role_type = 'SECRETARY' \n");
				sql.append("   UNION ALL \n");
				sql.append("   SELECT DISTINCT empcode \n");
				sql.append("     FROM emptable \n");
				sql.append("    WHERE ?partcode2 = (SELECT partcode FROM emptable WHERE empcode = (SELECT nu.logon_id  FROM nyj_complaint nc, nyj_user nu WHERE complaint_code = ?complaintCode1 AND nc.job_charge_id = nu.usr_id)) \n");
				sql.append("      AND empcode = (SELECT nu.logon_id FROM nyj_complaint nc, nyj_user nu WHERE complaint_code = ?complaintCode2 AND nc.job_charge_id = nu.usr_id) \n");
				sql.append("      AND isteam IN ('0', '1')\n");
				sql.append(" ); \n");				
				
				String complaintCode = instance.getName().split("_")[0];
				IDAO idao = ConnectiveDAO.createDAOImpl(instance.getProcessTransactionContext(), sql.toString(), IDAO.class);
				idao.set("partcode1", partCode);
				idao.set("partcode2", partCode);
				idao.set("complaintCode1", complaintCode);
				idao.set("complaintCode2", complaintCode);
				idao.select();
				
				if (idao.size() > 0) {
					List<String> userIdlist = new ArrayList<String>();
					while (idao.next()) {
						if (rm == null) {
							rm = RoleMapping.create();
							rm.setName(roleName);
							rm.setNickName(thisRole.getDisplayName().getText());
							rm.beforeFirst();
						} else {
							rm.moveToAdd();
						}
						userId = idao.getString("empcode");
						
						userIdlist.add(userId);
						
						rm.setEndpoint(userId);
						rm.fill(instance);
					}
					logger.info("[" + className + "] 담당자 정보가 있을 경우 userId [" + userIdlist.toString() + "]");
					if (rm != null) {
						instance.putRoleMapping(roleName, rm);
					}
				} else {
					logger.info("[" + complaintCode + "] 해당 부서의 담당자가 없어 업무 할당하지 못함 부서 코드 [" + partCode + "]");
					//throw new Exception("There's no such person in charge of information in this PartCode [" + partCode + "]");
				}
			}
			//협의요청검토담당이 세팅되어 있을 경우 전달받은 협의요청검토담당 ID를 액티비티 담당자 ID("userId")에 세팅
			if (UEngineUtil.isNotEmpty(consultCirculationUserIds)) {
				List<String> list = Arrays.asList(consultCirculationUserIds.split(";"));
				List<String> userIdlist = new ArrayList<String>();
				for (int i = 0; i < list.size(); i++) {
					if (rm == null) {
						rm = RoleMapping.create();
						rm.setName(roleName);
						rm.setNickName(thisRole.getDisplayName().getText());
						rm.beforeFirst();
					} else {
						rm.moveToAdd();
					}
					userId = list.get(i);
					
					userIdlist.add(userId);
					rm.setEndpoint(userId);
					rm.fill(instance);
				}
				logger.info("[" + className + "] 담당자 정보가 있을 경우 userId [" + userIdlist.toString() + "]");
				if (rm != null) {
					instance.putRoleMapping(roleName, rm);
				}
			}
		}
	}
	
	@Override
	public void afterExecute(Activity activity, ProcessInstance instance)
			throws Exception {
	}

	@Override
	public void afterComplete(Activity activity, ProcessInstance instance)
			throws Exception {
	}

	@Override
	public void onPropertyChange(Activity activity, ProcessInstance instance,
			String propertyName, Object changedValue) throws Exception {
	}

	@Override
	public void onDeploy(ProcessDefinition definition)
			throws Exception {
	}
}

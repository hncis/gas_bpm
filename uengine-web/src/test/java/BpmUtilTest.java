import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.uengine.bpmutil.util.BpmUtil;


public class BpmUtilTest {

	/**
	 * 프로세스를 플로우 컨트롤 한다.
	 *  
	 * @param isComplete 단계완료여부
	 * @param loginUserId 현재로그인사용자아이디
	 * @param processCode  프로세스코드
	 * @param statusCode  액티비티코드
	 * @param bizKey  프로세스개선 요청서번호
	 * @param roleMap  프로세스 참여자 정보
	 * @param varMap  프로세스 변수 정보
	 * @param comment  전자결재 의견
	 * @param isSubProcess 서브프로세스여부
	 * @throws Exception
	 */
	public static String flowControl(boolean isComplete, 
			String loginUserId, 
			String processCode, 
			String statusCode, 
			String bizKey, 
			Map<String, List<String>> roleMap, 
			Map<String, List<String>> varMap, 
			String comment,
			boolean isSubProcess) throws Exception {
		if (isSubProcess) { //서브 프로세스
			if (isComplete) {
				return BpmUtil.completeTask(processCode, bizKey, statusCode, loginUserId, roleMap, varMap, comment, isSubProcess); //현단계 완료 (Completed) 및 다음단계 자동진행
			}
			else {
				return BpmUtil.saveTask(processCode, bizKey, statusCode, loginUserId, roleMap, varMap, isSubProcess); //첫단계 진행 (Running)
			}		
		}
		else { //메인 프로세스
			if (isComplete) {
				return BpmUtil.completeTask(processCode, bizKey, statusCode, loginUserId, roleMap, varMap, comment); //현단계 완료 (Completed) 및 다음단계 자동진행
			}
			else {
				return BpmUtil.saveTask(processCode, bizKey, statusCode, loginUserId, roleMap, varMap); //첫단계 진행 (Running)
			}
		}
	}
	

	public static void main(String[] args) throws Exception {
		
		Map<String, List<String>> roleMap = new HashMap<String, List<String>>();
		Map<String, List<String>> varMap = new HashMap<String, List<String>>();
		List<String> varList0 = null;
		
		/* **************************************************************************************************
		//[메인 프로세스] 일반지역건축허가 - 플로우 컨트롤 시작
		
 		//[메인 프로세스] 일반지역건축허가 - 첫번째 [민원접수] 단계 시작과 동시에 완료
		List<String> roleList01410010 = new ArrayList<String>(); //일반지역건축허가담당
		roleList01410010.add("test01");
		roleMap.put("SWNROLE01410010", roleList01410010);
		flowControl(true,"test01","NYJPSWN01410000","SWNIF01410010","일반지역건축허가 2016120200002",roleMap,varMap,null,false);

 		//[메인 프로세스] 일반지역건축허가 - 두번째 [담당자지정] 단계 완료
		roleList01410010 = new ArrayList<String>(); //일반지역건축허가담당
		roleList01410010.add("test02");
		roleMap.put("SWNROLE01410010", roleList01410010);
		flowControl(true,"test01","NYJPSWN01410000","SWNBZ01410010","일반지역건축허가 2016120200002",roleMap,varMap,null,false);

 		//[메인 프로세스] 일반지역건축허가 - 세번째 [서류검토] 단계 완료
		varList0 = new ArrayList<String>(); //협의필요여부
		varList0.add("Y");
		varMap.put("ConsultNeedYN", varList0);
		flowControl(true,"test02","NYJPSWN01410000","SWNBZ01410020","일반지역건축허가 2016120200002",roleMap,varMap,null,false);
		
		//[메인 프로세스] 일반지역건축허가 - 네번째 [협의부서조회] 단계 완료
		varList0 = new ArrayList<String>(); //협의부서존재여부
		varList0.add("Y");
		varMap.put("ConsultDepartmentExistYN", varList0);
		flowControl(true,"test02","NYJPSWN01410000","SWNIF01410020","일반지역건축허가 2016120200002",roleMap,varMap,null,false);
		
		//[메인 프로세스] 일반지역건축허가 - 다섯번째 [협의요청] 단계 완료
		varList0 = new ArrayList<String>(); //협의부서코드
		varList0.add("DEPT0002");
		varList0.add("DEPT0003");
		varMap.put("ConsultDepartmentCode", varList0);
		flowControl(true,"test02","NYJPSWN01410000","SWNBZ01410030","일반지역건축허가 2016120200002",roleMap,varMap,null,false);

		//[서브 프로세스] 일반지역건축허가 협의회신 - 첫번째 [협의회신] 단계 완료 - 협의부서담당 (admin5,admin4)
		flowControl(true,"admin5","NYJPSWN01411000","SWNBZ01411010","일반지역건축허가 2016120200002",roleMap,varMap,null,true);
		flowControl(true,"admin4","NYJPSWN01411000","SWNBZ01411010","일반지역건축허가 2016120200002",roleMap,varMap,null,true);
		
		//[메인 프로세스] 일반지역건축허가 - 여섯번째 [보완여부검토] 단계 완료
		varList0 = new ArrayList<String>(); //서류보완여부
		varList0.add("Y");
		varMap.put("DocumentSupplementYN", varList0);
		flowControl(true,"test02","NYJPSWN01410000","SWNIF01410030","일반지역건축허가 2016120200002",roleMap,varMap,null,false);

		//[메인 프로세스] 일반지역건축허가 - 일곱번째 [반려여부확인] 단계 완료
		varList0 = new ArrayList<String>(); //반려여부
		varList0.add("Y");
		varMap.put("RejectYN", varList0);
		flowControl(true,"test02","NYJPSWN01410000","SWNIF01410040","일반지역건축허가 2016120200002",roleMap,varMap,null,false);

		//[메인 프로세스] 일반지역건축허가 - 여덟번째 [허가반려처리] 단계 완료
		flowControl(true,"test02","NYJPSWN01410000","SWNIF01410070","일반지역건축허가 2016120200002",roleMap,varMap,null,false);

		//[메인 프로세스] 일반지역건축허가 - 아홉번째 [인허가결재상신] 단계 완료
		flowControl(true,"test02","NYJPSWN01410000","SWNBZ01410050","일반지역건축허가 2016120200002",roleMap,varMap,null,false);

		//[메인 프로세스] 일반지역건축허가 - 열번째 [인허가결재진행] 단계 완료
		flowControl(true,"test02","NYJPSWN01410000","SWNIF01410080","일반지역건축허가 2016120200002",roleMap,varMap,null,false);
		
		//[메인 프로세스] 일반지역건축허가 - 플로우 컨트롤 완료
		*************************************************************************************************** */
		
		
		//[메인 프로세스] 프로세스개선요청 - 플로우 컨트롤 시작
		
 		//[메인 프로세스] 프로세스개선요청 - 첫번째 [프로세스개선요청서작성] 단계 시작
		flowControl(false,"admin","NYJPSWN09110000","SWNBZ09110010","프로세스개선요청 2016120200005",roleMap,null,null,false);

		//[메인 프로세스] 프로세스개선요청 - 첫번째 [프로세스개선요청서작성] 단계 완료
		List<String> roleList09110010 = new ArrayList<String>(); //프로세스주관부서
		roleList09110010.add("test01");
		roleList09110010.add("test02");
		roleMap.put("SWNROLE09110010", roleList09110010);		
		List<String> roleList09110020 = new ArrayList<String>(); //프로세스승인자
		roleList09110020.add("test01");
		roleMap.put("SWNROLE09110020", roleList09110020);
		List<String> roleList09110030 = new ArrayList<String>(); //프로세스검토자 --> 각자에게 서브프로세스 생성
		roleList09110030.add("test4");
		roleList09110030.add("test5");
		roleMap.put("SWNROLE09110030", roleList09110030);		
		flowControl(true,"admin","NYJPSWN09110000","SWNBZ09110010","프로세스개선요청 2016120200005",roleMap,varMap,null,false);

		//[서브 프로세스] 프로세스 개선요청 검토회신 - 첫번째 [검토회신] 단계 완료 - 프로세스검토자 (test4,test5)
		flowControl(true,"test4","NYJPSWN09111000","SWNBZ09111010","프로세스개선요청 2016120200005",roleMap,varMap,null,true);
		flowControl(true,"test5","NYJPSWN09111000","SWNBZ09111010","프로세스개선요청 2016120200005",roleMap,varMap,null,true);
		
		//[메인 프로세스] 프로세스개선요청 - 두번째 [결재상신] 단계 완료 - 프로세스주관부서 (test01,test02)
		flowControl(true,"test02","NYJPSWN09110000","SWNBZ09110030","프로세스개선요청 2016120200005",roleMap,varMap,null,false);

		//[메인 프로세스] 프로세스개선요청 - 세번째 [결재진행] 단계 완료 - 프로세스승인자 (test01)
		flowControl(true,"test01","NYJPSWN09110000","SWNBZ09110040","프로세스개선요청 2016120200005",roleMap,varMap,null,false);
		
		//[메인 프로세스] 프로세스개선요청 - 네번째 [프로세스수정] 단계 완료 - 프로세스주관부서 (test02)
		flowControl(true,"test02","NYJPSWN09110000","SWNBZ09110050","프로세스개선요청 2016120200005",roleMap,varMap,null,false);
		
		//[메인 프로세스] 프로세스개선요청 - 다섯번째 [결재상신] 단계 완료 - 프로세스주관부서 (test02)
		flowControl(true,"test02","NYJPSWN09110000","SWNBZ09110060","프로세스개선요청 2016120200005",roleMap,varMap,null,false);
		
		//[메인 프로세스] 프로세스개선요청 - 여섯번째 [결재진행] 단계 완료 - 프로세스승인자 (test01)
		flowControl(true,"test01","NYJPSWN09110000","SWNBZ09110070","프로세스개선요청 2016120200005",roleMap,varMap,null,false);

		//[메인 프로세스] 프로세스개선요청 - 일곱번째 [프로세스배포] 단계 완료 - 프로세스주관부서 (test02)
		flowControl(true,"test02","NYJPSWN09110000","SWNBZ09110080","프로세스개선요청 2016120200005",roleMap,varMap,null,false);
		
		//[메인 프로세스] 프로세스개선요청 - 플로우 컨트롤 완료

		
	

	}

}

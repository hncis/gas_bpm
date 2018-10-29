package org.uengine.bpmutil.vo;

import java.io.Serializable;
import java.util.List;

public class ServiceVO implements Serializable {

	public static final String BPM_SERVICE_SUCCESS_CODE 			= "S";
	public static final String BPM_SERVICE_FAIL_CODE 				= "F";
	
	/* 사용자 권한 행위 수행코드 */
	public static final String ACTION_CODE_COMELETE         		= "01"; //01: 완료
	public static final String ACTION_CODE_SAVEONLY         		= "02"; //02: 저장
	public static final String ACTION_CODE_COLLECTION       		= "03"; //03: 회수
	public static final String ACTION_CODE_REJECT           		= "04"; //04: 반려
	public static final String ACTION_CODE_DELEGATE         		= "05"; //05: 위임
	
	/* 프로세스 제어 수행코드 */
    public static final String ACTION_CODE_PROCESS_DELETE   		= "06"; //06: 프로세스 삭제
    public static final String ACTION_CODE_PROCESS_COMPLETE 		= "07"; //07: 프로세스 완료
    public static final String ACTION_CODE_PROCESS_STOP     		= "08"; //08: 프로세스 중지
    public static final String ACTION_CODE_PROCESS_CANCEL   		= "09"; //09: 프로세스 취소
    public static final String ACTION_CODE_PROCESS_RESTART  		= "10"; //10: 프로세스 재시작
    public static final String ACTION_CODE_PROCESS_CHANGE_DUEDATE 	= "11"; //11: 프로세스 처리기한 일자 변경
	
	/* 관리자 권한 행위 수행코드 */
	public static final String ACTION_CODE_ACTIVITY_COMPENSATE		= "12"; //12: 되돌리기
	public static final String ACTION_CODE_ACTIVITY_SUSPEND			= "13"; //13: 일시중지
	public static final String ACTION_CODE_ACTIVITY_RESUME			= "14"; //14: 재개
	public static final String ACTION_CODE_ACTIVITY_SKIP			= "15"; //15: 건너뛰기	
	
	private String actionCode;
	private String bizKey;
	private String processCode;
	private String statusCode;
	private String loginUserId;
	private List<ProcessRole> processRoles;
	private List<ProcessVariable> processVariables;
	private String delegateRoleCode; //2017-03-24 chonk
	private List<ProcessRoleId> delegateIds;
	private boolean isSystemCall; //2017-03-24 chonk
	private String dueDate; //2017-03-06 chonk
	private String comment;
	private boolean isSubProcess; //2016-11-29 freshka
	
	private List<CurrentActivityVO> currentActivities; //2017-01-10 chonk
	
	private String status;
	private String errorCode;
	private String message;
	
	public String getActionCode() {
		return actionCode;
	}
	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}
		
	public String getProcessCode() {
		return processCode;
	}
	public void setProcessCode(String processCode) {
		this.processCode = processCode;
	}

	public String getBizKey() {
		return bizKey;
	}
	public void setBizKey(String bizKey) {
		this.bizKey = bizKey;
	}

	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	
	public String getLoginUserId() {
		return loginUserId;
	}
	public void setLoginUserId(String loginUserId) {
		this.loginUserId = loginUserId;
	}
	
	public List<ProcessRole> getProcessRoles() {
		return processRoles;
	}
	public void setProcessRoles(List<ProcessRole> processRoles) {
		this.processRoles = processRoles;
	}
	
	public List<ProcessVariable> getProcessVariables() {
		return processVariables;
	}
	public void setProcessVariables(List<ProcessVariable> processVariables) {
		this.processVariables = processVariables;
	}
	
	//2017-03-24 chonk
	public String getDelegateRoleCode() {
		return delegateRoleCode;
	}
	public void setDelegateRoleCode(String delegateRoleCode) {
		this.delegateRoleCode = delegateRoleCode;
	}
	
	public List<ProcessRoleId> getDelegateIds() {
		return delegateIds;
	}
	public void setDelegateIds(List<ProcessRoleId> delegateIds) {
		this.delegateIds = delegateIds;
	}
	
	//2017-03-24 chonk
	public boolean getIsSystemCall() {
		return isSystemCall;
	}
	public void setIsSystemCall(boolean isSystemCall) {
		this.isSystemCall = isSystemCall;
	}
	
	//2017-03-06 chonk
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	//2016-11-29 freshka
	public boolean getIsSubProcess() {
		return isSubProcess;
	}
	public void setIsSubProcess(boolean isSubProcess) {
		this.isSubProcess = isSubProcess;
	}
	
	//2017-01-10 chonk
	public List<CurrentActivityVO> getCurrentActivities() {
		return currentActivities;
	}
	public void setCurrentActivities(List<CurrentActivityVO> currentActivities) {
		this.currentActivities = currentActivities;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}

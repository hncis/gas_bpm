package org.uengine.web.service.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.uengine.kernel.ProcessDefinition;
import org.uengine.kernel.UEngineException;
import org.uengine.web.service.vo.AuthorityVO;
import org.uengine.web.service.vo.CommentVO;
import org.uengine.web.service.vo.FlowchartVO;
import org.uengine.web.service.vo.ServiceVO;
import org.uengine.web.service.vo.WorkListVO;
import org.uengine.web.worklist.vo.MyWorkVO;

public interface BpmService {

	public ServiceVO executeService(ServiceVO serviceVO, HttpServletRequest request) throws UEngineException;
	public void getFlowChartByPdVer(FlowchartVO flowchartVO) throws Exception;
	public WorkListVO getWorkListByUserId(String userId, int pageSize, String minRegDate) throws Exception;
	public List<MyWorkVO> getMyWorkListByUserId(MyWorkVO vo) throws Exception;
	public WorkListVO getInstanceListByUserId(String userId, int pageSize, String minRegDate) throws Exception;
	public WorkListVO getMyInstanceListByUserId(String userId) throws Exception;
	public List<MyWorkVO> getMyInstanceListByUserIdAndStatus(MyWorkVO myWorkVO) throws Exception;
	public List<MyWorkVO> getMyWorkListCountByUserId(String userId) throws Exception;
	public List<MyWorkVO> getMyInstanceListCountByUserId(String userId) throws Exception;
	public List<CommentVO> getCommentList(String processCode, String bizKey) throws Exception;
	public AuthorityVO getAuthority(String processCode, String bizKey, String statusCode, String userId) throws Exception;
	public ProcessDefinition getProdVersionInfoByDefId(String defId) throws Exception;
	public void getFlowChartByInstanceId(FlowchartVO flowchartVO) throws Exception;
	
}

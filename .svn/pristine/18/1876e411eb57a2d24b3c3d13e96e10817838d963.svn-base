package com.hncis.certificate.manager;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Transactional;

import com.hncis.certificate.vo.BgabGascce01;
import com.hncis.common.vo.CommonApproval;
import com.hncis.common.vo.CommonMessage;

@Transactional
public interface CertificateManager {
	public int insertCertificateToRequest(BgabGascce01 dto);
	public int deleteCertificateToRequest(BgabGascce01 dto);
	public BgabGascce01 getSelectCertificateToRequest(BgabGascce01 dto);
	public int getSelectGridCertificateToListCount(BgabGascce01 dto);
	public List<BgabGascce01> getSelectGridCertificateToList(BgabGascce01 dto);
	
	public CommonMessage updateCertificateToApprove(BgabGascce01 dto, CommonApproval appInfo, CommonMessage message, HttpServletRequest req);
	public CommonMessage updateCertificateToApproveCancel(BgabGascce01 dto, CommonApproval appInfo, CommonMessage message, HttpServletRequest req);
	public int updateCertificateToConfirm(BgabGascce01 dto);
	public int updateCertificateToReject(BgabGascce01 dto);
}

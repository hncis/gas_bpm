package com.hncis.certificate.dao;

import java.util.List;

import com.hncis.certificate.vo.BgabGascce01;


public interface CertificateDao {
	public int insertCertificateToRequest(BgabGascce01 dto);
	public int updateCertificateToRequest(BgabGascce01 dto);
	public int deleteCertificateToRequest(BgabGascce01 dto);
	public BgabGascce01 getSelectCertificateToRequest(BgabGascce01 dto);
	public int getSelectGridCertificateToListCount(BgabGascce01 dto);
	public List<BgabGascce01> getSelectGridCertificateToList(BgabGascce01 dto);
	public int updateCertificateToApprove(BgabGascce01 dto);
	public int confirmCEToRequest(BgabGascce01 dto);
	public int updateInfoCEToReject(BgabGascce01 dto);
}

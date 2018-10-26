package com.hncis.certificate.dao.impl;

import java.util.List;

import com.hncis.certificate.dao.CertificateDao;
import com.hncis.certificate.vo.BgabGascce01;
import com.hncis.common.dao.CommonDao;

public class CertificateDaoImplByIBatis extends CommonDao implements CertificateDao{
	public int insertCertificateToRequest(BgabGascce01 dto){
		return insert("insertCertificateToRequest", dto);
	}
	public int updateCertificateToRequest(BgabGascce01 dto){
		return update("updateCertificateToRequest", dto);
	}
	public int deleteCertificateToRequest(BgabGascce01 dto){
		return delete("deleteCertificateToRequest", dto);
	}
	public BgabGascce01 getSelectCertificateToRequest(BgabGascce01 dto){
		return (BgabGascce01)getSqlMapClientTemplate().queryForObject("selectCertificateToRequest", dto);
	}
	public int getSelectGridCertificateToListCount(BgabGascce01 dto){
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject("selectGridCertificateToListCount", dto));
	}
	@SuppressWarnings("unchecked")
	public List<BgabGascce01> getSelectGridCertificateToList(BgabGascce01 dto){
		return getSqlMapClientTemplate().queryForList("selectGridCertificateToList", dto);
	}
	public int updateCertificateToApprove(BgabGascce01 dto){
		return delete("updateCertificateToApprove", dto);
	}
	public int confirmCEToRequest(BgabGascce01 dto){
		return update("confirmCEToRequest", dto);
	}
	public int updateInfoCEToReject(BgabGascce01 dto){
		return update("updateInfoCEToReject", dto);
	}
}

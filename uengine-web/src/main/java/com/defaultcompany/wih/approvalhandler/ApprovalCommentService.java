package com.defaultcompany.wih.approvalhandler;

import java.sql.*;
import java.util.HashMap;

import org.uengine.kernel.Activity;
import org.uengine.kernel.FormApprovalActivity;
import org.uengine.kernel.HumanApprovalActivity;
import org.uengine.kernel.ProcessInstance;
import org.uengine.kernel.RoleMapping;
import org.uengine.persistence.dao.DAOFactory;
import org.uengine.persistence.dao.KeyGeneratorDAO;

public class ApprovalCommentService {

	public void saveComments(ProcessInstance instance,
							String comment,
							String apprStat,
							String tracingTag,
							RoleMapping loggedRoleMapping){
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "";
		String apprTitle = "";
		try {
			Activity act = instance.getProcessDefinition().getActivity(tracingTag);
			
			if (act instanceof FormApprovalActivity) {
				FormApprovalActivity formApprovalActivity=(FormApprovalActivity) instance.getProcessDefinition().getActivity(tracingTag);
				if(FormApprovalActivity.SIGN_DRAFT.equals("SIGN_"+apprStat.toUpperCase())){
					apprTitle = loggedRoleMapping.getResourceName() + " (상신)";
				}else{
					apprTitle = formApprovalActivity.getName().getText();
				}
			} else if (act instanceof HumanApprovalActivity) {
				HumanApprovalActivity humanApprovalActivity=(HumanApprovalActivity) instance.getProcessDefinition().getActivity(tracingTag);
				if(HumanApprovalActivity.SIGN_DRAFT.equals("SIGN_"+apprStat.toUpperCase())){
					apprTitle = loggedRoleMapping.getResourceName() + " (상신)";
				}else{
					apprTitle = humanApprovalActivity.getName().getText();
				}
			}
			
			conn = instance.getProcessTransactionContext().getConnection();

			DAOFactory daoFactory = DAOFactory.getInstance(instance.getProcessTransactionContext());
			Timestamp now = new Timestamp(daoFactory.getNow().getTimeInMillis());
			KeyGeneratorDAO kgd = daoFactory.createKeyGenerator("DOC_COMMENTS", new HashMap());
			kgd.select();
			kgd.next();
			Number seqresult = kgd.getKeyNumber();
			
			sql = (
					"INSERT INTO doc_comments ("
					+ "id, instance_id, contents, opt_type, empno, empname, emptitle, "
					+ "tracingtag, created_date, created_by, updated_date, updated_by, apprtitle"
					+ ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)"
			);
			
			ps = conn.prepareStatement(sql);

			ps.setString(1, String.valueOf(seqresult));
			ps.setString(2, instance.getInstanceId());
			ps.setString(3, comment);
			ps.setString(4, apprStat);
			ps.setString(5, loggedRoleMapping.getEndpoint());
			ps.setString(6, loggedRoleMapping.getResourceName());
			ps.setString(7, loggedRoleMapping.getTitle());
			ps.setString(8, tracingTag);
			ps.setDate(9, new Date(now.getTime()));
			ps.setString(10, loggedRoleMapping.getEndpoint());
			ps.setDate(11, new Date(now.getTime()));
			ps.setString(12, loggedRoleMapping.getEndpoint());
			ps.setString(13, apprTitle);

			ps.executeUpdate();
			//System.out.println("---------------- 의견이 저장되었습니다. -----------------");
		} catch (Exception e) {
			e.printStackTrace();
			//System.out.println("---------------- 의견이 저장 되지 못하였습니다. -----------------");
		} finally {
			if (ps != null) try { ps.close(); } catch (Exception e1) {}
		}
	}
}

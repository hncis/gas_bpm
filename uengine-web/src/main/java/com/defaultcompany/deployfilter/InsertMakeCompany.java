package com.defaultcompany.deployfilter;

import org.uengine.kernel.DeployFilter;
import org.uengine.kernel.ProcessDefinition;
import org.uengine.kernel.RevisionInfo;
import org.uengine.persistence.processdefinition.ProcessDefinitionDAO;
import org.uengine.processmanager.ProcessTransactionContext;
import org.uengine.security.AclManager;
import org.uengine.util.UEngineUtil;

public class InsertMakeCompany implements DeployFilter
{
	
	public void beforeDeploy(ProcessDefinition definition, ProcessTransactionContext tc, String folder, boolean isNew) throws Exception
	{
		if (isNew)
		{
			RevisionInfo ri = (RevisionInfo) definition.getRevisionInfoList().get(0);
			AclManager acl = AclManager.getInstance();
			
			acl.setPermission(
					Integer.parseInt(definition.getBelongingDefinitionId()),
					AclManager.ACL_FIELD_EMP, ri.getAuthorId(),
					new String[]{AclManager.PERMISSION_MANAGEMENT + ""}
			);
			
			ProcessDefinitionDAO procDef = (ProcessDefinitionDAO) tc.findSynchronizedDAO("bpm_procdef", "defid", definition.getBelongingDefinitionId(), ProcessDefinitionDAO.class);
			
			if (UEngineUtil.isNotEmpty(ri.getAuthorCompany()))
			{
				acl.setPermission(
						Integer.parseInt(definition.getBelongingDefinitionId()),
						AclManager.ACL_FIELD_COM, ri.getAuthorCompany(),
						new String[]{AclManager.PERMISSION_INITIATE + "", AclManager.PERMISSION_VIEW + ""}
				);
				procDef.setComCode(ri.getAuthorCompany());
			}
		
		}
	}
}

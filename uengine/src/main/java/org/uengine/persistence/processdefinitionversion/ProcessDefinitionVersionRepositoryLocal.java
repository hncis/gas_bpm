package org.uengine.persistence.processdefinitionversion;

import java.sql.Blob;
import java.sql.Clob;
import java.util.Date;

//import java.rmi.RemoteException;

/**
 * @author Jinyoung Jang
 */


public interface ProcessDefinitionVersionRepositoryLocal extends javax.ejb.EJBLocalObject{
	public abstract Long getDefVerId();
	public abstract void setDefVerId(Long id);

	public abstract Long getDefId();
	public abstract void setDefId(Long definitionID);

	public abstract String getDefName();
	public abstract void setDefName(String definitionID);

	public abstract Long getVer();
	public abstract void setVer(Long version);

	public abstract String getFilePath();
	public abstract void setFilePath(String filePath);
	
	public abstract Date getModDate();
	public abstract void setModDate(Date val);

	public abstract Date getProdStartDate();
	public abstract void setProdStartDate(Date val);

	public abstract boolean getIsDeleted();
	public abstract void setIsDeleted(boolean isDeleted);

	public abstract boolean getProgramId();
	public abstract void setProgramId(String programId);

	public abstract String getAuthor();
	public abstract void setAuthor(String programId);

	public abstract String getDescription();
	public abstract void setDescription(String description);

	public abstract Clob getDefinition(); 
	public abstract void setDefinition(Clob definition);
	
	public abstract Blob getCachedDefinition(); 
	public abstract void setCachedDefinition(Blob definition);	
	
}
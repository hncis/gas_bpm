package org.uengine.test;

import java.util.List;

import org.uengine.util.dao.DataSourceConnectionFactory;

public class testClass {
	public static void main(String[] args) throws Exception {
		DataSourceConnectionFactory dsnCF = new DataSourceConnectionFactory();
		dsnCF.setDataSourceJndiName("java:/uEngineDS");
		List tableList = org.uengine.kernel.descriptor.DatabaseMappingActivityDescriptor.getTableNames(dsnCF);
		System.out.println("tableList.size():"+tableList.size());

	
	}

}

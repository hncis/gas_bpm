package org.uengine.kernel;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.metaworks.Type;
import org.metaworks.inputter.MultipleInput;
import org.metaworks.inputter.RadioInput;
import org.metaworks.inputter.TextAreaInput;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.uengine.util.dao.ConnectionFactory;
import org.uengine.util.dao.DataSourceConnectionFactory;
import org.uengine.util.dao.DefaultConnectionFactory;
import org.uengine.util.dao.JDBCConnectionFactory;

/**
 * @author allbegray
 */
public class NamedParameterSQLActivity extends DefaultActivity {
	
	private static final long serialVersionUID = org.uengine.kernel.GlobalContext.SERIALIZATION_UID;
	
	private static final int OPERATION_SELECT = 1;
	private static final int OPERATION_OTHER = 5;
	
	private static final String ADVICE = 
		"-- *Named parameter template*\n" +
		"-- the values of parameters can be specified by name rather than by index.\n\n\n" +
		"-- Correct example query)\n" +
		"-- select * from table1 where ID = :id\n" +
		"-- insert into table1(ID, PASSWD) values(:id, :password)\n\n" +
		"-- Incorrect example query)\n" +
		"-- insert into table1(ID, PASSWD) values(?, ?)";
	
	public static void metaworksCallback_changeMetadata(Type type) {
		
		type.getFieldDescriptor("Operation").setInputter(new RadioInput(
			new String[] {
					"SELECT",
					"INSERT, UPDATE, DELETE" 
				},
			new Object[] { 
					new Integer(OPERATION_SELECT), 
					new Integer(OPERATION_OTHER) 
				}
		));
		type.getFieldDescriptor("SqlStatement").setInputter(new TextAreaInput(45, 15));
		type.setFieldOrder(new String[] { "SqlArguments", "ConnectionFactory", "Operation", "SqlStatement", "ResultSet" });
		
		// setting for SqlArguments
		Type parametersType = ((MultipleInput) type.getFieldDescriptor("SqlArguments").getInputter()).getTable();
		parametersType.removeFieldDescriptor("Type");
		parametersType.removeFieldDescriptor("Direction");
		parametersType.removeFieldDescriptor("TransformerMapping");
		parametersType.getFieldDescriptor("Argument").setDisplayName("ArgumentName");
		parametersType.setFieldOrder(new String[] { "Argument", "Variable" });

		// setting for ResultSet
		Type resultSetType = ((MultipleInput) type.getFieldDescriptor("ResultSet").getInputter()).getTable();
		resultSetType.removeFieldDescriptor("Type");
		resultSetType.removeFieldDescriptor("Direction");
		resultSetType.removeFieldDescriptor("TransformerMapping");
		resultSetType.getFieldDescriptor("Argument").setDisplayName("ResultName");
		resultSetType.getFieldDescriptor("Variable").setDisplayName("StoreVariable");
		resultSetType.setFieldOrder(new String[] { "Argument", "Variable" });
	}
	
	ConnectionFactory connectionFactory;
		public ConnectionFactory getConnectionFactory() {
			return connectionFactory;
		}
		public void setConnectionFactory(ConnectionFactory factory) {
			connectionFactory = factory;
		}
	
	int operation;
		public int getOperation() {
			return operation;
		}
		public void setOperation(int operation) {
			this.operation = operation;
		}

	String sqlStatement;
		public String getSqlStatement() {
			return sqlStatement;
		}
		public void setSqlStatement(String sqlStatement) {
			this.sqlStatement = sqlStatement;
		}
		
	ParameterContext[] sqlArguments;
		public ParameterContext[] getSqlArguments() {
			return sqlArguments;
		}
		public void setSqlArguments(ParameterContext[] sqlArguments) {
			this.sqlArguments = sqlArguments;
		}
		
	ParameterContext[] resultSet;
		public ParameterContext[] getResultSet() {
			return resultSet;
		}
		public void setResultSet(ParameterContext[] resultSet) {
			this.resultSet = resultSet;
		}
	
	public NamedParameterSQLActivity() {
		super("Named Parameter SQL");
		this.setSqlStatement(ADVICE);
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public void executeActivity(ProcessInstance instance) throws Exception {
		
		DataSource ds = null;
		if (this.getConnectionFactory() == null) {
			
			ds = DefaultConnectionFactory.create().getDataSource();
			
		} else if (this.getConnectionFactory().getClass() == DataSourceConnectionFactory.class) {
			
			String jndiName = ((DataSourceConnectionFactory) this.getConnectionFactory()).getDataSourceJndiName();
			InitialContext ctx = new InitialContext();
			ds = (javax.sql.DataSource) ctx.lookup(jndiName);
			
		} else if (this.getConnectionFactory().getClass() == JDBCConnectionFactory.class) {
			
			JDBCConnectionFactory cf = (JDBCConnectionFactory) this.getConnectionFactory();
			BasicDataSource bds = new BasicDataSource();
			bds.setDriverClassName(cf.getDriverClass());
			bds.setUrl(cf.getConnectionString());
			bds.setUsername(cf.getUserId());
			bds.setPassword(cf.getPassword());
			ds = bds;
			
		}
		
		String actualSqlStatement = evaluateContent(instance, this.getSqlStatement()).toString().trim();
		
		SimpleJdbcTemplate simpleJdbcTemplate = new SimpleJdbcTemplate(ds);
		
		if (this.getOperation() == OPERATION_OTHER) {
			
			int maxOfProcessValueCount = 0;
			
			for (ParameterContext pc : this.getSqlArguments()) {
				int c = instance.getMultiple("", pc.getVariable().getName()).size();
				if (c > maxOfProcessValueCount)
					maxOfProcessValueCount = c;
			}
			
			Map<String, Object>[] paramMaps = new HashMap[maxOfProcessValueCount];
			for (int i = 0; i < paramMaps.length; i++) {
				paramMaps[i] = new HashMap<String, Object>();
				
				for (ParameterContext pc : this.getSqlArguments()) {
					ProcessVariableValue pvv = instance.getMultiple("", pc.getVariable().getName());
					Object objValue = null;
					
					if (pvv.size() >= i) {
						pvv.setCursor(i);
						objValue = pvv.getValue();
						if (objValue instanceof Calendar) {
							objValue = new Timestamp(((Calendar) objValue).getTimeInMillis());
						} else if (objValue instanceof java.util.Date) {
							objValue = new Timestamp(((java.util.Date) objValue).getTime());
						}
						pvv.beforeFirst();
					}
										
					paramMaps[i].put(pc.getArgument().getText(), objValue);
				}
			}
			
			simpleJdbcTemplate.batchUpdate(actualSqlStatement, paramMaps);
			
		} else if (this.getOperation() == OPERATION_SELECT) {
			
			Map<String, Object> paramMap = new HashMap<String, Object>();
			for (ParameterContext pc : this.getSqlArguments()) {
				paramMap.put(pc.getArgument().getText(), instance.get(pc.getVariable().getName()));
			}
			
			List<Map<String, Object>> results = simpleJdbcTemplate.queryForList(actualSqlStatement, paramMap);
			
			for (ParameterContext pc : this.getResultSet()) {
				ProcessVariableValue pvv = new ProcessVariableValue();
				
				for (Map<String, Object> resultMap : results) {
					Object objValue = resultMap.get(pc.getArgument());
					Class<?> variableClassType = pc.getVariable().getType();
					
					if ((objValue.getClass() == java.sql.Date.class || objValue.getClass() == Timestamp.class)
							&& (variableClassType == java.util.Date.class || variableClassType == Calendar.class)) {
						
						Calendar c = Calendar.getInstance();
						c.setTime((java.util.Date) objValue);
						objValue = c;
					}

					pvv.setValue(objValue);
					pvv.moveToAdd();
				}
				
				pvv.beforeFirst();
				instance.set(pc.getVariable().getName(), pvv);
			}
			
		}
		
		fireComplete(instance);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map getActivityDetails(ProcessInstance inst, String locale)
			throws Exception {
		
		Map details = super.getActivityDetails(inst, locale);
		details.put("query string", evaluateContent(inst, this.getSqlStatement()).toString().trim());
		
		return details;
	}
	
	@Override
	public ValidationContext validate(Map options) {
		
		ValidationContext vc = super.validate(options);
		
		String sql = this.getSqlStatement();
		int paramCnt = 0;
		if (sql != null) {
			if (!sql.equals(ADVICE)){
				for (int i = 0; i < sql.length(); i++) {
					if (sql.charAt(i) == ':')
						paramCnt++;
				}
			}
		}
		
		if (this.getOperation() == 0) {
			vc.add(getActivityLabel() + "\tChoice CRUD operation.");
		}
		
		if (this.getSqlArguments() != null && paramCnt != this.getSqlArguments().length) {
			vc.add(getActivityLabel() + "\tNumber of parameter does not match with parameter placeholders - ':ParameterName'.");
		}
		
		return vc;
	}

}

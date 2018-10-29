package org.uengine.kernel.descriptor;

public class ColumnProperty{
	String columnName;
		public String getColumnName() {
			return columnName;
		}
		public void setColumnName(String columnName) {
			this.columnName = columnName;
		}
	
	String type;
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		
	boolean nullable;
		public boolean isNullable() {
			return nullable;
		}
		public void setNullable(boolean nullable) {
			this.nullable = nullable;
		}
}
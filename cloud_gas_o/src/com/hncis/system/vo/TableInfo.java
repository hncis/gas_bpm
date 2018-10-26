package com.hncis.system.vo;

// TODO: Auto-generated Javadoc
/**
 * The Class TableInfo. - 테이블 정보 value object
 */
public class TableInfo {

	/** The table_name. 테이블명 */
	private String table_name;
	
	/** The column_id. 컬럼 id*/
	private String column_id;
	
	/** The column_name. 컬럼명*/
	private String column_name;
	
	/** The primary_key. 기본키*/
	private String primary_key;
	
	/** The data_type. 데이터 타입*/
	private String data_type;
	
	/** The data_length. 컬럼 길이*/
	private String data_length;
	
	/** The nullable. 컬럼 null인지 아닌지*/
	private String nullable;	
	
	/** The index_name. 테이블 인덱스 명*/
	private String index_name;
	
	/** The column_position. 테이블 컬럼 위치*/
	private String column_position;
	
	/** The descend. 테이블 정보의 컬럼이 asc 인지 desc 인지에 대한 값을 넣는 변수*/
	private String descend;
	
	private String key_name;
	
	private String seq_in_index;
	
	
	public String getKey_name() {
		return key_name;
	}

	public void setKey_name(String key_name) {
		this.key_name = key_name;
	}

	public String getSeq_in_index() {
		return seq_in_index;
	}

	public void setSeq_in_index(String seq_in_index) {
		this.seq_in_index = seq_in_index;
	}

	/**
	 * Gets the table_name.
	 *
	 * @return the table_name
	 */
	public String getTable_name() {
		return table_name;
	}
	
	/**
	 * Sets the table_name.
	 *
	 * @param tableName the new table_name
	 */
	public void setTable_name(String tableName) {
		table_name = tableName;
	}
	
	/**
	 * Gets the column_id.
	 *
	 * @return the column_id
	 */
	public String getColumn_id() {
		return column_id;
	}
	
	/**
	 * Sets the column_id.
	 *
	 * @param columnId the new column_id
	 */
	public void setColumn_id(String columnId) {
		column_id = columnId;
	}
	
	/**
	 * Gets the column_name.
	 *
	 * @return the column_name
	 */
	public String getColumn_name() {
		return column_name;
	}
	
	/**
	 * Sets the column_name.
	 *
	 * @param columnName the new column_name
	 */
	public void setColumn_name(String columnName) {
		column_name = columnName;
	}
	
	/**
	 * Gets the primary_key.
	 *
	 * @return the primary_key
	 */
	public String getPrimary_key() {
		return primary_key;
	}
	
	/**
	 * Sets the primary_key.
	 *
	 * @param primaryKey the new primary_key
	 */
	public void setPrimary_key(String primaryKey) {
		primary_key = primaryKey;
	}
	
	/**
	 * Gets the data_type.
	 *
	 * @return the data_type
	 */
	public String getData_type() {
		return data_type;
	}
	
	/**
	 * Sets the data_type.
	 *
	 * @param dataType the new data_type
	 */
	public void setData_type(String dataType) {
		data_type = dataType;
	}
	
	/**
	 * Gets the data_length.
	 *
	 * @return the data_length
	 */
	public String getData_length() {
		return data_length;
	}
	
	/**
	 * Sets the data_length.
	 *
	 * @param dataLength the new data_length
	 */
	public void setData_length(String dataLength) {
		data_length = dataLength;
	}
	
	/**
	 * Gets the nullable.
	 *
	 * @return the nullable
	 */
	public String getNullable() {
		return nullable;
	}
	
	/**
	 * Sets the nullable.
	 *
	 * @param nullable the new nullable
	 */
	public void setNullable(String nullable) {
		this.nullable = nullable;
	}
	
	/**
	 * Gets the index_name.
	 *
	 * @return the index_name
	 */
	public String getIndex_name() {
		return index_name;
	}
	
	/**
	 * Sets the index_name.
	 *
	 * @param indexName the new index_name
	 */
	public void setIndex_name(String indexName) {
		index_name = indexName;
	}
	
	/**
	 * Gets the column_position.
	 *
	 * @return the column_position
	 */
	public String getColumn_position() {
		return column_position;
	}
	
	/**
	 * Sets the column_position.
	 *
	 * @param columnPosition the new column_position
	 */
	public void setColumn_position(String columnPosition) {
		column_position = columnPosition;
	}
	
	/**
	 * Gets the descend.
	 *
	 * @return the descend
	 */
	public String getDescend() {
		return descend;
	}
	
	/**
	 * Sets the descend.
	 *
	 * @param descend the new descend
	 */
	public void setDescend(String descend) {
		this.descend = descend;
	}
	
	
}

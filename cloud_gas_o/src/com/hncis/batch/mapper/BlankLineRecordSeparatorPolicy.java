package com.hncis.batch.mapper;

import org.springframework.batch.item.file.separator.SimpleRecordSeparatorPolicy;

public class BlankLineRecordSeparatorPolicy extends SimpleRecordSeparatorPolicy {
	
	/*
	 * (non-Javadoc)
	 * 
	 * @leesungmin
	 * org.springframework.batch.item.file.separator.SimpleRecordSeparatorPolicy
	 * #isEndOfRecord(java.lang.String)
	 */
	@Override
	public boolean isEndOfRecord(String line) {
		if (line.trim().length() == 0) {
			return false;
		}
		return super.isEndOfRecord(line);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @leesungmin
	 * org.springframework.batch.item.file.separator.SimpleRecordSeparatorPolicy
	 * #postProcess(java.lang.String)
	 */
	@Override
	public String postProcess(String record) {
		if (record == null || record.trim().length() == 0
				|| "null".equals(record)) {
			return null;
		}
		return super.postProcess(record);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @leesungmin
	 * org.springframework.batch.item.file.separator.SimpleRecordSeparatorPolicy
	 * #preProcess(java.lang.String)
	 */
	@Override
	public String preProcess(String line) {
		return line.trim();
	}

}

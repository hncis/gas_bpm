package org.uengine.ui.list.datamodel;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.uengine.kernel.GlobalContext;
import org.uengine.util.UEngineUtil;

/**
 * <pre>
 *  data map based extends of the <tt>HashMap</tt> class
 * </pre>
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 */
public class DataMap<K,V> extends HashMap<K,V> {
    private static final long serialVersionUID = GlobalContext.SERIALIZATION_UID;

    private DataMap<K,V> prevData;
	
	private DataMap<K,V> nextData;
	
	private int rownum;
	
    private String errCode;
    
    public DataMap() {
        super();
    }
    
    public DataMap(int initialCapacity){
        super(initialCapacity);
    }
    
    public DataMap(int initialCapacity, float loadFactor){
        super(initialCapacity, loadFactor);
    }
    
    public DataMap(Map<K,V> map) {
        super(map);
    }
    
    public Object getObj(String key, Object objDef){
    	Object retObject = get(key.toUpperCase());
         if(retObject == null) {
            retObject = objDef;
        }
        return retObject;
    }
    
    public String getString(String key, String defaultString, String locale, String timeZone){
    	String value  = defaultString;
    	Object obj = getObj(key, defaultString);
    	if( obj instanceof String ){
    		value = (String)obj;
    	}else if( obj != null ){
    		if(obj instanceof Timestamp){
    			Calendar cal = Calendar.getInstance(
    					TimeZone.getTimeZone(
    							timeZone != null
    							?
    							timeZone
    							:
    							org.uengine.kernel.GlobalContext.getPropertyString("webclient.default.timezone", "GMT")
    					)
    			);
    			
    			cal.setTimeInMillis(((Timestamp)obj).getTime());
    			long timeInMillisForDate = cal.getTimeInMillis() + cal.getTimeZone().getRawOffset();

    			SimpleDateFormat dateformat = new SimpleDateFormat(org.uengine.kernel.GlobalContext.getPropertyString("webclient.datetime.formatter", "yyyy-MM-dd HH:mm:ss")); 
    			return dateformat.format(new Date(timeInMillisForDate));//dateStr;//cal.toString();//cal.getTime().toString();//dateformat.format(cal.getTime());
    		}
    		value = obj.toString();
    	}
    	
    	if (!UEngineUtil.isNotEmpty(value)) {
    		value = defaultString;
    	}
    	
    	return value;
    }
    
    public String getString(String key, String defaultString){
    	return getString(key, defaultString, null, null);
    }
    
    public boolean getBoolean(String key, boolean defaultBoolean){
        String boolVal = (String)getObj(key,String.valueOf(defaultBoolean));
        try {
        	return new Boolean(boolVal).booleanValue();	
        } catch(Exception e) {
        	return defaultBoolean;
        }
    }
    
    public int getInt(String key, int defaultInt){
        String inputValue = (String)getObj(key,String.valueOf(defaultInt));
        try {
            return Integer.parseInt(inputValue);
        }catch(Exception e) {
            return defaultInt;
        }
    }
    
    public float getFloat(String key, float defaultFloat){
        String inputValue = (String)getObj(key,String.valueOf(defaultFloat));
        try {
            return Float.parseFloat(inputValue);
        }catch(Exception e) {
            return defaultFloat;
        }
    }
    
    public long getLong(String key, long defaultLong){
        String inputValue = (String)getObj(key,String.valueOf(defaultLong));
        try {
            return Long.parseLong(inputValue);
        }catch(Exception e) {
            return defaultLong;
        }
    }
    
    public double getDouble(String key, double defaultDouble){
        String inputValue = (String)getObj(key,String.valueOf(defaultDouble));
        try {
            return Double.parseDouble(inputValue);
        }catch(Exception e) {
            return defaultDouble;
        }
    }
    
    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }
    
    public DataMap<K,V> getPrevData() {
		return prevData;
	}
    
	public void setPrevData(DataMap<K,V> prevData) {
		this.prevData = prevData;
	}
	
	public DataMap<K,V> getNextData() {
		return nextData;
	}
	
	public void setNextData(DataMap<K,V> nextData) {
		this.nextData = nextData;
	}

	public int getRownum() {
		return rownum;
	}

	public void setRownum(int rownum) {
		this.rownum = rownum;
	}
}

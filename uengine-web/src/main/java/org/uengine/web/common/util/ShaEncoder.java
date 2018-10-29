
package org.uengine.web.common.util; 

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Service;


/**
 * <pre>
 * org.uengine.web.common.util 
 * ShaEncoder.java
 * 
 * </pre>
 * @date : 2016. 6. 3. 오후 1:34:03
 * @version : 
 * @author : mkbok_Enki
 */
@Service("shaEncoder")
public class ShaEncoder {

	private Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name="passwordEncoder")
	private ShaPasswordEncoder encoder;

	public String encoding(String str) {
		return encoder.encodePassword(str, null);
	}
}

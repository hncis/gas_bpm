package org.uengine.web.sample.controller;


import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * org.uengine.web.sample.controller 
 * SampleController.java
 * 
 * </pre>
 * @date : 2016. 6. 3. 오후 1:41:07
 * @version : 
 * @author : mkbok_Enki
 */
@Controller
public class SampleController {
	
	Logger log = Logger.getLogger(this.getClass());
	
	
	/**
	 * <pre>
	 * 1. 개요 : 
	 * 2. 처리내용 : 
	 * </pre>
	 * @Method Name : onpenSampleList
	 * @date : 2016. 5. 19.
	 * @author : mkbok_Enki
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2016. 5. 19.		mkbok_Enki				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @param commandMap
	 * @return
	 */ 	
	@RequestMapping(value="/sample/openSampleList.do")
    public ModelAndView openSampleList(Map<String,Object> commandMap) throws Exception{
        ModelAndView mv = new ModelAndView("");
        log.debug("인터셉터 테스트");
         
        return mv;
    }
	
}

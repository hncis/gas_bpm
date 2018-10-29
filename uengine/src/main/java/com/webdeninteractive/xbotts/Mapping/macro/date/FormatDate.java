/*
-------------------------------------------------------------------
BIE is Copyright 2001-2004 Brunswick Corp.
-------------------------------------------------------------------
Please read the legal notices (docs/legal.txt) and the license
(docs/bie_license.txt) that came with this distribution before using
this software.
-------------------------------------------------------------------
 */
/*
 * FormatDate.java
 *
 * Created on October 10, 2002, 11:47 AM, 02/26/2004
 */

package com.webdeninteractive.xbotts.Mapping.macro.date;
import com.webdeninteractive.xbotts.Mapping.macro.*;
import javax.swing.ImageIcon;
import java.util.*;
import java.util.Date;
import java.text.*;
import org.w3c.dom.*;


/**
 *
 * @author  bmadigan, jdu
 */
public class FormatDate extends AbstractExtensionFunction {
    
    /** Creates a new instance of FormatDate
	 * with left link parameter date and two editable parameter date format.
	 */
    public FormatDate() {
	     super.addLinkableParameter("date");	
		 super.addEditableParameter("input format", "\"yyyy-MM-dd\"", String.class, "DateFormat of the input date.");
		 super.addEditableParameter("output format", "\"MM/dd/yyyy\"", String.class, "DateFormat to convert input to.");
    }
        
   
  /** formatDate function for nodelist.
   * @param list1 A unique node list.
   * @param list2 A unique node list.
   * @return The nodelist of the converted date format according to the output date format for each node's value.  		    
   */ 
    public org.w3c.dom.NodeList formatDate(org.w3c.dom.NodeList list, String inputFormat, String outputFormat) throws Exception{
	
        Element elem=null;
        Document owner= null;
        for(int i=0;i<list.getLength( );i++){		   
            if(owner==null){
                owner = list.item(i).getOwnerDocument();				
                elem = owner.createElement("tmp");
            }           
            String xValue = getStringValue(list.item(i));
			if(xValue == null || xValue.equals("")){	
                elem.appendChild(owner.createTextNode(""));
			}else{
		        Date d;        
		        if(inputFormat.equalsIgnoreCase("epoch")){
		            d = new Date(Long.parseLong(xValue)*1000);
		        }else{
		            SimpleDateFormat inForm = new SimpleDateFormat((String)inputFormat);
		            d = (Date)inForm.parse(xValue);
		        }
		        
		        if(outputFormat.equalsIgnoreCase("epoch")){
		            Calendar aCalendar = Calendar.getInstance();           
			   		aCalendar.setTime(d);
		            Long aLongValue = new Long(aCalendar.getTimeInMillis()/1000);
					elem.appendChild(owner.createTextNode(aLongValue.toString()));				   		
		        }else{           
		           SimpleDateFormat outForm = new SimpleDateFormat((String)outputFormat);
				   elem.appendChild(owner.createTextNode(outForm.format(d)));
		        }						    
			}
        }
        if(elem==null) return null;
        return elem.getChildNodes();
    } 

}

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
 * StringConcatFunc.java
 *
 * Created on October 10, 2002, 11:47 AM
 */

package com.webdeninteractive.xbotts.Mapping.macro.general;
import com.webdeninteractive.xbotts.Mapping.macro.*;
import com.webdeninteractive.bie.persistent.OIDGenerator;
import java.sql.SQLException;
import java.io.IOException;
import javax.swing.ImageIcon;
/**
 *
 * @author  bmadigan
 */
public class StaticIDGenerator extends AbstractExtensionFunction {
    
    /** Creates a new instance of StringConcatFunc */
    public StaticIDGenerator() {
    }
    
    public String getName() {
        return "getId";
    }
    
    
    MutableParameter[] mparams = new MutableParameter[]{
        new MutableParameter("key", "", Object.class, "Incrementor key", this)
    };
    public MutableParameter[] getMutableParameters() {
        return mparams;
    }
    
    /**
     *
     */
    public Object getId(String incrementorId)throws Exception{
        try{
             return new Integer(OIDGenerator.get(incrementorId));
        }catch(Exception ex){
            throw new Exception("Could not get incrementor: "+incrementorId, ex);
        }
    }
    
    public ImageIcon getIcon() {
        return new ImageIcon(StaticIDGenerator.class.getClassLoader().getResource("com/webdeninteractive/images/macro.gif"));
    }
    

    public String getSource() {
        return getClass( ).getName( );
    }
    
    /** Indicate that this extension should be instantiated, not called statically.
     *
     */
    public boolean isStatic( ){
        return true;
    }
}

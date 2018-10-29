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
public class IdGenerator extends AbstractExtensionFunction {
    
    /** Creates a new instance of StringConcatFunc */
    public IdGenerator() {
    }
    
    public String getName() {
        return "getId";
    }
    
    
    MutableParameter[] mparams = new MutableParameter[]{
        new MutableParameter("key", "", Object.class, "(Optional) incrementor key", this)
    };
    public MutableParameter[] getMutableParameters() {
        return mparams;
    }
    
    Object oid = null;
    
    /**
     *
     */
    public Object getId(String incrementorId)throws Exception{
        try{
            if(oid==null){
                //Create an id
                if(incrementorId==null||incrementorId.equals("")){
                    incrementorId=getSource( )+":"+getId( );
                }
                oid=new Integer(OIDGenerator.get(incrementorId));
            }
            return oid;
        }catch(Exception ex){
            throw new Exception("Could not get incrementor: "+incrementorId, ex);
        }
    }
    
    public ImageIcon getIcon() {
        return new ImageIcon(IdGenerator.class.getClassLoader().getResource("com/webdeninteractive/images/macro.gif"));
    }
    

    public String getSource() {
        return getClass( ).getName( );
    }
    
    /** Indicate that this extension should be instantiated, not called statically.
     *
     */
    public boolean isStatic( ){
        return false;
    }
}

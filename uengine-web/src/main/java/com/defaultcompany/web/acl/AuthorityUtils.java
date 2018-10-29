package com.defaultcompany.web.acl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.uengine.kernel.GlobalContext;
import org.uengine.ui.list.exception.UEngineException;
import org.uengine.util.dao.DefaultConnectionFactory;

/**
 * <pre>
 *  Authority Utility
 * </pre>
 * @author Sung-yeol Jung
 *
 */
public class AuthorityUtils {
    
    /**
     * <pre>
     *  Make Combo List(comTable Search)
     * </pre>
     *
     * @param loggedUserLocale
     * @return
     * 2011. 2. 10.  Sung-yeol Jung
     */
    public String getComTableComboText(String loggedUserLocale) {
        String resultCombo = "";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rsCom = null;
        
        try {
            conn = DefaultConnectionFactory.create().getConnection();
            pstmt = conn.prepareStatement(" select * from comtable where isdeleted = '0' ");
            rsCom = pstmt.executeQuery();
            
            resultCombo = "<option value=\"\">" + GlobalContext.getMessageForWeb("Not Choice", loggedUserLocale) + "</option>";
            
            while (rsCom.next()) {
                resultCombo += "<option value=\"" + rsCom.getString("comcode") + "\">" + rsCom.getString("comname") + "</option>";
            }
        } catch (Exception ex) {
            new UEngineException(ex, "getComTableComboText Exception");
        } finally {
            try {
                if (rsCom != null) { rsCom.close(); }
                if (pstmt != null) { pstmt.close(); }
                if (conn != null) { conn.close(); }
            } catch (Exception e) {
                new UEngineException(e, "getComTableComboText close Exception");
            }
        }
        
        return resultCombo;
    }
}

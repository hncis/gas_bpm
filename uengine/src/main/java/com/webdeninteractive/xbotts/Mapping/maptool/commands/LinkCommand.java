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
 * LinkCommand.java
 *
 * Created on August 13, 2002, 10:47 AM
 */

package com.webdeninteractive.xbotts.Mapping.maptool.commands;
import com.webdeninteractive.xbotts.Mapping.structures.Command;
import com.webdeninteractive.xbotts.Mapping.maptool.*;
import com.webdeninteractive.xbotts.Mapping.compiler.*;
//import com.webdeninteractive.xbotts.Mapping.XML.*;
import javax.swing.undo.*;
import org.w3c.dom.*;
/**
 *
 * @author  bmadigan
 */
public class LinkCommand extends javax.swing.undo.AbstractUndoableEdit implements Command{
    
    /** Creates a new instance of LinkCommand */
    public LinkCommand(Linkable source, Linkable target, MapToolDataModel model) {
        this.source=source;
        this.target=target;
        this.mtdm = model;
    }
    
    Linkable source,target;
    LinkedPair pair;
 //   MapToolController controller = MapToolController.getInstance( );
 //   MapToolDataModel mtdm = controller.getMapDataModel( );
    MapToolDataModel mtdm;
    
    public void redo( ) throws CannotRedoException{
        pair = mtdm.addLink(source, target);
    }
    
    public void undo( ) throws CannotUndoException{
        source.removeHardLinkTarget(target);
        target.removeHardLinkSource(source);
        mtdm.removeLink(pair);
    }
    
    public void execute(){
        /*
        String oc = target.getMaxOccurs( );
        if(!oc.equals("unbounded")){
           // if(target.getLinkType( )!=Linkable.NOT_LINKED){
                int linkCount = target.getHardLinkSources( ).length;
                if(oc==null||oc.equals("")) oc="1";
                int maxOccurs = Integer.parseInt(oc);
                if(linkCount>=maxOccurs){
                   // controller.postWarning(target.getName( )+" has a maxOccurs value of "+maxOccurs+", can't add any more links.");
                  //  return;
                }
           // }
        }
         */
    	
    	
        if(source.getStructure( ).equals(Linkable.ROOT)||target.getStructure( ).equals(Linkable.ROOT)){
            //controller.postWarning("Root nodes are not linkable, link is implied.");
            return;
        }
        if(source.getStructure( ).equals(Linkable.TEMPLATE)||target.getStructure( ).equals(Linkable.TEMPLATE)){
            //controller.postWarning("Document node node cannot be linked, link is implied.");
            return;
        }
        pair = mtdm.addLink(source, target);
    }
    
    public String getPresentationName(){
        return "new link- "+source.getName( )+" -> "+target.getName( );
    }
    
    public String getUndoPresentationName(){
        return "new undo link- "+source.getName( )+" -> "+target.getName( );
    }
    
    public String getRedoPresentationName(){
        return "new redo link- "+source.getName( )+" -> "+target.getName( );
    }
    public boolean canRedo() {return true;}
    public boolean canUndo() {return true;}
    
}

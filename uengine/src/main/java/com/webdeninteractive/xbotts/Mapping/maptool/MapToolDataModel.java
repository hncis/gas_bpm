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
 * MapToolDataModel.java
 *
 * Created on September 24, 2002, 10:43 AM
 */

package com.webdeninteractive.xbotts.Mapping.maptool;
import com.webdeninteractive.xbotts.Mapping.macro.*;
//import com.webdeninteractive.xbotts.Mapping.XML.Tags;
import com.webdeninteractive.xbotts.Mapping.compiler.*;
import com.webdeninteractive.xbotts.Utility.*;

import org.uengine.processdesigner.mapper.TransformerArgument;
import org.w3c.dom.*;

import java.awt.*;
import javax.swing.*;
import java.util.*;

/** Contains a model of the map. MapToolController and other components
 *  use this model for all state information that relates to the map.
 * @author  bmadigan
 */
public class MapToolDataModel{
    
 
    private LinkedPair addLink(String type, String id, Linkable source, Linkable target){
        source.addHardLinkTarget(target);
        target.addHardLinkSource(source);
        LinkedPair pair = new LinkedPair(id,source,target);
        linkedPairs.put(id, pair);
//        /fireLinkAddedEvent(pair);
        return pair;
    }
    
    public LinkedPair addLink(Linkable source, Linkable target){

    	/**
    	 *  duplicated link check
    	 */
    	Linkable[] existingTargets = source.getHardLinkTargets();
    	for(int i=0; i<existingTargets.length; i++){
    		if(existingTargets[i]==target) return null;
    	}
    	
        source.addHardLinkTarget(target);
        target.addHardLinkSource(source);
        if(target.getHardLinkSources( ).length>1){
            //create n to 1 mapping
            Linkable targetParent = target.getOwner( );
            Linkable sourceParent = source.getOwner( );
            
            if(targetParent!=null){
            	targetParent.addNToOneImpliedLinkSource(sourceParent);
            
	            Linkable[] sources = target.getHardLinkSources( );
	            if(target.getHardLinkSources( ).length==2){
	                targetParent.insertNToOneImpliedLinkSource(0,target.getHardLinkSources()[0].getOwner( ));
	            }
            }
        }
        LinkPath srcPath = new LinkPath(source);
        LinkPath tgtPath = new LinkPath(target);
/*        Link link = new Link(srcPath, tgtPath);
        String linkId = Integer.toString(getNextLinkId( ));
        Element linkElement = link.generateLinkElement(getOwnerDocument( ));
        linkElement.setAttribute(Tags.ID, linkId);
        linkElement.setAttribute(Tags.LINK_TYPE, Tags.ATOMIC_LINK);
*/        
        LinkedPair pair = new LinkedPair("test",source,target);
        linkedPairs.put(pair, pair);
        //getMapTable( ).appendChild(linkElement);
        //MapToolController mtc = MapToolController.getInstance( );
       // analyzeAllLinks(mtc.getSourceRoot( ), mtc.getTargetRoot( ));
        fireLinkAddedEvent(pair);

        return pair;
        
        //return null;
    }
    public void removeLink(Linkable source, Linkable target){
        Set keys = linkedPairs.keySet();
        Iterator iter =  keys.iterator( );
        while(iter.hasNext()){
            Object id = iter.next( );
            LinkedPair pair = (LinkedPair) linkedPairs.get(id);
            if(pair.getSource().equals(source)&&
            pair.getTarget().equals(target)){
                iter.remove();
                removeLink(pair);
            }
        }
    }
    public void removeLink(LinkedPair lp){
        if(linkedPairs.containsKey(lp.getId( ))) linkedPairs.remove(lp.getId( ));
        //removeLinkElement(lp.getId( ));

        if(lp.getTarget() instanceof TransformerArgument){
        	TransformerArgument ta = ((TransformerArgument)lp.getTarget());
        	ta.getTransformer().getArgumentSourceMap().remove(ta.getName());
        	ta.removeHardLinkSource((Linkable) lp.getSource());
        }
        
        fireLinkRemovedEvent(lp);
    }
    
     
    public int getNextLinkId( ){
        
        return 1;
    }

    ArrayList listeners = new ArrayList( );
	    public void fireLinkAddedEvent( LinkedPair pair ){
	        MapChangedEvent event = new MapChangedEvent(pair);
	        for(int i=0;i<listeners.size( );i++){
	            MapModelListener l = (MapModelListener) listeners.get(i);
	            l.linkAdded(event);
	        }
	    }
	    public void fireLinkRemovedEvent( LinkedPair pair ){
	        MapChangedEvent event = new MapChangedEvent(pair);
	        for(int i=0;i<listeners.size( );i++){
	            MapModelListener l = (MapModelListener) listeners.get(i);
	            l.linkRemoved(event);
	        }
	    }
	    public void addMapModelListener(MapModelListener listener){
	        listeners.add(listener);
	    }
	    public void removeMapModelListener(MapModelListener listener){
	        listeners.remove(listener);
	    }

     HashMap linkedPairs = new HashMap( );
	     public Iterator getLinkedPairs( ){
	         return linkedPairs.values( ).iterator( );
	     }
	
	 ArrayList extensionFunctions;
		public ArrayList getExtensionFunctions() {
			return extensionFunctions;
		}
	
		public void setExtensionFunctions(ArrayList extensionFunctions) {
			this.extensionFunctions = extensionFunctions;
		}
		 
	     
 }

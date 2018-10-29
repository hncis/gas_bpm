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
 * LinkPanel.java
 *
 * Created on September 6, 2002, 4:35 PM
 */

package com.webdeninteractive.xbotts.Mapping.maptool;

import com.webdeninteractive.xbotts.Mapping.maptool.commands.*;
import com.webdeninteractive.xbotts.Mapping.compiler.*;
//import com.webdeninteractive.xbotts.Mapping.structures.Command;
//import com.webdeninteractive.xbotts.Mapping.XML.*;
import com.webdeninteractive.xbotts.Mapping.macro.*;
import com.webdeninteractive.xbotts.Utility.*;

import org.uengine.kernel.GlobalContext;
import org.uengine.kernel.UEngineException;
import org.uengine.processdesigner.framework.AbsoluteLayout;
import org.uengine.processdesigner.mapper.Transformer;
import org.uengine.processdesigner.mapper.TransformerArgument;
import org.uengine.processdesigner.mapper.TransformerDesigner;
import org.uengine.processdesigner.mapper.transformers.MaxTransformer;
import org.uengine.processdesigner.mapper.transformers.NumberTransformer;
import org.uengine.processdesigner.mapper.transformers.SequenceGeneratorTransformer;
import org.uengine.util.UEngineUtil;
import org.w3c.dom.*;
import java.util.*;
import java.io.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import javax.swing.undo.*;
import javax.swing.filechooser.*;

import java.awt.Color;
import java.awt.Image;
import java.net.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.dnd.*;
import java.awt.dnd.peer.*;
/**
 * Handles macro and link display, contains actions for manipulating both.
 * @author  bmadigan, Chunho Kim
 */
public class LinkPanel extends JPanel implements MapModelListener, TreeExpansionListener{
	
    public LinkPanel(MapToolDataModel mapToolDataModel, JTree sourceTree, JTree targetTree, MapToolPanel mapToolPanel) {
        super(true);
    	this.model = mapToolDataModel;
    	System.out.println("model : " + model.toString());
    	this.sourceTree = sourceTree;
    	this.targetTree = targetTree;
    	this.mapToolPanel = mapToolPanel;
        setBackground(Color.WHITE);
    	
        init( );

        if(sourceTree instanceof SchemaJTree){
        	((SchemaJTree)sourceTree).setLinkPanel(this);
        }
        
        if(targetTree instanceof SchemaJTree){
        	((SchemaJTree)targetTree).setLinkPanel(this);
        }
    	
    }
    
    MapToolPanel mapToolPanel;
    
    MapToolDataModel model;
		public MapToolDataModel getModel() {
			return model;
		}
		public void setModel(MapToolDataModel model) {
			this.model = model;
		}

    JTree sourceTree;
    JTree targetTree;
    
    
    private void init( ) {
        this.setMaximumSize(new Dimension(255,255000));
        this.setPreferredSize(new Dimension(255,10));
        sourceTree.addTreeExpansionListener(this);
        
        targetTree.addTreeExpansionListener(this);
        
        if(model == null) return;
        
        
        
        createPopupmenu();
//	    {
//	    	JMenuItem menuItem = new JMenuItem("Sequence");
//	        menuItem.addActionListener(new ActionListener(){
//	        	public void actionPerformed(ActionEvent ae){
//	        	   	Transformer ta = new SequenceGeneratorTransformer();
//	            	
//	            	final TransformerDesigner tfd = ta.createDesigner(LinkPanel.this);
//	            	
//	            	add(tfd);
//	            	
//	            	Dimension preferredSize = tfd.getPreferredSize();
//	            	tfd.setBounds(lastX,lastY,preferredSize.width,preferredSize.height);
//	            	
//	            	tfd.revalidate();
//	            	revalidate();
//	        	}
//	        });
//	        
//	        menu.add(menuItem);
//        }
//	    
//	    {
//	    	JMenuItem menuItem = new JMenuItem("MaxMin");
//	        menuItem.addActionListener(new ActionListener(){
//	        	public void actionPerformed(ActionEvent ae){
//	        	   	Transformer ta = new MaxTransformer();
//	            	
//	            	final TransformerDesigner tfd = ta.createDesigner(LinkPanel.this);
//	            	
//	            	add(tfd);
//	            	
//	            	Dimension preferredSize = tfd.getPreferredSize();
//	            	tfd.setBounds(lastX,lastY,preferredSize.width,preferredSize.height);
//	            	
//	            	tfd.revalidate();
//	            	revalidate();
//	        	}
//	        });
//	        
//	        menu.add(menuItem);
//        }
//
//	    {
//	    	JMenuItem menuItem = new JMenuItem("ToNumber");
//	        menuItem.addActionListener(new ActionListener(){
//	        	public void actionPerformed(ActionEvent ae){
//	        	   	Transformer ta = new NumberTransformer();
//	            	
//	            	final TransformerDesigner tfd = ta.createDesigner(LinkPanel.this);
//	            	
//	            	add(tfd);
//	            	
//	            	Dimension preferredSize = tfd.getPreferredSize();
//	            	tfd.setBounds(lastX,lastY,preferredSize.width,preferredSize.height);
//	            	
//	            	tfd.revalidate();
//	            	revalidate();
//	        	}
//	        });
//	        
//	        menu.add(menuItem);
//        }

        
/*        ArrayList extFuncs = model.getExtensionFunctions( );
        for(int i=0;i<extFuncs.size( );i++){
            ExtensionFunction func = (ExtensionFunction)extFuncs.get(i);
            ComponentFactory componentFactory = ComponentFactory.newInstance();
            
            JPanel extc = componentFactory.getExtensionComponent(func);
            extc.setBounds(func.getX( ), func.getY(),  extc.getWidth( ), extc.getHeight( ));
            func.setUI(extc);
            add(extc);
            //get the links for this function, add them too.
            
            ArrayList links = model.getLinksForExtensionId(func.getId( ));
            if(links==null) continue;
            for(int j=0;j<links.size();j++){
                LinkedPair lp = (LinkedPair)links.get(j);
                Object source = lp.getSource( );
                Object target = lp.getTarget( );
                if(source instanceof Linkable){
                    addSourceToParameterLink((Linkable)source,(Parameter)target);
                }else if(source instanceof ExtensionFunction){
                    if(target instanceof Linkable){
                        addExtensionFunctionToTargetLink((ExtensionFunction)source, (Linkable)target);
                    }else if(target instanceof Parameter){
                        addExtensionFunctionToParameterLink((ExtensionFunction)source, (Parameter) target);
                    }
                }
            }
        }
        
        menu.add(new ExtensionProperties( ).buildExtensionMenu( ));*/
        /** the following may look funny, but its meant to handle a popup menu
         * on any platform.
         */
        this.addMouseListener(new MouseAdapter( ){
            boolean showMenu = false;
            public void mousePressed(MouseEvent event){
                if(event.isPopupTrigger()|| event.getModifiers() == 4){
                    showMenu=true;
                }
            }
            public void mouseReleased(MouseEvent event){
                if(event.isPopupTrigger()|| event.getModifiers() == 4){
                    showMenu=true;
                }
                if(showMenu){
                    popupMenu.show(LinkPanel.this,event.getX(), event.getY( ));
                    LinkPanel.this.lastX = event.getX( );
                    LinkPanel.this.lastY = event.getY( );
                    showMenu=false;
                }
            }
        });
        setLayout(new AbsoluteLayout());
    }
    
    int lastX = 0;
    public JTree getSourceTree() {
		return sourceTree;
	}
	public void setSourceTree(JTree sourceTree) {
		this.sourceTree = sourceTree;
	}
	public JTree getTargetTree() {
		return targetTree;
	}
	public void setTargetTree(JTree targetTree) {
		this.targetTree = targetTree;
	}


	int lastY = 0;
    final JPopupMenu popupMenu = new JPopupMenu();
    
    private void addMenuItem(JMenu parent, final String clsName) throws Exception {
    	
    	Transformer td = getTransformer(clsName);
    	JMenuItem menuItem = new JMenuItem(td.getName());
        menuItem.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent ae) {
        		addTransformer(clsName);
        	}
        });
        
        if (parent == null)
        	popupMenu.add(menuItem);
        else 
        	parent.add(menuItem);
    }
    
    public void addTransformer(String clsName){
		Transformer transformer = null;
		try {
			transformer = getTransformer(clsName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
    	addTransformer(transformer);

    }
    
    public void addTransformer(Transformer transformer){
    	TransformerDesigner tfd = transformer.createDesigner(LinkPanel.this);
    	
    	add(tfd);
    	
    	Dimension preferredSize = tfd.getPreferredSize();
    	tfd.setBounds(lastX,lastY,preferredSize.width,preferredSize.height);
    	
    	tfd.revalidate();
    	transformer.afterAdded();
    	
    	revalidate();

    }
    
    private JMenu getMenuGroup(String groupName, String clsName) throws Exception {
    	JMenu menuGroup = new JMenu(groupName);
    	addMenuItem(menuGroup, clsName);
        popupMenu.add(menuGroup);
        
        return menuGroup;
    }
    
    private ArrayList getTransformerTypeList() {
		try {
			return (ArrayList) GlobalContext.deserialize(getClass().getClassLoader().getResourceAsStream(
							"org/uengine/processdesigner/transformertypes.xml"
					), String.class);
		} catch (Exception e) {
			return new ArrayList();
		}
	}
    
    @SuppressWarnings("unchecked")
	public void createPopupmenu() {
		final ArrayList taskList = new ArrayList();
		HashMap bandByTask = new HashMap(){
			public Object put(Object key, Object val){
				taskList.add(key);
				return super.put(key, val);
			}
		};
		
		HashMap transformerByBand = new HashMap();
		try {
			ArrayList clsNames = getTransformerTypeList(); 
			if(clsNames==null || clsNames.size()==0) 
				throw new UEngineException("No transformertypes.xml found by the ClassLoader. Please check the classpath.");
			
			ArrayList<JMenu> groupList = new ArrayList<JMenu>();
			for(Iterator iter = clsNames.iterator(); iter.hasNext();){
				Object tfDesc = iter.next();
				

				if(tfDesc==null) continue;
				
				//get the activity descriptions					
				String clsName = null;
				String group = "$transformertypes.groups.default.label";
				String name = null;
				
				
					if (tfDesc instanceof TransformerTypeDescriptor){
						TransformerTypeDescriptor ttd = (TransformerTypeDescriptor) tfDesc;
						clsName = ttd.getTransformerTypeClass();
						group = ttd.getGroup();
						name = ttd.getName();
						
						if (clsName != null)		clsName = clsName.trim();
						if (group != null)		group = group.trim();
						if (name != null)		name = name.trim();
						
					} else {
						clsName = (String)tfDesc;
					}
					
					if (name == null) {
						name = UEngineUtil.getClassNameOnly(clsName);
						name = GlobalContext.getLocalizedMessage("transformertypes." + clsName.toLowerCase() + ".label", name);
					}
					
					if (group.indexOf('.') != -1) {
						int intGroupNamePoint = group.lastIndexOf('.');
						group = group.substring(intGroupNamePoint + 1);
					} 


//					final Class<Transformer> transformerCls;
//					
//					try {
//						transformerCls = (Class<Transformer>) Class.forName(clsName);
//					}catch(Throwable e){
//						e.printStackTrace();
//						
//						continue;
//					}
//					
//					Transformer transformer = transformerCls.newInstance();
//					
//					if (group.equals(name)) {
//						addMenuItem(null, transformer);
//					} else {
//						boolean isGroup = false;
//						for (int i = 0; i < groupList.size(); i++) {
//							JMenu groupMenu = groupList.get(i);
//							if ((groupMenu.getText()).equals(group)) {
//								addMenuItem(groupMenu, transformer);
//								isGroup = true;
//							}
//						}
//						
//						if (!isGroup) {
//							groupList.add(getMenuGroup(group, transformer));
//						}
//					}
					
					if (group.equals(name)) {
						addMenuItem(null, clsName);
					} else {
						boolean isGroup = false;
						for (int i = 0; i < groupList.size(); i++) {
							JMenu groupMenu = groupList.get(i);
							if ((groupMenu.getText()).equals(group)) {
								addMenuItem(groupMenu, clsName);
								isGroup = true;
							}
						}
						
						if (!isGroup) {
							groupList.add(getMenuGroup(group, clsName));
						}
					}
			}	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		JMenuItem linkSelectedPaths = new JMenuItem("Link Selected");
		linkSelectedPaths.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
        		SchemaJTree sourceTree = (SchemaJTree) getSourceTree();
        		SchemaJTree targetTree = (SchemaJTree) getTargetTree();
        		
        		TreePath[] srcTreePaths = sourceTree.getSelectionPaths();
        		TreePath[] trgTreePaths = targetTree.getSelectionPaths();
        		
        		int minLength = srcTreePaths.length;
        		if(srcTreePaths.length > trgTreePaths.length) minLength = trgTreePaths.length;
        		
        		for(int i=0; i<minLength; i++){
        			Linkable target = (Linkable) trgTreePaths[i].getLastPathComponent();
        			Linkable source = (Linkable) srcTreePaths[i].getLastPathComponent();
        			
        			addLink(source, target);
        			
        		}
        		
        		revalidate();

			}
			
		});
		popupMenu.add(linkSelectedPaths);
		
		JMenuItem unlinkSelectedPaths = new JMenuItem("Unlink Selected");
		unlinkSelectedPaths.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
        		SchemaJTree sourceTree = (SchemaJTree) getSourceTree();
        		SchemaJTree targetTree = (SchemaJTree) getTargetTree();
        		
        		TreePath[] srcTreePaths = sourceTree.getSelectionPaths();
        		TreePath[] trgTreePaths = targetTree.getSelectionPaths();
        		
        		if(srcTreePaths!=null)
        		for(int i=0; i<srcTreePaths.length; i++){
        			Linkable source = (Linkable) srcTreePaths[i].getLastPathComponent();
        			
        			Linkable[] targets = source.getHardLinkTargets();
        			for(int j=0;j<targets.length;j++){
                        removeLink(source, targets[j]);
                    }
        		}
        		
        		if(trgTreePaths!=null)
        		for(int i=0; i<trgTreePaths.length; i++){
        			Linkable target = (Linkable) trgTreePaths[i].getLastPathComponent();
        			
        			Linkable[] sources = target.getHardLinkSources();
        			for(int j=0;j<sources.length;j++){
                        removeLink(sources[j], target);
                    }
        			
        		}
        		
        		revalidate();

			}
			
		});		
		popupMenu.add(unlinkSelectedPaths);
	}
    
    @SuppressWarnings("unchecked")
	private Transformer getTransformer(String clsName) throws Exception {
    	Class<Transformer> transformerCls = null;
		
		try {
			transformerCls = (Class<Transformer>) Class.forName(clsName);
		} catch(Throwable e) {
			e.printStackTrace();
		}
		
		Transformer transformer = transformerCls.newInstance();
		
		return transformer;
    }
    
 /*   public AbstractAction getActionForMacro(final Class cl){
        try{
            ExtensionElement ext = (ExtensionElement)cl.newInstance( );
            return new AbstractAction(ext.getName( ),ext.getIcon( )){
                public void actionPerformed(ActionEvent e){
                    try{
                        MapToolController.getInstance( ).execute(new AddExtensionElement((ExtensionElement)cl.newInstance( )));
                    }catch(Exception ex){
                        MapToolController.getInstance( ).postException(ex);
                    }
                }
            };
        }catch(Exception ex){
            MapToolController.getInstance( ).postException(ex);
            return new AbstractAction("Class not found: "+cl.getName( )){
                public void actionPerformed(ActionEvent e){
                    
                }
            };
        }
    }
*/    
/*    public AbstractAction getActionForExtensionFunction(final Class cl){
        try{
            ExtensionFunction ext = (ExtensionFunction)cl.newInstance( );
            //return new AbstractAction(ext.getDisplayName( ), ext.getIcon( )){
            return new AbstractAction(ext.getName( ), ext.getIcon( )){
                public void actionPerformed(ActionEvent e){
                    try{
                        MapToolController.getInstance( ).execute(
                        new AddExtensionFunction((ExtensionFunction)cl.newInstance( ))
                        );
                    }catch(Exception ex){
                        MapToolController.getInstance( ).postException(ex);
                    }
                }
            };
        }catch(Exception ex){
            MapToolController.getInstance( ).postException(ex);
            return new AbstractAction("Cant find Extension Class: "+cl.getName( )){
                public void actionPerformed(ActionEvent e){
                    
                }
            };
        }
    }
*/    
    ArrayList macros = new ArrayList( );
 /*   public void addMacro(MacroComponent macro){
        macro.setBounds(LinkPanel.this.lastX,LinkPanel.this.lastY,macro.getWidth( ), macro.getHeight( ));
        macros.add(macro);
        add(macro);
        revalidate();
        getParent( ).repaint( );
    }
    public void addMacro(MacroComponent macro, Rectangle bounds){
        macro.setBounds(bounds);
        macros.add(macro);
        add(macro);
        revalidate();
        getParent( ).repaint( );
    }
    public void addMacro(MacroComponent macro, int x, int y){
        macro.setBounds(x,y,100,65);
        macros.add(macro);
        add(macro);
        revalidate();
        getParent( ).repaint( );
    }
    public void removeMacro(MacroComponent macro){
        this.remove(macro);
        revalidate( );
        getParent().repaint( );
    }
    
    public MacroComponent getMacro(int id){
        for(int i=0;i<macros.size( );i++){
            MacroComponent mc = (MacroComponent)macros.get(i);
            ExtensionElement ext = mc.getExtensionElement( );
            if(id == ext.getId( )) return mc;
        }
        return null;
    }
    */
    HashMap sourceToParam = new HashMap( );
    private void addSourceToParameterLink(Linkable source, Parameter parameter){
        sourceToParam.put(source, parameter);
    }
    private void removeSourceToParameterLink(Linkable source, Parameter parameter){
        sourceToParam.remove(source);
    }
    HashMap extToTarget = new HashMap( );
    private void addExtensionFunctionToTargetLink(ExtensionFunction function, Linkable target){
        extToTarget.put(function, target);
    }
    private void removeExtensionFunctionToTargetLink(ExtensionFunction function, Linkable target){
        extToTarget.remove(function);
    }
    
    
    /** Adding soon.
     *
     */
    HashMap extToParameter = new HashMap( );
    private void addExtensionFunctionToParameterLink(ExtensionFunction function, Parameter parameter){
        extToParameter.put(function, parameter);
    }
    private void removeExtensionFunctionToParameterLink(ExtensionFunction function, Parameter parameter){
        extToParameter.remove(function);
    }
    HashMap sourceToMacroMap = new HashMap( );
 /*   public void addSourceToArgumentLink(Linkable source,
    MacroComponent component,
    Argument arg ){
        sourceToMacroMap.put(source, component);
    }
    
    HashMap funcToArgMap = new HashMap( );
    public void addFunctionToArgumentLink( MacroComponent component1,
    Function function,
    MacroComponent component2,
    Argument arg ){
        funcToArgMap.put(component1, component2);
    }
*/ /*   HashMap funcToTargetMap = new HashMap( );
    public void addFunctionToTargetLink( MacroComponent component,
    Function function, Linkable target ){
        funcToTargetMap.put(target, component);
    }
 */   
    public void drawGrid(Graphics graphics ){
        Graphics2D g = (Graphics2D) graphics;
        g.setColor(Color.lightGray);
        int size = 20;
        int v = this.getBounds().width/size;
        int h = 100;
        for(int i=1;i<=h;i++) {
            g.drawLine(0,i*size,this.getBounds( ).width,i*size);
        }
        for(int i=1;i<=v;i++){
            g.drawLine(i*size,0,i*size, this.getBounds( ).height);
        }
    }
    
    
    
    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        Graphics2D g = (Graphics2D)graphics;
        
        //g.drawRect(0,0,500,500);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // float dash1[] = {5.0f};
        // g.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f));
        
        Iterator iter = getModel( ).getLinkedPairs( );
        while(iter.hasNext( )){
            paintLink(g, Color.black, (LinkedPair)iter.next( ));
        }
 /*       iter = getModel( ).getContextMappings( ).values( ).iterator( );
        while(iter.hasNext( )){
            //paint context mappings
            paintLink(g, Color.blue, (LinkedPair)iter.next( ));
        }
*///        paintFunctionToTargetLines(g);
    }
    
    protected void paintLink(Graphics2D g, Color c, LinkLine l, boolean highlighted){
        Color srcColor,tgtColor;
        if(l.srcVisible){
            srcColor=c;
        }else{
            srcColor = new Color(c.getRed( ), c.getGreen( ), c.getBlue( ), 0);
        }
        if(l.tgtVisible){
            tgtColor=c;
        }else{
            tgtColor = new Color(c.getRed( ), c.getGreen( ), c.getBlue( ), 0);
        }
        Paint p = new GradientPaint(l.x1,l.y1, srcColor, l.x2, l.y2, tgtColor);
        g.setPaint(p);
        Stroke old = g.getStroke( );
        g.setStroke(new BasicStroke((highlighted? 1.0f:0.5f)));
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.drawLine(l.x1+6,l.y1,l.x2-7,l.y2);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        g.setStroke(old);//reset
        if(l.srcVisible) g.drawRect(l.x1,l.y1-3,6,6);
        if(l.tgtVisible) g.drawRect(l.x2-7,l.y2-3,6,6);
        g.setColor(c);
    }
    
    private void paintLink(Graphics2D g, Color c, LinkedPair linkedPair){
        Linkable src = (Linkable)linkedPair.getSource();
        Linkable target = (Linkable)linkedPair.getTarget();
        LinkLine l;
        l = getLinkLine(src, target);
 
        if(l!=null) paintLink(g, c, l,(isSelected(src,sourceTree)||isSelected(target, targetTree)));
    }
    
    private boolean isSelected(Linkable node, JTree owner){
    	if(!(node instanceof DefaultMutableTreeNode)) return false;
    	
        DefaultMutableTreeNode dmt = (DefaultMutableTreeNode)node;
        TreePath path = new TreePath(dmt.getPath( ));
        return owner.isPathSelected(path);
    }
    
/*    public void paintFunctionToTargetLines(Graphics2D g){
        HashMap functionLinks = getModel( ).getFunctionLinks( );
        Iterator keys = functionLinks.keySet().iterator( );
        while(keys.hasNext( )){
            ArrayList links = (ArrayList) functionLinks.get(keys.next( ));
            Iterator pairs = links.iterator( );
            while(pairs.hasNext( )){
                LinkedPair pair = (LinkedPair) pairs.next( );
                Object source = pair.getSource( );
                Object target = pair.getTarget( );
                if(source instanceof Linkable){
                    paintFunctionLink(g, Color.black, (Linkable)source, (Parameter)target);
                }else if(source instanceof ExtensionFunction && target instanceof Parameter){
                    paintFunctionLink(g, Color.black, (ExtensionFunction)source, (Parameter)target);
                }else if(source instanceof ExtensionFunction && target instanceof Linkable){
                    paintFunctionLink(g, Color.black, (ExtensionFunction) source, (Linkable) target);
                }else{
                    System.out.println("Cant paint function link: "+source+" to "+target);
                    return;
                }
            }
        }
    }*/
    
/*    private void paintFunctionLink
    (Graphics2D g, Color color, Linkable source, Parameter param){
        LinkLine l = new LinkLine( );
        ExtensionComponent macro =(ExtensionComponent) param.getExtensionFunction( ).getUI( );
        if(macro==null){
            return;
        }
        int x1,y1,x2,y2;
        x1=0;
        //disabled//TreePath path = getNearestVisiblePath(sourceTree, (LinkableTreeNode)source);
        if(path==null) return;
        y1=((sourceTree.getRowForPath(path)+1)*rowHeight)-(rowHeight/2);
        Rectangle bounds = macro.getBounds( );
        if(macro.isExpanded()){
            Component c = macro.getParameterComponent(param);
            Point p = new Point(c.getBounds().x, c.getBounds( ).y+(c.getBounds( ).height/2));
            Point linkPoint = SwingUtilities.convertPoint(macro.getInputsPanel( ),p,this);
            x2 = linkPoint.x;
            y2 = linkPoint.y;
        }else{
            x2 = bounds.x;
            y2 = bounds.y+(bounds.height/2);
        }
        l.x1=x1;
        l.x2=x2;
        l.y1=y1;
        l.y2=y2;
        if(!path.getLastPathComponent( ).equals(source)){
            l.srcVisible=false;
        }
        paintLink(g,color,l, isSelected(source, sourceTree));
    }
    
    private void paintFunctionLink
    (Graphics2D g, Color color, ExtensionFunction source, Parameter param){
        g.setStroke(new BasicStroke(0.5f));
        g.setColor(Color.gray);
        ExtensionComponent sourceComponent = (ExtensionComponent)source.getUI();
        ExtensionFunction paramOwner = param.getExtensionFunction( );
        ExtensionComponent targetComponent = (ExtensionComponent)paramOwner.getUI( );
        int x1,y1,x2,y2;
        if(sourceComponent.isExpanded()){
            Component resultComponent = sourceComponent.getResultComponent();
            
            Point linkPoint = SwingUtilities.convertPoint(sourceComponent.getOutputsPanel( ),
            new Point(resultComponent.getBounds().x, resultComponent.getBounds( ).y+(resultComponent.getBounds( ).height/2)),
            this);
            
            y1 = linkPoint.y;
            x1 = linkPoint.x+(resultComponent.getBounds().width);
        }else{
            Rectangle bounds = sourceComponent.getBounds( );
            x1 = bounds.x+bounds.width;
            y1 = bounds.y + (bounds.height/2);
        }
        if(targetComponent.isExpanded( )){
            Component paramComp = targetComponent.getParameterComponent(param);
            Point linkPoint = SwingUtilities.convertPoint(targetComponent.getInputsPanel( ),
            new Point(paramComp.getBounds().x, paramComp.getBounds( ).y+(paramComp.getBounds( ).height/2)),
            this);
            y2 = linkPoint.y;
            x2 = linkPoint.x;
        }else{
            Rectangle bounds = targetComponent.getBounds( );
            x2 = bounds.x;
            y2 = bounds.y + (bounds.height/2);
        }
        g.drawRect(x1,y1-3,6,6);
        g.drawRect(x2-7,y2-3,6,6);
        g.drawLine(x1+6,y1,x2-7,y2);
    }
    
    private void paintFunctionLink
    (Graphics2D g, Color color, ExtensionFunction source, Linkable target){
        ExtensionComponent component = (ExtensionComponent)source.getUI();
        if(component==null){
            System.out.println("no component for extension: "+source.getName( ));
            return;
        }
        int x1,y1,x2,y2;
        x1=component.getBounds().x+component.getBounds().width;
        x2=this.getBounds().width;
        TreePath path = getNearestVisiblePath(targetTree, (LinkableTreeNode)target);
        if(path==null) return;
        y2=((targetTree.getRowForPath(path)+1)*rowHeight)-(rowHeight/2);
        Rectangle bounds = component.getBounds( );
        if(component.isExpanded()){
            Component c = component.getResultComponent();
            Point p = new Point(c.getBounds().x, c.getBounds( ).y+(c.getBounds( ).height/2));
            Point linkPoint = SwingUtilities.convertPoint(component.getOutputsPanel( ),p,this);
            y1 = linkPoint.y;
        }else{
            y1 = bounds.y+(bounds.height/2);
        }
        LinkLine l = new LinkLine(x1,y1,x2,y2,true,
        (path.getLastPathComponent( ).equals(target)?true:false));
        
        paintLink(g, color, l, isSelected(target, targetTree));
    }
    */
    HashMap nToNLinks = new HashMap( );
    public void paintNToNLinks(Graphics2D g){
    }
    
    public void addImpliedSchemaLink(LinkableTreeNode source, LinkableTreeNode target){
        impliedRefs.put(source,target);
        repaint( );
    }
    
    public void addLink(Linkable source, Linkable target){
    	getModel().addLink(source, target);
    	//revalidate();
    	//invalidate();
    	getParent().repaint();
    }
    
    public void removeLink(Linkable source, Linkable target){
    	//getMapToolController().view.repaint();
    	getModel().removeLink(source, target);
    	getParent().repaint();
    }
    
    HashMap impliedRefs = new HashMap( );
    HashMap lines = new HashMap( );
    
    protected int rowHeight = 18;
    public void setRowHeight(int h){
        this.rowHeight=h;
    }
    
    protected LinkLine getLinkLine(Linkable source, Linkable target){
        int x1,y1,x2,y2;
        x1=0;
        x2=this.getBounds( ).width;
        boolean srcVisible = true;
        boolean tgtVisible = true;
        
        if(source instanceof LinkableTreeNode){
	        TreePath srcPath = getNearestVisiblePath(sourceTree, (LinkableTreeNode)source);
	        if(srcPath==null) return null;
	        if(!srcPath.getLastPathComponent().equals(source)){
	            srcVisible=false;
	        }
	        y1=(((sourceTree.getRowForPath(srcPath)+1)*rowHeight)-(rowHeight/2));
	        //y1 = source.getComponent().getLocation().y;
        }else{
        	Point sourcePoint = source.getLinkPoint();
        	x1 = sourcePoint.x;
        	y1 = sourcePoint.y;
        	

        }

        if(target instanceof LinkableTreeNode){
	        x2=this.getBounds( ).width;
	        TreePath tgtPath = getNearestVisiblePath(targetTree, (LinkableTreeNode)target);
	        if(tgtPath==null) return null;
	        if(!tgtPath.getLastPathComponent( ).equals(target)){
	            tgtVisible=false;
	        }
	        y2=(((targetTree.getRowForPath(tgtPath)+1)*rowHeight)-(rowHeight/2));
	        //y2 = target.getComponent().getY();
        }else{
        	Point targetPoint = target.getLinkPoint();
        	x2 = targetPoint.x;
        	y2 = targetPoint.y;
        }
        
        return new LinkLine(x1,y1,x2,y2,srcVisible, tgtVisible);
        //return new LinkLine(x1,y1,x2,y2, true, true);
    }
    
    public TreePath getNearestVisiblePath(JTree tree, TreeNode source){
        if(source==null) return new TreePath(tree.getModel( ).getRoot( ));
        Enumeration enumeration = tree.getExpandedDescendants(tree.getPathForRow(0));
        while(enumeration!=null&&enumeration.hasMoreElements( )){
            TreePath next =(TreePath) enumeration.nextElement( );
            TreeNode node = (TreeNode)next.getLastPathComponent( );
            if(node.equals(source)) return next;//never happens?
            int index = node.getIndex(source);
            if(index!=-1){
                TreePath path = next.pathByAddingChild(source);
                if(tree.isVisible(path)){
                    return next.pathByAddingChild(source);
                }
            }
        }
        return getNearestVisiblePath(tree, source.getParent( ));
    }
    
    public int getNearestVisibleRow(JTree tree, TreeNode source){
        TreePath path = getNearestVisiblePath(tree, source);
        return tree.getRowForPath(path);
    }
    
    
    public void extensionFunctionAdded(MapChangedEvent event) {
 /*       ExtensionFunction source = (ExtensionFunction) event.getSource( );
        ExtensionComponent ui = (ExtensionComponent)getMapToolController().getComponentFactory().getExtensionComponent(source);// new ExtensionComponent(source);
        source.setUI(ui);
        revalidate( );
        ui.setBounds(LinkPanel.this.lastX,LinkPanel.this.lastY,ui.getWidth( ), ui.getHeight( ));
        add(ui);
 */   }
    
    public void extensionFunctionRemoved(MapChangedEvent event) {
        ExtensionFunction source = (ExtensionFunction) event.getSource( );
        remove(source.getUI( ));
        revalidate();
        getParent( ).repaint( );
    }
    

    public void linkAdded(MapChangedEvent event) {
//    	getMapToolController().view.repaint();
    }
    
    public void linkRemoved(MapChangedEvent event) {
//    	getMapToolController().view.repaint();
    }
    
    public void contextMappingAdded(MapChangedEvent event) {
//    	getMapToolController().view.repaint();
    }
    
    public void contextMappingRemoved(MapChangedEvent event) {
//    	getMapToolController().view.repaint();
    }
    
    public void treeCollapsed(javax.swing.event.TreeExpansionEvent treeExpansionEvent) {
        repaint( );
    }
    
    public void treeExpanded(javax.swing.event.TreeExpansionEvent treeExpansionEvent) {
        repaint( );
    }
    
    public void valueChanged(javax.swing.event.TreeSelectionEvent treeSelectionEvent) {
        repaint( );
    }
    
    public Dimension getPreferredSize( ){
        Dimension pref = super.getPreferredSize( );
        Component[] comp = super.getComponents();
        int height = pref.height;
        for(int i=0;i<comp.length;i++){
            
 /*           if(comp[i] instanceof ExtensionComponent){
                Rectangle bounds = comp[i].getBounds();
                if(bounds.y+100 > height){
                    height = bounds.y+100;
                }
            }
*/        }
        pref.height=height;
        return pref;
    }
	public MapToolPanel getMapToolPanel() {
		return mapToolPanel;
	}
	public JPopupMenu getPopupMenu() {
		return popupMenu;
	}



}


 

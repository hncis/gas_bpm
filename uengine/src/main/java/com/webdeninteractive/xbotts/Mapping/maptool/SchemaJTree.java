/*
-------------------------------------------------------------------
BIE is Copyright 2001-2004 Brunswick Corp.
-------------------------------------------------------------------
Please read the legal notices (docs/legal.txt) and the license
(docs/bie_license.txt) that came with this distribution before using
this software.
-------------------------------------------------------------------
 * SchemaJTree.java
 *
 * Created on August 9, 2002, 4:27 PM
 */

package com.webdeninteractive.xbotts.Mapping.maptool;

import com.webdeninteractive.xbotts.Mapping.compiler.*;
import com.webdeninteractive.xbotts.Mapping.macro.*;

import javax.swing.*;
import javax.swing.tree.*;

import java.util.*;

import javax.swing.event.*;

import org.metaworks.FieldDescriptor;
import org.metaworks.InputForm;
import org.metaworks.Instance;
import org.metaworks.Type;
import org.metaworks.inputter.Inputter;
import org.metaworks.inputter.WebInputter;
import org.metaworks.validator.NotNullValid;
import org.metaworks.validator.Validator;
import org.uengine.contexts.HtmlFormContext;
import org.uengine.kernel.GlobalContext;
import org.uengine.kernel.ParameterContext;
import org.uengine.kernel.ProcessDefinition;
import org.uengine.kernel.ProcessVariable;
import org.uengine.kernel.UEngineException;
import org.uengine.processdesigner.DesignerLabel;
import org.uengine.processdesigner.ProcessDesigner;
import org.uengine.processdesigner.ProgressDialog;
import org.uengine.processdesigner.mapper.transformers.FormFieldDescriptor;
import org.uengine.util.UEngineUtil;

import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.dnd.*;
import java.awt.dnd.peer.*;
import java.awt.event.*;
import java.io.*;

/**
 * @author  bmadigan
 */
public class SchemaJTree extends JTree implements DragGestureListener, DragSourceListener, DropTargetListener  {
	
	SchemaJTree sourceTree;
    Cursor theCursor;
    boolean bShift = false;

    public void setSourceTree(SchemaJTree sourceTree) {
		this.sourceTree  = sourceTree;
	}
	
	Linkable lSelectedComponent;
	boolean isSource;
	LinkPanel linkPanel;
    final JPopupMenu menu = new JPopupMenu( );

    public SchemaJTree(TreeModel model, boolean isSource) {
        super(model);
        
        setToolTipText("");
        
        this.linkPanel = linkPanel;
        
		setFont(new java.awt.Font("Dialog", 0, 12));
        this.isSource=isSource;
        setBackground(Color.WHITE);
        this.setUI(new BTreeUI(isSource));
        
        dragSource = new DragSource( ){
            protected DragSourceContext createDragSourceContext(
            DragSourceContextPeer dscp,
            DragGestureEvent dgl,
            Cursor dragCursor,
            Image dragImage,
            Point imageOffset,
            Transferable t,
            DragSourceListener dsl){
                theCursor = dragCursor;
                return new DragSourceContext(dscp, dgl, dragCursor,dragImage,imageOffset,t, dsl);
            }
            protected void updateCurrentCursor(int dropOp,
            int targetAct, int status) {}
        };
        
       DragGestureRecognizer dgr =
        dragSource.createDefaultDragGestureRecognizer(
        this,                               //DragSource
        DnDConstants.ACTION_LINK,           //specifies valid actions
        this                                //DragGestureListener
        );
        dgr.setSourceActions(dgr.getSourceActions() & ~InputEvent.BUTTON3_MASK);
        DropTarget dropTarget = new DropTarget(this, this);
        
        this.setRowHeight(18);
        // this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
        // this.setBorder(BorderFactory.createLineBorder(Color.blue, 1));
        SchemaCellRenderer renderer = new SchemaCellRenderer( );
        this.setCellRenderer(renderer);
        
//        ToolTipManager.sharedInstance().registerComponent(this);
//        final JMenuItem expandItem = new JMenuItem(expandAction);
//        final JMenuItem collapseItem = new JMenuItem(collapseAction);
//        final JMenuItem linkSource = new JMenuItem(LinkSourceAction);
        final JMenuItem unlinkItem = new JMenuItem(unlinkAction);
        final JMenuItem setAsKeyItem = new JMenuItem(setAsKeyAction);
        final JMenuItem unSetKeyItem = new JMenuItem(unSetKeyAction);
        final JMenu selectSource = new JMenu("Link From");
 
        menu.add(unlinkItem);
        menu.add(setAsKeyItem);
        menu.add(unSetKeyItem);
        menu.add(new JMenuItem(createVariableAction));
        menu.add(new JMenuItem(createFormAction));
        
//        menu.add(expandItem);
//        menu.add(collapseItem);
//        menu.add(new JSeparator( ));
//        if(isSource) menu.add(linkSource);
//        
        this.addKeyListener(new KeyAdapter(){
        	public void keyPressed(KeyEvent e){
				if(e.getKeyCode()==16){
					bShift = true;
				}
        	}
        	
        	public void keyReleased(KeyEvent e){
        		if(e.getKeyCode()==16){
        			bShift = false;
        		}
        	}
        });
        
        this.addMouseListener(new MouseAdapter() {
            boolean showMenu = false;
            public void mousePressed(MouseEvent evt) {
                int x = evt.getX( );
                int y = evt.getY( );
                int row = getRowForLocation(x,y);
                if(row==-1) return;
                if(SchemaJTree.this.isSource){
                    SchemaJTree.this.addSelectionRow(row);
                }else{
                    SchemaJTree.this.addSelectionRow(row);
                }
                if (evt.isPopupTrigger() || evt.getModifiers()==4) {
                    showMenu=true;
                    if(!SchemaJTree.this.isSource) SchemaJTree.this.setSelectionRow(row);
                }
                //MapToolController.getInstance().getLinkPanel().repaint( );//tell it to repaint
            }
            public void mouseReleased(MouseEvent evt) {
                int x = evt.getX( );
                int y = evt.getY( );
                int row = getRowForLocation(x,y);
                
                /*if(bShift){
                	
                	if(SchemaJTree.this.isSource){
                    	Linkable target;
                		SchemaJTree schemaJTree = (SchemaJTree)linkPanel.targetTree;
                		target = (Linkable) schemaJTree.getSelectionPath().getLastPathComponent();
                	
                		TreePath sourcePath = getPathForLocation(x, y);
                        
                        if(sourcePath!=null){
                            Linkable source = (Linkable)sourcePath.getLastPathComponent();
                            linkPanel.addLink(source, target);
                        }
                	}else{
                    	Linkable src;
                		SchemaJTree schemaJTree = (SchemaJTree)linkPanel.sourceTree;
                		src = (Linkable) schemaJTree.getSelectionPath().getLastPathComponent();
                	
                		TreePath targetPath = getPathForLocation(x, y);
                        
                        if(targetPath!=null){
                            Linkable target = (Linkable)targetPath.getLastPathComponent();
                            linkPanel.addLink(src, target);
                        }
                	}
                	
                    

                }*/
                
                
                if(row==-1) return;
                if (evt.isPopupTrigger()) {
                    showMenu=true;
                }
                if(showMenu){
                    /*selectSource.removeAll( );
                    java.util.Set keys = control.clipboardManager.getKeys( );
                    if(keys.size( )>0&&!SchemaJTree.this.isSource){
                        menu.add(selectSource);
                        Iterator iter = keys.iterator();
                        while(iter.hasNext( )){
                            selectSource.add(new JMenuItem(new LinkTargetAction((String)iter.next( ))));
                        }
                    }
                    menu.add(unlinkItem);*/
                    menu.show(evt.getComponent(), evt.getX(), evt.getY());
                    showMenu=false;
                }
            }
        });
       // this.addTreeSelectionListener(selectListener);
        expandAll( );
    }
    
    
    AbstractAction unlinkAction = new AbstractAction("Unlink"){
        public void actionPerformed(java.awt.event.ActionEvent e) {
            try{
                TreePath[] paths = SchemaJTree.this.getSelectionPaths( );
                for(int i=0;i<paths.length;i++){
                    Linkable selected = (Linkable) paths[i].getLastPathComponent();
                    if(null==selected) return;
                    if(SchemaJTree.this.isSource){
                        Linkable[] targets = selected.getHardLinkTargets( );
                        
                        for(int j=0;j<targets.length;j++){
                            getLinkPanel().removeLink(selected, targets[j]);
                            continue;
                        }
                        repaint();
                        /*java.util.List contexts = selected.getTargetContexts();
                        if(contexts.size( )>0){
                            Linkable l = (Linkable)contexts.get(0);
                            MapToolController.getInstance( ).execute(
                            new UnlinkContext(selected, l)
                            );
                            repaint( );
                            continue;
                        }
                        java.util.List params = selected.getExtensionParameterTargets( );
                        if(params.size( )>0){
                            MapToolController.getInstance( ).execute(
                            new UnlinkSourceFromParameter(selected, (Parameter)params.get(0))
                            );
                            repaint( );
                            continue;
                        }*/
                        
                    }else{
                        Linkable[] sources = selected.getHardLinkSources( );
                        for(int j=0;j<sources.length;j++){
                            getLinkPanel().removeLink(sources[j], selected);
                            //continue;
                        }
                        repaint( );
/*                        java.util.List contexts = selected.getSourceContexts();
                        if(contexts.size( )>0){
                            Linkable l = (Linkable)contexts.get(0);
                            MapToolController.getInstance( ).execute(
                            new UnlinkContext(l, selected)
                            );
                        }
                        java.util.List functions = selected.getFunctionCallSources( );
                        if(functions.size( )>0){
                            MapToolController.getInstance( ).execute(
                            new UnlinkExtensionFunctionFromTarget((ExtensionFunction)functions.get(0), selected)
                            );
                            repaint( );
                            continue;
                        }
*/                    }
                }
            }catch(Exception ex){
//                MapToolController.getInstance( ).postException(ex);
            }
        }
    };
    
    AbstractAction setAsKeyAction = new AbstractAction("Set As Key"){
        public void actionPerformed(java.awt.event.ActionEvent e) {
            try{
                TreePath[] paths = SchemaJTree.this.getSelectionPaths( );
                for(int i=0;i<paths.length;i++){
                    Linkable selected = (Linkable) paths[i].getLastPathComponent();
                    if(null==selected) return;
                    selected.setKeyType(Record.KEY);
                    repaint();
                }
            }catch(Exception ex){
//                MapToolController.getInstance( ).postException(ex);
            }
        }
    };
    
    AbstractAction createVariableAction = new AbstractAction("Create Process Variable"){
        public void actionPerformed(java.awt.event.ActionEvent e) {
            try{
            	Type createVariableOption = new Type(
            		"Create Process Variable",
            		new FieldDescriptor[]{
                			new FieldDescriptor("Prefix"),
                			new FieldDescriptor("Suffix"),
                			new FieldDescriptor("Volatile", Boolean.class, null, null),
            		}
            	){

					@Override
					public void save(Instance rec) throws Exception {
						
						String prefix = (String) rec.getFieldValue("Prefix");
						if(prefix==null) prefix = "";

						String suffix = (String) rec.getFieldValue("Suffix");
						if(suffix==null) suffix = "";
						
						boolean isVolatile = UEngineUtil.boolValue((Boolean)rec.getFieldValue("Volatile"));
	
	                    ProcessDefinition def = (ProcessDefinition) ProcessDesigner.getInstance().getProcessDefinitionDesigner().getActivity();

	                    ProcessVariable[] existingVariables = def.getProcessVariables();
	                    
		                TreePath[] paths = SchemaJTree.this.getSelectionPaths( );
		                for(int i=0;i<paths.length;i++){
		                    Linkable selected = (Linkable) paths[i].getLastPathComponent();
		                    
		                    ProcessVariable generatedPV = new ProcessVariable();
		                    generatedPV.setName(prefix + selected.getName() + suffix);
		                    generatedPV.setVolatile(isVolatile);

		                    if(selected instanceof Record){
		                    	generatedPV.setType(((Record)selected).getClassType());
		                    }else{
		                    	generatedPV.setType(String.class);
		                    }
		                    
		                    existingVariables = (ProcessVariable[]) UEngineUtil.addArrayElement(existingVariables, generatedPV);
		                    
		                }
		                
		                def.setProcessVariables(existingVariables);
					}

					@Override
					public void update(Instance rec) throws Exception {
						save(rec);
					}
            		
            	};
            	
            	InputForm form = new InputForm(createVariableOption);
            	form.postInputDialog(ProcessDesigner.getInstance(), "Generate", "Generate");
            	
            	
            	
            }catch(Exception ex){
//                MapToolController.getInstance( ).postException(ex);
            }
        }
    };


    AbstractAction createFormAction = new AbstractAction("Create Form"){
        public void actionPerformed(java.awt.event.ActionEvent e) {
            try{
            	Type createVariableOption = new Type(
            		"Create Form",
            		new FieldDescriptor[]{
                			new FieldDescriptor("Form Name", String.class, null, new Validator[]{
                				new NotNullValid()
                			}),
                			new FieldDescriptor("Alias"),
                			new FieldDescriptor("Form Variable Name"),
                			new FieldDescriptor("Fields", FormFieldDescriptor[].class, null, null),
            		}
            	){

					@Override
					public void save(final Instance rec) throws Exception {
						
						FormFieldDescriptor[] fields = (FormFieldDescriptor[])rec.getFieldValue("Fields");
						
						final StringBuffer sb = new StringBuffer();
						
						Map parameterMap = new HashMap();
						
	                    for(int i=0; i<fields.length; i++){
	                    	sb.append(fields[i].getDisplayName()).append(" : ");
	                    	
	                    	WebInputter inputter = (WebInputter) fields[i].getInputter();
	                    	sb.append(inputter.getInputterHTML("", fields[i], null, null));
	                    	sb.append("\n<br>");
	                    }
	                    
	                    System.out.println(sb);
	                    	                    
	                    SwingUtilities.invokeLater(new Runnable(){

							public void run() {
								new ProgressDialog("Create and deploy form...", ProcessDesigner.getInstance()){

									public void run() throws Exception {
										
										String name = (String) rec.getFieldValue("Form Name");
										String alias = (String) rec.getFieldValue("Alias");
										String formVarName = (String) rec.getFieldValue("Form Variable Name");

										alias = alias!=null ? alias : name;
										formVarName = formVarName!=null ? formVarName : alias;
										
										String defAlias = ProcessDesigner.getInstance().getClientProxy().createFormDefinition(name, alias, sb.toString());
										
										
										if(defAlias.indexOf("SUCCESS:") == -1)
											throw new UEngineException("Failed to deploy form", defAlias);
										else{
											int pos = defAlias.indexOf("SUCCESS:");
											defAlias = defAlias.substring(pos+"SUCCESS:".length());
										}
										
					                    ProcessDefinition def = (ProcessDefinition) ProcessDesigner.getInstance().getProcessDefinitionDesigner().getActivity();
					                    ProcessVariable[] existingVariables = def.getProcessVariables();
					                    
					                    ProcessVariable generatedPV = new ProcessVariable();
					                    generatedPV.setName(formVarName);
					                    generatedPV.setType(HtmlFormContext.class);
					                    
					                    HtmlFormContext htmlFormCtx = new HtmlFormContext();
					                    htmlFormCtx.setFormDefId(defAlias);
					                    
					                    generatedPV.setDefaultValue(htmlFormCtx);
					                    
					                    existingVariables = (ProcessVariable[]) UEngineUtil.addArrayElement(existingVariables, generatedPV);
					                    
					                    def.setProcessVariables(existingVariables);
										
									}
									
								}.show();
							}
							
						});
	                    
					}

					@Override
					public void update(Instance rec) throws Exception {
						save(rec);
					}
            		
            	};
            	
                TreePath[] paths = SchemaJTree.this.getSelectionPaths( );

                FormFieldDescriptor[] fds = new FormFieldDescriptor[paths.length];
            	                                       	
            	for(int i=0;i<paths.length;i++){
            		FormFieldDescriptor fd = new FormFieldDescriptor();
            		
            		Linkable selected = (Linkable) paths[i].getLastPathComponent();
                    
                    Class type = null;
                    if(selected instanceof Record){
                    	type = (((Record)selected).getClassType());
                    }
                    
                    if(type==null) type=String.class;

                    fd.setType(type);
                    fd.setName(selected.getName());
                    fds[i] = fd;
                }
            	
            	Instance inst = createVariableOption.createInstance();
            	inst.setFieldValue("Fields", fds);
            	
            	InputForm form = new InputForm(createVariableOption);
            	form.setInstance(inst);
            	
            	form.postInputDialog(ProcessDesigner.getInstance(), "Generate", "Generate");
            	
            }catch(Exception ex){
            	ex.printStackTrace();
            }
        }
    };
    
    AbstractAction unSetKeyAction = new AbstractAction("Unset Key"){
        public void actionPerformed(java.awt.event.ActionEvent e) {
            try{
                TreePath[] paths = SchemaJTree.this.getSelectionPaths( );
                for(int i=0;i<paths.length;i++){
                    Linkable selected = (Linkable) paths[i].getLastPathComponent();
                    if(null==selected) return;
                    selected.setKeyType(Record.UNSET_KEY);
                    repaint();
                }
            }catch(Exception ex){
//                MapToolController.getInstance( ).postException(ex);
            }
        }
    };
    
    public void expandAll( ){
        expandAll(this, true);
    }
    
    // If expand is true, expands all nodes in the tree.
    // Otherwise, collapses all nodes in the tree.
    public void expandAll(JTree tree, boolean expand) {
        
        // Traverse tree from root
        expandAll(tree, tree.getPathForRow(0), expand);
    }
    
    private void expandAll(JTree tree, TreePath parent, boolean expand) {
        // if(expanded) return; //comment out to enable expand/collapse.
        // Traverse children
        TreeNode node = (TreeNode)parent.getLastPathComponent();
        
        if (node.getChildCount() >= 0) {
            Enumeration e = node.children( );
            if(e!=null){
                while (e.hasMoreElements() ) {
                    TreeNode n = (TreeNode)e.nextElement();
                    TreePath path = parent.pathByAddingChild(n);
                    expandAll(tree, path, expand);
                }
            }
        }
        
        // Expansion or collapse must be done bottom-up
        if (expand) {
            tree.expandPath(parent);
        } else {
            tree.collapsePath(parent);
        }
    }
    
    public void dragDropEnd(java.awt.dnd.DragSourceDropEvent dragSourceDropEvent) {
    }
    
    public void dragEnter(java.awt.dnd.DropTargetDragEvent dropTargetDragEvent) {
    }
    
    public void dragEnter(java.awt.dnd.DragSourceDragEvent dragSourceDragEvent) {
    }
    
    public void dragExit(java.awt.dnd.DropTargetEvent dropTargetEvent) {
    }
    
    public void dragExit(java.awt.dnd.DragSourceEvent dragSourceEvent) {
    }
    
    DragSource dragSource;
    public void dragGestureRecognized(java.awt.dnd.DragGestureEvent dragGestureEvent) {
        
        if(isSource){
            Point loc = dragGestureEvent.getDragOrigin( );
            TreePath path = getPathForLocation(loc.x, loc.y);
            int row = getRowForLocation(loc.x,loc.y);
            if(row == 0) return;
            if(path!=null){
                Transferable transferable =(Transferable) path.getLastPathComponent( );
                //if(!isSelectableNode((Linkable)transferable)) return;
                //System.out.println("Recognizing Drag Gesture");
                int action = dragGestureEvent.getDragAction();
                if (action == DnDConstants.ACTION_LINK)
                if(transferable!=null){
                    dragSource.startDrag(dragGestureEvent, theCursor, transferable, this);
                }
                
            }
        }
    }
    
    public void dragOver(java.awt.dnd.DropTargetDragEvent dropTargetDragEvent) {
        if(false==isSource){
            Point loc = dropTargetDragEvent.getLocation( );
            TreePath path = getPathForLocation(loc.x, loc.y);
            if(path == null) return;
            Linkable l = (Linkable) path.getLastPathComponent( );
            if(isSelectableNode(l)){
                this.setSelectionPath(path);
                revalidate( );
                repaint( );
            }else{
                theCursor = DragSource.DefaultLinkNoDrop;
                this.setSelectionPath(null);
            }
        }
    }
    
    public void dragOver(java.awt.dnd.DragSourceDragEvent dragSourceDragEvent) {
    }
    
    public void drop(java.awt.dnd.DropTargetDropEvent dropTargetDropEvent) {
        //get transferable.
        if(false==isSource){
            Transferable t = dropTargetDropEvent.getTransferable( );
            if(t!=null){
                try{
                    DataFlavor[] flavors = t.getTransferDataFlavors( );
                    if(flavors[0].equals(Function.FUNCTION_FLAVOR)){
                    	System.out.println("FUNCTION_FLAVOR");
                        Function func = (Function) t.getTransferData(Function.FUNCTION_FLAVOR);
                        ExtensionElement ext = func.getOwnerExtension( );
                        Component ui = ext.getUI( );
                        Point loc = dropTargetDropEvent.getLocation( );
                        TreePath targetPath = getPathForLocation(loc.x, loc.y);
                        if(targetPath!=null){
                            Linkable target = (Linkable)targetPath.getLastPathComponent();
                            if(isSelectableNode(target)){
                            	
                            }
                        }
                    }else if(flavors[0].equals(Linkable.LINKABLE_FLAVOR)){
                    	System.out.println("LINKABLE_FLAVOR");
                        Linkable src = (Linkable) t.getTransferData( Linkable.LINKABLE_FLAVOR );
                        Point loc = dropTargetDropEvent.getLocation( );
                        TreePath targetPath = getPathForLocation(loc.x, loc.y);
                        
                        if(targetPath!=null){
                            Linkable target = (Linkable)targetPath.getLastPathComponent();
                            //if both have complex content
                           // if(src.getStructure( ).equals(Linkable.COMPLEX_TYPE)&&
                           // target.getStructure( ).equals(Linkable.COMPLEX_TYPE)){
                                //
                           // }else{
                            	//linkPanel.getModel().addLink(src, target);
                                linkPanel.addLink(src, target);
                                
                           // }
                        }
                    }else if(flavors[0].equals(ExtensionFunction.EXTENSION_FUNCTION_FLAVOR)){
                    	System.out.println("EXTENSION_FUNCTION_FLAVOR");
                        ExtensionFunction function = (ExtensionFunction) t.getTransferData(flavors[0]);
                        Point loc = dropTargetDropEvent.getLocation( );
                        TreePath targetPath = getPathForLocation(loc.x, loc.y);
                        if(targetPath!=null){
                            Linkable target = (Linkable)targetPath.getLastPathComponent();
                        }
                    }
                }catch(Exception ufe){
                }

            	
            	
            }
        }
    }
    
    public void dropActionChanged(java.awt.dnd.DropTargetDragEvent dropTargetDragEvent) {
    }
    
    public void dropActionChanged(java.awt.dnd.DragSourceDragEvent dragSourceDragEvent) {
    }
    
    /*
     
    TreePath selectedTreePath;
    TreeNode selectedNode;
    public void valueChanged(javax.swing.event.TreeSelectionEvent treeSelectionEvent) {
        selectedTreePath = treeSelectionEvent.getNewLeadSelectionPath();
        if (selectedTreePath == null) {
            selectedNode = null;
            return;
        }
        selectedNode =
        (TreeNode)selectedTreePath.getLastPathComponent();
     
    }
     */
    
    
    public void updateUI( ){ }
    
    //if the node has any children, its not selectable for now. this will have to be removed
    //to allow node sets to link to macros.
    public boolean isSelectableNode(Linkable node){
        if(node.getStructure( )==null) return false;
        if(node.getStructure( ).equals(Linkable.COMPLEX_TYPE)
        ||node.getStructure( ).equals(Linkable.ROOT)
        ||node.getStructure( ).equals(Linkable.TEMPLATE)) return false;
        return true;
    }
    
    class SchemaCellRenderer extends JPanel implements TreeCellRenderer{
        
        public SchemaCellRenderer( ) { }
        boolean lr;
        Color selectedColor = new Color(204,204,204);
        Color illegalSelectionColor = Color.white;
        Color foreground = Color.black;
        Color background = Color.white;
        JLabel label = new JLabel();
        String labelText = "";
        Font _font;
        JPanel inner = new JPanel( );
        boolean selected;
        boolean linked = false;
        int linkType = 0;
        LinkableTreeNode node;
        
        public SchemaCellRenderer(JTree tree, LinkableTreeNode l, boolean selected, boolean leaf, int row){
        	this.node = l;
            this.selected=selected;
            this.lr = ((SchemaJTree)tree).isSource;
            this.linkType = l.getLinkType();
            this.setBackground(tree.getBackground( ));
            StringBuffer annotation = new StringBuffer( ).append("<html>");
            
            linked = l.isLinked();
            if(selected){
                _font=new Font("DialogInput", Font.BOLD, 11);
                background=Color.lightGray;
            }else{
                _font=new Font("Dialog",Font.PLAIN,10);
            }
            this.setFont(_font);
            label.setFont(_font);
            
            JLabel icon = new JLabel();
            icon.setIcon(getIcon(l.getStructureGeneric()));
            
            boolean hasErrors=false;
            labelText=l.getName( );
            boolean legalSelection = isSelectableNode(l);
            l.setComponent(this);
            if(leaf) {
                foreground = Color.blue;
                background = Color.white;
                if(l.getNodeType()!=null && l.getNodeType().equals(Linkable.ATTR_NODE)){
                    labelText = "@"+labelText;
                    foreground = Color.blue;
                }
            } else {
                if(row==0){
                    String loc = "getSchemaLocation";
                    //icon.setIcon(getIcon(Linkable.ROOT));
                    annotation.append("<i>Location:</i> <b>").append(loc).append("</b><br>");
                    if(loc.length()>30){
                        if(loc.lastIndexOf(File.separator)!=-1){
                            loc = "..."+loc.substring(loc.lastIndexOf(File.separator),loc.length());
                        }else{
                            loc = "..."+loc.substring(loc.length()-29,loc.length());
                        }
                    }
                    labelText = loc;
                }
                if(legalSelection) foreground = Color.blue;
                else foreground = Color.gray;
            }
            
  /*          if(l instanceof Record){
            	Record rec = (Record)l;
            	
            	rec.addLinkListener(new MapModelListener(){

					public void extensionFunctionAdded(MapChangedEvent event) {
						
					}

					public void extensionFunctionRemoved(MapChangedEvent event) {
						
					}

					public void linkAdded(MapChangedEvent event) {
				        
						Record rec = (Record) event.getSource();
						
						
						
						StringBuffer annotation = new StringBuffer();
					
		                annotation
		                	.append( "<i>Name:</i> <b>").append(rec.getName( ))
			                .append("</b><br><i>Sources:</i> <br><table width=250><tr><td>")
				            ;
			                
			                for(int i=0; i<rec.getHardLinkSources().length; i++){
				                annotation.append(rec.getHardLinkSources()[i].getName() + ", ");
			                }
			                
			            annotation.append("</b><br><i>Targets:</i> <br><table width=250><tr><td>")
				            ;
			                
			                for(int i=0; i<rec.getHardLinkTargets().length; i++){
				                annotation.append(rec.getHardLinkTargets()[i].getName() + ", ");
			                }
		                
		                annotation.append("</td></tr></table>");
		                
		                SchemaCellRenderer.this.setToolTipText(annotation.toString( ));
					}

					public void linkRemoved(MapChangedEvent event) {
						linkAdded(event);
					}

					public void contextMappingAdded(MapChangedEvent event) {
						
					}

					public void contextMappingRemoved(MapChangedEvent event) {
						
					}
            		
            	});
            }
            */
            if(row!=0){
                annotation
                	.append( "<i>Name:</i> <b>").append(l.getName( ))
	                .append("</b><br><i>Annotation:</i> <br><table width=250><tr><td>").append(l.getDocumentation( ))
	                .append("</td></tr></table>")
                ;
            }
            this.setToolTipText(annotation.toString( ));

            inner.setBackground(background);
            
            if(hasErrors){
                inner.setBackground(Color.red);
            }
            label.setText(labelText);
            label.setForeground(foreground);
            label.setBackground(background);
//            label.setToolTipText("xxxxxxxxx");
//            ToolTipManager.sharedInstance().registerComponent(label);
//                     
            // this.setBorder(BorderFactory.createLineBorder(Color.lightGray,1));
            //label.setBorder(BorderFactory.createLineBorder(Color.blue,1));
            inner.setBorder(BorderFactory.createLineBorder((selected?Color.black:Color.lightGray),1));
            if(lr){
                inner.add(icon);
                inner.add(label);
                inner.setLayout(new FlowLayout(FlowLayout.LEFT,2, 0));
                this.setLayout(new FlowLayout(FlowLayout.LEFT,1,1));
            }else{
                inner.add(label);
                inner.add(icon);
                inner.setLayout(new FlowLayout(FlowLayout.RIGHT,2,0));
                this.setLayout(new FlowLayout(FlowLayout.RIGHT,1,1));
            }
            
            this.add(inner);
            
            //inner.setSize(label.getSize( ));
            //this.setSize(label.getSize( ));
            ToolTipManager.sharedInstance().registerComponent(this);
            
        }
        
        
        @Override
		public String getToolTipText() {
			Record rec = (Record) node;
			
			StringBuffer annotation = new StringBuffer();
			String sep = "";
            
            for(int i=0; i<rec.getHardLinkSources().length; i++){
                annotation.append(rec.getHardLinkSources()[i].getName() + sep);
                sep = ", ";
            }
            
            for(int i=0; i<rec.getHardLinkTargets().length; i++){
            	
            	if(rec.getHardLinkTargets()!=null &&rec.getHardLinkTargets().length >=i && rec.getHardLinkTargets()[i] !=null ){
	                annotation.append(rec.getHardLinkTargets()[i].getName() + sep);
	                sep = ", ";
            	}
            }
            
            if(", ".equals(sep))
 	            annotation.insert(0, "linked to :");
            else
            	annotation.append("not linked");
            
			return annotation.toString();
		}


		public Dimension getPreferredSize( ){
            Dimension d = inner.getPreferredSize( );
            
            //return new Dimension(d.width+50, d.height+2);
            return new Dimension(200, d.height+2);
        }
        
        
        public void paintComponent(Graphics graphics){
            super.paintComponent(graphics );
            if(graphics!=null&&linked){
                Graphics2D g = (Graphics2D)graphics.create( );//new context
                Rectangle bounds = this.getBounds( );
                int _y=bounds.height/2;
                g.setColor(GraphicsConstants.getLinkColor(linkType));
                g.setStroke((selected? GraphicsConstants.SELECTED_STROKE:GraphicsConstants.NORMAL_STROKE));
                g.drawLine(0,_y, bounds.width * 3, _y);
                g.dispose( );
            }
            
            if(Record.KEY == node.getKeyType()){
                Graphics2D g = (Graphics2D)graphics.create( );//new context
                Rectangle bounds = this.getBounds();
                Dimension dim = inner.getPreferredSize();
                ImageIcon keyIcon = DesignerLabel.getImageIcon(DesignerLabel.KEY);
                int keyIconX = 0;
                if(lr){	//if Source node
                	keyIconX = dim.width;
                }else{  // Target node
                	keyIconX = bounds.width - (FlowLayout.RIGHT + dim.width + keyIcon.getIconWidth()); 
                }
                keyIcon.paintIcon(this, g, keyIconX, 0);
            }
        }
        
        public Component getTreeCellRendererComponent(JTree tree,
	        Object value,
	        boolean selected,
	        boolean expanded,
	        boolean leaf,
	        int row,
	        boolean hasFocus){
            return new SchemaCellRenderer(tree, (LinkableTreeNode)value, selected, leaf, row);
        }
    }    
    
    private TreePath buildPath(String path){
        //build a tree path by parsing the path.
        java.util.List pathList = new ArrayList();
        Linkable root = (Linkable)getModel().getRoot( );
        pathList.add(root);
        path = path.substring(1, path.length( )-1);
        StringTokenizer st = new StringTokenizer(path, ",");
        while(st.hasMoreTokens( )){
            String next = st.nextToken( ).trim( );
            if(next==null||next.equals("")||next.equals("/")) continue;
            root = root.getChild(next);
            if(root==null){
                System.out.println("Could not find child: ["+next+"] in path: "+path);
                return null;
            }
            pathList.add(root);
        }
        TreePath treePath = new TreePath((Linkable[])pathList.toArray(new Linkable[]{}));
        return treePath;
    }

	public LinkPanel getLinkPanel() {
		return linkPanel;
	}

	public void setLinkPanel(LinkPanel linkPanel) {
		this.linkPanel = linkPanel;
	}
	
    HashMap icons = null;
    ImageIcon errorIcon = new ImageIcon(ProcessDesigner.class.getClassLoader().getResource("com/webdeninteractive/images/error-glyph.gif"));
   private ImageIcon getIcon(String type){
        if(icons == null){
            icons = new HashMap( );
            icons.put(Linkable.ROOT, new ImageIcon(ProcessDesigner.class.getClassLoader().getResource("com/webdeninteractive/images/rootNode.gif")));
            icons.put(Linkable.GROUP, new ImageIcon(ProcessDesigner.class.getClassLoader().getResource("com/webdeninteractive/images/group.gif")));
            icons.put(Linkable.OPTIONAL_GROUP, new ImageIcon(ProcessDesigner.class.getClassLoader().getResource("com/webdeninteractive/images/optgrp.gif")));
            icons.put(Linkable.GROUP_REFERENCE, new ImageIcon(ProcessDesigner.class.getClassLoader().getResource("com/webdeninteractive/images/group_re.gif")));
            icons.put(Linkable.OPTIONAL_REPEATING_GROUP, new ImageIcon(ProcessDesigner.class.getClassLoader().getResource("com/webdeninteractive/images/optrgrp.gif")));
            icons.put(Linkable.REPEATING_GROUP, new ImageIcon(ProcessDesigner.class.getClassLoader().getResource("com/webdeninteractive/images/rptgrp.gif")));
            icons.put(Linkable.REPEATING_GROUP_REFERENCE, new ImageIcon(ProcessDesigner.class.getClassLoader().getResource("com/webdeninteractive/images/group_re.gif")));
            icons.put(Linkable.OPTIONAL_REPEATING_GROUP_REFERENCE, new ImageIcon(ProcessDesigner.class.getClassLoader().getResource("com/webdeninteractive/images/group_re.gif")));
            icons.put(Linkable.FIELD, new ImageIcon(ProcessDesigner.class.getClassLoader().getResource("com/webdeninteractive/images/field.gif")));
            icons.put(Linkable.OPTIONAL_FIELD, new ImageIcon(ProcessDesigner.class.getClassLoader().getResource("com/webdeninteractive/images/optfield.gif")));
            icons.put(Linkable.REPEATING_FIELD, new ImageIcon(ProcessDesigner.class.getClassLoader().getResource("com/webdeninteractive/images/rptfld.gif")));
            icons.put(Linkable.OPTIONAL_REPEATING_FIELD, new ImageIcon(ProcessDesigner.class.getClassLoader().getResource("com/webdeninteractive/images/optrfld.gif")));
        }
        
        ImageIcon icon = (ImageIcon) icons.get(type);
        if(icon==null) return errorIcon;
        return icon;
    }

	public JPopupMenu getMenu() {
		return menu;
	}

}


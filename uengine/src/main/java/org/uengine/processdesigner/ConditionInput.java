package org.uengine.processdesigner;
    
import org.uengine.kernel.And;
import org.uengine.kernel.Condition;
import org.uengine.kernel.Evaluate;
import org.uengine.kernel.Or;

import org.uengine.processdesigner.*;

import org.metaworks.inputter.*;

import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;

/**
 * @author Jinyoung Jang
 */

public class ConditionInput extends AbstractComponentInputter{

    /**
     * 
     * @uml.property name="keyInputField"
     * @uml.associationEnd 
     * @uml.property name="keyInputField" multiplicity="(0 1)"
     */
    //ProcessVariableInput keyInput;

    JTextField keyInputField;

    /**
     * 
     * @uml.property name="valInputField"
     * @uml.associationEnd 
     * @uml.property name="valInputField" multiplicity="(0 1)"
     */
    //ProcessVariableInput keyInput;

    JTextField valInputField;

    /**
     * 
     * @uml.property name="conditionBox"
     * @uml.associationEnd 
     * @uml.property name="conditionBox" multiplicity="(0 1)"
     */
    JComboBox conditionBox;

    /**
     * 
     * @uml.property name="radioAnd"
     * @uml.associationEnd 
     * @uml.property name="radioAnd" multiplicity="(0 1)"
     */
    JRadioButton radioAnd;

    /**
     * 
     * @uml.property name="radioOr"
     * @uml.associationEnd 
     * @uml.property name="radioOr" multiplicity="(0 1)"
     */
    JRadioButton radioOr;

    /**
     * 
     * @uml.property name="conditionTree"
     * @uml.associationEnd 
     * @uml.property name="conditionTree" multiplicity="(0 -1)" elementType="javax.swing.tree.DefaultMutableTreeNode"
     */
    JTree conditionTree;

    /**
     * 
     * @uml.property name="treeData"
     * @uml.associationEnd 
     * @uml.property name="treeData" multiplicity="(0 1)"
     */
    //TestTreeModel treeData;
    DefaultTreeModel treeData;

    /**
     * 
     * @uml.property name="rootNode"
     * @uml.associationEnd 
     * @uml.property name="rootNode" multiplicity="(0 1)"
     */
    //DefaultMutableNode rootNode;
    DefaultMutableTreeNode rootNode;

    /**
     * 
     * @uml.property name="popupMenu"
     * @uml.associationEnd 
     * @uml.property name="popupMenu" multiplicity="(0 1)"
     */
    JPopupMenu popupMenu;

    MouseListener popupListener;

    /**
     * 
     * @uml.property name="addItem"
     * @uml.associationEnd 
     * @uml.property name="addItem" multiplicity="(0 1)"
     */
    JMenu addItem;

    /**
     * 
     * @uml.property name="orMenu"
     * @uml.associationEnd 
     * @uml.property name="orMenu" multiplicity="(0 1)"
     */
    JMenuItem orMenu;

    /**
     * 
     * @uml.property name="andMenu"
     * @uml.associationEnd 
     * @uml.property name="andMenu" multiplicity="(0 1)"
     */
    JMenuItem andMenu;

    /**
     * 
     * @uml.property name="conditionMenu"
     * @uml.associationEnd 
     * @uml.property name="conditionMenu" multiplicity="(0 1)"
     */
    JMenuItem conditionMenu;

    /**
     * 
     * @uml.property name="deleteItem"
     * @uml.associationEnd 
     * @uml.property name="deleteItem" multiplicity="(0 1)"
     */
    JMenuItem deleteItem;

    
    public ConditionInput( ){
        super();

    }
        
    
    public Component getNewComponent(){

        //Create the popup menu.
        popupMenu = new JPopupMenu();
        addItem = new JMenu("add");
        
        orMenu = new JMenuItem("Or");
        orMenu.addActionListener( new ActionListener(){
            public void actionPerformed( ActionEvent e){
                addCondition( "Or");
            }
        });
        andMenu = new JMenuItem("And");
        andMenu.addActionListener( new ActionListener(){
            public void actionPerformed( ActionEvent e){
                addCondition( "And");
            }
        });
        conditionMenu = new JMenuItem("Condition");
        conditionMenu.addActionListener( new ActionListener(){
            public void actionPerformed( ActionEvent e){
                //addCondition( "Condition");
                showCondiInputForm();
            }
        });
        conditionMenu.setVisible( false);
        
        addItem.add( orMenu);
        addItem.add( andMenu);
        addItem.add( conditionMenu);
        
        deleteItem = new JMenuItem("delete");
        deleteItem.addActionListener( new ActionListener(){
            public void actionPerformed( ActionEvent e){
                deleteCondition();
            }
        });
        deleteItem.setVisible( false);

        popupMenu.add(addItem);
        popupMenu.add(deleteItem);
        

        
        
                
        // 2번째줄 UI
/*      JPanel commandPanel = new JPanel();
        ButtonGroup group = new ButtonGroup();
        radioAnd = new JRadioButton("And");
        radioOr = new JRadioButton( "Or");
        
        JButton addBtn = new JButton("Insert");
        JButton deleteBtn = new JButton( "Delete");
        
        group.add( radioAnd);
        group.add( radioOr);
        
        addBtn.addActionListener( new ActionListener(){
            public void actionPerformed( ActionEvent e){
//              addCondition();
            }
        });
        deleteBtn.addActionListener( new ActionListener(){
            public void actionPerformed( ActionEvent e){
                deleteCondition();
            }
        });
        
        //commandPanel
        commandPanel.setLayout( new GridLayout( 0, 4));
        commandPanel.add( radioAnd);
        commandPanel.add( radioOr);
        commandPanel.add( addBtn);
        commandPanel.add( deleteBtn);
*/      

        popupListener = new PopupListener();        
        
        //3번째 Tree 및 모음.
        
        JPanel newInputPanel = new JPanel();
        //treeData = new TestTreeModel();
        //rootNode = new Tree( null, "root");
        rootNode = new DefaultMutableTreeNode( "root");
        //rootNode.add("aa");
        //rootNode.add("bb");
        treeData = new DefaultTreeModel( rootNode);
        
        conditionTree = new JTree( treeData);
        conditionTree.setSize( 200, 40);
        conditionTree.setRootVisible( true);
        conditionTree.addMouseListener( popupListener);
        conditionTree.setPreferredSize( new Dimension(200, 40));
        
        conditionTree.addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent e) {
        
                DefaultMutableTreeNode node = 
                    (DefaultMutableTreeNode)conditionTree.getLastSelectedPathComponent();
                
                if( node.getUserObject() != null){
                    if( node.getUserObject() instanceof And){
                        addItem.setVisible( true);
                        conditionMenu.setVisible( true);
                        deleteItem.setVisible( true);
                    }
                        
                    if( node.getUserObject() instanceof Evaluate){
                        addItem.setVisible( false);
                        deleteItem.setVisible( true);
                    }
                    
                    if( node == rootNode){
                        conditionMenu.setVisible( false);
                        deleteItem.setVisible( false);
                    }
                }
            }
        });
        
//      JPanel temp = new JPanel();
//      temp.setLayout( new GridLayout( 2,0));
//      temp.add( inputPanel);
//      temp.add( commandPanel);
        
        newInputPanel.setLayout( new BorderLayout());
//      newInputPanel.add( "North", inputPanel);
        newInputPanel.add("Center", new JScrollPane( conditionTree));
        newInputPanel.setSize( 200,40);
    
        return newInputPanel;
    }
    
    protected void showCondiInputForm(){
                
        keyInputField = new JTextField(7);      
                            //should be revised
/*      keyInput = new ProcessVariableInput(ProcessDesigner.getInstance());
        Component keyInputField = keyInput.getNewComponent();*/
        
        valInputField = new JTextField(7);
        conditionBox  = new JComboBox( new String[]{ "==","<",">","<=",">=","!="});
        
        JPanel inputPanel = new JPanel();
        
        JPanel keyPanel = new JPanel();
        JPanel valPanel = new JPanel();
        
        JLabel keyL = new JLabel(" Key : ");
        JLabel valL = new JLabel(" Value : ");
        
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        
        keyPanel.setLayout( new BorderLayout());
        keyPanel.add( BorderLayout.WEST, keyL);
        keyPanel.add( BorderLayout.CENTER, keyInputField);
        //keyPanel.setSize( 200, keyPanel.getHeight());
        
        valPanel.setLayout( new BorderLayout());
        valPanel.add( BorderLayout.WEST, valL);
        valPanel.add( BorderLayout.CENTER, valInputField);
        //valPanel.setSize( 200, valPanel.getHeight());
        
        //inputPanel.setLayout( new GridLayout(1,3));
        inputPanel.setLayout( gridbag);
        //inputPanel.add( keyPanel);
        //inputPanel.add( conditionBox);
        //inputPanel.add( valPanel);
        c.gridx = 0;
        c.gridy = 0;
       // c.weightx = 0.5;
        gridbag.setConstraints( keyPanel, c);
        inputPanel.add(keyPanel);
        
        
        c.gridx = 1;
        c.gridy = 0;
        gridbag.setConstraints( conditionBox, c);
        conditionBox.setSize( 70, conditionBox.getHeight());
        inputPanel.add(conditionBox);
        
        
        c.gridx = 2;
        c.gridy = 0;
        gridbag.setConstraints( valPanel, c);
        inputPanel.add(valPanel);
        
                
        final JDialog dlg = new JDialog(DesignerUtil.getMyDialog(conditionTree), "Insert Condition", true);
        Container cont = dlg.getContentPane();
        
        cont.setLayout( new BorderLayout());
        cont.add( "Center", inputPanel);
        
        JButton addBtn = new JButton("Add");
        addBtn.addActionListener( new ActionListener(){
            public void actionPerformed( ActionEvent e){
                addCondition( "Condition");
                dlg.setVisible( false);
            }
        });
        
        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.addActionListener( new ActionListener(){
            public void actionPerformed( ActionEvent e){
                dlg.setVisible( false);
            }
        });
        
        Panel btnPanel = new Panel();
        btnPanel.setLayout(new GridLayout(1,2));
        btnPanel.add( addBtn);
        btnPanel.add( cancelBtn);
        
        cont.add( "South", btnPanel);
        
        dlg.setSize( 300,100);
        Point p = DesignerUtil.getAbsoluteLocation( conditionTree, dlg.getClass());
        dlg.setLocation( p.x, p.y);
        dlg.setVisible( true);
    }
    
    protected void addCondition( String param){
//System.out.println("Here~~ 1");
        Object obj = conditionTree.getLastSelectedPathComponent();
        
        
        
        if( param.equals("Condition") && getNewEvaluate() == null)
            return ;
        
        DefaultMutableTreeNode selected;
        
        // add root
        if( obj == null || obj.toString().equals("root")){

            //DefaultMutableTreeNode newNode = new DefaultMutableTreeNode( eval);
            //rootNode.add( newNode);
            selected = rootNode;
//System.out.println("Here~~ 3");
        }else{
            selected = (DefaultMutableTreeNode)obj;
        }
        
        if( selected != null){
        //  DefaultMutableTreeNode selected = (DefaultMutableTreeNode)obj;
            DefaultMutableTreeNode newNode;
            
            if( param.equals("And")){
                newNode = new DefaultMutableTreeNode( new And());

            }else if( param.equals("Or")){
                newNode = new DefaultMutableTreeNode( new Or());

            }else{
                Evaluate eval = getNewEvaluate();
                newNode = new DefaultMutableTreeNode( eval);                
            }
            
            selected.add( newNode);
            
//System.out.println("Here~~ 4");
        }
        
        conditionTree.updateUI();
    }
    
    protected void deleteCondition(){
        Object obj = conditionTree.getLastSelectedPathComponent();
//System.out.println("Here~~ 2" + obj);     
        if( obj == null)
            return ;
            
        DefaultMutableTreeNode selected = (DefaultMutableTreeNode)obj;

        DefaultMutableTreeNode parent = (DefaultMutableTreeNode)selected.getParent();
        
        parent.remove( selected);
        
        conditionTree.updateUI();
    }
    
    protected Evaluate getNewEvaluate(){
        
//changed
/*
System.out.println("ConditionInput::getNewEvaluate: keyInput.getValue()" +  keyInput.getValue());
        if( keyInput.getValue()==null || valInputField.getText().equals(""))
            return null;*/
//from
        if( keyInputField.getText().equals("") || valInputField.getText().equals(""))
            return null;

//end
            
        Evaluate newEvaluate = new Evaluate();
//      newEvaluate.setKey( ((ProcessVariable)keyInput.getValue()).getName() );
        newEvaluate.setKey( keyInputField.getText() );
        newEvaluate.setCondition( conditionBox.getSelectedItem().toString());
        
        try{
            Integer value = new Integer( valInputField.getText());
            
            newEvaluate.setValue( value);
            return newEvaluate;
        }catch( Exception e){}
        
        newEvaluate.setValue( valInputField.getText());
        
        return newEvaluate;
    }
    
    protected Condition makeCondition( DefaultMutableTreeNode node){
        
        And andCond = null;
        Condition firstCondition;
        //Tree child = (Tree)node.getChildAt(0);
        
        if( node.isLeaf())
            return (Condition)node.getUserObject();
        
        if( node.getUserObject() instanceof And){
            
            andCond = (And)node.getUserObject();
            
            for( int i=0; i< node.getChildCount(); i++){
                andCond.addCondition( makeCondition( (DefaultMutableTreeNode)node.getChildAt( i)));
            }
            
            return andCond;
        }
        
        return null;
    /*  
        if( node.getChildCount() == 0 && node.getData() instanceof Evaluate)
            return (Condition)node.getData();
        
        if( child != null){
            //child = (Tree)node.getChildAt(0);
            
            andCondition = (And)child.getData();
            andCondition.addCondition( (Evaluate)node.getData());
System.out.println("MakeCondition First: " + (Evaluate)node.getData());
            child = (Tree)child.getChildAt(0);
            andCondition.addCondition( (Evaluate)child.getData());
System.out.println("MakeCondition Second: " + (Evaluate)child.getData());
        }
        
        while( !( child.isLeaf()) ){
            child = (Tree)child.getChildAt(0);
            
            And temp = (And)child.getData();
            temp.addCondition( andCondition);
System.out.println("MakeCondition First: " + andCondition);

            child = (Tree)child.getChildAt(0);
            temp.addCondition( (Evaluate)child.getData() );
System.out.println("MakeCondition Second: " + (Evaluate)child.getData());
            
            andCondition = temp;
        }
        
        return andCondition;
        */
    }
/*  
    protected DefaultMutableTreeNode getSelectedNode() {
        TreePath   selPath = conditionTree.getSelectionPath();
    
        if(selPath != null)
            return (DefaultMutableTreeNode)selPath.getLastPathComponent();
        return null;
    }   
    */
    public Object getValue(){
        
/*      Condition[] returnVal = new Condition[ rootNode.getChildCount()];
        
        for( int i=0; i< rootNode.getChildCount(); i++){
            returnVal[i] = makeCondition( (DefaultMutableTreeNode)rootNode.getChildAt(i));
        }
        
        return returnVal;*/
        if(rootNode.getChildCount() == 0) return null;
        return makeCondition((DefaultMutableTreeNode)rootNode.getChildAt(0));
    }
    
    public void setValue( Object obj){
        /*
        if( obj != null){
            if( obj instanceof Evaluate){
                Evaluate eval = (Evaluate)obj;
                
                keyInputField.setText( eval.getKey());
                valInputField.setText( eval.getValue().toString());
            }
        }
        */
    }
    
    public static void main(String[] args) throws Exception{
    
        final JDialog dlg = new JDialog( );
        
        final ConditionInput input = new ConditionInput();
        
        dlg.setSize( 330,200);
        dlg.getContentPane().add( "Center", input.getNewComponent());
        
        JButton testBtn = new JButton("GetValue");
        testBtn.addActionListener( new ActionListener(){
            public void actionPerformed( ActionEvent e){
                
                Condition[] cons = (Condition[])input.getValue();
                
                for( int i=0; i< cons.length; i++)
                    System.out.println( "GetValue : " + cons[i]);
            }
        });
        
        JButton testBtn2 = new JButton("Test");
        testBtn2.addActionListener( new ActionListener(){
            public void actionPerformed( ActionEvent e){
                
                Class cls = Condition.class ;
                Class cls2 = Condition[].class;
                
                if(cls==cls2)
                    System.out.println( "Equals? : " );
                if(cls2 == Condition[].class)
                    System.out.println( "Equals? 22: " );
                System.out.println( "" + cls );     
                System.out.println( "" + cls2 );
            }
        });
        
        dlg.getContentPane().add( "North", testBtn2);
        dlg.getContentPane().add( "South", testBtn);
        dlg.setVisible( true);
    }
    
    class PopupListener extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            maybeShowPopup(e);
        }

        public void mouseReleased(MouseEvent e) {
            maybeShowPopup(e);
        }

        private void maybeShowPopup(MouseEvent e) {
            if (e.isPopupTrigger()) {
                popupMenu.show(e.getComponent(),
                           e.getX(), e.getY());
            }
        }
    }
}
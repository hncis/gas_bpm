package org.uengine.processdesigner;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.EventHandler;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import org.metaworks.ObjectInstance;
import org.metaworks.ObjectType;
import org.metaworks.inputter.Inputter;
import org.metaworks.inputter.ObjectInput;
import org.metaworks.inputter.SelectInput;
import org.uengine.kernel.Condition;
import org.uengine.kernel.Evaluate;
import org.uengine.kernel.GlobalContext;
import org.uengine.kernel.Not;
import org.uengine.kernel.Or;
import org.uengine.kernel.Otherwise;
import org.uengine.kernel.ProcessDefinition;
import org.uengine.kernel.ProcessVariable;
import org.uengine.kernel.Role;
import org.uengine.kernel.RoleExist;
import org.uengine.processdesigner.inputters.ProcessVariableInput;
import org.uengine.processdesigner.inputters.RoleInput;
import org.uengine.processdesigner.inputters.conditioninput.ConditionTableModel;

public class ConditionTable extends JTable implements ActionListener{//implements TableModelListener, TableColumnModelListener{
	public static final int DOUBLE_CLICK=2;
	final static String[] condition = { "==", "!=", ">=", " > ", " < ", "<=", "contains", "not contains" };
	
	int selectedRow=-1;
	int selectedColumn=-1;
	JPopupMenu pop;
	TableColumnModel columnModel; 
	DefaultTableModel dataModel;
	JTableHeader header;
	boolean multiple;
	DefaultCellEditor editor;
	JComboBox combo;
	DefaultComboBoxModel comboModel;
	ProcessVariableInput pvi;
    ProcessVariableInput pvi2;
    JComboBox conditionBox;
	JDialog dlg;
	//SelectInput conditionSelectInput;
	//final static int CONDITION_SELECTED=0;
	//final static int OTHERWISE_SELECTED=1;
	RoleInput roleinput;
	JLabel roleComment;
	
	
	JPanel pl;
	JPanel plOther;
	JPanel roleExist;
	final static String CONDITION_COMMAND = "condition";
	final static String OTHERWISE_COMMAND = "other";
	final static String ROLEEXIST_COMMAND = "roleExist";
	
	public ConditionTable() {
		this(false);
	}
	
	public ConditionTable(boolean multi) {
		
		this.multiple = multi;
		
		/*if(multi)
			conditionSelectInput = new SelectInput(getConditionNames());*/
		if(multiple){
			if(getConditionNames()!=null)
				combo = new JComboBox(getConditionNames());
			else
				combo = new JComboBox();
			editor = new DefaultCellEditor(combo);
			
			
		}
		
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.setCellSelectionEnabled(true);

		dataModel = new DefaultTableModel(10,20){
			
			public String getColumnName(int column) {
				if(column==0) 
					return "No.";
				else if(column==1){
					if(multiple)
						return GlobalContext.getLocalizedMessage("conditioneditor.case.label", "Case");
					else
						return GlobalContext.getLocalizedMessage("conditioneditor.condition.label", "Condition")+column;
				}else{
					if(multiple)
						return GlobalContext.getLocalizedMessage("conditioneditor.condition.label", "Condition") + String.valueOf(column-1);
					else
						return GlobalContext.getLocalizedMessage("conditioneditor.condition.label", "Condition")+column;
				}
				
			}

			public int getColumnCount() {
				return 20;
			}

			public boolean isCellEditable(int row, int column) {
				if(multiple && column==1)
					return column==1;
				return false;
			}

			public Object getValueAt(int row, int column) {
				if(column==0)return String.valueOf(row+1);
				return super.getValueAt(row, column);
			}
			
		};
		
		this.setModel(dataModel);
		columnModel = this.getColumnModel();
		header = this.getTableHeader();
		
		columnModel.getColumn(0).setPreferredWidth(10);
		
		
		//v�� �߰���f
		ActionListener menuitemListener = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(((JMenuItem)e.getSource()).getName().equals("addCol")){
						columnModel.getColumn(0).setCellRenderer(header.getDefaultRenderer());
					
				}else if(((JMenuItem)e.getSource()).getName().equals("delCol")){
					if(columnModel.getColumnCount()>1 || selectedColumn!=0){
						Vector row = (Vector)dataModel.getDataVector().get(selectedRow);
						//System.out.println(row);
						row.remove(selectedColumn);
						
						dataModel.fireTableStructureChanged();
						dataModel.fireTableChanged(new TableModelEvent(dataModel));
						columnModel.getColumn(0).setCellRenderer(header.getDefaultRenderer());
						
					}
				}else if(((JMenuItem)e.getSource()).getName().equals("addRow")){
					//dataModel.addRow(new Vector());
					Object[] newRow = new Object[10];
					dataModel.addRow(newRow);
					
					
				}else if(((JMenuItem)e.getSource()).getName().equals("delRow")){
					dataModel.removeRow(selectedRow);
				}else if(((JMenuItem)e.getSource()).getName().equals("editCondition")){
					
					if(selectedColumn!=0 //v�ǺбⰡ �ƴѰ��
							&& !ConditionTable.this.multiple)
						editCell();
					else if(selectedColumn!=0 //v�Ǻб⿡ ���õ� column�� 1(�б�)�� �ƴҶ�
							&& ConditionTable.this.multiple && selectedColumn!=1){
						if(isEditable(selectedRow))//otherwise�� �ƴϸ�
							editCell();
						else{//otherwise�̸�
							if(selectedColumn==getOtherwiseColumn(selectedRow)){//otherwise �ܿ��� ����Ұ�
								editCell();
							}else
								showMessageDialog("Otherwise�ܿ��� ������ �� ��4ϴ�.");
						}
					}else if(selectedColumn!=0 //v�Ǻб⿡ ���õ� �÷��� 1(�б�)�϶�
							&& ConditionTable.this.multiple && selectedColumn==1){
						
						columnModel.getColumn(selectedColumn).setCellEditor(editor);
					}
					//editCell();
				}
			}
		};
		//PopupMenu
		pop = new JPopupMenu();
		JMenuItem addColumn = new JMenuItem(GlobalContext.getLocalizedMessage("conditioneditor.addcondition.label", "Add Condition"));
		addColumn.setName("addCol");
		//
		JMenuItem delColumn = new JMenuItem(GlobalContext.getLocalizedMessage("conditioneditor.clearcondition.label", "Clear"));
		delColumn.setName("delCol");
		//
		JMenuItem addRow = new JMenuItem(GlobalContext.getLocalizedMessage("conditioneditor.addconditionrow.label", "Add Condition Row"));
		addRow.setName("addRow");
		//
		JMenuItem delRow = new JMenuItem(GlobalContext.getLocalizedMessage("conditioneditor.removeconditionrow.label", "Remove Condition Row"));
		delRow.setName("delRow");
		
		JMenuItem editCondition = new JMenuItem(GlobalContext.getLocalizedMessage("conditioneditor.editcondition.label", "Edit Condition"));
		editCondition.setName("editCondition");
		
		//Listener
		addColumn.addActionListener(menuitemListener);
		delColumn.addActionListener(menuitemListener);
		addRow.addActionListener(menuitemListener);
		delRow.addActionListener(menuitemListener);
		editCondition.addActionListener(menuitemListener);
		
		pop.add(addColumn);
		pop.add(delColumn);
		pop.add(addRow);
		pop.add(delRow);
		pop.add(editCondition);
		
		//setCellEditor ...RightButton Click�� Context PopupMenu show
		addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e) {
				
				JTable table = (JTable)e.getSource();
				selectedRow = table.rowAtPoint(e.getPoint());
				selectedColumn = table.columnAtPoint(e.getPoint());
				
				//Edit Cell
				if(selectedColumn!=0 //v�ǺбⰡ �ƴѰ��
						&& SwingUtilities.isLeftMouseButton(e) 
						&& e.getClickCount()==DOUBLE_CLICK 
						&& !ConditionTable.this.multiple)
					editCell();
				else if(selectedColumn!=0 //v�Ǻб⿡ ���õ� column�� 1(�б�)�� �ƴҶ�
						&& SwingUtilities.isLeftMouseButton(e)  
						&& e.getClickCount()==DOUBLE_CLICK
						&& ConditionTable.this.multiple && selectedColumn!=1){
					if(isEditable(selectedRow))//otherwise�� �ƴϸ�
						editCell();
					else{//otherwise�̸�
						if(selectedColumn==getOtherwiseColumn(selectedRow)){//otherwise �ܿ��� ����Ұ�
							editCell();
						}else
							showMessageDialog(GlobalContext.getLocalizedMessage("conditioneditor.you_cant_edit_except_otherwise.message", "You can't edit except Otherwise."));
					}
				}else if(selectedColumn!=0 //v�Ǻб⿡ ���õ� �÷��� 1(�б�)�϶�
						&& SwingUtilities.isLeftMouseButton(e)  
						&& ConditionTable.this.multiple && selectedColumn==1){
					
					columnModel.getColumn(selectedColumn).setCellEditor(editor);
				}
				
				//�8��ʹ�ư Ŭ���� ContextMenu popup
				if(SwingUtilities.isRightMouseButton(e))
					pop.show((JTable)e.getComponent(), e.getX(), e.getY());
			}
			
		});
		
		columnModel.getColumn(0).setCellRenderer(header.getDefaultRenderer());
		
	}
	//���� RadioButton ButtonModel
	JPanel cards;
	ButtonGroup group;
	JRadioButton byVariable;
	JRadioButton byUser;
	JRadioButton byNull;
	JComboBox roleExistOrNotExist;
	
	private JPanel CreateRadioPanel(){
		final JPanel pl = new JPanel();
        JPanel plRadio = new JPanel(new FlowLayout());
        
        byVariable = new JRadioButton(new AbstractAction(GlobalContext.getLocalizedMessage("conditioneditor.processvariable.label", "Process Variable")) {
            public void actionPerformed(ActionEvent evt) {
                CardLayout cl = (CardLayout)cards.getLayout();
                cl.show(cards, "byVariable");
                enableAll(cards);
                cards.getParent().remove(valueInput.getComponent());
                cards.invalidate();
                dlg.pack();
                
            }
        });
        byUser = new JRadioButton(new AbstractAction(GlobalContext.getLocalizedMessage("conditioneditor.directvalue.label", "Direct Value")) {
            public void actionPerformed(ActionEvent evt) {
            	CardLayout cl = (CardLayout)cards.getLayout();
                cl.show(cards, "byUser");
                enableAll(cards);
                cards.getParent().add(valueInput.getComponent(), BorderLayout.SOUTH);
                cards.invalidate();
                dlg.pack();
                
            }
        });
        
        byNull = new JRadioButton(new AbstractAction("NULL") {
            public void actionPerformed(ActionEvent evt) {
            	disableAll(cards);
            }
        });
        
        group = new ButtonGroup();
        group.add(byVariable);
        group.add(byUser);
        group.add(byNull);
        byVariable.setSelected(true);
        
        pl.add(byVariable);
        pl.add(byUser);
        pl.add(byNull);
        
        return pl;
	}
	
	String[] conditionNames;
	public String[] getConditionNames() {
		return conditionNames;
	}
	public void setConditionNames(String[] conditionNames) {
		String[] oldConditions = this.conditionNames;		
		this.conditionNames = conditionNames;
		
		//conditionSelectInput.setSelections(conditionNames);
		comboModel = new DefaultComboBoxModel(conditionNames);
		combo.setModel(comboModel);
		
		//table���� "�б�"�� �ش��ϴ� row���� ��; ��ġ
		DefaultTableModel tableModel = (DefaultTableModel)getModel();
		Vector rows = tableModel.getDataVector();
		
		if(oldConditions!=null)
		for(int i=0; i<rows.size(); i++){
			Vector cols = (Vector)rows.get(i);
			String key = (String)cols.get(1);
			
			if(key!=null)
			for(int ci=0; ci<oldConditions.length; ci++){
				if(key.equals(oldConditions[ci]) && conditionNames.length > ci){
					setValueAt(conditionNames[ci], i, 1);
				}
			}
		}
	}
	//Editor
	ObjectInput valueInput;
	
	
	protected void editCell(){
		//Object value = table.getValueAt(row, column);
        //JPanel pl = new JPanel(new BorderLayout());
		
		//String[] condition = { "<", "<=", ">=", ">", "==", "!=" };
		pl = new JPanel();
		plOther = new JPanel();
		roleExist = new JPanel();
		
		//Panel ��ġ > dialog��
		dlg = createDialog(pl, plOther, roleExist);
		
		//v�Ǻб��� ���
		if(multiple){
			plOther.setBorder(BorderFactory.createEtchedBorder());
			//JTextField txtField = new JTextField("", 15);
			JLabel lblOtherwise = new JLabel(GlobalContext.getLocalizedMessage("conditioneditor.if_all_the_other_values_are_not_met.message", "If all the other values are not met.") );
			lblOtherwise.setBorder(BorderFactory.createEmptyBorder());
			plOther.add(lblOtherwise);
			
		}
		
		//pl.setLayout(new BoxLayout(pl, BoxLayout.X_AXIS));
		pl.setLayout(new FlowLayout());
		pl.setBorder(BorderFactory.createEtchedBorder());
		
        
		pvi = new ProcessVariableInput();
		pvi.setProcessDefinition(getProcessDefinition());
		pvi2 = new ProcessVariableInput();
		pvi2.setProcessDefinition(getProcessDefinition());
		conditionBox = new JComboBox(condition);
        //
        pl.add(pvi.getComponent());
        pl.add(conditionBox);
        
        //
        valueInput = new ObjectInput();
        
        Inputter dtInput_;
		try {
			dtInput_ = ObjectType.getDefaultInputter(Class.class);
		} catch (Exception e1) {
			dtInput_ = new DataTypeInput();
		}
		final Inputter dtInput = dtInput_;
		
        cards = new JPanel(new CardLayout());
        cards.add(pvi2.getComponent(), "byVariable");
        cards.add(dtInput.getComponent(), "byUser");       
        
        final JPanel dynamicPanel = new JPanel(new BorderLayout());
        
        dynamicPanel.add(CreateRadioPanel(), BorderLayout.NORTH);
        dynamicPanel.add(cards, BorderLayout.CENTER);
        //pl2.add(valueInput.getComponent(), BorderLayout.WEST);
        pl.add(dynamicPanel);
        
        
		dtInput.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try{
					Class type = (Class)dtInput.getValue();
					if(type!=null){
						if(valueInput==null)
							valueInput = new ObjectInput();
						
						cards.getParent().add(valueInput.getComponent(), BorderLayout.SOUTH);
						valueInput.setType(type);
						dlg.pack();
						
					}
				}catch(Exception ex){}
			}
		});
        
		
		roleExist.setLayout(new BoxLayout(roleExist, BoxLayout.X_AXIS));
		roleExist.setBorder(BorderFactory.createEtchedBorder());
		
		roleinput = new RoleInput();
		roleinput.setProcessDefinition(getProcessDefinition());
		roleComment = new JLabel(GlobalContext.getLocalizedMessage("conditioneditor.s_actual_binding_is_exist.label", "'s actual binding is "));
		
		roleExistOrNotExist = new JComboBox();
		roleExistOrNotExist.addItem("exist");
		roleExistOrNotExist.addItem("not exist");
		
		roleExist.add(roleinput.getComponent());
		roleExist.add(roleComment);
		roleExist.add(roleExistOrNotExist);
		
	    //��� v���� ��8�� ��n�1�
        try{
        	Vector rows = ((DefaultTableModel)getModel()).getDataVector();
        	Vector cols = (Vector)rows.get(selectedRow);
        	
        	if(cols.get(selectedColumn) instanceof Otherwise){
        		//outterButtonModel2.setSelected(true);
        		otherwiseButton.setSelected(true);
        		enableAll(plOther);
        		disableAll(pl);
        		disableAll(roleExist);
        	
        	}else if(cols.get(selectedColumn) instanceof Evaluate){
        		conditionButton.setSelected(true);
        		enableAll(pl);
		        disableAll(plOther);
		        disableAll(roleExist);
		        
        		Evaluate evaluate = (Evaluate)cols.get(selectedColumn);
		        if(evaluate!=null){
			        pvi.setValue(ProcessVariable.forName(evaluate.getKey()));
			        
			        if(evaluate.getValue() instanceof ProcessVariable ){
			        	//group.setSelected(innerButtonModel, true);
			        	byVariable.setSelected(true);
			        	pvi2.setValue((ProcessVariable)evaluate.getValue());
			        	
			        }else{
			        	//group.setSelected(innerButtonModel2, true);
			        	byUser.setSelected(true);
			        	//View ��ü
			        	CardLayout cl = (CardLayout)cards.getLayout();
			        	cl.show(cards, "byUser");
			        	
		                //������
		                dtInput.setValue(evaluate.getValue().getClass());
			        	valueInput.setValue(evaluate.getValue());
			        	dlg.pack();
			        	
			        }
			        
			        for(int i=0; i<condition.length; i++){
			        	if(condition[i].equals(evaluate.getCondition()))         conditionBox.setSelectedIndex(i);
			
			        }
		        }
		        
		        
			}else if(cols.get(selectedColumn) instanceof RoleExist){
		        roleExistButton.setSelected(true);
				disableAll(pl);
		        disableAll(plOther);
		        enableAll(roleExist);
		        
		        RoleExist roleExist = (RoleExist)cols.get(selectedColumn);
		        Role existingRole = roleExist.getRole();
		        if(existingRole==null && roleExist.getRoleName()!=null){
		        	existingRole = Role.forName(roleExist.getRoleName());
		        }
		        
		        roleinput.setValue(existingRole);
		        
		        
			}else{
				conditionButton.setSelected(true);
				byVariable.setSelected(true);
				
				enableAll(pl);
		        disableAll(plOther);
		        disableAll(roleExist);
			}
        }catch(Exception ex){
        	ex.printStackTrace();
        }finally{
        	dlg.pack();
        	dlg.setVisible(true);
        	
        	
        }
	}
	
	public void disableAll(Container container){
		if(container==null) return;
		Component[] components = container.getComponents();
		for(int i=0;i<components.length;i++){
			if(components[i] instanceof Container)
				disableAll((Container)components[i]);
			components[i].setEnabled(false);
		}
	}
	public void enableAll(Container container){
		if(container==null) return;
		Component[] components = container.getComponents();
		for(int i=0;i<components.length;i++){
			if(components[i] instanceof Container)
				enableAll((Container)components[i]);
			components[i].setEnabled(true);
		}
	}
	
	
	
	public int getOtherwiseColumn(int row){
		Vector rows = dataModel.getDataVector();
		
		for(int i=0;i<rows.size();i++){
			Vector theRow = (Vector)rows.get(i);
			for(int j=0;j<theRow.size();j++){
				if(i==row && theRow.get(j) instanceof Otherwise)
					return j;
			}
		}
		return -1;
	}
	
	
	public boolean isExistRole(int row){
		Vector rows = dataModel.getDataVector();
		
		for(int i=0;i<rows.size();i++){
			Vector theRow = (Vector)rows.get(i);
			for(int j=0;j<theRow.size();j++){
				if(i==row && theRow.get(j) instanceof RoleExist)
					return true;
			}
		}
		return false;
	}
	
	
	public boolean isExistEvaluate(int row){
		Vector rows = dataModel.getDataVector();
		
		for(int i=0;i<rows.size();i++){
			Vector theRow = (Vector)rows.get(i);
			for(int j=0;j<theRow.size();j++){
				if(i==row && (theRow.get(j) instanceof Evaluate || theRow.get(j) instanceof RoleExist))
					return true;
			}
		}
		return false;
	}
	
	public int getEvaluateCount(int row){
		Vector rows = dataModel.getDataVector();
		int cnt=0;
		for(int i=0;i<rows.size();i++){
			Vector theRow = (Vector)rows.get(i);
			for(int j=0;j<theRow.size();j++){
				if(i==row && theRow.get(j) instanceof Evaluate)
					cnt++;
			}
		}
		return cnt;
	}
	
	public boolean isEditable(int row){
		Vector rows = dataModel.getDataVector();
		
		for(int i=0;i<rows.size();i++){
			Vector theRow = (Vector)rows.get(i);
			for(int j=0;j<theRow.size();j++){
				if(i==row && theRow.get(j) instanceof Otherwise)
					return false;
			}
		}
		return true;
	}
	
	
	
	//�ٱ��� RadioButton
	ButtonGroup condGroup;
	//ButtonModel outterButtonModel;
	//ButtonModel outterButtonModel2;
	
	
	String currentCommand = CONDITION_COMMAND;
	public void actionPerformed(ActionEvent evt) {
		String command = ((JRadioButton)evt.getSource()).getActionCommand();
		currentCommand = command;
		
		if(command.equals(CONDITION_COMMAND)){
			enableAll(pl);
			disableAll(roleExist);
			if(plOther!=null)//�ݺ������� ��� NULL
				disableAll(plOther);
			
		}else if(command.equals(OTHERWISE_COMMAND)){
			enableAll(plOther);
        	disableAll(pl);
        	disableAll(roleExist);
        	
		}else if(command.equals(ROLEEXIST_COMMAND)){
			enableAll(roleExist);
        	disableAll(pl);
        	if(plOther!=null)//�ݺ������� ��� NULL
        		disableAll(plOther);
		}
		
    }
	
	//v������â
	JRadioButton conditionButton;
	JRadioButton otherwiseButton;
	JRadioButton roleExistButton;
	
	public JDialog createDialog(final Component contents, final Component other, final Component roleExist){
		
		//JFrame frame = new JFrame();
		JDialog dialog = new JDialog(ProcessDesigner.getInstance(), GlobalContext.getLocalizedMessage("conditioneditor.editcondition.label", "Edit Condition"), true);
        
        Container contentPane = dialog.getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        
        conditionButton = new JRadioButton(GlobalContext.getLocalizedMessage("conditioneditor.condition_expression.label", "Expression"));
        conditionButton.setActionCommand(CONDITION_COMMAND);
        conditionButton.addActionListener(this);
        
        roleExistButton = new JRadioButton(GlobalContext.getLocalizedMessage("conditioneditor.existence_of_actual_binding.label", "Existence of actual binding"));
        roleExistButton.setActionCommand(ROLEEXIST_COMMAND);
        roleExistButton.addActionListener(this);
        
        if(multiple){
        	
        	
        	
        	JPanel optPanel = new JPanel(new BorderLayout());
	        JPanel optPanel2 = new JPanel(new BorderLayout());
	        JPanel optPanel3 = new JPanel(new BorderLayout());
	        
	        /*conditionButton = new JRadioButton();
	        conditionButton.setActionCommand(CONDITION_ACTION_NAME);
	        conditionButton.addActionListener(this);*/
	        
	        otherwiseButton = new JRadioButton(GlobalContext.getLocalizedMessage("conditioneditor.otherwise.label", "Otherwise"));
	        otherwiseButton.setActionCommand(OTHERWISE_COMMAND);
	        otherwiseButton.addActionListener(this);
	        
	        /*roleExistButton = new JRadioButton();
	        roleExistButton.setActionCommand(ROLEEXIST_ACTION_NAME);
	        roleExistButton.addActionListener(this);*/
	        
	        condGroup = new ButtonGroup();
	        condGroup.add(conditionButton);
	        condGroup.add(otherwiseButton);
	        condGroup.add(roleExistButton);
	        
	        //outterButtonModel = conditionButton.getModel();
	        //outterButtonModel2 = otherwiseButton.getModel();
	        //condGroup.setSelected(outterButtonModel, true);
	    	//disableAll((JPanel)other);
	       
	    	
	    	
	        optPanel.add(conditionButton, BorderLayout.WEST);
	        optPanel2.add(otherwiseButton, BorderLayout.WEST);
	        optPanel3.add(roleExistButton, BorderLayout.WEST);
	        
	        //BoxLayout �̴ϱ� �� ��ų��
	        contentPane.add(optPanel);//v������ �ɼ�
	        contentPane.add((JPanel)contents);//�����
	        contentPane.add(optPanel3);//���� x�翩�� �ɼ�
	        contentPane.add(roleExist);//����x�翩��
	        contentPane.add(optPanel2);//v�ǿܰ�� �ɼ�
	        contentPane.add(other);//v�ǿܰ��
	        //conditionButton.setSelected(true);
	        
        }else{
        	
        	
        	JPanel optPanel = new JPanel(new BorderLayout());
        	JPanel optPanel2 = new JPanel(new BorderLayout());
        	
        	condGroup = new ButtonGroup();
	        condGroup.add(conditionButton);
	        condGroup.add(roleExistButton);
        	
	        conditionButton.setSelected(true);
	        
	        optPanel.add(conditionButton, BorderLayout.WEST);
        	optPanel2.add(roleExistButton, BorderLayout.WEST);
        	
        	contentPane.add(optPanel);//v������ �ɼ�
        	contentPane.add((JPanel)contents);//�����
        	contentPane.add(optPanel2);//v������ �ɼ�
        	contentPane.add(roleExist);//����x�翩��
        	
        }
        
        //Control Panel
        JButton okButton = new JButton(GlobalContext.getLocalizedMessage("basic.messages.ok", "Ok"));
        okButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				if(multiple){//v�Ǻб�
					//if(outterButtonModel.isSelected()){//�Ϲ�v��
					if(currentCommand.equals(CONDITION_COMMAND)){
						confirm(CONDITION_COMMAND);
					//}else {//Otherwise
					}else if(currentCommand.equals(OTHERWISE_COMMAND)) {
						if(!isExistEvaluate(selectedRow)){
							Otherwise otherwise = new Otherwise();
							ConditionTable.this.setValueAt(otherwise, selectedRow, selectedColumn);
						}else{
							showMessageDialog(GlobalContext.getLocalizedMessage("conditioneditor.condition_already_exist.message", "You can't set the otherwise in already filled cell."));
						}
						
					}else{
						confirm(ROLEEXIST_COMMAND);
					}
				}else{//�ݺ�����
					if(currentCommand.equals(CONDITION_COMMAND))
						confirm(CONDITION_COMMAND);
					else
						confirm(ROLEEXIST_COMMAND);
				}
				ConditionTable.this.dlg.setVisible(false);
			}
        	
        });
        
        JButton cancelButton = new JButton(GlobalContext.getLocalizedMessage("basic.messages.cancel", "Cancel"));
        cancelButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				ConditionTable.this.dlg.setVisible(false);
			}
        	
        });
        
        JPanel ctrlPanel = new JPanel(new FlowLayout());
        ctrlPanel.add(okButton);
        ctrlPanel.add(cancelButton);
        
        contentPane.add(ctrlPanel);
        
        dialog.setResizable(true);
        dialog.setLocationRelativeTo(
        		(Component)columnModel.getColumn(selectedColumn).getCellRenderer());
        //dialog.setVisible(true);
        return dialog;
        
        
	}
	/*public void setSelected(int sel){
		if(sel==CONDITION_SELECTED){
			outterButtonModel.setSelected(true);
		}else{
			outterButtonModel2.setSelected(true);
		}
	}*/
	
	public void confirm(String command){
		ProcessVariable pv = (ProcessVariable)pvi.getValue();
		String cond = (String)conditionBox.getSelectedItem();
		
		//if(innerButtonModel.isSelected()){//��������
		if(command.equals(CONDITION_COMMAND)){
			if(byVariable.isSelected()){//��������
				ProcessVariable pv2 = (ProcessVariable)pvi2.getValue();
				Evaluate eval = new Evaluate(pv, cond, pv2);
				ConditionTable.this.setValueAt(eval, selectedRow, selectedColumn);
			
			}else if(byUser.isSelected()){//��b�Է�
				Evaluate eval = new Evaluate(pv, cond, valueInput.getValue());
				ConditionTable.this.setValueAt(eval, selectedRow, selectedColumn);
				
			}else{//Null
				Evaluate eval = new Evaluate(pv, cond, null);
				ConditionTable.this.setValueAt(eval, selectedRow, selectedColumn);
			}
		}else{
			Role role = (Role)roleinput.getValue();
			Condition roleExist = new RoleExist(role);
			
			if(roleExistOrNotExist.getSelectedIndex()> 0){
				roleExist = new Not(roleExist);
			}
			
			ConditionTable.this.setValueAt(roleExist, selectedRow, selectedColumn);
			
		}
		
	}
	
	public void setSelectedItem(Object item){
		combo.getModel().setSelectedItem(item);
	}
	public Object getSelecedItem(){
		return combo.getModel().getSelectedItem();
		
	}
	
	public void showMessageDialog(String message){
		JOptionPane.showMessageDialog(this, message, GlobalContext.getLocalizedMessage("basic.messages.warning", "Warning"), 0);
		
	}

	ProcessDefinition processDefinition;

	public ProcessDefinition getProcessDefinition() {
		return processDefinition;
	}

	public void setProcessDefinition(ProcessDefinition processDefinition) {
		this.processDefinition = processDefinition;
	}
	
}


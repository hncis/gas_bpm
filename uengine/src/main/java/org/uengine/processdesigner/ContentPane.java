/**
 * 
 */
package org.uengine.processdesigner;

import java.awt.GridLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.uengine.kernel.GlobalContext;
import org.uengine.kernel.ProcessDefinition;
import org.uengine.kernel.designer.ProcessDefinitionDesigner;
import org.uengine.util.UEngineUtil;

/**
 * @author next3
 *
 */
public class ContentPane extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = GlobalContext.SERIALIZATION_UID;

	/**
	 * 
	 */
	public ContentPane(ProcessDesigner pd) {
		super(new GridLayout(1,1));
		JTabbedPane tabbedPane = new JTabbedPane();
		
		JPanel designerPanel = makeTextPanel("Panel #1");
		pd.setDesignerPanel(designerPanel);
		tabbedPane.addTab(GlobalContext.getLocalizedMessage("pd.label", "Process Designer"), designerPanel);
		
		ProcessDefinition processDefinition = ProcessDefinition.create();
		
		ProcessDefinitionDesigner cac = 
			 (ProcessDefinitionDesigner)UEngineUtil.getComponentByEscalation(processDefinition.getClass(), "designer");
		
		pd.setProcessDefinitionDesigner(cac);
		cac.setActivity(processDefinition);

		JComponent xpdPane = makeTextPanel("Panel #2");
		tabbedPane.addTab(GlobalContext.getLocalizedMessage("xpd.label", "XPD Viewer"), xpdPane);
		
		add(tabbedPane);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
	}

	private JPanel makeTextPanel(String text) {
		JPanel panel = new JPanel(false);
		JLabel filter = new JLabel(text);
		filter.setHorizontalAlignment(JLabel.CENTER);
		panel.setLayout(new GridLayout(1,1));
		panel.add(filter);
		return panel;
	}

}

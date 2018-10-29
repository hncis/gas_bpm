package org.uengine.processdesigner;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

import javax.jnlp.ServiceManager;
import javax.jnlp.SingleInstanceListener;
import javax.jnlp.SingleInstanceService;
import javax.jnlp.UnavailableServiceException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.codehaus.jackson.map.ObjectMapper;
import org.metaworks.FieldDescriptor;
import org.metaworks.GridApplication;
import org.metaworks.InputForm;
import org.metaworks.Instance;
import org.metaworks.ObjectInstance;
import org.metaworks.ObjectType;
import org.pushingpixels.flamingo.api.common.JCommandButton;
import org.pushingpixels.flamingo.api.common.icon.ImageWrapperResizableIcon;
import org.pushingpixels.flamingo.api.common.icon.ResizableIcon;
import org.pushingpixels.flamingo.api.ribbon.JRibbon;
import org.pushingpixels.flamingo.api.ribbon.JRibbonBand;
import org.pushingpixels.flamingo.api.ribbon.JRibbonFrame;
import org.pushingpixels.flamingo.api.ribbon.RibbonElementPriority;
import org.pushingpixels.flamingo.api.ribbon.RibbonTask;
import org.pushingpixels.flamingo.api.ribbon.resize.CoreRibbonResizePolicies;
import org.pushingpixels.flamingo.api.ribbon.resize.IconRibbonBandResizePolicy;
import org.pushingpixels.flamingo.api.ribbon.resize.RibbonBandResizePolicy;
import org.pushingpixels.substance.api.skin.SubstanceBusinessBlackSteelLookAndFeel;
import org.uengine.admin.ScriptConsole;
import org.uengine.kernel.Activity;
import org.uengine.kernel.ComplexActivity;
import org.uengine.kernel.DefaultActivity;
import org.uengine.kernel.DefaultProcessInstance;
import org.uengine.kernel.GlobalContext;
import org.uengine.kernel.ProcessDefinition;
import org.uengine.kernel.ProcessInstance;
import org.uengine.kernel.RevisionInfo;
import org.uengine.kernel.Role;
import org.uengine.kernel.Serializer;
import org.uengine.kernel.UEngineException;
import org.uengine.kernel.ValidationContext;
import org.uengine.kernel.designer.AbstractActivityDesigner;
import org.uengine.kernel.designer.ProcessDefinitionDesigner;
import org.uengine.ui.YesOrNoDialog;
import org.uengine.util.ClientProxy;
import org.uengine.util.UEngineUtil;

/**
 * ProcessDesigner
 *
 * @author Jinyoung Jang
 * @author <a href="mailto:ghbpark@hanwha.co.kr">Sungsoo Park</a>
 * @version $Id: ProcessDesigner.java,v 1.82 2014/11/07 06:37:40 mkbok Exp $
 */
public class ProcessDesigner extends JRibbonFrame implements Printable, ClipboardOwner, SingleInstanceListener {

    public static ClientProxy   proxy;

    SingleInstanceService       singleInstanceService;

    public final static Color   ACT_LABEL_BGCOLOR      = new Color(200, 200, 220);
    public final static Color   ACT_LABEL_COLOR        = new Color(0, 0, 0);      // new Color(200,200,220);
    public final static Color   DARK_ACT_LABEL_BGCOLOR = new Color(158, 187, 228); // new Color(130,130,180);

    private String              folderId;
    private String              definitionId;
    private String              defVerId;
    private String              superDefId;

    private RevisionInfo        revisionInfo;

    // mr.heo
    private final static String EMPTY_STRING           = "";
    JPanel                      browserPanel;
    JMenuItem                   undoMenuItem, redoMenuItem;
    public static JComponent    selectionMarker;
    static Point                selectionPoint;

    Map                         activityTypeNameMap    = new HashMap();

    public Map getActivityTypeNameMap() {
        return activityTypeNameMap;
    }

    JRibbon ribbonMenu;

    public JRibbon getRibbonMenu() {
        return ribbonMenu;
    }

    public void setRibbonMenu(JRibbon value) {
        ribbonMenu = value;
    }

    // JPanel activityMenuPanel;
    // public JPanel getActivityMenuPanel() {
    // return activityMenuPanel;
    // }
    // public void setActivityMenuPanel(JPanel value) {
    // activityMenuPanel = value;
    // }

    JPanel designerPanel;

    public JPanel getDesignerPanel() {
        return designerPanel;
    }

    public void setDesignerPanel(JPanel value) {
        designerPanel = value;
    }

    JPanel propertyPanel;

    public JPanel getPropertyPanel() {
        return propertyPanel;
    }

    public void setPropertyPanel(JPanel panel) {
        propertyPanel = panel;
    }

    JPanel           openedFilesPanel;

    // for simulator
    CardLayout       simulator_CardLayout;
    JPanel           simulator_TabPanel;
    JToggleButton    modelingTabBtn;
    JToggleButton    simulateTabBtn;
    // JToggleButton simulateTabBtn2;
    ProcessSimulator simulator;
    static JDialog          propertyDialog;
    JPanel           askMePanel;
    JPanel           threeActionsPanel;
    //

    JToolBar         toolBar;

    public JToolBar getToolBar() {
        return toolBar;
    }

    public void setToolBar(JToolBar bar) {
        toolBar = bar;
    }

    static ProcessDesigner instance;

    static public ProcessDesigner getInstance() {
        return instance;
    }

    static public void setInstance(ProcessDesigner value) {
        instance = value;
    }

    String savingFolder;

    public String getSavingFolder() {
        return savingFolder;
    }

    public void setSavingFolder(String value) {
        System.out.println("setSavingFolder : " + value);
        savingFolder = value;
    }

    boolean isAdhoc;

    public boolean isAdhoc() {
        return isAdhoc;
    }

    public void setAdhoc(boolean b) {
        isAdhoc = b;
    }

    String instanceId;

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String string) {
        instanceId = string;
    }

    ProcessInstance processInstance;

    public ProcessInstance getProcessInstance() {
        return processInstance;
    }

    public void setProcessInstance(ProcessInstance instance) {
        processInstance = instance;
    }

    boolean internalSingalForSplitter = false;

    boolean documentChanged;

    public boolean isDocumentChanged() {
        return documentChanged;
    }

    public void setDocumentChanged(boolean b) {

        if (b) {
            if (!getTitle().endsWith("*")) {
                setTitle(getTitle() + "*");
            }

        } else {
            if (getTitle().endsWith("*")) {
                setTitle(getTitle().substring(0, getTitle().length()));
            }
        }

        documentChanged = b;
    }

    Context                   initialContext;

    ProcessDefinitionDesigner processDefinitionDesigner;

    public ProcessDefinitionDesigner getProcessDefinitionDesigner() {
        return processDefinitionDesigner;
    }

    public void setProcessDefinitionDesigner(ProcessDefinitionDesigner value) {
        processDefinitionDesigner = value;

        getDesignerPanel().removeAll();
        getDesignerPanel().add("Center", value);

        setBackgroundRecursive(value);

        if (getInstance() != null)
            try {
                value.openDialog();
            } catch (Exception e) {
            }

        if (value != null && value.getActivity() != null)
            value.getActivity().addProperyChangeListener(new PropertyChangeListener() {

                public void propertyChange(PropertyChangeEvent evt) {
                    if (evt.getSource() instanceof ProcessDefinition && evt.getPropertyName().equals("")) {
                        try {
                            setDefinitionForUndo((ProcessDefinition) evt.getOldValue());
                            setDefinitionForRedo(null);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }

            });

    }

    public static void setBackgroundRecursive(Container con) {
        if (con instanceof AbstractActivityDesigner)
            con.setBackground(Color.WHITE);

        Component[] comp = con.getComponents();

        for (int i = 0; i < comp.length; i++) {
            if (comp[i] instanceof Container)
                setBackgroundRecursive((Container) comp[i]);
        }
    }

    Hashtable viewers = new Hashtable();

    String    currentWorkingPath;

    public String getCurrentWorkingPath() {
        return currentWorkingPath;
    }

    public void setCurrentWorkingPath(String string) {
        currentWorkingPath = string;
    }

    String currentWorkingFile;

    public String getCurrentWorkingFile() {
        return currentWorkingFile;
    }

    public void setCurrentWorkingFile(String string) {
        currentWorkingFile = string;
    }

    private ProcessDefinition definitionForUndo;

    public ProcessDefinition getDefinitionForUndo() {
        return definitionForUndo;
    }

    public void setDefinitionForUndo(ProcessDefinition s) {
        definitionForUndo = s;

        if (undoMenuItem != null)
            undoMenuItem.setEnabled(s != null);
    }

    private ProcessDefinition definitionForRedo;

    public ProcessDefinition getDefinitionForRedo() {
        return definitionForRedo;
    }

    public void setDefinitionForRedo(ProcessDefinition s) {
        definitionForRedo = s;

        if (redoMenuItem != null)
            redoMenuItem.setEnabled(s != null);
    }

    private String oldEncoding;

    public String getOldEncoding() {
        return oldEncoding;
    }

    public void setOldEncoding(String string) {
        oldEncoding = string;
    }

    String currentLocale;

    public String getCurrentLocale() {
        return currentLocale;
    }

    public void setCurrentLocale(String currentLocale) {
        this.currentLocale = currentLocale;
    }

    public ProcessDesigner() {
        super(GlobalContext.getLocalizedMessage("pd.window.title"));

        Splash splash = new Splash();
        splash.setVisible(true);

        try {
            singleInstanceService = (SingleInstanceService) ServiceManager.lookup("javax.jnlp.SingleInstanceService");
        } catch (UnavailableServiceException e) {
            singleInstanceService = null;
        }

        if (singleInstanceService != null)
            singleInstanceService.addSingleInstanceListener(this);

        GlobalContext.setDesignTime(true);

        System.out.println("rolemapping.class=" + GlobalContext.getPropertyString("rolemapping.class"));

        //
        setInstance(this);

        // ------ default window settings -------------------

        java.net.URL imgURL = getClass().getClassLoader().getResource("org/uengine/kernel/images/UE32X32.gif");
        super.setIconImage(new ImageIcon(imgURL).getImage());

        // setting inputter packages for loading inputters
        String[] inputterPkgs = GlobalContext.getPropertyStringArray("pd.inputterpackages");
        if (inputterPkgs != null)
            for (int i = 0; i < inputterPkgs.length; i++)
                ObjectType.addInputterPackage(inputterPkgs[i]);
        // end

        // setting default activity type names
        activityTypeNameMap.put(ProcessDefinition.class, GlobalContext.getLocalizedMessage("activitytypes.org.uengine.kernel.processdesigner.label", "Process Definition"));
        // end

        ObjectType.addInputterPackage("org.uengine.processdesigner.inputters");

        //Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        //screenSize.height = screenSize.height - 30;
        //this.setSize(screenSize);
        //this.setLocation(0, 0);
        // this.setSize(800, 500);

        this.addWindowListener(

        new WindowAdapter() {
            /*
             * public void windowClosing(WindowEvent e) { if(checkDiscardChanges()) System.exit(0); saveDesign(true);
             * System.exit(0); }
             */

            public void windowClosing(WindowEvent e) {
                int result = checkDiscardChanges();

                if (result == YesOrNoDialog.YES) {
                    System.exit(0);
                } else if (result == YesOrNoDialog.NO) {
                    saveDesign(true);
                    System.exit(0);
                } else {
                    // ProcessDesigner.getInstance().show();
                }

                singleInstanceService.removeSingleInstanceListener(ProcessDesigner.this);
                System.exit(0);

            }
        });


        setRibbonMenu(createRibbonMenu());
        setDesignerPanel(createDesignerPanel());

        boolean isPopupMode = true;
        final JTabbedPane tabPane = new JTabbedPane();// JTabbedPane.BOTTOM);
        final JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tabPane, propertyPanel);
        setTabs(tabPane, sp, isPopupMode);

        try {
            loadProperties();
        } catch (Exception e) {
            System.out.println("can't load default system properties.");
        }

        // -------- settings for main tab --------

        setCtrlButtons(tabPane, sp, isPopupMode);

        splash.close();

    }

    public void setCtrlButtons(final JTabbedPane tabPane, final JSplitPane sp, boolean isPopupMode) {
        // JPanel mainTabPanel = new JPanel(new GridLayout(0,1));
        simulator = new ProcessSimulator();

        JPanel mainTabPanel2 = new JPanel(new BorderLayout());
        threeActionsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        askMePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));

        modelingTabBtn = new JToggleButton(GlobalContext.getLocalizedMessage("pd.modeller.label"));
        threeActionsPanel.add(modelingTabBtn);
        simulateTabBtn = new JToggleButton(GlobalContext.getLocalizedMessage("pd.simulator.label"));
        threeActionsPanel.add(simulateTabBtn);

        JButton deployBtn = new JButton(GlobalContext.getLocalizedMessage("pd.deployer.label", "Deploy"));
        deployBtn.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                saveDesignToServer();
            }

        });
        threeActionsPanel.add(deployBtn);

        final JLabel askMeWhatever = new JLabel(GlobalContext.getLocalizedMessage("pd.ask.me.whatever", "Ask me whatever : "));
        JTextField askTf = new JTextField();
        askTf.setPreferredSize(new Dimension(150, 25));

        askMePanel.add(askMeWhatever);
        askMePanel.add(askTf);

        openedFilesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        mainTabPanel2.add("West", askMePanel);
        mainTabPanel2.add("Center", openedFilesPanel);
        mainTabPanel2.add("East", threeActionsPanel);

        askTf.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                openNativeBrowser("http://www.uengine.org/ask/?query=" + askMeWhatever.getText());
            }

        });

        // mr.heo 2005.12
        // simulateTabBtn2 = new JToggleButton(GlobalContext.getLocalizedMessage("pd.simulator.label")+"2");
        // mainTabPanel2.add(simulateTabBtn2);

        // mainTabPanel2.add(mainTabPanel);

        /*
         * JTabbedPane mainTab = new JTabbedPane(JTabbedPane.BOTTOM); mainTab.add("Modeling", sp); mainTab.add("Simulate", new
         * JLabel("Simulate"));
         */
        JPanel mainPanel = new JPanel(new BorderLayout());

        mainPanel.add("South", mainTabPanel2);
        simulator_CardLayout = new CardLayout();
        simulator_TabPanel = new JPanel(simulator_CardLayout);
        mainPanel.add("Center", simulator_TabPanel);
        if (isPopupMode) {
            simulator_TabPanel.add("tab1", tabPane);
            // try {
            // RolePicker picker = RolePicker.create();
            // picker.show();
            // } catch (Exception e) {
            // e.printStackTrace();
            // }
            propertyDialog = new JDialog(this);
            propertyDialog.add(propertyPanel);
//            propertyDialog.setBounds(this.getWidth()/2, 30, this.getWidth()/2, this.getHeight());
            //propertyDialog.setBounds((int) (this.getWidth() * 0.15), (int) (this.getHeight() * 0.5), (int) (this.getWidth() * 0.7), (int) (this.getHeight() * 0.4));
            propertyDialog.pack();
            propertyDialog.setVisible(true);
        } else {
            simulator_TabPanel.add("tab1", sp);
        }
        simulator_TabPanel.add("tab2", simulator);

        modelingTabBtn.setEnabled(false);

        modelingTabBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                simulator_CardLayout.show(simulator_TabPanel, "tab1");

                modelingTabBtn.setEnabled(false);
                simulateTabBtn.setEnabled(true);

            }
        });

        simulateTabBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                switchToSimulator();
            }
        });

//        JPanel ribbonMenuPanel = new JPanel(new BorderLayout());
//        ribbonMenuPanel.add(getRibbonMenu());
//        SubstanceLookAndFeel.setDecorationType(ribbonMenuPanel, DecorationAreaType.PRIMARY_TITLE_PANE);
//        getContentPane().add(ribbonMenuPanel, BorderLayout.NORTH);
        getContentPane().add(mainPanel, BorderLayout.CENTER);

        ProcessInstance.USE_CLASS = DefaultProcessInstance.class;
        ComplexActivity.USE_JMS = false;
        ComplexActivity.USE_THREAD = false;

        getProcessDefinitionDesigner().openDialog();

    }

    public void setTabs(final JTabbedPane tabPane, final JSplitPane sp, boolean isPopupMode) {

        tabPane.add("Designer", new JScrollPane(getDesignerPanel()));

        JEditorPane bpelEditorPane = new JEditorPane();
        bpelEditorPane.setContentType("text/xml");
        tabPane.add("BPEL", new JScrollPane(bpelEditorPane));
        viewers.put("BPEL", bpelEditorPane);

        JEditorPane beanEditorPane = new JEditorPane();
        beanEditorPane.setContentType("text/xml");
        tabPane.add("XPD", new JScrollPane(beanEditorPane));
        viewers.put("XPD", beanEditorPane);

        tabPane.getModel().addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                String encoding = tabPane.getTitleAt(tabPane.getSelectedIndex());

                changeView(encoding);
            }
        });

        propertyPanel = new JPanel(new BorderLayout());
        
        // JPanel leftPanel = new JPanel(new BorderLayout());
        // JPanel rightPanel = new JPanel(new BorderLayout());
        // leftPanel.add("Center", tabPane);
        // rightPanel.add("Center", propertyPanel);

        propertyPanel.addComponentListener(new ComponentListener() {

            public void componentResized(final ComponentEvent e) {
                // prevent the recursive call
                if (internalSingalForSplitter) {
                    internalSingalForSplitter = false;
                } else {
                    SwingUtilities.invokeLater(new Runnable() {

                        public void run() {
                            internalSingalForSplitter = true;
                            sp.setDividerLocation(((ProcessDesigner.this).getWidth() - propertyPanel.getWidth()));
                        }

                    });
                }
            }

            public void componentMoved(ComponentEvent e) {

            }

            public void componentShown(ComponentEvent e) {

            }

            public void componentHidden(ComponentEvent e) {

            }

        });

        sp.setDividerLocation(480);
    }

    public void loadDesignWithDefinitionId(final String definitionId) {

        new ProgressDialog("Loading...") {
            public void run() throws Exception {
                // pd.loadDesign(definitionId);
                // InputStream is = proxy.showProcessDefinitionWithVersionId(definitionID);//new
                // URL(UEngineUtil.getWebServerBaseURL() + URL_showProcessDefinitionJSPWithVersionId +
                // definitionId).openStream();
                InputStream is = proxy.showProcessDefinitionWithDefinitionId(definitionId);
                ProcessDesigner.getInstance().loadDesign(is, "XPD");
            }

            public void success() {
                dispose();
            }
        }.show();
    }

    private void switchToSimulator() {
        simulator_CardLayout.show(simulator_TabPanel, "tab2");
        modelingTabBtn.setEnabled(true);
        // simulateTabBtn2.setEnabled(true);
        simulateTabBtn.setEnabled(false);

        ProcessDefinition processDefinition = (ProcessDefinition) getProcessDefinitionDesigner().getActivity();
        processDefinition.beforeSerialization();
        simulator.setProcessDefinition(processDefinition);
    }

    /*
     * private boolean checkDiscardChanges(){ if(isDocumentChanged()){ YesOrNoDialog yesOrNo = new
     * YesOrNoDialog(ProcessDesigner.getInstance(), "Discard changes?"); yesOrNo.show(); boolean yes = yesOrNo.getAnswer();
     * return yes; } return true; }
     */

    private int checkDiscardChanges() {

        if (isDocumentChanged()) {

            (new Exception("checkDiscardChanges:")).printStackTrace();

            YesOrNoDialog yesOrNo = new YesOrNoDialog(ProcessDesigner.getInstance(), "Discard changes?");
            yesOrNo.show();
            int yesnocancel = yesOrNo.getResult();

            return yesnocancel;
        }

        return YesOrNoDialog.YES;

    }

    protected void changeView(String encoding) {
        // if(encoding.equals("Designer")) return;
        //
        // ByteArrayOutputStream bao = new ByteArrayOutputStream();
        // GlobalContext.serialize(getProcessDefinitionDesigner().getActivity(), bao, encoding);
        //
        // JEditorPane editor = (JEditorPane)viewers.get(encoding);
        // editor.setText(bao.toString("UTF-8"));

        try {
            if (encoding.equals("Designer")) {
                String oldEnc = getOldEncoding();
                JEditorPane editor = (JEditorPane) viewers.get(oldEnc);
                String changedDef = editor.getText();

                ByteArrayInputStream sbi = new ByteArrayInputStream(changedDef.getBytes("UTF-8"));
                loadDesign(sbi, oldEnc, false);
            } else {
                ByteArrayOutputStream bao = new ByteArrayOutputStream();

                JEditorPane editor = (JEditorPane) viewers.get(encoding);
                try {
                    GlobalContext.serialize(getProcessDefinitionDesigner().getActivity(), bao, encoding);
                    editor.setText(bao.toString("UTF-8"));
                } catch (Exception e) {
                    PrintWriter errWriter = new PrintWriter(bao);
                    e.printStackTrace(errWriter);
                    errWriter.flush();
                    editor.setText(bao.toString());
                    e.printStackTrace();
                }

                setOldEncoding(encoding);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    protected ArrayList getActivityTypeList() {
        try {
            /*
             * ArrayList activityList = new ArrayList(); String activityTypes =
             * GlobalContext.getProperties().getProperty("activitytypes"); StringTokenizer sk = new
             * StringTokenizer(activityTypes, ","); while(sk.hasMoreTokens()){ String activityType = sk.nextToken().trim();
             * activityList.add(activityType); } return activityList;
             */
            return (ArrayList) GlobalContext.deserialize(getClass().getClassLoader().getResourceAsStream("org/uengine/processdesigner/activitytypes.xml"), String.class);// ClassBrowser.findClasses(
                                                                                                                                                                         // "org.uengine.kernel.Activity");
        } catch (Exception e) {
            return new ArrayList();
        }
    }

    final static int        BUTTON_NAME  = 0;
    final static int        BUTTON_VALUE = 1;
    final static String[]   iconNames    = new String[] { "org/uengine/kernel/images/Toolbar-new.gif", "org/uengine/kernel/images/Toolbar-open.gif", "org/uengine/kernel/images/Toolbar-save.gif",
            "org/uengine/kernel/images/partners.gif",
            // "org/uengine/kernel/images/Toolbar-copy.gif",
            // "org/uengine/kernel/images/Toolbar-undo.gif",
            // "org/uengine/kernel/images/resume.gif",
            // "org/uengine/kernel/images/config.gif",
            "org/uengine/kernel/images/human.gif", "org/uengine/kernel/images/Toolbar-toblock.gif",
                                         // "org/uengine/kernel/images/help.gif"

                                         /*
                                          * Toolbar-copy.gif Toolbar-cut.gif Toolbar-delete.gif Toolbar-new.gif Toolbar-open.gif
                                          * Toolbar-paste.gif Toolbar-print.gif Toolbar-save.gif Toolbar-toblock.gif
                                          * Toolbar-undo.gif
                                          */

                                         };

    final static String[][] command      = new String[][] {
            { "New", GlobalContext.getLocalizedMessage("processdesigner.toolbar.new.label", "New") },
            { "Open", GlobalContext.getLocalizedMessage("processdesigner.toolbar.open.label", "Open") },
            { "Save", GlobalContext.getLocalizedMessage("processdesigner.toolbar.save.label", "Save") },
            { "Save As Server", GlobalContext.getLocalizedMessage("processdesigner.toolbar.deploy.label", "Deploy") },
            // {"Edit", "����"},
            // {"Undo", "�ǵ�����"},
            // {"Redo", "�ٽý���"},
            // {"Setting", "����"},
            { "Role Picker", GlobalContext.getLocalizedMessage("processdesigner.toolbar.rolepicker.label", "Role Picker") },
            { "Simulation", GlobalContext.getLocalizedMessage("processdesigner.toolbar.simulator.label", "Simulator") } /*
                                                                                                                         * ,
                                                                                                                         * {"Help"
                                                                                                                         * ,
                                                                                                                         * "����"
                                                                                                                         * }
                                                                                                                         */
                                         };

    public JRibbon createRibbonMenu() {
//        JRibbon ribbon = new JRibbon();
        createFileRibbonMenu(this.getRibbon());

        final ArrayList taskList = new ArrayList();
        HashMap bandByTask = new HashMap() {
            public Object put(Object key, Object val) {
                taskList.add(key);
                return super.put(key, val);
            }
        };
        HashMap activityByBand = new HashMap();
        try {
            ArrayList clsNames = getActivityTypeList();

            if (clsNames == null || clsNames.size() == 0)
                throw new UEngineException("No activitytypes.xml found by the ClassLoader. Please check the classpath.");
            
            Map<String, List<JRibbonBand>> bandMap = new HashMap<String, List<JRibbonBand>>();

            for (Iterator iter = clsNames.iterator(); iter.hasNext();) {
                Object actDesc = iter.next();

                if (actDesc == null)
                    continue;

                // get the activity descriptions
                String clsName = null;
                String group = "$activitytypes.groups.default.label";
                String taskName = "";
                String bandName = "";
                String bandIconName = "";
                String name = null;
                final Class activityCls;
                // {
                if (actDesc instanceof ActivityTypeDescriptor) {
                    ActivityTypeDescriptor typedActDesc = ((ActivityTypeDescriptor) actDesc);
                    clsName = typedActDesc.getActivityTypeClass();
                    group = typedActDesc.getGroup();
                    name = typedActDesc.getName();
                } else {
                    clsName = (String) actDesc;
                }

                if (name == null) {
                    name = UEngineUtil.getClassNameOnly(clsName);
                    name = GlobalContext.getLocalizedMessage("activitytypes." + clsName.toLowerCase() + ".label", name);
                }

                if (name.length() > 1 && name.startsWith("$")) {
                    name = GlobalContext.getLocalizedMessage(name.substring(1));
                }

                if (group.length() > 1 && group.startsWith("$")) {
                    // System.out.println("group = " +group);
                    bandIconName = group.substring(1);
                    group = GlobalContext.getLocalizedMessage(group.substring(1));
                    // groupSplitString = group.split("[.]");
                    // groupSplitString[0];
                }

                if (group.indexOf('.') != -1) {
                    String groupSplitString[] = group.split("[.]");
                    taskName = groupSplitString[0];
                    bandName = groupSplitString[1];
                } else {
                    taskName = GlobalContext.getLocalizedMessage("activitytypes.groups.others", taskName);
                    bandName = group;
                }

                try {
                    activityCls = Class.forName(clsName);
                } catch (Throwable e) {
//                    e.printStackTrace();
                    continue;
                }
                // }
                URL btnIconResourceUrl = getClass().getClassLoader().getResource(ActivityLabel.getPNGIconPath(activityCls));
                
                if ( btnIconResourceUrl == null )
                	btnIconResourceUrl = getClass().getClassLoader().getResource(ActivityLabel.getGIFIconPath(activityCls));
                if ( btnIconResourceUrl == null )
                	btnIconResourceUrl = getClass().getClassLoader().getResource(ActivityLabel.getPNGIconPath(DefaultActivity.class));
                if ( btnIconResourceUrl == null )
                	btnIconResourceUrl = getClass().getClassLoader().getResource(ActivityLabel.getGIFIconPath(DefaultActivity.class));

                ResizableIcon btnSVGIcon = null;

                if (btnIconResourceUrl != null) {
                    btnSVGIcon = ImageWrapperResizableIcon.getIcon(btnIconResourceUrl, new Dimension(32, 32));
                }


                if (btnSVGIcon != null) {
                    JCommandButton btn = new JCommandButton(name, btnSVGIcon);
                    activityTypeNameMap.put(activityCls, name);
//                    btn.setToolTipText(clsName);
                    btn.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent ae) {
                            ProcessDesigner.this.insertActivity(activityCls);
                            ProcessDesigner.this.setDocumentChanged(true);
                        }
                    });

                    DragSource dragSource = DragSource.getDefaultDragSource();
                    dragSource.createDefaultDragGestureRecognizer(btn, // component where drag originates
                            DnDConstants.ACTION_COPY_OR_MOVE, // actions
                            new DragGestureListener() {
                                /**
                                 * start of D&D framework implementation
                                 */

                                public void dragGestureRecognized(DragGestureEvent e) {
                                    e.startDrag(DragSource.DefaultCopyDrop, // cursor
                                            new Transferable() {

                                                public DataFlavor[] getTransferDataFlavors() {
                                                    return null;
                                                }

                                                public boolean isDataFlavorSupported(DataFlavor flavor) {
                                                    return false;
                                                }

                                                public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
                                                    List list = new ArrayList();
                                                    list.add(activityCls);
                                                    return list;
                                                }

                                            }); // drag source listener
                                }

                                public void dragDropEnd(DragSourceDropEvent e) {
                                }

                                public void dragEnter(DragSourceDragEvent e) {
                                }

                                public void dragExit(DragSourceEvent e) {
                                }

                                public void dragOver(DragSourceDragEvent e) {
                                }

                                public void dropActionChanged(DragSourceDragEvent e) {
                                }

                                /**
                                 * end of D&D framework implementation
                                 */
                            }); // drag gesture recognizer

                    if (!activityByBand.containsKey(bandName)) {
                        URL bandIconResourceUrl = getClass().getClassLoader().getResource(ActivityLabel.getPNGIconPath(activityCls, bandIconName));

                        ResizableIcon bandSVGIcon = null;

                        if (bandIconResourceUrl != null) {
                            bandSVGIcon = ImageWrapperResizableIcon.getIcon(bandIconResourceUrl, new Dimension(25, 25));
                        }

                        if (bandSVGIcon == null) { // when there's no representative icon for each task, use the first activity
                                                   // icon instead.
                            bandSVGIcon = btnSVGIcon;
                        }

                        if (bandSVGIcon != null) {
                            JRibbonBand band;
                            band = new JRibbonBand(bandName, null);
                            activityByBand.put(bandName, band);

//                            if (!bandByTask.containsKey(taskName)) {
//                                RibbonTask task = new RibbonTask(taskName);
//                                bandByTask.put(taskName, task);
//                            }
                            // System.out.println("#########lhbTaskName : " + taskName);
                            // System.out.println("#########lhbBandName : " + bandName);
                            List<JRibbonBand> taskRibbonList;
                            if (bandMap.containsKey(taskName)) {
                            	taskRibbonList = bandMap.get(taskName);
                            } else {
                            	taskRibbonList = new ArrayList<JRibbonBand>();
                            }
                            band.setResizePolicies(getActivityBandResizablePolicies(band));
                            taskRibbonList.add(band);
                            bandMap.put(taskName, taskRibbonList);
                            
                        }
                    }

                    if (activityByBand.get(bandName) != null) {
                        ((JRibbonBand) activityByBand.get(bandName)).addCommandButton(btn, RibbonElementPriority.MEDIUM);
                    }
                    // System.out.println("###########BtnText = " + btn.getText());
                } else {
                    System.err.println("[ERROR] No image icon for activity type [" + activityCls + "]");
                }

            }
            
            List<RibbonTask> finalTasks = new ArrayList<RibbonTask>();
            for (Iterator<String> iter = bandMap.keySet().iterator(); iter.hasNext();){
            	String taskName = iter.next();
            	List<JRibbonBand> ribbonList = bandMap.get(taskName);
            	JRibbonBand[] bandArray = new JRibbonBand[ribbonList.size()];
            	bandArray = ribbonList.toArray(bandArray);
            	finalTasks.add(new RibbonTask(taskName, bandArray));
            }

            addTask(this.getRibbon(), finalTasks, bandByTask);

            // ribbon.setPreferredSize(new Dimension(1024, 100));
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return this.getRibbon();
    }

    public void addTask(JRibbon ribbon, List<RibbonTask> taskList, HashMap bandByTask) {
        for (Iterator<RibbonTask> iter = taskList.iterator(); iter.hasNext();) {
            ribbon.addTask(iter.next());
        }
    }

    public JPanel createDesignerPanel() {
        JPanel pan = new JPanel(new BorderLayout());
        // pan.setBackground(Color.WHITE);

        setDesignerPanel(pan);

        ProcessDefinition processDefinition = ProcessDefinition.create();

        ProcessDefinitionDesigner cac = (ProcessDefinitionDesigner) UEngineUtil.getComponentByEscalation(processDefinition.getClass(), "designer");

        setProcessDefinitionDesigner(cac);
        addUndoListener(processDefinition);
        cac.setActivity(processDefinition);

        return pan;
    }

    private void addUndoListener(ProcessDefinition processDefinition) {
        processDefinition.addProperyChangeListener(new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getSource() instanceof ProcessDefinition && evt.getPropertyName().equals("")) {
                    try {
                        setDefinitionForUndo((ProcessDefinition) evt.getOldValue());
                        setDefinitionForRedo(null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

        });
    }

    private JRibbonBand getFileBand() {
        JRibbonBand band = null;
        URL bandIconResourceUrl = getClass().getClassLoader().getResource(ActivityLabel.getPNGIconPath(this.getClass(), "file"));
        if (bandIconResourceUrl != null) {
            ResizableIcon bandSVGIcon = ImageWrapperResizableIcon.getIcon(bandIconResourceUrl, new Dimension(32, 32));
            String bandName = GlobalContext.getLocalizedMessage("pd.menu.file", "File");
            band = new JRibbonBand(bandName, null);

            URL btnIconResourceUrl = getClass().getClassLoader().getResource(ActivityLabel.getPNGIconPath(this.getClass(), "new"));
            if (btnIconResourceUrl != null) {
            	ResizableIcon btnSVGIcon = ImageWrapperResizableIcon.getIcon(btnIconResourceUrl, new Dimension(32, 32));
                JCommandButton btn = new JCommandButton(GlobalContext.getLocalizedMessage("pd.menu.new", "New"), btnSVGIcon);
                btn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        loadDesign(ProcessDefinition.create());
                    }
                });
                band.addCommandButton(btn, RibbonElementPriority.MEDIUM);
            }

            btnIconResourceUrl = getClass().getClassLoader().getResource(ActivityLabel.getPNGIconPath(this.getClass(), "open"));
            if (btnIconResourceUrl != null) {
            	ResizableIcon btnSVGIcon = ImageWrapperResizableIcon.getIcon(btnIconResourceUrl, new Dimension(32, 32));
                JCommandButton btn = new JCommandButton(GlobalContext.getLocalizedMessage("pd.menu.open", "Open"), btnSVGIcon);
                btn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        loadDesign();
                    }
                });
                band.addCommandButton(btn, RibbonElementPriority.MEDIUM);
            }

            btnIconResourceUrl = getClass().getClassLoader().getResource(ActivityLabel.getPNGIconPath(this.getClass(), "save"));
            if (btnIconResourceUrl != null) {
            	ResizableIcon btnSVGIcon = ImageWrapperResizableIcon.getIcon(btnIconResourceUrl, new Dimension(32, 32));
                JCommandButton btn = new JCommandButton(GlobalContext.getLocalizedMessage("pd.menu.save", "Save"), btnSVGIcon);
                btn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        saveDesign(false);
                    }
                });
                band.addCommandButton(btn, RibbonElementPriority.MEDIUM);
            }

            btnIconResourceUrl = getClass().getClassLoader().getResource(ActivityLabel.getPNGIconPath(this.getClass(), "save_as"));
            if (btnIconResourceUrl != null) {
            	ResizableIcon btnSVGIcon = ImageWrapperResizableIcon.getIcon(btnIconResourceUrl, new Dimension(32, 32));
                JCommandButton btn = new JCommandButton(GlobalContext.getLocalizedMessage("pd.menu.saveas", "Save As"), btnSVGIcon);
                btn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        saveDesign(true);
                    }
                });
                band.addCommandButton(btn, RibbonElementPriority.MEDIUM);
            }

            btnIconResourceUrl = getClass().getClassLoader().getResource(ActivityLabel.getPNGIconPath(this.getClass(), "save_to_server"));
            if (btnIconResourceUrl != null) {
            	ResizableIcon btnSVGIcon = ImageWrapperResizableIcon.getIcon(btnIconResourceUrl, new Dimension(32, 32));
                JCommandButton btn = new JCommandButton(GlobalContext.getLocalizedMessage("pd.menu.savetoserver", "Save to server"), btnSVGIcon);
                btn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        saveDesignToServer();
                    }
                });
                band.addCommandButton(btn, RibbonElementPriority.MEDIUM);
            }

            btnIconResourceUrl = getClass().getClassLoader().getResource(ActivityLabel.getPNGIconPath(this.getClass(), "import_from_bpel"));
            if (btnIconResourceUrl != null) {
            	ResizableIcon btnSVGIcon = ImageWrapperResizableIcon.getIcon(btnIconResourceUrl, new Dimension(32, 32));
                JCommandButton btn = new JCommandButton(GlobalContext.getLocalizedMessage("pd.menu.importtobpel", "Import from BPEL"), btnSVGIcon);
                btn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        importBPEL();
                    }
                });
                band.addCommandButton(btn, RibbonElementPriority.MEDIUM);
            }

            btnIconResourceUrl = getClass().getClassLoader().getResource(ActivityLabel.getPNGIconPath(this.getClass(), "expose_webservices"));
            if (btnIconResourceUrl != null) {
            	ResizableIcon btnSVGIcon = ImageWrapperResizableIcon.getIcon(btnIconResourceUrl, new Dimension(32, 32));
                JCommandButton btn = new JCommandButton(GlobalContext.getLocalizedMessage("pd.menu.exposeaswebservice", "Expose WebServices"), btnSVGIcon);
                btn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        generateServiceClass();
                    }
                });
                band.addCommandButton(btn, RibbonElementPriority.MEDIUM);
            }

            btnIconResourceUrl = getClass().getClassLoader().getResource(ActivityLabel.getPNGIconPath(this.getClass(), "generate_partner_process"));
            if (btnIconResourceUrl != null) {
                ResizableIcon btnSVGIcon = ImageWrapperResizableIcon.getIcon(btnIconResourceUrl, new Dimension(32, 32));
                JCommandButton btn = new JCommandButton(GlobalContext.getLocalizedMessage("pd.menu.generatepartnerprocess", "Generate Partener Process"), btnSVGIcon);
                btn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        generatePartnerProcess();
                    }
                });
                band.addCommandButton(btn, RibbonElementPriority.MEDIUM);
            }

            btnIconResourceUrl = getClass().getClassLoader().getResource(ActivityLabel.getPNGIconPath(this.getClass(), "print"));
            if (btnIconResourceUrl != null) {
            	ResizableIcon btnSVGIcon = ImageWrapperResizableIcon.getIcon(btnIconResourceUrl, new Dimension(32, 32));
                JCommandButton btn = new JCommandButton(GlobalContext.getLocalizedMessage("pd.menu.print", "Print"), btnSVGIcon);
                btn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        printDesign();
                    }
                });
                band.addCommandButton(btn, RibbonElementPriority.MEDIUM);
            }
        }
        band.setResizePolicies(getFile1BandResizablePolicies(band));
        return band;
    }

    private JRibbonBand getEditBand() {
        JRibbonBand band = null;
        URL bandIconResourceUrl = getClass().getClassLoader().getResource(ActivityLabel.getPNGIconPath(this.getClass(), "edit"));
        if (bandIconResourceUrl != null) {
        	ResizableIcon bandSVGIcon = ImageWrapperResizableIcon.getIcon(bandIconResourceUrl, new Dimension(32, 32));
            String bandName = GlobalContext.getLocalizedMessage("pd.menu.edit", "Edit");
            band = new JRibbonBand(bandName, null);

            URL btnIconResourceUrl = getClass().getClassLoader().getResource(ActivityLabel.getPNGIconPath(this.getClass(), "undo"));
            if (btnIconResourceUrl != null) {
                ResizableIcon btnSVGIcon = ImageWrapperResizableIcon.getIcon(btnIconResourceUrl, new Dimension(32, 32));
                JCommandButton btn = new JCommandButton(GlobalContext.getLocalizedMessage("pd.menu.undo", "Undo"), btnSVGIcon);
                btn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        undo();
                    }
                });
                band.addCommandButton(btn, RibbonElementPriority.MEDIUM);
            }

            btnIconResourceUrl = getClass().getClassLoader().getResource(ActivityLabel.getPNGIconPath(this.getClass(), "redo"));
            if (btnIconResourceUrl != null) {
                ResizableIcon btnSVGIcon = ImageWrapperResizableIcon.getIcon(btnIconResourceUrl, new Dimension(32, 32));
                JCommandButton btn = new JCommandButton(GlobalContext.getLocalizedMessage("pd.menu.redo", "Redo"), btnSVGIcon);
                btn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        redo();
                    }
                });
                band.addCommandButton(btn, RibbonElementPriority.MEDIUM);
            }

            btnIconResourceUrl = getClass().getClassLoader().getResource(ActivityLabel.getPNGIconPath(this.getClass(), "settings"));
            if (btnIconResourceUrl != null) {
                ResizableIcon btnSVGIcon = ImageWrapperResizableIcon.getIcon(btnIconResourceUrl, new Dimension(32, 32));
                JCommandButton btn = new JCommandButton(GlobalContext.getLocalizedMessage("pd.menu.settings", "Settings"), btnSVGIcon);
                btn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        settings();
                    }
                });
                band.addCommandButton(btn, RibbonElementPriority.MEDIUM);
            }

            btnIconResourceUrl = getClass().getClassLoader().getResource(ActivityLabel.getPNGIconPath(this.getClass(), "role_picker"));
            if (btnIconResourceUrl != null) {
                ResizableIcon btnSVGIcon = ImageWrapperResizableIcon.getIcon(btnIconResourceUrl, new Dimension(32, 32));
                JCommandButton btn = new JCommandButton(GlobalContext.getLocalizedMessage("pd.roles.label", "Role Picker"), btnSVGIcon);
                btn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        viewRoleResolutionDlg();
                    }
                });
                band.addCommandButton(btn, RibbonElementPriority.MEDIUM);
            }
        }
        band.setResizePolicies(getFileBandResizablePolicies(band));
        return band;
    }

    private JRibbonBand getSimulationBand() {
        JRibbonBand band = null;
        URL bandIconResourceUrl = getClass().getClassLoader().getResource(ActivityLabel.getPNGIconPath(this.getClass(), "simulation"));
        if (bandIconResourceUrl != null) {
//            ResizableIcon bandSVGIcon = ImageWrapperResizableIcon.getIcon(bandIconResourceUrl, new Dimension(32, 32));
            String bandName = GlobalContext.getLocalizedMessage("pd.menu.simulation", "Simulation");
            band = new JRibbonBand(bandName, null);

            URL btnIconResourceUrl = getClass().getClassLoader().getResource(ActivityLabel.getPNGIconPath(this.getClass(), "test"));
            if (btnIconResourceUrl != null) {
                ResizableIcon btnSVGIcon = ImageWrapperResizableIcon.getIcon(btnIconResourceUrl, new Dimension(32, 32));
                JCommandButton btn = new JCommandButton(GlobalContext.getLocalizedMessage("pd.menu.test"), btnSVGIcon);
                btn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        switchToSimulator();
                    }
                });
                band.addCommandButton(btn, RibbonElementPriority.MEDIUM);
            }
        }
        band.setResizePolicies(getFileBandResizablePolicies(band));
        return band;
    }

    private JRibbonBand getHelpBand() {
        JRibbonBand band = null;
        URL bandIconResourceUrl = getClass().getClassLoader().getResource(ActivityLabel.getPNGIconPath(this.getClass(), "help"));
        if (bandIconResourceUrl != null) {
            ResizableIcon bandSVGIcon = ImageWrapperResizableIcon.getIcon(bandIconResourceUrl, new Dimension(32, 32));
            String bandName = GlobalContext.getLocalizedMessage("pd.menu.help", "Help");
            band = new JRibbonBand(bandName, null);

            URL btnIconResourceUrl = getClass().getClassLoader().getResource(ActivityLabel.getPNGIconPath(this.getClass(), "help"));
            if (btnIconResourceUrl != null) {
                ResizableIcon btnSVGIcon = ImageWrapperResizableIcon.getIcon(btnIconResourceUrl, new Dimension(32, 32));
                JCommandButton btn = new JCommandButton(GlobalContext.getLocalizedMessage("pd.menu.help", "Help"), btnSVGIcon);
                btn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        help();
                    }
                });
                band.addCommandButton(btn, RibbonElementPriority.MEDIUM);
            }

            btnIconResourceUrl = getClass().getClassLoader().getResource(ActivityLabel.getPNGIconPath(this.getClass(), "about"));
            if (btnIconResourceUrl != null) {
                ResizableIcon btnSVGIcon = ImageWrapperResizableIcon.getIcon(btnIconResourceUrl, new Dimension(32, 32));
                JCommandButton btn = new JCommandButton(GlobalContext.getLocalizedMessage("pd.menu.about", "About"), btnSVGIcon);
                btn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        about();
                    }
                });
                band.addCommandButton(btn, RibbonElementPriority.MEDIUM);
            }

            /*btnIconResourceUrl = getClass().getClassLoader().getResource(ActivityLabel.getPNGIconPath(this.getClass(), "update"));
            if (btnIconResourceUrl != null) {
                ResizableIcon btnSVGIcon = ImageWrapperResizableIcon.getIcon(btnIconResourceUrl, new Dimension(32, 32));
                JCommandButton btn = new JCommandButton(GlobalContext.getLocalizedMessage("pd.menu.update"), btnSVGIcon);
                btn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        update();
                    }
                });
                // SubstanceLookAndFeel.setDecorationType(btn, DecorationAreaType.GENERAL);
                band.addCommandButton(btn, RibbonElementPriority.MEDIUM);
            }*/
        }
        band.setResizePolicies(getFileBandResizablePolicies(band));
        return band;
    }

    private List<RibbonBandResizePolicy> getFileBandResizablePolicies(JRibbonBand band) {
		List<RibbonBandResizePolicy> policies = new ArrayList<RibbonBandResizePolicy>();
		policies.add(new CoreRibbonResizePolicies.None(band.getControlPanel()));
		policies.add(new IconRibbonBandResizePolicy(band.getControlPanel()));
		return policies;
	}
    private List<RibbonBandResizePolicy> getFile1BandResizablePolicies(JRibbonBand band) {
    	List<RibbonBandResizePolicy> policies = new ArrayList<RibbonBandResizePolicy>();
		policies.add(new CoreRibbonResizePolicies.None(band.getControlPanel()));
    	policies.add(new IconRibbonBandResizePolicy(band.getControlPanel()));
    	return policies;
    }

    private List<RibbonBandResizePolicy> getActivityBandResizablePolicies(JRibbonBand band) {
    	List<RibbonBandResizePolicy> policies = new ArrayList<RibbonBandResizePolicy>();
    	policies.add(new CoreRibbonResizePolicies.None(band.getControlPanel()));
//    	policies.add(new CoreRibbonResizePolicies.Mid2Low(band.getControlPanel()));
    	policies.add(new IconRibbonBandResizePolicy(band.getControlPanel()));
    	return policies;
    }

	protected void createFileRibbonMenu(JRibbon ribbon) {
    	List<JRibbonBand> bandList = new ArrayList<JRibbonBand>();
        JRibbonBand fileBand;
        if ((fileBand = getFileBand()) != null) {
        	bandList.add(fileBand);
        }

        JRibbonBand editBand;
        if ((editBand = getEditBand()) != null) {
        	bandList.add(editBand);
        }

//        JRibbonBand simulationBand;
//        if ((simulationBand = getSimulationBand()) != null) {
//        	bandList.add(simulationBand);
//        }

        JRibbonBand helpBand;
        if ((helpBand = getHelpBand()) != null) {
        	bandList.add(helpBand);
        }
        
        JRibbonBand[] bands = new JRibbonBand[bandList.size()];
        
        bands = bandList.toArray(bands);
        
        RibbonTask fileTask = new RibbonTask(GlobalContext.getLocalizedMessage("pd.menu.file", "File"), bands);

        ribbon.addTask(fileTask);
        ribbon.add(new JSeparator(JSeparator.VERTICAL));
    }

    public void viewRoleResolutionDlg() {
        try {
            RolePicker picker = RolePicker.create();
            picker.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertActivity(Class cls) {
        try {
            Activity activity = (Activity) cls.newInstance();
            insertActivity(activity.createDesigner());
        } catch (Exception e) {
        }
    }

    public void insertActivity(ActivityDesigner designer) {
        getDesignerPanel().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        try {
            Activity activity = designer.getActivity();
            Vector selectedComps = ActivityDesignerListener.getSelectedComponents();
            if (selectedComps != null && selectedComps.size() == 1) {
                Vector addingActivityComps = new Vector();
                addingActivityComps.add(designer);

                ((ActivityDesigner) selectedComps.elementAt(0)).onDropped(addingActivityComps);

            } else if (selectedComps != null && selectedComps.size() > 1 && activity instanceof ComplexActivity) {
                ActivityDesigner wrapper = designer;

                Vector addingActivityComps = new Vector();
                addingActivityComps.add(wrapper);
                ((ActivityDesigner) selectedComps.elementAt(0)).onDropped(addingActivityComps);

                wrapper.onDropped(selectedComps);
            } else {
                getProcessDefinitionDesigner().addActivity(designer);
            }

            getDesignerPanel().revalidate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // //////////////////// private methods ///////////////////////////////

    protected void saveDesign(boolean rename) {

        String fileName = null;
        String filePath = null;

        File currentWorkingFileObj = null;
        if (getCurrentWorkingFile() != null) {
            currentWorkingFileObj = new File(getCurrentWorkingFile());
        }

        if (rename || currentWorkingFileObj == null || !currentWorkingFileObj.exists()) {

            JFileChooser chooser = new JFileChooser();

            if (getCurrentWorkingPath() != null)
                chooser.setSelectedFile(new File(getCurrentWorkingPath()));

            chooser.setDialogType(JFileChooser.SAVE_DIALOG);
            int returnVal = chooser.showSaveDialog(this);
            if (returnVal != JFileChooser.APPROVE_OPTION)
                return;

            currentWorkingFileObj = chooser.getSelectedFile();
        }

        fileName = currentWorkingFileObj.getName();
        filePath = currentWorkingFileObj.getParent();

        ProcessDefinition def = (ProcessDefinition) getProcessDefinitionDesigner().getActivity();

        // serialize
        try {
            String fileType = "XPD";

            if (fileName.lastIndexOf('.') < 0)
                fileName = fileName + "." + fileType;

            /*
             * String fileType = fileName.substring( fileName.lastIndexOf(".")+1); if( fileName.lastIndexOf(".") < 0){ fileType
             * = ((ExampleFileFilter)chooser.getFileFilter()).getDescription(); fileType = fileType.substring( 0,
             * fileType.indexOf(" ")); //somthing looks wrong --> filePath = filePath + "." + fileType; }
             * if(fileType.toUpperCase().equals("BEAN")) fileType = "Bean";
             */

            System.out.println("\nSelected FileType : " + fileType);
            FileOutputStream fo = new FileOutputStream(filePath + System.getProperty("file.separator") + fileName);

            Class cls = Class.forName("org.uengine.components.serializers." + fileType + "Serializer");
            Serializer se = (Serializer) cls.newInstance();
            System.out.println("Use this serializer : " + se);

            // ignore the belonging definition if write to a file
            String temp = def.getBelongingDefinitionId();
            def.setBelongingDefinitionId(null);

            se.serialize(def, fo, null);

            // TestSVGGen.export2SVG(getProcessDefinitionDesigner(), new FileOutputStream(filePath +
            // System.getProperty("file.separator") + fileName+".svg"));

            // restore the belonging def
            def.setBelongingDefinitionId(temp);
            setCurrentWorkingPath(filePath);
            setCurrentWorkingFile(currentWorkingFileObj.getPath());
            setDocumentChanged(false);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    protected void generateServiceClass() {
        final ProcessDefinition definition = (ProcessDefinition) getProcessDefinitionDesigner().getActivity();

        /*
         * JFileChooser chooser = new JFileChooser(); chooser.setDialogType(JFileChooser.SAVE_DIALOG);
         * chooser.setDialogTitle("Generate and deploy Web Service"); String axisDirectory =
         * System.getProperty(GlobalContext.PROPERTY_AXIS_WEBAPP_DIR, ".");
         */

        /*
         * String defaultFileName = axisDirectory + System.getProperty("file.separator") + procName + ".jws"; File savingFile =
         * new File(defaultFileName); //System.out.println("service class path = " + savingFile.getAbsolutePath());
         * chooser.setCurrentDirectory(new File(axisDirectory)); chooser.setSelectedFile(savingFile); int returnVal =
         * chooser.showSaveDialog(this); if(returnVal != JFileChooser.APPROVE_OPTION) return; try{ String fileName =
         * chooser.getSelectedFile().getName(); String fileType = fileName.substring( fileName.lastIndexOf(".")+1); String
         * filePath = chooser.getSelectedFile().getPath(); PrintStream fo = new PrintStream(new FileOutputStream(filePath));
         * org.uengine.webservice.ServiceClassGenerator.generateSource(definition, new Properties(), fo); }catch( Exception ex){
         * ex.printStackTrace(); }
         */

        final ProgressDialog progDlg = new ProgressDialog("deploy...") {
            public void run() throws Exception {

                String procName;
                {
                    procName = definition.getName().getText();
                    procName = procName.replace(' ', '_');
                    String firstChar = procName.substring(0, 1);
                    procName = firstChar.toUpperCase() + procName.substring(1, procName.length());
                }

                String filePath = System.getProperty(GlobalContext.PROPERTY_UENGINE_HOME_DIR) + "/src/exposedprocesses/";
                PrintStream fo = new PrintStream(new FileOutputStream(filePath + procName + "SoapBindingImpl.java"));
                PrintStream fo2 = new PrintStream(new FileOutputStream(filePath + procName + ".java"));

                Properties option = new Properties();
                option.setProperty("packageName", "org.uengine");
                option.setProperty("className", procName + "SoapBindingImpl");
                option.setProperty("interface", procName);
                org.uengine.webservice.ServiceClassGenerator.generateSource(definition, option, fo);
                fo.close();

                option.setProperty("isInterface", "yes");
                option.setProperty("className", procName);
                org.uengine.webservice.ServiceClassGenerator.generateSource(definition, option, fo2);
                fo2.close();

                AntToolDialog antTool = new AntToolDialog();
                antTool.setCommand("exposeProcess -Dparam.definition=" + procName);
                antTool.run();
            }
        };

        progDlg.show();

    }

    protected void saveDesignToServer() {
        try {
            saveDesignToServer(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void saveDesignToServer(final boolean needConfirm) throws Exception {
        saveDesignToServer(needConfirm, (ProcessDefinition) getProcessDefinitionDesigner().getActivity());
    }

    public boolean validateDefinition(ProcessDefinition definition) {
        ValidationContext valCtx = definition.validate(null);

        if (valCtx != null && !valCtx.hasNoError()) {
            JDialog d = new JDialog(this, "This process definition need to be verified.", true);
            JTextArea ta = new JTextArea();
            ta.setEditable(false);
            d.getContentPane().add(new JScrollPane(ta));

            StringBuffer errMsg = new StringBuffer();
            for (Enumeration enumeration = valCtx.elements(); enumeration.hasMoreElements();) {
                Object item = (Object) enumeration.nextElement();
                errMsg.append(item + "\n");
            }
            ta.setText(errMsg.toString());

            d.setSize(440, 150);
            d.setLocationRelativeTo(this);
            d.show();
            return false;
        }

        return true;
    }

    public boolean saveDesignToServer(final boolean needConfirm, final ProcessDefinition definition) throws Exception {
        final ProcessDesigner finalThis = this;
        // /
        if (!validateDefinition(definition))
            return false;

        if (isAdhoc()) {

            final ProgressDialog progDlg = new ProgressDialog("deploy...", GlobalContext.getLocalizedMessage("deploy.msg.success", "Your process definition has been successfully deployed.")) {
                public void run() throws Exception {
                    String instanceId = getInstanceId();
                    ProcessDefinition def = definition;

                    ByteArrayOutputStream bao = new ByteArrayOutputStream();
                    GlobalContext.serialize(def, bao, "XPD");

                    Map parameters = new HashMap();
                    parameters.put("instanceId", instanceId);
                    parameters.put("definitionXML", bao.toString("UTF-8"));

                    String result = proxy.changeProcessDefinition(parameters);
                    
                    ObjectMapper mapper = new ObjectMapper();
                    Map<String, String> resultParameters = mapper.readValue(result, Map.class);
                    if ( "fail".equals(resultParameters.get("status")) ){
                    	String errorMessage = (String) resultParameters.get("message");
                        throw new Exception(errorMessage);
                    }

                    if (result == null) {
                        throw new Exception(result);
                    }

                    setDocumentChanged(false);
                }

                public void success() {
                    if (!needConfirm)
                        dispose();
                    else
                        super.success();
                }
            };

            progDlg.show();

            return (progDlg.getFailCause() == null);
        }

        // /if validation process is ok
        final Vector emptyIfSuccess = new Vector();

        org.metaworks.Type saveDialog = new ObjectType(SaveDialogInfo.class) {

            public void save(final Instance rec) {
                super.save(rec);

                final SaveDialogInfo saveDialogInfo = (SaveDialogInfo) ((ObjectInstance) rec).getObject();

                try {
                    saveDesignToServer(definition, saveDialogInfo);
                } catch (UEngineException e) {
                    emptyIfSuccess.add("failed");
                }

            }

            public void update(Instance rec) {
                save(rec);
            }
        };

        System.out.print(getDefinitionId());
        if (UEngineUtil.isNotEmpty(getDefinitionId())) {
            saveDialog.removeFieldDescriptor("Alias");
        }

        InputForm inputForm = (new InputForm(saveDialog));

        ProcessDefinition def = definition;

        String definitionName = def.getName().getText();
        String alias = def.getAlias();
        int version = def.getVersion();

        SaveDialogInfo defaultSettings = new SaveDialogInfo();
        defaultSettings.setName(definitionName);
        defaultSettings.setAlias(alias);
        defaultSettings.setVersion(version + 1);
        defaultSettings.setAuthor(getRevisionInfo());

        // ObjectInstance objInstance = saveDi

        /*
         * Instance defaultSettings = saveDialog.createInstance();{ ProcessDefinition def =
         * (ProcessDefinition)getProcessDefinitionDesigner().getActivity(); String definitionName = def.getName().getText();
         * String alias = def.getAlias(); int version=def.getVersion(); defaultSettings.setFieldValue("Name", definitionName);
         * defaultSettings.setFieldValue("Alias", alias); defaultSettings.setFieldValue("Version", new Integer(version+1));
         * defaultSettings.setFieldValue("Author", getRevisionInfo()); }
         */

        ObjectInstance objInstance = (ObjectInstance) saveDialog.createInstance();
        objInstance.setObject(defaultSettings);
        inputForm.setInstance(objInstance);
        inputForm.postInputDialog(this, "Confirm", "Confirm", saveDialog.getName());

        return emptyIfSuccess.isEmpty();

    }

    public void saveDesignToServer(final ProcessDefinition definition, final SaveDialogInfo saveDialogInfo) throws UEngineException {
        final int version = definition.getVersion();
        final ProgressDialog progDlg = new ProgressDialog("deploy...") {

            public void run() throws Exception {
                Serializer se = GlobalContext.getSerializer("XPD");

                ProcessDefinition def = definition;

                def.setName(saveDialogInfo.getName());
                def.setAlias(saveDialogInfo.getAlias());
                def.setVersion(saveDialogInfo.getVersion());
                saveDialogInfo.getAuthor().setVersion(saveDialogInfo.getVersion());
                String author = saveDialogInfo.getAuthor().getAuthorId();

                RevisionInfo revInfo = saveDialogInfo.getAuthor();
                def.addRevisionInfo(revInfo);
                def.clearWithOutLastRevisionInfo();

                setRevisionInfo((RevisionInfo) revInfo.clone());

                ByteArrayOutputStream bao = new ByteArrayOutputStream();
                se.serialize(def, bao, null);

                String strDef = bao.toString("UTF-8");

                System.out.println("getSavingFolder() : " + getSavingFolder());
                Map parameters = new HashMap();

                parameters.put("definition", strDef);
                parameters.put("definitionName", def.getName().getText());
                parameters.put("version", "" + def.getVersion());
                parameters.put("description", saveDialogInfo.getAuthor().getChangeDescription());
                parameters.put("folderId", getFolderId());
                parameters.put("programId", def.getProgramId());
                parameters.put("author", author);
                parameters.put("duration", Integer.toString(def.getDuration()));
                

                if (UEngineUtil.isNotEmpty(getSuperDefId())) {
                    parameters.put("defId", null);
                    parameters.put("version", "1");
                    parameters.put("superDefId", getSuperDefId());
                } else {
                    if (def == getProcessDefinitionDesigner().getActivity()) {
                        parameters.put("defId", getDefinitionId());
                        parameters.put("defVerId", getDefinitionVersionId());
                    } else {
                        parameters.put("defId", def.getBelongingDefinitionId());
                        parameters.put("defVerId", def.getId());
                    }
                }

                parameters.put("alias", def.getAlias());

                parameters.put("autoProduction", "" + saveDialogInfo.isAutoProduction());

                String result = proxy.saveProcessDefinition(parameters);
                
                ObjectMapper mapper = new ObjectMapper();
                Map<String, String> resultParameters = mapper.readValue(result, Map.class);
                if ( "fail".equals(resultParameters.get("status")) ){
                	String errorMessage = (String) resultParameters.get("message");
                	def.getRevisionInfoList().remove(revInfo);
                    definition.setVersion(version);
                    throw new Exception(errorMessage);
                }
                if ( resultParameters.get("defVerId") != null ) {
                	String[] newDefVerId = resultParameters.get("defVerId").trim().split("@");
                	if (newDefVerId.length  > 0) {
                		if (newDefVerId.length  > 1) {
                			String newVersionId = newDefVerId[1];
                            def.setId(newVersionId);

                            if (def == getProcessDefinitionDesigner().getActivity())
                                setDefinitionVersionId(newVersionId);
                		}
						String belongingDefId = newDefVerId[0];
						def.setBelongingDefinitionId(belongingDefId);

						if (def == getProcessDefinitionDesigner().getActivity())
							setDefinitionId(belongingDefId);
                	}
                	
                }
                
                setDocumentChanged(false);

                LoadedDefinition oldDef = null;
                if (currentloadedType == 0)
                    oldDef = getLoadedDefinition((String) parameters.get("definitionId"));
                else
                    oldDef = getLoadedDefinition((String) parameters.get("defVerId"));

                LoadedDefinition newDef = new LoadedDefinition(((ProcessDefinition) getProcessDefinitionDesigner().getActivity()).getId(), def.getName().getText(), 1);

                replaceDefinition(oldDef, newDef);

            }
        };

        progDlg.show();
        if (progDlg.getFailCause() != null)
            throw progDlg.getFailCause();

    }

    protected void generatePartnerProcess() {
        try {
            final ProcessDesigner finalThis = this;
            org.metaworks.Type saveDialog = new org.metaworks.Type("Select a partner role to generate it's process", new FieldDescriptor[] {
                    new FieldDescriptor("Role", new Object[] { "inputter", new org.uengine.processdesigner.inputters.RoleInput((ProcessDefinition) getProcessDefinitionDesigner().getActivity()) }),
                    new FieldDescriptor("Save Location", new Object[] { "inputter", new org.metaworks.inputter.FileInput() }) }) {

                public void save(final Instance rec) throws Exception {
                    final ProgressDialog progDlg = new ProgressDialog("generate...") {
                        public void run() throws Exception {
                            Role role = (Role) rec.getFieldValue("Role");
                            ProcessDefinition def = (ProcessDefinition) finalThis.getProcessDefinitionDesigner().getActivity();

                            FileOutputStream fo = new FileOutputStream(rec.getFieldValue("Save Location").toString());
                            Hashtable option = new Hashtable();
                            option.put("role", role);
                            GlobalContext.serialize(def, fo, "Invert", option);
                        }
                    };

                    progDlg.show();

                }

                public void update(Instance rec) throws Exception {
                    save(rec);
                }
            };

            InputForm inputForm = (new InputForm(saveDialog));
            inputForm.postInputDialog(this, "Confirm", "Confirm", saveDialog.getName());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // load functions----------------------------------------------------------

    public void loadDesign() {

        UnifiedResourcePicker picker = new UnifiedResourcePicker();
        picker.show();

        // if(!checkDiscardChanges()) return;
        int checked = checkDiscardChanges();
        if (checked == YesOrNoDialog.NO || checked == YesOrNoDialog.CANCEL)
            return;

        try {
            URL url = new java.net.URL(picker.getValue().toString());
            InputStream fi = url.openStream();
            loadDesign(fi, "XPD", false);

            if (url.getFile() != null) {
                setCurrentWorkingFile(url.getFile());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadDesign(InputStream fi, String fileType) {
        loadDesign(fi, fileType, true);

        // mr.heo
        ProcessDefinitionDesigner designer = this.getProcessDefinitionDesigner();
        if (!isRootDefinition())
            designer.SetVisiableButton(true);
        else
            designer.SetVisiableButton(false);

        addOpenedFile((ProcessDefinition) getProcessDefinitionDesigner().getActivity());

    }

    public void loadDesign(InputStream fi, String fileType, boolean changeSavingTargetInfo) {
        // deserialize
        try {
            Class cls = Class.forName("org.uengine.components.serializers." + fileType + "Serializer");
            Serializer se = (Serializer) cls.newInstance();
            System.out.println("Use this serializer : " + se);

            // ByteArrayOutputStream bao = new ByteArrayOutputStream();
            // UEngineUtil.copyStream(fi, bao);
            // fi = new ByteArrayInputStream(bao.toString("UTF-8").getBytes());

            // UEngineUtil.copyStream(fi, System.out);
            ProcessDefinition pd = (ProcessDefinition) se.deserialize(fi, null);

            loadDesign(pd, changeSavingTargetInfo);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void loadDesign(ProcessDefinition pd) {
        loadDesign(pd, true);
    }

    public void loadDesign(ProcessDefinition pd, boolean changeSavingTargetInfo) {
        if (checkDiscardChanges() == YesOrNoDialog.YES) {
            ProcessDefinitionDesigner pdd = (ProcessDefinitionDesigner) UEngineUtil.getComponentByEscalation(pd.getClass(), "designer");

            pdd.setActivity(pd);
            setProcessDefinitionDesigner(pdd);

            if (isAdhoc() && getProcessInstance() != null)
                pdd.setProcessInstance(getProcessInstance());

            setTitle(pd.getName() + " - " + GlobalContext.getLocalizedMessage("pd.window.title"));

            getDesignerPanel().revalidate();
            getDesignerPanel().validate();

            setDocumentChanged(false);

            if (changeSavingTargetInfo) {
                setDefinitionVersionId(pd.getId());
                setDefinitionId(pd.getBelongingDefinitionId());
            }
        }
    }

    public void importBPEL() {
        FileChooser chooser = new FileChooser();
        chooser.setDialogTitle("=> 1. Please select BPEL file :");
        int returnVal = chooser.showOpenDialog(this);
        if (returnVal != JFileChooser.APPROVE_OPTION)
            return;

        File procFile = chooser.getSelectedFile();

        chooser.setDialogTitle("==========> 2. Please select WSDL file :");
        returnVal = chooser.showOpenDialog(this);
        if (returnVal != JFileChooser.APPROVE_OPTION)
            return;

        File defFile = chooser.getSelectedFile();

        Hashtable option = new Hashtable();

        try {
            option.put("wsdl", new FileInputStream(defFile.getPath()));
            ProcessDefinition pd = (ProcessDefinition) GlobalContext.getSerializer("BPEL").deserialize(new FileInputStream(procFile.getPath()), option);
            setProcessDefinitionDesigner((ProcessDefinitionDesigner) pd.createDesigner());

            getDesignerPanel().invalidate();
            getDesignerPanel().validate();
            setCurrentWorkingPath(defFile.getPath());
        } catch (Exception e) {
            e.printStackTrace();
            // errDialog(e);
        }

    }

    // -----------------------------------------------------------------------------

    public void printDesign() {
        PrinterJob printJob = PrinterJob.getPrinterJob();
        printJob.setPrintable(this);

        if (printJob.printDialog()) {
            try {
                printJob.print();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    protected BufferedImage m_bi         = null;
    public int              m_maxNumPage = 1;

    public int print(Graphics g, PageFormat pf, int pi) throws PrinterException {

        if (pi >= 1) {
            return NO_SUCH_PAGE;
        }

        // g.translate((int)pf.getImageableX(), (int)pf.getImageableY());
        //
        // int wPage = (int)pf.getImageableWidth();
        // int hPage = (int)pf.getImageableHeight();
        //
        // int w = m_bi.getWidth(this);
        // int h = m_bi.getHeight(this);
        //
        // if (w == 0 || h == 0) return NO_SUCH_PAGE;
        //
        // int nCol = Math.max((int)Math.ceil((double)w/wPage), 1);
        // int nRow = Math.max((int)Math.ceil((double)h/hPage), 1);
        // m_maxNumPage = nCol*nRow;
        // int iCol = pi % nCol;
        // int iRow = pi / nCol;
        // int x = iCol*wPage;
        // int y = iRow*hPage;
        // int wImage = Math.min(wPage, w-x);
        // int hImage = Math.min(hPage, h-y);
        // g.drawImage(m_bi, 0, 0, wImage, hImage, x, y, x+wImage, y+hImage, this);
        // System.gc();

        getDesignerPanel().printAll(g);
        //
        return PAGE_EXISTS;
    }

    public Context getInitialContext() throws Exception {
        if (initialContext == null) {
            initialContext = new InitialContext();
        }

        return initialContext;
    }

    public void settings() {
        org.metaworks.Type settingsTable = new org.metaworks.Type("settings", new FieldDescriptor[] { new FieldDescriptor("property"), new FieldDescriptor("value") });

        settingsTable.getFieldDescriptor("property").setUpdatable(false);

        final GridApplication app = new GridApplication(settingsTable) {
            public void onClose() {
                Instance[] recs = getInstances();
                Properties settings = new Properties();

                for (int i = 0; i < recs.length; i++) {
                    String k = (String) recs[i].getFieldValue("property");
                    String v = (String) recs[i].getFieldValue("value");

                    if (k == null)
                        continue;
                    if (v == null)
                        v = "";
                    settings.setProperty(k, v);
                    System.setProperty(k, v);
                }

                try {
                    settings.list(System.out);
                    /*
                     * PrintStream out = new PrintStream(new FileOutputStream(GlobalContext.SETTING_FILE)); settings.list(out);
                     */

                    settings.store(new FileOutputStream(GlobalContext.SETTING_FILE), null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        app.runDialog(this);

        try {
            Properties settings = loadProperties();

            new File(GlobalContext.SETTING_FILE).mkdirs();

            settings.store(new FileOutputStream(GlobalContext.SETTING_FILE), null);

            for (Enumeration enumeration = settings.keys(); enumeration.hasMoreElements();) {
                String k = (String) enumeration.nextElement();
                Instance rec = settingsTable.createInstance();
                rec.setFieldValue("property", k);
                rec.setFieldValue("value", settings.get(k));
                app.addInstance(rec);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Properties loadProperties() throws Exception {
        Properties settings = new Properties();
        settings.load(new FileInputStream(GlobalContext.SETTING_FILE));

        if (!settings.containsKey(GlobalContext.PROPERTY_UENGINE_HOME_DIR))
            settings.setProperty(GlobalContext.PROPERTY_UENGINE_HOME_DIR, System.getProperty(GlobalContext.PROPERTY_UENGINE_HOME_DIR, "c:/uengine"));
        if (!settings.containsKey(GlobalContext.PROPERTY_JBOSS_HOME_DIR))
            settings.setProperty(GlobalContext.PROPERTY_JBOSS_HOME_DIR, System.getProperty(GlobalContext.PROPERTY_JBOSS_HOME_DIR, "c:/uengine/was"));
        if (!settings.containsKey(GlobalContext.PROPERTY_AXIS_WEBAPP_DIR))
            settings.setProperty(GlobalContext.PROPERTY_AXIS_WEBAPP_DIR, System.getProperty(GlobalContext.PROPERTY_AXIS_WEBAPP_DIR, "c:/uengine/was/server/default/deploy/axis.app"));
        if (!settings.containsKey(GlobalContext.PROPERTY_STUB_DIR))
            settings.setProperty(GlobalContext.PROPERTY_STUB_DIR, System.getProperty(GlobalContext.PROPERTY_STUB_DIR, "c:/uengine/src/stubs"));
        if (!settings.containsKey(GlobalContext.PROPERTY_ANT_PATH))
            settings.setProperty(GlobalContext.PROPERTY_ANT_PATH, System.getProperty(GlobalContext.PROPERTY_ANT_PATH, "ant.bat"));

        if (!settings.containsKey(GlobalContext.PROPERTY_TEMP_PATH))
            settings.setProperty(GlobalContext.PROPERTY_TEMP_PATH, System.getProperty(GlobalContext.PROPERTY_TEMP_PATH, "c:/uengine/temp"));

        for (Enumeration enumeration = settings.keys(); enumeration.hasMoreElements();) {
            String k = (String) enumeration.nextElement();
            String v = (String) settings.getProperty(k);
            System.setProperty(k, v);
        }

        return settings;
    }

    public void help() {
    	String bpm_host = GlobalContext.getPropertyString("bpm_host", "bpm.enkisoft.co.kr");
    	String bpm_port = GlobalContext.getPropertyString("bpm_port", "81");
    	
        openNativeBrowser("http://"+bpm_host+":"+bpm_port);
    }

    public void about() {
        JDialog aboutDlg = new JDialog(this, "About uEngine", true);
        aboutDlg.getContentPane().setLayout(new FlowLayout());

        aboutDlg.getContentPane().add(new JLabel("uEngine version " + GlobalContext.STR_UENGINE_VER + ", Copyleft (C) 2003-2017 uEngine.org"));
        aboutDlg.getContentPane().add(new JLabel("uEngine comes with ABSOLUTELY NO WARRANTY; for details"));
        aboutDlg.getContentPane().add(new JLabel("see the GNU General Public License.  This is free software, and you are welcome"));
        aboutDlg.getContentPane().add(new JLabel("to redistribute it under certain conditions of GNU GPL License."));

        aboutDlg.setSize(500, 150);

        Point p = this.getLocation();
        Dimension d = this.getSize();
        Dimension ad = aboutDlg.getSize();

        p = new Point(p.x + (d.width - ad.width) / 2, p.y + (d.height - ad.height) / 2);
        aboutDlg.setLocation(p);

        aboutDlg.show();
    }

    public void export2SVG() {
        /*
         * try { SVGGenerator.main(getProcessDefinitionDesigner().getComponent()); } catch (IOException e) {
         * e.printStackTrace(); }
         */
    }

    public void update() {
        UnifiedResourcePicker picker = new UnifiedResourcePicker();
        picker.setTitle("Component update...");
        picker.setTab("HTTP");
        picker.setValue("http://localhost:8082/html/uengine-web/update");
        picker.show();

        if (!picker.isConfirmed())
            return;
        try {
            String url = (String) picker.getValue();
            File localFile;

            if (!url.startsWith("file://")) {
                int BUF_SIZE = 65535;
                byte[] buffer = new byte[BUF_SIZE];

                java.net.URL urlURL = new java.net.URL(url);
                BufferedInputStream is = new BufferedInputStream(urlURL.openStream());
                String fileName = urlURL.getFile();
                fileName = fileName.substring(fileName.lastIndexOf('/') + 1);
                localFile = new File(fileName);
                localFile = localFile.getAbsoluteFile();
                FileOutputStream fo = new FileOutputStream(localFile);
                BufferedOutputStream fos = new BufferedOutputStream(fo);

                int iRead, iPos = 0;
                while ((iRead = is.read(buffer, 0, BUF_SIZE)) > 0) {
                    fos.write(buffer, 0, iRead);
                    iPos += iRead;
                }

                fos.close();
                fo.close();
                is.close();
            } else {
                localFile = new File(new java.net.URI(url));
            }

            AntToolDialog antTool = new AntToolDialog();
            antTool.setCommand("DeployActivityComponent -Dparam.component.file=\"" + localFile.getName() + "\" -Dparam.component.dir=\"" + localFile.getParent() + "\"");
            antTool.run();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void test() {
        ScriptConsole.main(new String[] {});
    }

    public static ClientProxy getClientProxy() {
        return proxy;
    }

    // /////////////////////// main ////////////////////////////////////

    public static void main(final String args[]) throws Exception {
 
    	final String args1[] = {"ko",
    	"23",
    	"101",
    	"309",
    	"<org.uengine.kernel.RevisionInfo>"+
    	  "<authorName>테스터</authorName>"+
    	  "<authorId>test</authorId>"+
    	  "<authorCompany>uengine</authorCompany>"+
    	  "<changeTime>"+
    	  "<time>1475028796021</time>"+
    	  "<timezone>GMT+09:00</timezone>"+
    	  "</changeTime>"+
    	  "<version>0</version>"+
    	  "</org.uengine.kernel.RevisionInfo>"
    	};
    	
        if (args.length > 0) {
            GlobalContext.setDefaultLocale(args[0].trim());
        } else {
            GlobalContext.setDefaultLocale(Locale.getDefault().getLanguage());
        }

        try {
            UIManager.setLookAndFeel(new SubstanceBusinessBlackSteelLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        try {
        	UIManager.setLookAndFeel(new SubstanceBusinessBlackSteelLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
        	e.printStackTrace();
        }
        
        /*try {
        	JFrame.setDefaultLookAndFeelDecorated(true);
        	UIManager.setLookAndFeel(new SubstanceBusinessBlackSteelLookAndFeel());
        	// new org.jvnet.substance.skin.SubstanceBusinessBlackSteelLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
        	e.printStackTrace();
        }*/

        try {
            loadProperties();
        } catch (Exception e) {
            System.out.println("failed to load properties: " + e.getMessage());
        }

        JRibbonFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);
        System.out.print(" args.length = [" + args.length + "]");

        for (int i = 0; i < args.length; i++) {
            System.out.print(" args[" + i + "] = (" + args[i] + ")");
        }

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                final ProcessDesigner pd = new ProcessDesigner();

                pd.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                pd.setExtendedState(pd.getExtendedState()|JFrame.MAXIMIZED_BOTH);
                pd.pack();
 
                propertyDialog.setBounds((int) (pd.getWidth()), (int) (pd.getHeight() ), (int) (pd.getWidth()), (int) (pd.getHeight()));
                pd.setVisible(true);

                try {
                    proxy = new ClientProxy();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                mainAction(args, pd);

                // We don't need to reload the cached files. Surely users should manage their changed files before deploying
                // them
                /*
                 * String currentEditingFilePath = System.getProperty(GlobalContext.PROPERTY_TEMP_PATH, "c:/uengine/temp");
                 * File[] updFiles = (new File(currentEditingFilePath)).listFiles(new FileFilter(){ public boolean accept(File
                 * pathname) { return pathname.getName().endsWith(".upd"); }} ); for(int i=0; i<updFiles.length; i++){
                 * ProcessDefinition def; try { def = (ProcessDefinition) GlobalContext.deserialize(new
                 * FileInputStream(updFiles[i]), ProcessDefinition.class); pd.addOpenedFile(def); } catch (Exception e) {
                 * e.printStackTrace(); } }
                 */
                // for test single instance service

                /*
                 * try { Thread.sleep(5000); } catch (InterruptedException e) { e.printStackTrace(); } pd.newActivation(new
                 * String[]{"701","984","12480" });
                 */}
        });

        // pd.viewRoleResolutionDlg();

    }

    public void lostOwnership(Clipboard clipboard, Transferable contents) {
    }

    public static void mainAction(String args[], final ProcessDesigner pd) {

        if (args.length > 0 && args[1].equals("@ADHOC")) {
            final String instanceId = args[2].trim();
            // final String definitionId = args[3].trim();

            pd.setAdhoc(true);
            pd.setInstanceId(instanceId);

            if (instanceId.length() > 0 && !instanceId.equals("null")) {

                new ProgressDialog("Loading...") {
                    public void run() throws Exception {

                        InputStream pis = proxy.showProcessDefinitionWithInstanceId(instanceId);
                        pd.loadDesign(pis, "XPD");

                    }

                    public void success() {
                        dispose();
                    }
                }.show();
            }

        } else if (args.length > 1) {
            pd.setAdhoc(false);
            pd.setInstanceId(null);

            final String folderId = args[1].trim().equals("null")?"":args[1].trim();
            final String definitionId = args[2].trim().equals("null")?"":args[2].trim();
            final String defVerId = args[3].trim().equals("null")?"":args[3].trim();
            final String superDefId = (args.length>5)?args[5].trim():"";

            try {
                final String authorInfo = args[4].trim();

                pd.setRevisionInfo((RevisionInfo) GlobalContext.deserialize(authorInfo, RevisionInfo.class));
                proxy.setComCode(pd.getRevisionInfo().getAuthorCompany());

            } catch (Exception e) {
                (new UEngineException("Fail to retrieve author information: " + e.getMessage(), e)).printStackTrace();
            }
            

            pd.setFolderId(folderId);
            pd.setDefinitionId(definitionId);
            pd.setDefinitionVersionId(defVerId);
            pd.setSuperDefId(superDefId);

            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            new Exception().printStackTrace(new PrintStream(bao));
            final String stackTrace = bao.toString();

            if (UEngineUtil.isNotEmpty(superDefId)) {
                new ProgressDialog("Loading...") {

                    String callerStackTrace = stackTrace;

                    public void run() throws Exception {
                        // pd.loadDesign(definitionId);
                        System.out.println("============= superDefId : " + superDefId);
                        InputStream is = proxy.showProcessDefinitionWithDefinitionId(superDefId);
                        pd.loadDesign(is, "XPD");
                    }

                    public void success() {
                        dispose();
                    }
                }.show();

                ((ProcessDefinition) pd.getProcessDefinitionDesigner().getActivity()).setVersion(0);
                pd.list.clear();

            } else {
                if (UEngineUtil.isNotEmpty(defVerId)) {
                    new ProgressDialog("Loading...") {

                        String callerStackTrace = stackTrace;

                        public void run() throws Exception {
                            InputStream is = proxy.showProcessDefinitionWithVersionId(defVerId);
                            pd.loadDesign(is, "XPD");
                        }

                        public void success() {
                            dispose();
                        }
                    }.show();

                    pd.list.clear();
                    pd.addDefinition(new LoadedDefinition(defVerId, "test", 1));

                } else {
                    pd.loadDesign(ProcessDefinition.create()); // new document
                }
            }
        }
    }

    // / undo/redo framwork ///////////////////////////

    public boolean undo() {
        if (isUndoable()) {
            try {
                ProcessDefinition currDefinition = (ProcessDefinition) getProcessDefinitionDesigner().getActivity();

                ProcessDefinition restoredDefinition = getDefinitionForUndo();

                setProcessDefinitionDesigner((ProcessDefinitionDesigner) restoredDefinition.createDesigner());
                addUndoListener(restoredDefinition);

                setDefinitionForUndo(null);
                setDefinitionForRedo((ProcessDefinition) currDefinition.clone());

                return true;
            } catch (Exception e) {
            }
        }

        return false;
    }

    public boolean isUndoable() {
        return getDefinitionForUndo() != null;
    }

    public boolean redo() {
        if (isRedoable()) {
            try {
                ProcessDefinition currDefinition = (ProcessDefinition) getProcessDefinitionDesigner().getActivity();

                ProcessDefinition restoredDefinition = getDefinitionForRedo();

                setProcessDefinitionDesigner((ProcessDefinitionDesigner) restoredDefinition.createDesigner());
                addUndoListener(restoredDefinition);

                setDefinitionForUndo((ProcessDefinition) currDefinition.clone());
                setDefinitionForRedo(null);

                return true;
            } catch (Exception e) {
            }
        }

        return false;
    }

    public boolean isRedoable() {
        return getDefinitionForRedo() != null;
    }

    private long thread_When_The_Undo_Signaled = -1;

    public void saveDefinitionForUndo() {
        try {
            if (thread_When_The_Undo_Signaled == Thread.currentThread().getId())
                return; // allow only one time

            thread_When_The_Undo_Signaled = Thread.currentThread().getId();
            ProcessDefinition currDefinition = (ProcessDefinition) getProcessDefinitionDesigner().getActivity();
            setDefinitionForUndo((ProcessDefinition) currDefinition.clone());
        } catch (Exception e) {
        }
    }

    ArrayList list = new ArrayList();
    int       currentloadedType;

    public void addDefinition(Object obj) {
        list.add(obj);
        int type = ((LoadedDefinition) obj).getType();
        if (type == 0 || type == 1)
            this.currentloadedType = type;

    }

    public void replaceDefinition(Object oldObj, Object newObj) {
        if (list.contains(oldObj))
            list.set(list.indexOf(oldObj), newObj);
    }

    public LoadedDefinition getLoadedDefinition(String id) {
        for (int i = 0; i < list.size(); i++) {
            String defid = ((LoadedDefinition) list.get(i)).getID();
            if (defid.equalsIgnoreCase(id))
                return (LoadedDefinition) list.get(i);
        }

        return null;
    }

    public boolean isRootDefinition() {
        if (list.size() == 0 || list.size() == 1)
            return true;

        return false;
    }

    public String getParentDefinitionNameForToolTip() {
        // if(list==null) return EMPTY_STRING;
        if (list.size() > 1)
            return ((LoadedDefinition) list.get(list.size() - 1)).getName();
        return EMPTY_STRING;
    }

    public void loadParentDefinition() {
        if (list.size() > 1)
            list.remove(list.size() - 1);
        else
            return;

        LoadedDefinition def = (LoadedDefinition) list.get(list.size() - 1);
        final String value = def.getID();
        if (def.getType() == 0) {
            new ProgressDialog("Loading...") {
                public void run() throws Exception {
                    InputStream is = proxy.showProcessDefinitionWithDefinitionId(value);
                    ProcessDesigner.getInstance().loadDesign(is, "XPD");
                }

                public void success() {
                    dispose();
                }
            }.show();
        } else {
            new ProgressDialog("Loading...") {
                public void run() throws Exception {
                    InputStream is = proxy.showProcessDefinitionWithVersionId(value);// new
                                                                                     // URL(UEngineUtil.getWebServerBaseURL() +
                                                                                     // URL_showProcessDefinitionJSPWithVersionId
                                                                                     // + definitionId).openStream();
                    ProcessDesigner.getInstance().loadDesign(is, "XPD");
                }

                public void success() {
                    dispose();
                }
            }.show();
        }

    }

    public void openNativeBrowser(String url) {
        // not the browser itself is started, i only call something like
        // start http://www.javasoft.com
        // and then the standardbrowser will be started ...
        StringBuffer call = new StringBuffer();
        System.err.println(System.getProperty("os.name"));
        System.err.println(System.getProperty("user.dir"));

        try {
            // which OS ?
            String operatingSystem = System.getProperty("os.name");

            // how to call the OS
            if (operatingSystem.toLowerCase().indexOf("windows") > -1)
                call.append("cmd /c start ").append(url);
            else if (operatingSystem.toLowerCase().indexOf("mac") > -1)
                call.append("open ").append(url + " &");
            else if (operatingSystem.toLowerCase().indexOf("linux") > -1)
                // use Script 'netscape'
                call.append("mozilla ").append(url).append(" &");
            else
                call.append("netscape ").append(url).append(" &");
            System.err.println(call.toString());
            // start it ...
            Runtime.getRuntime().exec(call.toString());

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    // /////////////////////////////////////////////////
    public String getDefinitionId() {
        return definitionId;
    }

    public void setDefinitionId(String definitionId) {
        this.definitionId = definitionId;
    }

    public String getDefinitionVersionId() {
        return defVerId;
    }

    public void setDefinitionVersionId(String defVerId) {
        this.defVerId = defVerId;
    }

    public String getFolderId() {
        return folderId;
    }

    public void setFolderId(String folderId) {
        this.folderId = folderId;
    }

    public String getSuperDefId() {
        return superDefId;
    }

    public void setSuperDefId(String superDefId) {
        this.superDefId = superDefId;
    }

    public JDialog getPropertyDialog() {
        return propertyDialog;
    }

    public void setPropertyDialog(JDialog propertyDialog) {
        this.propertyDialog = propertyDialog;
    }

    public RevisionInfo getRevisionInfo() {
        return revisionInfo;
    }

    public void setRevisionInfo(RevisionInfo revisionInfo) {
        this.revisionInfo = revisionInfo;
    }

    public ArrayList getList() {
        return list;
    }

    public void setList(ArrayList list) {
        this.list = list;
    }

    public JToggleButton getModelingTabBtn() {
        return modelingTabBtn;
    }

    public void setModelingTabBtn(JToggleButton modelingTabBtn) {
        this.modelingTabBtn = modelingTabBtn;
    }

    public JToggleButton getSimulateTabBtn() {
        return simulateTabBtn;
    }

    public void setSimulateTabBtn(JToggleButton simulateTabBtn) {
        this.simulateTabBtn = simulateTabBtn;
    }

    public JPanel getAskMePanel() {
        return askMePanel;
    }

    public void setAskMePanel(JPanel askMePanel) {
        this.askMePanel = askMePanel;
    }

    public JPanel getThreeActionsPanel() {
        return threeActionsPanel;
    }

    public void setThreeActionsPanel(JPanel threeActionsPanel) {
        this.threeActionsPanel = threeActionsPanel;
    }

    private static String createTempFilePath(String alias, String version) {
        String currentEditingFilePath = System.getProperty(GlobalContext.PROPERTY_TEMP_PATH, "c:/uengine/temp");
        new File(currentEditingFilePath).mkdirs();
        currentEditingFilePath = currentEditingFilePath + "/" + alias + "." + version + ".upd";

        return currentEditingFilePath;
    }

    private static String createTempFilePath(ProcessDefinition definition) {
        return createTempFilePath(definition.getAlias(), "" + definition.getVersion());
    }

    private static String createTempFilePath(FileLoadButton fileLoadBtn) {
        return createTempFilePath(fileLoadBtn.getAlias(), fileLoadBtn.getVersion());
    }

    // implement for SingleInstanceListener (Java Web Start single application launch support)
    public void newActivation(String[] arg0) {
        ProcessDefinition currentlyEditingPD = (ProcessDefinition) getProcessDefinitionDesigner().getActivity();

        String currentEditingFilePath = createTempFilePath(currentlyEditingPD);

        // save currently being edited
        try {
            GlobalContext.serialize(currentlyEditingPD, new FileOutputStream(currentEditingFilePath), ProcessDefinition.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();

            File currentEditingPDFile = new File(currentEditingFilePath);
            currentEditingPDFile.delete();// (new File(currentEditingPDFile.getAbsolutePath() + ".undeletable.upd"));

            System.out.println("Retry to generate file ..." + currentEditingPDFile.getAbsolutePath());

            try {
                GlobalContext.serialize(currentlyEditingPD, new FileOutputStream(currentEditingFilePath), ProcessDefinition.class);
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (Exception e1) {
                e1.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            currentlyEditingPD = null; // it is very meaningful since we need to clear the memory immediately as soon as
                                       // possible
            mainAction(arg0, this);

            currentlyEditingPD = (ProcessDefinition) getProcessDefinitionDesigner().getActivity();

        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    class FileLoadButton extends JLabel {

        String  alias;
        String  version;
        boolean isAdhoc;
        String  instanceId;

        public FileLoadButton(ProcessDefinition pd) {
            super(pd.getName() + " v" + pd.getVersion(), DesignerLabel.getImageIcon("org/uengine/kernel/images/error_red"), JLabel.LEFT);

            setAlias(pd.getAlias());
            setVersion("" + pd.getVersion());
            setAdhoc(ProcessDesigner.this.isAdhoc());
            setInstanceId(ProcessDesigner.this.getInstanceId());
        }

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public boolean isAdhoc() {
            return isAdhoc;
        }

        public void setAdhoc(boolean isAdhoc) {
            this.isAdhoc = isAdhoc;
        }

        public String getInstanceId() {
            return instanceId;
        }

        public void setInstanceId(String instanceId) {
            this.instanceId = instanceId;
        }

    }

    FileLoadButton oldFileLoadButton = null;

    protected void addOpenedFile(ProcessDefinition currentlyEditingPD) {

        final FileLoadButton newFileBtn = new FileLoadButton(currentlyEditingPD);

        refreshNewFileButton(newFileBtn);

        newFileBtn.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                String alias = newFileBtn.getText();
                try {

                    boolean isCloseAction = (e.getX() < 20);// )(newFileBtn.getSize().width - e.getX() < 20);

                    if (isCloseAction) {

                        (new File(createTempFilePath(newFileBtn))).delete();

                        openedFilesPanel.remove(newFileBtn);
                        openedFilesPanel.getParent().repaint();
                        openedFilesPanel.revalidate();
                        openedFilesPanel.invalidate();
                    } else {
                        ProcessDefinition currentlyEditingPD = (ProcessDefinition) getProcessDefinitionDesigner().getActivity();

                        GlobalContext.serialize(currentlyEditingPD, new FileOutputStream(createTempFilePath(currentlyEditingPD)), ProcessDefinition.class);

                        currentlyEditingPD = null; // it is very meaningful since we need to clear the memory immediately as
                                                   // soon as possible

                        currentlyEditingPD = (ProcessDefinition) GlobalContext.deserialize(new FileInputStream(createTempFilePath(newFileBtn)), ProcessDefinition.class);

                        setDocumentChanged(false);
                        setDefinitionForRedo(null);
                        setDefinitionForUndo(null);

                        loadDesign(currentlyEditingPD);

                        setAdhoc(newFileBtn.isAdhoc);
                        setInstanceId(newFileBtn.getInstanceId());

                        refreshNewFileButton(newFileBtn);
                    }
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }

        });
        openedFilesPanel.add(newFileBtn);
        openedFilesPanel.revalidate();

    }

    private void refreshNewFileButton(FileLoadButton newFileBtn) {
        if (oldFileLoadButton != null) {
            Font font = new Font(GlobalContext.getLocalizedMessage("pd.font"), 0, 10);
            oldFileLoadButton.setFont(font);

        }

        Font font = new Font(GlobalContext.getLocalizedMessage("pd.font"), 3, 10);
        newFileBtn.setFont(font);

        oldFileLoadButton = newFileBtn;

    };

}

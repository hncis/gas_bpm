package org.uengine.processdesigner.mapper;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import org.metaworks.InputForm;
import org.metaworks.Instance;
import org.metaworks.ObjectInstance;
import org.metaworks.ObjectType;
import org.uengine.kernel.GlobalContext;
import org.uengine.processdesigner.ProcessDesigner;
import org.uengine.processdesigner.framework.Movable;

import com.webdeninteractive.xbotts.Mapping.compiler.Linkable;

public class TransformerDesigner extends JPanel{
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = GlobalContext.SERIALIZATION_UID;
	
	JLabel titleLabel;
    final JPopupMenu popupMenu = new JPopupMenu();

	public transient boolean isDeleted;

	public TransformerDesigner(){
		super(new BorderLayout());
		
        Movable mouseListener = new Movable(this){
            boolean showMenu = false;

			public void mouseDragged(MouseEvent arg0) {
				super.mouseDragged(arg0);
//				transformer.getLinkPanel().revalidate();
//				transformer.getLinkPanel().updateUI();
				transformer.getLinkPanel().repaint();
			}

			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==2){
					try {
						
						final Vector oldArgumentNames = new Vector();
						oldArgumentNames.add(getTransformer().getInputArguments());
						oldArgumentNames.add(getTransformer().getOutputArguments());

						ObjectType type = new ObjectType(getTransformer().getClass()){

							@Override
							public void save(Instance rec) {

								String[] oldInputArgNames = (String[]) oldArgumentNames.get(0);
								String[] oldOutputArgNames = (String[]) oldArgumentNames.get(1);
								
								Hashtable oldLinksIn = new Hashtable();
								for(int i=0; i<oldInputArgNames.length; i++){
									TransformerArgument ta = getTransformer().getTransferArgument(oldInputArgNames[i]);
									oldLinksIn.put(oldInputArgNames[i], ta.getHardLinkSources());
									
									Linkable[] sources = ta.getHardLinkSources();
									for(int j=0; j<sources.length; j++){
										getTransformer().getLinkPanel().removeLink(sources[j], ta);
									
										for(int k=0; k<sources[j].getHardLinkTargets().length; k++){
											sources[j].removeHardLinkTarget(sources[j].getHardLinkTargets()[k]);
										}
										
									}
									
								}
								
								Hashtable oldLinksOut = new Hashtable();
								if(oldOutputArgNames!=null)
								for(int i=0; i<oldOutputArgNames.length; i++){
									TransformerArgument ta = getTransformer().getTransferArgument(oldOutputArgNames[i]);
									if(ta==null) continue;
									
									oldLinksOut.put(oldOutputArgNames[i], ta.getHardLinkTargets());

									Linkable[] targets= ta.getHardLinkTargets();
									for(int j=0; j<targets.length; j++){
										getTransformer().getLinkPanel().removeLink(ta, targets[j]);
									}
								}

								
								//rearrange all the elements in the Transformer Designer panel
								setTransformer(getTransformer());

								for(int i=0; i<getTransformer().getInputArguments().length; i++){
									String argName = getTransformer().getInputArguments()[i];
									if(oldLinksIn.containsKey(argName)){
										Linkable[] sources = (Linkable[]) oldLinksIn.get(argName);
										for(int j=0; j<sources.length; j++){
											TransformerArgument ta = getTransformer().getTransferArgument(argName);

											getTransformer().getLinkPanel().addLink(sources[j], ta);
										}
									}
								}
								
								for(int i=0; i<getTransformer().getOutputArguments().length; i++){
									String argName = getTransformer().getOutputArguments()[i];
									if(oldLinksOut.containsKey(argName)){
										Linkable[] targets = (Linkable[]) oldLinksOut.get(argName);
										for(int j=0; j<targets.length; j++){
											TransformerArgument ta = getTransformer().getTransferArgument(argName);

											getTransformer().getLinkPanel().addLink(ta, targets[j]);
										}
									}
								}
								
								setSize(getPreferredSize());
							}

							@Override
							public void update(Instance rec) {
								save(rec);
							}
							
						};
						
						ObjectInstance transformerInstance = new ObjectInstance(type, getTransformer());
						
						InputForm inputForm = new InputForm(type);
						
						inputForm.setInstance(transformerInstance);
						
						inputForm.postInputDialog(ProcessDesigner.getInstance());
						
						//inputForm.getInstance();
						
						
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					
					//new InputForm()
				}else if(e.getButton()==3 && e.getClickCount()==2){
					
					/**
					 * ultra hard code but readable enough ;)
					 */
					if(getSize().width==10){
						setSize(getPreferredSize());
					}else{
						setSize(10,10);
					}
					
					revalidate();
				}

				   if(e.isPopupTrigger()|| e.getModifiers() == 4){
	                    showMenu=true;
	                }
	                if(showMenu){
	                    popupMenu.show(TransformerDesigner.this,e.getX(), e.getY( ));
//	                    LinkPanel.this.lastX = e.getX( );
//	                    LinkPanel.this.lastY = e.getY( );
	                    showMenu=false;
	                }
			}
        	
			
        };
        
        addMouseMotionListener(mouseListener);
        addMouseListener(mouseListener);
        
        
		JMenuItem deleteMI = new JMenuItem("Delete");
		deleteMI.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				
				Transformer transformer = getTransformer();
				
				for(int i=0; i<transformer.getInputArguments().length; i++){
					String argName = transformer.getInputArguments()[i];
					TransformerArgument ta = getTransformer().getTransferArgument(argName);

					
					for(int j=0; j<ta.getHardLinkSources().length; j++){
						Linkable source = ta.getHardLinkSources()[j];

                        getTransformer().getLinkPanel().removeLink(source, ta);

					}
				
				}

				for(int i=0; i<transformer.getOutputArguments().length; i++){
					String argName = transformer.getOutputArguments()[i];
					TransformerArgument ta = getTransformer().getTransferArgument(argName);
					
					for(int j=0; j<ta.getHardLinkTargets().length; j++){
						Linkable target = ta.getHardLinkTargets()[j];

                        getTransformer().getLinkPanel().removeLink(ta, target);
					}
				}
				
				transformer.getLinkPanel().remove(transformer.getDesigner());
				
				isDeleted = true;
				
				revalidate();

			}
			
		});		
		popupMenu.add(deleteMI);

	}
	
	Transformer transformer;
		public Transformer getTransformer() {
			return transformer;
		}
		public void setTransformer(Transformer transformer) {
			this.transformer = transformer;
			
			removeAll();
			
			transformer.transformerArgumentsByName = new HashMap();
			
			add("North", titleLabel = new JLabel());
			
			titleLabel.setText(transformer.getName());
			
			JPanel inputArgumentPanel = new JPanel(new FlowLayout());
			inputArgumentPanel.setLayout(new BoxLayout(inputArgumentPanel, BoxLayout.Y_AXIS));
			
			String[] inputArguments = transformer.getInputArguments();
			if(inputArguments!=null)
			for(int i=0; i<inputArguments.length; i++){
				TransformerArgument ta = new TransformerArgument();
				ta.setInputArgument(true);
				ta.setName(inputArguments[i]);
				ta.setTransformer(transformer);
				inputArgumentPanel.add(ta);
			}
			add("West", inputArgumentPanel);

			JPanel outputArgumentPanel = new JPanel();
			outputArgumentPanel.setLayout(new BoxLayout(outputArgumentPanel, BoxLayout.Y_AXIS));

			String[] outputArguments = transformer.getOutputArguments();
			if(outputArguments!=null)
			for(int i=0; i<outputArguments.length; i++){
				TransformerArgument ta = new TransformerArgument();
				ta.setInputArgument(false);
				ta.setName(outputArguments[i]);
				ta.setTransformer(transformer);
				
				//locate the output argument right
				ta.setAlignmentX(Component.RIGHT_ALIGNMENT);
				outputArgumentPanel.add(ta);
			}
			add("East", outputArgumentPanel);
			
			add("Center", new JLabel(" "));
			
			revalidate();
			if(getParent()!=null)
				getParent().validate();
		}

	public void paint(Graphics g) {
		super.paint(g);
		
		Graphics2D g2 = (Graphics2D) g;
		Stroke stroke = new BasicStroke(1, 1, 1, 1, new float[]{4f,2f}, 3);
		g2.setStroke(stroke);
		g2.setColor(new Color(0, 0, 0));
		g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 8, 8);
	}
}

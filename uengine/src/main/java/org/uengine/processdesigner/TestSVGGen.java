package org.uengine.processdesigner;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.RepaintManager;

import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.dom.svg.SVGDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.uengine.kernel.GlobalContext;
import org.uengine.kernel.HumanActivity;
import org.uengine.kernel.ProcessDefinition;
import org.uengine.kernel.SwitchActivity;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

public class TestSVGGen {

    public void paint(Graphics2D g2d) {
        g2d.setPaint(Color.red);
        g2d.fill(new Rectangle(10, 10, 100, 100));
    }

    public static void main(String[] args) throws Exception {
    	
    	ProcessDefinition sampleProcDef = new ProcessDefinition();
    	sampleProcDef = (ProcessDefinition) GlobalContext.deserialize(new FileInputStream(args[0]), ProcessDefinition.class);
    	ActivityDesigner actDesigner = sampleProcDef.createDesigner();
    	
    	SwitchActivity switchAct = new SwitchActivity();
    	switchAct.addChildActivity(new HumanActivity());
    	switchAct.addChildActivity(new HumanActivity());
    	
    	JPanel panel = new JPanel(new BorderLayout());
    	Component switchDesigner = switchAct.createDesigner().getComponent();
    	panel.add("Center", switchDesigner);
    	panel.setPreferredSize(new Dimension(300,200));
    	JFrame frame = new JFrame();
    	frame.getContentPane().setLayout(new BorderLayout());
    	frame.getContentPane().add("Center", panel);

        // Get a DOMImplementation.
        DOMImplementation domImpl =
            GenericDOMImplementation.getDOMImplementation();

        // Create an instance of org.w3c.dom.Document.
        String svgNS = "http://www.w3.org/2000/svg";
        Document document = domImpl.createDocument(svgNS, "svg", null);

        // Create an instance of the SVG Generator.
        SVGGraphics2D svgGenerator = new SVGGraphics2D(document);

        // Ask the test to render into the SVG Graphics2D implementation.
        //TestSVGGen test = new TestSVGGen();
        switchDesigner.paint(svgGenerator);

        // Finally, stream out SVG to the standard output using
        // UTF-8 encoding.
        boolean useCSS = true; // we want to use CSS style attributes
        Writer out = new OutputStreamWriter(new FileOutputStream("c:\\uengine\\test.svg"), "UTF-8");
        svgGenerator.stream(out, useCSS);
        
        System.exit(0);
    }
    
    public static void export2SVG(JComponent componentToExport, FileOutputStream fao) throws Exception {
    		
////        // Get a DOMImplementation.
////        DOMImplementation domImpl =
////            GenericDOMImplementation.getDOMImplementation();
////        // Create an instance of org.w3c.dom.Document.
////        String svgNS = "http://www.w3.org/2000/svg";
////        Document document = domImpl.createDocument(svgNS, "svg", null);
//
//        DOMImplementation impl = SVGDOMImplementation.getDOMImplementation();
//        String svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;
//        SVGDocument document = (SVGDocument) impl.createDocument(svgNS, "svg", null);
//
//        // Create an instance of the SVG Generator.
//        SVGGraphics2D svgGenerator = new SVGGraphics2D(document);
//
//        try{
//        	
//        	disableDoubleBuffering(componentToExport);
//
//	        componentToExport.paint(svgGenerator);
//	        
//	        enableDoubleBuffering(componentToExport);
////	        SwingSVGPrettyPrint.print(componentToExport, svgGenerator);
//	
//	        // Populate the document root with the generated SVG content.
////	        Element root = document.getDocumentElement();
////	        svgGenerator.getRoot(root);
//
//	        
////			JSVGCanvas canvas = new JSVGCanvas();
////			canvas.setPreferredSize(new Dimension(800,600));
////	        JFrame f = new JFrame();
////	        f.getContentPane().setLayout(new BorderLayout());
////	        f.getContentPane().add("Center", canvas);
////	        canvas.setSVGDocument(document);
////	        f.pack();
////	        f.setVisible(true);
//
//	        // Finally, stream out SVG to the standard output using
//	        // UTF-8 encoding.
//	        boolean useCSS = false; // we want to use CSS style attributes
//	        Writer out = new OutputStreamWriter(fao, "UTF-8");
//	        svgGenerator.stream(out, useCSS);
//	        out.flush();
//	        out.close();
//
//    	}catch(Exception e){
//    		throw e;
//    	}finally{
//    		svgGenerator.dispose(); 
//    		svgGenerator = null;
//    	}
        
    }
    
    public static void disableDoubleBuffering(Component c) {
        RepaintManager currentManager = RepaintManager.currentManager(c);
        currentManager.setDoubleBufferingEnabled(false);
      }

      public static void enableDoubleBuffering(Component c) {
        RepaintManager currentManager = RepaintManager.currentManager(c);
        currentManager.setDoubleBufferingEnabled(true);
      }
      
}


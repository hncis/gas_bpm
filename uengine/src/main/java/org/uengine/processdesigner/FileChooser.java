package org.uengine.processdesigner;

import javax.swing.*;
import java.io.*;

public class FileChooser extends JFileChooser{

	FileChooser(){
		super();
		
		setMultiSelectionEnabled( false);
		setCurrentDirectory( new File( "./dcwf/admin/designer/files/"));
		
		javax.swing.filechooser.FileFilter temp = this.getFileFilter();
		
		addChoosableFileFilter( new ExampleFileFilter( "Bean","Bean"));
		addChoosableFileFilter( new ExampleFileFilter( "Binary","Binary"));
		addChoosableFileFilter( new ExampleFileFilter( "BPML","BPML"));
		addChoosableFileFilter( new ExampleFileFilter( "BPEL","BPEL"));
		
		setFileFilter( temp);
	}
}
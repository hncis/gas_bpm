package org.uengine.processdesigner;

import java.awt.event.*;
import java.awt.*;

/**
 * @author Jinyoung Jang
 */

public class KeyListenerTransferrer implements KeyListener{

	Component transferredComp;

	public KeyListenerTransferrer(Component comp){
		transferredComp = comp;
	}

// methods implement KeyListener
	public synchronized void keyPressed(KeyEvent e){
		e.setSource(transferredComp);
		
		KeyListener[] listeners = transferredComp.getKeyListeners();
			
		for(int i=0; i<listeners.length; i++)
			listeners[i].keyPressed(e);
	}
	
	public synchronized void keyTyped(KeyEvent e){
		e.setSource(transferredComp);
		
		KeyListener[] listeners = transferredComp.getKeyListeners();
			
		for(int i=0; i<listeners.length; i++)
			listeners[i].keyTyped(e);
	}	
	
	public synchronized void keyReleased(KeyEvent e){
		e.setSource(transferredComp);
		
		KeyListener[] listeners = transferredComp.getKeyListeners();
			
		for(int i=0; i<listeners.length; i++)
			listeners[i].keyReleased(e);
	}

// end


}
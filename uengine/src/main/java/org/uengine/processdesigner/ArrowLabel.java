package org.uengine.processdesigner;

import java.awt.Dimension;

/**
 * @author Jinyoung Jang
 */

public class ArrowLabel extends PlaceHolder{

	public final static int DEFAULT_WIDTH = 30;
	public final static int DEFAULT_HEIGHT = 20;
	
	public ArrowLabel(){
		super(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT), false);
	}
}
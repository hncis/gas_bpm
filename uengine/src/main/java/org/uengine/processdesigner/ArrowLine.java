package org.uengine.processdesigner;

import java.awt.*;
import java.awt.event.*;

/**
 * @author Jinyoung Jang
 */

public class ArrowLine extends Component implements ComponentListener{

	public double x0;
	public double y0;
	public double x1;
	public double y1;
	private Color lineColor;
	
	Component start;
	Component end;
		public Component getStartComponent(){
			return start;
		}
		public Component getEndComponent(){
			return end;
		}
	
	public ArrowLine(Component start, Component end){
		super();
		
		lineColor = Color.blue;
		
		this.start= start;
		this.end = end;
		
		start.addComponentListener(this);
		end.addComponentListener(this);
	}
	
	public void setColor( Color lineColor){
		this.lineColor = lineColor;
	}
	
	public void paint(Graphics g){
		super.paint( g);
		
		g.setColor( lineColor);
		
		//g.drawLine( 0, 0, (int)getSize().width, (int)getSize().height);
		
		//g.drawLine( (int)x0, (int)y0, (int)x1, (int)y1);
		double l = 1;           //L:선의 길이(끝에서 화살표 꼭지점까지) 초기화
		l = Math.sqrt((x1 - x0) * (x1 - x0) + (y1 - y0) * (y1 - y0)); //h:꼭지점에서 AX나 BX의 X좌표
		double h = 10;         //화살표 도착 지점의 삼각형의 크기
		
		double AX = 1.0f / 2.0f / l * (2 * h * x0 + 2 * (l - h) * x1 - h * y0 + h * y1);
		double AY = 1.0f / 2.0f / l * (h * x0 - h * x1 + 2 * h * y0 + 2 * (l - h) * y1);
		double BX = 1.0f / 2.0f / l * (2 * h * x0 + 2 * (l - h) * x1 + h * y0 - h * y1);
		double By = 1.0f / 2.0f / l * (-1 * h * x0 + h * x1 + 2 * h * y0 + 2 * (l - h) * y1);
		
		g.drawLine( (int)x0, (int)y0, (int)x1, (int)y1);
		//g.setColor(Color.red);
		g.drawLine( (int)AX, (int)AY, (int)x1, (int)y1);
		//g.setColor(Color.gray);
		g.drawLine ( (int)x1, (int)y1, (int)BX, (int)By);
		

	}
	
	public void drawLine( int x0, int y0,int x1, int y1){
		
		//System.out.println("ArrowLine :" + x0 + ", " + y0 + ", " + x1 + ", " + y1);
		
		int x, y, w, h;
		
		if( x0 < x1){
			x = x0;
			w = x1 - x0;
		}else{
			x = x1;
			w = x0 - x1;
		}
		
		if( y0 < y1){
			y = y0;
			h = y1 - y0;
		}else{
			y = y1;
			h = y0 - y1;
		}
		
		setLocation( x-5, y-5);
		setSize( w + 10, h + 10);
		
		
		if( x0 < x1 && y0 < y1){
			this.x0 = 0;
			this.y0 = 0;
			this.x1 = w;
			this.y1 = h;
		}else if( x0 > x1 && y0 > y1){
			this.x0 = w;
			this.y0 = h;
			this.x1 = 0;
			this.y1 = 0;
		}else if( x0 < x1 && y0 > y1){
			this.x0 = 0;
			this.y0 = h;
			this.x1 = w;
			this.y1 = 0;
		}else if( x0 > x1 && y0 < y1){
			this.x0 = w;
			this.y0 = 0;
			this.x1 = 0;
			this.y1 = h;
		}else if( x0 < x1 && y0 == y1){
			this.x0 = 0;
			this.y0 = 0;
			this.x1 = x1;
			this.y1 = 0;
		}else if( x0 > x1 && y0 == y1){
			this.x0 = x0;
			this.y0 = 0;
			this.x1 = 0;
			this.y1 = 0;
		}else if( x0 == x1 && y0 < y1){
			this.x0 = 0;
			this.y0 = 0;
			this.x1 = 0;
			this.y1 = y1;
		}else if( x0 == x1 && y0 > y1){
			this.x0 = 0;
			this.y0 = y0;
			this.x1 = 0;
			this.y1 = 0;
		}

		this.x0 = this.x0+5;
		this.y0 = this.y0+5;
		this.x1 = this.x1+5;
		this.y1 = this.y1+5;
						
		this.repaint() ;
	}
	
	
///////// Methods implements ComponentListener 

	public void componentHidden(ComponentEvent e){
	  //Invoked when the component has been made invisible. 
	}
	public void componentMoved(ComponentEvent e){
	  //Invoked when the component's position changes. 
	  	Point startPoint = start.getLocation();
	  	Point endPoint = end.getLocation();
	  	
System.out.println("startPoint : " + startPoint);
System.out.println("endingPoint : " + endPoint);
	  	   	
	  	drawLine((int)startPoint.getX(), (int)startPoint.getY(), (int)endPoint.getX(), (int)endPoint.getY());
	}
	public void componentResized(ComponentEvent e){
	  //Invoked when the component's size changes. 
	}
	public void componentShown(ComponentEvent e){
	}

}
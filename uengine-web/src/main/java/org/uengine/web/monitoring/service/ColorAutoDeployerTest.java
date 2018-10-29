package org.uengine.web.monitoring.service;
import java.lang.reflect.Field;

import be.ceau.chart.color.Color;


public class ColorAutoDeployerTest {

	public final String color0 = "#341f97";
	public final String color1 = "#1abc9c";
	public final String color2 = "#f1c40f";
	public final String color3 = "#3498db";
	public final String color4 = "#e74c3c";
	public final String color5 = "#2ecc71";
	public final String color6 = "#e67e22";
	public final String color7 = "#9b59b6";
	public final String color8 = "#bdc3c7";
	public final String color9 = "#34495e";
	public final String color10 = "#7f8c8d";
	public final String color11 = "#c0392b";
	public final String color12 = "#FEA47F";
	public final String color13 = "#25CCF7";
	public final String color14 = "#EAB543";
	public final String color15 = "#CAD3C8";
	public final String color16 = "#F97F51";
	public final String color17 = "#1B9CFC";
	public final String color18 = "#F8EFBA";
	public final String color19 = "#58B19F";
	public final String color20 = "#2C3A47";
	public final String color21 = "#B33771";
	public final String color22 = "#3B3B98";
	public final String color23 = "#FD7272";
	public final String color24 = "#9AECDB";
	public final String color25 = "#6D214F";
	public final String color26 = "#182C61";
	public final String color27 = "#FC427B";
	public final String color28 = "#BDC581";
	public final String color29 = "#82589F";
	
	// getColor(i%30)
	
	
	public String getColor(int counter) throws IllegalArgumentException, IllegalAccessException{
		String colorName = "color";
		colorName =  colorName.concat(String.valueOf(Math.abs(counter%30)));
		ColorAutoDeployerTest cadt = new ColorAutoDeployerTest();
		Field fd = null;
		try {
			fd = cadt.getClass().getDeclaredField(colorName);
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return (String)fd.get(cadt);
	}
	
	public Color transColor(String colorStr){
		 return new Color(
		            Integer.valueOf( colorStr.substring( 1, 3 ), 16 ),
		            Integer.valueOf( colorStr.substring( 3, 5 ), 16 ),
		            Integer.valueOf( colorStr.substring( 5, 7 ), 16 ) );
	}

}

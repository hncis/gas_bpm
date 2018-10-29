package org.uengine.processpublisher.graph.viewer;

import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

import org.uengine.kernel.Activity;
import org.uengine.kernel.ComplexActivity;
import org.uengine.kernel.viewer.DefaultActivityViewer;
import org.uengine.processpublisher.graph.GraphActivity;
import org.uengine.processpublisher.graph.SwimLaneCoordinate;
import org.uengine.processpublisher.graph.SwimLanePoint;

public class GenerateViewer implements ActivityViewer{
	
	public StringBuffer Header(){
		StringBuffer sb = new StringBuffer();
	
		return sb ;
		
	}

	public StringBuffer render(GraphActivity graph, SwimLaneCoordinate coodinate, Map options) {
        Hashtable roleList = coodinate.getRoleList();
        Vector graphList = coodinate.getGraphList();
		int roleCount= roleList.size();
		StringBuffer sb = new StringBuffer();
		int distanceY = 80;
		Map noDecoratedOption = new Hashtable();
		noDecoratedOption.putAll(options);
		noDecoratedOption.remove("decorated");	
				
		sb.append("<table cellpadding=0 cellspacing=0 style='border:0px solid #aaaaaa'><tr>");
		for(int i =0; i < roleCount; i++){
			sb.append("<td style='border:1px solid #aaaaaa' width=200 align=center bgcolor=#aabbff><b>"+roleList.get(new Integer(i+1))+"</b></td>");
		}
		sb.append("</tr><tr>");	
		
		for(int i =0; i < roleCount; i++){
			Vector graphXListTemp = new Vector();
			sb.append("<td style='border:1px solid #aaaaaa;' align=center><table border=0 height=100% >");
			int cols = 0;
			
			//���� X�� ������ ��Ƽ��Ƽ �˻�
			for(int j=0; j<graphList.size();j++){
				
				SwimLanePoint sp = ((GraphActivity)graphList.get(j)).getSLPoint();
				if(sp.pointX == i+1){
					graphXListTemp.add(graphList.get(j));
					if(cols < sp.pointIndexX)
						cols = sp.pointIndexX;
				}
			}
			
			int preY=0;
			//X,Y�� ���� ��Ƽ��Ƽ �˻�
			for(int j=0; j<graphXListTemp.size();j++){
				SwimLanePoint sp = ((GraphActivity)graphXListTemp.get(j)).getSLPoint();
				if(preY >= sp.pointY)	continue;
				Vector graphXYListTemp = new Vector();
				
				//lane�� �߰��� ��Ƽ��Ƽ �˻�
				for(int k=0; k < graphXListTemp.size(); k++){
					SwimLanePoint sp2 = ((GraphActivity)graphXListTemp.get(k)).getSLPoint();
					if(sp.pointY == sp2.pointY){
						graphXYListTemp.add(graphXListTemp.get(k));
					}
				}
				
				//��� ó��
				for (int k = preY; k < sp.pointY-1; k++) {
					sb.append("<tr>");
					//for (int l = 0; l < graphXYListTemp.size(); l++) {
						sb.append("<td width=100 height="+distanceY+" colspan="+(cols)+">  </td>");
					//}
					sb.append("</tr>");
				}

				//���
				String colspans="";
				sb.append("<tr>");
				for (int k = 0; k < graphXYListTemp.size(); k++) {
					DefaultActivityViewer sav = new DefaultActivityViewer();
					GraphActivity graphAct = (GraphActivity)graphXYListTemp.get(k);
					Activity act = graphAct.getReferenceActivity();
					
					if(cols != graphXYListTemp.size())	colspans="colspan ="+cols;
					sb.append("<td width=100 height="+distanceY+" align=center "+colspans+">");
					
					if(graphAct.getName()==null) {
						sb.append("<div id='sw_act_" + act.getTracingTag() + "'>"+ sav.render(act,null, (act instanceof ComplexActivity ? noDecoratedOption : options))+ "</div>");
					} else if(graphAct.isStartGraphActivity()) {
						sb.append("<div id='sw_act_start'><img src=\"../processmanager/images/start.gif\"></div>");
					} else if(graphAct.isEndGraphActivity()) {
						sb.append("<div id='sw_act_end'><img src=\"../processmanager/images/end.gif\"></div>");
					} else {
						sb.append("<div id='sw_act_" + act.getTracingTag() + "'>"+ sav.render(act,null, (act instanceof ComplexActivity ? noDecoratedOption : options))+ "</div>");
					}
					sb.append("</td>");
				}
				sb.append("</tr>");
				preY=sp.pointY;
			}
			sb.append("</table></td>");
		}
	
		sb.append("</tr></table>");
		return sb;
	}
}

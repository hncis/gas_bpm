package org.uengine.processdesigner;

import java.awt.Point;

public class ArrowLinkingInfo {
	Point linkPoint;
	boolean isTarget;
	boolean isSource;
	
	public boolean isTarget() {
		return isTarget;
	}
	public void setTarget(boolean isTarget) {
		this.isTarget = isTarget;
	}
	public boolean isSource() {
		return isSource;
	}
	public void setSource(boolean isSource) {
		this.isSource = isSource;
	}
	
	public Point getLinkPoint() {
		return linkPoint;
	}
	public void setLinkPoint(Point linkPoint) {
		this.linkPoint = linkPoint;
	}

	
}

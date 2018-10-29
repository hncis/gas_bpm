package com.webdeninteractive.xbotts.Mapping.maptool;

public class LinkLine extends SimpleLine{
    public LinkLine(int x1, int y1, int x2, int y2, boolean srcVisible, boolean tgtVisible){
        super(x1,y1,x2,y2);
        this.srcVisible=srcVisible;
        this.tgtVisible=tgtVisible;
    }
    
    public LinkLine( ){ }
    public boolean tgtVisible = true;
    public boolean srcVisible = true;

}

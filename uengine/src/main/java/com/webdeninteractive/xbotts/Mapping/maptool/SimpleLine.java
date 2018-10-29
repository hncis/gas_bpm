package com.webdeninteractive.xbotts.Mapping.maptool;

public class SimpleLine{
    SimpleLine( ){ }
    SimpleLine(int x1, int y1, int x2, int y2){
        this.x1=x1;
        this.y1=y1;
        this.x2=x2;
        this.y2=y2;
    }
    public int x1,x2,y1,y2;
    public String toString( ){
        return "X1:"+x1+" Y1:"+y1+" X2:"+x2+" Y2:"+y2;
    }
}

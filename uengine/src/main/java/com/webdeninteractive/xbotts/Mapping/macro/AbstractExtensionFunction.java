/*
-------------------------------------------------------------------
BIE is Copyright 2001-2004 Brunswick Corp.
-------------------------------------------------------------------
Please read the legal notices (docs/legal.txt) and the license
(docs/bie_license.txt) that came with this distribution before using
this software.
-------------------------------------------------------------------
 */
/*
 * AbstractMacroFunction.java
 *
 * Created on October 10, 2002, 10:14 AM
 */

package com.webdeninteractive.xbotts.Mapping.macro;
import java.awt.Component;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Node;
/**
 *
 * @author  bmadigan
 */
public abstract class AbstractExtensionFunction implements ExtensionFunction{
    
    /** Creates a new instance of AbstractMacroFunction */
    public AbstractExtensionFunction() {
    }
    public Object getTransferData(java.awt.datatransfer.DataFlavor dataFlavor)
    throws java.awt.datatransfer.UnsupportedFlavorException, java.io.IOException{
        if(isDataFlavorSupported(dataFlavor)){
            return this;
        }
        throw new UnsupportedFlavorException(dataFlavor);
    }
    
    public java.awt.datatransfer.DataFlavor[] getTransferDataFlavors(){
        return flavors;
    }
    
    public boolean isDataFlavorSupported(java.awt.datatransfer.DataFlavor dataFlavor){
        return dataFlavor.equals(EXTENSION_FUNCTION_FLAVOR);
    }
    
    
    public void setId(int id){
        this.id=id;
    }
    int id;
    public int getId( ){
        return id;
    }
    
    
    
    public ImageIcon getIcon() {
        return new ImageIcon(AbstractExtensionFunction.class.getClassLoader().getResource("com/webdeninteractive/images/macro.gif"));
    }
    
    Component ui;
    public Component getUI() {
        return ui;
    }
    
    public void setUI(Component ui) {
        this.ui=ui;
    }
    
    int loc_x, loc_y;
    public int getX() {
        return loc_x;
    }
    
    public int getY() {
        return loc_y;
    }
    
    public void setX(int x) {
        this.loc_x=x;
    }
    
    public void setY(int y) {
        this.loc_y=y;
    }
    
    /** Provides a description of this ExtensionElement for the user. <BR>
     *  can be html formatted.
     */
    public String getDescription(){
        return "";
    }
    /** For java extensions, should return 'javaclass'
     *
     */
    public String getLanguage(){
        return "javaclass";
    }
    
    /** Used to resolve per-map constant paramters. <br>The values for these params
     *  are stored in the map and compiled into the source xslt <br>
     *  as variables for passing to functions.
     */
    public MutableParameter[] getMutableParameters(){
        return mutableParams.toArray(emptyMutables);
    }
    private MutableParameter[] emptyMutables = new MutableParameter[] {};
    
    /** Used to resolve the order and names of parameters to the extension function
     *  call. See Parameter.
     */
    public Parameter[] getParameters(){
        return parameters.toArray(emptyParams);
    }
    private Parameter[] emptyParams = new Parameter[]{ };
    
    /** Provides a Parameter by name. if no parameter with this name exists in
     * this extension element, returns null.
     */
    public Parameter getParameter(String name){
        java.util.Iterator<Parameter> iter = parameters.iterator( );
        while(iter.hasNext( )){
            Parameter p = iter.next( );
            if(p.getName( ).equals(name)) return p;
        }
        return null;
    }
    
    /** The java type that this extension function call returns.
     */
    public Class<String> getReturnType( ){
        return String.class;
    }
    
    /** Determines if this function can be instantiated or if methods are static.
     *  Most functions are static unless they need to retain information across multiple
     *  calls.
     */
    public boolean isStatic( ) {
        return true;
    }
    
    
    private List<Parameter> parameters = new ArrayList<Parameter>( );
    private void addParameter(String name, Class type){
        parameters.add(new Parameter(name, type, "", this));
    }
    
    public void addLinkableParameter(String name){
        addParameter(name, String.class);
    }
    
    private List<MutableParameter> mutableParams = new java.util.ArrayList<MutableParameter>( );
    public void addEditableParameter(String name, String defaultValue, Class type, String desc){
        mutableParams.add(
        new MutableParameter(name, defaultValue, type, desc, this)
        );
    }
    
    public void addEditableParameter(MutableParameter m){
        mutableParams.add(m);
    }
    
    public String getName( ){
        try{
            return getExtensionMethodName(this);
        }catch(Exception ex){
            return null;
        }
    }
    
    public String getSource( ){
        return null;
    }
    
    public static String getExtensionMethodName(ExtensionFunction ext)
    throws Exception {
        Method[] methods = ext.getClass( ).getDeclaredMethods( );
        if(methods==null||methods.length<1){
            throw new Exception("Extension class "+ext.getClass( ).getName( )+" must declare a public method.");
        }
        //need the class's short name for later
        String cname = ext.getClass( ).getName( );
        if(cname.indexOf(".")!=-1) cname = cname.substring(cname.lastIndexOf("."), cname.length());
        //look for all public methods
        for(int i=0;i<methods.length;i++){
            if(Modifier.isPublic(methods[i].getModifiers( ))){
                //make sure we get the one that is not a constructor.
                //Dont know why constructors are included, they just are.
                if(!methods[i].getName( ).equals(cname)) return methods[i].getName( );
            }
        }
        //did not find a non-constructor, public method.
        throw new Exception("No public method declared in extension class: "+ext.getClass( ).getName( ));
    }
    
    
    /** getStringValue function for a Node
     * this probably exists elsewhere..
     * @param n A unique Node
     * @return The value of the node.
     */
    public  String getStringValue(Node n){
        if(n instanceof CharacterData){
            return n.getNodeValue( );
        }
        if(n==null){
            return "";
        }
        return getStringValue(n.getFirstChild( ));
    }
    
       /** Rounds the given value to the given number of decimal places.
     * @param value number to round.
     * @param decimalPlaces number of (rounded) decimal places to print.
     * @return The rounded number formated according to the decimal places.
     */
    public String roundToPlaces(double value, double decimalPlaces)
    throws Exception {
        StringBuffer formatString = new StringBuffer("##0");
        int numberOfDecimal = (int)Math.round(decimalPlaces);
		if (numberOfDecimal > 0) {
        	formatString.append(".");
            for(int j=0; j<numberOfDecimal; j++){
                formatString.append("0");
            }
        }
        DecimalFormat df = new DecimalFormat(formatString.toString());
        return df.format( value );
        
    } // roundToPlaces()
}

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
 * Linkable.java
 *
 * Created on July 30, 2002, 10:52 AM
 */

package com.webdeninteractive.xbotts.Mapping.compiler;
import com.webdeninteractive.xbotts.Mapping.macro.*;

import java.awt.Point;
import java.awt.datatransfer.*;
import java.util.*;
/**
 *
 * @author  bmadigan
 */
public interface Linkable {
    int getX( );
    int getY( );
    void setName(String name);
    String getName( );
    void setType(String type);
    String getType( );
    void setStructure(String structure);
    String getStructure( );
    void setNodeType(String type);
    String getNodeType( );
    void setDirection(String direction);
    String getDirection();
    void setKeyType(int keyType);
    int getKeyType();
    
    public static final String ATTR_NODE = "attributeNode";
    public static final String ELEMENT_NODE = "elementNode";
    public static final String TEXT_NODE = "textNode";
    /** not related to getType and getStructure
     *  returns one of the constant fields
     */
    String getStructureGeneric( ); 
    
    void addImpliedLinkSource( Linkable l);
    void addImpliedLinkTarget( Linkable l);
    
    void addNToOneImpliedLinkSource(Linkable l);
    void insertNToOneImpliedLinkSource(int index, Linkable l);
    Linkable[] getNToOneImpliedLinkSources( );
    
    void setHardLinkTarget( Linkable l);
    void setHardLinkSource( Linkable l);
    Linkable getHardLinkTarget( );
    Linkable getHardLinkSource( );
    
    Linkable[] getImpliedLinkSources( );
    Linkable[] getImpliedLinkTargets( );

    /** Methods to support n-1, 1-n, n-n mappings.
     *
     */
    void addHardLinkTarget(Linkable target);
    boolean removeHardLinkTarget(Linkable target);
    Linkable[] getHardLinkTargets( );
    java.util.List getLinkTargets( );
    java.util.List getLinkSources( );
    void addHardLinkSource(Linkable source);
    boolean removeHardLinkSource(Linkable source);
    Linkable[] getHardLinkSources( );
    /** methods for linking extension elements.
     *
     */
    void addLinkToExtensionArgument(Argument arg);
    ArrayList getExtensionArgumentTargets( );
    void addLinkFromExtensionFunction(Function func);
    ArrayList getExtensionFunctionSources( );
    /** the above methods for extension links are going away soon, use
     * these:
     */
    void addExtensionParameterTarget(Parameter param);
    void removeExtensionParameterTarget(Parameter param);
    ArrayList getExtensionParameterTargets( );
    void addFunctionCallSource(ExtensionFunction function);
    void removeFunctionCallSource(ExtensionFunction function);
    ArrayList getFunctionCallSources( );
    boolean isLinked( );
    int getLinkType( );
    //traversal methods [dont belong here really, but for lack of a better place]
    Linkable getOwner( );
    Linkable getChild(int index);
    Linkable getChild(String name);
    Linkable[] getChildNodes( );
    Vector getChildren( );
    //added for checking of occurence. 0,1,n,unbounded.
    String getMaxOccurs( );
    String getMinOccurs( );
    void setMaxOccurs(String maxOccurs );
    void setMinOccurs(String minOccurs );
    
    void setUse(String use);
    String getUse( );
    
    void setDocumentation(String doc);
    String getDocumentation( );
    
    String getDefault( );
    void setDefault(String value );
    void appendChild(Linkable child);
    final public static DataFlavor LINKABLE_FLAVOR =
    new DataFlavor(Linkable.class, "Linkable");
    static DataFlavor flavors[] = {LINKABLE_FLAVOR};
    
    public List getTargetContexts( );
    public List getSourceContexts( );
    
    
    public boolean is(Linkable compareTo);
    /** Linkable structure types. determined for each linkable in SchemaAnalyzer and used by CompilerContext
     * These are used by the complier, more specific to schemas
     */
    public static final String SEQUENCE = "sequence";
    public static final String SIMPLE_TYPE = "simpleType";
    public static final String COMPLEX_TYPE = "complexType";

    public static final String TEMPLATE = "template";
    
    /** Generic structure types. Provide more information than the above, should replace them.
     *
     */
    public static final String ROOT = "root";
    
    public static final String GROUP = "group";
    
    public static final String OPTIONAL_GROUP = "opt_group";
    
    public static final String GROUP_REFERENCE = "group_ref";
    
    public static final String OPTIONAL_REPEATING_GROUP = "opt_rep_group";
    
    public static final String REPEATING_GROUP = "rep_group";
    
    public static final String REPEATING_GROUP_REFERENCE = "rep_grp_ref";
    
    public static final String OPTIONAL_REPEATING_GROUP_REFERENCE = "opt_rep_group_ref";
    
    public static final String FIELD = "field";
    
    public static final String OPTIONAL_FIELD = "opt_field";
    
    public static final String REPEATING_FIELD = "rep_field";
    
    public static final String OPTIONAL_REPEATING_FIELD = "opt_rep_field";
    
    public static final int NOT_LINKED = 0;
    public static final int ATOMIC_LINK = 1;
    public static final int CONTEXT_LINK = 2;

    ////////// added by uEngine //////////////
    
    public Point getLinkPoint();

}
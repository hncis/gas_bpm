package org.uengine.ui.taglibs.input;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

/**
*
* @author <a href="mailto:ghbpark@hanwha.co.kr">Sungsoo Park</a>
* @version $Id: Util.java,v 1.3 2009/04/10 05:25:46 erim79 Exp $
*/
public class Util {

    /** Print out any HTML tag attributes we might have been passed. 
     * @throws IOException 
     * @throws JspTagException 
    */
    public static void printAttributes(JspWriter out, Map attributes) throws JspTagException, IOException {
    	out.print(getAttributesTag(attributes));
    }
    
    public static String getAttributesTag(Map attributes) throws JspTagException {
    	String strAttrTag = "";

        if (attributes != null) {
            Iterator i = attributes.keySet().iterator();
            while (i.hasNext()) {
                Object oKey = i.next();
                Object oVal = attributes.get(oKey);

                /*
                 * If the attribute contains non-Strings, give the user a more
                 * meaningful message than what he or she would get if we just
                 * propagated a ClassCastException back. (This'll get caught
                 * below.)
                 */
                if (!(oKey instanceof String)
                        || (oVal != null && !(oVal instanceof String)))
                    throw new JspTagException(
                            "all members in attributes Map must be Strings");
                String key = (String) oKey;
                String value = (String) oVal;

                // check for illegal keys
                if (key.equals("name") || key.equals("value")
                        || key.equals("type") || key.equals("checked"))
                    throw new JspTagException("illegal key '" + key
                            + "'found in attributes Map");

                /*
                 * Print the key and value. If the value is null, make it equal
                 * to the key. This follows the conventions of XHTML 1.0 and
                 * does not break regular HTML.
                 */
                if (value == null)
                    value = key;

                strAttrTag += quote(key) + "=\"" + quote(value) + "\" ";
            }
        }
        
    	return strAttrTag;
    }

    /** Quote metacharacters in HTML. */
    public static String quote(String x) {
        if (x == null)
            return null;
        else {
            // deal with ampersands first so we can ignore the ones we add later
            if ( x.length() == 0 ) return x;
            x = replace(x, "&", "&amp;");
            x = replace(x, "\"", "&quot;");
            x = replace(x, "<", "&lt;");
            x = replace(x, ">", "&gt;");
            return x;
        }
    }

    /**
     * Efficient string replace function. Replaces instances of the substring
     * find with replace in the string subject. karl@xk72.com
     * 
     * @param subject
     *            The string to search for and replace in.
     * @param find
     *            The substring to search for.
     * @param replace
     *            The string to replace instances of the string find with.
     */
    public static String replace(String subject, String find, String replace) {
        StringBuffer buf = new StringBuffer();
        int l = find.length();
        int s = 0;
        int i = subject.indexOf(find);
        while (i != -1) {
            buf.append(subject.substring(s, i));
            buf.append(replace);
            s = i + l;
            i = subject.indexOf(find, s);
        }
        buf.append(subject.substring(s));
        return buf.toString();
    }

    /**
     * Gets a named property from a JavaBean and returns its value as an Object,
     * possibly null.
     */
    public static Object beanPropertyValueObject(Object bean, String name)
            throws JspTagException {
        if (bean != null) {
            Method reader = null;
            Object[] params = null;

            // Try to find a reader method for the named property
            try {
                PropertyDescriptor prop = new PropertyDescriptor(name, bean
                        .getClass());
                reader = prop.getReadMethod();
            } catch (IntrospectionException e) {
                // No property exists with that name, try a generic get method
                // Object get( Object key )
                try {
                    reader = bean.getClass().getMethod("get",
                            new Class[] { Object.class });
                    params = new Object[] { name };
                } catch (NoSuchMethodException f) {
                    // Try an Object get( String key) method
                    try {
                        reader = bean.getClass().getMethod("get",
                                new Class[] { String.class });
                        params = new Object[] { name };
                    } catch (NoSuchMethodException g) {
                        // Give up
                    }
                }
            }

            // If a reader method has been found
            if (reader != null) {
                try {
                    return reader.invoke(bean, params);
                } catch (IllegalAccessException e) {
                } catch (IllegalArgumentException e) {
                } catch (InvocationTargetException e) {
                    throw new JspTagException("Exception getting property \""
                            + name + "\" from bean "
                            + bean.getClass().getName() + ": "
                            + e.getTargetException());
                }
            }
        }

        return null;
    }

    /**
     * Gets a named property from a JavaBean and returns its value as a String,
     * possibly null.
     */
    public static String beanPropertyValue(Object bean, String name)
            throws JspTagException {
        Object value = beanPropertyValueObject(bean, name);
        return (value != null ? value.toString() : null);
    }

    /**
     * Gets a named property (possibly an array property) from a JavaBean and
     * returns its values as an array of Strings, possibly null. If the property
     * is not an array property, an array of size 1 is returned.
     */
    public static String[] beanPropertyValues(Object bean, String name)
            throws JspTagException {
        Object value = beanPropertyValueObject(bean, name);
        if (value != null) {
            // Check if the value is an array object
            if (value.getClass().isArray()) {
                // Convert to an array of Strings
                int n = java.lang.reflect.Array.getLength(value);
                String[] strs = new String[n];
                for (int i = 0; i < n; i++) {
                    Object o = java.lang.reflect.Array.get(value, i);
                    strs[i] = (o != null ? o.toString() : null);
                }
                return strs;
            }
            // If not an array, just convert the object to a String in an array
            // and return
            else {
                return new String[] { value.toString() };
            }
        } else {
            return null;
        }
    }

    /**
     * Finds the input:form tag enclosing the given tag and returns it.
     */
    public static Form findFormTag(Tag tag) {
        Tag formTag = TagSupport.findAncestorWithClass(tag, Form.class);
        if (formTag != null) {
            return (Form) formTag;
        } else {
            return null;
        }
    }

    /**
     * Finds the input:form tag enclosing the given tag and returns the "bean"
     * property from it, that is the default bean for this form, possibly null.
     */
    public static String defaultFormBeanId(Tag tag) {
        Form form = findFormTag(tag);
        if (form != null) {
            return form.getBean();
        } else {
            return null;
        }
    }
}
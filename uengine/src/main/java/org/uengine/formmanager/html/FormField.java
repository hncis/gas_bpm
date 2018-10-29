/*
 * Copyright 2001-2004 by HANWHA S&C Corp.,
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of HANWHA S&C Corp. ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with HANWHA S&C Corp.
 */
package org.uengine.formmanager.html;

import java.util.*;


/**
 * Represents a <em>field</em> in an HTML <a href="http://www.w3.org/TR/html4/interact/forms.html">form</a>,
 * a <em>field</em> being defined as the combination of all <a href="http://www.w3.org/TR/html4/interact/forms.html#h-17.2">controls</a>
 * in the form having the same <a href="http://www.w3.org/TR/html4/interact/forms.html#control-name">name</a>.
 * The properties of a FormField object describe how the values associated with a particular <a href="http://www.w3.org/TR/html4/interact/forms.html#control-name">name</a>
 * in a <a href="http://www.w3.org/TR/html4/interact/forms.html#form-data-set">form data set</a>
 * should be interpreted.
 * These properties include whether multiple values can be expected, the number of values which would typically
 * be set by the user, and a list of values which are predefined in the HTML.
 * This information allows the server to store and format the data in an appropriate way.
 * <p>
 * A form field which allows user values will normally consist of a single control whose {@link FormControlType#isPredefinedValue()}
 * returns <code>false</code>, such as a {@link FormControlType#TEXT TEXT} control.
 * <p>
 * When a form field consists of more than one control, these controls will normally be all be of the same {@link FormControlType} which has predefined values, such as the {@link FormControlType#CHECKBOX CHECKBOX} control.
 * <p>
 * Form fields consisting of more than one control do not necessarily return multiple values.
 * A form field consisting of {@link FormControlType#CHECKBOX CHECKBOX} controls can return multiple values, whereas
 * a form field consisting of {@link FormControlType#CHECKBOX RADIO} controls will return at most one value.
 * <p>
 * The HTML designer can disregard convention and mix all types of controls with the same name in the same form,
 * or include multiple controls of the same name which do not have predefined values.  This library will attempt to make sense of such
 * situations, although it is up to the developer using the library to decide how to handle the received data.
 * <p>
 * FormField objects are usually created by calling the {@link Segment#findFormFields() findFormFields()} method on a form {@link Element} or a {@link Source} object.
 *
 * @see FormFields
 * @see FormControlType
 */
public final class FormField {
    /** only used in FormFields class */
    static Comparator COMPARATOR = new PositionComparator();
    private String name;
    
    private String size;
    private String rows;
    private String cols;
    private String content;
    
    private List attributeList;
    
    private boolean isMultiple;
    
    private Segment segment;  
    
    private String formControlTypeId;
    private int userValueCount = 0;
    private boolean allowsMultipleValues = false;
    private LinkedHashSet predefinedValues = null;
    private int position;
    private transient FormControlType firstEncounteredFormControlType = null;
    private transient boolean updateable = false;

//  /** Constructor called from FormFields class. */
//  FormField(String name, int position, FormControlType formControlType) {
//      this.name = name;
//
//      if (formControlType != null) {
//          this.formControlTypeId = formControlType.getFormControlTypeId();
//          System.out.println("formControlTypeId : " + formControlTypeId);
//      }
//
//      this.position = position;
//      firstEncounteredFormControlType = formControlType;
//      updateable = true;
//  }

    FormField(String name, Segment segment, FormControlType formControlType, List attributeList) {
        this.name = name;
        this.attributeList = attributeList;

        if (formControlType != null) {
            this.formControlTypeId = formControlType.getFormControlTypeId();
//          System.out.println("formControlTypeId : " + formControlTypeId);
        }

        this.segment = segment;
        firstEncounteredFormControlType = formControlType;
        updateable = true;
    }

    /**
     * Returns the <a href="http://www.w3.org/TR/html4/interact/forms.html#control-name">name</a> of the field.
     * @return  the <a href="http://www.w3.org/TR/html4/interact/forms.html#control-name">name</a> of the field.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the number of values which would typically be set by the user, and are not included in the list of {@link #getPredefinedValues PredefinedValues}.
     * This should in most cases be either 0 or 1.
     * The word "typically" is used because the use of scripts can cause <a href="http://www.w3.org/TR/html4/interact/forms.html#h-17.2.1">control types</a>
     * which normally have predefined values to be set by the user, which is a condition which is beyond the scope of this library to test for.
     * <p>
     * A value of 0 indicates the field values will consist only of {@link #getPredefinedValues PredefinedValues}.
     * This is the case when the field consists of only
     * {@link FormControlType#CHECKBOX CHECKBOX}, {@link FormControlType#RADIO RADIO}, {@link FormControlType#BUTTON BUTTON},
     * {@link FormControlType#SUBMIT SUBMIT}, {@link FormControlType#IMAGE IMAGE}, {@link FormControlType#SELECT_SINGLE SELECT_SINGLE}
     * and {@link FormControlType#SELECT_MULTIPLE SELECT_MULTIPLE} form control types.
     * <p>
     * A value of 1 indicates the field values will consist of at most 1 value set by the user.
     * It is still possible to receive multiple values, but any others should consist only of {@link #getPredefinedValues PredefinedValues},
     * and this situation will only arise if the HTML designer has mixed different control types with the same name.
     * <p>
     * A value greater than 1 indicates that the HTML designer has included multiple controls of the same name which do not have predefined values.
     *
     * @return  the number of values which would typically be set by the user.
     */
    public int getUserValueCount() {
        return userValueCount;
    }

    /**
     * Indicates whether the field allows multiple values.
     * <p>
     * This will be true if the field consists of more than one control, and one of the following conditions is met:
     * <ul>
     * <li>Any one of the controls allows user values ({@link FormControlType#isPredefinedValue()}<code>==false</code>)
     * <li>Any one of the controls allows multiple values ({@link FormControlType#allowsMultipleValues()}<code>==true</code>)
     * <li>Not all of the controls are of the same type <super>*</super>
     * </ul>
     * <p>
     * <super>*</super> Note that for the purposes of this comparison, all control types which cause the form to be submitted
     * ({@link FormControlType#isSubmit()}<code>==true</code>) are considered to be the same type.
     *
     * @return  <code>true</code> if the field allows multiple values, otherwise <code>false</code>.
     */
    public boolean allowsMultipleValues() {
        return allowsMultipleValues;
    }

    /**
     * Returns a java.util.Collection containing the predefined values of all controls in this field.
     * A control only has a predefined value if {@link FormControlType#isPredefinedValue()}<code>==true</code>.
     * Its value is defined by its <a href="http://www.w3.org/TR/html4/interact/forms.html#initial-value">initial value</a>.
     * <p>
     * An interator over this collection will return the values in the order of appearance in the source.
     *
     * @return  a java.util.Collection containing the predefined values of all controls in this field, or <code>null</code> if none.
     */
    public Collection getPredefinedValues() {
        return predefinedValues;
    }

    /**
     * Returns a string representation of this object useful for debugging purposes.
     * @return  a string representation of this object useful for debugging purposes.
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Field: ").append(name).append(", UserValueCount=")
          .append(userValueCount).append(", AllowsMultipleValues=")
          .append(allowsMultipleValues).append(", formControlTypeId=").append(formControlTypeId);

        //sb.append(", fefct=").append(firstEncounteredFormControlType==null ? "null" : firstEncounteredFormControlType.getName());
        if (predefinedValues != null) {
            for (Iterator i = predefinedValues.iterator(); i.hasNext();) {
                sb.append("\nPredefinedValue: ");
                sb.append(i.next());
            }
        }

        sb.append("\n\n");

        return sb.toString();
    }

    /** only called from FormFields class */
    void setLowerPosition(int position) {
        if (!updateable) {
            throw new NoLongerUpdateableException();
        }

        if (position < this.position) {
            this.position = position;
        }
    }

    /** only called from FormFields class */
    void incrementUserValueCount() {
        userValueCount++;
    }

    /** only called from FormFields class */
    void addPredefinedValue(String predefinedValue) {
        if (!updateable) {
            throw new NoLongerUpdateableException();
        }

        if (predefinedValues == null) {
            predefinedValues = new LinkedHashSet();
        }

        predefinedValues.add(predefinedValue);
    }

    /** only called from FormFields class */
    void setMultipleValues() {
        if (!updateable) {
            throw new NoLongerUpdateableException();
        }

        allowsMultipleValues = true;
    }

    /** only called from FormFields class */
    void setMultipleValues(FormControlType formControlType) {
        if (!updateable) {
            throw new NoLongerUpdateableException();
        }

        if (!allowsMultipleValues) {
            allowsMultipleValues = calculateMultipleValues(formControlType);
        }
    }

    private boolean calculateMultipleValues(FormControlType formControlType) {
        if ((userValueCount > 0) || formControlType.allowsMultipleValues() ||
                firstEncounteredFormControlType.allowsMultipleValues()) {
            return true;
        }

        if (formControlType == firstEncounteredFormControlType) {
            return false;
        }

        if (formControlType.isSubmit() &&
                firstEncounteredFormControlType.isSubmit()) {
            return false;
        }

        return true;
    }

    /** only called from FormFields class */
    void merge(FormField formField) {
        updateable = false;
        formField.updateable = false;

        if (formField.userValueCount > userValueCount) {
            userValueCount = formField.userValueCount;
        }

        allowsMultipleValues = allowsMultipleValues ||
            formField.allowsMultipleValues;

        if (predefinedValues == null) {
            predefinedValues = formField.predefinedValues;
        } else if (formField.predefinedValues != null) {
            for (Iterator i = formField.getPredefinedValues().iterator();
                    i.hasNext();) {
                predefinedValues.add(i.next());
            }
        }
    }

    private static class PositionComparator implements Comparator {
        public int compare(Object o1, Object o2) {
            if (!(o1 instanceof FormField && o2 instanceof FormField)) {
                throw new ClassCastException();
            }

            FormField formField1 = (FormField) o1;
            FormField formField2 = (FormField) o2;

            if (formField1.position < formField2.position) {
                return -1;
            }

            if (formField1.position > formField2.position) {
                return 1;
            }

            return formField1.name.compareTo(formField2.name);
        }
    }

    private static class NoLongerUpdateableException extends RuntimeException {
        public NoLongerUpdateableException() {
            super(
                "Internal Error: FormField objects are no longer updateable after merge or deserialisation");
        }
    }
//  /**
//   * @return
//   */
//  public FormControlType getFirstEncounteredFormControlType() {
//      return firstEncounteredFormControlType;
//  }

    public FormControlType getFormControlType() {
        return firstEncounteredFormControlType;
    }

    /**
     * @return
     */
    public String getFormControlTypeId() {
        return formControlTypeId;
    }

    /**
     * @param type
     */
    public void setFirstEncounteredFormControlType(FormControlType type) {
        firstEncounteredFormControlType = type;
    }

    /**
     * @param string
     */
    public void setFormControlTypeId(String string) {
        formControlTypeId = string;
    }



    /**
     * @return
     */
    public boolean isAllowsMultipleValues() {
        return allowsMultipleValues;
    }

    /**
     * @return
     */
    public String getCols() {
        return cols;
    }

    /**
     * @return
     */
    public int getPosition() {
        return position;
    }

    /**
     * @return
     */
    public String getRows() {
        return rows;
    }

    /**
     * @return
     */
    public String getSize() {
        return size;
    }

    /**
     * @return
     */
    public boolean isUpdateable() {
        return updateable;
    }

    /**
     * @param b
     */
    public void setAllowsMultipleValues(boolean b) {
        allowsMultipleValues = b;
    }

    /**
     * @param string
     */
    public void setCols(String string) {
        cols = string;
    }

    /**
     * @param string
     */
    public void setName(String string) {
        name = string;
    }

    /**
     * @param i
     */
    public void setPosition(int i) {
        position = i;
    }

    /**
     * @param string
     */
    public void setRows(String string) {
        rows = string;
    }

    /**
     * @param string
     */
    public void setSize(String string) {
        size = string;
    }

    /**
     * @param b
     */
    public void setUpdateable(boolean b) {
        updateable = b;
    }

    /**
     * @param i
     */
    public void setUserValueCount(int i) {
        userValueCount = i;
    }

    /**
     * @return
     */
    public String getContent() {
        return content;
    }

    /**
     * @param string
     */
    public void setContent(String string) {
        content = string;
    }

    /**
     * @return
     */
    public Segment getSegment() {
        return segment;
    }

    /**
     * @param segment
     */
    public void setSegment(Segment segment) {
        this.segment = segment;
    }

    /**
     * @return
     */
    public List getAttributeList() {
        return attributeList;
    }

    /**
     * @param list
     */
    public void setAttributeList(List list) {
        attributeList = list;
    }

    /**
     * @return
     */
    public boolean isMultiple() {
        return isMultiple;
    }

    /**
     * @param b
     */
    public void setMultiple(boolean b) {
        isMultiple = b;
    }

}
/*
 * $Log: FormField.java,v $
 * Revision 1.2  2007/12/05 02:31:24  curonide
 * *** empty log message ***
 *
 * Revision 1.5  2007/12/04 07:34:39  bpm
 * *** empty log message ***
 *
 * Revision 1.3  2007/12/04 05:25:48  bpm
 * *** empty log message ***
 *
 * Revision 1.1  2007/07/02 01:41:01  pongsor
 * Form management support
 *
 * Revision 1.1  2007/01/26 10:54:44  mahler
 * *** empty log message ***
 *
 * Revision 1.1  2007/01/26 10:41:16  mahler
 * *** empty log message ***
 *
 * Revision 1.1  2005/09/06 06:59:37  ghbpark
 * xcommons 2.0 start
 *
 * Revision 1.1  2005/04/11 10:24:20  ghbpark
 * *** empty log message ***
 *
 * Revision 1.1  2005/04/03 01:36:51  ghbpark
 * *** empty log message ***
 *
 * Revision 1.2  2004/05/11 03:48:47  ghbpark
 * html 파서 추가
 *
 */

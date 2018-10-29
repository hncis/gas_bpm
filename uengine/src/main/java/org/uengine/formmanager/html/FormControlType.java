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
 * Represents one of the HTML <a href="http://www.w3.org/TR/html4/interact/forms.html#h-17.2.1">control types</a> in a <a href="http://www.w3.org/TR/html4/interact/forms.html">form</a>
 * which have the potential to be <a href="http://www.w3.org/TR/html4/interact/forms.html#successful-controls">successful</a>.
 * This means that they can contribute name/value pairs to the <a href="http://www.w3.org/TR/html4/interact/forms.html#form-data-set">form data set</a> when the form is <a href="http://www.w3.org/TR/html4/interact/forms.html#submit-format">submitted</a>.
 * <p>
 * Each type of control has a certain behaviour in regards to the name/value pairs submitted.
 * This class defines that behaviour, so that the meaning of the name/value pairs submitted from an arbitrary HTML page can be determined.
 *
 * @see FormField
 * @see FormFields
 */
public final class FormControlType {
	private static HashMap map = new HashMap();
	private static HashSet tagNames = new HashSet();

	/**
	 * <code>&lt;<a href="http://www.w3.org/TR/html4/interact/forms.html#h-17.4.1">input</a> type="text" name="FieldName" value="DefaultValue" /&gt;</code>.
	 * <p>
	 * <code>{@link #getFormControlTypeId()} = "text"</code><br />
	 * <code>{@link #getTagName()} = "input"</code><br />
	 * <code>{@link #allowsMultipleValues()} = true</code><br />
	 * <code>{@link #isPredefinedValue()} = false</code><br />
	 * <code>{@link #isSubmit()} = false</code><br />
	 * <code>{@link #getAdditionalSubmitNames(String) getAdditionalSubmitNames("<i>name</i>")} = null</code><br />
	 */
	public static final FormControlType TEXT = construct(new FormControlType(
				"text", "input", true, false));

	/**
	 * <code>&lt;<a href="http://www.w3.org/TR/html4/interact/forms.html#h-17.4.1">input</a> type="password" name="FieldName" value="DefaultValue" /&gt;</code>.
	 * <p>
	 * <code>{@link #getFormControlTypeId()} = "password"</code><br />
	 * <code>{@link #getTagName()} = "input"</code><br />
	 * <code>{@link #allowsMultipleValues()} = true</code><br />
	 * <code>{@link #isPredefinedValue()} = false</code><br />
	 * <code>{@link #isSubmit()} = false</code><br />
	 * <code>{@link #getAdditionalSubmitNames(String) getAdditionalSubmitNames("<i>name</i>")} = null</code><br />
	 */
	public static final FormControlType PASSWORD = construct(new FormControlType(
				"password", "input", true, false));

	/**
	 * <code>&lt;<a href="http://www.w3.org/TR/html4/interact/forms.html#h-17.4.1">input</a> type="hidden" name="FieldName" value="PredefinedValue" /&gt;</code>.
	 * <p>
	 * <code>{@link #getFormControlTypeId()} = "hidden"</code><br />
	 * <code>{@link #getTagName()} = "input"</code><br />
	 * <code>{@link #allowsMultipleValues()} = true</code><br />
	 * <code>{@link #isPredefinedValue()} = false</code><br />
	 * <code>{@link #isSubmit()} = false</code><br />
	 * <code>{@link #getAdditionalSubmitNames(String) getAdditionalSubmitNames("<i>name</i>")} = null</code><br />
	 * <p>
	 * Note that {@link #isPredefinedValue()} returns false for this control type because the value of hidden fields is usually set via server or client side scripting.
	 */
	public static final FormControlType HIDDEN = construct(new FormControlType(
				"hidden", "input", true, false));

	/**
	 * <code>&lt;<a href="http://www.w3.org/TR/html4/interact/forms.html#h-17.7">textarea</a> name="FieldName"&gt;Default Value&lt;/textarea&gt;</code>.
	 * <p>
	 * <code>{@link #getFormControlTypeId()} = "textarea"</code><br />
	 * <code>{@link #getTagName()} = "textarea"</code><br />
	 * <code>{@link #allowsMultipleValues()} = true</code><br />
	 * <code>{@link #isPredefinedValue()} = false</code><br />
	 * <code>{@link #isSubmit()} = false</code><br />
	 * <code>{@link #getAdditionalSubmitNames(String) getAdditionalSubmitNames("<i>name</i>")} = null</code><br />
	 */
	public static final FormControlType TEXTAREA = construct(new FormControlType(
				"textarea", "textarea", true, false));

	/**
	 * <code>&lt;<a href="http://www.w3.org/TR/html4/interact/forms.html#h-17.4.1">input</a> type="checkbox" name="FieldName" value="PredefinedValue" /&gt;</code>.
	 * <p>
	 * <code>{@link #getFormControlTypeId()} = "checkbox"</code><br />
	 * <code>{@link #getTagName()} = "input"</code><br />
	 * <code>{@link #allowsMultipleValues()} = true</code><br />
	 * <code>{@link #isPredefinedValue()} = true</code><br />
	 * <code>{@link #isSubmit()} = false</code><br />
	 * <code>{@link #getAdditionalSubmitNames(String) getAdditionalSubmitNames("<i>name</i>")} = null</code><br />
	 */
	public static final FormControlType CHECKBOX = construct(new FormControlType(
				"checkbox", "input", true, true));

	/**
	 * <code>&lt;<a href="http://www.w3.org/TR/html4/interact/forms.html#h-17.4.1">input</a> type="radio" name="FieldName" value="PredefinedValue" /&gt;</code>.
	 * <p>
	 * <code>{@link #getFormControlTypeId()} = "radio"</code><br />
	 * <code>{@link #getTagName()} = "input"</code><br />
	 * <code>{@link #allowsMultipleValues()} = false</code><br />
	 * <code>{@link #isPredefinedValue()} = true</code><br />
	 * <code>{@link #isSubmit()} = false</code><br />
	 * <code>{@link #getAdditionalSubmitNames(String) getAdditionalSubmitNames("<i>name</i>")} = null</code><br />
	 */
	public static final FormControlType RADIO = construct(new FormControlType(
				"radio", "input", false, true));

	/**
	 * <code>&lt;<a href="http://www.w3.org/TR/html4/interact/forms.html#h-17.4.1">input</a> type="file" name="FieldName" value="DefaultFileName" /&gt;</code>.
	 * <p>
	 * <code>{@link #getFormControlTypeId()} = "file"</code><br />
	 * <code>{@link #getTagName()} = "input"</code><br />
	 * <code>{@link #allowsMultipleValues()} = true</code><br />
	 * <code>{@link #isPredefinedValue()} = false</code><br />
	 * <code>{@link #isSubmit()} = false</code><br />
	 * <code>{@link #getAdditionalSubmitNames(String) getAdditionalSubmitNames("<i>name</i>")} = null</code><br />
	 */
	public static final FormControlType FILE = construct(new FormControlType(
				"file", "input", true, false));

	/**
	 * <code>&lt;<a href="http://www.w3.org/TR/html4/interact/forms.html#h-17.5">button</a> type="submit" name="FieldName" value="PredefinedValue" /&gt;</code>.
	 * <p>
	 * <code>{@link #getFormControlTypeId()} = "button"</code><br />
	 * <code>{@link #getTagName()} = "button"</code><br />
	 * <code>{@link #allowsMultipleValues()} = false</code><br />
	 * <code>{@link #isPredefinedValue()} = true</code><br />
	 * <code>{@link #isSubmit()} = true</code><br />
	 * <code>{@link #getAdditionalSubmitNames(String) getAdditionalSubmitNames("<i>name</i>")} = null</code><br />
	 */
	public static final FormControlType BUTTON = construct(new FormControlType(
				"button", "button", false, true));

	/**
	 * <code>&lt;<a href="http://www.w3.org/TR/html4/interact/forms.html#h-17.4.1">input</a> type="submit" name="FieldName" value="PredefinedValue" /&gt;</code>.
	 * <p>
	 * <code>{@link #getFormControlTypeId()} = "submit"</code><br />
	 * <code>{@link #getTagName()} = "input"</code><br />
	 * <code>{@link #allowsMultipleValues()} = false</code><br />
	 * <code>{@link #isPredefinedValue()} = true</code><br />
	 * <code>{@link #isSubmit()} = true</code><br />
	 * <code>{@link #getAdditionalSubmitNames(String) getAdditionalSubmitNames("<i>name</i>")} = null</code><br />
	 */
	public static final FormControlType SUBMIT = construct(new FormControlType(
				"submit", "input", false, true));

	/**
	 * <code>&lt;<a href="http://www.w3.org/TR/html4/interact/forms.html#h-17.4.1">input</a> type="image" name="FieldName" src="ImageURL" value="PredefinedValue" /&gt;</code>.
	 * <p>
	 * <code>{@link #getFormControlTypeId()} = "image"</code><br />
	 * <code>{@link #getTagName()} = "input"</code><br />
	 * <code>{@link #allowsMultipleValues()} = false</code><br />
	 * <code>{@link #isPredefinedValue()} = true</code><br />
	 * <code>{@link #isSubmit()} = true</code><br />
	 * <code>{@link #getAdditionalSubmitNames(String) getAdditionalSubmitNames("<i>name</i>")} = {"<i>name</i>.x","<i>name</i>.y"}</code><br />
	 */
	public static final FormControlType IMAGE = construct(new FormControlType(
				"image", "input", false, true));

	/**
	 * <code>&lt;<a href="http://www.w3.org/TR/html4/interact/forms.html#h-17.6">select</a> name="FieldName"&gt; &lt;option value="PredefinedValue"&gt;Display Text&lt;/option&gt; &lt;/select&gt;</code>.
	 * <p>
	 * <code>{@link #getFormControlTypeId()} = "select_single"</code><br />
	 * <code>{@link #getTagName()} = "select"</code><br />
	 * <code>{@link #allowsMultipleValues()} = false</code><br />
	 * <code>{@link #isPredefinedValue()} = true</code><br />
	 * <code>{@link #isSubmit()} = false</code><br />
	 * <code>{@link #getAdditionalSubmitNames(String) getAdditionalSubmitNames("<i>name</i>")} = null</code><br />
	 */
	public static final FormControlType SELECT_SINGLE = construct(new FormControlType(
				"select_single", "select", false, true));

	/**
	 * <code>&lt;<a href="http://www.w3.org/TR/html4/interact/forms.html#h-17.6">select</a> name="FieldName" multiple&gt; &lt;option value="PredefinedValue"&gt;Display Text&lt;/option&gt; &lt;/select&gt;</code>.
	 * <p>
	 * <code>{@link #getFormControlTypeId()} = "select_multiple"</code><br />
	 * <code>{@link #getTagName()} = "select"</code><br />
	 * <code>{@link #allowsMultipleValues()} = true</code><br />
	 * <code>{@link #isPredefinedValue()} = true</code><br />
	 * <code>{@link #isSubmit()} = false</code><br />
	 * <code>{@link #getAdditionalSubmitNames(String) getAdditionalSubmitNames("<i>name</i>")} = null</code><br />
	 */
	public static final FormControlType SELECT_MULTIPLE = construct(new FormControlType(
				"select_multiple", "select", true, true));
	private String formControlTypeId;
	private String tagName;
	private boolean allowsMultipleValues;
	private boolean predefinedValue;

	private FormControlType(String formControlTypeId, String tagName,
		boolean allowsMultipleValues, boolean predefinedValue) {
		this.formControlTypeId = formControlTypeId;
		this.tagName = tagName;
		this.allowsMultipleValues = allowsMultipleValues;
		this.predefinedValue = predefinedValue;
	}

	private static FormControlType construct(FormControlType formControlType) {
		map.put(formControlType.formControlTypeId, formControlType);
		tagNames.add(formControlType.tagName);

		return formControlType;
	}

	/**
	 * Returns a string which identifies this form control type.
	 * <p>
	 * This is the same as the control type's static field name in lower case.
	 *
	 * @return  a string which identified this form control type.
	 */
	public String getFormControlTypeId() {
		return formControlTypeId;
	}

	/**
	 * Returns the {@link StartTag#getName() name} of the tag that defines this form control type.
	 * @return  the name of the tag that defines this form control type.
	 */
	public String getTagName() {
		return tagName;
	}

	/**
	 * Indicates whether more than one control of this type with the same <a href="http://www.w3.org/TR/html4/interact/forms.html#control-name">name</a> can be <a href="http://www.w3.org/TR/html4/interact/forms.html#successful-controls">successful</a>.
	 * @return  <code>true</code> if more than one control of this type with the same <a href="http://www.w3.org/TR/html4/interact/forms.html#control-name">name</a> can be <a href="http://www.w3.org/TR/html4/interact/forms.html#successful-controls">successful</a>, otherwise <code>false</code>.
	 */
	public boolean allowsMultipleValues() {
		return allowsMultipleValues;
	}

	/**
	 * Indicates whether the <a href="http://www.w3.org/TR/html4/interact/forms.html#current-value">value</a> submitted by this type of control is predefined in the HTML and typically not modified by the user or server/client scripts.
	 * @return  <code>true</code> if the <a href="http://www.w3.org/TR/html4/interact/forms.html#current-value">value</a> submitted by this type of control is predefined in the HTML and typically not modified by the user or server/client scripts, otherwise <code>false</code>.
	 */
	public boolean isPredefinedValue() {
		return predefinedValue;
	}

	/**
	 * Indicates whether this control type causes the form to be <a href="http://www.w3.org/TR/html4/interact/forms.html#submit-format">submitted</a>.
	 * @return  <code>true</code> if this control type causes the form to be <a href="http://www.w3.org/TR/html4/interact/forms.html#submit-format">submitted</a>, otherwise <code>false</code>.
	 */
	public boolean isSubmit() {
		return (this == SUBMIT) || (this == BUTTON) || (this == IMAGE);
	}

	/**
	 * Returns an array containing the additional field names submitted if a control of this type with the specified <a href="http://www.w3.org/TR/html4/interact/forms.html#control-name">name</a> is <a href="http://www.w3.org/TR/html4/interact/forms.html#successful-controls">successful</a>.
	 * <p>
	 * This method returns <code>null</code> for all control types except {@link #IMAGE}.
	 * It relates to the extra <code><i>name</i>.x</code> and <code><i>name</i>.y</code> data submitted when a pointing device is used to activate an IMAGE control.
	 * @param  name  the <a href="http://www.w3.org/TR/html4/interact/forms.html#control-name">name</a> of a form control.
	 * @return  an array containing the additional field names submitted if a control of this type with the specified <a href="http://www.w3.org/TR/html4/interact/forms.html#control-name">name</a> is <a href="http://www.w3.org/TR/html4/interact/forms.html#successful-controls">successful</a>, or <code>null</code> if none.
	 */
	public String[] getAdditionalSubmitNames(String name) {
		if (this != IMAGE) {
			return null;
		}

		String[] names = new String[2];
		names[0] = name + ".x";
		names[1] = name + ".y";

		return names;
	}

	/**
	 * Returns the {@link FormControlType} with the specified ID.
	 * @param  formControlTypeId  the ID of a form control type.
	 * @return  the {@link FormControlType} with the specified ID, or null if no such control exists.
	 */
	public static FormControlType get(String formControlTypeId) {
		return (FormControlType) map.get(formControlTypeId);
	}

	/**
	 * Indicates whether an HTML tag with the specified name is potentially a form <a href="http://www.w3.org/TR/html4/interact/forms.html#h-17.2">control</a>.
	 * @param  tagName  the name of an HTML tag.
	 * @return  <code>true</code> if an HTML tag with the specified name is potentially a form <a href="http://www.w3.org/TR/html4/interact/forms.html#h-17.2">control</a>, otherwise <code>false</code>.
	 */
	public static boolean isPotentialControl(String tagName) {
		return tagNames.contains(tagName);
	}

	/**
	 * Returns a string representation of this object useful for debugging purposes.
	 * @return  a string representation of this object useful for debugging purposes.
	 */
	public String toString() {
		return "formControlTypeId=" + formControlTypeId + ", tagName=" +
		tagName + ", allowsMultipleValues=" + allowsMultipleValues +
		", predefinedValue=" + predefinedValue;
	}
}
/*
 * $Log: FormControlType.java,v $
 * Revision 1.3  2009/02/26 04:55:19  erim79
 * *** empty log message ***
 *
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
 * Revision 1.1  2007/01/26 10:41:15  mahler
 * *** empty log message ***
 *
 * Revision 1.1  2005/09/06 06:59:37  ghbpark
 * xcommons 2.0 start
 *
 * Revision 1.1  2005/04/11 10:24:20  ghbpark
 * *** empty log message ***
 *
 * Revision 1.1  2005/04/03 01:36:52  ghbpark
 * *** empty log message ***
 *
 * Revision 1.2  2004/05/11 03:48:47  ghbpark
 * html �ļ� �߰�
 *
 */

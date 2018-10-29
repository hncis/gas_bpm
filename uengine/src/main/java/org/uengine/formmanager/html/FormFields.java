package org.uengine.formmanager.html;

import java.util.*;

/**
 * form �±׿� �ִ� input, textarea, select, button �� 
 * �Ӽ�; �м��Ͽ� ArrayList�� ��´�. 
 */
public final class FormFields {
	private HashMap map=new HashMap();
	private ArrayList list=new ArrayList();

	private FormFields() {}

	static FormFields construct(Segment segment) {
		FormFields formFields=new FormFields();
		// <input 
		formFields.loadInputControls(segment);
		// <textarea
		formFields.loadTextAreaControls(segment);
		// <button
		formFields.loadButtonControls(segment);
		// <select
		formFields.loadSelectControls(segment);
		Collections.sort(formFields.list,FormField.COMPARATOR);
		return formFields;
	}

	/**
	 * ���ʵ� ����
	 * @return  FormField ��ü ��
	 */
	public int getCount() {
		return list.size();
	}

	/**
	 * ���ʵ� ����
	 * @return  FormField ��ü ��
	 */
	public int size() {
		return getCount();
	}

	/**
	 * Returns the FormField with the specified name (case insensitive).
	 *
	 * @param  name  the name of the FormField to get.
	 * @return  the FormField with the specified name; null if no FormField with the specified name exists.
	 */
	public FormField get(String name) {
		return (FormField)map.get(name.toLowerCase());
	}

	/**
	 * Returns an iterator over the {@link FormField} objects in the list.
	 * @return  an iterator over the {@link FormField} objects in the list.
	 */
	public Iterator iterator() {
		return list.iterator();
	}

	/**
	 * Merges the specified FormFields into this FormFields collection.
	 * This is useful if a full list of possible FormFields is required from multiple {@link Source} documents.
	 * <p>
	 * If FormField objects with the same name appear in both collections, the resulting FormField will have the following properties:
	 * <ul>
	 * <li>{@link FormField#allowsMultipleValues() allowsMultipleValues} : <code>true</code> if either FormField allows multiple values</li>
	 * <li>{@link FormField#getPredefinedValues() getPredefinedValues} : the union of predefined values in both FormField objects</li>
	 * <li>{@link FormField#getUserValueCount() getUserValueCount} : the maximum user value count from both FormField objects</li>
	 * </ul>
	 * <p>
	 * NOTE: Some underlying data structures may end up being shared between both FormFields collections.
	 */
	public void merge(FormFields formFields) {
		for (Iterator i=formFields.iterator(); i.hasNext();) {
			FormField formField=(FormField)i.next();
			String name=formField.getName();
			FormField existingFormField=get(name);
			if (existingFormField==null)
				add(formField);
			else
				existingFormField.merge(formField);
		}
		Collections.sort(list,FormField.COMPARATOR);
	}

	/**
	 * Returns a string representation of this object useful for debugging purposes.
	 * @return  a string representation of this object useful for debugging purposes.
	 */
	public String toString() {
		StringBuffer sb=new StringBuffer();
		for (Iterator i=iterator(); i.hasNext();) {
			sb.append(i.next());
		}
		return sb.toString();
	}

	private void add(FormField formField) {
		map.put(formField.getName().toLowerCase(),formField);
		list.add(formField);
	}

	/**
	 * input �±� �м� (input �Ϸ�) 
	 * @param segment
	 */
	private void loadInputControls(Segment segment) {
		// input �±��� ��� EndTag�� �ʿ� ��8�Ƿ� 
		// findAllStartTags�� ��� 
		List inputTags=segment.findAllStartTags("input");
		if (inputTags==null) return;
		for (Iterator i=inputTags.iterator(); i.hasNext();) {
			StartTag startTag=(StartTag)i.next();
			Attributes attributes=startTag.getAttributes();
			List attributeList = attributes.getList();
			Attribute nameAttribute=attributes.get("name");
			Attribute sizeAttribute=attributes.get("size");
			if (nameAttribute==null) continue;
			FormControlType formControlType=startTag.getFormControlType();
			if (formControlType==null) continue;
			String predefinedValue=null;
			if (formControlType.isPredefinedValue()) {
				Attribute valueAttribute=attributes.get("value");
				if (valueAttribute!=null) predefinedValue=valueAttribute.getValue();  // only treat as a predefined value if it actually has a value.
			}
			String name=nameAttribute.getValue();
			
			String size = null;
			if ( sizeAttribute!= null ) {
				size = sizeAttribute.getValue();
			}
			registerField(name,startTag,size,predefinedValue,formControlType, attributeList);
			String[] additionalSubmitNames=formControlType.getAdditionalSubmitNames(name);
			if (additionalSubmitNames!=null) {
				for (int j=0; j<additionalSubmitNames.length; j++) {
					registerUserValueField(additionalSubmitNames[j],startTag, null, null, null, formControlType, attributeList);
				}
			}
		}
	}

	
	/**
	 * ��ư ��Ʈ�� �Ϸ� 
	 * @param segment
	 */
	private void loadButtonControls(Segment segment) {
		List buttonTags=segment.findAllStartTags("button");
		if (buttonTags==null) return;
		for (Iterator i=buttonTags.iterator(); i.hasNext();) {
			StartTag startTag=(StartTag)i.next();
			FormControlType formControlType=startTag.getFormControlType();
			if (formControlType==null) continue;
			Attributes attributes=startTag.getAttributes();
			Attribute nameAttribute=attributes.get("name");
			Attribute sizeAttribute=attributes.get("size");
			if (nameAttribute==null) continue;
			String name=nameAttribute.getValue();
			String size = null;
			if ( sizeAttribute!= null ) {
				size = sizeAttribute.getValue();
			}
			String predefinedValue=null;
			Attribute valueAttribute=attributes.get("value");
			if (valueAttribute!=null) predefinedValue=valueAttribute.getValue();  // only treat as a predefined value if it actually has a value.
			registerField(name,startTag,size,predefinedValue,formControlType, attributes.getList());
		}
	}

	
	/**
	 * Select ��Ʈ�� �۾� �Ϸ�
	 * @param segment
	 */
	private void loadSelectControls(Segment segment) {
		List selectElements=segment.findAllElements("select");
		if (selectElements==null) return;
		List optionTags=segment.findAllStartTags("option");

		Iterator selectIterator=selectElements.iterator();
		Element selectElement=(Element)selectIterator.next();
		
		if (optionTags==null){	// Option �� ��; ���.
			StartTag selectTag=selectElement.getStartTag();
			Attribute nameAttribute=selectTag.getAttributes().get("name");
			List attributeList = selectTag.getAttributes().getList(); //kbs
			registerPredefinedValueField( nameAttribute.getValue(), selectTag, null, null, selectTag.getFormControlType(), attributeList );
			return;
		}		
		
		Element lastSelectElement=null;
		String name=null;
		FormControlType formControlType=null;
		for (Iterator optionIterator=optionTags.iterator(); optionIterator.hasNext();) {
			StartTag optionTag=(StartTag)optionIterator.next();
			// find select element containing this option:
			List attributeList = selectElement.getStartTag().getAttributes().getList();
			while (optionTag.begin>selectElement.end) {
				if (!selectIterator.hasNext()) return;
				selectElement=(Element)selectIterator.next();
//				System.out.println(selectElement.getContentText());
			}
			
			if (selectElement!=lastSelectElement) {
				if (optionTag.begin<selectElement.begin) continue;  // option tag before current select element - ignore it
				StartTag selectTag=selectElement.getStartTag();
				formControlType=selectTag.getFormControlType();
				if (formControlType==null) throw new RuntimeException("Internal Error: FormControlType not recognised for select tag "+selectTag);
				Attribute nameAttribute=selectTag.getAttributes().get("name");
				
				if (nameAttribute==null) {
					// select element has no name - skip to the next one
					if (!selectIterator.hasNext()) return;
					selectElement=(Element)selectIterator.next();
					lastSelectElement=null;
					continue;
				}
				name=nameAttribute.getValue();
//				System.out.println("name : " + name);
				if (name==null) continue;
			}
			lastSelectElement=selectElement;
			String value;
			Attribute valueAttribute=optionTag.getAttributes().get("value");
//			System.out.println("111 : " + valueAttribute.getValue());
//			System.out.println("111 : " + optionTag.getFollowingTextSegment().getSourceTextNoWhitespace());
			if (valueAttribute!=null) {
				value=valueAttribute.getValue();
			} else {
				Segment valueSegment=optionTag.getFollowingTextSegment();
				value=valueSegment.getSourceTextNoWhitespace();
				if (value.length()==0) continue; // no value attribute and no content - ignore this option
			}
			
			registerPredefinedValueField(name,selectElement, null, value,formControlType, attributeList);
		}
	}

	private void registerField(String name, Segment segment, String size, String predefinedValue, FormControlType formControlType, List attributeList) {
		registerPredefinedValueField(name, segment, size, predefinedValue, formControlType, attributeList);
//		if (predefinedValue==null)
//			registerUserValueField(name, position);
//		else
//			registerPredefinedValueField(name, position, predefinedValue, formControlType);
	}

	private void registerUserValueField(String name, Segment segment, String rows, String cols, String content, FormControlType formControlType, List attributeList) {
		FormField formField=get(name);
		if (formField==null) {
			add(formField=new FormField(name,segment,formControlType, attributeList));
		} else {
			formField.setMultipleValues();
			//formField.setLowerPosition(position);
			formField.setSegment(segment);
			formField.setAttributeList(attributeList);
		}
		if ( rows != null ) formField.setRows(rows);
		if ( cols != null ) formField.setCols(cols);
		if ( content != null ) formField.setContent(content);
		formField.incrementUserValueCount();
	}

	private void registerPredefinedValueField(String name, Segment segment, String size, String predefinedValue, FormControlType formControlType, List attributeList) {
		FormField formField=get(name);
		if ( formField==null 
			|| formControlType.equals(FormControlType.RADIO) 
			|| formControlType.equals(FormControlType.CHECKBOX) ) 
		{
			if ( formField!=null ) {
				formField.setMultiple(true);
				add(formField=new FormField(name,segment,formControlType, attributeList));
				formField.setMultiple(true);
			} else {
				add(formField=new FormField(name,segment,formControlType, attributeList));
			}
		} else {
			formField.setMultipleValues(formControlType);
			//formField.setLowerPosition(position);
			formField.setSegment(segment);
			formField.setAttributeList(attributeList);
		}
		if ( size != null ) formField.setSize(size);
		formField.addPredefinedValue(predefinedValue);
	}

	private void loadTextAreaControls(Segment segment) {
		List textAreaElements = segment.findAllElements("textarea");
		
		if ( textAreaElements == null ) return;
		
		for ( Iterator iter=textAreaElements.iterator(); iter.hasNext(); ) {
			Element el = (Element) iter.next();
			
			String content = el.getContentText();
			
			StartTag startTag = (StartTag)el.getStartTag();
						
//			System.out.println(startTag.getSourceText());
			Attributes attributes=startTag.getAttributes();
			
			Attribute nameAttribute=attributes.get("name");
			if (nameAttribute==null) continue;
			String name=nameAttribute.getValue();
//			System.out.println("name : " + name);
			
			String rows = null;
			nameAttribute = attributes.get("rows");
			if (nameAttribute!=null) { 
				rows = nameAttribute.getValue();
//				System.out.println("rows : " + rows);
			}
			
			String cols = null;
			nameAttribute = attributes.get("cols");
			if (nameAttribute!=null) {
				cols = nameAttribute.getValue();
//				System.out.println("rows : " + cols);
			}
			
			FormControlType formControlType=startTag.getFormControlType();
//			System.out.println("formControlType : " + formControlType);
			registerUserValueField(name,el, rows, cols, content, formControlType, attributes.getList());
		}
		
//		List textareaTags=segment.findAllStartTags("textarea");
//		System.out.println("textareaTags : " + textareaTags.size());
//		if (textareaTags==null) return;
//		for (Iterator i=textareaTags.iterator(); i.hasNext();) {
//			StartTag startTag=(StartTag)i.next();
//			System.out.println(startTag.getSourceText());
//			Attributes attributes=startTag.getAttributes();
//			
//			Attribute nameAttribute=attributes.get("name");
//			if (nameAttribute==null) continue;
//			String name=nameAttribute.getValue();
//			System.out.println("name : " + name);
//			
//			nameAttribute = attributes.get("rows");
//			if (nameAttribute!=null) { 
//				String rows = nameAttribute.getValue();
//				System.out.println("rows : " + rows);
//			}
//			
//			nameAttribute = attributes.get("cols");
//			if (nameAttribute!=null) {
//				String cols = nameAttribute.getValue();
//				System.out.println("rows : " + cols);
//			}
//			
//			FormControlType formControlType=startTag.getFormControlType();
//			System.out.println("formControlType : " + formControlType);
//			registerUserValueField(name,startTag.begin);
//		}
	}

	private void registerTextArea(String name, Segment segment, String predefinedValue, FormControlType formControlType, List attributeList) {
		FormField formField=get(name);
		if (formField==null) {
			add(formField=new FormField(name,segment,formControlType, attributeList));
		} else {
			formField.setMultipleValues(formControlType);
			//formField.setLowerPosition(position);
			formField.setSegment(segment);
			formField.setAttributeList(attributeList);
		}
		formField.addPredefinedValue(predefinedValue);
	}
}

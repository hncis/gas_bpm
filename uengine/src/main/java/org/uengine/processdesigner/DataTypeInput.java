/*
 * Created on 2004-06-07
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package org.uengine.processdesigner;

import org.metaworks.inputter.SelectInput;
import org.uengine.contexts.ActivitySelectionContext;
import org.uengine.contexts.ComplexType;
import org.uengine.contexts.DatabaseSynchronizationOption;
import org.uengine.contexts.FileContext;
import org.uengine.contexts.HtmlFormContext;
import org.uengine.contexts.OfficeDocumentInstance;

/**
 * @author Jinyoung Jang
 *
 */
public class DataTypeInput extends SelectInput {
	public DataTypeInput(){
		super(
				new String[]{
					null,
					"Text",
					"Number",
					//"Real Number",
					"Date",
					"Yes or No",
					"File",
					"Activity Selection",
					"Complex Type",
					"Html Form"/*,
					"Office Document",*/
				},
				new Object[]{
					null,
					String.class,
					Number.class,
					//Double.class,
					java.util.Calendar.class,
					Boolean.class,
					FileContext.class,
					ActivitySelectionContext.class,
					ComplexType.class,
					HtmlFormContext.class/*,
					OfficeDocumentInstance.class,*/
				}
			);
	}

	public void setValue(Object arg0) {
		if(arg0!=null && Number.class.isAssignableFrom((Class)arg0))
			super.setValue(Number.class);
		
		super.setValue(arg0);
	}
}

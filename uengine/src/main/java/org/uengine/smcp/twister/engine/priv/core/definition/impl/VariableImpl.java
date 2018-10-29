/*
 * Created on 2004-05-08
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package org.uengine.smcp.twister.engine.priv.core.definition.impl;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class VariableImpl implements org.uengine.smcp.twister.engine.priv.core.definition.Variable{
	
	String name;
	String messageType;

		/**
		 * 
		 * @uml.property name="name"
		 */
		public String getName() {
			return name;
		}

		/**
		 * 
		 * @uml.property name="name"
		 */
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * 
		 * @uml.property name="messageType"
		 */
		public String getMessageType() {
			return messageType;
		}

		/**
		 * 
		 * @uml.property name="messageType"
		 */
		public void setMessageType(String messageType) {
			this.messageType = messageType;
		}

}

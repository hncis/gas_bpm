/*
 * Created on 2005. 3. 22.
 */
package org.uengine.contexts;

import java.io.Serializable;

/**
 * @author Jinyoung Jang
 */
public class DocumentContext extends FileContext{

	String templatePath;

	public String getTemplatePath() {
		return templatePath;
	}

	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}
}

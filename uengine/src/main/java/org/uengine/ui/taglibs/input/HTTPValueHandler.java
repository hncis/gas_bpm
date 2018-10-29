package org.uengine.ui.taglibs.input;

import java.util.Map;

interface HTTPValueHandler{
	   Object parseValue (Map parameterMap, String key);
}
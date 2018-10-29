/*
 * Created on 2005. 5. 4.
 */
package org.uengine.processdesigner;

/**
 * @author Jinyoung Jang
 */
public interface UndoableAction {
	public void undo(Object target);
	public void redo(Object target);
}

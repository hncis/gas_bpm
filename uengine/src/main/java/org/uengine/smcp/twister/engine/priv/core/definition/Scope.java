package org.uengine.smcp.twister.engine.priv.core.definition;

/**
 * A scope is an anonymous sub-process (inline defined) with a nested
 * activity, its own associated variables, fault handlers and compensation
 * handler.<br>
 * Scope won't be really implemented in a first version of the engine, it's
 * only present as a simple activity container. 
 */
public interface Scope extends StructuredActivity {
}

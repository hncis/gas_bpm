package org.uengine.smcp.twister.engine.priv.core.definition;

/**
 * A flow is an activity container used to provide concurreny in
 * activity execution and also to use a simple linkage between
 * activities (from, to) instead of a container oriented activity
 * organisation.<br>
 * A flow completes when all activities within the flow are completed.
 * @see org.smcp.twister.engine.priv.core.definition.Link
 */
public interface Flow extends StructuredActivity {
}

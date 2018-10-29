package org.uengine.smcp.twister.engine.priv.core.definition.impl;

import org.uengine.smcp.twister.engine.priv.core.definition.Sequence;

/**
 * A persistent implementation of the Sequence interface.
 * @see org.smcp.twister.engine.priv.core.Sequence
 * @hibernate.class table="SEQUENCE"
 * @hibernate.subclass discriminator-value="SEQ"
 */
public class SequenceImpl extends StructuredActivityImpl implements Sequence {

}

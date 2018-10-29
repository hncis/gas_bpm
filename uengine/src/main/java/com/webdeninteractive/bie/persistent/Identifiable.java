/*
-------------------------------------------------------------------
BIE is Copyright 2001-2004 Brunswick Corp.
-------------------------------------------------------------------
Please read the legal notices (docs/legal.txt) and the license
(docs/bie_license.txt) that came with this distribution before using
this software.
-------------------------------------------------------------------
*/
package com.webdeninteractive.bie.persistent;

import java.io.*;

/** A shareable persistent class that has independent identity */
public abstract
class	Identifiable  extends Persistent implements Serializable {

/** Initialize the object ID. */
protected
Identifiable(int OID) {
	this.OID =  OID;
}
/** Return the object ID. */
public
int	getOID() {
	return OID;
}
/** The object ID. */	
protected int	OID;
	
}

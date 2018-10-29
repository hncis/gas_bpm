package org.uengine.formmanager.html;

import java.util.*;

/**
 * Represents all <a href="http://www.w3.org/TR/html401/intro/sgmltut.html#h-3.2.2">attributes</a> of a particular {@link StartTag}.
 * <p>
 * Created using the {@link StartTag#getAttributes()} method.
 * <p>
 * This segment starts at the end of the StartTag's {@link StartTag#getName() name}
 * and ends before the closing '/', '?' or '&gt;' character of the StartTag.
 * <p>
 * See also the XML 1.0 specification for <a href="http://www.w3.org/TR/REC-xml#dt-attr">attributes</a>.
 *
 * @see StartTag
 * @see Attribute
 */
public final class Attributes extends Segment {
	private ArrayList attributeList=null;

	// parsing states:
	private static final int BETWEEN_ATTRIBUTES=0;
	private static final int IN_NAME=1;
	private static final int START_VALUE=2;
	private static final int IN_VALUE=3;

	private Attributes(Source source, int begin, int end, ArrayList attributeList) {
		super(source,begin,end);
		this.attributeList=attributeList;
	}

	/**
	 * called from StartTag constructor
	 * @param  source  the source document.
	 * @param  startTagBegin  the position of the beginning of the enclosing StartTag.
	 * @param  startTagName  the name of the enclosing StartTag.
	 */
	static Attributes construct(Source source, int startTagBegin, String startTagName) {
		int tagNameEnd=startTagBegin+1+startTagName.length();
		char closeChar=(startTagName.charAt(0)=='?' ? '?' : '/'); // closeChar will normally be '/' but can also be '?' for xml processing instructions like <?xml version="1.0"?>
		ArrayList attributeList=new ArrayList();
		String lsource=source.getSourceTextLowerCase();
		int i=tagNameEnd;
		int state=BETWEEN_ATTRIBUTES;
		char quote=' ';
		Segment nameSegment=null;
		String key=null;
		int currentBegin=-1;
		boolean terminatingCharacter=false;
		while (!terminatingCharacter) {
			char c=source.text.charAt(i);
			if (c=='>' || (c==closeChar && source.text.charAt(i+1)=='>')) terminatingCharacter=true;
			switch (state) {
				case BETWEEN_ATTRIBUTES:
					if (!isWhiteSpace(c)) {
						state=IN_NAME;
						currentBegin=i;
					}
					break;
				case IN_NAME:
					if (terminatingCharacter || c=='=' || isWhiteSpace(c)) {
						nameSegment=new Segment(source,currentBegin,i);
						key=nameSegment.getSourceText().toLowerCase();
						if (c=='=') {
							state=START_VALUE;
						} else {
							attributeList.add(new Attribute(source,key,nameSegment));
							state=BETWEEN_ATTRIBUTES;
						}
					}
					break;
				case START_VALUE:
					currentBegin=i;
					if (terminatingCharacter || isWhiteSpace(c)) {
						Segment valueSegment=new Segment(source,i,i);
						attributeList.add(new Attribute(source,key,nameSegment,valueSegment,valueSegment));
						state=BETWEEN_ATTRIBUTES;
						break;
					}
					if (c=='\'' || c=='"') {
						quote=c;
						currentBegin++;
					} else {
						quote=' ';
					}
					state=IN_VALUE;
					break;
				case IN_VALUE:
					if (terminatingCharacter || c==quote || (quote==' ' && isWhiteSpace(c))) {
						Segment valueSegment;
						Segment valueSegmentIncludingQuotes;
						if (quote==' ') {
							valueSegment=valueSegmentIncludingQuotes=new Segment(source,currentBegin,i);
						} else {
							if (terminatingCharacter) {
								// don't want to terminate, only encountered a >, /> or ?> in the middle of a quoted value
								terminatingCharacter=false;
								break;
							}
							valueSegment=new Segment(source,currentBegin,i);
							valueSegmentIncludingQuotes=new Segment(source,currentBegin-1,i+1);
						}
						attributeList.add(new Attribute(source, key, nameSegment, valueSegment, valueSegmentIncludingQuotes));
						state=BETWEEN_ATTRIBUTES;
					}
					break;
			}
			i++;
		}
		return new Attributes(source, tagNameEnd, i-1, attributeList);
	}

	/**
	 * Returns the attribute with the specified name (case insensitive).
	 * <p>
	 * If more than one attribute exists with the specified name (which is technically illegal HTML), the first is returned.
	 *
	 * @param  name  the name of the attribute to get.
	 * @return  the attribute with the specified name; null if no attribute with the specified name exists.
	 */
	public Attribute get(String name) {
		if (size()==0) return null;
		for (int i=0; i<size(); i++) {
			Attribute attribute=(Attribute)attributeList.get(i);
			if (attribute.key.equalsIgnoreCase(name)) return attribute;
		}
		return null;
	}

	/**
	 * Returns a <code>java.util.List</code> containing the {@link Attribute} objects.
	 * @return  a <code>java.util.List</code> containing the {@link Attribute} objects.
	 */
	public List getList() {
		return attributeList;
	}

	/**
	 * Convenience method to return the number of attributes in the list.
	 * <p>
	 * This is equivalent to calling <code>getList().size()</code>.
	 * @return  the number of attributes in the list.
	 * @see  #getList()
	 */
	public int getCount() {
		return attributeList.size();
	}

	/**
	 * Convenience method to return the number of attributes in the list.
	 * <p>
	 * This is equivalent to calling <code>getCount()</code>, and is only provided for consistency with the <code>java.util.Collection</code> interface.
	 * @return  the number of attributes in the list.
	 * @see  #getCount()
	 */
	public int size() {
		return getCount();
	}

	/**
	 * Convenience method to return an iterator over the {@link Attribute} objects in the list.
	 * <p>
	 * This is equivalent to calling <code>getList().iterator()</code>.
	 * @return  an iterator over the {@link Attribute} objects in the list.
	 * @see  #getList()
	 */
	public Iterator iterator() {
		return attributeList.iterator();
	}

	public String toString() {
		StringBuffer sb=new StringBuffer();
		sb.append("Attributes ").append(super.toString()).append(": ");
		if (attributeList==null) {
			sb.append("EMPTY");
		} else {
			sb.append('\n');
			for (Iterator i=attributeList.iterator(); i.hasNext();) {
				Attribute attribute=(Attribute)i.next();
				sb.append("  ").append(attribute.toString());
			}
		}
		return sb.toString();
	}
}

/*
 * Copyright 2001-2004 by HANWHA S&C Corp.,
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of HANWHA S&C Corp. ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with HANWHA S&C Corp.
 */
package org.uengine.formmanager.html;


/**
 * Represents an HTML <a href="http://www.w3.org/TR/html401/intro/sgmltut.html#h-3.2.1">element</a>,
 * which encompasses the {@link StartTag}, an optional {@link EndTag} and all content in between.
 * <p>
 * If the start tag has no corresponding end tag:
 * <ul>
 *  <li>
 *   If the end tag is {@link StartTag#isEndTagOptional() optional}, the end of the element occurs at the
 *   start of the next tag that implicitly terminates this type of element.
 *  <li>
 *   If the end tag is {@link StartTag#isEndTagForbidden() forbidden}, the element spans only the start tag.
 *  <li>
 *   If the end tag is {@link StartTag#isEndTagRequired() required}, the source HTML is invalid and the
 *   element spans only the start tag.
 *   No attempt is made by this library to determine how user agents might interpret invalid HTML.
 * </ul>
 * Note that this behaviour has changed since version 1.0, which didn't treated optional end tags the same as required end tags.
 * <p>
 * Created using the {@link Segment#findAllElements(String name)} or {@link StartTag#getElement()} method.
 * <p>
 * See also the XML 1.0 specification for <a href="http://www.w3.org/TR/REC-xml#dt-element">elements</a>.
 *
 * @see StartTag
 */
public final class Element extends Segment {
    private StartTag startTag;
    private EndTag endTag = null;

    Element(Source source, StartTag startTag, EndTag endTag) {
        super(source, startTag.begin,
            (endTag == null) ? startTag.end : endTag.end);
        this.startTag = startTag;
        this.endTag = ((endTag == null) || (endTag.length() == 0)) ? null : endTag;
    }

    /**
     * Returns the {@link #getContent() content} text of the element.
     * @return  the content text of the element, or <code>null</code> if the element is {@link #isEmpty() empty}.
     */
    public String getContentText() {
        return isEmpty() ? null
                         : source.text.substring(startTag.end, getContentEnd());
    }

    /**
     * Returns the segment representing the <a href="http://www.w3.org/TR/REC-xml#dt-content">content</a> of the element.
     * This is <code>null</code> if the element is {@link #isEmpty() empty}, otherwise everything between the
     * end of the start tag and the start of the end tag.
     * If the end tag is not present, the content reaches to the end of the element.
     * <p>
     * Note that the returned segment is newly created with every call to this method.
     *
     * @return  the segment representing the content of the element, or <code>null</code> if the element is {@link #isEmpty() empty}.
     */
    public Segment getContent() {
        return isEmpty() ? null
                         : new Segment(source, startTag.end, getContentEnd());
    }

    /**
     * Returns the start tag of the element.
     * @return  the start tag of the element.
     */
    public StartTag getStartTag() {
        return startTag;
    }

    /**
     * Returns the end tag of the element.
     * <p>
     * If the element has no end tag this method returns <code>null</code>.
     *
     * @return  the end tag of the element, or <code>null</code> if the element has no end tag.
     */
    public EndTag getEndTag() {
        return endTag;
    }

    /**
     * Returns the {@link StartTag#getName() name} of the StartTag of this element.
     * @return  the name of the StartTag of this element.
     */
    public String getName() {
        return startTag.getName();
    }

    /**
     * Indicates whether the element is <a href="http://www.w3.org/TR/REC-xml#dt-empty">empty</a>.
     *
     * @return  <code>true</code> if the element is empty, otherwise <code>false</code>.
     */
    public boolean isEmpty() {
        return startTag.end == getContentEnd();
    }

    /**
     * Indicates whether the element is an <a href="http://www.w3.org/TR/REC-xml#dt-eetag">empty element tag</a>.
     * This is signified by the characters "/&gt;" at the end of the start tag and the absence of an end tag.
     * Note that not every {@link #isEmpty() empty} element is an empty element tag.
     *
     * @return  <code>true</code> if the element is an empty element tag, otherwise <code>false</code>.
     * @see  #isEmpty()
     */
    public boolean isEmptyElementTag() {
        return startTag.isEmptyElementTag();
    }

    /**
     * Indicates whether an element with the given name is a
     * <a href="http://www.w3.org/TR/html401/sgml/loosedtd.html#block">block</a> element according to the
     * <a href="http://www.w3.org/TR/html401/sgml/loosedtd.html">HTML 4.01 Transitional DTD</a>.
     * <p>
     * A brief description of the difference between block and inline elements is given in the HTML 4.01
     * Specification section <a href="http://www.w3.org/TR/html401/struct/global.html#h-7.5.3">7.5.3</a>.
     *
     * @return  <code>true</code> if an element with the given name is a block element, otherwise <code>false</code>.
     */
    public static boolean isBlock(String name) {
        return Tag.isBlock(name);
    }

    /**
     * Indicates whether an element with the given name is an
     * <a href="http://www.w3.org/TR/html401/sgml/loosedtd.html#inline">inline</a> element according to the
     * <a href="http://www.w3.org/TR/html401/sgml/loosedtd.html">HTML 4.01 Transitional DTD</a>.
     * <p>
     * A brief description of the difference between block and inline elements is given in the HTML 4.01
     * Specification section <a href="http://www.w3.org/TR/html401/struct/global.html#h-7.5.3">7.5.3</a>.
     *
     * @return  <code>true</code> if an element with the given name is an inline element, otherwise <code>false</code>.
     */
    public static boolean isInline(String name) {
        return Tag.isInline(name);
    }

    public String toString() {
        return "Element " + super.toString() + ": " + startTag + "-" + endTag;
    }

    private int getContentEnd() {
        return (endTag != null) ? endTag.begin : end;
    }
}
/*
 * $Log: Element.java,v $
 * Revision 1.2  2007/12/05 02:31:24  curonide
 * *** empty log message ***
 *
 * Revision 1.5  2007/12/04 07:34:39  bpm
 * *** empty log message ***
 *
 * Revision 1.3  2007/12/04 05:25:49  bpm
 * *** empty log message ***
 *
 * Revision 1.1  2007/07/02 01:41:01  pongsor
 * Form management support
 *
 * Revision 1.1  2007/01/26 10:54:44  mahler
 * *** empty log message ***
 *
 * Revision 1.1  2007/01/26 10:41:16  mahler
 * *** empty log message ***
 *
 * Revision 1.1  2005/09/06 06:59:37  ghbpark
 * xcommons 2.0 start
 *
 * Revision 1.1  2005/04/11 10:24:20  ghbpark
 * *** empty log message ***
 *
 * Revision 1.1  2005/04/03 01:36:51  ghbpark
 * *** empty log message ***
 *
 * Revision 1.2  2004/05/11 03:48:47  ghbpark
 * html 파서 추가
 *
 */

package org.uengine.formmanager.html;

import java.util.*;


/**
 * Represents the <a href="http://www.w3.org/TR/html401/intro/sgmltut.html#didx-element-2">start tag</a> of an {@link Element}.
 * <p>
 * Created using one of the following methods:
 * <ul>
 *  <li>{@link Element#getStartTag()}
 *  <li>{@link Source#findPreviousStartTag(int pos)}
 *  <li>{@link Source#findPreviousStartTag(int pos, String name)}
 *  <li>{@link Source#findNextStartTag(int pos)}
 *  <li>{@link Source#findNextStartTag(int pos, String name)}
 *  <li>{@link Source#findEnclosingStartTag(int pos)}
 *  <li>{@link Segment#findAllStartTags(String name)}
 * </ul>
 * Note that an HTML {@link Segment#isComment() comment} is represented as a StartTag object.
 * <p>
 * See also the XML 1.0 specification for <a href="http://www.w3.org/TR/REC-xml#dt-stag">start tags</a>.
 *
 * @see Element
 * @see EndTag
 */
public final class StartTag extends Tag {
    static final StartTag CACHED_NULL = new StartTag();
    private Attributes attributes; // only null if isComment

    private StartTag(Source source, int begin, int end, String name,
        Attributes attributes) {
        super(source, begin, end,
            name.equals(COMMENT_NAME) ? COMMENT_NAME : name);
        this.attributes = attributes;
    }

    private StartTag() {
    }
     // used when creating CACHED_NULL

    private static StartTag construct(Source source, int begin, String name) {
        // it is necessary to get the attributes so that we can be sure that the search on the closing ">"
        // character doesn't pick up anything from the attribute values, which can legally contain
        // ">" characters. (see HTML 4.01 specification section 5.3.2 - Character Entity References)
        // This is not called for a comment tag
        Attributes attributes = Attributes.construct(source, begin, name);
        int end = attributes.end +
            ((source.text.charAt(attributes.end) == '>') ? 1 : 2);
        StartTag startTag = new StartTag(source, begin, end, name, attributes);

        return startTag;
    }

    /**
     * Indicates whether the corresponding end tag is forbidden.
     * <p>
     * This is the case if one of {@link #isEmptyElementTag()}, {@link #isProcessingInstruction()},
     * {@link #isDocTypeDeclaration()}, {@link #isComment()},
     * or {@link EndTag#isForbidden(String name) EndTag.isForbidden(getName())} is <code>true</code>.
     * <p>
     * Note that as of version 1.1, this method also takes the name of the tag into account
     * by checking whether the HTML specification forbids an end tag of this name.
     *
     * @return  <code>true</code> if the corresponding end tag is <i>forbidden</i>, otherwise <code>false</code>.
     */
    public boolean isEndTagForbidden() {
        return isComment() || isEmptyElementTag() || isProcessingInstruction() ||
        isDocTypeDeclaration() || EndTag.isForbidden(name);
    }

    /**
     * Indicates whether the corresponding end tag is <i>optional</i> according to the HTML specification.
     * <p>
     * This is equivalent to {@link EndTag#isOptional(String name) EndTag.isOptional(getName())}
     *
     * @return  <code>true</code> if the corresponding end tag is <i>optional</i>, otherwise <code>false</code>.
     */
    public boolean isEndTagOptional() {
        return isEndTagOptional(name);
    }

    /**
     * Indicates whether the corresponding end tag is <i>required</i> according to the HTML specification.
     * <p>
     * This is equivalent to {@link EndTag#isRequired(String name) EndTag.isRequired(getName())}
     *
     * @return  <code>true</code> if the corresponding end tag is <i>required</i>, otherwise <code>false</code>.
     */
    public boolean isEndTagRequired() {
        return isEndTagRequired(name);
    }

    // Documentation inherited by Segment
    public boolean isComment() {
        return name == COMMENT_NAME;
    }

    /**
     * Indicates whether the start tag is an <a href="http://www.w3.org/TR/REC-xml#dt-eetag">empty element tag</a>.
     * <p>
     * This is signified by the characters "/&gt;" at the end of the start tag.
     *
     * @return  <code>true</code> if the StartTag is an empty element tag, otherwise <code>false</code>.
     */
    public boolean isEmptyElementTag() {
        return source.text.charAt(end - 2) == '/';
    }

    /**
     * Indicates whether the start tag is an XML <a href="http://www.w3.org/TR/REC-xml#sec-pi">processing instruction</a>.
     * <p>
     * The following code is an example of a processing instruction:
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;
     * </pre>
     *
     * @return  <code>true</code> if the start tag is an XML processing instruction, otherwise <code>false</code>.
     */
    public boolean isProcessingInstruction() {
        return name.charAt(0) == '?';
    }

    /**
     * Indicates whether the start tag is a <a href="http://www.w3.org/TR/REC-xml#dt-doctype">document type declaration</a>.
     * <p>
     * The following code is an example of a document type declaration:
     * <pre>
     * &lt;!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"&gt;
     * </pre>
     *
     * @return  <code>true</code> if the start tag is a document type declaration, otherwise <code>false</code>.
     */
    public boolean isDocTypeDeclaration() {
        return (name.charAt(0) == '!') && !isComment();
    }

    /**
     * Returns the attributes specified in this start tag.
     * <p>
     * Guaranteed not <code>null</code> unless this start tag represents an HTML {@link #isComment() comment}.
     *
     * @return  the attributes specified in this start tag, or <code>null</code> if this start tag represents an HTML comment.
     */
    public Attributes getAttributes() {
        return attributes;
    }

    /**
     * Returns the {@link FormControlType} of this start tag.
     * @return  the form control type of this start tag, or <code>null</code> if it is not a <a href="http://www.w3.org/TR/html4/interact/forms.html#h-17.2">control</a>.
     */
    public FormControlType getFormControlType() {
        if (isComment() || !FormControlType.isPotentialControl(name)) {
            return null;
        }

        if (name.equals("textarea")) {
            return FormControlType.TEXTAREA;
        }

        Attributes attributes = getAttributes();

        if (name.equals("select")) {
            Attribute multiple = attributes.get("multiple");

            return (multiple != null) ? FormControlType.SELECT_MULTIPLE
                                      : FormControlType.SELECT_SINGLE;
        }

        Attribute type = attributes.get("type");

        if (name.equals("button")) {
            return ((type == null) ||
            type.getValue().equalsIgnoreCase("submit"))
            ? FormControlType.BUTTON : null;
        }

        // assume tag name "input":
        if (type == null) {
            return FormControlType.TEXT;
        }

        return FormControlType.get(type.getValue().toLowerCase());
    }

    /**
     * Returns the segment containing the text that immediately follows this start tag up until the start of the following tag.
     * <p>
     * Guaranteed not <code>null</code>.
     * @return  the segment containing the text that immediately follows this start tag up until the start of the following tag.
     */
    public Segment getFollowingTextSegment() {
        int endData = source.text.indexOf('<', end);

        if (endData == -1) {
            endData = source.length();
        }

        return new Segment(source, end, endData);
    }

    /**
     * Returns the end tag that corresponds to this start tag.
     * <p>
     * This method exists mainly for backward compatability with version 1.0.
     * <p>
     * The {@link #getElement()} method is much more useful as it will determine the span of the
     * element even if the end tag is {@link #isEndTagOptional() optional} and doesn't exist
     * (This is a new feature in version 1.1).
     * <p>
     * This method on the other hand will just return <code>null</code> in the above case, and
     * is equivalent to calling <code>getElement().getEndTag()</code>
     *
     * @return  the end tag that corresponds to this start tag, or <code>null</code> if none exists.
     */
    public EndTag findEndTag() {
        return getElement().getEndTag();
    }

    /**
     * Returns the element that corresponds to this start tag.
     * Guaranteed not <code>null</code>.
     * <p>
     * Note that as of version 1.1, this method returns an element spanning the logical
     * HTML <a href="http://www.w3.org/TR/html401/intro/sgmltut.html#h-3.2.1">element</a>
     * if the end tag is {@link #isEndTagOptional optional} but not present.
     * In this case the version 1.0 method returned an element spanning only the start tag.
     * <h4>Example 1: Elements that have {@link #isEndTagRequired() required} end tags</h4>
     * <pre>
     * 1. &lt;div&gt;
     * 2.   &lt;div&gt;
     * 3.     &lt;div&gt;
     * 4.       &lt;div&gt;This is line 4&lt;/div&gt;
     * 5.     &lt;/div&gt;
     * 6.     &lt;div&gt;This is line 6&lt;/div&gt;
     * 7.   &lt;/div&gt;</pre>
     * <ul>
     *  <li>The start tag on line 1 returns an empty element spanning only the start tag.
     *   This is because the end tag of a <code>&lt;div&gt;</code> element is required,
     *   making the sample code invalid as all the end tags are matched with other start tags.
     *  <li>The start tag on line 2 returns an element spanning to the end of line 7.
     *  <li>The start tag on line 3 returns an element spanning to the end of line 5.
     *  <li>The start tag on line 4 returns an element spanning to the end of line 4.
     *  <li>The start tag on line 6 returns an element spanning to the end of line 6.
     * </ul>
     * <h4>Example 2: Elements that have {@link #isEndTagOptional() optional} end tags</h4>
     * <pre>
     * 1. &lt;ul&gt;
     * 2.   &lt;li&gt;item 1
     * 3.   &lt;li&gt;item 2
     * 4.     &lt;ul&gt;
     * 5.       &lt;li&gt;subitem 1&lt;/li&gt;
     * 6.       &lt;li&gt;subitem 2
     * 7.     &lt;/ul&gt;
     * 8.   &lt;li&gt;item 3&lt;/li&gt;
     * 9. &lt;/ul&gt;</pre>
     * <ul>
     *  <li>The start tag on line 1 returns an element spanning to the end of line 9.
     *  <li>The start tag on line 2 returns an element spanning to the start of the <code>&lt;li&gt;</code> start tag on line 3.
     *  <li>The start tag on line 3 returns an element spanning to the start of the <code>&lt;li&gt;</code> start tag on line 8.
     *  <li>The start tag on line 4 returns an element spanning to the end of line 7.
     *  <li>The start tag on line 5 returns an element spanning to the end of line 5.
     *  <li>The start tag on line 6 returns an element spanning to the start of the <code>&lt;/ul&gt;</code> end tag on line 7.
     *  <li>The start tag on line 8 returns an element spanning to the end of line 8.
     * </ul>
     *
     * @return  the element that corresponds to this start tag.
     */
    public Element getElement() {
        String cacheKey = SearchCache.getElementKey(this);
        Element element = source.getSearchCache().getElement(cacheKey);

        if (element == null) {
            element = new Element(source, this, findEndTagInternal());
            source.getSearchCache().setElement(cacheKey, element);
        }

        return element;
    }

    private EndTag findEndTagInternal() {
        // This behaves the same as findEndTag(), except that a missing optional end tag will return a zero length EndTag instead of null
        if (isEndTagForbidden()) {
            return null;
        }

        TerminatorSets terminatorSets = getOptionalEndTagTerminatorSets();

        if (terminatorSets != null) { // end tag is optional

            return findOptionalEndTag(terminatorSets);
        }

        // end tag is required
        Segment[] findResult = findEndTag(source.getSourceTextLowerCase(),
                source.findNextEndTag(end, name));

        if (findResult == null) {
            return null;
        }

        return (EndTag) findResult[0];
    }

    public String toString() {
        return '"' + name + "\" " + super.toString();
    }

    private EndTag findOptionalEndTag(TerminatorSets terminatorSets) {
        Iterator i = source.getNextTagIterator(end);

        while (i.hasNext()) {
            Tag tag = (Tag) i.next();
            Set terminatorSet;

            if (tag instanceof EndTag) {
                if (tag.name.equals(name)) {
                    return (EndTag) tag;
                }

                terminatorSet = terminatorSets.getEndTagTerminatorSet();
            } else {
                terminatorSet = terminatorSets.getIgnoredNestedElementSet();

                if ((terminatorSet != null) &&
                        terminatorSet.contains(tag.name)) {
                    Element ignoredNestedElement = ((StartTag) tag).getElement();
                    i = source.getNextTagIterator(ignoredNestedElement.end);

                    continue;
                }

                terminatorSet = terminatorSets.getStartTagTerminatorSet();
            }

            if ((terminatorSet != null) && terminatorSet.contains(tag.name)) {
                return new EndTag(source, tag.begin, tag.begin, name);
            }
        }

        // Ran out of tags. The only legitimate case of this happening is if the end HTML tag is missing, in which case the end of the element is the end of the source document
        return new EndTag(source, source.end, source.end, name);
    }

    static StartTag findPreviousOrNext(Source source, int pos, String name,
        boolean previous) {
        String cacheKey = SearchCache.getStartTagKey(pos, name, previous);
        StartTag startTag = source.getSearchCache().getStartTag(cacheKey);

        if (startTag == null) {
            startTag = findPreviousOrNextUncached(source, pos, name, previous);
            source.getSearchCache().setStartTag(cacheKey, startTag);
        }

        return (startTag == CACHED_NULL) ? null : startTag;
    }

    private static StartTag findPreviousOrNextUncached(Source source, int pos,
        String name, boolean previous) {
        if (name == null) {
            return findPreviousOrNext(source, pos, previous);
        }

        name = (name.equals(COMMENT_NAME)) ? COMMENT_NAME : name.toLowerCase();

        try {
            int begin = pos;
            String searchString = '<' + name;
            String lsource = source.getSourceTextLowerCase();

            while (true) {
                begin = previous ? lsource.lastIndexOf(searchString, begin)
                                 : lsource.indexOf(searchString, begin);

                if (begin == -1) {
                    return null;
                }

                int nameEnd = begin + searchString.length();
                Segment enclosingComment = source.findEnclosingComment(begin -
                        1);

                if (name == COMMENT_NAME) {
                    if (enclosingComment != null) {
                        if (previous) {
                            return (StartTag) enclosingComment;
                        }

                        begin = enclosingComment.end;

                        continue;
                    }

                    int endCommentPos = source.text.indexOf("-->", nameEnd);

                    if (endCommentPos == -1) {
                        return null; // No matching end comment delimiter which is illegal, so ignore it.
                    }

                    return new StartTag(source, begin, endCommentPos + 3,
                        COMMENT_NAME, null);
                }

                if (!isIdentifierPart(lsource.charAt(nameEnd))) {
                    if (enclosingComment == null) {
                        break;
                    }

                    begin = (previous ? (enclosingComment.begin - 2)
                                      : enclosingComment.end);

                    continue;
                }

                begin = (previous ? (begin - 2) : nameEnd);
            }

            return construct(source, begin, name);
        } catch (IndexOutOfBoundsException ex) {
            return null;
        }
    }

    private static StartTag findPreviousOrNext(Source source, int pos,
        boolean previous) {
        // This has a known bug that it will recognise a < in an attribute value as the start of the tag.
        // See the comments in the documentation of the Source element for more details.
        try {
            Segment enclosingComment = source.findEnclosingComment(pos);

            if (enclosingComment != null) {
                if (previous) {
                    return (StartTag) enclosingComment;
                }

                pos = enclosingComment.end;
            }

            int begin = pos;

            while (true) {
                begin = previous ? source.text.lastIndexOf('<', begin)
                                 : source.text.indexOf('<', begin);

                if (begin == -1) {
                    return null;
                }

                if (source.text.charAt(begin + 1) != '/') {
                    break;
                }

                begin += (previous ? (-2) : 2);
            }

            int nameBegin = begin + 1;
            int identifierBegin = nameBegin;
            char nameFirstChar = source.text.charAt(nameBegin);

            if (nameFirstChar == '?') {
                identifierBegin++;
            } else if (nameFirstChar == '!') {
                identifierBegin++;

                if ((source.text.charAt(nameBegin + 1) == '-') &&
                        (source.text.charAt(nameBegin + 2) == '-')) {
                    // this is a comment tag
                    int end = source.text.indexOf("-->", nameBegin + 3) + 3;

                    if (end == 2) {
                        return findPreviousOrNext(source,
                            begin + (previous ? (-2) : 2), previous); // no matching end comment delimiter which is illegal, so ignore it
                    }

                    return new StartTag(source, begin, end, COMMENT_NAME, null);
                }
            }

            int nameEnd = source.getIdentifierEnd(identifierBegin);

            if (nameEnd == -1) { // not a valid identifier, just ignore it.

                return findPreviousOrNext(source, begin + (previous ? (-2) : 2),
                    previous);
            }

            String name = source.text.substring(nameBegin, nameEnd);

            return construct(source, begin, name);
        } catch (IndexOutOfBoundsException ex) {
            return null;
        }
    }

    private Segment[] findEndTag(String lsource, EndTag nextEndTag) {
        return findEndTag(lsource, end, source.findNextStartTag(end, name),
            nextEndTag);
    }

    private Segment[] findEndTag(String lsource, int afterPos,
        StartTag nextStartTag, EndTag nextEndTag) {
        // returns null if no end tag exists in the rest of the file, otherwise the following two segments:
        // first is the matching end tag to this start tag.  Must be present if array is returned.
        // second is the next occurrence after the returned end tag of a start tag of the same name. (null if none exists)
        if (nextEndTag == null) {
            return null; // no end tag in the rest of the file
        }

        Segment[] returnArray = { nextEndTag, nextStartTag };

        if ((nextStartTag == null) || (nextStartTag.begin > nextEndTag.begin)) {
            return returnArray; // no more start tags of the same name in rest of file, or they occur after the end tag that we found.  This means we have found the matching end tag.
        }

        Segment[] findResult = nextStartTag.findEndTag(lsource, nextEndTag); // find the matching end tag to the interloping start tag

        if (findResult == null) {
            return null; // no end tag in the rest of the file
        }

        EndTag nextStartTagsEndTag = (EndTag) findResult[0];
        nextStartTag = (StartTag) findResult[1];
        nextEndTag = source.findNextEndTag(nextStartTagsEndTag.end, name); // find end tag after the interloping start tag's end tag

        return findEndTag(lsource, nextStartTagsEndTag.end, nextStartTag,
            nextEndTag); // recurse to see if this is the matching end tag
    }
}
/*
 * $Log: StartTag.java,v $
 * Revision 1.2  2007/12/05 02:31:24  curonide
 * *** empty log message ***
 *
 * Revision 1.5  2007/12/04 07:34:39  bpm
 * *** empty log message ***
 *
 * Revision 1.3  2007/12/04 05:25:48  bpm
 * *** empty log message ***
 *
 * Revision 1.1  2007/07/02 01:41:01  pongsor
 * Form management support
 *
 * Revision 1.1  2007/01/26 10:54:44  mahler
 * *** empty log message ***
 *
 * Revision 1.1  2007/01/26 10:41:15  mahler
 * *** empty log message ***
 *
 * Revision 1.1  2005/09/06 06:59:37  ghbpark
 * xcommons 2.0 start
 *
 * Revision 1.1  2005/04/11 10:24:20  ghbpark
 * *** empty log message ***
 *
 * Revision 1.1  2005/04/03 01:36:52  ghbpark
 * *** empty log message ***
 *
 * Revision 1.2  2004/05/11 03:48:47  ghbpark
 * html 파서 추가
 *
 */

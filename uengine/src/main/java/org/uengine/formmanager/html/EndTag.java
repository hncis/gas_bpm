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
 * Represents the <a href="http://www.w3.org/TR/html401/intro/sgmltut.html#didx-element-3">end tag</a> of an {@link Element}.
 * <p>
 * Created using the {@link StartTag#findEndTag()}, {@link Source#findPreviousEndTag(int pos, String name)} or {@link Source#findNextEndTag(int pos, String name)} method.
 * <p>
 * See also the XML 1.0 specification for <a href="http://www.w3.org/TR/REC-xml#dt-etag">end tags</a>.
 *
 * @see StartTag
 * @see Element
 */
public final class EndTag extends Tag {
    static final EndTag CACHED_NULL = new EndTag();

    private EndTag() {
    }
     // used when creating CACHED_NULL

    /**
     * Constructor called from {@link #findNext(Source source, int pos)}, {@link StartTag#findEndTag()} and {@link #findPreviousOrNext(Source source, int pos, String name, boolean previous)}
     *
     * @param  source  the source document.
     * @param  begin  the beginning of the segment.
     * @param  end  the end of the element.
     * @param  name  the name of the tag.
     */
    EndTag(Source source, int begin, int end, String name) {
        super(source, begin, end, name);
    }

    /**
     * Indicates whether an end tag of the given name is <i>forbidden</i> according to the HTML specification.
     * <p>
     * An overview of this information for all tags can be found in the HTML
     * <a href="http://www.w3.org/TR/html401/index/elements.html">index of elements</a>.
     *
     * @return  <code>true</code> if an end tag of the given name is <i>forbidden</i>, otherwise <code>false</code>.
     */
    public static boolean isForbidden(String name) {
        return isEndTagForbidden(name);
    }

    /**
     * Indicates whether an end tag of the given name is <i>optional</i> according to the HTML specification.
     * <p>
     * An overview of this information for all tags can be found in the HTML
     * <a href="http://www.w3.org/TR/html401/index/elements.html">index of elements</a>.
     *
     * @return  <code>true</code> if an end tag of the given name is <i>optional</i>, otherwise <code>false</code>.
     */
    public static boolean isOptional(String name) {
        return isEndTagOptional(name);
    }

    /**
     * Indicates whether an end tag of the given name is <i>required</i> according to the HTML specification.
     * <p>
     * An overview of this information for all tags can be found in the HTML
     * <a href="http://www.w3.org/TR/html401/index/elements.html">index of elements</a>.
     * It is assumed that an end tag is <i>required</i> if it is not <i>forbidden</i> or <i>optional</i>.
     *
     * @return  <code>true</code> if an end tag of the given name is <i>required</i>, otherwise <code>false</code>.
     */
    public static boolean isRequired(String name) {
        return isEndTagRequired(name);
    }

    /**
     * Returns the previous or next end tag matching the specified name, starting at the specified position.
     * <p>
     * Called from {@link Source#findPreviousEndTag(int pos, String name)} and {@link Source#findNextEndTag(int pos, String name)}.
     *
     * @param  source  the source document.
     * @param  pos  the position to search from.
     * @param  name  the name of the tag (must be lower case and not null).
     * @param  previous  search backwards if true, otherwise search forwards.
     * @return  the previous or next end tag matching the specified name, starting at the specified position, or null if none is found.
     */
    static EndTag findPreviousOrNext(Source source, int pos, String name,
        boolean previous) {
        String cacheKey = SearchCache.getEndTagKey(pos, name, previous);
        EndTag endTag = source.getSearchCache().getEndTag(cacheKey);

        if (endTag == null) {
            endTag = findPreviousOrNextUncached(source, pos, name, previous);
            source.getSearchCache().setEndTag(cacheKey, endTag);
        }

        return (endTag == CACHED_NULL) ? null : endTag;
    }

    private static EndTag findPreviousOrNextUncached(Source source, int pos,
        String name, boolean previous) {
        name = name.toLowerCase();

        Segment enclosingComment = source.findEnclosingComment(pos);

        if (enclosingComment != null) {
            pos = (previous ? enclosingComment.begin : enclosingComment.end);
        }

        try {
            String searchString = "</" + name + ">";
            String lsource = source.getSourceTextLowerCase();
            int begin = previous ? lsource.lastIndexOf(searchString, pos)
                                 : lsource.indexOf(searchString, pos);

            if (begin == -1) {
                return null;
            }

            return new EndTag(source, begin, begin + searchString.length(), name);
        } catch (IndexOutOfBoundsException ex) {
            return null;
        }
    }

    static EndTag findNext(Source source, int pos) {
        String cacheKey = SearchCache.getEndTagKey(pos);
        EndTag endTag = source.getSearchCache().getEndTag(cacheKey);

        if (endTag == null) {
            endTag = findNextUncached(source, pos);
            source.getSearchCache().setEndTag(cacheKey, endTag);
        }

        return (endTag == CACHED_NULL) ? null : endTag;
    }

    private static EndTag findNextUncached(Source source, int pos) {
        try {
            Segment enclosingComment = source.findEnclosingComment(pos);

            if (enclosingComment != null) {
                return findNext(source, enclosingComment.end);
            }

            int begin = source.text.indexOf("</", pos);

            if (begin == -1) {
                return null;
            }

            int nameBegin = begin + 2;
            int nameEnd = source.getIdentifierEnd(nameBegin);

            if (nameEnd == -1) { // not a valid identifier, keep looking.

                return findNext(source, nameBegin);
            }

            if (source.text.charAt(nameEnd) != '>') { // closing bracket does not appear immediately after name, so keep looking

                return findNext(source, nameEnd);
            }

            String name = source.text.substring(nameBegin, nameEnd);

            return new EndTag(source, begin, nameEnd + 1, name);
        } catch (IndexOutOfBoundsException ex) {
            return null;
        }
    }
}
/*
 * $Log: EndTag.java,v $
 * Revision 1.2  2007/12/05 02:31:24  curonide
 * *** empty log message ***
 *
 * Revision 1.5  2007/12/04 07:34:38  bpm
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

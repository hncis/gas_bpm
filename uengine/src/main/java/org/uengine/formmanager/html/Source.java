package org.uengine.formmanager.html;

import java.util.*;


/**
 * Represents a source HTML document.
 * <p>
 * Note that many of the useful functions which can be performed on the source document are
 * defined in its superclass, {@link Segment}.
 * The Source object is itself a Segment which spans the entire document.
 * <p>
 * Most of the methods defined in this class are useful for determining the elements and tags
 * surrounding or neighbouring a particular character position in the document.
 * <p>
 * <b>IMPORTANT NOTE</b>: The {@link #findPreviousStartTag(int pos)}, {@link #findEnclosingStartTag(int pos)},
 * and {@link #findEnclosingElement(int pos)} methods contain a known bug which may result in incorrect return
 * values if any attribute values contain the '<code>&lt;</code>' character.
 * This is technically allowed in HTML, although discouraged,
 * (see section <a href="http://www.w3.org/TR/html401/charset.html#h-5.3.2">5.3.2</a> of the HTML spec), but is
 * disallowed in XHTML (see section <a href="http://www.w3.org/TR/REC-xml#CleanAttrVals">3.1</a> of the XML spec).
 *
 * @see Segment
 */
public final class Source extends Segment {
    String text;
    private String lowerCase = null;
    private SearchCache searchCache = null;

    /**
     * Constructs a new Source object with the specified source text.
     * @param  text  the source text.
     */
    public Source(String text) {
        super(0, text.length());
        source = this;
        this.text = text;
    }

    /**
     * Returns the source text.
     * <p>
     * Note that the returned String is not newly created with every call to this method,
     * as is the case with other {@link Segment} subclasses.
     *
     * @return  the source text.
     */
    public String getSourceText() {
        return text;
    }

    /**
     * Returns the source text.
     * @return  the source text.
     */
    public String toString() {
        return text;
    }

    /**
     * Returns the source text in lower case.
     * <p>
     * Note that the returned String is cached after the first call, so multiple calls to
     * this method can be made efficiently.
     *
     * @return  the source text in lower case.
     */
    public final String getSourceTextLowerCase() {
        if (lowerCase == null) {
            lowerCase = text.toLowerCase();
        }

        return lowerCase;
    }

    /**
     * Returns the StartTag immediately preceding (or enclosing) the specified position in the source document.
     * <p>
     * If the specified position is within an HTML {@link Segment#isComment() comment}, the segment
     * spanning the comment is returned.
     *
     * @param  pos  the position in the source document from which to start the search.
     * @return  the StartTag immediately preceding the specified position in the source document, or <code>null</code> if none exists.
     */
    public StartTag findPreviousStartTag(int pos) {
        return findPreviousStartTag(pos, null);
    }

    /**
     * Returns the StartTag with the specified name immediately preceding (or enclosing) the specified position in the source document.
     * <p>
     * Start tags positioned within an HTML {@link Segment#isComment() comment} are ignored, but the comment segment itself is treated as a start tag.
     * <p>
     * Specifying a <code>null</code> name parameter is equivalent to calling {@link #findPreviousStartTag(int) findPreviousStartTag(pos)}.
     *
     * @param  pos  the position in the source document from which to start the search.
     * @param  name  the {@link StartTag#getName() name} of the StartTag to search for.
     * @return  the StartTag with the specified name immediately preceding the specified position in the source document, or <code>null</code> if none exists.
     */
    public StartTag findPreviousStartTag(int pos, String name) {
        return StartTag.findPreviousOrNext(this, pos, name, true);
    }

    /**
     * Returns the StartTag beginning at or immediately following the specified position in the source document.
     * <p>
     * StartTags positioned within an HTML {@link Segment#isComment() comment} are ignored, but subsequent comment segments are treated as start tags.
     *
     * @param  pos  the position in the source document from which to start the search.
     * @return  the StartTag beginning at or immediately following the specified position in the source document, or <code>null</code> if none exists.
     */
    public StartTag findNextStartTag(int pos) {
        return findNextStartTag(pos, null);
    }

    /**
     * Returns the StartTag with the specified name beginning at or immediately following the specified position in the source document.
     * <p>
     * Start tags positioned within an HTML {@link Segment#isComment() comment} are ignored.
     * <p>
     * Specifying a <code>null</code> name parameter is equivalent to calling {@link #findNextStartTag(int) findNextStartTag(pos)}.
     *
     * @param  pos  the position in the source document from which to start the search.
     * @param  name  the {@link StartTag#getName() name} of the StartTag to search for.
     * @return  the StartTag with the specified name beginning at or immediately following the specified position in the source document, or <code>null</code> if none exists.
     */
    public StartTag findNextStartTag(int pos, String name) {
        return StartTag.findPreviousOrNext(this, pos, name, false);
    }

    /**
     * Returns the Comment beginning at or immediately following the specified position in the source document.
     * <p>
     * If the specified position is within a comment, the comment following the enclosing comment is returned.
     *
     * @param  pos  the position in the source document from which to start the search.
     * @return  the Comment beginning at or immediately following the specified position in the source document, or <code>null</code> if none exists.
     */
    public StartTag findNextComment(int pos) {
        return findNextStartTag(pos, COMMENT_NAME);
    }

    /**
     * Returns the EndTag with the specified name immediately preceding (or enclosing) the specified position in the source document.
     * <p>
     * End tags positioned within an HTML {@link Segment#isComment() comment} are ignored.
     *
     * @param  pos  the position in the source document from which to start the search.
     * @param  name  the {@link StartTag#getName() name} of the EndTag to search for.
     * @return  the EndTag immediately preceding the specified position in the source document, or <code>null</code> if none exists.
     */
    public EndTag findPreviousEndTag(int pos, String name) {
        return EndTag.findPreviousOrNext(this, pos, name, true);
    }

    /**
     * Returns the EndTag beginning at or immediately following the specified position in the source document.
     * <p>
     * End tags positioned within an HTML {@link Segment#isComment() comment} are ignored.
     *
     * @param  pos  the position in the source document from which to start the search.
     * @return  the EndTag beginning at or immediately following the specified position in the source document, or <code>null</code> if none exists.
     */
    public EndTag findNextEndTag(int pos) {
        return EndTag.findNext(this, pos);
    }

    /**
     * Returns the EndTag with the specified name beginning at or immediately following the specified position in the source document.
     * <p>
     * End tags positioned within an HTML {@link Segment#isComment() comment} are ignored.
     *
     * @param  pos  the position in the source document from which to start the search.
     * @param  name  the {@link StartTag#getName() name} of the EndTag to search for.
     * @return  the EndTag with the specified name beginning at or immediately following the specified position in the source document, or <code>null</code> if none exists.
     */
    public EndTag findNextEndTag(int pos, String name) {
        return EndTag.findPreviousOrNext(this, pos, name, false);
    }

    /**
     * Returns an iterator of {@link Tag} objects beginning at or immediately following the specified position in the source document.
     * <p>
     * Tags positioned within an HTML {@link Segment#isComment() comment} are ignored, but the comment segments themselves are treated as start tags.
     *
     * @param  pos  the position in the source document from which to start the iteration.
     * @return  an iterator of {@link Tag} objects beginning at or immediately following the specified position in the source document.
     */
    public Iterator getNextTagIterator(int pos) {
        return Tag.getNextTagIterator(this, pos);
    }

    /**
     * Returns the tag (either a {@link StartTag} or {@link EndTag}) beginning at or immediately following the specified position in the source document.
     * <p>
     * <i>IMPLEMENTATION NOTE: Sequential tags in a document should be retrieved using the iterator from
     * {@link #getNextTagIterator(int pos)} as it is far more efficient than using multiple calls to this method.</i>
     *
     * @param  pos  the position in the source document from which to start the search.
     * @return  the tag beginning at or immediately following the specified position in the source document, or <code>null</code> if none exists.
     * @see  #getNextTagIterator(int pos)
     */
    public Tag findNextTag(int pos) {
        Iterator i = getNextTagIterator(pos);

        return i.hasNext() ? (Tag) i.next() : null;
    }

    /**
     * Returns the StartTag enclosing the specified position in the source document.
     * <p>
     * If the specified position is within an HTML {@link Segment#isComment() comment}, the segment
     * spanning the comment is returned.
     *
     * @param  pos  the position in the source document.
     * @return  the StartTag enclosing the specified position in the source document, or <code>null</code> if the position is not within a StartTag.
     */
    public StartTag findEnclosingStartTag(int pos) {
        return findEnclosingStartTag(pos, null);
    }

    /**
     * Returns a Segment spanning the HTML {@link Segment#isComment() comment} that encloses the specified position in the source document.
     *
     * @param  pos  the position in the source document.
     * @return  a Segment spanning the HTML {@link Segment#isComment() comment} that encloses the specified position in the source document, or <code>null</code> if the position is not within a comment.
     */
    public Segment findEnclosingComment(int pos) {
        return findEnclosingStartTag(pos, COMMENT_NAME);
    }

    /**
     * Returns the most nested Element enclosing the specified position in the source document.
     * <p>
     * If the specified position is within an HTML {@link Segment#isComment() comment}, the segment
     * spanning the comment is returned.
     *
     * @param  pos  the position in the source document.
     * @return  the most nested Element enclosing the specified position in the source document, or <code>null</code> if the position is not within an Element.
     */
    public Element findEnclosingElement(int pos) {
        return findEnclosingElement(pos, null);
    }

    /**
     * Returns the most nested Element with the specified name enclosing the specified position in the source document.
     * <p>
     * Elements positioned within an HTML {@link Segment#isComment() comment} are ignored, but the comment segment itself is treated as an Element.
     *
     * @param  pos  the position in the source document.
     * @param  name  the {@link Element#getName() name} of the Element to search for.
     * @return  the most nested Element with the specified name enclosing the specified position in the source document, or <code>null</code> if none exists.
     */
    public Element findEnclosingElement(int pos, String name) {
        int startBefore = pos;

        while (true) {
            StartTag startTag = findPreviousStartTag(startBefore, name);

            if (startTag == null) {
                return null;
            }

            Element element = startTag.getElement();

            if (pos < element.end) {
                return element;
            }

            startBefore = startTag.begin - 1;
        }
    }

    final int getIdentifierEnd(int pos) {
        if (!isIdentifierStart(text.charAt(pos))) {
            return -1;
        }

        while (true) {
            if (!isIdentifierPart(text.charAt(++pos))) {
                return pos;
            }
        }
    }

    private StartTag findEnclosingStartTag(int pos, String name) {
        StartTag startTag = findPreviousStartTag(pos, name);

        if ((startTag == null) || (startTag.end <= pos)) {
            return null;
        }

        return startTag;
    }

    final SearchCache getSearchCache() {
        if (searchCache == null) {
            searchCache = new SearchCache();
        }

        return searchCache;
    }
}
/*
 * $Log: Source.java,v $
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

package org.uengine.formmanager.html;

import java.util.*;


/**
 * Represents a segment of a {@link Source} document.
 */
public class Segment {
    static final String COMMENT_NAME = "!--";
    private static final String WHITESPACE = " \n\r\t";
    int begin;
    int end;
    Source source;

    /**
     * Constructs a new Segment with the specified source and the specified begin and end positions.
     * @param  source  the source document.
     * @param  begin  the character position in the source where this segment begins.
     * @param  end  the character position in the source where this segment ends.
     */
    public Segment(Source source, int begin, int end) {
        this(begin, end);

        if (source == null) {
            throw new IllegalArgumentException(
                "source argument must not be null");
        }

        this.source = source;
    }

    // Only called from Source constructor
    Segment(int begin, int end) {
        if ((begin == -1) || (end == -1) || (begin > end)) {
            throw new IllegalArgumentException();
        }

        this.begin = begin;
        this.end = end;
    }

    Segment() {
    }
     // used when creating CACHED_NULL objects

    /**
     * Returns the character position in the Source where this segment begins.
     * @return  the character position in the Source where this segment begins.
     */
    public final int getBegin() {
        return begin;
    }

    /**
     * Returns the character position in the Source where this segment ends.
     * @return  the character position in the Source where this segment ends.
     */
    public final int getEnd() {
        return end;
    }

    /**
     * Compares the specified object with this Segment for equality.
     * <p>
     * Returns <code>true</code> if and only if the specified object is also a Segment,
     * and both segments have the same source, and the same begin and end positions.
     * @param  object  the object to be compared for equality with this Segment.
     * @return  <code>true</code> if the specified object is equal to this Segment, otherwise <code>false</code>.
     */
    public final boolean equals(Object object) {
        if ((object == null) || !(object instanceof Segment)) {
            return false;
        }

        Segment segment = (Segment) object;

        return (segment.begin == begin) && (segment.end == end) &&
        (segment.source == source);
    }

    /**
     * Returns the length of the Segment.
     * This is defined as the number of characters between the begin and end positions.
     * @return  the length of the Segment.
     */
    public final int length() {
        return end - begin;
    }

    /**
     * Indicates whether this Segment encloses the specified Segment.
     * @param  segment  the segment to be tested for being enclosed by this segment.
     * @return  <code>true</code> if this Segment encloses the specified Segment, otherwise <code>false</code>.
     */
    public final boolean encloses(Segment segment) {
        return (begin <= segment.begin) && (end >= segment.end);
    }

    /**
     * Indicates whether this Segment encloses the specified character position in the {@link Source} document.
     * <p>
     * This is the case if <code>{@link #getBegin()} <= pos < {@link #getEnd()}</code>.
     *
     * @param  pos  the position in the source document to be tested.
     * @return  <code>true</code> if this Segment encloses the specified position, otherwise <code>false</code>.
     */
    public final boolean encloses(int pos) {
        return (begin <= pos) && (pos < end);
    }

    /**
     * Indicates whether this Segment represents an HTML <a href="http://www.w3.org/TR/html401/intro/sgmltut.html#h-3.2.4">comment</a>.
     * <p>
     * An HTML comment is an area of the source document enclosed by the delimiters
     * <code>&lt;!--</code> on the left and <code>--&gt;</code> on the right.
     * <p>
     * The HTML 4.01 Specification section <a href="http://www.w3.org/TR/html401/intro/sgmltut.html#h-3.2.4">3.2.4</a>
     * states that the end of comment delimiter may contain whitespace between the "<code>--</code>" and "<code>&gt;</code>" characters,
     * but this library does not recognise end of comment delimiters containing whitespace.
     *
     * @return  <code>true</code> if this Segment represents an HTML comment, otherwise <code>false</code>.
     */
    public boolean isComment() {
        return false; // overridden in StartTag
    }

    /**
     * Returns the source text of this segment.
     * <p>
     * Note that the returned String is newly created with every call to this method, unless this
     * segment is itself a {@link Source} object.
     *
     * @return  the source text of this segment.
     */
    public String getSourceText() {
        return source.text.substring(begin, end);
    }

    /**
     * Returns the source text of this segment without {@link #isWhiteSpace(char) whitespace}.
     * All leading and trailing whitespace is omitted, and any sections of internal whitespace are replaced by a single space.
     * Note that any markup contained in this segment will be regarded as normal text for the purposes of this method.
     * @return  the source text of this segment without whitespace.
     */
    public final String getSourceTextNoWhitespace() {
        StringBuffer sb = new StringBuffer();
        int i = begin;
        boolean lastWasWhitespace = true;
        boolean isWhitespace = false;

        while (i < end) {
            char c = source.text.charAt(i++);

            if (isWhitespace = isWhiteSpace(c)) {
                if (!lastWasWhitespace) {
                    sb.append(' ');
                }
            } else {
                sb.append(c);
            }

            lastWasWhitespace = isWhitespace;
        }

        if (isWhitespace) {
            sb.setLength(Math.max(0, sb.length() - 1));
        }

        return sb.toString();
    }

    /**
     * Returns a list of Segment objects representing every word in this segment separated by {@link #isWhiteSpace(char) whitespace}.
     * Note that any markup contained in this segment will be regarded as normal text for the purposes of this method.
     *
     * @return  a list of Segment objects representing every word in this segment separated by whitespace.
     */
    public final List findWords() {
        ArrayList words = new ArrayList();
        int wordBegin = -1;

        for (int i = begin; i < end; i++) {
            if (isWhiteSpace(source.text.charAt(i))) {
                if (wordBegin == -1) {
                    continue;
                }

                words.add(new Segment(source, wordBegin, i));
                wordBegin = -1;
            } else {
                if (wordBegin == -1) {
                    wordBegin = i;
                }
            }
        }

        if (wordBegin != -1) {
            words.add(new Segment(source, wordBegin, end));
        }

        return words;
    }

    /**
     * StartTag 만을 분석 (EndTag 제외)
     * @param  name  the {@link StartTag#getName() name} of the StartTags to find.
     * @return  a list of all StartTag objects with the specified name enclosed by this Segment.
     */
    public List findAllStartTags(String name) {
        StartTag startTag = findNextStartTag(begin, name);

        if (startTag == null) {
            return null;
        }

        ArrayList list = new ArrayList();

        do {
            list.add(startTag);
            startTag = findNextStartTag(startTag.end, name);
        } while (startTag != null);

        return list;
    }

    /**
     * Returns a list of all {@link Segment} objects enclosed by this Segment that represent HTML {@link #isComment() comments}.
     * @return  a list of all Segment objects enclosed by this Segment that represent HTML comments.
     */
    public List findAllComments() {
        return findAllStartTags(COMMENT_NAME);
    }

    /**
     * Returns a list of all {@link Element} objects enclosed by this Segment.
     * @return  a list of all Element objects enclosed by this Segment.
     */
    public List findAllElements() {
        return findAllElements(null);
    }

    /**
     * 모든 엘리먼트를 찾는다.
     * @param  name  the {@link Element#getName() name} of the Elements to find.
     * @return  a list of all Element objects with the specified name enclosed by this Segment.
     */
    public List findAllElements(String name) {
        List startTags = findAllStartTags(name);

        if (startTags == null) {
            return null;
        }

        ArrayList elements = new ArrayList(startTags.size());

        for (Iterator i = startTags.iterator(); i.hasNext();) {
            StartTag startTag = (StartTag) i.next();
            Element element = startTag.getElement();

            if (element.end > end) {
                break;
            }

            elements.add(element);
        }

        return elements;
    }

    /**
     * 문서안에 포함된 모든 폼 필드를 찾아서 분석
     */
    public FormFields findFormFields() {
        return FormFields.construct(this);
    }

    /**
     * Indicates whether the specified character is whitespace.
     * Whitespace is considered to be either a space, tab, carriage return or line feed character.
     * @param  c  the character to test.
     * @return  <code>true</code> if the specified character is whitespace, otherwise <code>false</code>.
     */
    public static final boolean isWhiteSpace(char c) {
        return WHITESPACE.indexOf(c) != -1;
    }

    /**
     * Indicates whether the specified character can be the first character of a StartTag {@link StartTag#getName() name}.
     * This is true if and only if the character is a letter, or an exclamation mark (!).
     * @param  c  the character to test.
     * @return  <code>true</code> if the specified character can be the first character of a StartTag name, otherwise <code>false</code>.
     */
    static boolean isIdentifierStart(char c) {
        return Character.isLetter(c) || (c == '!');
    }

    /**
     * Indicates whether the specified character can be part of a StartTag {@link StartTag#getName() name}.
     * This is true if and only if the character is a letter, a digit, or one of '-', '.' or ':'
     * @param  c  the character to test.
     * @return  <code>true</code> if the specified character can be part of a StartTag name, otherwise <code>false</code>.
     */
    static boolean isIdentifierPart(char c) {
        return Character.isLetterOrDigit(c) || (c == '-') || (c == '.') ||
        (c == ':');
    }

    /**
     * Returns a string representation of this object useful for debugging purposes.
     * @return  a string representation of this object useful for debugging purposes.
     */
    public String toString() {
        return "(" + begin + ',' + end + ')';
    }

    
    private StartTag findNextStartTag(int pos, String name) {
        StartTag startTag = source.findNextStartTag(pos, name);

        if ((startTag == null) || (startTag.end > end)) {
            return null;
        }

        return startTag;
    }
}
/*
 * $Log: Segment.java,v $
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

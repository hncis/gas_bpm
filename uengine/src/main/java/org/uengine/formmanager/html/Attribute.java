package org.uengine.formmanager.html;


/**
 * Represents a single <a href="http://www.w3.org/TR/html401/intro/sgmltut.html#h-3.2.2">attribute</a>
 * name/value segment within a {@link StartTag}.
 * <p>
 * Created using the {@link Attributes#get(String key)} method.
 * <p>
 * See also the XML 1.0 specification for <a href="http://www.w3.org/TR/REC-xml#dt-attr">attributes</a>.
 *
 * @see Attributes
 */
public final class Attribute extends Segment {
    /**
     * Name of the attribute in lower case.
     * <p>
     * Note that this package treats all attribute names as case in-sensitive, contrary to the XHTML specification.
     */
    public String key;

    /** Segment spanning the Name of the attribute. */
    public Segment nameSegment;

    /** Segment spanning the Value of the attribute. */
    public Segment valueSegment;

    /**
     * Segment spanning the value of the attribute, including quotation marks if any.
     * <p>
     * If the value is not enclosed by quotation marks, this is the same as the {@link #valueSegment}
     */
    public Segment valueSegmentIncludingQuotes;

    /**
     * Constructs an Attribute with no value part, called from Attributes class.
     * <p>
     * Note that the resulting Attribute segment will have the same span as the supplied nameSegment.
     *
     * @param  source  the source document.
     * @param  key  the name of the attribute in lower case.
     * @param  nameSegment  the segment representing the name.
     */
    Attribute(Source source, String key, Segment nameSegment) {
        this(source, key, nameSegment, null, null);
    }

    /**
     * Constructs an Attribute, called from Attributes class.
     * <p>
     * The resulting Attribute segment will begin at the start of the nameSegment
     * and finish at the end of the valueSegmentIncludingQuotes.  If the attribute
     * has no value, it will finish at the end of the nameSegment.
     * <p>
     * If the attribute has no value, the <code>valueSegment</code> and <code>valueSegmentIncludingQuotes</code> must be null.
     * The <valueSegmentIncludingQuotes</code> parameter must not be null if the <code>valueSegment</code> is not null, and vice versa
     *
     * @param  source  the source document.
     * @param  key  the name of the attribute in lower case.
     * @param  nameSegment  the segment spanning the name.
     * @param  valueSegment  the segment spanning the value.
     * @param  valueSegmentIncludingQuotes  the segment spanning the value, including quotation marks if any.
     */
    Attribute(Source source, String key, Segment nameSegment,
        Segment valueSegment, Segment valueSegmentIncludingQuotes) {
        super(source, nameSegment.getBegin(),
            ((valueSegmentIncludingQuotes == null) ? nameSegment.getEnd()
                                                   : valueSegmentIncludingQuotes.getEnd()));
        this.key = key;
        this.nameSegment = nameSegment;
        this.valueSegment = valueSegment;
        this.valueSegmentIncludingQuotes = valueSegmentIncludingQuotes;
    }

    /**
     * Returns the character used to quote the value.
     * <p>
     * This will be either a double-quote (") or a single-quote (').
     *
     * @return  the character used to quote the value, or a space if the value is not quoted or the attribute has no value.
     */
    public char getQuoteChar() {
        if (valueSegment == valueSegmentIncludingQuotes) {
            return ' '; // no quotes
        }

        return source.text.charAt(valueSegmentIncludingQuotes.getBegin());
    }

    /**
     * Returns the name of the attribute in original case.
     * @return  the name of the attribute in original case.
     */
    public String getName() {
        return nameSegment.getSourceText();
    }

    /**
     * Returns the value of the attribute, or null if the attribute has no value.
     * @return  the value of the attribute, or null if the attribute has no value.
     */
    public String getValue() {
        if (!hasValue()) {
            return null;
        }

        return valueSegment.getSourceText();
    }

    /**
     * Indicates whether the attribute has a value.
     * @return  <code>true</code> if the attribute has a value, otherwise <code>false</code>.
     */
    public boolean hasValue() {
        return valueSegment != null;
    }

    public String toString() {
        String s = key + super.toString() + ",name=" + nameSegment.toString();

        if (hasValue()) {
            s = s + ",value=" + valueSegment.toString() + '"' +
                valueSegment.getSourceText() + '"' + ",valueIncludingQuotes=" +
                valueSegmentIncludingQuotes.toString() + '"' +
                valueSegmentIncludingQuotes.getSourceText() + "\"\n";
        } else {
            s = s + ",NO VALUE\n";
        }

        return s;
    }
}
/*
 * $Log: Attribute.java,v $
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

package org.uengine.formmanager.html;

import java.io.*;

import java.util.*;


/**
 * 원본 HTML 문서의 복사본으로 HTML문서를 수정하려 할 경우 이 복사본을 사용하여 문서를 수정
 * <p>
 * An OutputDocument represents an original source text that
 * has been modified by substituting segments of it with other text.
 * Each of these substitutions is registered by adding an {@link IOutputSegment} to the OutputDocument.
 * After all of the substitutions have been added, the modified text can be retrieved using the
 * {@link #output(Writer)} or {@link #toString()} methods.
 * <p>
 * The registered OutputSegments must not overlap each other, but may be adjacent.
 * <p>
 * The following example converts all externally referenced style sheets to internal style sheets:
 * <pre>
 *  OutputDocument outputDocument=new OutputDocument(htmlText);
 *  Source source=new Source(htmlText);
 *  List linkStartTags=source.findAllStartTags("link");
 *  if (linkStartTags!=null) {
 *    StringBuffer sb=new StringBuffer();
 *    for (Iterator i=linkStartTags.iterator(); i.hasNext();) {
 *      StartTag startTag=(StartTag)i.next();
 *      Attributes attributes=startTag.getAttributes();
 *      Attribute relAttribute=attributes.get("rel");
 *      if (relAttribute==null || !"stylesheet".equalsIgnoreCase(relAttribute.getValue())) continue;
 *      Attribute hrefAttribute=attributes.get("href");
 *      if (hrefAttribute==null) continue;
 *      String href=hrefAttribute.getValue();
 *      if (href==null) continue;
 *      String styleSheetContent;
 *      try {
 *        styleSheetContent=getString(new URL(href).openStream()); // note getString method is not defined here
 *      } catch (Exception ex) {
 *        continue; // don't convert if URL is invalid
 *      }
 *      sb.setLength(0);
 *      sb.append("&lt;style");
 *      Attribute typeAttribute=attributes.get("type");
 *      if (typeAttribute!=null) sb.append(' ').append(typeAttribute.getSourceText());
 *      sb.append(">\n").append(styleSheetContent).append("\n&lt;/style>");
 *      outputDocument.add(new StringOutputSegment(startTag,sb.toString()));
 *    }
 *  }
 *  String convertedHtmlText=outputDocument.toString();
 * </pre>
 *
 * @see IOutputSegment
 * @see StringOutputSegment
 */
public final class OutputDocument {
    private String sourceText;
    private ArrayList outputSegments = new ArrayList();

    /**
     * Constructs a new OutputDocument based on the specified source text.
     * @param  sourceText  the source text.
     */
    public OutputDocument(String sourceText) {
        if (sourceText == null) {
            throw new IllegalArgumentException();
        }

        this.sourceText = sourceText;
    }

    /**
     * Constructs a new OutputDocument based on the specified {@link Source} object.
     * <p>
     * This is equivalent to calling <code>new OutputDocument(source.text)</code>.
     *
     * @param  source  the Source document.
     */
    public OutputDocument(Source source) {
        this(source.text);
    }

    /**
     * Returns the original source text on which this OutputDocument is based.
     * @return  the original source text on which this OutputDocument is based.
     */
    public String getSourceText() {
        return sourceText;
    }

    /**
     * Adds the specified IOutputSegment to this OutputDocument.
     * <p>
     * Note that for efficiency reasons no exception is thrown if the added OutputSegment overlaps another.
     * The resulting output in this case is undefined.
     */
    public void add(IOutputSegment outputSegment) {
        outputSegments.add(outputSegment);
    }

    /**
     * Outputs the final content of this OutputDocument to the specified Writer.
     * @throws  IOException  if an I/O exception occurs.
     */
    public void output(Writer out) throws IOException {
        if (outputSegments.isEmpty()) {
            out.write(sourceText);

            return;
        }

        int pos = 0;
        Collections.sort(outputSegments, OutputSegmentComparator.INSTANCE);

        for (Iterator i = outputSegments.iterator(); i.hasNext();) {
            IOutputSegment outputSegment = (IOutputSegment) i.next();

            if (outputSegment.getBegin() > pos) {
                out.write(sourceText.substring(pos, outputSegment.getBegin()));
            }

            outputSegment.output(out);
            pos = outputSegment.getEnd();
        }

        if (pos < sourceText.length()) {
            out.write(sourceText.substring(pos));
        }

        out.close();
    }

    /**
     * Returns the final content of this OutputDocument as a String.
     * @return  the final content of this OutputDocument as a String.
     */
    public String toString() {
        StringWriter out = new StringWriter((int) (sourceText.length() * 1.5));

        try {
            output(out);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
         // should never happen with StringWriter

        return out.toString();
    }

    private static class OutputSegmentComparator implements Comparator {
        public static OutputSegmentComparator INSTANCE = new OutputSegmentComparator();

        public int compare(Object o1, Object o2) {
            if (!(o1 instanceof IOutputSegment && o2 instanceof IOutputSegment)) {
                throw new ClassCastException();
            }

            IOutputSegment outputSegment1 = (IOutputSegment) o1;
            IOutputSegment outputSegment2 = (IOutputSegment) o2;

            if (outputSegment1.getBegin() < outputSegment2.getBegin()) {
                return -1;
            }

            if (outputSegment1.getBegin() > outputSegment2.getBegin()) {
                return 1;
            }

            if (outputSegment1.getEnd() < outputSegment2.getEnd()) {
                return -1;
            }

            if (outputSegment1.getEnd() > outputSegment2.getEnd()) {
                return 1;
            }

            return 0;
        }
    }
}
/*
 * $Log: OutputDocument.java,v $
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
 * Revision 1.1  2007/01/26 10:41:16  mahler
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
 
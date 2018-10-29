package org.uengine.formmanager.html;

import java.io.*;


/**
 * Defines the interface for an OutputSegment, which is used in an {@link OutputDocument} to
 * replace segments of the source document with other text.
 * <p>
 * All text in the OutputDocument between the character positions defined by {@link #getBegin()} and {@link #getEnd()}
 * is replaced by the content of this OutputSegment.
 * If {@link #getBegin()} and {@link #getEnd()} are the same, the content is inserted at this position.
 *
 * @see OutputDocument
 */
public interface IOutputSegment {
    /**
     * Returns the character position in the OutputDocument where this segment begins.
     * @return  the character position in the OutputDocument where this segment begins.
     */
    public int getBegin();

    /**
     * Returns the character position in the OutputDocument where this segment ends.
     * @return  the character position in the OutputDocument where this segment ends.
     */
    public int getEnd();

    /**
     * Outputs the content of this OutputSegment to the specified Writer.
     * @param  out  the Writer to which the output is to be sent.
     * @throws  IOException  if an I/O exception occurs.
     */
    public void output(Writer out) throws IOException;
}
/*
 * $Log: IOutputSegment.java,v $
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
 * Revision 1.1  2005/04/03 01:36:51  ghbpark
 * *** empty log message ***
 *
 * Revision 1.2  2004/05/11 03:48:47  ghbpark
 * html 파서 추가
 *
 */

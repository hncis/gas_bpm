package org.uengine.formmanager.html;

import java.io.*;


/**
 * Implements an {@link IOutputSegment} whose content is a String constant. 가나다
 */
public final class StringOutputSegment implements IOutputSegment {
	private int begin;
	private int end;
	private String text;

	/**
	 * Constructs a new StringOutputSegment with the specified begin and end positions and the specified content.
	 * @param  begin  the position in the OutputDocument where this OutputSegment begins.
	 * @param  end  the position in the OutputDocument where this OutputSegment ends.
	 * @param  text  the textual content of the new OutputSegment.
	 */
	public StringOutputSegment(int begin, int end, String text) {
		this.begin = begin;
		this.end = end;
		this.text = text;
	}

	/**
	 * Constructs a new StringOutputSegment with the same span as the specified {@link Segment}.
	 * @param  segment  a Segment defining the beginning and ending positions of the new OutputSegment.
	 * @param  text  the textual content of the new OutputSegment.
	 */
	public StringOutputSegment(Segment segment, String text) {
		begin = segment.begin;
		end = segment.end;
		this.text = text;
	}

	public int getBegin() {
		return begin;
	}

	public int getEnd() {
		return end;
	}

	public void output(Writer out) throws IOException {
		if (text != null) {
			out.write(text);
		}
	}

	/**
	 * Returns a string representation of this object useful for debugging purposes.
	 * Note that it does NOT return the textual content of the segment.
	 * @return  a string representation of this object useful for debugging purposes.
	 */
	public String toString() {
		return "(" + begin + ',' + end + "):" +
		((text == null) ? "null" : ("\"" + text + '"'));
	}
}
/*
 * $Log: StringOutputSegment.java,v $
 * Revision 1.2  2007/12/05 02:31:24  curonide
 * *** empty log message ***
 *
 * Revision 1.5  2007/12/04 07:34:38  bpm
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
 * html �ļ� �߰�
 *
 */

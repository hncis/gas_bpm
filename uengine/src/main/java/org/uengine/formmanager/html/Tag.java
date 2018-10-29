package org.uengine.formmanager.html;

import java.util.*;

/**
 * {@link StartTag} or {@link EndTag} 를 대표하기 위한 상위 클래스
 *
 * @see Source#getNextTagIterator(int pos)
 *
 * @author <a href="mailto:ghbpark@hanwha.co.kr">Sungsoo Park</a>
 * @version $Id: Tag.java,v 1.2 2007/12/05 02:31:24 curonide Exp $
 */
public class Tag extends Segment {
    /** <a href="http://www.w3.org/TR/html401/struct/links.html#edef-A">HTML 4.01 definition</a> */
    public static final String A = "a";

    /** <a href="http://www.w3.org/TR/html401/struct/text.html#edef-ABBR">HTML 4.01 definition</a> */
    public static final String ABBR = "abbr";

    /** <a href="http://www.w3.org/TR/html401/struct/text.html#edef-ACRONYM">HTML 4.01 definition</a> */
    public static final String ACRONYM = "acronym";

    /** <a href="http://www.w3.org/TR/html401/struct/global.html#edef-ADDRESS">HTML 4.01 definition</a> */
    public static final String ADDRESS = "address";

    /** <a href="http://www.w3.org/TR/html401/struct/objects.html#edef-APPLET">HTML 4.01 definition</a> */
    public static final String APPLET = "applet";

    /** <a href="http://www.w3.org/TR/html401/struct/objects.html#edef-AREA">HTML 4.01 definition</a> */
    public static final String AREA = "area";

    /** <a href="http://www.w3.org/TR/html401/present/graphics.html#edef-B">HTML 4.01 definition</a> */
    public static final String B = "b";

    /** <a href="http://www.w3.org/TR/html401/struct/links.html#edef-BASE">HTML 4.01 definition</a> */
    public static final String BASE = "base";

    /** <a href="http://www.w3.org/TR/html401/present/graphics.html#edef-BASEFONT">HTML 4.01 definition</a> */
    public static final String BASEFONT = "basefont";

    /** <a href="http://www.w3.org/TR/html401/struct/dirlang.html#edef-BDO">HTML 4.01 definition</a> */
    public static final String BDO = "bdo";

    /** <a href="http://www.w3.org/TR/html401/present/graphics.html#edef-BIG">HTML 4.01 definition</a> */
    public static final String BIG = "big";

    /** <a href="http://www.w3.org/TR/html401/struct/text.html#edef-BLOCKQUOTE">HTML 4.01 definition</a> */
    public static final String BLOCKQUOTE = "blockquote";

    /** <a href="http://www.w3.org/TR/html401/struct/global.html#edef-BODY">HTML 4.01 definition</a> */
    public static final String BODY = "body";

    /** <a href="http://www.w3.org/TR/html401/struct/text.html#edef-BR">HTML 4.01 definition</a> */
    public static final String BR = "br";

    /** <a href="http://www.w3.org/TR/html401/interact/forms.html#edef-BUTTON">HTML 4.01 definition</a> */
    public static final String BUTTON = "button";

    /** <a href="http://www.w3.org/TR/html401/struct/tables.html#edef-CAPTION">HTML 4.01 definition</a> */
    public static final String CAPTION = "caption";

    /** <a href="http://www.w3.org/TR/html401/present/graphics.html#edef-CENTER">HTML 4.01 definition</a> */
    public static final String CENTER = "center";

    /** <a href="http://www.w3.org/TR/html401/struct/text.html#edef-CITE">HTML 4.01 definition</a> */
    public static final String CITE = "cite";

    /** <a href="http://www.w3.org/TR/html401/struct/text.html#edef-CODE">HTML 4.01 definition</a> */
    public static final String CODE = "code";

    /** <a href="http://www.w3.org/TR/html401/struct/tables.html#edef-COL">HTML 4.01 definition</a> */
    public static final String COL = "col";

    /** <a href="http://www.w3.org/TR/html401/struct/tables.html#edef-COLGROUP">HTML 4.01 definition</a> */
    public static final String COLGROUP = "colgroup";

    /** <a href="http://www.w3.org/TR/html401/struct/lists.html#edef-DD">HTML 4.01 definition</a> */
    public static final String DD = "dd";

    /** <a href="http://www.w3.org/TR/html401/struct/text.html#edef-DEL">HTML 4.01 definition</a> */
    public static final String DEL = "del";

    /** <a href="http://www.w3.org/TR/html401/struct/text.html#edef-DFN">HTML 4.01 definition</a> */
    public static final String DFN = "dfn";

    /** <a href="http://www.w3.org/TR/html401/struct/lists.html#edef-DIR">HTML 4.01 definition</a> */
    public static final String DIR = "dir";

    /** <a href="http://www.w3.org/TR/html401/struct/global.html#edef-DIV">HTML 4.01 definition</a> */
    public static final String DIV = "div";

    /** <a href="http://www.w3.org/TR/html401/struct/lists.html#edef-DL">HTML 4.01 definition</a> */
    public static final String DL = "dl";

    /** <a href="http://www.w3.org/TR/html401/struct/lists.html#edef-DT">HTML 4.01 definition</a> */
    public static final String DT = "dt";

    /** <a href="http://www.w3.org/TR/html401/struct/text.html#edef-EM">HTML 4.01 definition</a> */
    public static final String EM = "em";

    /** <a href="http://www.w3.org/TR/html401/interact/forms.html#edef-FIELDSET">HTML 4.01 definition</a> */
    public static final String FIELDSET = "fieldset";

    /** <a href="http://www.w3.org/TR/html401/present/graphics.html#edef-FONT">HTML 4.01 definition</a> */
    public static final String FONT = "font";

    /** <a href="http://www.w3.org/TR/html401/interact/forms.html#edef-FORM">HTML 4.01 definition</a> */
    public static final String FORM = "form";

    /** <a href="http://www.w3.org/TR/html401/present/frames.html#edef-FRAME">HTML 4.01 definition</a> */
    public static final String FRAME = "frame";

    /** <a href="http://www.w3.org/TR/html401/present/frames.html#edef-FRAMESET">HTML 4.01 definition</a> */
    public static final String FRAMESET = "frameset";

    /** <a href="http://www.w3.org/TR/html401/struct/global.html#edef-H1">HTML 4.01 definition</a> */
    public static final String H1 = "h1";

    /** <a href="http://www.w3.org/TR/html401/struct/global.html#edef-H2">HTML 4.01 definition</a> */
    public static final String H2 = "h2";

    /** <a href="http://www.w3.org/TR/html401/struct/global.html#edef-H3">HTML 4.01 definition</a> */
    public static final String H3 = "h3";

    /** <a href="http://www.w3.org/TR/html401/struct/global.html#edef-H4">HTML 4.01 definition</a> */
    public static final String H4 = "h4";

    /** <a href="http://www.w3.org/TR/html401/struct/global.html#edef-H5">HTML 4.01 definition</a> */
    public static final String H5 = "h5";

    /** <a href="http://www.w3.org/TR/html401/struct/global.html#edef-H6">HTML 4.01 definition</a> */
    public static final String H6 = "h6";

    /** <a href="http://www.w3.org/TR/html401/struct/global.html#edef-HEAD">HTML 4.01 definition</a> */
    public static final String HEAD = "head";

    /** <a href="http://www.w3.org/TR/html401/present/graphics.html#edef-HR">HTML 4.01 definition</a> */
    public static final String HR = "hr";

    /** <a href="http://www.w3.org/TR/html401/struct/global.html#edef-HTML">HTML 4.01 definition</a> */
    public static final String HTML = "html";

    /** <a href="http://www.w3.org/TR/html401/present/graphics.html#edef-I">HTML 4.01 definition</a> */
    public static final String I = "i";

    /** <a href="http://www.w3.org/TR/html401/present/frames.html#edef-IFRAME">HTML 4.01 definition</a> */
    public static final String IFRAME = "iframe";

    /** <a href="http://www.w3.org/TR/html401/struct/objects.html#edef-IMG">HTML 4.01 definition</a> */
    public static final String IMG = "img";

    /** <a href="http://www.w3.org/TR/html401/interact/forms.html#edef-INPUT">HTML 4.01 definition</a> */
    public static final String INPUT = "input";

    /** <a href="http://www.w3.org/TR/html401/struct/text.html#edef-INS">HTML 4.01 definition</a> */
    public static final String INS = "ins";

    /** <a href="http://www.w3.org/TR/html401/interact/forms.html#edef-ISINDEX">HTML 4.01 definition</a> */
    public static final String ISINDEX = "isindex";

    /** <a href="http://www.w3.org/TR/html401/struct/text.html#edef-KBD">HTML 4.01 definition</a> */
    public static final String KBD = "kbd";

    /** <a href="http://www.w3.org/TR/html401/interact/forms.html#edef-LABEL">HTML 4.01 definition</a> */
    public static final String LABEL = "label";

    /** <a href="http://www.w3.org/TR/html401/interact/forms.html#edef-LEGEND">HTML 4.01 definition</a> */
    public static final String LEGEND = "legend";

    /** <a href="http://www.w3.org/TR/html401/struct/lists.html#edef-LI">HTML 4.01 definition</a> */
    public static final String LI = "li";

    /** <a href="http://www.w3.org/TR/html401/struct/links.html#edef-LINK">HTML 4.01 definition</a> */
    public static final String LINK = "link";

    /** <a href="http://www.w3.org/TR/html401/struct/objects.html#edef-MAP">HTML 4.01 definition</a> */
    public static final String MAP = "map";

    /** <a href="http://www.w3.org/TR/html401/struct/lists.html#edef-MENU">HTML 4.01 definition</a> */
    public static final String MENU = "menu";

    /** <a href="http://www.w3.org/TR/html401/struct/global.html#edef-META">HTML 4.01 definition</a> */
    public static final String META = "meta";

    /** <a href="http://www.w3.org/TR/html401/present/frames.html#edef-NOFRAMES">HTML 4.01 definition</a> */
    public static final String NOFRAMES = "noframes";

    /** <a href="http://www.w3.org/TR/html401/interact/scripts.html#edef-NOSCRIPT">HTML 4.01 definition</a> */
    public static final String NOSCRIPT = "noscript";

    /** <a href="http://www.w3.org/TR/html401/struct/objects.html#edef-OBJECT">HTML 4.01 definition</a> */
    public static final String OBJECT = "object";

    /** <a href="http://www.w3.org/TR/html401/struct/lists.html#edef-OL">HTML 4.01 definition</a> */
    public static final String OL = "ol";

    /** <a href="http://www.w3.org/TR/html401/interact/forms.html#edef-OPTGROUP">HTML 4.01 definition</a> */
    public static final String OPTGROUP = "optgroup";

    /** <a href="http://www.w3.org/TR/html401/interact/forms.html#edef-OPTION">HTML 4.01 definition</a> */
    public static final String OPTION = "option";

    /** <a href="http://www.w3.org/TR/html401/struct/text.html#edef-P">HTML 4.01 definition</a> */
    public static final String P = "p";

    /** <a href="http://www.w3.org/TR/html401/struct/objects.html#edef-PARAM">HTML 4.01 definition</a> */
    public static final String PARAM = "param";

    /** <a href="http://www.w3.org/TR/html401/struct/text.html#edef-PRE">HTML 4.01 definition</a> */
    public static final String PRE = "pre";

    /** <a href="http://www.w3.org/TR/html401/struct/text.html#edef-Q">HTML 4.01 definition</a> */
    public static final String Q = "q";

    /** <a href="http://www.w3.org/TR/html401/present/graphics.html#edef-S">HTML 4.01 definition</a> */
    public static final String S = "s";

    /** <a href="http://www.w3.org/TR/html401/struct/text.html#edef-SAMP">HTML 4.01 definition</a> */
    public static final String SAMP = "samp";

    /** <a href="http://www.w3.org/TR/html401/interact/scripts.html#edef-SCRIPT">HTML 4.01 definition</a> */
    public static final String SCRIPT = "script";

    /** <a href="http://www.w3.org/TR/html401/interact/forms.html#edef-SELECT">HTML 4.01 definition</a> */
    public static final String SELECT = "select";

    /** <a href="http://www.w3.org/TR/html401/present/graphics.html#edef-SMALL">HTML 4.01 definition</a> */
    public static final String SMALL = "small";

    /** <a href="http://www.w3.org/TR/html401/struct/global.html#edef-SPAN">HTML 4.01 definition</a> */
    public static final String SPAN = "span";

    /** <a href="http://www.w3.org/TR/html401/present/graphics.html#edef-STRIKE">HTML 4.01 definition</a> */
    public static final String STRIKE = "strike";

    /** <a href="http://www.w3.org/TR/html401/struct/text.html#edef-STRONG">HTML 4.01 definition</a> */
    public static final String STRONG = "strong";

    /** <a href="http://www.w3.org/TR/html401/present/styles.html#edef-STYLE">HTML 4.01 definition</a> */
    public static final String STYLE = "style";

    /** <a href="http://www.w3.org/TR/html401/struct/text.html#edef-SUB">HTML 4.01 definition</a> */
    public static final String SUB = "sub";

    /** <a href="http://www.w3.org/TR/html401/struct/text.html#edef-SUP">HTML 4.01 definition</a> */
    public static final String SUP = "sup";

    /** <a href="http://www.w3.org/TR/html401/struct/tables.html#edef-TABLE">HTML 4.01 definition</a> */
    public static final String TABLE = "table";

    /** <a href="http://www.w3.org/TR/html401/struct/tables.html#edef-TBODY">HTML 4.01 definition</a> */
    public static final String TBODY = "tbody";

    /** <a href="http://www.w3.org/TR/html401/struct/tables.html#edef-TD">HTML 4.01 definition</a> */
    public static final String TD = "td";

    /** <a href="http://www.w3.org/TR/html401/interact/forms.html#edef-TEXTAREA">HTML 4.01 definition</a> */
    public static final String TEXTAREA = "textarea";

    /** <a href="http://www.w3.org/TR/html401/struct/tables.html#edef-TFOOT">HTML 4.01 definition</a> */
    public static final String TFOOT = "tfoot";

    /** <a href="http://www.w3.org/TR/html401/struct/tables.html#edef-TH">HTML 4.01 definition</a> */
    public static final String TH = "th";

    /** <a href="http://www.w3.org/TR/html401/struct/tables.html#edef-THEAD">HTML 4.01 definition</a> */
    public static final String THEAD = "thead";

    /** <a href="http://www.w3.org/TR/html401/struct/global.html#edef-TITLE">HTML 4.01 definition</a> */
    public static final String TITLE = "title";

    /** <a href="http://www.w3.org/TR/html401/struct/tables.html#edef-TR">HTML 4.01 definition</a> */
    public static final String TR = "tr";

    /** <a href="http://www.w3.org/TR/html401/present/graphics.html#edef-TT">HTML 4.01 definition</a> */
    public static final String TT = "tt";

    /** <a href="http://www.w3.org/TR/html401/present/graphics.html#edef-U">HTML 4.01 definition</a> */
    public static final String U = "u";

    /** <a href="http://www.w3.org/TR/html401/struct/lists.html#edef-UL">HTML 4.01 definition</a> */
    public static final String UL = "ul";

    /** <a href="http://www.w3.org/TR/html401/struct/text.html#edef-VAR">HTML 4.01 definition</a> */
    public static final String VAR = "var";
    private static final TagSet END_TAG_FORBIDDEN_SET = new TagSet(new String[] {
                AREA, BASE, BASEFONT, BR, COL, FRAME, HR, IMG, INPUT, ISINDEX,
                LINK, META, PARAM
            });
    private static final TagSet BLOCK_SET = new TagSet(new String[] {
                P, H1, H2, H3, H4, H5, H6, UL, OL, DIR, MENU, PRE, DL, DIV,
                CENTER, NOSCRIPT, NOFRAMES, BLOCKQUOTE, FORM, ISINDEX, HR, TABLE,
                FIELDSET, ADDRESS
            });
    private static final TagSet INLINE_SET = new TagSet(new String[] {
                TT, I, B, U, S, STRIKE, BIG, SMALL, EM, STRONG, DFN, CODE, SAMP,
                KBD, VAR, CITE, ABBR, ACRONYM, A, IMG, APPLET, OBJECT, FONT,
                BASEFONT, BR, SCRIPT, MAP, Q, SUB, SUP, SPAN, BDO, IFRAME, INPUT,
                SELECT, TEXTAREA, LABEL, BUTTON, INS, DEL
            });
    private static final TagSet _HTML = new TagSet().union(HTML);
    private static final TagSet _TABLE = new TagSet().union(TABLE);
    private static final TagSet _DL = new TagSet().union(DL);
    private static final TagSet _LI = new TagSet().union(LI);
    private static final TagSet _UL_OL = new TagSet().union(UL).union(OL);
    private static final TagSet _DD_DT = new TagSet().union(DD).union(DT);
    private static final TagSet _BODY_FRAMESET = new TagSet().union(BODY).union(FRAMESET);
    private static final TagSet _THEAD_TBODY_TFOOT_TR = new TagSet().union(THEAD)
                                                                    .union(TBODY)
                                                                    .union(TFOOT)
                                                                    .union(TR);
    private static final TagSet _THEAD_TBODY_TFOOT_TR_COLGROUP = new TagSet().union(_THEAD_TBODY_TFOOT_TR)
                                                                             .union(COLGROUP);
    private static final TagSet _THEAD_TBODY_TFOOT_TR_TD_TH = new TagSet().union(_THEAD_TBODY_TFOOT_TR)
                                                                          .union(TD)
                                                                          .union(TH);
    private static final TagSet _THEAD_TBODY_TFOOT_TR_TABLE = new TagSet().union(_THEAD_TBODY_TFOOT_TR)
                                                                          .union(TABLE);
    private static final TagSet _THEAD_TBODY_TFOOT_TABLE = new TagSet().union(_THEAD_TBODY_TFOOT_TR_TABLE)
                                                                       .minus(TR);
    private static final TagSet _OPTION_OPTGROUP = new TagSet().union(OPTION)
                                                               .union(OPTGROUP);
    private static final TagSet _SELECT = new TagSet().union(SELECT);
    private static final TagSet _TBODY_TFOOT = new TagSet().union(TBODY).union(TFOOT);
    private static final TagSet _TBODY_THEAD = new TagSet().union(TBODY).union(THEAD);
    private static final TagSet _BLOCKSET_DL_DD_DT_TABLE_TH_TD_LI = new TagSet().union(BLOCK_SET)
                                                                                .union(DL)
                                                                                .union(_DD_DT)
                                                                                .union(TABLE)
                                                                                .union(TH)
                                                                                .union(TD)
                                                                                .union(LI);
    private static final TagSet _BLOCKSET_DL_DD_DT_TABLE_BODY_HTML_THEAD_TBODY_TFOOT_TR_TD_TH_CAPTION_LEGEND =
        new TagSet().union(BLOCK_SET).union(DL).union(_DD_DT).union(TABLE)
                    .union(BODY).union(HTML).union(_THEAD_TBODY_TFOOT_TR_TD_TH)
                    .union(CAPTION).union(LEGEND);
    private static final TerminatorSets DEFINITION_TERMINATOR_SETS = new TerminatorSets(_DD_DT,
            _DL, _DL);
    private static final TerminatorSets TABLE_CELL_TERMINATOR_SETS = new TerminatorSets(_THEAD_TBODY_TFOOT_TR_TD_TH,
            _THEAD_TBODY_TFOOT_TR_TABLE, _TABLE);
    private static final TerminatorSets TABLE_SECTION_TERMINATOR_SETS = new TerminatorSets(_TBODY_THEAD,
            _TABLE, _TABLE);
    private static final HashMap OPTIONAL_END_TAG_TERMINATOR_SETS_MAP = buildOptionalEndTagTerminatorSetsMap(); // contains a map of tags having optional end tags to the TerminatorSets that can terminate the element if the end tag is not present
    String name = null;

    Tag(Source source, int begin, int end, String name) {
        super(source, begin, end);
        this.name = name.toLowerCase();
    }

    Tag() {
    }
     // used when creating CACHED_NULL objects

    /**
     * 태그의 이름을 리턴 (always in lower case).
     * @return  태그 이름
     */
    public String getName() {
        return name;
    }

    TerminatorSets getOptionalEndTagTerminatorSets() {
        return (TerminatorSets) OPTIONAL_END_TAG_TERMINATOR_SETS_MAP.get(name);
    }

    static boolean isEndTagForbidden(String name) {
        return END_TAG_FORBIDDEN_SET.contains(name.toLowerCase());
    }

    static boolean isEndTagOptional(String name) {
        return OPTIONAL_END_TAG_TERMINATOR_SETS_MAP.containsKey(name.toLowerCase());
    }

    static boolean isEndTagRequired(String name) {
        return !isEndTagForbidden(name) && !isEndTagOptional(name);
    }

    static boolean isBlock(String name) {
        return BLOCK_SET.contains(name.toLowerCase());
    }

    static boolean isInline(String name) {
        return INLINE_SET.contains(name.toLowerCase());
    }

    static Iterator getNextTagIterator(Source source, int pos) {
        return new NextTagIterator(source, pos);
    }

    private static HashMap buildOptionalEndTagTerminatorSetsMap() {
        HashMap map = new HashMap();
        map.put(BODY, new TerminatorSets(null, _HTML, null));
        map.put(COLGROUP,
            new TerminatorSets(_THEAD_TBODY_TFOOT_TR_COLGROUP, _TABLE, _TABLE));
        map.put(DD, DEFINITION_TERMINATOR_SETS);
        map.put(DT, DEFINITION_TERMINATOR_SETS);
        map.put(HEAD, new TerminatorSets(_BODY_FRAMESET, _HTML, null));
        map.put(HTML, new TerminatorSets(_HTML, null, null)); // HTML is included in StartTagTerminatorSet in case the source contains multiple HTML documents
        map.put(LI, new TerminatorSets(_LI, _UL_OL, _UL_OL));
        map.put(OPTION, new TerminatorSets(_OPTION_OPTGROUP, _SELECT, null));
        map.put(P,
            new TerminatorSets(_BLOCKSET_DL_DD_DT_TABLE_TH_TD_LI,
                _BLOCKSET_DL_DD_DT_TABLE_BODY_HTML_THEAD_TBODY_TFOOT_TR_TD_TH_CAPTION_LEGEND,
                null));
        map.put(TBODY, new TerminatorSets(_TBODY_TFOOT, _TABLE, _TABLE)); // TFOOT is included in StartTagTerminatorSet although correct HTML requires TFOOT to precede TBODY
        map.put(TD, TABLE_CELL_TERMINATOR_SETS);
        map.put(TFOOT, TABLE_SECTION_TERMINATOR_SETS);
        map.put(TH, TABLE_CELL_TERMINATOR_SETS);
        map.put(THEAD, TABLE_SECTION_TERMINATOR_SETS);
        map.put(TR,
            new TerminatorSets(_THEAD_TBODY_TFOOT_TR, _THEAD_TBODY_TFOOT_TABLE,
                _TABLE));

        return map;
    }

    private static class NextTagIterator implements Iterator {
        private Source source;
        private Tag nextTag = null;
        private Tag cachedTagAfterNext;
        private boolean cachedTagAfterNextIsStartTag = false;

        public NextTagIterator(Source source, int pos) {
            this.source = source;

            Segment enclosingComment = source.findEnclosingComment(pos);

            if (enclosingComment != null) {
                pos = enclosingComment.end;
            }

            cachedTagAfterNext = source.findNextEndTag(pos);
            loadNextTag(pos);
        }

        public boolean hasNext() {
            return nextTag != null;
        }

        public Object next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Tag result = nextTag;
            loadNextTag(result.end);

            return result;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        private void loadNextTag(int pos) {
            nextTag = cachedTagAfterNextIsStartTag
                ? (Tag) source.findNextEndTag(pos)
                : (Tag) source.findNextStartTag(pos);

            if (cachedTagAfterNext == null) {
                return;
            }

            if ((nextTag != null) &&
                    (nextTag.begin < cachedTagAfterNext.begin)) {
                if (!cachedTagAfterNextIsStartTag &&
                        ((StartTag) nextTag).isComment() &&
                        (cachedTagAfterNext.begin < nextTag.end)) { // if this start tag is a comment and the next end tag is inside it
                    cachedTagAfterNext = source.findNextEndTag(nextTag.end); // then find the next end tag outside of the comment.
                }

                return;
            }

            Tag newNextTag = cachedTagAfterNext;
            cachedTagAfterNext = nextTag;
            nextTag = newNextTag;
            cachedTagAfterNextIsStartTag = !cachedTagAfterNextIsStartTag;
        }
    }

    static final class TerminatorSets {
        private Set startTagTerminatorSet;
        private Set endTagTerminatorSet;
        private Set ignoredNestedElementSet;

        public TerminatorSets(TagSet startTagTerminatorSet,
            TagSet endTagTerminatorSet, TagSet ignoredNestedElementSet) {
            this.startTagTerminatorSet = startTagTerminatorSet;
            this.endTagTerminatorSet = endTagTerminatorSet;
            this.ignoredNestedElementSet = ignoredNestedElementSet;
        }

        public Set getStartTagTerminatorSet() {
            return startTagTerminatorSet;
        }

        public Set getEndTagTerminatorSet() {
            return endTagTerminatorSet;
        }

        public Set getIgnoredNestedElementSet() {
            return ignoredNestedElementSet;
        }
    }

    private static final class TagSet extends HashSet {
        public TagSet() {
        }

        public TagSet(String[] items) {
            for (int i = 0; i < items.length; i++) {
                add(items[i]);
            }
        }

        public TagSet union(String item) {
            add(item);

            return this;
        }

        public TagSet union(Collection collection) {
            for (Iterator i = collection.iterator(); i.hasNext();) {
                add(i.next());
            }

            return this;
        }

        public TagSet minus(String item) {
            remove(item);

            return this;
        }
    }
}
/*
 * $Log: Tag.java,v $
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
 * Revision 1.1  2005/04/03 01:36:51  ghbpark
 * *** empty log message ***
 *
 * Revision 1.2  2004/05/11 03:48:47  ghbpark
 * html 파서 추가
 *
 */

package com.hncis.common.util;

/**
 *
 * <pre>
 * Statements
 * </pre>
 *
 * @ClassName   : PageNavigation.java
 * @Description : 페이지 클래스
 * @since 2014. 11. 19.
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2014. 11. 19.     이동원                  최초 생성
 * </pre>
 */
public class PageNavigation {
    public static final int PAGE_SIZE  = 15; //한페이지 보여지는수
    public static final int BLOCK_SIZE = 10; //블록 사이즈

    //초기 생성자..
    public PageNavigation(){
        this( PAGE_SIZE , BLOCK_SIZE );
    }

    private int pageSize    = PAGE_SIZE;
    private int blockSize   = BLOCK_SIZE;

    public PageNavigation( int pageSize , int blockSize ){
        this.pageSize = pageSize;
        this.blockSize = blockSize;
    }

    public int getNumOfRec(){
        return numOfRec;
    }
    public int getNumOfPage(){
        return numOfPage;
    }
    public int getBlockStart(){
        return blockStart;
    }
    public int getBlockEnd(){
        return blockEnd;
    }

    private int numOfRec = 0;
    private int numOfPage = 0;
    private int blockStart = 0;
    private int blockEnd = 0;

    public boolean isPrePageExists(){
        return prePage;
    }

    public boolean isNextPageExists(){
        return nextPage;
    }

    private boolean prePage = false;
    private boolean nextPage = false;

    private int length = 0;
    private int offset = 0;

    public int getLength(){
        return length;
    }

    public int getOffset(){
        return offset;
    }

    private int page = 0;
    public void setCurPage( int p ){
        page = p;
    }

    public int getCurPage(){
        return page;
    }

    public void init( int numRec ){
        if( numRec > 0 ){
            numOfRec = numRec;//12
            numOfPage = ( ( ( numRec - 1 ) / pageSize ) + 1 );//2

            if( page > numOfPage ){
                throw new RuntimeException("WRONG PAGE NUMBER");
            }else{
                offset = ( page - 1 ) * pageSize;
                length = Math.min( pageSize , numOfRec - ( page - 1 ) * pageSize );

                blockStart  = ( ( page - 1 ) / blockSize ) * blockSize + 1;
                blockEnd = Math.min( blockStart + blockSize - 1 , numOfPage );

                prePage = ( blockStart != 1 );
                nextPage = ( blockEnd != numOfPage );

//                System.out.println("*****************************");
//                System.out.println("page:"+page);
//                System.out.println("numRec:"+numRec);
//                System.out.println("numOfPage:"+numOfPage);
//                System.out.println("offset:"+offset);
//                System.out.println("length:"+length);
//                System.out.println("blockStart:"+blockStart);
//                System.out.println("blockEnd:"+blockEnd);
//                System.out.println("prePage:"+prePage);
//                System.out.println("nextPage:"+nextPage);
//                System.out.println("*****************************");
            }
        }else{
            numOfRec    = 0;
            numOfPage   = 0;
            blockStart  = 1;
            blockEnd    = 1;
            prePage     = false;
            nextPage    = false;
        }
    }

    public int getPageSize() {
        return pageSize;
    }
}
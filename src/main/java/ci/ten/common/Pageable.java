package ci.ten.common;

import java.io.Serializable;

/**
 * Created by dongjigong on 2017/9/25.
 *
 * @Email osbornjonny@126.com
 * @Description
 */
public class Pageable implements Serializable {


    private static final long serialVersionUID = 4389008786822014078L;
    private int page = 0;

    private int limit = 15;

    private int size = 0 ;

    private int start =0;

    public int getStart() {
        if(start > 0){
            return start;
        }
        return page*limit;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        if(size > 0){
            return size;
        }
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}

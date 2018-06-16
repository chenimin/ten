package ci.ten.common;

import java.util.ArrayList;
import java.util.List;

public class Pagination<E> {

    private int page;
    private int pagesize;
    private int count;
    private List<E> pageItems = new ArrayList<E>();

    public Pagination(){
    }
    public Pagination(int page,int pagesize,int count,List<E> pageItems){
        this.page = page;
        this.pagesize = pagesize;
        this.count = count;
        this.pageItems = pageItems;
    }
    public void setPage(int page) {
        this.page = page;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

    public void setPageItems(List<E> pageItems) {
        this.pageItems = pageItems;
    }

    public int getPage() {
        return page;
    }

    public int getPagesize() {
        return pagesize;
    }

    public List<E> getPageItems() {
        return pageItems;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
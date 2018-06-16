package ci.ten.common;

import com.github.pagehelper.Page;

import java.util.Collections;
import java.util.List;


public class AjaxResult<T> {

    private Object data;

    private int page;
    private int limit;
    private int code;

    private String msg;
    private Long count;

    public AjaxResult() {
        this.code = 200;
        this.msg = "success";
        this.count = 0L;
        this.data = Collections.emptyList();
    }

    public AjaxResult(Object data) {
        this.code = 200;
        this.msg = "success";
        this.count = 0L;
        this.data = data;
    }
    public AjaxResult(Page page , Object data) {
        this.code = 200;
        this.msg = "success";
        this.count = 0L;
        this.data = data;
        this.page = page.getPageNum();
        this.limit = page.getPageSize();
        this.count = page.getTotal();
    }

    public AjaxResult(int code, String message) {
        this.code = code;
        this.msg = message;
    }

    public AjaxResult(Page page, int code, String message) {
        this.data = page.getResult();
        this.page = page.getPageNum();
        this.limit = page.getPageSize();
        this.code = code;
        this.msg = message;
        this.count = page.getTotal();
    }

    public AjaxResult(Page page) {
        this.data = page.getResult();
        this.page = page.getPageNum();
        this.limit = page.getPageSize();
        this.count = page.getTotal();
        this.code = 0;
        this.msg = "success";
    }

    public AjaxResult(Pagination page) {
        this.data = page.getPageItems();
        this.count = Long.valueOf(page.getCount());
        this.limit = page.getPagesize();
        this.page = page.getPage();
        this.code = 0;
        this.msg = "success";
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Object getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}

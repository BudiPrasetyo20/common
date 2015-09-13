package com.dubby.base.model;

import java.util.ArrayList;
import java.util.List;

public class PagedResultImpl implements PagedResult {

    private Integer actualRowCount = 0;
    private List result = new ArrayList();
    private Integer currentPageIndex;
    private Integer pageSize;

    @Override
    public Integer getActualRowCount() {
        return actualRowCount;
    }

    @Override
    public void setActualRowCount(Integer actualRowCount) {
        this.actualRowCount = actualRowCount;
    }

    @Override
    public List getResult() {
        return result;
    }

    @Override
    public void setResult(List result) {
        this.result = result;
    }

    @Override
    public Integer getCurrentPageIndex() {
        return currentPageIndex;
    }

    @Override
    public void setCurrentPageIndex(Integer pageIndex) {
        this.currentPageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}

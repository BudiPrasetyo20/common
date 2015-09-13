package com.dubby.base.model;

import java.util.List;

public interface PagedResult {
    public Integer getActualRowCount();
    public void setActualRowCount(Integer actualRowCount);
    public List getResult();
    public void setResult(List result);
    public Integer getCurrentPageIndex();
    public void setCurrentPageIndex(Integer pageIndex);
    public Integer getPageSize();
    public void setPageSize(Integer pageSize);
}

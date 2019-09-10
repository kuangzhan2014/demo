package com.maitianer.demo.model.common;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * 兼容LayUI数据表格的异步请求协议
 * 页码：page
 * 每页数据量：limit
 * UserDTO: Leo
 * Date: 2018/3/26 下午8:56
 */
public class DataTableRequest<T> extends Page<T> {

    enum OrderDirection {
        desc, asc
    }

    private OrderDirection orderDirection;
    private String searchProperty;
    private String searchValue;
    private Long totalPage;

    public Long getPage() {
        return getCurrent();
    }

    public DataTableRequest<T> setPage(Integer page) {
        setCurrent(page);
        return this;
    }

    /**
     * 兼容Layui DataTable的每页记录数参数
     * @param limit
     * @return
     */
    public Page<T> setLimit(long limit) {
        return setSize(limit);
    }

    public OrderDirection getOrderDirection() {
        return orderDirection;
    }

    public DataTableRequest<T> setOrderDirection(OrderDirection orderDirection) {
        this.orderDirection = orderDirection;
        if (orderDirection != OrderDirection.asc) {
            // TODO 动态表格排序还有问题
//            setAsc(false);
        }
        return this;
    }

    public String getSearchProperty() {
        return searchProperty;
    }

    public DataTableRequest<T> setSearchProperty(String searchProperty) {
        this.searchProperty = searchProperty;
        return this;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public DataTableRequest<T> setSearchValue(String searchValue) {
        this.searchValue = searchValue;
        return this;
    }

    public Long getTotalPage() {
        return getTotal()/getSize()+1;
    }

    public void setTotalPage(Long totalPage) {
        this.totalPage = totalPage;
    }
}

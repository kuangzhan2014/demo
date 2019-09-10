package com.maitianer.demo.model.res;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.util.Assert;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author Chen
 * @Date 2018/9/20 16:14
 */
public class PageRES<T> {
    private List<T> list;

    private Long total;

    private Long page;

    private Long limit;

    public PageRES(){
        this.list = null;
        this.total = 0L;
        this.page = 1L;
        this.limit = 20L;
    }

    public PageRES(List<T> list, Long total, Long page, Long limit) {
        this.list = list;
        this.total = total;
        this.page = page;
        this.limit = limit;
    }

    public PageRES(IPage<T> page){
        this.list = page.getRecords();
        this.total = page.getTotal();
        this.limit = page.getSize();
        this.page = page.getCurrent();
    }

    public PageRES(IPage<T> page, List<T> list) {
        this.list = list;
        this.total = page.getTotal();
        this.limit = page.getSize();
        this.page = page.getCurrent();
    }

    public static<T> PageRES<T> success(IPage<T> page) {
        return new PageRES<>(page);
    }

    public <U> PageRES<U> map(Function<? super T, ? extends U> converter) {
        return new PageRES<>(getConvertedContent(converter), total, page, limit);
    }

    private <U> List<U> getConvertedContent(Function<? super T, ? extends U> converter) {

        Assert.notNull(converter, "Function must not be null!");

        return list.stream().map(converter::apply).collect(Collectors.toList());
    }

    public List<T> getList() {
        return list;
    }

    public PageRES<T> setList(List<T> list) {
        this.list = list;
        return this;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getPage() {
        return page;
    }

    public void setPage(Long page) {
        this.page = page;
    }

    public Long getLimit() {
        return limit;
    }

    public void setLimit(Long limit) {
        this.limit = limit;
    }
}

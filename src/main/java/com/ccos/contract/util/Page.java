package com.ccos.contract.util;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.List;

//分页工具类
@Getter
@Setter
public class Page<T> {
    private Integer pageNum;//当前页
    private Integer pageSize;
    private long totalCount;
    private Integer totalPages;
    private Integer prePage;
    private Integer nextPage;
    private Integer startNavPage;
    private Integer endNavPage;
    private List<T> dataList;

    //带参构造
    public Page(Integer pageNum, Integer pageSize, long totalCount) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.totalCount = totalCount;

        //总页数
        this.totalPages=(int)Math.ceil(totalCount/(pageSize*1.0));
        //上一页
        this.prePage = pageNum- 1< 1? 1: pageNum- 1;
        //下一页
        this.nextPage = pageNum+ 1> totalPages? totalPages: pageNum+ 1;

        this.startNavPage = pageNum - 5; // 导航开始页  （当前页-5）
        this.endNavPage = pageNum + 4; // 导航结束页 （当前页+4）

        // 导航开始页 （当前页-5；如果当前页-5小于1，则导航开始页为1，此时导航结束页为导航开始数+9；如果导航开始数+9大于总页数，则导航结束页为总页数）
        if (this.startNavPage < 1) {
            // 如果当前页-5小于1，则导航开始页为1
            this.startNavPage = 1;
            // 此时导航结束页为导航开始数+9；如果导航开始数+9大于总页数，则导航结束页为总页数
            this.endNavPage = this.startNavPage + 9 > totalPages ? totalPages : this.startNavPage + 9;
        }
        // 导航结束页 （当前页+4；如果当前页+4大于总页数，则导航结束页为总页数，此时导航开始页为导航结束页-9；如果导航结束页-9小于1，则导航开始页为1）
        if (this.endNavPage > totalPages) {
            // 如果当前页+4大于总页数，则导航结束页为总页数
            this.endNavPage = totalPages;
            // 此时导航开始页为导航结束页-9；如果导航结束页-9小于1，则导航开始页为1
            this.startNavPage = this.endNavPage - 9 < 1 ? 1 : this.endNavPage - 9;

        }
    }
}

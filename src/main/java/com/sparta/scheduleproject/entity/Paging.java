package com.sparta.scheduleproject.entity;

import lombok.Getter;

@Getter
public class Paging {
    private int page;  // 현재 페이지 번호
    private int pageSize;  // 한 페이지에 표시할 데이터 수
    private int totalRecords;  // 총 데이터 개수
    private int totalPages;  // 총 페이지 수

    public Paging(int page, int pageSize, int totalRecords) {
        this.page = page;
        this.pageSize = pageSize;
        this.totalRecords = totalRecords;
        this.totalPages = (int) Math.ceil((double) totalRecords / pageSize);
    }

    public int getOffset() {
        return (page - 1) * pageSize;
    }
}


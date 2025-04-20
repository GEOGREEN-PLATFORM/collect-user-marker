package com.example.collect_user_marker.util.pagination;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class SimplifiedPageResponse<T> {

    private final int currentPage;
    private final int totalItems;
    private final int totalPages;
    private final List<T> content;

    public SimplifiedPageResponse(Page<T> page) {
        this.currentPage = page.getNumber();
        this.totalItems = (int) page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.content = page.getContent();
    }
}

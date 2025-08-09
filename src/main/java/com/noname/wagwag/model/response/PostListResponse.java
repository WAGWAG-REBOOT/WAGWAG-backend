package com.noname.wagwag.model.response;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class PostListResponse {
    
    private List<PostResponse> posts;
    private long totalElements;
    private int totalPages;
    private int currentPage;
    private int pageSize;
    private boolean hasNext;
    private boolean hasPrevious;
    
    public static PostListResponse from(Page<PostResponse> page) {
        PostListResponse response = new PostListResponse();
        response.setPosts(page.getContent());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setCurrentPage(page.getNumber());
        response.setPageSize(page.getSize());
        response.setHasNext(page.hasNext());
        response.setHasPrevious(page.hasPrevious());
        return response;
    }
}

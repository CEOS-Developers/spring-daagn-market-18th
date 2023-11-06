package com.ceos18.dangn.post.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class PostListResponseDto {
    private int total_page;
    private int current_page;
    private List<PostDto> post_list;

    public PostListResponseDto(int total_page, int current_page, List<PostDto> newDtos) {
        this.total_page = total_page;
        this.current_page = current_page;
        this.post_list = newDtos;
    }
}

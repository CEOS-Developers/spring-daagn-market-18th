package com.ceos18.dangn.post.dto;

import com.ceos18.dangn.domain.Post;
import lombok.Getter;

@Getter
public class PostDto {
    private Long post_id;
    private String title;
    private String thumbnail;
    private int price;
    private int view;

    private int total_ChatRoom;

    private String seller_town;

    public PostDto(Post post, int totalChatRoom, String sellerTown) {
        this.post_id = post.getId();
        this.title = post.getTitle();
        this.thumbnail = post.getThumbnail();
        this.price = post.getPrice();
        this.view = post.getView();

        this.total_ChatRoom = totalChatRoom;

        this.seller_town = sellerTown;
    }
}

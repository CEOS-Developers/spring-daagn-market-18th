package com.ceos18.dangn.post.dto;

import com.ceos18.dangn.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class PostDetailResponseDto {
    private Long post_id;

    private String seller_profileImage;
    private String seller_nickname;
    private String seller_town;
    private double seller_manners;

    private String title;
    private String category;
    private String description;
    private String wishplace;
    private int price;
    private boolean isPriceOffer;
    private int view;

    private int total_ChatRoom;

    public PostDetailResponseDto(Long postId, Post post, String sellerTown, int totalChatRoom) {
        this.post_id = postId;

        this.seller_profileImage = post.getSeller().getProfileImage();
        this.seller_nickname = post.getSeller().getNickname();
        this.seller_town = sellerTown;
        this.seller_manners = post.getSeller().getManners();

        this.title = post.getTitle();
        this.category = post.getCategory().getName();
        this.description = post.getDescription();
        this.wishplace = post.getWishPlace();
        this.view = post.getView();

        this.total_ChatRoom = totalChatRoom;
    }
}

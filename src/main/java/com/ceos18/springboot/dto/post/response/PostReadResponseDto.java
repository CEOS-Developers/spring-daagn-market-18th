package com.ceos18.springboot.dto.post.response;

import com.ceos18.springboot.entity.Post;
import com.ceos18.springboot.entity.enums.DealType;
import com.ceos18.springboot.entity.enums.PostStatus;
import com.ceos18.springboot.vo.CategoryVo;
import com.ceos18.springboot.vo.MemberVo;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class PostReadResponseDto {
	private Long id;
	private CategoryVo category; // 연관관계
	private MemberVo seller; // // 연관관계
	private String title;
	private DealType dealType;
	private String description;
	private String dealPlace;
	private BigDecimal price;
	private Boolean isPriceOffer;
	private PostStatus status;
	private int likedCount;
	private int viewCount;
	private LocalDateTime pullupAt;

	public static PostReadResponseDto fromEntity(Post post) {
		PostReadResponseDto dto = new PostReadResponseDto();
		dto.setId(post.getId());
		dto.setCategory(CategoryVo.fromEntity(post.getCategory()));
		dto.setSeller(MemberVo.fromEntity(post.getSeller()));
		dto.setTitle(post.getTitle());
		dto.setDealType(post.getDealType());
		dto.setDescription(post.getDescription());
		dto.setDealPlace(post.getDealPlace());
		dto.setPrice(post.getPrice());
		dto.setIsPriceOffer(post.getIsPriceOffer());
		dto.setStatus(post.getStatus());
		dto.setLikedCount(post.getLikedCount());
		dto.setViewCount(post.getViewCount());
		dto.setPullupAt(post.getPullupAt());
		return dto;
	}

//	public Post toEntity() {
//		Post post = new Post();
//		post.setId(this.id);
//		post.setCategory(this.category.toEntity());
//		post.setSeller(this.seller.toEntity());
//		post.setTitle(this.title);
//		post.setDealType(this.dealType);
//		post.setDescription(this.description);
//		post.setDealPlace(this.dealPlace);
//		post.setPrice(this.price);
//		post.setIsPriceOffer(this.isPriceOffer);
//		post.setStatus(this.status);
//		post.setLikedCount(this.likedCount);
//		post.setViewCount(this.viewCount);
//		post.setPullupAt(this.pullupAt);
//		return post;
//	}

}

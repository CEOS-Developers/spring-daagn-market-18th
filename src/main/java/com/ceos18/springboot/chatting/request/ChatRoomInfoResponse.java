package com.ceos18.springboot.chatting.request;

import com.ceos18.springboot.post.entity.PostStatus;
import com.ceos18.springboot.user.entity.User;
import com.ceos18.springboot.chatting.entity.ChatRoom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatRoomInfoResponse {
    private String id;

    private Long userId;

    private String userNickName;

    private String userImg;

    private PostStatus postStatus;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String lastMessage;

    private boolean isWriter;

    public static ChatRoomInfoResponse of(ChatRoom chatRoom, User user, boolean isWriter) {
        return ChatRoomInfoResponse.builder()
                .id(chatRoom.getId())
                .userId(user.getId())
                .userNickName(user.getNickname())
                .userImg(user.getImgUrl())
                .postStatus(chatRoom.getPost().getPostStatus())
                .createdAt(chatRoom.getCreatedAt())
                .updatedAt(chatRoom.getUpdatedAt())
                .lastMessage(chatRoom.getLastMessage())
                .isWriter(isWriter)
                .build();
    }
}

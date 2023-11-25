package com.ceos18.springboot.chatting.entity;

import com.ceos18.springboot.common.entity.BaseTimeEntity;
import com.ceos18.springboot.exception.ErrorCode;
import com.ceos18.springboot.exception.KarrotException;
import com.ceos18.springboot.post.entity.Post;
import com.ceos18.springboot.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ChatRoom extends BaseTimeEntity {

    private static final long serialVersionUID = 6494678977089006639L;

    @Id
    @Column(name = "roomId")
    private String id;

    @Column(length = 1000)
    private String lastMessage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "callerId", referencedColumnName = "id")
    private User caller;   //도움 요청자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "helperId", referencedColumnName = "id")
    private User helper;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatMessage> chatMessageList = new ArrayList<>();

    @Builder
    public ChatRoom(User caller, User helper, Post post) {
        this.id = UUID.randomUUID().toString();
        this.caller = caller;
        this.helper = helper;
        this.post = post;
    }

    public void updateLastMessage(String lastMessage) {
        if (!Objects.nonNull(lastMessage)) {
            throw new KarrotException(ErrorCode.VALUE_IS_NONNULL);
        } else {
            this.lastMessage = lastMessage;
        }
    }

    public void addChatMessage(ChatMessage message) {
        this.chatMessageList.add(message);
    }

}

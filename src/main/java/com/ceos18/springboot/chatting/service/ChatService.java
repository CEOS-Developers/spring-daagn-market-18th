package com.ceos18.springboot.chatting.service;

import com.ceos18.springboot.chatting.entity.ChatRoom;
import com.ceos18.springboot.chatting.repository.ChatRoomRepository;
import com.ceos18.springboot.chatting.request.ChatRoomInfoResponse;
import com.ceos18.springboot.exception.KarrotException;
import com.ceos18.springboot.post.entity.DealMethod;
import com.ceos18.springboot.post.entity.Post;
import com.ceos18.springboot.post.entity.PostStatus;
import com.ceos18.springboot.post.repository.PostRepository;
import com.ceos18.springboot.exception.ErrorCode;
import com.ceos18.springboot.user.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final PostRepository postRepository;

    public static final String ENTER_INFO = "ENTER_INFO";
    private static final String CHAT_ROOMS = "CHAT_ROOM";
    private final ObjectMapper objectMapper;

    private Map<String, ChatRoom> chatRooms;

    @PostConstruct
    private void init() {
        chatRooms = new LinkedHashMap<>();
    }

    //채팅방 생성
    public ChatRoomInfoResponse createChatRoom(User member, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new KarrotException(ErrorCode.POST_NOT_FOUND));

        ChatRoom chatRoom;
        User user;

        // 거래방법 (판매하기, 나눔하기) 에 따라 다르게 저장
        if (post.getDealMethod().equals(DealMethod.SELL)) {
            chatRoom = ChatRoom.builder()
                    .caller(post.getUser())
                    .helper(member)
                    .post(post)
                    .build();
            user = chatRoom.getCaller();

        } else {
            chatRoom = ChatRoom.builder()
                    .caller(member)
                    .helper(post.getUser())
                    .post(post)
                    .build();
            user = chatRoom.getHelper();
        }

        chatRooms.put(chatRoom.getId(), chatRoom);
        chatRoomRepository.save(chatRoom);

        if (post.getPostStatus().equals(PostStatus.IN_PROGRESS))
            post.updatePostStatus(PostStatus.CHATTING);

        return ChatRoomInfoResponse.of(chatRoom, user, false);
    }

}

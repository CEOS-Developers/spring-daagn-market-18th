package com.ceos18.springboot.chatting.controller;

import com.ceos18.springboot.chatting.request.ChatRoomRequest;
import com.ceos18.springboot.chatting.request.ChatRoomInfoResponse;
import com.ceos18.springboot.chatting.service.ChatService;
import com.ceos18.springboot.login.CurrentUser;
import com.ceos18.springboot.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/chat")
@Slf4j
public class ChatRoomController {

    private static final Logger logger = LoggerFactory.getLogger(ChatRoomController.class);
    private final ChatService chatService;

    //채팅방 생성
    @PostMapping("/room")
    public ResponseEntity<ChatRoomInfoResponse> createRoom(@CurrentUser User user,
                                                           @RequestBody ChatRoomRequest request) {
        ChatRoomInfoResponse chatRoomInfoResponse = chatService.createChatRoom(user, request.getPostId());

        logger.info("Chat room created by user: {} with room ID: {}", user.getId(), chatRoomInfoResponse.getId());

        return ResponseEntity.ok(chatRoomInfoResponse);
    }

}

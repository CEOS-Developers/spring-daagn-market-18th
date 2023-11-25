package com.ceos18.springboot.chatting.controller;

import com.ceos18.springboot.chatting.entity.ChatMessage;
import com.ceos18.springboot.chatting.entity.ChatRoom;
import com.ceos18.springboot.chatting.repository.ChatMessageRepository;
import com.ceos18.springboot.chatting.repository.ChatRoomRepository;
import com.ceos18.springboot.chatting.service.ChatService;
import com.ceos18.springboot.exception.KarrotException;
import com.ceos18.springboot.exception.ErrorCode;
import com.ceos18.springboot.post.entity.DealMethod;
import com.ceos18.springboot.user.entity.User;
import com.ceos18.springboot.user.repository.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Slf4j
@RestController
@CrossOrigin
@Transactional
public class ChattingController {
    private final SimpMessagingTemplate template;
    private final SimpMessageSendingOperations sendingOperations;
    private final ChatService chatService;
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;

    //메시지 처리
    @MessageMapping("/message")
    public void message(SendMessage sendMessage) {
        ChatRoom chatRoom = chatRoomRepository.findById(sendMessage.getRoomId())
                .orElseThrow(() -> new KarrotException(ErrorCode.CHATROOM_NOT_FOUND));

        ChatMessage message;

        if (ChatMessage.MessageType.ENTER.equals(sendMessage.getType())) {
            message = createEnterMessage(chatRoom, sendMessage.getType());
            sendMessage.setMessage(message.getMessage());
        } else {
            User user = userRepository.findById(sendMessage.getSenderId())
                    .orElseThrow(() -> new KarrotException(ErrorCode.MEMBER_NOT_FOUND));
            message = createTalkMessage(chatRoom, user, sendMessage.getType(), sendMessage.getMessage());
            sendMessage.setMessage(message.getMessage());
        }


        chatMessageRepository.save(message);
        chatRoom.addChatMessage(message);
        chatRoom.updateLastMessage(message.getMessage());
        chatRoomRepository.save(chatRoom);

        if (message.getType().equals(ChatMessage.MessageType.TALK)) {
            sendMessage.setSenderId(message.getSender().getId());
            sendMessage.setSenderNickname(message.getSender().getNickname());
            sendMessage.setImgUrl(message.getSender().getImgUrl());
        }
        sendMessage.setSendTime(message.getSendDate());
        sendingOperations.convertAndSend("/sub/chat/room/" + message.getChatRoom().getId(), sendMessage);
    }



    private ChatMessage createTalkMessage(ChatRoom chatRoom, User sender, ChatMessage.MessageType messageType, String messageContent) {
        return ChatMessage.builder()
                .sender(sender)
                .chatRoom(chatRoom)
                .messageType(messageType.name())
                .message(messageContent)
                .build();
    }

    private ChatMessage createEnterMessage(ChatRoom chatRoom, ChatMessage.MessageType messageType) {
        DealMethod dealMethod = chatRoom.getPost().getDealMethod();
        User member = dealMethod.equals(DealMethod.SELL) ? chatRoom.getHelper() : chatRoom.getCaller();
        String messageContent = dealMethod.equals(DealMethod.SELL) ?
                "Karrot!" :
                member.getNickname() + "님이 채팅을 시작했어요.";

        return ChatMessage.builder()
                .sender(null)
                .chatRoom(chatRoom)
                .messageType(messageType.name())
                .message(messageContent)
                .build();
    }



    @Data
    private static class SendMessage {
        ChatMessage.MessageType type;
        String roomId;
        String message;
        Long senderId;
        String senderNickname;
        String imgUrl;
        LocalDateTime sendTime;
    }
}


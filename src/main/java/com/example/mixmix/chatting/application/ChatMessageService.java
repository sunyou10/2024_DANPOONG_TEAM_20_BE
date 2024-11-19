package com.example.mixmix.chatting.application;

import com.example.mixmix.chatting.domain.ChatMessage;
import com.example.mixmix.chatting.domain.ChatRoom;
import com.example.mixmix.chatting.domain.repository.ChatMessageRepository;
import com.example.mixmix.chatting.domain.repository.ChatRoomRepository;
import com.example.mixmix.chatting.exception.ExistsChatRoomException;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;

    @Transactional
    public void saveMessage(String roomId, String sender, String content) {
        ChatMessage chatMessage = new ChatMessage();
        ChatRoom chatRoom = chatRoomRepository.findById(Long.parseLong(roomId)).
                orElseThrow(ExistsChatRoomException::new);
        chatMessage.setChatRoom(chatRoom);
        chatMessage.setSender(sender);
        chatMessage.setContent(content);
        chatMessage.setTimestamp(LocalDateTime.now());

        chatMessageRepository.save(chatMessage);
    }
}

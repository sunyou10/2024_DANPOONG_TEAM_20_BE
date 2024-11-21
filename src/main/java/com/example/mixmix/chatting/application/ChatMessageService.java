package com.example.mixmix.chatting.application;

import com.example.mixmix.chatting.api.dto.response.ChatMessageResList;
import com.example.mixmix.chatting.api.dto.response.ChatMessageResDto;
import com.example.mixmix.chatting.domain.ChatMessage;
import com.example.mixmix.chatting.domain.ChatRoom;
import com.example.mixmix.chatting.domain.repository.ChatMessageRepository;
import com.example.mixmix.chatting.domain.repository.ChatRoomRepository;
import com.example.mixmix.chatting.exception.ExistsChatRoomException;
import com.example.mixmix.global.dto.PageInfoResDto;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public ChatMessageResList findChatMessages(Long roomId, Pageable pageable) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(ExistsChatRoomException::new);

        Page<ChatMessage> messagePage = chatMessageRepository.findByChatRoom(chatRoom, pageable);

        List<ChatMessageResDto> messageDtos = messagePage.getContent().stream()
                .map(ChatMessageResDto::fromEntity)
                .toList();

        PageInfoResDto pageInfoResDto = PageInfoResDto.builder()
                .totalPages(messagePage.getTotalPages())
                .totalItems(messagePage.getTotalElements())
                .currentPage(messagePage.getNumber())
                .build();

        return ChatMessageResList.of(messageDtos, pageInfoResDto);
    }
}

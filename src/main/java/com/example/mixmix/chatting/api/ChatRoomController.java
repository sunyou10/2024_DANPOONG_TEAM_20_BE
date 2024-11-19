package com.example.mixmix.chatting.api;

import com.example.mixmix.chatting.api.dto.request.ChatRoomReqDto;
import com.example.mixmix.chatting.api.dto.response.ChatRoomResDto;
import com.example.mixmix.chatting.application.ChatRoomService;
import com.example.mixmix.global.annotation.CurrentUserEmail;
import com.example.mixmix.global.template.RspTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat-rooms")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @PostMapping
    public RspTemplate<ChatRoomResDto> createChatRoom(
            @CurrentUserEmail String email,
            @RequestBody ChatRoomReqDto request) {
        ChatRoomResDto chatRoomResDto = chatRoomService.createChatRoom(email, request);

        return new RspTemplate<>(HttpStatus.CREATED, "채팅방 생성", chatRoomResDto);
    }
}

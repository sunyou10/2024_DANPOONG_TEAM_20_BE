package com.example.mixmix.chatting.application;

import com.example.mixmix.chatting.api.dto.request.ChatRoomReqDto;
import com.example.mixmix.chatting.api.dto.response.ChatRoomResDto;
import com.example.mixmix.chatting.domain.ChatRoom;
import com.example.mixmix.chatting.domain.repository.ChatRoomRepository;
import com.example.mixmix.member.domain.Member;
import com.example.mixmix.member.domain.repository.MemberRepository;
import com.example.mixmix.member.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public ChatRoomResDto createChatRoom(String email, ChatRoomReqDto chatRoomReqDto) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(MemberNotFoundException::new);

        ChatRoom chatRoom = ChatRoom.builder()
                .name(chatRoomReqDto.roomName())
                .member(member)
                .build();

        ChatRoom savedChatRoom = chatRoomRepository.save(chatRoom);

        return new ChatRoomResDto(savedChatRoom.getId(), savedChatRoom.getRoomName(), member.getId());
    }
}

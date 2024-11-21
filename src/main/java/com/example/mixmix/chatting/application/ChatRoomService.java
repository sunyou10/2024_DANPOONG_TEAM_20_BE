package com.example.mixmix.chatting.application;

import com.example.mixmix.chatting.api.dto.request.ChatRoomReqDto;
import com.example.mixmix.chatting.api.dto.response.ChatRoomResList;
import com.example.mixmix.chatting.api.dto.response.ChatRoomResDto;
import com.example.mixmix.chatting.domain.ChatRoom;
import com.example.mixmix.chatting.domain.repository.ChatRoomRepository;
import com.example.mixmix.global.dto.PageInfoResDto;
import com.example.mixmix.member.domain.Member;
import com.example.mixmix.member.domain.repository.MemberRepository;
import com.example.mixmix.member.exception.MemberNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public ChatRoomResDto createChatRoom(String email, ChatRoomReqDto chatRoomReqDto) {
        Member frommember = memberRepository.findByEmail(email)
                .orElseThrow(MemberNotFoundException::new);
        Member toMember = memberRepository.findById(chatRoomReqDto.toMemberId())
                .orElseThrow(MemberNotFoundException::new);

        ChatRoom chatRoom = ChatRoom.builder()
                .name(chatRoomReqDto.roomName())
                .fromMember(frommember)
                .toMember(toMember)
                .build();

        ChatRoom savedChatRoom = chatRoomRepository.save(chatRoom);

        return ChatRoomResDto.from(savedChatRoom.getId(),
                savedChatRoom.getRoomName(),
                frommember.getId(),
                toMember.getId(),
                frommember.getName());
    }

    public ChatRoomResList getChatRooms(String email, Pageable pageable) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(MemberNotFoundException::new);

        Page<ChatRoom> chatRoomPage = chatRoomRepository.findChatRoomsByMember(member, pageable);

        List<ChatRoomResDto> chatRoomResDtos = chatRoomPage.getContent().stream()
                .map(chatRoom -> ChatRoomResDto.builder()
                        .roomId(chatRoom.getId())
                        .name(chatRoom.getRoomName())
                        .fromMemberId(chatRoom.getFromMember().getId())
                        .toMemberId(chatRoom.getToMember().getId())
                        .loginUserName(chatRoom.getFromMember().getName())
                        .build()
                )
                .toList();

        PageInfoResDto pageInfoResDto = PageInfoResDto.builder()
                .totalPages(chatRoomPage.getTotalPages())
                .totalItems(chatRoomPage.getTotalElements())
                .currentPage(chatRoomPage.getNumber())
                .build();

        return ChatRoomResList.of(chatRoomResDtos, pageInfoResDto);
    }
}
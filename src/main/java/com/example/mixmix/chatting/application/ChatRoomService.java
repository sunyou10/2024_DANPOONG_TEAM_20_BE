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
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(MemberNotFoundException::new);

        ChatRoom chatRoom = ChatRoom.builder()
                .name(chatRoomReqDto.roomName())
                .member(member)
                .build();

        ChatRoom savedChatRoom = chatRoomRepository.save(chatRoom);

        return new ChatRoomResDto(savedChatRoom.getId(), savedChatRoom.getRoomName(), member.getId());
    }

    public ChatRoomResList getChatRooms(String email, Pageable pageable) {
        // 이메일로 멤버 조회
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(MemberNotFoundException::new);

        // 멤버와 연결된 채팅방 조회 (페이지 정보 포함)
        Page<ChatRoom> chatRoomPage = chatRoomRepository.findByMember(member, pageable);

        // ChatRoom -> ChatRoomResDto로 변환
        List<ChatRoomResDto> chatRoomResDtos = chatRoomPage.getContent().stream()
                .map(chatRoom -> ChatRoomResDto.builder()
                        .id(chatRoom.getId())
                        .name(chatRoom.getRoomName())
                        .memberId(chatRoom.getMember().getId())
                        .build()
                )
                .toList();

        // 페이지 정보 생성
        PageInfoResDto pageInfoResDto = PageInfoResDto.builder()
                .totalPages(chatRoomPage.getTotalPages())
                .totalItems(chatRoomPage.getTotalElements())
                .currentPage(chatRoomPage.getNumber())
                .build();

        // ChatRoomList 생성 및 반환
        return ChatRoomResList.of(chatRoomResDtos, pageInfoResDto);
    }

}

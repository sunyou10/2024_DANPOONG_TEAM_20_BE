package com.example.mixmix.chatting.api.dto.response;

import lombok.Builder;

@Builder
public record ChatRoomResDto(
        Long roomId,
        String name,
        Long fromMemberId,
        Long toMemberId,
        String loginUserName
) {
    public static ChatRoomResDto from(Long roomId,
                                      String name,
                                      Long fromMemberId,
                                      Long toMemberId,
                                      String loginUserName) {
        return ChatRoomResDto.builder()
                .roomId(roomId)
                .name(name)
                .fromMemberId(fromMemberId)
                .toMemberId(toMemberId)
                .loginUserName(loginUserName)
                .build();
    }
}
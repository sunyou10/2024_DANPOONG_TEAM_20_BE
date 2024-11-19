package com.example.mixmix.chatting.api.dto.response;

import lombok.Builder;

@Builder
public record ChatRoomResDto(
        Long id,
        String name,
        Long memberId
) {
}

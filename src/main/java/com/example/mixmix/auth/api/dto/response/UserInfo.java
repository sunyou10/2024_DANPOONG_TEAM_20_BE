package com.example.mixmix.auth.api.dto.response;

public record UserInfo(
        String email,
        String name,
        String picture,
        String nickname
) {
}

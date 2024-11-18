package com.example.mixmix.ranking.api.dto.response;

import com.example.mixmix.ranking.domain.Ranking;

public record RankingInfoResDto(
        String email,
        Integer streakRank
) {
    public static RankingInfoResDto from(Ranking ranking) {
        return new RankingInfoResDto(
                ranking.getMember().getEmail(),
                ranking.getStreakRank()
        );
    }
}

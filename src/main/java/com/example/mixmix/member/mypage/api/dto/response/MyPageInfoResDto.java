package com.example.mixmix.member.mypage.api.dto.response;

import com.example.mixmix.member.domain.Member;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record MyPageInfoResDto(
        String introduction,
        String nationality,
        String school,
        String nickName,
        String picture,
        String name,
        String streak,
        Integer streakRank,
        long educationCount,
        long socialCount,
        LocalDateTime lastStreakUpdate,
        boolean isStreakUpdated

) {
    public static MyPageInfoResDto from(Member member, long educationCount, long socialCount) {
        return MyPageInfoResDto.builder()
                .introduction(member.getIntroduction())
                .nationality(member.getNationality())
                .school(member.getSchool())
                .nickName(member.getNickname())
                .picture(member.getPicture())
                .name(member.getName())
                .streak(String.valueOf(member.getStreak()))
                .streakRank(member.getRanking().getStreakRank())
                .educationCount(educationCount)
                .socialCount(socialCount)
                .lastStreakUpdate(member.getLastStreakUpdate())
                .isStreakUpdated(member.isStreakUpdated())
                .build();
    }
}

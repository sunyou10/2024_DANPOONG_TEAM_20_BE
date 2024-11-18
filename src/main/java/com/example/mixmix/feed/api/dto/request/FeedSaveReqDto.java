package com.example.mixmix.feed.api.dto.request;

import com.example.mixmix.feed.domain.Feed;
import com.example.mixmix.feed.domain.FeedType;
import com.example.mixmix.member.domain.Member;

public record FeedSaveReqDto(
        String feedImage,
        String title,
        String contents,
        String hashTags,
        FeedType feedType
) {
    public Feed toEntity(Member member) {
        return Feed.builder()
                .feedImage(feedImage)
                .title(title)
                .contents(contents)
                .hashTags(hashTags)
                .feedType(feedType)
                .member(member)
                .build();
    }
}

package com.example.mixmix.feed.api.dto.response;

import com.example.mixmix.feed.domain.Feed;
import com.example.mixmix.feed.domain.FeedType;
import lombok.Builder;

@Builder
public record FeedInfoResDto(
        String feedImage,
        String title,
        String contents,
        String hashTags,
        FeedType feedType,
        Long memberId
) {
    public static FeedInfoResDto from(Feed feed) {
        return FeedInfoResDto.builder()
                .feedImage(feed.getFeedImage())
                .title(feed.getTitle())
                .contents(feed.getContents())
                .hashTags(feed.getHashTags())
                .feedType(feed.getFeedType())
                .memberId(feed.getMember().getId())
                .build();
    }
}

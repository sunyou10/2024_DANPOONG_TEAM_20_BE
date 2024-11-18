package com.example.mixmix.feed.domain.repository;

import com.example.mixmix.feed.api.dto.response.FeedInfoResDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FeedCustomRepository {
    Page<FeedInfoResDto> findAllByFeedType(String keyword, Pageable pageable);
}

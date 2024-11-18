package com.example.mixmix.feed.application;

import com.example.mixmix.feed.api.dto.request.FeedSaveReqDto;
import com.example.mixmix.feed.api.dto.response.FeedInfoResDto;
import com.example.mixmix.feed.api.dto.response.FeedListResDto;
import com.example.mixmix.feed.api.dto.response.FeedSaveInfoResDto;
import com.example.mixmix.feed.domain.Feed;
import com.example.mixmix.feed.domain.repository.FeedRepository;
import com.example.mixmix.feed.exception.FeedNotFoundException;
import com.example.mixmix.global.dto.PageInfoResDto;
import com.example.mixmix.member.domain.Member;
import com.example.mixmix.member.domain.repository.MemberRepository;
import com.example.mixmix.member.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FeedService {

    private final MemberRepository memberRepository;
    private final FeedRepository feedRepository;

    // 피드 생성
    @Transactional
    public FeedSaveInfoResDto save(String email, FeedSaveReqDto feedSaveReqDto) {
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
        member.incrementStreak();

        Feed feed = feedRepository.save(feedSaveReqDto.toEntity(member));

        return FeedSaveInfoResDto.of(feed, member.getId());
    }

    // 피드 개별 조회
    public FeedInfoResDto findById(Long feedId) {
        Feed feed = feedRepository.findById(feedId).orElseThrow(FeedNotFoundException::new);

        return FeedInfoResDto.from(feed);
    }

    // 피드 soical/education 구분해서 전체 조회
    public FeedListResDto findAllByFeedType(String keyword, Pageable pageable) {
        Page<FeedInfoResDto> feedInfoResDtos = feedRepository.findAllByFeedType(keyword, pageable);

        return FeedListResDto.of(
                feedInfoResDtos.getContent(),
                PageInfoResDto.from(feedInfoResDtos));
    }
}

package com.example.mixmix.feed.domain.repository;

import com.example.mixmix.feed.api.dto.response.FeedInfoResDto;
import com.example.mixmix.feed.domain.FeedType;
import com.example.mixmix.feed.domain.QFeed;
import com.example.mixmix.global.entity.Status;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FeedCustomRepositoryImpl implements FeedCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<FeedInfoResDto> findAllByFeedType(String keyword, Pageable pageable) {
        QFeed feed = QFeed.feed;

        FeedType feedType = FeedType.valueOf(keyword);

        List<FeedInfoResDto> content = queryFactory
                .select(Projections.constructor(
                        FeedInfoResDto.class,
                        feed.feedImage,
                        feed.title,
                        feed.contents,
                        feed.hashTags,
                        feed.feedType,
                        feed.member.id,
                        feed.id
                ))
                .from(feed)
                .where(
                        feed.feedType.eq(feedType)
                                .and(feed.status.eq(Status.valueOf("ACTIVE")))
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(feed.count())
                .from(feed)
                .where(feed.feedType.eq(feedType))
                .fetchOne();

        return PageableExecutionUtils.getPage(content, pageable, () -> total);
    }

    @Override
    public long countEducationFeedsByMemberId(Long memberId) {
        QFeed feed = QFeed.feed;

        return queryFactory
                .select(feed.count())
                .from(feed)
                .where(
                        feed.member.id.eq(memberId)
                                .and(feed.feedType.eq(FeedType.EDUCATION))
                                .and(feed.status.eq(com.example.mixmix.global.entity.Status.ACTIVE))
                )
                .fetchOne();
    }

    @Override
    public long countSocialFeedsByMemberId(Long memberId) {
        QFeed feed = QFeed.feed;

        return queryFactory
                .select(feed.count())
                .from(feed)
                .where(
                        feed.member.id.eq(memberId)
                                .and(feed.feedType.eq(FeedType.SOCIAL))
                                .and(feed.status.eq(com.example.mixmix.global.entity.Status.ACTIVE))
                )
                .fetchOne();
    }
}

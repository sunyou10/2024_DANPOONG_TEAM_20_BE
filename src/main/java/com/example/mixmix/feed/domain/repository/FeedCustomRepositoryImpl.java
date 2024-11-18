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

}

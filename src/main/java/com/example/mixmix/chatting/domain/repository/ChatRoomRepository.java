package com.example.mixmix.chatting.domain.repository;

import com.example.mixmix.chatting.domain.ChatRoom;
import com.example.mixmix.member.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Page<ChatRoom> findByMember(Member member, Pageable pageable);
}

package com.example.mixmix.notification.domain.repository;

import com.example.mixmix.member.domain.Member;
import com.example.mixmix.notification.domain.Notification;
import com.example.mixmix.notification.domain.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long>, NotificationCustomRepository {
    List<Notification> findAllByReceiver(Member receiver);
    List<Notification> findAllByReceiverAndType(Member receiver, Type type);

    Boolean existsByReceiverAndIsReadFalse(Member receiver);
}

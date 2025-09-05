package com.sarankirthic.lms.repository;

import com.sarankirthic.lms.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}

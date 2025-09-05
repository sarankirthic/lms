package com.sarankirthic.lms.service;

import com.sarankirthic.lms.model.Notification;
import com.sarankirthic.lms.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public List<Notification> getAllNotification(){
        return  notificationRepository.findAll();
    }

    public Notification getNotificationById(Long id){
        return notificationRepository.findById(id).orElse(null);
    }

    public Notification saveNotification(Notification notification){
        return notificationRepository.save(notification);
    }

    public void deleteNotification(Long id){
        notificationRepository.deleteById(id);
    }
}

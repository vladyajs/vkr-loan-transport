package com.vkr.models;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long notificationId;

    @ManyToOne
    @JoinColumn(name = "loan_application_id")
    private LoanApplication loanApplication;

    private String message;
    private LocalDateTime sentAt;
    private String notificationType;

    // Конструкторы, геттеры и сеттеры

    public void sendNotification() {
        // Логика отправки уведомлений
    }

    public void getNotificationStatus() {
        // Логика получения статуса уведомления
    }
}


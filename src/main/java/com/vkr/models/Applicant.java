package com.vkr.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "applicants", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "inn")
})
@Getter
@Setter
public class Applicant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applicantId;

    @NotNull
    @Column(nullable = false)
    private String firstName;

    @NotNull
    @Column(nullable = false)
    private String lastName;

    @Past(message = "Дата рождения должна быть в прошлом")
    @NotNull
    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Email(message = "Некорректный формат email")
    @NotNull
    @Column(nullable = false, unique = true)
    private String email;

    @Pattern(regexp = "^(\\+7|7|8)\\(?\\d{3}\\)?\\d{3}-?\\d{2}-?\\d{2}$", message = "Некорректный формат номера телефона")
    @NotNull
    @Column(nullable = false)
    private String phoneNumber;

    private String address;

    @NotNull(message = "ИНН не может быть пустым")
    @Size(min = 10, max = 12, message = "ИНН должен быть длиной 10 или 12 символов")
    @Column(nullable = false, unique = true)
    private String inn; // Идентификационный номер налогоплательщика

    @PrePersist
    public void validateApplicant() {
        if (!isAdult()) {
            throw new IllegalArgumentException("Заявитель должен быть старше 18 лет");
        }
        if (!isValidInn()) {
            throw new IllegalArgumentException("ИНН невалиден");
        }
    }

    private boolean isValidInn() {
        return inn != null && (inn.length() == 10 || inn.length() == 12);
    }

    private boolean isAdult() {
        return LocalDate.now().minusYears(18).isAfter(dateOfBirth);
    }
}

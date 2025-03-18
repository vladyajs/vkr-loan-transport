package com.vkr.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Applicant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long applicantId;

    private String firstName;
    private String lastName;

    @Past(message = "Дата рождения должна быть в прошлом")
    private LocalDate dateOfBirth;

    @Email(message = "Некорректный формат email")
    private String email;

    @Pattern(regexp = "^(\\+7|7|8)\\(?\\d{3}\\)?\\d{3}-?\\d{2}-?\\d{2}$", message = "Некорректный формат номера телефона")
    private String phoneNumber;

    private String address;

    @NotNull(message = "ИНН не может быть пустым")
    @Size(min = 10, max = 12, message = "ИНН должен быть длиной 10 или 12 символов")
    private String inn; // Идентификационный номер налогоплательщика

    // Метод для валидации ИНН
    public boolean isValidInn() {
        // Простая проверка на длину ИНН
        return inn != null && (inn.length() == 10 || inn.length() == 12);
    }

    // Пример метода для проверки возраста заявителя
    public boolean isAdult() {
        return LocalDate.now().minusYears(18).isAfter(dateOfBirth);
    }

    // Метод для проверки правильности всех данных
    public void validateApplicant() {
        if (!isAdult()) {
            throw new IllegalArgumentException("Заявитель должен быть старше 18 лет");
        }
        if (!isValidInn()) {
            throw new IllegalArgumentException("ИНН невалиден");
        }
    }
}

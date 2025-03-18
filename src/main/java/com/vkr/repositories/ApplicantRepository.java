package com.vkr.repositories;

import com.vkr.models.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicantRepository extends JpaRepository<Applicant, Long> {
    // Здесь можно добавить методы для поиска по email, ИНН и т.д.
    Applicant findByEmail(String email);
    Applicant findByInn(String inn);
}

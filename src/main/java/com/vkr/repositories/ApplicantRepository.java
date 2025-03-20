package com.vkr.repositories;

import com.vkr.models.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicantRepository extends JpaRepository<Applicant, Long> {
    // Здесь можно добавить методы для поиска по email, ИНН и т.д.
    Optional<Applicant> findByEmail(String email);
    Optional<Applicant> findByInn(String inn);
}

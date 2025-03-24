package com.vkr.services;

import com.vkr.models.Applicant;
import com.vkr.repositories.ApplicantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApplicantService {

    private final ApplicantRepository applicantRepository;

    // Создание нового заявителя
    public Applicant createApplicant(Applicant applicant) {
        // Валидация данных заявителя перед сохранением
        applicant.validateApplicant();
        return applicantRepository.save(applicant);
    }

    // Поиск заявителя по ID
    public Optional<Applicant> findApplicantById(Long applicantId) {
        return applicantRepository.findById(applicantId);
    }

    public Applicant findApplicantByEmail(String email) {
        return applicantRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Заявитель не найден"));
    }

    public Applicant findApplicantByInn(String inn) {
        return applicantRepository.findByInn(inn)
                .orElseThrow(() -> new RuntimeException("Заявитель не найден"));
    }

    public List<Applicant> findAllApplicants() {
        return applicantRepository.findAll();
    }


    // Обновление данных заявителя
    public Applicant updateApplicant(Long applicantId, Applicant updatedApplicant) {
        Applicant applicant = applicantRepository.findById(applicantId)
                .orElseThrow(() -> new IllegalArgumentException("Заявитель не найден"));

        // Обновление данных заявителя
        applicant.setFirstName(updatedApplicant.getFirstName());
        applicant.setLastName(updatedApplicant.getLastName());
        applicant.setDateOfBirth(updatedApplicant.getDateOfBirth());
        applicant.setEmail(updatedApplicant.getEmail());
        applicant.setPhoneNumber(updatedApplicant.getPhoneNumber());
        applicant.setAddress(updatedApplicant.getAddress());
        applicant.setInn(updatedApplicant.getInn());

        // Валидация после обновления
        applicant.validateApplicant();

        return applicantRepository.save(applicant);
    }
}

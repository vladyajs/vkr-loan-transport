package com.vkr.controllers;

import com.vkr.models.Applicant;
import com.vkr.services.ApplicantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/applicants")
public class ApplicantController {

    @Autowired
    private ApplicantService applicantService;

    // Создание нового заявителя
    @PostMapping
    public ResponseEntity<Applicant> createApplicant(@RequestBody Applicant applicant) {
        Applicant createdApplicant = applicantService.createApplicant(applicant);
        return ResponseEntity.ok(createdApplicant);
    }

    // Получение заявителя по ID
    @GetMapping("/{applicantId}")
    public ResponseEntity<Applicant> getApplicantById(@PathVariable Long applicantId) {
        return applicantService.findApplicantById(applicantId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Получение заявителя по email
    @GetMapping("/email/{email}")
    public ResponseEntity<Applicant> getApplicantByEmail(@PathVariable String email) {
        try {
            return ResponseEntity.ok(applicantService.findApplicantByEmail(email));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Получение заявителя по ИНН
    @GetMapping("/inn/{inn}")
    public ResponseEntity<Applicant> getApplicantByInn(@PathVariable String inn) {
        try {
            return ResponseEntity.ok(applicantService.findApplicantByInn(inn));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Обновление данных заявителя
    @PutMapping("/{applicantId}")
    public ResponseEntity<Applicant> updateApplicant(@PathVariable Long applicantId, @RequestBody Applicant updatedApplicant) {
        return ResponseEntity.ok(applicantService.updateApplicant(applicantId, updatedApplicant));
    }
}

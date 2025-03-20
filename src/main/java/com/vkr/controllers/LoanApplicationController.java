package com.vkr.controllers;

import com.vkr.models.AuditLog;
import com.vkr.models.LoanApplication;
import com.vkr.models.enums.ApplicationStatus;
import com.vkr.services.AuditLogService;
import com.vkr.services.LoanApplicationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/loan-applications")
@AllArgsConstructor
public class LoanApplicationController {

    private final LoanApplicationService loanApplicationService;
    private final AuditLogService auditLogService;

    @PostMapping
    public ResponseEntity<Long> createLoanApplication(@Valid @RequestBody LoanApplication loanApplication) {
        Long createdLoanApplication = loanApplicationService.submitLoanApplication(loanApplication);
        return ResponseEntity.ok(createdLoanApplication);
    }

    @PutMapping("/{loanApplicationId}")
    public ResponseEntity<LoanApplication> updateLoanApplicationStatus(@PathVariable Long loanApplicationId, @RequestParam ApplicationStatus status) {
        LoanApplication updatedLoanApplication = loanApplicationService.updateApplicationStatus(loanApplicationId, status);
        return ResponseEntity.ok(updatedLoanApplication);
    }

    @GetMapping
    public ResponseEntity<List<LoanApplication>> getAllLoanApplications() {
        return ResponseEntity.ok(loanApplicationService.getAllLoanApplications());
    }

    @GetMapping("/{loanApplicationId}")
    public ResponseEntity<LoanApplication> getLoanApplication(@PathVariable Long loanApplicationId) {
        return ResponseEntity.ok(loanApplicationService.getLoanApplication(loanApplicationId));
    }

    /*@PostMapping("/{loanApplicationId}/resubmit")
    public ResponseEntity<LoanApplication> resubmitLoanApplication(@PathVariable Long loanApplicationId) {
        return ResponseEntity.ok(loanApplicationService.resubmitLoanApplication(loanApplicationId));
    }*/

    @GetMapping("/{loanApplicationId}/audit-logs")
    public ResponseEntity<List<AuditLog>> getAuditLogs(@PathVariable Long loanApplicationId) {
        List<AuditLog> logs = auditLogService.getAuditLogsByApplicationId(loanApplicationId);
        return ResponseEntity.ok(logs);
    }




}



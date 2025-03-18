package com.vkr.controllers;

import com.vkr.models.LoanApplication;
import com.vkr.services.LoanApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/loan-applications")
public class LoanApplicationController {

    @Autowired
    private LoanApplicationService loanApplicationService;

    @PostMapping
    public ResponseEntity<Long> createLoanApplication(@Valid @RequestBody LoanApplication loanApplication) {
        Long createdLoanApplication = loanApplicationService.submitLoanApplication(loanApplication);
        return ResponseEntity.ok(createdLoanApplication);
    }

    @PutMapping("/{loanApplicationId}")
    public ResponseEntity<LoanApplication> updateLoanApplicationStatus(@PathVariable Long loanApplicationId, @RequestParam String status) {
        LoanApplication updatedLoanApplication = loanApplicationService.updateApplicationStatus(loanApplicationId, status);
        return ResponseEntity.ok(updatedLoanApplication);
    }
}



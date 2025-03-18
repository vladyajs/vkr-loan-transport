package com.vkr.controllers;

import com.vkr.models.LoanTransport;
import com.vkr.services.LoanTransportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loan-transports")
public class LoanTransportController {

    @Autowired
    private LoanTransportService loanTransportService;

    @PostMapping("/{loanApplicationId}")
    public ResponseEntity<LoanTransport> transportLoanApplication(@PathVariable Long loanApplicationId) {
        LoanTransport loanTransport = loanTransportService.transportLoanApplication(loanApplicationId);
        return ResponseEntity.ok(loanTransport);
    }
}


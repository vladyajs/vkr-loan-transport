package com.vkr.services;

import com.vkr.models.LoanTransport;
import com.vkr.repositories.LoanTransportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LoanTransportService {

    @Autowired
    private LoanTransportRepository loanTransportRepository;

    @Autowired
    private CamundaService camundaService;  // Сервис для взаимодействия с Camunda

    public LoanTransport transportLoanApplication(Long loanApplicationId) {
        LoanTransport loanTransport = new LoanTransport();
        loanTransport.setLoanApplicationId(loanApplicationId);
        loanTransport.setStatus("На обработке");
        loanTransport.setCreatedAt(LocalDateTime.now());

        loanTransportRepository.save(loanTransport);

        // Отправляем заявку на следующий этап
        camundaService.startLoanTransportProcess(loanTransport);

        return loanTransport;
    }
}

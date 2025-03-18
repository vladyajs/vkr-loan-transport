package com.vkr.services;

import com.vkr.models.LoanApplication;
import com.vkr.models.LoanTransport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@Service
public class CamundaService {

    private static final Logger logger = LoggerFactory.getLogger(CamundaService.class);

    private final String CAMUNDA_API_URL = "http://localhost:8080/engine-rest";  // Путь к Camunda API

    // Старт процесса одобрения заявки
    public void startApprovalProcess(LoanApplication loanApplication) {
        // Здесь используется Camunda REST API для запуска процесса
        String url = CAMUNDA_API_URL + "/process-definition/key/loanApproval/start";

        // Создаем данные для отправки в Camunda
        Map<String, Object> variables = new HashMap<>();
        variables.put("loanApplicationId", loanApplication.getLoanApplicationId());
        variables.put("applicantId", loanApplication.getApplicant().getApplicantId());

        // Отправляем запрос в Camunda для старта процесса
        try {
            //restTemplate.postForObject(url, variables, String.class);
            logger.info("Процесс одобрения для заявки с ID {} успешно запущен.", loanApplication.getLoanApplicationId());
        } catch (Exception e) {
            logger.error("Ошибка при запуске процесса одобрения для заявки с ID {}", loanApplication.getLoanApplicationId(), e);
            throw new RuntimeException("Не удалось запустить процесс одобрения в Camunda");
        }
    }

    // Старт процесса обработки заявки
    public void startLoanTransportProcess(LoanTransport loanTransport) {
        // Здесь используется Camunda REST API для старта процесса обработки
        String url = CAMUNDA_API_URL + "/process-definition/key/loanTransport/start";

        // Создаем данные для отправки в Camunda
        Map<String, Object> variables = new HashMap<>();
        variables.put("loanApplicationId", loanTransport.getLoanApplicationId());

        // Отправляем запрос в Camunda для старта процесса
        try {
            //restTemplate.postForObject(url, variables, String.class);
            logger.info("Процесс обработки заявки с ID {} успешно запущен.", loanTransport.getLoanApplicationId());
        } catch (Exception e) {
            logger.error("Ошибка при запуске процесса обработки заявки с ID {}", loanTransport.getLoanApplicationId(), e);
            throw new RuntimeException("Не удалось запустить процесс обработки заявки в Camunda");
        }
    }
}



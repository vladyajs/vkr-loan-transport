package com.vkr.vkr_loan_transport.controllers;

import com.vkr.controllers.LoanApplicationController;
import com.vkr.models.LoanApplication;
import com.vkr.services.LoanApplicationService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(LoanApplicationController.class)
@AutoConfigureMockMvc(addFilters = false) // Отключаем Spring Security в тесте
public class LoanApplicationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoanApplicationService loanApplicationService;

    @Test
    public void testGetLoanApplication() throws Exception {
        LoanApplication application = new LoanApplication();
        application.setLoanApplicationId(1L);
        application.setApplicationStatus("На рассмотрении");

        when(loanApplicationService.getLoanApplication(1L)).thenReturn(application);

        mockMvc.perform(get("/loan-applications/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.loanApplicationId").value(1))
                .andExpect(jsonPath("$.applicationStatus").value("На рассмотрении"));
    }
}

package com.vkr.vkr_loan_transport.services;

import com.vkr.clients.NotificationServiceClient;
import com.vkr.clients.RiskServiceClient;
import com.vkr.clients.ScoringServiceClient;
import com.vkr.models.LoanApplication;
import com.vkr.repositories.LoanApplicationRepository;
import com.vkr.services.AuditLogService;
import com.vkr.services.LoanApplicationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class LoanApplicationServiceTest {

    @Mock
    private LoanApplicationRepository loanApplicationRepository;

    @Mock
    private ScoringServiceClient scoringServiceClient;

    @Mock
    private RiskServiceClient riskServiceClient;

    @Mock
    private NotificationServiceClient notificationServiceClient;

    @Mock
    private AuditLogService auditLogService; // ✅ Добавлено

    @InjectMocks
    private LoanApplicationService loanApplicationService;

    @Test
    public void testSubmitLoanApplication() {
        LoanApplication application = new LoanApplication();
        application.setLoanApplicationId(1L);

        when(loanApplicationRepository.save(any(LoanApplication.class))).thenReturn(application);

        // ✅ Добавляем заглушку для логирования, чтобы избежать NullPointerException
        doNothing().when(auditLogService).logAction(anyString(), any(LoanApplication.class));

        Long result = loanApplicationService.submitLoanApplication(application);

        assertNotNull(result);
        assertEquals(1L, result);
        verify(scoringServiceClient, times(1)).sendForScoring(application);
        verify(riskServiceClient, times(1)).sendForRiskAssessment(application);
        verify(notificationServiceClient, times(1)).sendNotification(application);
        verify(auditLogService, times(1)).logAction(eq("creating request"), eq(application)); // ✅ Проверяем логирование
    }
}

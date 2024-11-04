package com.SE1730.Group3.JobLink.src.data.models.all;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TopUpDTO {
    private BigDecimal amount;
    private String paymentType;
    private String status;
    private LocalDateTime transactionDate;
}

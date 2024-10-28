package com.SE1730.Group3.JobLink.src.data.models.all;

import org.threeten.bp.LocalDate;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionDTO {
    private UUID id;
    private String tid;
    private BigDecimal amount;
    private String paymentType;
    private String status;
    private String bankName;
    private String bankNumber;
    private LocalDate transactionDate;
    private String userReceive;
}

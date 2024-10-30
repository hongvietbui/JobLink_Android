package com.SE1730.Group3.JobLink.src.data.models.all;

import com.squareup.moshi.Json;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionDTO {
    @Json(name = "Id")
    private UUID id;
    @Json(name = "Tid")
    private String tid;
    @Json(name = "Amount")
    private BigDecimal amount;
    @Json(name = "PaymentType")
    private String paymentType;
    @Json(name = "Status")
    private String status;
    @Json(name = "BankName")
    private String bankName;
    @Json(name = "BankNumber")
    private String bankNumber;
    @Json(name = "TransactionDate")
    private LocalDateTime transactionDate;
    @Json(name = "UserReceive")
    private String userReceive;
}

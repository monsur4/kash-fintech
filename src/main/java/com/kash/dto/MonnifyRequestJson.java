package com.kash.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MonnifyRequestJson {
    double amount;
    private String invoiceReference;
    private String description;
    private String currencyCode;
    private String contractCode;
    private String customerEmail;
    private String customerName;
    private String expiryDate;
    private String[] paymentMethods;
    private IncomeSplitConfig[] incomeSplitConfig;
    private String redirectUrl;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class IncomeSplitConfig {
        private String subAccountCode;
        private double feePercentage;
        private double splitAmount;
        private boolean feeBearer;
    }
}
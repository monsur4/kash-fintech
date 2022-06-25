package com.kash.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MonnifyResponseJson {
    private boolean requestSuccessful;
    private String responseMessage;
    private String responseCode;
    private ResponseBody responseBody;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class ResponseBody{
        private double amount;
        private String invoiceReference;
        private String invoiceStatus;
        private String description;
        private String contractCode;
        private String customerEmail;
        private String customerName;
        private String expiryDate;
        private String createdBy;
        private String createdOn;
        private String checkoutUrl;
        private String accountNumber;
        private String accountName;
        private String bankName;
        private String bankCode;
    }
}

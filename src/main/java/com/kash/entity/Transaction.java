package com.kash.entity;

import com.kash.constant.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "Transactions")
public class Transaction {
    @Id
    private String id;
    private String customerId;
    private double balanceBefore;
    private TransactionType transactionType;
    private double transactionAmount;
    private String email;
}

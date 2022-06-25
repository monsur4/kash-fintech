package com.kash.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Users")
public class User {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private double balance;
    private String email;
    private String accountNumber;
}

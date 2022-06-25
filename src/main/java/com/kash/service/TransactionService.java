package com.kash.service;

import com.kash.constant.PaymentMethods;
import com.kash.constant.TransactionType;
import com.kash.dto.MonnifyRequestJson;
import com.kash.dto.MonnifyResponseJson;
import com.kash.dto.TransactionDetailDto;
import com.kash.dto.UserDto;
import com.kash.entity.Transaction;
import com.kash.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${kashpoint.redirect.url}")
    private String redirectUrl;

    @Value("${monnify.base.url}")
    private String baseUrl;

    @Value("${monnify.endpoint.url}")
    private String endpointUrl;

    @Value("${monnify.base64token}")
    private String base64Token;

    public <T> MonnifyResponseJson fundAccount(T transactionDetailDto) {
        // retrieve account information from database using email
        if(!(transactionDetailDto instanceof TransactionDetailDto)) {
            return null;
        }
        TransactionDetailDto transactionDetails  = (TransactionDetailDto) transactionDetailDto;

        UserDto user = userService.getUserByEmail(transactionDetails.getEmail());
        if (user == null) return null;

        // create a monnifyrequest, and send the request
        MonnifyRequestJson requestJson = MonnifyRequestJson.builder()
                .amount(transactionDetails.getAmount())
                .currencyCode("NGN")
                .contractCode("7236777561")
                .customerEmail(transactionDetails.getEmail())
                .customerName("Second Customer")
                .description("Funding account")
                .expiryDate("2022-10-30 12:00:00")
                .invoiceReference("SECOND-INVOICE-REF")
                .paymentMethods(new String[]{PaymentMethods.ACCOUNT_TRANSFER.name()})
                .redirectUrl(redirectUrl)
                //.incomeSplitConfig(null)
                .build();

        // send request and retrieve response
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", base64Token);
        HttpEntity<String> resTokenEntity = new HttpEntity<>(null, headers);
        ParameterizedTypeReference<LinkedHashMap<String, Object>> resMap =
                new ParameterizedTypeReference<LinkedHashMap<String, Object>>() {};
        ResponseEntity<LinkedHashMap<String, Object>> resToken =
                restTemplate.exchange("https://sandbox.monnify.com/api/v1/auth/login",
                        HttpMethod.POST,
                        resTokenEntity,
                        resMap);
        String accessToken = ((Map<String, String>)resToken.getBody().get("responseBody")).get("accessToken");

        HttpHeaders headers2 = new HttpHeaders();
        headers2.setBearerAuth(accessToken);
        HttpEntity<MonnifyRequestJson> httpEntity = new HttpEntity<>(requestJson, headers2);
        ResponseEntity<MonnifyResponseJson> response =
                restTemplate.exchange(baseUrl + endpointUrl, HttpMethod.POST, httpEntity, MonnifyResponseJson.class);

        // continue transaction
        return response.getBody();
    }

    @Transactional
    public void saveTransaction(LinkedHashMap<String, Object> eventData) {
        Object emailObj =((LinkedHashMap<String, Object>)eventData.get("customer")).get("email");
        if(!(emailObj instanceof String)){
            return;
        }
        String email = (String) emailObj;
        double amountPaid = Double.parseDouble(eventData.get("amountPaid").toString());

        Transaction transaction = Transaction.builder()
                .transactionType(TransactionType.CREDIT)
                .email(email)
                .transactionAmount(amountPaid)
                .build();

        transactionRepository.save(transaction);
    }
}

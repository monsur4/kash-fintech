package com.kash.controller;

import com.kash.dto.MonnifyResponseJson;
import com.kash.dto.TransactionDetailDto;
import com.kash.dto.WebhookResponseJson;
import com.kash.service.TransactionService;
import com.kash.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService;

    @PostMapping
    public void fundAccount(@RequestBody TransactionDetailDto transactionDetailDto, HttpServletResponse response){
        MonnifyResponseJson monnifyResponseJson = transactionService.fundAccount(transactionDetailDto);
        String checkoutUrl = monnifyResponseJson.getResponseBody().getCheckoutUrl();
        try {
            response.sendRedirect(checkoutUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(monnifyResponseJson);
    }

    @GetMapping("/complete")
    public String transactionCompleteCallBack(){
        return "Transaction complete";
    }

    @PostMapping("/webhook")
    public void webHook(@RequestBody WebhookResponseJson<LinkedHashMap<String, Object>> responseJson) {
        if(responseJson.getEventType().equals("SUCCESSFUL_TRANSACTION")){
            LinkedHashMap<String, Object> eventData = responseJson.getEventData();

            // save transaction to transaction table
            transactionService.saveTransaction(eventData);
            // update user balance
            userService.updateBalance(eventData);
        }
    }

}

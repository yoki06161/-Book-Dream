package com.bookdream.sbb.pay;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import com.bookdream.sbb.user.UserService;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import com.siot.IamportRestClient.*;

// 아임포트는 REST API - 이걸 사용하면 html페이지를 반환할 수 없으므로, order 페이지는 분리
@RestController
@RequiredArgsConstructor
public class PayController {
	// 결제 검증 서비스
	private IamportClient iamportClient;
	
    @Value("${imp.api.key}")
    private String apiKey;
 
    @Value("${imp.api.secretkey}")
    private String secretKey;
    
    @PostConstruct
    public void init() {
        this.iamportClient = new IamportClient(apiKey, secretKey,true);
    }
    
    @PostMapping("/payment/validation/{imp_uid}")
    public IamportResponse<Payment> validateIamport(@PathVariable("imp_uid") String imp_uid) throws IamportResponseException, IOException {
        IamportResponse<Payment> payment = iamportClient.paymentByImpUid(imp_uid);
        //System.out.println("결제 요청 응답. 결제 내역 - 주문 번호: " + payment.getResponse().getMerchantUid());
        return payment;
    }
}
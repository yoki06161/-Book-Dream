package com.bookdream.sbb.pay;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
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
	// 결제 후 DB에 결제/주문정보 저장
	@Autowired
	private PayService payService;
	private OrdersService ordersService;
	
    @Value("${imp.api.key}")
    private String apiKey;
 
    @Value("${imp.api.secretkey}")
    private String secretKey;
    
    @PostConstruct
    public void init() {
        this.iamportClient = new IamportClient(apiKey, secretKey,true);
    }
    
    @PostMapping("/payment/validation/{imp_uid}")
    public IamportResponse<Payment> validateIamport(@PathVariable("imp_uid") String pay_id) throws IamportResponseException, IOException {
    	IamportResponse<Payment> payment = iamportClient.paymentByImpUid(pay_id);
        System.out.println(pay_id);
//        System.out.println("결제 요청 응답. 결제 내역 - 주문 번호: " + payment.getResponse().getMerchantUid());
//        System.out.println("받는사람 우편번호"+payment.getResponse().getBuyerPostcode());
//        System.out.println("받는사람 전번"+payment.getResponse().getBuyerTel());
//        System.out.println("받는사람 이메일"+payment.getResponse().getBuyerEmail());
//        System.out.println("받는사람이름"+payment.getResponse().getBuyerName());
//        System.out.println("받는사람 주소"+payment.getResponse().getBuyerAddr());
        String name = payment.getResponse().getBuyerName();
        String address = payment.getResponse().getBuyerAddr();
        String phone = payment.getResponse().getBuyerTel();
        //String pw = (String) payData.get(0);
        String post_code = payment.getResponse().getBuyerPostcode();
        String total_price = payment.getResponse().getAmount().toString()+"원";
        //String request = (String) payData.get(1);
        System.out.println(name);
        System.out.println(address);
        System.out.println(phone);
        //System.out.println(pw);
        //System.out.println(request);
        System.out.println(post_code);
        System.out.println(total_price);
        
        // 결제 테이블에 값 저장
     	payService.savePays(pay_id, name,phone,address,post_code,total_price);
     	// 주문 테이블에 값 저장(for문)
     	//ordersService.saveOrder();
        return payment;
    }
    
    @PostMapping("/payment/updatePay")
    public ResponseEntity<String> payAdd(@RequestBody Map<String, Object> payData) {
    	System.out.println("Received data: " + payData);
    	//@RequestBody List<Map<String, Object>> selectItems
        // payData에서 올바른 키를 사용하여 값을 가져옵니다.
        String pay_id = (String) payData.get("imp_uid");
        String pw = (String) payData.get("pw");
        String request = (String) payData.get("options");
        System.out.println(pay_id);
        System.out.println(pw);
        System.out.println(request);
      
        if (pay_id == null || pw == null || request == null) {
        	return ResponseEntity.badRequest().body("Missing required fields");
        }
        
        System.out.println(pay_id);
        System.out.println(pw);
        System.out.println(request);
        try {
            payService.updatePaysById(pay_id, pw, request);
            return ResponseEntity.ok("Payment updated successfully");
        } catch (Exception e) {
            // 예외 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }
}
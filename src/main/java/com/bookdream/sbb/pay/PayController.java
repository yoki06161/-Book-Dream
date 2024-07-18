package com.bookdream.sbb.pay;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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
import com.siot.IamportRestClient.*;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class PayController {
	
//	private final PayService payService;
//	private final OrdersService ordersService;
//	private final RefundService refundService;

	// 결제 검증 서비스
	private IamportClient iamportClient;
	
    @Value("${iamport.api.key}")
    private String apiKey;
 
    @Value("${iamport.api.secret}")
    private String secretKey;
    
    @PostConstruct
    public void init() {
        this.iamportClient = new IamportClient(apiKey, secretKey,true);
    }

	
//	@PostMapping("/pay")
//	public String pay() {
//		return "pay/pay";
//	}
//    @PostMapping("/pay")
//	public ResponseEntity<String> paymentComplete(@RequestBody List<Map<String, Object>> selectedItems, @RequestBody String totalSum) throws IOException {
//		// 현재 인증된 사용자의 이메일 가져오기
//		String email = SecurityContextHolder.getContext().getAuthentication().getName();
//				// 로그인하지 않았다면, 세션에 저장된 장바구니 내역 출력
////				if(email == "anonymousUser") {
////					return "basket/list"; 
////				} else {
////					// 로그인했다면 DB에 저장된 장바구니 내역 불러오기
////					//세션스토리지에 저장된 정보와 테이블에 저장된 정보를 비교하여.. 동일한 상품 id가 있을 경우 기존테이블 정보 유지
////
////					return "basket/list"; 
////				}
//		//
//        String orderNumber = String.valueOf(selectedItems.get(0).getOrderNumber());
//        try {
//        	payService.savePay()
//            ordersService.saveOrder(email, selectedItems);
//            System.out.println("결제 성공 : 결제 번호 " + orderNumber);
//            return ResponseEntity.ok().build();
//        } catch (RuntimeException e) {
//        	// 결제 취소
//        	System.out.println("주문 상품 환불 진행 : 결제 번호 " + orderNumber);
//            //String token = refundService.getToken(apiKey, secretKey);
//            //refundService.refundWithToken(token, orderNumber, e.getMessage());
//            //return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        	return null;
//        }
//    }
	
	@GetMapping("")
	public String order() {
//		// 현재 인증된 사용자의 이메일 가져오기
//		String email = SecurityContextHolder.getContext().getAuthentication().getName();
//		System.out.println(email);
//		
//		// 로그인하지 않았다면 로그인화면으로 이동
//		if(email == "anonymousUser") {
//			view = "pay/loginform";
//		} else {
//			// 로그인했다면 주문 페이지로 이동
//			view = "pay/order";
//		}
		
		return "pay/order";
	}
	
	
	@GetMapping("/pay/success")
	public String paySuccess() {
		return "pay/pay_success";
	}
}
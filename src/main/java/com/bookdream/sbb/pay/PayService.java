package com.bookdream.sbb.pay;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bookdream.sbb.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PayService {
	private final PayRepository payRepository;

	public void savePays(String pay_id, String name, String phone, String address, String post_code,
			String total_price) {
		// Pay 객체 생성 및 필드 설정
		Pay pay = new Pay();
		pay.setPay_id(pay_id);
		pay.setName(name);
		pay.setPhone(phone);
		pay.setAddress(address);
		pay.setPost_code(post_code);	
		pay.setTotal_price(total_price);

		// 데이터베이스에 저장
		payRepository.save(pay);
	}

	public void updatePaysById(String pay_id, String pw, String request) {
		Optional<Pay> optionalPay = payRepository.findById(pay_id);
		if (optionalPay.isPresent()) {
			Pay pay = optionalPay.get();
			pay.setPw(pw);
			pay.setRequest(request);
			payRepository.save(pay); // 업데이트된 엔터티를 저장합니다.
		} else {
			// 예외 처리 또는 적절한 처리
			System.out.println("Pay ID not found: " + pay_id);
		}
	}

	public Object getPaysById(String pay_id) {
		Optional<Pay> optionalPay = payRepository.findById(pay_id);
		
		return optionalPay;
	}
	
	public List<Pay> getPayByEmail(String email) {
	    List<Pay> pays = payRepository.findByEmail(email);
	    System.out.println("Found pays: " + pays);
	    return pays;
	}

}
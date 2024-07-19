package com.bookdream.sbb.pay;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bookdream.sbb.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PayService {
	private final PayRepository payRepository;

	public void savePays(String name, String phone, String pw, String address, String detail_address, String post_code,
			String request, String total_price) {
		 // Pay 객체 생성 및 필드 설정
        Pay pay = new Pay();
        pay.setName(name);
        pay.setPhone(phone);
        pay.setPw(pw);
        pay.setAddress(address);
        pay.setDetail_address(detail_address);
        pay.setPost_code(post_code);
        pay.setRequest(request);
        pay.setTotal_price(total_price);

        // 데이터베이스에 저장
        payRepository.save(pay);
	}
}

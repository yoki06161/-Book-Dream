function init() {
	var payId = document.getElementById('pay_id').textContent;
	
	var payPw = document.getElementById('pay_pw').value;
	var button = document.getElementById(''+payId);
	
	// 주문 결제 취소 버튼 클릭 이벤트 리스너
	button.addEventListener('click', function() {
		var pw = document.getElementById('pw').value;
		
		// 저장된 비밀번호와 입력된 비밀번호가 같을때
		if (payPw == pw) {
			// 인증 토큰 발급 받기
			axios({
				url: "/payment/getToken",
			    method: "post", 
			    // "Content-Type": "application/json"
			    headers: { 
					"Content-Type": "application/json",
					[csrfHeader]: csrfToken
				}, 
			    data: {
			      // REST API키
			      imp_key: "5147107512137784", 
			      // REST API Secret
			      imp_secret: "4wk5KrpOR2u3RCAtBalUmjRpu26EVTYnzOsMkVXyfjdYPrK8rFu19UCY3FZMkw9MjTZeYm9ScMSrkFUL" 
			    }
			})
			.then(response => {
				console.log(response.data);
				//alert('주문/결제 취소 완료');
				
				// 주문/결제 취소 요청
				axios({
				    method: "post",
				    url: `/payment/cancel/${payId}`,
					headers: {
						'Content-Type': 'application/json',
						[csrfHeader]: csrfToken
					},
					data: {
						access_token: response.data.access_token,
						imp_uid: payId
					}
				})
				.then(response => {
					console.log(response.data);
					
					alert('주문/결제 취소 완료');
					location.href='/';
				})
				.catch(error => {
					if (error.response) {
						// 서버 응답이 있을 경우
						console.error('서버 응답 오류:', error.response.data);
					} else if (error.request) {
						// 요청이 전송되지 않았을 경우
						console.error('요청이 전송되지 않음:', error.request);
					} else {
						// 요청 설정 중 오류가 발생했을 경우
						console.error('요청 설정 중 오류 발생:', error.message);
					}
				});	
							
			})
			.catch(error => {
				if (error.response) {
					// 서버 응답이 있을 경우
					console.error('서버 응답 오류:', error.response.data);
				} else if (error.request) {
					// 요청이 전송되지 않았을 경우
					console.error('요청이 전송되지 않음:', error.request);
				} else {
					// 요청 설정 중 오류가 발생했을 경우
					console.error('요청 설정 중 오류 발생:', error.message);
				}
			});			
		} else {
			// 저장된 비밀번호와 입력된 비밀번호가 다를때
			alert('비밀번호가 다릅니다.');
		}
	});
}

document.addEventListener('DOMContentLoaded', init);

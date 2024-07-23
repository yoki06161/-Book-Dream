function init() {
	var payId = document.getElementById('pay_id').textContent;
	
	var payPw = document.getElementById('pay_pw').value;
	console.log(payPw);
	var button = document.getElementById(''+payId);
	
	// 주문 결제 취소 버튼 클릭 이벤트 리스너
	button.addEventListener('click', function() {
		var pw = document.getElementById('pw').value;
		
		// 저장된 비밀번호와 입력된 비밀번호가 같을때
		if (payPw == pw) {
			alert('주문/결제 취소 완료');
		} else {
			// 저장된 비밀번호와 입력된 비밀번호가 다를때
			alert('비밀번호가 다릅니다.');
		}
	});
}

document.addEventListener('DOMContentLoaded', init);

function init() {
	// 세션 스토리지 전체 삭제(저장이 잘못되었을때 주석풀고 사용)
	//sessionStorage.clear();
	
	// sessionStorage에서 저장된 뱃지 숫자를 가져와서 설정
	if (sessionStorage.getItem('badgeCount')) {
		document.getElementById('badge').textContent = sessionStorage.getItem('badgeCount');
	}
}

// DOMContentLoaded 이벤트 리스너를 사용하여 문서가 완전히 로드된 후 실행하는 함수 호출
document.addEventListener('DOMContentLoaded', init);

// 장바구니 추가 함수
function aa() {
	let book_id = parseInt(document.querySelector('.book_id').value);    // int형으로 담기
	let book_img = document.querySelector('img').src;
	let book_title = document.querySelector('.title').textContent;
	let book_price = document.querySelector('.price').textContent;
	 
	let count = parseInt(document.querySelector('.bcount').value);     // int형으로 담기
	let count_price = document.querySelector('.result').textContent + '원';
	//console.log(book_id);

	// 세션 스토리지에서 'book_ids' 배열을 불러오기 (없으면 빈 배열로 초 기화)
	let sessionBookIds = JSON.parse(sessionStorage.getItem('book_ids')) || [];
	
	// 세션 스토리지에 저장하기 위해 객체로 묶어 JSON 문자열로 변환
	let dataToStore = {
		book_id: book_id,
		book_img: book_img,
		book_title: book_title,
		book_price: book_price,
	    count: count,
	    count_price: count_price
	};

	// 세션 스토리지에서 장바구니 내역 배열을 불러오기 (없으면 빈 배열로 초기화)
	let dataArray = JSON.parse(sessionStorage.getItem("dataArray")) || [];
	//let dataArray = [];
	console.log(dataArray);
	
	
	// book_id가 세션 스토리지에 없는 경우 book_id와  장바구니에 상품 정보를 각각 배열에 추가하고 뱃지 숫자 업데이트
	if (!sessionBookIds.includes(book_id)) {
		// book_id를 배열에 추가
	    sessionBookIds.push(book_id);
		// 새 객체를 배열에 추가
		dataArray.push(dataToStore);
		// 배열을 JSON 문자열로 변환하여 세션 스토리지에 저장
	    sessionStorage.setItem('book_ids', JSON.stringify(sessionBookIds)); 
		sessionStorage.setItem("dataArray", JSON.stringify(dataArray));
		console.log(sessionBookIds);
		console.log(dataArray);
		alert('장바구니에 추가되었습니다.');

	    // 뱃지 숫자 변경
	    var badge = parseInt(document.getElementById('badge').textContent);
	    var newBadgeCount = badge + 1;
	    document.getElementById('badge').textContent = newBadgeCount;

	    // 뱃지 숫자를 sessionStorage에 저장
	    sessionStorage.setItem('badgeCount', newBadgeCount);
	} else {
	    alert('이미 장바구니에 있습니다.');
		console.log(sessionBookIds);
		console.log(dataArray);
	}
}
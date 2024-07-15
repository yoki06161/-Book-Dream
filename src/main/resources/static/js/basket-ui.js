function init() {
    // '전체선택' 체크박스를 선택
    const selectAllCheckbox = document.getElementById('select_all');
    // '전체선택' 체크박스를 제외한 모든 체크박스를 선택
    const checkboxes = document.querySelectorAll('input[type="checkbox"]:not(#select_all)');
    // 주문 버튼을 선택
    const orderButton = document.getElementById('order');

    // 주문 버튼의 상태를 업데이트하는 함수
    function updateButtonState() {
        const anyChecked = Array.from(checkboxes).some(checkbox => checkbox.checked);
        orderButton.disabled = !anyChecked;
    }

    // '전체선택' 체크박스의 상태를 업데이트하는 함수
    function updateSelectAllState() {
        const allChecked = Array.from(checkboxes).every(checkbox => checkbox.checked);
        selectAllCheckbox.checked = allChecked;
    }

    // 개별 체크박스의 상태가 변경될 때 호출되는 함수
    function handleCheckboxChange() {
        updateButtonState();
        updateSelectAllState();
    }

    // '전체선택' 체크박스 클릭 시 모든 체크박스의 상태를 업데이트하는 함수
    selectAllCheckbox.addEventListener('change', function () {
        const selectAll = selectAllCheckbox.checked;
        checkboxes.forEach((checkbox) => {
            checkbox.checked = selectAll;
        });
        updateButtonState();
    });

    // 각 체크박스에 변경 이벤트 리스너를 추가
    checkboxes.forEach(checkbox => {
        checkbox.addEventListener('change', handleCheckboxChange);
    });

    // 페이지 로드 시 초기 버튼 및 '전체선택' 체크박스 상태를 업데이트
    updateButtonState();
    updateSelectAllState();

    // 페이지 로드 후 세션 스토리지에 저장된 formData 출력
    displayFormData();
}

// 세션 스토리지에서 폼데이터를 가져와서 콘솔에 출력하는 함수
function displayFormData() {
    // 세션 스토리지에서 장바구니 내역 배열을 불러오기 (없으면 빈 배열로 초기화)
	let dataArray = JSON.parse(sessionStorage.getItem("dataArray")) || [];
    let dataArrayList = document.getElementById('dataArrayList');
	let dataNotFound = document.getElementById('dataNotFound');
	console.log(dataArrayList);
	    
	if (dataArray.length > 0) {
	    dataArray.forEach(function(data, index) {
	        // tr 요소 생성 변수
	        let tr = document.createElement('tr');

	        // select 요소 만들고, 선택된 옵션을 제외한 다른 옵션을 고를 수 있게 하기
	        let selectElement = document.createElement('select');
	        selectElement.className = `count${index}`; // 고유 클래스 이름 추가
	        for (let i = 1; i <= 10; i++) {
	            let option = document.createElement('option');
	            option.value = i;
	            option.textContent = i;
	            if (i == data.count) {
	                option.selected = true; // 선택된 select 값
	            }
	            selectElement.appendChild(option);
	        }

	        // Select 요소 값이 변경될 때마다 값을 업데이트하는 이벤트 리스너 추가
	        selectElement.addEventListener('change', function() {
	            updateTotalPrice(index);
	        });

	        let btnClose = document.createElement('button');
	        btnClose.type = 'button';
	        btnClose.className = 'btn-close';
	        btnClose.setAttribute('aria-label', 'Close');

	        // btnClose 클릭 이벤트 리스너
	        btnClose.addEventListener('click', function() {

	            let deleteNum = document.querySelector(`.row${index} input[type="hidden"]`).value;
				
	            // 삭제할 book_id
	            let bookIdToDelete = data.book_id;

	            // 배열에서 해당 book_id를 가진 요소의 인덱스 찾기
	            let indexToDelete = dataArray.findIndex(item => item.book_id === bookIdToDelete);

				// 배열에서 해당 인덱스의 요소 삭제
				if (indexToDelete !== -1) {
					// 배열에서 요소 삭제
				    dataArray.splice(indexToDelete, 1);
					
				    // sessionStorage에 업데이트된 배열 저장
				    sessionStorage.setItem("dataArray", JSON.stringify(dataArray));

				    // 콘솔 확인
				    console.log('Updated dataArray:', dataArray);
					
					// 장바구니 배열 길이 가져오기
					dataArray = JSON.parse(sessionStorage.getItem("dataArray")) || [];
					let badgeCount = dataArray.length;

					// sessionStorage에 badgeCount 저장
					sessionStorage.setItem('badgeCount', badgeCount);

					// 뱃지 숫자 설정
					document.getElementById('badge').textContent = badgeCount;
				} else {
				    console.log(`Book ID ${bookIdToDelete} not found in dataArray`);
				}
	            // 해당 행을 삭제
	            tr.remove(bookIdToDelete);
	        });

	        tr.innerHTML = `
	            <input type="hidden" value=${data.book_id}>
	            <td><input class="form-check-input" type="checkbox"></td>
	            <td><img src=${data.book_img} alt="상품사진" style="width: 82px; height: 118.34px;"></td>
	            <td class="text-start"><span class="fs-5 fw-bold">${data.book_title}</span>
	            <br><p class="price">${data.book_price}</p></td>
	            <td></td>
	            <td class="result">${data.count_price}</td>
	            <td></td>`;

	        // select 요소 td에 넣기
			tr.children[4].appendChild(selectElement);
			tr.children[4].appendChild(document.createTextNode(' 권')); 

	        // btnClose 요소 td에 넣기
	        let tdClose = document.createElement('td');
	        tr.children[6].appendChild(btnClose);

	        // 각 행에 고유한 클래스를 추가합니다.
	        tr.className = `row${index}`;
	        dataArrayList.appendChild(tr);
	    });

	    // '전체선택' 체크박스 상태와 주문 버튼 상태 업데이트
	    initCheckboxes();
	} else {
	    // div 요소 생성 변수
	    let div = document.createElement('div');
	    div.innerHTML = `<h3 class="text-center">장바구니에 담은 상품이 없습니다</h3>`;
	    dataNotFound.append(div);
	}

	function updateTotalPrice(index) {
	    let count = document.querySelector(`.row${index} select`).value;
	    let price = document.querySelector(`.row${index} .price`).textContent;

	    // ,제거 후 실수로 변환. ,가 찾을 문자. g는 전역 검색을 뜻. 모든 ','을 ''로 바꾸겠단 뜻
	    price = parseFloat(price.replace(/,/g, ''));

	    let result = document.querySelector(`.row${index} .result`);

	    if (count > 0) {
	        let total = count * price;
	        result.textContent = total.toLocaleString() + '원';
	    }
	}
}

function initCheckboxes() {
    // '전체선택' 체크박스를 선택
    const selectAllCheckbox = document.getElementById('select_all');
    // '전체선택' 체크박스를 제외한 모든 체크박스를 선택
    const checkboxes = document.querySelectorAll('input[type="checkbox"]:not(#select_all)');
    // 주문 버튼을 선택
    const orderButton = document.getElementById('order');

    function updateButtonState() {
        const anyChecked = Array.from(checkboxes).some(checkbox => checkbox.checked);
        orderButton.disabled = !anyChecked;
    }

    function updateSelectAllState() {
        const allChecked = Array.from(checkboxes).every(checkbox => checkbox.checked);
        selectAllCheckbox.checked = allChecked;
    }

    function handleCheckboxChange() {
        updateButtonState();
        updateSelectAllState();
    }

    checkboxes.forEach(checkbox => {
        checkbox.addEventListener('change', handleCheckboxChange);
    });

    selectAllCheckbox.addEventListener('change', function () {
        const selectAll = selectAllCheckbox.checked;
        checkboxes.forEach((checkbox) => {
            checkbox.checked = selectAll;
        });
        updateButtonState();
    });

    updateButtonState();
    updateSelectAllState();
}

// DOMContentLoaded 이벤트 리스너를 사용하여 init 함수 호출
document.addEventListener('DOMContentLoaded', init);

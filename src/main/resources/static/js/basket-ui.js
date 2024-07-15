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
	// 장바구니 페이지에서 수량 변경 시 호출
	//updateCountPrice();
}

// 세션 스토리지에서 폼데이터를 가져와서 콘솔에 출력하는 함수
function displayFormData() {
    // 세션 스토리지에서 'book_ids' 배열을 불러오기 (없으면 빈 배열로 초기화)
    let sessionBookIds = JSON.parse(sessionStorage.getItem('book_ids')) || [];
        
    // 세션 스토리지에서 장바구니 내역 배열을 불러오기 (없으면 빈 배열로 초기화)
    let dataArray = JSON.parse(sessionStorage.getItem("dataArray")) || [];
    let dataArrayList = document.getElementById('dataArrayList');
	let dataNotFound = document.getElementById('dataNotFound');
    
    
    if (sessionBookIds.length > 0 && dataArray.length > 0) {
        dataArray.forEach(function(data,index) {
			// tr요소 생성 변수 
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
				let count = this.value;
				console.log('Selected count:', count, 'for element:', selectElement.className);

				// ,제거 후 실수로 변환. ,가 찾을 문자. g는 전역 검색을 뜻. 모든 ','을 ''로 바꾸겠단 뜻
				let price = document.querySelector(`.row${index} .price`).textContent;
				console.log(price);

				// , 제거 후 실수로 변환
				price = parseFloat(price.replace(/,/g, ''));
				console.log(price);
				                        
				let result = document.querySelector(`.row${index} .result`);

				if (count > 1) {
					let total = count * price;
				    result.textContent = total.toLocaleString() + '원';
				} 
			});

            tr.innerHTML = `
				<input type="hidden" value=${data.book_id}>
                <td><input class="form-check-input" type="checkbox"></td>
                <td><img src=${data.book_img} alt="상품사진" style="width: 82px; height: 118.34px;"></td>
                <td class="text-start"><span class="fs-5 fw-bold">${data.book_title}</span>
				<br><p class="price">${data.book_price}</p></td>
				<td></td>
                <td class="result">${data.count_price}</td>
                <td><button type="button" class="btn-close" aria-label="Close"></button></td>`;
            
            // select 요소 td에 넣기
            tr.children[4].appendChild(selectElement);
			tr.children[4].appendChild(document.createTextNode(' 권')); // "권" 텍스트 추가
			
			// 각 행에 고유한 클래스를 추가합니다.
			tr.className = `row${index}`;
            dataArrayList.appendChild(tr);
        });

        // '전체선택' 체크박스 상태와 주문 버튼 상태 업데이트
        initCheckboxes();
    } else {
		// tr요소 생성 변수 
		let div = document.createElement('div');
		div.innerHTML = `<h3 class="text-center">장바구니에 담은 상품이 없습니다</h3>`;
        dataNotFound.append(div);
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

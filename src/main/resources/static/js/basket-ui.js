document.addEventListener('DOMContentLoaded', init);

function init() {
    // 전역 변수 선언
    const selectAllCheckbox = document.getElementById('select_all'); // 전체 선택 체크박스 요소
    const orderButton = document.getElementById('order'); // 주문 버튼 요소

    // 주문 버튼 상태 업데이트 함수
    function updateButtonState() {
        const checkboxes = document.querySelectorAll('input[type="checkbox"]:not(#select_all)'); // 전체 체크박스 제외한 체크박스들
        const anyChecked = Array.from(checkboxes).some(checkbox => checkbox.checked); // 하나 이상 체크된 체크박스가 있는지 확인
        orderButton.disabled = !anyChecked; // 체크된 체크박스가 없으면 주문 버튼 비활성화
    }

    // 전체 선택 체크박스 상태 업데이트 함수
    function updateSelectAllState() {
        const checkboxes = document.querySelectorAll('input[type="checkbox"]:not(#select_all)'); // 전체 체크박스 제외한 체크박스들
        const allChecked = Array.from(checkboxes).every(checkbox => checkbox.checked); // 모든 체크박스가 체크되었는지 확인
        selectAllCheckbox.checked = allChecked; // 전체 선택 체크박스의 상태 업데이트
    }

    // 체크박스 변경 이벤트 핸들러
    function handleCheckboxChange() {
        updateButtonState(); // 주문 버튼 상태 업데이트
        updateSelectAllState(); // 전체 선택 체크박스 상태 업데이트
    }

    // 전체 선택 체크박스 클릭 이벤트 리스너
    selectAllCheckbox.addEventListener('change', function () {
        const checkboxes = document.querySelectorAll('input[type="checkbox"]:not(#select_all)'); // 전체 체크박스 제외한 체크박스들
        const selectAll = selectAllCheckbox.checked; // 전체 선택 체크박스의 상태
        checkboxes.forEach((checkbox) => {
            checkbox.checked = selectAll; // 전체 체크박스의 상태에 따라 각 체크박스의 체크 상태 설정
        });
        updateButtonState(); // 주문 버튼 상태 업데이트
    });

    // 문서의 체크박스 변경 이벤트 리스너
    document.addEventListener('change', function (event) {
        if (event.target.matches('input[type="checkbox"]:not(#select_all)')) {
            handleCheckboxChange(); // 체크박스 변경 시 이벤트 핸들러 호출
        }
    });

    displayFormData(); // 데이터 표시 함수 호출
}

// 데이터 표시 함수
function displayFormData() {
    let dataArray = JSON.parse(sessionStorage.getItem("dataArray")) || []; // 세션 스토리지에서 데이터 배열 가져오기
    let dataArrayList = document.getElementById('dataArrayList'); // 상품 목록 테이블 요소
    let dataNotFound = document.getElementById('dataNotFound'); // 상품이 없을 때 표시할 요소

    // 기존 상품 목록 초기화
    dataArrayList.innerHTML = '';

    if (dataArray.length > 0) {
        // 데이터 배열을 순회하며 각 상품 정보를 테이블에 추가
        dataArray.forEach(function(data, index) {
            let tr = document.createElement('tr'); // 새로운 행 요소 생성

            // 수량 선택 셀 추가
            let selectElement = document.createElement('select');
            selectElement.className = `count${index}`;
            for (let i = 1; i <= 10; i++) {
                let option = document.createElement('option');
                option.value = i;
                option.textContent = i;
                if (i == data.count) {
                    option.selected = true;
                }
                selectElement.appendChild(option);
            }

            // 수량 변경 이벤트 리스너 추가
            selectElement.addEventListener('change', function() {
                updateCountPrice(index); // 수량 변경 시 수량당 가격 업데이트
            });

            // 삭제 버튼 추가
            let btnClose = document.createElement('button');
            btnClose.type = 'button';
            btnClose.className = 'btn-close';
            btnClose.setAttribute('aria-label', 'Close');
            btnClose.setAttribute('data-bs-toggle', 'modal');
            btnClose.setAttribute('data-bs-target', `#deleteModal${index}`);

            // 행 내용 설정
            tr.innerHTML = `
                <input type="hidden" value=${data.book_id}>
                <td><input class="form-check-input" type="checkbox"></td>
                <td><img src=${data.book_img} alt="상품사진" style="width: 82px; height: 118.34px;"></td>
                <td class="text-start"><p class="fs-5 fw-bold">${data.book_title}</p>
				<p class="fs-6" style="color:gray;">${data.book_writer}</p>
                <p class="fs-5 price">${data.book_price}</p></td>
                <td></td>
                <td class="result">${data.count_price}</td>
                <td></td>`;

            // 수량 선택 셀과 단위 추가
            tr.children[4].appendChild(selectElement);
            tr.children[4].appendChild(document.createTextNode(' 권'));

            // 삭제 버튼 셀 추가
            let tdClose = document.createElement('td');
            tdClose.appendChild(btnClose);
            tr.appendChild(tdClose);

            // 행 클래스 설정
            tr.className = `row${index}`;
            dataArrayList.appendChild(tr); // 테이블에 행 추가

			
            // 삭제 모달 추가
            let modal = document.createElement('div');
            modal.className = 'modal fade';
            modal.id = `deleteModal${index}`;
            modal.tabIndex = -1;
            modal.setAttribute('data-bs-backdrop', 'static');
            modal.innerHTML = `
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h1 class="modal-title fs-5">장바구니 상품 삭제</h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            장바구니에 담긴 이 상품을 삭제하시겠어요?
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-primary delete" data-index="${index}">예</button>
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" style="border: 0px;">아니오(삭제 취소)</button>
                        </div>
                    </div>
                </div>
            `;

            document.body.appendChild(modal); // 삭제 모달을 문서에 추가
			
			// 수량당 가격 계산 및 총 가격 합계 업데이트
			let count = parseInt(data.count); // 선택된 수량
			let price = parseFloat(data.book_price.replace(/,/g, '')); // 상품 가격
			let countPrice = count * price; // 수량당 가격 계산
			totalSum += countPrice; // 총 가격 합계 누적

			// 총 가격 합계 엘리먼트 업데이트
			let totalSumElement = document.getElementById('totalSum');
			totalSumElement.textContent = totalSum.toLocaleString(); // 통화 형식으로 총 가격 합계 표시
        });

        initCheckboxes(); // 체크박스 초기화 함수 호출
    } else {
        // 상품이 없을 때 메시지 표시
        let div = document.createElement('div');
        div.innerHTML = `<h3 class="text-center">장바구니에 담은 상품이 없습니다</h3>`;
        dataNotFound.append(div); // 메시지를 표시할 요소에 추가
        // 장바구니가 비어 있을 경우 전체 선택 체크박스 해제
        document.getElementById('select_all').checked = false;
    }
}

// 전역 변수 선언
let totalSum = 0; // 총 가격 합계 변수 선언

// 수량당 가격 업데이트 함수
function updateCountPrice(index) {
    let count = document.querySelector(`.row${index} select`).value; // 선택된 수량
    let price = document.querySelector(`.row${index} .price`).textContent; // 상품 가격
    price = parseFloat(price.replace(/,/g, '')); // 쉼표 제거 후 숫자 변환
    let result = document.querySelector(`.row${index} .result`); // 수량당 가격 요소

    if (count > 0) {
        let countPrice = count * price; // 수량당 가격 계산
        result.textContent = countPrice.toLocaleString() + '원'; // 통화 형식으로 포맷팅하여 출력
        
        totalSum += countPrice; // 총 가격 합계 업데이트
        
        let totalSumElement = document.getElementById('totalSum'); // 총 가격 합계 표시 엘리먼트
        totalSumElement.textContent = totalSum.toLocaleString(); // 통화 형식으로 총 가격 합계 표시
    }
}

// 체크박스 초기화 함수
function initCheckboxes() {
    const selectAllCheckbox = document.getElementById('select_all'); // 전체 선택 체크박스 요소
    const orderButton = document.getElementById('order'); // 주문 버튼 요소

    // 주문 버튼 상태 업데이트 함수
    function updateButtonState() {
        const checkboxes = document.querySelectorAll('input[type="checkbox"]:not(#select_all)'); // 전체 체크박스 제외한 체크박스들
        const anyChecked = Array.from(checkboxes).some(checkbox => checkbox.checked); // 하나 이상 체크된 체크박스가 있는지 확인
        orderButton.disabled = !anyChecked; // 체크된 체크박스가 없으면 주문 버튼 비활성화
    }

    // 전체 선택 체크박스 상태 업데이트 함수
    function updateSelectAllState() {
        const checkboxes = document.querySelectorAll('input[type="checkbox"]:not(#select_all)'); // 전체 체크박스 제외한 체크박스들
        const allChecked = Array.from(checkboxes).every(checkbox => checkbox.checked); // 모든 체크박스가 체크되었는지 확인
        selectAllCheckbox.checked = allChecked; // 전체 선택 체크박스의 상태 업데이트
    }

    // 체크박스 변경 이벤트 핸들러
    function handleCheckboxChange() {
        updateButtonState(); // 주문 버튼 상태 업데이트
        updateSelectAllState(); // 전체 선택 체크박스 상태 업데이트
    }

    const checkboxes = document.querySelectorAll('input[type="checkbox"]:not(#select_all)'); // 전체 체크박스 제외한 체크박스들
    checkboxes.forEach(checkbox => {
        checkbox.addEventListener('change', handleCheckboxChange); // 체크박스 변경 이벤트 리스너 추가
    });

    // 전체 선택 체크박스 클릭 이벤트 리스너
    selectAllCheckbox.addEventListener('change', function () {
        const selectAll = selectAllCheckbox.checked; // 전체 선택 체크박스의 상태
        checkboxes.forEach((checkbox) => {
            checkbox.checked = selectAll; // 전체 체크박스의 상태에 따라 각 체크박스의 체크 상태 설정
        });
        updateButtonState(); // 주문 버튼 상태 업데이트
    });

    updateButtonState(); // 초기 버튼 상태 업데이트
    updateSelectAllState(); // 초기 전체 선택 체크박스 상태 업데이트
}

// 삭제 버튼 클릭 이벤트 리스너
document.addEventListener('click', function(event) {
    if (event.target.classList.contains('delete')) {
        event.stopPropagation(); // 이벤트 전파 방지
        let index = event.target.getAttribute('data-index'); // 삭제할 인덱스 가져오기
        let dataArray = JSON.parse(sessionStorage.getItem("dataArray")) || []; // 세션 스토리지에서 데이터 배열 가져오기
        deleteItem(index, dataArray[index]); // 아이템 삭제 함수 호출
    }
});

// 아이템 삭제 함수
function deleteItem(index, data) {
    let bookIdToDelete = data.book_id; // 삭제할 아이템의 ID
    let dataArray = JSON.parse(sessionStorage.getItem("dataArray")) || []; // 세션 스토리지에서 데이터 배열 가져오기
    let indexToDelete = dataArray.findIndex(item => item.book_id === bookIdToDelete); // 삭제할 아이템의 인덱스 찾기

    if (indexToDelete !== -1) {
        dataArray.splice(indexToDelete, 1); // 데이터 배열에서 아이템 삭제
        sessionStorage.setItem("dataArray", JSON.stringify(dataArray)); // 삭제 후 데이터 배열을 세션 스토리지에 다시 저장
        let badgeCount = dataArray.length; // 장바구니 아이템 수 계산
        sessionStorage.setItem('badgeCount', badgeCount); // 세션 스토리지에 장바구니 아이템 수 저장
        document.getElementById('badge').textContent = badgeCount; // UI에서 장바구니 아이템 수 업데이트

        // 삭제 후 페이지 새로고침
        location.reload();
    }
}

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

    // 각 체크박스에 변경 이벤트 리스너를 추가
    checkboxes.forEach(checkbox => {
        checkbox.addEventListener('change', handleCheckboxChange);
    });

    // '전체선택' 체크박스 클릭 시 모든 체크박스의 상태를 업데이트하는 함수
    selectAllCheckbox.addEventListener('change', function () {
        const selectAll = selectAllCheckbox.checked;
        checkboxes.forEach((checkbox) => {
            checkbox.checked = selectAll;
        });
        updateButtonState();
        updateSelectAllState();
    });

    // 페이지 로드 시 초기 버튼 및 '전체선택' 체크박스 상태를 업데이트
	updateButtonState();
    updateSelectAllState();
	
	// 페이지 로드 후 세션 스토리지에 저장된 formData 출력
	displayFormData();
}

// 세션 스토리지에서 'formData'를 가져와서 콘솔에 출력하는 함수
function displayFormData() {
    let formData = JSON.parse(sessionStorage.getItem('formData'));
    if (formData) {
		console.log(formData);
        console.log('Book ID:', formData.book_id);
        console.log('Count:', formData.count);
        console.log('Count Price:', formData.countPrice);
    } else {
        console.log('No formData found in sessionStorage.');
    }
}


// DOMContentLoaded 이벤트 리스너를 사용하여 init 함수 호출
document.addEventListener('DOMContentLoaded', init);

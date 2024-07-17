//function init() {
  // list 페이지에서 체크된 항목들의 데이터가 담긴 배열 가져오기
//  let selectedItems = JSON.parse(sessionStorage.getItem('selectedItems'));
//  console.log(selectedItems);
  

//  const form = document.getElementById('orderForm');
//  const submitBtn = document.getElementById('pay');
//  const inputs = form.querySelectorAll('input');
//  const inputs = form.querySelectorAll('input[type="text"], input[type="number"], input[type="password"]');
//  const pwError = document.getElementById('pwError');
//  const pwFormatError = document.getElementById('pwFormatError');
//  const customInput = document.getElementById('customInput');

  // 필수 입력 필드 검사
//  const checkInputs = () => {
//   let allFilled = true;
//    inputs.forEach((input) => {
//      if (input.value.trim() === '') {
//     if (input.name !== 'customInput' && input.value.trim() === '') {
//        allFilled = false;
//      }
//    });
//    return allFilled;
//  };

  // 비밀번호 유효성 검사
//  const checkPasswords = () => {
//    const pw = document.getElementById('pw').value;
//    const pw2 = document.getElementById('pw2').value;

    // 비밀번호가 영문+숫자 8자리인지 확인
//   const pwValid = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/.test(pw);

//    if (!pwValid) {
//     pwError.style.display = 'none';
//      pwFormatError.style.display = 'block';
//      return false;
//    } else if (pw !== pw2) {
//      pwError.style.display = 'block';
//      pwFormatError.style.display = 'none';
//      return false;
//    } else {
      // 비밀번호1과 비밀번호2가 일치한지 확인
//      if (pw !== pw2) {
//        pwError.style.display = 'block';
//        pwFormatError.style.display = 'none';
//        return false;
//      } else {
//        pwError.style.display = 'none';
//        pwFormatError.style.display = 'none';
//        return true;
//      }
//      pwError.style.display = 'none';
//      pwFormatError.style.display = 'none';
//      return true;
//    }
//  };

  // 전체 폼 유효성 검사
// const validateForm = () => {
//    const allInputsFilled = checkInputs();
//    const passwordsValid = checkPasswords();
//    console.log("All Inputs Filled: ", allInputsFilled);
//    console.log("Passwords Valid: ", passwordsValid);
//    submitBtn.disabled = !(allInputsFilled && passwordsValid);
//  };

//  inputs.forEach((input) => {
//    input.addEventListener('input', (event) => {
//      validateForm();
//    });
//    input.addEventListener('input', validateForm);
//	// 추가: input[type="number"]에 대한 maxlength 처리
//	if (input.type === 'number' && input.name === 'phone') {
//	  input.addEventListener('input', (event) => {
//	    if (input.value.length > 11) {
//	      input.value = input.value.slice(0, 11);
//	    }
//	  });
//	}
//  });

  // 옵션 변경 이벤트 처리
//  document.getElementById('options').addEventListener('change', () => {
//    const selectBox = document.getElementById('options');
//    if (selectBox.value === 'directInput') {
//      customInput.style.display = 'block';
//    } else {
//      customInput.style.display = 'none';
//    }
//    validateForm();
//  });

  // 초기화 시점에 모든 입력 필드가 비어 있는지 확인
//  validateForm();
  
//  displayFormData(); // 데이터 표시 함수 호출

//  displayFormData();
//}

//function sample6_execDaumPostcode() {
//  new daum.Postcode({
//    oncomplete: function (data) {
//      var addr = ''; // 주소 변수
//      var extraAddr = ''; // 참고항목 변수
//      var addr = ''; 
//      var extraAddr = '';

      // 사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
//      if (data.userSelectedType === 'R') {
//        addr = data.roadAddress;
//      } else {
//        addr = data.jibunAddress;
//     }

      // 사용자가 선택한 주소가 도로명 타입일 때 참고항목을 조합한다.
//      if (data.userSelectedType === 'R') {
//        if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
//          extraAddr += data.bname;
//@@ -102,29 +106,23 @@ function sample6_execDaumPostcode() {
//        extraAddr = '';
//     }

      // 우편번호와 주소 정보(+참고항목)를 해당 필드에 넣는다.
//      document.getElementById('sample6_postcode').value = data.zonecode;
//      document.getElementById('sample6_address').value = addr + extraAddr;
      // 커서를 상세주소 필드로 이동한다.
//      document.getElementById('sample6_detailAddress').focus();
//    },
//  }).open();
//}

// 데이터 표시 함수
//function displayFormData() {
//  let selectedItems = JSON.parse(sessionStorage.getItem('selectedItems'));
//  let dataArrayList = document.getElementById('dataArrayList'); // 상품 목록 테이블 요소
//  let dataArrayList = document.getElementById('dataArrayList');

  // 기존 상품 목록 초기화
//  dataArrayList.innerHTML = '';

//  if (selectedItems.length > 0) {
    // 데이터 배열을 순회하며 각 상품 정보를 테이블에 추가
//  if (selectedItems && selectedItems.length > 0) {
//    selectedItems.forEach(function(data, index) {
//      let tr = document.createElement('tr'); // 새로운 행 요소 생성
//      let tr = document.createElement('tr');

      // 수량 선택 셀 추가
//      let selectElement = document.createElement('select');
//      selectElement.className = `count${index}`;
//      selectElement.name = `count`;
//@@ -137,7 +135,7 @@ function displayFormData() {
//        }
//        selectElement.appendChild(option);
//      }
      // 행 내용 설정

//      tr.innerHTML = `
//        <input type="hidden" value=${data.book_id} name="book_id">
//        <td><img src=${data.book_img} alt="상품사진" style="width: 82px; height: 118.34px;"></td>
//@@ -147,10 +145,9 @@ function displayFormData() {
//        <td>${data.count}권</td>
//        <td class="result" name="count_price">${data.count_price}</td>`;

//      dataArrayList.appendChild(tr); // 테이블에 행 추가
//      dataArrayList.appendChild(tr);
//    });
//  }
//}

// DOMContentLoaded 이벤트 리스너를 사용하여 init 함수 호출
//document.addEventListener('DOMContentLoaded', init);
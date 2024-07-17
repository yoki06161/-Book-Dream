function init() {
  // list 페이지에서 체크된 항목들의 데이터가 담긴 배열 가져오기
  let selectedItems = JSON.parse(sessionStorage.getItem('selectedItems'));
  console.log(selectedItems);
  
  const form = document.getElementById('orderForm');
  const submitBtn = document.getElementById('pay');
  const inputs = form.querySelectorAll('input');
  const pwError = document.getElementById('pwError');
  const pwFormatError = document.getElementById('pwFormatError');
  const customInput = document.getElementById('customInput');

  const checkInputs = () => {
    let allFilled = true;
    inputs.forEach((input) => {
      if (input.value.trim() === '') {
        allFilled = false;
      }
    });
    return allFilled;
  };

  const checkPasswords = () => {
    const pw = document.getElementById('pw').value;
    const pw2 = document.getElementById('pw2').value;

    // 비밀번호가 영문+숫자 8자리인지 확인
    const pwValid = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/.test(pw);

    if (!pwValid) {
      pwError.style.display = 'none';
      pwFormatError.style.display = 'block';
      return false;
    } else {
      // 비밀번호1과 비밀번호2가 일치한지 확인
      if (pw !== pw2) {
        pwError.style.display = 'block';
        pwFormatError.style.display = 'none';
        return false;
      } else {
        pwError.style.display = 'none';
        pwFormatError.style.display = 'none';
        return true;
      }
    }
  };

  const validateForm = () => {
    const allInputsFilled = checkInputs();
    const passwordsValid = checkPasswords();
    submitBtn.disabled = !(allInputsFilled && passwordsValid);
  };

  inputs.forEach((input) => {
    input.addEventListener('input', (event) => {
      validateForm();
    });
  });

  // 옵션 변경 이벤트 처리
  document.getElementById('options').addEventListener('change', () => {
    const selectBox = document.getElementById('options');
    if (selectBox.value === 'directInput') {
      customInput.style.display = 'block';
    } else {
      customInput.style.display = 'none';
    }
  });

  // 초기화 시점에 모든 입력 필드가 비어 있는지 확인
  validateForm();
  
  displayFormData(); // 데이터 표시 함수 호출
}

function sample6_execDaumPostcode() {
  new daum.Postcode({
    oncomplete: function (data) {
      var addr = ''; // 주소 변수
      var extraAddr = ''; // 참고항목 변수

      // 사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
      if (data.userSelectedType === 'R') {
        addr = data.roadAddress;
      } else {
        addr = data.jibunAddress;
      }

      // 사용자가 선택한 주소가 도로명 타입일 때 참고항목을 조합한다.
      if (data.userSelectedType === 'R') {
        if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
          extraAddr += data.bname;
        }
        if (data.buildingName !== '' && data.apartment === 'Y') {
          extraAddr +=
            extraAddr !== '' ? ', ' + data.buildingName : data.buildingName;
        }
        if (extraAddr !== '') {
          extraAddr = ' (' + extraAddr + ')';
        }
      } else {
        extraAddr = '';
      }

      // 우편번호와 주소 정보(+참고항목)를 해당 필드에 넣는다.
      document.getElementById('sample6_postcode').value = data.zonecode;
      document.getElementById('sample6_address').value = addr + extraAddr;
      // 커서를 상세주소 필드로 이동한다.
      document.getElementById('sample6_detailAddress').focus();
    },
  }).open();
}

// 데이터 표시 함수
function displayFormData() {
  let selectedItems = JSON.parse(sessionStorage.getItem('selectedItems'));
  let dataArrayList = document.getElementById('dataArrayList'); // 상품 목록 테이블 요소

  // 기존 상품 목록 초기화
  dataArrayList.innerHTML = '';

  if (selectedItems.length > 0) {
    // 데이터 배열을 순회하며 각 상품 정보를 테이블에 추가
    selectedItems.forEach(function(data, index) {
      let tr = document.createElement('tr'); // 새로운 행 요소 생성

      // 수량 선택 셀 추가
      let selectElement = document.createElement('select');
      selectElement.className = `count${index}`;
      selectElement.name = `count`;
      for (let i = 1; i <= 10; i++) {
        let option = document.createElement('option');
        option.value = i;
        option.textContent = i;
        if (i == data.count) {
          option.selected = true;
        }
        selectElement.appendChild(option);
      }
      // 행 내용 설정
      tr.innerHTML = `
        <input type="hidden" value=${data.book_id} name="book_id">
        <td><img src=${data.book_img} alt="상품사진" style="width: 82px; height: 118.34px;"></td>
        <td class="text-start"><p class="fs-5 fw-bold">${data.book_title}</p>
          <p class="fs-6" style="color:gray;">${data.book_writer}</p>
        <p class="fs-5 price">${data.book_price}</p></td>
        <td>${data.count}권</td>
        <td class="result" name="count_price">${data.count_price}</td>`;

      dataArrayList.appendChild(tr); // 테이블에 행 추가
    });
  }
}

// DOMContentLoaded 이벤트 리스너를 사용하여 init 함수 호출
document.addEventListener('DOMContentLoaded', init);

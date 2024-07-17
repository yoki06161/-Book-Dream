function init() {
  // list 페이지에서 체크된 항목들의 데이터가 담긴 배열 가져오기
  let selectedItems = JSON.parse(sessionStorage.getItem('selectedItems'));
  console.log(selectedItems);

  const form = document.getElementById('orderForm');
  const submitBtn = document.getElementById('pay');
  const inputs = form.querySelectorAll('input[type="text"], input[type="number"], input[type="password"]');
  const pwError = document.getElementById('pwError');
  const pwFormatError = document.getElementById('pwFormatError');
  const customInput = document.getElementById('customInput');

  // 필수 입력 필드 검사
  const checkInputs = () => {
    let allFilled = true;
    inputs.forEach((input) => {
      if (input.name !== 'customInput' && input.value.trim() === '') {
        allFilled = false;
      }
    });
    return allFilled;
  };

  // 비밀번호 유효성 검사
  const checkPasswords = () => {
    const pw = document.getElementById('pw').value;
    const pw2 = document.getElementById('pw2').value;

    const pwValid = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/.test(pw);

    if (!pwValid) {
      pwError.style.display = 'none';
      pwFormatError.style.display = 'block';
      return false;
    } else if (pw !== pw2) {
      pwError.style.display = 'block';
      pwFormatError.style.display = 'none';
      return false;
    } else {
      pwError.style.display = 'none';
      pwFormatError.style.display = 'none';
      return true;
    }
  };

  // 전체 폼 유효성 검사
  const validateForm = () => {
    const allInputsFilled = checkInputs();
    const passwordsValid = checkPasswords();
    console.log("All Inputs Filled: ", allInputsFilled);
    console.log("Passwords Valid: ", passwordsValid);
    submitBtn.disabled = !(allInputsFilled && passwordsValid);
  };

  inputs.forEach((input) => {
    input.addEventListener('input', validateForm);
	// 추가: input[type="number"]에 대한 maxlength 처리
	if (input.type === 'number' && input.name === 'phone') {
	  input.addEventListener('input', (event) => {
	    if (input.value.length > 11) {
	      input.value = input.value.slice(0, 11);
	    }
	  });
	}
  });

  document.getElementById('options').addEventListener('change', () => {
    const selectBox = document.getElementById('options');
    if (selectBox.value === 'directInput') {
      customInput.style.display = 'block';
    } else {
      customInput.style.display = 'none';
    }
    validateForm();
  });

  validateForm();

  displayFormData();
}

function sample6_execDaumPostcode() {
  new daum.Postcode({
    oncomplete: function (data) {
      var addr = ''; 
      var extraAddr = '';

      if (data.userSelectedType === 'R') {
        addr = data.roadAddress;
      } else {
        addr = data.jibunAddress;
      }

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

      document.getElementById('sample6_postcode').value = data.zonecode;
      document.getElementById('sample6_address').value = addr + extraAddr;
      document.getElementById('sample6_detailAddress').focus();
    },
  }).open();
}

function displayFormData() {
  let selectedItems = JSON.parse(sessionStorage.getItem('selectedItems'));
  let dataArrayList = document.getElementById('dataArrayList');

  dataArrayList.innerHTML = '';

  if (selectedItems && selectedItems.length > 0) {
    selectedItems.forEach(function(data, index) {
      let tr = document.createElement('tr');

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

      tr.innerHTML = `
        <input type="hidden" value=${data.book_id} name="book_id">
        <td><img src=${data.book_img} alt="상품사진" style="width: 82px; height: 118.34px;"></td>
        <td class="text-start"><p class="fs-5 fw-bold">${data.book_title}</p>
          <p class="fs-6" style="color:gray;">${data.book_writer}</p>
        <p class="fs-5 price">${data.book_price}</p></td>
        <td>${data.count}권</td>
        <td class="result" name="count_price">${data.count_price}</td>`;

      dataArrayList.appendChild(tr);
    });
  }
}

document.addEventListener('DOMContentLoaded', init);

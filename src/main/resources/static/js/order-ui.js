function init() {
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
    submitBtn.disabled = !allFilled;
  };

  const checkPasswords = () => {
    const pw = document.getElementById('pw').value;
    const pw2 = document.getElementById('pw2').value;

    // 비밀번호가 영문+숫자 8자리인지 확인
    const pwValid = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/.test(pw);

    if (!pwValid) {
      pwError.style.display = 'none';
      pwFormatError.style.display = 'block';
      submitBtn.disabled = true;
    } else {
      // 비밀번호1과 비밀번호2가 일치한지 확인
      if (pw !== pw2) {
        pwError.style.display = 'block';
        pwFormatError.style.display = 'none';
        submitBtn.disabled = true;
      } else {
        pwError.style.display = 'none';
        pwFormatError.style.display = 'none';
      }
    }
  };

  inputs.forEach((input) => {
    input.addEventListener('input', (event) => {
      if (event.target.id === 'pw' || event.target.id === 'pw2') {
        checkPasswords();
      }
      checkInputs();
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
  checkInputs();
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

// DOMContentLoaded 이벤트 리스너를 사용하여 init 함수 호출
document.addEventListener('DOMContentLoaded', init);
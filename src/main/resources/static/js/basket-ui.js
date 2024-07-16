document.addEventListener('DOMContentLoaded', init);

function init() {
    const selectAllCheckbox = document.getElementById('select_all');
    const orderButton = document.getElementById('order');

    function updateButtonState() {
        const checkboxes = document.querySelectorAll('input[type="checkbox"]:not(#select_all)');
        const anyChecked = Array.from(checkboxes).some(checkbox => checkbox.checked);
        orderButton.disabled = !anyChecked;
    }

    function updateSelectAllState() {
        const checkboxes = document.querySelectorAll('input[type="checkbox"]:not(#select_all)');
        const allChecked = Array.from(checkboxes).every(checkbox => checkbox.checked);
        selectAllCheckbox.checked = allChecked;
    }

    function handleCheckboxChange() {
        updateButtonState();
        updateSelectAllState();
    }

    selectAllCheckbox.addEventListener('change', function () {
        const checkboxes = document.querySelectorAll('input[type="checkbox"]:not(#select_all)');
        const selectAll = selectAllCheckbox.checked;
        checkboxes.forEach((checkbox) => {
            checkbox.checked = selectAll;
        });
        updateButtonState();
    });

    document.addEventListener('change', function (event) {
        if (event.target.matches('input[type="checkbox"]:not(#select_all)')) {
            handleCheckboxChange();
        }
    });

    displayFormData();
}

function displayFormData() {
    let dataArray = JSON.parse(sessionStorage.getItem("dataArray")) || [];
    let dataArrayList = document.getElementById('dataArrayList');
    let dataNotFound = document.getElementById('dataNotFound');

    // 기존 목록 초기화
    dataArrayList.innerHTML = '';

    if (dataArray.length > 0) {
        dataArray.forEach(function(data, index) {
            let tr = document.createElement('tr');

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

            selectElement.addEventListener('change', function() {
                updateTotalPrice(index);
            });

            let btnClose = document.createElement('button');
            btnClose.type = 'button';
            btnClose.className = 'btn-close';
            btnClose.setAttribute('aria-label', 'Close');
            btnClose.setAttribute('data-bs-toggle', 'modal');
            btnClose.setAttribute('data-bs-target', `#deleteModal${index}`);

            tr.innerHTML = `
                <input type="hidden" value=${data.book_id}>
                <td><input class="form-check-input" type="checkbox"></td>
                <td><img src=${data.book_img} alt="상품사진" style="width: 82px; height: 118.34px;"></td>
                <td class="text-start"><span class="fs-5 fw-bold">${data.book_title}</span>
                <br><p class="price">${data.book_price}</p></td>
                <td></td>
                <td class="result">${data.count_price}</td>
                <td></td>`;

            tr.children[4].appendChild(selectElement);
            tr.children[4].appendChild(document.createTextNode(' 권'));

            let tdClose = document.createElement('td');
            tdClose.appendChild(btnClose);
            tr.appendChild(tdClose);

            tr.className = `row${index}`;
            dataArrayList.appendChild(tr);

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

            document.body.appendChild(modal);
        });

        initCheckboxes(); // 체크박스 초기화
    } else {
        let div = document.createElement('div');
        div.innerHTML = `<h3 class="text-center">장바구니에 담은 상품이 없습니다</h3>`;
        dataNotFound.append(div);
        // 장바구니가 비어 있을 경우 전체선택 체크박스를 해제
        document.getElementById('select_all').checked = false;
    }
}

function updateTotalPrice(index) {
    let count = document.querySelector(`.row${index} select`).value;
    let price = document.querySelector(`.row${index} .price`).textContent;
    price = parseFloat(price.replace(/,/g, ''));
    let result = document.querySelector(`.row${index} .result`);

    if (count > 0) {
        let total = count * price;
        result.textContent = total.toLocaleString() + '원';
    }
}

function initCheckboxes() {
    const selectAllCheckbox = document.getElementById('select_all');
    const orderButton = document.getElementById('order');

    function updateButtonState() {
        const checkboxes = document.querySelectorAll('input[type="checkbox"]:not(#select_all)');
        const anyChecked = Array.from(checkboxes).some(checkbox => checkbox.checked);
        orderButton.disabled = !anyChecked;
    }

    function updateSelectAllState() {
        const checkboxes = document.querySelectorAll('input[type="checkbox"]:not(#select_all)');
        const allChecked = Array.from(checkboxes).every(checkbox => checkbox.checked);
        selectAllCheckbox.checked = allChecked;
    }

    function handleCheckboxChange() {
        updateButtonState();
        updateSelectAllState();
    }

    const checkboxes = document.querySelectorAll('input[type="checkbox"]:not(#select_all)');
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

document.addEventListener('click', function(event) {
    if (event.target.classList.contains('delete')) {
        event.stopPropagation(); // 이벤트 전파 방지
        let index = event.target.getAttribute('data-index');
        let tr = document.querySelector(`.row${index}`);
        let dataArray = JSON.parse(sessionStorage.getItem("dataArray")) || [];
        deleteItem(index, dataArray[index], tr, dataArray);
        let modal = document.querySelector(`#deleteModal${index}`);
        let bootstrapModal = bootstrap.Modal.getInstance(modal);
        bootstrapModal.hide();
    }
});

function deleteItem(index, data, tr, dataArray) {
    let bookIdToDelete = data.book_id;
    let indexToDelete = dataArray.findIndex(item => item.book_id === bookIdToDelete);

    if (indexToDelete !== -1) {
        dataArray.splice(indexToDelete, 1);
        sessionStorage.setItem("dataArray", JSON.stringify(dataArray));
        let badgeCount = dataArray.length;
        sessionStorage.setItem('badgeCount', badgeCount);
        document.getElementById('badge').textContent = badgeCount;
        tr.remove();

        let dataNotFound = document.getElementById('dataNotFound');
        if (dataArray.length === 0) {
            let div = document.createElement('div');
            div.innerHTML = `<h3 class="text-center">장바구니에 담은 상품이 없습니다</h3>`;
            dataNotFound.append(div);
            // 장바구니가 비어 있을 경우 전체선택 체크박스를 해제
            document.getElementById('select_all').checked = false;
        }
    }
}

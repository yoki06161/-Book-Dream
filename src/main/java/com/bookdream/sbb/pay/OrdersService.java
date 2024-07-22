function init() {
	//페이지에 넘어오자마자 입력했던 데이터 DB에 등록하도록 통신

	// 주문 완료 데이터 출력
	displayFormData();	
}

function displayFormData() {
    // list 페이지에서 체크된 항목들의 데이터가 담긴 배열+총가격 가져오기
    let selectedItems = JSON.parse(sessionStorage.getItem('selectedItems')) || [];
    let totalSum = parseFloat(sessionStorage.getItem('totalSum')) || 0;
	let dataArrayList = document.getElementById('dataArrayList');

	let totalSumElement = document.getElementById('totalSum');
	if (totalSumElement) {
	    totalSumElement.textContent = totalSum.toLocaleString() + '원';
	}

	dataArrayList.innerHTML = '';

	if (selectedItems.length > 0) {
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
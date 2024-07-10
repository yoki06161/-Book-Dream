// DOMContentLoaded 이벤트 리스너를 사용하여 문서가 완전히 로드된 후 실행
document.addEventListener('DOMContentLoaded', function() {

    // sessionStorage에서 저장된 뱃지 숫자를 가져와서 설정
    if (sessionStorage.getItem('badgeCount')) {
        document.getElementById('badge').textContent = sessionStorage.getItem('badgeCount');
    }

    // 모든 basket 클래스를 가진 요소를 선택하고 각각에 대해 클릭 이벤트 리스너 추가
    document.querySelectorAll('.basket').forEach(function(button, index) {
        button.addEventListener('click', function() {
            // 클릭된 버튼을 변수에 저장
            const button = this;

            // 버튼의 고유한 클릭 상태를 저장하기 위한 키 생성
            const buttonKey = 'buttonClicked_' + index;

            // 버튼이 이미 클릭된 상태인지 확인
            if (sessionStorage.getItem(buttonKey)) {
                return; // 이미 클릭된 버튼이면 숫자 증가를 막음
            }

            // 뱃지 숫자 변경
            var badge = parseInt(document.getElementById('badge').textContent);
            var newBadgeCount = badge + 1;
            document.getElementById('badge').textContent = newBadgeCount;

            // 뱃지 숫자를 sessionStorage에 저장
            sessionStorage.setItem('badgeCount', newBadgeCount);

            // 버튼 클릭 상태를 sessionStorage에 저장
            sessionStorage.setItem(buttonKey, 'clicked');
        });
    });

});

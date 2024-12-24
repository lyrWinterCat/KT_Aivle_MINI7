document.addEventListener("DOMContentLoaded", () => {
    //------------ 현재위치 기반 위도경도 입력 기능 -----------
    const getLocationBtn = document.getElementById("getLocation");

    getLocationBtn.addEventListener("click", function() {
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(function(position) {
                const latitude = position.coords.latitude;
                const longitude = position.coords.longitude;

                document.getElementById("lat").value = latitude;
                document.getElementById("lon").value = longitude;
                alert("현재 위치가 설정되었습니다: 위도 " + latitude + ", 경도 " + longitude);
            }, function(error) {
                alert("위치 정보를 가져오는 데 실패했습니다: " + error.message);
            });
        } else {
            alert("이 브라우저는 위치 정보를 지원하지 않습니다.");
        }
    });

    //-------------     음성인식to텍스트 기능(https만 가능)      ------------
    // 녹음버튼객체선택
    const RecBtn = document.querySelector("#Rec");
    const resultText = document.querySelector("#text");
    RecBtn.addEventListener("click", (event) => {
        record();
    });

    // 음성인식 객체
    const SpeechRecognition = window.SpeechRecognition || window.webkitSpeechRecognition;
    const recognition = new SpeechRecognition();

    recognition.lang = 'ko-KR';
    recognition.interimResults = false; //중간결과반영여부
    recognition.maxAlternatives = 1; //음성인식 결과

    function record() {
        // 마이크 접근 가능 여부 확인
        if (!navigator.mediaDevices || !navigator.mediaDevices.getUserMedia) {
            alert("마이크를 사용할 수 없습니다. 현재 증상을 입력해주세요.");
            resultText.focus(); // 텍스트 영역으로 포커스 이동
            return;
        }

        recognition.start(); // 녹음 시작
        RecBtn.classList.toggle("recording");
        console.log("Voice Record Start");
    }

    // 음성인식 결과처리(텍스트변환)
    recognition.onresult = (event) => {
        const transcript = event.results[0][0].transcript;
        console.log('인식된 텍스트:', transcript);
        resultText.value = transcript;
    };

    // 음성 인식 오류 처리
    recognition.onerror = (event) => {
        console.error('음성 인식 오류:', event.error);
        alert("마이크를 사용할 수 없습니다. 현재 증상을 입력해주세요."); // 오류 알림
        resultText.focus(); // 텍스트 영역으로 포커스 이동
//        RecBtn.classList.toggle("recording");
    };

    // 음성 인식 종료 후 처리
    recognition.onend = () => {
        console.log('음성 인식 종료');
        RecBtn.classList.toggle("recording");
    };
});

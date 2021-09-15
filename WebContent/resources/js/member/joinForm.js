(()=>{
	
	let confirmId = '';
	document.querySelector('#btnIdCheck').addEventListener('click', ()=>{

		let userId = document.querySelector('#userId').value;
		   
		if(!userId){
			document.querySelector("#idCheck").innerHTML = "아이디를 입력하지 않았습니다.";
			return;
		}

		fetch("/member/id-check?userId=" + userId)
		.then(response => {
			if(response.ok){
				return response.text()
			} else {
				throw new Error(response.status);
			}
		})
		.then(text=>{
			if(text == 'available'){
				confirmId = userId;
				document.querySelector("#idCheck").innerHTML = "사용 가능한 아이디 입니다.";
			} else {
				document.querySelector("#idCheck").innerHTML = "사용 불가능한 아이디 입니다.";
			}
		})
		.catch(error=>{
			document.querySelector("#idCheck").innerHTML = "응답에 실패했습니다. 상태코드 : " + error;
		})
	});
	   
	document.querySelector("#frm_join").addEventListener('submit', e =>{

		/*let password = document.querySelector('#password').value;
		let tell = document.querySelector('#tell').value;

		let pwReg = /(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[^a-zA-Zㄱ-힣0-9])(?=.{8,})/;
		let tellReg = /^\d{9,11}$/;*/
		
		let userId = document.querySelector('#userId').value;
		
		if(confirmId != userId){
			e.preventDefault();
			document.querySelector('#idCheck').innerHTML = "아이디 중복 검사를 하지 않았습니다.";
			document.querySelector('#userId').focus();
		}

		/*if(!pwReg.test(password)){
			e.preventDefault();
			document.querySelector('#pwCheck').innerHTML = "비밀번호는 숫자, 영문자, 특수문자 조합의 8글자 이상인 문자열 입니다.";
		}

		if(!tellReg.test(tell)){
			e.preventDefault();
			document.querySelector('#tellCheck').innerHTML = "전화번호는 9~11자리 숫자 입니다.";
		}*/
	})
		
})();
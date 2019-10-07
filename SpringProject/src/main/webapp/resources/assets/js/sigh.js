$(document).ready(function(){
	
	$("#join").click(function(){
		
		var ps = $("#inputPassword").val();
		var cps = $("#inputPasswordCheck").val();
		var name = $("#inputName").val();
		var id = $("#inputId").val();
		var ph = $("#inputMobile").val();
		
		var query = {id:id,ps:ps,name:name,ph:ph};
		console.log(query);
		if(name == "" || ps == "" || id == "" || ph == "" ){
			
			alert("입력하지 않은 값이 있습니다. 확인후 다시 진행해주세요.");
			
		}else if(ps != cps){
			
			alert("비밀번호 확인을 해주세요.");
		}else if(name.length < 3){
			
			alert("이름은 최소 3글자 이상으로 기입하여 주세요.");
			
		}else{
			$.ajax({
				
				url:"/signupDB",
				type:"POST",
				data:query,
				success : function(data){
					
					if(data == "1"){
						alert("회원가입 완료! 로그인페이지로 이동합니다");
						window.location.href = "/"
					}else{
						alert("회원가입 실패! 다시 시도해주세요");
					}
					
				}
			
				
			});
			
		}
		
		
		
		
		
	});
	
	
});
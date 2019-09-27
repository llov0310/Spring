
$(document).ready(function(){

	$("#btn-login").click(function(){
		var user_id = $("#user_id").val();
		var password = $("#password").val();
	
	
		var query = {user_id : user_id,
				password : password}
		
		console.log(query);
		
		$.ajax({
			type:'POST',
			url:'/loginCheck',
			data:query,
			success : function(data){
				
				if(data == "3"){
					alert("아이디가 틀렸습니다. 다시입력해주세요");
					$("#user_id").focus();
				
				}else if(data == "2"){
					
					alert("패스워드가 틀렸습니다. 다시 입력해 주세요");
					$("#password").focus();
					
				}else if(data == "1"){
					
					alert("로그인 성공!");
					window.location.href = "/mainpost";
				}else{
					
					alert("존재하지 않는 아이디 입니다.");
				}
				
			}
		
		});
		
		
	}); // click function END
	
}); // document.ready END

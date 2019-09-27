//첫 페이지 진입시 게시글 리스트 불러오기
//	$.ajax({
//		
//		type:"POST",
//		url : "/getPost",
//		dataType : "json",
//		success : function(data){
//			
//			for(var i=0; i<data.length; i++){
//			$(".list").append(
//					"<tr class='postnum'>"+
//					"<td>"+data[i].page+"</td>"+
//					"<td style='display:none'>"+data[i].post_cd+"</td>"+
//					"<td>"+data[i].post_title+"</td>"+
//					"<td>"+data[i].user_id+"</td>"+
//					"<td>"+data[i].post_dt_char+"</td>"+
//					"<td>"+data[i].post_cont+"</td>"+
//					"</tr>"
//					
//			);
//			}
//			
//		}
//			
//		
//		
//	});
	


$(document).ready(function(){
	
	
	
	
	//로그아웃버튼구현
	$(".logout").click(function(){
		
		location.href = "/logout";
		
	});
	
	//글쓰기
	$("#add").click(function(){
		
		location.href = "/addPost";
		
	});
	
	//상세보기게시글
	$(document).on('click','.postnum',function(){
		
		var postnum = $(this).find('td:eq(1)').text();
		
		console.log(postnum);
		
		 window.location.href = "/detailpost/"+postnum;
		
		
	});
		
	
	
	
});
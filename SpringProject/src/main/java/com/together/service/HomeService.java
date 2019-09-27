package com.together.service;

import java.util.ArrayList;

import com.together.domain.AttachVO;
import com.together.domain.CommentVO;
import com.together.domain.MemberVO;
import com.together.domain.Paging;
import com.together.domain.PostVO;

public interface HomeService {

	//로그인
	ArrayList<MemberVO> login(MemberVO mVO);

	//포스트 불러오기
	ArrayList<PostVO> getPost(Paging pig);

	
	//포스트 작성
	int addwritepost(PostVO postvo);

	//포스트 상세보기
	ArrayList<PostVO> getPostview(String num);

//	//댓글조회
	ArrayList<CommentVO> getCommentview(String postnum);

	//댓글작성
	int addComment(CommentVO cVO);

	//파일업로드
	int DBupload(AttachVO aVO);

	//새로등록된 포스트의 코드 알아내기
	ArrayList<PostVO> PostCode(PostVO postvo);

	//업로드된 파일의 게시글 번호 확정부여
	int updateFile(AttachVO attachvo);

	//해당 게시글 첨부파일 가져오기;
	ArrayList<AttachVO> getAttachview(String postnum);

	//포스트 조회수상승
	int postCount(String postnum);


	
	
}
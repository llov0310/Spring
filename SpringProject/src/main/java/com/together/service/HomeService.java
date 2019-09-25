package com.together.service;

import java.util.ArrayList;

import com.together.domain.CommentVO;
import com.together.domain.MemberVO;
import com.together.domain.PostVO;

public interface HomeService {

	//로그인
	ArrayList<MemberVO> login(MemberVO mVO);

	//포스트 불러오기
	ArrayList<PostVO> getPost();

	
	//포스트 작성
	int addwritepost(PostVO postvo);

	//포스트 상세보기
	ArrayList<PostVO> getPostview(String num);

	//댓글조회
	ArrayList<CommentVO> getCommentview(String postnum);

	
	
}
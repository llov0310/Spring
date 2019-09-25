package com.together.mapper;

import java.util.ArrayList;

import com.together.domain.CommentVO;
import com.together.domain.MemberVO;
import com.together.domain.PostVO;

public interface HomeMapper {

	ArrayList<MemberVO> login(MemberVO mVO);
	//로그인을 위한 함수 추가

	ArrayList<PostVO> post();
	//게시글을 전체 불러오기위한 함수 추가

	int writepost(PostVO postvo);
	//포스트 작성을위한 함수

	ArrayList<PostVO> getPostview(String num);
	//포스트 상세보기를위한 함수

	ArrayList<CommentVO> getCommentview(String postnum);
	//포스트에있는 댓글 출력
	
}
package com.together.mapper;

import java.util.ArrayList;

import com.together.domain.AttachVO;
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

	ArrayList<CommentVO> getcommentview(String postnum);
	//포스트에있는 댓글 출력
	
	ArrayList<AttachVO> getattachview(String postnum);
	//포스트에있는 업로드파일 출력

	int addcomment(CommentVO cVO);
	//댓글작성

	int DBupload(AttachVO aVO);
	//게시글 작성시 파일업로드

	ArrayList<PostVO> PostCode(PostVO postvo);
	//포스트 코드를 알아내기위한 함수

	int updatefile(AttachVO attachvo);
	//업로드된 파일의 포스트번호 부여

}
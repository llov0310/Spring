package com.together.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.together.domain.AttachVO;
import com.together.domain.CommentVO;
import com.together.domain.MemberVO;
import com.together.domain.Paging;
import com.together.domain.PostVO;
import com.together.mapper.HomeMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class HomeImp implements HomeService {
	
	private HomeMapper mapper;

	@Override
	public ArrayList<MemberVO> login(MemberVO mVO) {
		
		return mapper.login(mVO);
	}

	@Override
	public ArrayList<PostVO> getPost(Paging pig) {
		// TODO Auto-generated method stub
		return mapper.post(pig);
	}

	@Override
	public int addwritepost(PostVO postvo) {
		// TODO Auto-generated method stub
		return mapper.writepost(postvo);
	}

	@Override
	public ArrayList<PostVO> getPostview(String num) {
		// TODO Auto-generated method stub
		return mapper.getPostview(num);
	}

	@Override
	public ArrayList<CommentVO> getCommentview(String postnum) {
		// TODO Auto-generated method stub
		return mapper.getcommentview(postnum);
	}

	@Override
	public int addComment(CommentVO cVO) {
		System.out.println("임플리먼트");
		System.out.println(cVO);
		return mapper.addcomment(cVO);
	}

	@Override
	public int DBupload(AttachVO aVO) {
		// TODO Auto-generated method stub
		return mapper.DBupload(aVO);
	}

	@Override
	public ArrayList<PostVO> PostCode(PostVO postvo) {
		// TODO Auto-generated method stub
		return mapper.PostCode(postvo);
	}

	@Override
	public int updateFile(AttachVO attachvo) {
		// TODO Auto-generated method stub
		return mapper.updatefile(attachvo);
	}

	@Override
	public ArrayList<AttachVO> getAttachview(String postnum) {
		// TODO Auto-generated method stub
		return mapper.getattachview(postnum);
	}

	@Override
	public int postCount(String postnum) {
		// TODO Auto-generated method stub
		return mapper.postcount(postnum);
	}


	

}
package com.together.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.together.domain.CommentVO;
import com.together.domain.MemberVO;
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
	public ArrayList<PostVO> getPost() {
		// TODO Auto-generated method stub
		return mapper.post();
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
		return mapper.getCommentview(postnum);
	}


	

}
package com.together.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.together.domain.CommentVO;
import com.together.domain.MemberVO;
import com.together.domain.PostVO;
import com.together.service.HomeService;

import lombok.AllArgsConstructor;



/**
 * Handles requests for the application home page.
 */
@Controller
@AllArgsConstructor
public class HomeController {
	
	private HomeService homservice;
	
	
   //홈 페이지 맵핑
   @RequestMapping(value = "/", method=RequestMethod.GET)
   public String home(Model model, HttpServletRequest request) {

		
	   return "home";
   }
   
   
   //로그인 페이지 맵핑
   @RequestMapping(value = "/mainpost", method=RequestMethod.GET)
   public String login(Model model,HttpServletRequest request) {
	   
	   
	   return "post";
   }
   
   //로그인 페이지 맵핑
   @RequestMapping(value = "/loginCheck", method=RequestMethod.POST)
   @ResponseBody
   public String login(Model model, @RequestParam("user_id") String user_id, 
		   @RequestParam("password") String password,HttpSession session) {

	   MemberVO MVO = new MemberVO();
	   MVO.setUser_id(user_id);
	   MVO.setPassword(password);
	   
	   ArrayList<MemberVO> loginCheck = homservice.login(MVO);
	   
	   
	   if(loginCheck.size() != 0) {
	   
		   if(user_id.equals(loginCheck.get(0).getUser_id())){
		   
			   if(password.equals(loginCheck.get(0).getPassword())) {
				   
			   System.out.println(loginCheck);
			   session.setAttribute("user",loginCheck.get(0));
			   		return "1";
			   }else {
				   return "2";
			   }
		   
		   
		   }
	   }
	   
	   return "3";
	   
   }

   
   //회원가입 페이지 맵핑
   @RequestMapping(value = "/signup", method=RequestMethod.GET)
   public String signup(Model model) {
	   
	   return "nav/signup";
  
   }
   
   //회원가입 페이지 맵핑
   @RequestMapping(value = "/logout", method=RequestMethod.GET)
   public String logout(Model model,HttpSession session) {
	   
	   session.invalidate();
	   
	   return "home";
  
   }
   
 //로그인 페이지 맵핑
   @RequestMapping(value = "/getPost", method=RequestMethod.POST)
   @ResponseBody
   public ArrayList<PostVO> getPost(Model model,HttpSession session) {
	   
	   ArrayList<PostVO> getPostList = homservice.getPost();
	   
	   model.addAttribute("post",getPostList);
	   System.out.println(getPostList);
	   
	return getPostList;

	  
   }
   
   
 //포스트 작성탭 이동
   @RequestMapping(value = "/addPost", method=RequestMethod.GET)
   public String addPost(Model model,HttpSession session) {
	  
	   return "addpost";
  
   }
   
   
   //포스트 작성시 insert문
     @RequestMapping(value = "/addwritepost", method=RequestMethod.POST)
     @ResponseBody
     public String addWritePost(Model model,HttpSession session,
    		 HttpServletRequest request,
    		 @RequestParam("title") String title, @RequestParam("content") String content) {
  	   
    	 
    	 System.out.println("이밑에가 값");
    	String user_id = ((MemberVO) request.getSession().getAttribute("user")).getUser_id();
    	
    	 PostVO postvo = new PostVO();
    	 postvo.setUser_id(user_id);
    	 postvo.setPost_title(title);
    	 postvo.setPost_info(content);
  	   	
    	 int addwritepost = homservice.addwritepost(postvo);
    	 
    	 
    	 System.out.println(addwritepost);
    	 
    	 if(addwritepost != 0) {
    		 return "1";
    	 }else {
    		 return "2";
    	 }
  	   
    	 

  	  
     }
     
//   //포스트 데이터처리
//     @RequestMapping(value = "/postview", method=RequestMethod.POST)
//     @ResponseBody
//     public ArrayList<PostVO> postview(Model model,HttpSession session,
//    		 @RequestParam("postNum") String num) {
//  	  
//    	 ArrayList<PostVO> DetailPost = homservice.getPostview(num);
//    	 System.out.println(DetailPost);
//    	 
//    	 
//    	 
//    	 if(DetailPost.get(0).getUser_id().equals(((MemberVO) session.getAttribute("user")).getUser_id())) {
//    		 model.addAttribute("grant",1);
//    	 }
//    	 System.out.println(model);
//  	   return DetailPost;
//    
//     }
     
     //포스트 뷰이동 탭
     @RequestMapping(value = "/detailpost"+"/{postnum}", method=RequestMethod.GET)
     public String detailpost(Model model,HttpSession session,
    		 HttpServletRequest request,
    		 @PathVariable String postnum) {
    	 
    	 String user_id = ((MemberVO) session.getAttribute("user")).getUser_id();
    	
    	 ArrayList<PostVO> DetailPost = new ArrayList<PostVO>();
    	 ArrayList<CommentVO> DetailComment = new ArrayList<CommentVO>();
    	 
    	 DetailPost= homservice.getPostview(postnum);
    	 DetailComment = homservice.getCommentview(postnum);
    	 
    	 if(DetailPost.get(0).getUser_id().equals(user_id)){
    		 model.addAttribute("grant",1);
    	 }
    	 model.addAttribute("comment",DetailComment);
    	 model.addAttribute("post",DetailPost);
    	 
    	 return "postview";
   
     }
}




package com.together.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.together.domain.AttachVO;
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
   
 //포스트리스트
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
   
   @RequestMapping(value = "/fileupload",method = RequestMethod.POST)
   @ResponseBody
   public String upload(MultipartFile uploadfile,HttpServletResponse response){
	   System.out.println("upload() POST 호출");
       System.out.println("파일 이름: {}" + uploadfile.getOriginalFilename());
       System.out.println("파일 크기: {}" + uploadfile.getSize());

       Upload utilfile = new Upload();
       String result = utilfile.saveFile(uploadfile);
       AttachVO AVO = new AttachVO();
       AVO.setAt_uid(result);
       AVO.setAt_name(uploadfile.getOriginalFilename());
       
       
     int DBupload = homservice.DBupload(AVO);
       
       
      if(result != null || DBupload != 0) {
    	  return result;
      }else {
    	  return "0";
      }
       

   }
   
   //포스트 작성시 insert문
     @RequestMapping(value = "/addwritepost", method=RequestMethod.POST)
     @ResponseBody
     public String addWritePost(Model model,HttpSession session,
    		 HttpServletRequest request,
    		 @RequestParam("title") String title, @RequestParam("content") String content,
    		 @RequestParam("filename") String file) {
  	   
    	 
    	 System.out.println("이밑에가 값");
    	 System.out.println(file);
    	String user_id = ((MemberVO) request.getSession().getAttribute("user")).getUser_id();
    	
    	 PostVO postvo = new PostVO();
    	 postvo.setUser_id(user_id);
    	 postvo.setPost_title(title);
    	 postvo.setPost_info(content);
  	   	
    	 int addwritepost = homservice.addwritepost(postvo);
    	 
    	 ArrayList<PostVO> PostCode = homservice.PostCode(postvo);
    	 
    	 AttachVO attachvo = new AttachVO();
    	 attachvo.setAt_uid(file);
    	 attachvo.setPost_cd(PostCode.get(0).getPost_cd());
    	 
    	 int updateFile = homservice.updateFile(attachvo);
    	 
    	 System.out.println(addwritepost);
    	 
    	 if(addwritepost != 0) {
    		 return "1";
    	 }else {
    		 return "2";
    	 }
  	   
    	 

  	  
     }
    
     //포스트 뷰이동 탭
     @RequestMapping(value = "/detailpost"+"/{postnum}", method=RequestMethod.GET)
     public String detailpost(Model model,HttpSession session,
    		 HttpServletRequest request,
    		 @PathVariable String postnum) {
    	 
    	 String user_id = ((MemberVO) session.getAttribute("user")).getUser_id();
    	
    	 ArrayList<PostVO> DetailPost = new ArrayList<PostVO>();
    	 ArrayList<CommentVO> DetailComment = new ArrayList<CommentVO>();
    	 ArrayList<AttachVO> DetailAttach = new ArrayList<AttachVO>();
    	 
    	 DetailPost= homservice.getPostview(postnum);
    	 DetailComment = homservice.getCommentview(postnum);
    	 DetailAttach = homservice.getAttachview(postnum);
    	 
    	 if(DetailPost.get(0).getUser_id().equals(user_id)){
    		 model.addAttribute("grant",1);
    	 }
    	 model.addAttribute("comment",DetailComment);
    	 model.addAttribute("post",DetailPost);
    	 
    	 if(DetailAttach.size() != 0) {
    		 model.addAttribute("file",DetailAttach.get(0));
    	 }
    	 
    	 return "postview";
   
     }
     
   //포스트 데이터처리
   @RequestMapping(value = "/addcomment", method=RequestMethod.POST)
   @ResponseBody
   public String addcomment(Model model,HttpSession session,
  		 @RequestParam("num") String num, @RequestParam("comment") String comment) {
	  
	   String user_id = ((MemberVO) session.getAttribute("user")).getUser_id();
	   CommentVO CVO = new CommentVO();
	   CVO.setUser_id(user_id);
	   CVO.setCmt_info(comment);
	   CVO.setPost_cd(Integer.parseInt(num));
	   
	   System.out.println(CVO);
	   
  	 	int AddComment = homservice.addComment(CVO);
  	 
  	 	if(AddComment != 0) {
  	 		return "1";
  	 	}else {
  	 		return "0";
  	 	}
  
   }
   
}




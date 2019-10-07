package com.together.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mysql.cj.util.StringUtils;
import com.together.domain.AttachVO;
import com.together.domain.CommentVO;
import com.together.domain.MemberVO;
import com.together.domain.Paging;
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

	// 홈 페이지 맵핑
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model, HttpServletRequest request) {

		return "home";
	}

	// 로그인 페이지 맵핑
	@RequestMapping(value = "/mainpost", method = RequestMethod.GET)
	public String login(Model model, HttpServletRequest request) {

		Paging pig = new Paging();



		pig.setStartNum(0);
		pig.setEndNum(10);
		ArrayList<PostVO> getPostList = homservice.getPost(pig);

		model.addAttribute("post", getPostList);

		return "post";
	}
	
	// 회원가입 맵핑
		@RequestMapping(value = "/signup", method = RequestMethod.GET)
		public String sighup(Model model, HttpServletRequest request) {
			
			

			
			return "sighup";
		}
		
		// 로그인 페이지 맵핑
		@RequestMapping(value = "/signupDB", method = RequestMethod.POST)
		@ResponseBody
		public String sigh(Model model,
				@RequestParam("id") String id, 
				@RequestParam("ps") String password,
				@RequestParam("name") String name, 
				@RequestParam("ph") String phNumber) {
			
				
			MemberVO member = new MemberVO();
			member.setUser_id(id);
			member.setUser_nm(name);
			member.setUser_ph(phNumber);
			member.setPassword(password);
			
			int signupDB = homservice.Sigh(member);
			
			if(signupDB != 0) {
				
				return "1";
			}
			
			
			return "0";
		}
		

	// 로그인 페이지 맵핑
	@RequestMapping(value = "/loginCheck", method = RequestMethod.POST)
	@ResponseBody
	public String login(Model model, @RequestParam("user_id") String user_id, @RequestParam("password") String password,
			HttpSession session) {

		MemberVO MVO = new MemberVO();
		MVO.setUser_id(user_id);
		MVO.setPassword(password);

		ArrayList<MemberVO> loginCheck = homservice.login(MVO);

		if (loginCheck.size() != 0) {

			if (user_id.equals(loginCheck.get(0).getUser_id())) {

				if (password.equals(loginCheck.get(0).getPassword())) {

					System.out.println(loginCheck);
					session.setAttribute("user", loginCheck.get(0));
					return "1";
				} else {
					return "2";
				}

			}
		}

		return "3";

	}

	// 로그아웃
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) {

		Cookie[] cookies = request.getCookies();

		if (cookies != null) {

			for (int i = 0; i < cookies.length; i++) {

				cookies[i].setMaxAge(0); // 유효시간을 0으로 설정

				response.addCookie(cookies[i]); // 응답 헤더에 추가

			}

		}
		session.invalidate();

		return "home";

	}


	// 포스트 작성탭 이동
	@RequestMapping(value = "/addPost", method = RequestMethod.GET)
	public String addPost(Model model, HttpSession session) {

		return "addpost";

	}
	
	

	@RequestMapping(value = "/fileupload", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	@ResponseBody
	public String upload(MultipartFile uploadfile, HttpServletResponse response) {
		System.out.println("upload() POST 호출");
		System.out.println("파일 이름: {}" + uploadfile.getOriginalFilename());
		System.out.println("파일 크기: {}" + uploadfile.getSize());

		Upload utilfile = new Upload();
		String result = utilfile.saveFile(uploadfile);
		AttachVO AVO = new AttachVO();
		AVO.setAt_uid(result);
		AVO.setAt_name(uploadfile.getOriginalFilename());
		AVO.setAt_size(uploadfile.getSize());

		int DBupload = homservice.DBupload(AVO);
		System.out.println(result);

		if (result != null || DBupload != 0) {
			return result;
		} else {
			return "0";
		}

	}

	// 포스트 작성시 insert문
	@RequestMapping(value = "/addwritepost", method = RequestMethod.POST)
	@ResponseBody
	public String addWritePost(Model model, HttpSession session, HttpServletRequest request,
			@RequestParam("title") String title, @RequestParam("content") String content,
			@RequestParam("filename") String file) throws UnsupportedEncodingException {

		System.out.println("이밑에가 값");
		System.out.println(new String(file.getBytes("utf-8"), "euc-kr"));
		System.out.println(file);
		String user_id = ((MemberVO) request.getSession().getAttribute("user")).getUser_id();

		PostVO postvo = new PostVO();
		postvo.setUser_id(user_id);
		postvo.setPost_title(title);
		postvo.setPost_info(content);

		int addwritepost = homservice.addwritepost(postvo);

		ArrayList<PostVO> PostCode = homservice.PostCode(postvo);

		System.out.println(PostCode);

		AttachVO attachvo = new AttachVO();
		attachvo.setAt_uid(file);
		attachvo.setPost_cd(PostCode.get(0).getPost_cd());

		int updateFile = homservice.updateFile(attachvo);

		System.out.println(addwritepost);

		if (addwritepost != 0) {
			return "1";
		} else {
			return "2";
		}

	}

	// 포스트 뷰이동 탭
	@RequestMapping(value = "/detailpost" + "/{postnum}", method = RequestMethod.GET)
	public String detailpost(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response,
			@PathVariable String postnum) {

		String user_id = ((MemberVO) session.getAttribute("user")).getUser_id();

		ArrayList<PostVO> DetailPost = new ArrayList<PostVO>();
		ArrayList<CommentVO> DetailComment = new ArrayList<CommentVO>();
		ArrayList<AttachVO> DetailAttach = new ArrayList<AttachVO>();

		DetailPost = homservice.getPostview(postnum);
		DetailComment = homservice.getCommentview(postnum);
		DetailAttach = homservice.getAttachview(postnum);

		if (DetailPost.get(0).getUser_id().equals(user_id)) {
			model.addAttribute("grant", 1);
		}
		model.addAttribute("comment", DetailComment);
		model.addAttribute("post", DetailPost);

		if (DetailAttach.size() != 0) {
			model.addAttribute("file", DetailAttach.get(0));

		}

		// 저장된 쿠키 불러오기
		Cookie cookies[] = request.getCookies();
		Map<Object, Object> map = new HashMap<>();
		if (request.getCookies() != null) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie obj = cookies[i];
				map.put(obj.getName(), obj.getValue());
			}
		}

		// 저장된 쿠키중에 read_count 만 불러오기
		String readCount = (String) map.get("read_count");
		// 저장될 새로운 쿠키값 생성
		String newReadCount = "|" + postnum;

		// 저장된 쿠키에 새로운 쿠키값이 존재하는 지 검사
		if (StringUtils.indexOfIgnoreCase(readCount, newReadCount) == -1) {

			// 없을 경우 쿠키 생성
			Cookie cookie = new Cookie("read_count", readCount + newReadCount);

			response.addCookie(cookie);
			int postcount = homservice.postCount(postnum);

		}

		return "postview";

	}

	// 포스트 데이터처리
	@RequestMapping(value = "/addcomment", method = RequestMethod.POST)
	@ResponseBody
	public String addcomment(Model model, HttpSession session, @RequestParam("num") String num,
			@RequestParam("comment") String comment) {

		String user_id = ((MemberVO) session.getAttribute("user")).getUser_id();
		CommentVO CVO = new CommentVO();
		CVO.setUser_id(user_id);
		CVO.setCmt_info(comment);
		CVO.setPost_cd(Integer.parseInt(num));

		System.out.println(CVO);

		int AddComment = homservice.addComment(CVO);

		if (AddComment != 0) {
			return "1";
		} else {
			return "0";
		}

	}

	@RequestMapping(value = "/downloadFile", method = RequestMethod.POST)
	@ResponseBody
	public byte[] downloadfile(Model model, HttpSession session, HttpServletResponse response,
			@RequestParam String filename, @RequestParam String fileuid) throws IOException {

		File file = new File("C:\\upload\\temp\\UPLOAD_PATH\\" + fileuid);
		byte[] bytes = FileCopyUtils.copyToByteArray(file);

		String fn = new String(file.getName().getBytes(), "iso_8859_1");

		response.setContentType("application/octet-stream; charset=utf-8");

		response.setHeader("Content-Disposition", "attachment;filename=\"" + filename + "\"");

		response.setContentLength(bytes.length);

		FileInputStream fileInputStream = new FileInputStream(file);
		ServletOutputStream servletOutputStream = response.getOutputStream();

		servletOutputStream.flush();// 출력

		fileInputStream.close();

		return bytes;

	}
	
	@RequestMapping(value = "/postdelete", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	@ResponseBody
	public String postDelete(Model model,@RequestParam("postnum") String postnum) {
	
		
		int postDel = homservice.PostDel(postnum);
		
		return "success";
	}

}

package com.spring.project.board.controller;

import java.io.File;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.spring.project.board.service.BoardService;
import com.spring.project.board.vo.ArticleVO;
import com.spring.project.member.vo.MemberVO;

@Controller
public class BoardControllerImpl implements BoardController {

	static Logger logger = LoggerFactory.getLogger(BoardController.class);
	private static final String ARTICLE_IMAGE_REPO = "C:\\board\\article_image"; 
	
	@Autowired
	BoardService boardService;
	@Autowired
	ArticleVO articleVO;
	
	@Override
	@RequestMapping(value="/board/listArticles.do", method= {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView listArticles(HttpServletRequest request, HttpServletResponse response) throws Exception {		 
		logger.info("listArticles() called");
		String viewName = (String) request.getAttribute("viewName");
		List<ArticleVO> articlesList = boardService.listArticles();
		ModelAndView mav = new ModelAndView();
		mav.addObject("articlesList", articlesList); 
		mav.setViewName(viewName);
		return mav;
	}

	@Override
	@RequestMapping(value="/board/addNewArticle.do", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity addNewArticle(MultipartHttpServletRequest multipartRequest, HttpServletResponse response)
			throws Exception {
		logger.info("addNewArticle() called");			
		multipartRequest.setCharacterEncoding("utf-8");
		Map<String, Object> articleMap = new HashMap<String, Object>();
		Enumeration enu = multipartRequest.getParameterNames();
		while(enu.hasMoreElements()) {
			String name = (String) enu.nextElement();
			String value = multipartRequest.getParameter(name);
			articleMap.put(name, value);
		}
		
		String imageFileName = upload(multipartRequest);
		HttpSession session = multipartRequest.getSession();
		MemberVO memberVO = (MemberVO) session.getAttribute("member");
		String id = memberVO.getId();
		articleMap.put("parentNO", 0);
		articleMap.put("id", id);
		articleMap.put("imageFileName", imageFileName);
		
		String message;
		ResponseEntity resEnt;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-type", "text/html; charset=utf-8");
		try {
			int articleNO = boardService.addNewArticle(articleMap);
			
			if(imageFileName != null && imageFileName.length() != 0) {
				// srcFile : upload() 통해 생성된 파일
				File srcFile = new File(ARTICLE_IMAGE_REPO + "\\temp\\" + imageFileName);
				File destDir = new File(ARTICLE_IMAGE_REPO + "\\" + articleNO);
				FileUtils.moveFileToDirectory(srcFile, destDir, true);
			} 
			
			message = "<script>"
					+ " alert('글이 정상적으로 저장됐습니다'); "
					+ " location.href='" + multipartRequest.getContextPath() + "/board/listArticles.do';"
					+ "</script>";
			resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
					
		} catch(Exception e) {
			// upload() 통해 생성된 srcFile 삭제
			File srcFile = new File(ARTICLE_IMAGE_REPO + "\\temp\\" + imageFileName);
			srcFile.delete();
			
			message = "<script>"
					+ " alert('글 작성에 실패했습니다!'); "
					+ " location.href='" + multipartRequest.getContextPath() + "/board/articleForm.do';"
					+ "</script>";
			resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
			e.printStackTrace();
		}
		
		return resEnt;
	}
	
	@RequestMapping(value="/board/*Form.do", method=RequestMethod.GET)
	public ModelAndView form(HttpServletRequest request, HttpServletResponse response) {
		logger.info("form() called");				
		String viewName = (String) request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		return mav;
	}
	
	private String upload(MultipartHttpServletRequest multipartRequest) throws Exception {
		logger.info("upload() called");		
		String imageFileName = null;
		Iterator<String> fileNames = multipartRequest.getFileNames();
		
		while(fileNames.hasNext()) {
			String fileName = fileNames.next();
			MultipartFile mFile = multipartRequest.getFile(fileName);
			imageFileName = mFile.getOriginalFilename();
			File file = new File(ARTICLE_IMAGE_REPO + "\\temp\\" + fileName);
			if(mFile.getSize() != 0) {
				file.getParentFile().mkdir();
				mFile.transferTo(new File(ARTICLE_IMAGE_REPO + "\\temp\\" + imageFileName));
			}
			
			logger.info("upload() called");
			logger.info("fileName : " + fileName);
			logger.info("imageFileName : " + imageFileName);
		}
	return imageFileName;
	}

	@Override
	@RequestMapping(value="/board/viewArticle.do", method=RequestMethod.GET)
	public ModelAndView viewArticle(int articleNO, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("viewArticle() called");
		String viewName = (String) request.getAttribute("viewName");
		articleVO = boardService.viewArticle(articleNO);
		ModelAndView mav = new ModelAndView();
		mav.addObject("article", articleVO);
		mav.setViewName(viewName);		
		return mav;
	}

	@Override
	@RequestMapping(value="/board/modArticle.do", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity modArticle(MultipartHttpServletRequest multipartRequest, HttpServletResponse response)
			throws Exception {
		logger.info("modArticle() called");
		multipartRequest.setCharacterEncoding("utf-8");
		Map<String, Object> articleMap = new HashMap<String, Object>();
		Enumeration enu = multipartRequest.getParameterNames();
		while(enu.hasMoreElements()) {
			String name = (String) enu.nextElement();
			String value = multipartRequest.getParameter(name);
			articleMap.put(name, value);
		}
		
		String imageFileName = upload(multipartRequest);
		HttpSession session = multipartRequest.getSession();
		MemberVO memberVO = (MemberVO) session.getAttribute("member");
		String id = memberVO.getId();
		articleMap.put("id", id);
		articleMap.put("imageFileName", imageFileName);
				
		String articleNO = (String) articleMap.get("articleNO");
		String message;
		ResponseEntity resEnt = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		
		try {
			boardService.modArticle(articleMap);
			if(imageFileName != null && imageFileName.length() != 0) {
				File srcFile = new File(ARTICLE_IMAGE_REPO + "\\temp\\" + imageFileName);
				File destDir = new File(ARTICLE_IMAGE_REPO + "\\" + articleNO);
				FileUtils.moveFileToDirectory(srcFile, destDir, true);
				
				String originalFileName = (String) articleMap.get("originalFileName");
				File oldFile = new File(ARTICLE_IMAGE_REPO + "\\" + articleNO + "\\" + originalFileName);
				oldFile.delete();
			} 
			message = "<script>"
					+ " alert('글이 정상적으로 수정됐습니다'); "
					+ " location.href='" + multipartRequest.getContextPath() + "/board/viewArticle?ArticleNO=" + articleNO + "';"
					+ " </script> ";
			resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
		} catch(Exception e) {
			File srcFile = new File(ARTICLE_IMAGE_REPO + "\\temp\\" + imageFileName);
			srcFile.delete();
			message = "<script>"
					+ " alert('글 수정 과정에서 에러가 발생했습니다!'); "
					+ " location.href='" + multipartRequest.getContextPath() + "/board/viewArticle?ArticleNO=" + articleNO + "';"
					+ " </script> ";
			resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
				e.printStackTrace();
		}
		return resEnt;
	}

}

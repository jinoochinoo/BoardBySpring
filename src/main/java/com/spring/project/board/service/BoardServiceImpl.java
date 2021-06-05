package com.spring.project.board.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.spring.project.board.dao.BoardDAO;
import com.spring.project.board.vo.ArticleVO;
import com.spring.project.board.vo.ImageVO;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class BoardServiceImpl implements BoardService {

	@Autowired
	BoardDAO boardDAO;
	
	@Override
	public Map<String, Object> listArticles(Map<String, Integer> pagingMap) throws Exception {
		Map<String, Object> mapArticles = new HashMap<String, Object>();
		List<ArticleVO> listArticles = boardDAO.selectAllArticlesList(pagingMap);
		int totalArticlesNO = boardDAO.selectTotalArticlesNO();
		
		mapArticles.put("listArticles", listArticles);
		mapArticles.put("totalArticlesNO", totalArticlesNO);
		return mapArticles;
	}
	
	@Override
	public int addNewArticle(Map<String, Object> articleMap) throws Exception{
		int articleNO = boardDAO.insertNewArticle(articleMap);
		articleMap.put("articleNO", articleNO);
		if(articleMap.get("imageFileList") != null) {
			boardDAO.insertNewImage(articleMap);
		}
		return articleNO;
	}
	
//////////////////////////////첨부파일 한 개일 때 사용한 addNewArticle() ///////////////////////////////////
//	@Override
//	public int addNewArticle(Map<String, Object> articleMap) throws Exception {
//		return boardDAO.insertNewArticle(articleMap); 
//	}

	@Override
	public Map<String, Object> viewArticle(int articleNO) throws Exception {
		Map<String, Object> articleMap = new HashMap<String, Object>();
		ArticleVO articleVO = boardDAO.selectArticle(articleNO);
		List<ImageVO> imageFileList = boardDAO.selectImageFileList(articleNO);
		articleMap.put("article", articleVO);
		articleMap.put("imageFileList", imageFileList);
		return articleMap;
	}

	@Override
	public void modArticle(Map<String, Object> articleMap) throws Exception {
		boardDAO.updateArticle(articleMap);
	}

	@Override
	public void removeArticle(int articleNO) throws Exception {
		boardDAO.deleteArticle(articleNO);
	}

	@Override 
	public void updateFile(Map<String, Object> updateFile) throws Exception {
		boardDAO.updateFile(updateFile);
	}

}

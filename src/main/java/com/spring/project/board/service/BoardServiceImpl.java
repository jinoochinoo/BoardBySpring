package com.spring.project.board.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.spring.project.board.dao.BoardDAO;
import com.spring.project.board.vo.ArticleVO;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class BoardServiceImpl implements BoardService {

	@Autowired
	BoardDAO boardDAO;
	
	@Override
	public List<ArticleVO> listArticles() throws Exception {
		List<ArticleVO> listArticles = boardDAO.selectAllArticlesList();
		return listArticles;
	}

	@Override
	public int addNewArticle(Map<String, Object> articleMap) throws Exception {
		return boardDAO.insertNewArticle(articleMap); 
	}

}

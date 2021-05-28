package com.spring.project.board.service;

import java.util.List;
import java.util.Map;

import com.spring.project.board.vo.ArticleVO;

public interface BoardService {

	public List<ArticleVO> listArticles() throws Exception;
	
	public int addNewArticle(Map<String, Object> articleMap) throws Exception;
}

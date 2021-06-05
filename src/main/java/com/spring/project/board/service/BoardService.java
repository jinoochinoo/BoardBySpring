package com.spring.project.board.service;

import java.util.List;
import java.util.Map;

import com.spring.project.board.vo.ArticleVO;

public interface BoardService {

	public Map<String, Object> listArticles(Map<String, Integer> pagingMap) throws Exception;
	
	public int addNewArticle(Map<String, Object> articleMap) throws Exception;

	public Map<String, Object> viewArticle(int articleNO) throws Exception;
	
	public void modArticle(Map<String, Object> articleMap) throws Exception;
	
	public void removeArticle(int articleNO) throws Exception;
	
	public void updateFile(Map<String, Object> updateFile) throws Exception;
}

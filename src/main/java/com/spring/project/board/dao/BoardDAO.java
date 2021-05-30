package com.spring.project.board.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.spring.project.board.vo.ArticleVO;

public interface BoardDAO {

	public List<ArticleVO> selectAllArticlesList() throws DataAccessException;

	public int insertNewArticle(Map<String, Object> articleMap) throws DataAccessException;

	public ArticleVO selectArticle(int articleNO) throws DataAccessException;

	public void updateArticle(Map<String, Object> articleMap) throws DataAccessException;

	public void deleteArticle(int articleNO) throws DataAccessException;
	
	public void insertNewImage(Map<String, Object> articleMap) throws DataAccessException;
}

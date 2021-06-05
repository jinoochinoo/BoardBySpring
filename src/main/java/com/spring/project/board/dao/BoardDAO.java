package com.spring.project.board.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.spring.project.board.vo.ArticleVO;
import com.spring.project.board.vo.ImageVO;

public interface BoardDAO {

	public List<ArticleVO> selectAllArticlesList(Map<String, Integer> pagingMap) throws DataAccessException;

	public int insertNewArticle(Map<String, Object> articleMap) throws DataAccessException;

	public ArticleVO selectArticle(int articleNO) throws DataAccessException;

	public void updateArticle(Map<String, Object> articleMap) throws DataAccessException;

	public void deleteArticle(int articleNO) throws DataAccessException;
	
	public void insertNewImage(Map<String, Object> articleMap) throws DataAccessException;

	public List<ImageVO> selectImageFileList(int articleNO) throws DataAccessException;

	public void updateFile(Map<String, Object> updateFile) throws DataAccessException;

	public int selectTotalArticlesNO() throws DataAccessException;

}

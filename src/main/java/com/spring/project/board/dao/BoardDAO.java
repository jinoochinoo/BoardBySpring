package com.spring.project.board.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.spring.project.board.vo.ArticleVO;

public interface BoardDAO {

	public List<ArticleVO> selectAllArticlesList() throws DataAccessException;

	public int insertNewArticle(Map<String, Object> articleMap) throws DataAccessException;
}
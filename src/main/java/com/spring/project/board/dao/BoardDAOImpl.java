package com.spring.project.board.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.spring.project.board.vo.ArticleVO;

@Repository
public class BoardDAOImpl implements BoardDAO {

	@Autowired
	SqlSession sqlSession;
	
	@Override
	public List<ArticleVO> selectAllArticlesList() throws DataAccessException {
		
		List<ArticleVO> listArticles = sqlSession.selectList("mapper.board.selectAllArticlesList");
		
		return listArticles;
	}

	@Override
	public int  insertNewArticle(Map<String, Object> articleMap) throws DataAccessException {
		
		int articleNO = selectNewArticleNO();
		articleMap.put("articleNO", articleNO);
		sqlSession.insert("mapper.board.insertNewArticle", articleMap);
		return articleNO;
	}
	
	private int selectNewArticleNO() {		
		int articleNO = sqlSession.selectOne("mapper.board.selectNewArticleNO");
		return articleNO;
	}

}

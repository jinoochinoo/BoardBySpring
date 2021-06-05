package com.spring.project.board.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.spring.project.board.vo.ArticleVO;
import com.spring.project.board.vo.ImageVO;

@Repository
public class BoardDAOImpl implements BoardDAO {

	@Autowired
	SqlSession sqlSession;
	
	@Autowired
	ArticleVO articleVO;
	
	
	
	@Override
	public List<ArticleVO> selectAllArticlesList(Map<String, Integer> pagingMap) throws DataAccessException {
		
		List<ArticleVO> listArticles = sqlSession.selectList("mapper.board.selectAllArticlesList", pagingMap);
		
		return listArticles;
	}
	
	@Override
	public int  insertNewArticle(Map<String, Object> articleMap) throws DataAccessException {
		int articleNO = selectNewArticleNO();
		articleMap.put("articleNO", articleNO);
		sqlSession.insert("mapper.board.insertNewArticle", articleMap);
		return articleNO;
	}
	
	@Override
	public void insertNewImage(Map<String, Object> articleMap) throws DataAccessException {
		List<ImageVO> imageFileList = (ArrayList) articleMap.get("imageFileList");
		int articleNO = (Integer) articleMap.get("articleNO");
		int imageFileNO = selectNewImageFileNO();
		for(ImageVO imageVO : imageFileList) {
			imageVO.setImageFileNO(++imageFileNO);
			imageVO.setArticleNO(articleNO);
		}
		sqlSession.insert("mapper.board.insertNewImage", imageFileList);
	}
	
	private int selectNewImageFileNO() throws DataAccessException {
		return sqlSession.selectOne("mapper.board.selectNewImageFileNO");
	}
	
	private int selectNewArticleNO() {		
		int articleNO = sqlSession.selectOne("mapper.board.selectNewArticleNO");
		return articleNO;
	}

	@Override
	public ArticleVO selectArticle(int articleNO) throws DataAccessException {
		articleVO = sqlSession.selectOne("mapper.board.selectArticle", articleNO);
		return articleVO;
	}

	@Override
	public void updateArticle(Map<String, Object> articleMap) throws DataAccessException {
		sqlSession.update("mapper.board.updateArticle", articleMap);
	}

	@Override
	public void deleteArticle(int articleNO) throws DataAccessException {
		sqlSession.delete("mapper.board.deleteArticle", articleNO);
	}

	@Override
	public List<ImageVO> selectImageFileList(int articleNO) throws DataAccessException {
		List<ImageVO> imageFileList = sqlSession.selectList("mapper.board.selectImageFileList", articleNO);		
		return imageFileList;

	}
	
	@Override 
	public void updateFile(Map<String, Object> updateFile) throws DataAccessException {
		sqlSession.update("mapper.board.updateFile", updateFile);
	}

	@Override
	public int selectTotalArticlesNO() throws DataAccessException {
		return sqlSession.selectOne("mapper.board.selectTotalArticlesNO");
	}

}

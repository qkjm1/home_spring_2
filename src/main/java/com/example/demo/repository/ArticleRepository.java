package com.example.demo.repository;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import com.example.demo.vo.Article;
import com.example.demo.vo.ResultData;


@Mapper
public interface ArticleRepository {


//	@Delete("DELETE FROM article WHERE id = #{id}")
	public void deleteArticle(int id);

//	@Update("UPDATE article SET updateDate = NOW(), title = #{title}, `body` = #{body} WHERE id = #{id}")
	public void modifyArticle(int id, String title, String body);

//	@Select("SELECT * FROM article WHERE id = #{id}")
	public Article getArticleById(int id);

//	@Select("SELECT * FROM article ORDER BY id DESC")
	public List<Article> getArticles();

	public void writeArticle(int memberId, String title, String body, String boardId);

	public int getLastInsertId();

	public Article getForPrintArticle(int id);

	public List<Article> getArticlesByBoard(int boardId);

	public int getTotalCnt();

	public int getArticleCountByBoard(int boardId, String searchKeywordTypeCode, String searchKeyword);

	public List<Article> getForPrintArticles(int boardId, int limitTake , int limitForm, String searchKeywordTypeCode, String searchKeyword);

	public int increaseHitCount(int id);
	
	public int getArticleHitCount(int id);

	
}
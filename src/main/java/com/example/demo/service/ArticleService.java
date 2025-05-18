package com.example.demo.service;

import java.io.Console;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.repository.ArticleRepository;
import com.example.demo.util.Ut;
import com.example.demo.vo.Article;
import com.example.demo.vo.ResultData;

import jakarta.websocket.server.ServerEndpoint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Service
public class ArticleService {

	@Autowired
	private ArticleRepository articleRepository;

	public ArticleService(ArticleRepository articleRepository) {
		this.articleRepository = articleRepository;
	}

	public ResultData writeArticle(int memberId, String title, String body, String boardId) {
		articleRepository.writeArticle(memberId, title, body, boardId);

		int id = articleRepository.getLastInsertId();

		return ResultData.from("S-1", Ut.f("%d번 글이 등록되었습니다", id), "등록 된 게시글 id", id);
	}

	public ResultData loginedMemberCanModify(int loginedMemberId, Article article) {

		if (article.getMemberId() != loginedMemberId) {
			return ResultData.from("F-A", Ut.f("%d번 게시글에 대한 권한 없음", article.getId()));
		}

		return ResultData.from("S-1", Ut.f("%d번 게시글을 수정함", article.getId()));
	}
	
	public ResultData userCanModify(int loginedMemberId, Article article) {

		if (article.getMemberId() != loginedMemberId) {
			return ResultData.from("F-A", Ut.f("%d번 게시글에 대한 수정 권한 없음", article.getId()));
		}

		return ResultData.from("S-1", Ut.f("%d번 게시글 수정 가능", article.getId()));
	}

	public ResultData userCanDelete(int loginedMemberId, Article article) {
		if (article.getMemberId() != loginedMemberId) {
			return ResultData.from("F-A", Ut.f("%d번 게시글에 대한 삭제 권한 없음", article.getId()));
		}

		return ResultData.from("S-1", Ut.f("%d번 게시글 삭제 가능", article.getId()));
	}
	
	public Article getForPrintArticle(int loginedMemberId, int id) {

		Article article = articleRepository.getForPrintArticle(id);

		controlForPrintData(loginedMemberId, article);

		return article;
	}
	

	public void deleteArticle(int id) {
		articleRepository.deleteArticle(id);
	}

	public void modifyArticle(int id, String title, String body) {
		articleRepository.modifyArticle(id, title, body);
	}

	public Article getArticleById(int id) {
		return articleRepository.getArticleById(id);
	}

	public List<Article> getArticles() {
		return articleRepository.getArticles();
	}
	
	
	private void controlForPrintData(int loginedMemberId, Article article) {
		if (article == null) {
			return;
		}	
// 수정과 삭제가 가능한 유저인지 상세보기 들어갈때 객체 찾아서 저장
		ResultData userCanModifyRd = userCanModify(loginedMemberId, article);
		article.setUserCanModify(userCanModifyRd.isSuccess());
		
		ResultData userDeleteRd = userCanDelete(loginedMemberId, article);
		article.setUserCanDelete(userDeleteRd.isSuccess());
		
	}

	public List<Article> getArticlesByBoard(int boardId) {
		return articleRepository.getArticlesByBoard(boardId);
	}

	public int getTotalCnt() {
		return articleRepository.getTotalCnt();
	}

	public int getArticleCountByBoard(int boardId, String searchKeywordTypeCode, String searchKeyword) {
		return articleRepository.getArticleCountByBoard(boardId, searchKeywordTypeCode, searchKeyword);
	}

	public List<Article> getForPrintArticles(int boardId, int listInApage, int page, String searchKeywordTypeCode, String searchKeyword) {
		int limitTake = listInApage;
		int limitForm = (page-1)*listInApage;
			
		return articleRepository.getForPrintArticles(boardId, limitTake, limitForm, searchKeywordTypeCode, searchKeyword);
	}

	public ResultData increaseHitCount(int id) {
		int affectedRow = articleRepository.increaseHitCount(id);

		if (affectedRow == 0) {
			return ResultData.from("F-1", "해당 게시글 없음", "id", id);
		}

		return ResultData.from("S-1", "조회수 증가", "id", id);
	}
	
	public Object getArticleHitCount(int id) {
		return articleRepository.getArticleHitCount(id);
	}


}

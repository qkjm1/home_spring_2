package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.ReactionRepository;
import com.example.demo.vo.ResultData;

@Service
public class ReactionService {

	@Autowired
	private ReactionRepository reactionRepository;

	public ReactionService(ReactionRepository reactionRepository) {
		this.reactionRepository = reactionRepository;
	}

	public ResultData userReaction(int isLoginMemberId, String relTypeCode, int relId) {

		if (isLoginMemberId == 0) {
			return ResultData.from("F-L", "로그인 하고 써야해");
		}

		int userReaction = reactionRepository.userReaction(isLoginMemberId, relTypeCode, relId);

		if (userReaction != 0) {
			return ResultData.from("F-1", "doReaction", "userReaction", userReaction);
		}

		return ResultData.from("S-1", "츄천가능", "userReaction", userReaction);
	}

	public ResultData delReaction(int isLoginMemberId, String relTypeCode, int relId) {
		reactionRepository.delReaction(isLoginMemberId, relTypeCode, relId);
		System.err.println("service");

		int userReaction = reactionRepository.userReaction(isLoginMemberId, relTypeCode, relId);

		return ResultData.from("S-2", "삭제 성공", "SUM point", userReaction);
	}

	public ResultData goodReaction(int isLoginMemberId, String relTypeCode, int relId) {

		System.err.println("good:isLoginMemberId=============" + isLoginMemberId);

		reactionRepository.goodReaction(isLoginMemberId, relTypeCode, relId);

		int afterRow = reactionRepository.userReaction(isLoginMemberId, relTypeCode, relId);

		if (afterRow != 1) {
			return ResultData.from("F-1", "좋아요 실패");
		}

		return ResultData.from("S-1", "좋아요 성공", "afterRow", afterRow);
	}

	public ResultData badReaction(int isLoginMemberId, String relTypeCode, int relId) {
		
		System.err.println("bad:isLoginMemberId=============" + isLoginMemberId);

		reactionRepository.badReaction(isLoginMemberId, relTypeCode, relId);

		int afterRow = reactionRepository.userReaction(isLoginMemberId, relTypeCode, relId);

		if (afterRow != -1) {
			return ResultData.from("F-1", "싫어요 실패");
		}

		return ResultData.from("S-1", "좋아요 성공", "afterRow", afterRow);
	}
	
	
	// 좋아요/싫어요 각 합
	
	public boolean isAlreadyAddGoodRp(int memberId, String relTypeCode, int relId) {
		int getPointTypeCodeByMemberId = reactionRepository.getSumReactionPoint(memberId, relTypeCode, relId);

		if (getPointTypeCodeByMemberId > 0) {
			return true;
		}

		return false;
	}

	public boolean isAlreadyAddBadRp(int memberId, String relTypeCode, int relId) {
		int getPointTypeCodeByMemberId = reactionRepository.getSumReactionPoint(memberId, relTypeCode, relId);

		if (getPointTypeCodeByMemberId < 0) {
			return true;
		}

		return false;
	}

}

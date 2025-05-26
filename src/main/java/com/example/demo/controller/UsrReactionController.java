package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.KjmDemoApplication;
import com.example.demo.interceptor.BeforeActionInterceptor;
import com.example.demo.service.ArticleService;
import com.example.demo.service.BoardService;
import com.example.demo.service.MemberService;
import com.example.demo.service.ReactionService;
import com.example.demo.util.Ut;
import com.example.demo.vo.Article;
import com.example.demo.vo.Member;
import com.example.demo.vo.ResultData;
import com.example.demo.vo.Rq;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Controller
public class UsrReactionController {

	private final UsrArticleController usrArticleController;

	@Autowired
	private Rq rq;

	@Autowired
	private ReactionService reactionService;

	UsrReactionController(UsrArticleController usrArticleController) {
		this.usrArticleController = usrArticleController;
	}

	// 좋아요를 눌렀을 때 (좋아요 인설트 / 싫어요 딜레이트)(인설트에 필요한것 누가(memberId)
	// 어느글(relTypeCode,relId)에 좋아요)
	// 좋아요 이후 띄울 화면에 URI
	@RequestMapping("/usr/reactionPoint/doGoodReaction")
	@ResponseBody
	public Object goodReaction(HttpServletRequest req, String relTypeCode, int relId, String replaceUri) {

		// 로그인한 유저가 해당글에 좋아요를 누른적이 있는지 확인
		ResultData usrReactionRd = reactionService.userReaction(rq.getIsLoginMemberId(), relTypeCode, relId);

		System.err.println("usrReactionRd: " + usrReactionRd.getData1());

		if (usrReactionRd.getData1().equals(1)) {
			ResultData Reaction = reactionService.delReaction(rq.getIsLoginMemberId(), relTypeCode, relId);

			return Ut.jsReplace("S-1", "좋아요 취소", replaceUri);

		} else if (usrReactionRd.getData1().equals(-1)) {
			ResultData Reaction = reactionService.delReaction(rq.getIsLoginMemberId(), relTypeCode, relId);

			Reaction = reactionService.goodReaction(rq.getIsLoginMemberId(), relTypeCode, relId);

			return Ut.jsReplace("S-2", "싫어요 했었음", replaceUri);
		}

		ResultData goodReactionRd = reactionService.goodReaction(rq.getIsLoginMemberId(), relTypeCode, relId);

		if (goodReactionRd.isFail()) {

			return ResultData.from(goodReactionRd.getResultCode(), goodReactionRd.getMsg());
		}

		return Ut.jsReplace("S-3", "좋아요", replaceUri);
	}
	
	
	// 싫어요를 눌렀을 때 (싫어요 인설트 / 좋아요 딜레이트)
	@RequestMapping("/usr/reactionPoint/doBadReaction")
	@ResponseBody
	public Object badReaction(HttpServletRequest req, String relTypeCode, int relId, String replaceUri) {

		// 로그인한 유저가 해당글에 싫어요를 누른적이 있는지 확인
		ResultData usrReactionRd = reactionService.userReaction(rq.getIsLoginMemberId(), relTypeCode, relId);

		System.err.println("usrReactionRd: " + usrReactionRd.getData1());

		if (usrReactionRd.getData1().equals(-1)) {
			ResultData Reaction = reactionService.delReaction(rq.getIsLoginMemberId(), relTypeCode, relId);
			System.err.println("싫어요 취소");
			return Ut.jsReplace("S-1", "싫어요 취소", replaceUri);

		} else if (usrReactionRd.getData1().equals(1)) {
			ResultData Reaction = reactionService.delReaction(rq.getIsLoginMemberId(), relTypeCode, relId);

			Reaction = reactionService.badReaction(rq.getIsLoginMemberId(), relTypeCode, relId);
			System.err.println("좋아요 취소후 싫어요");
			return Ut.jsReplace("S-2", "좋아요 했었음", replaceUri);
		}

		ResultData badReactionRd = reactionService.badReaction(rq.getIsLoginMemberId(), relTypeCode, relId);

		if (badReactionRd.isFail()) {

			return ResultData.from(badReactionRd.getResultCode(), badReactionRd.getMsg());
		}
		System.err.println("싫어요");
		return Ut.jsReplace("S-3", "싫어요", replaceUri);
	}

	
}
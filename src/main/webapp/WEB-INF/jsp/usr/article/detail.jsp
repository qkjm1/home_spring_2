<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="ARTICLE DETAIL"></c:set>
<%@ include file="../common/head.jspf"%>




<script>
	const params = {};
	params.id = parseInt('${param.id }');
	
	var addGoodRp = ${addGoodRp};
	var addBadRp = ${addBadRp};
</script>

<script>
	function ArticleDetail__doIncreaseHitCount() {
		
		const localKey = 'article__' + params.id + '__OnView';
		
		if(localStorage.getItem(localKey)){
			return;
		}
		localStorage.setItem(localKey, true);
		
		$.get('../article/doIncreaseHitCountRd', {
			id : params.id,
			ajaxMode : 'Y'
		}, function(data) {
			console.log(data);
			console.log(data.data1);
			console.log(data.msg);
			$('.article-detail__hit-count').html(data.data1);
		}, 'json');
	}

	$(function() {
		ArticleDetail__doIncreaseHitCount();

	})
</script>

<script>
	function checkRP() {
		if (addGoodRp == true) {
			$('#goodBtn').toggleClass('btn-outline');
		} else if (addBadRp==true){
			$('#badBtn').toggleClass('btn-outline');
		} else {
			return;
		}
	}
</script>

<section class="mt-24 text-xl px-4">
	<div class="mx-auto">
		<table class="table" border="1" cellspacing="0" cellpadding="5" style="width: 100%; border-collapse: collapse;">
			<tbody>
				<tr>
					<th style="text-align: center;">ID</th>
					<td style="text-align: center;">${article.id}</td>
				</tr>
				<tr>
					<th style="text-align: center;">Registration Date</th>
					<td style="text-align: center;">${article.regDate}</td>
				</tr>
				<tr>
					<th style="text-align: center;">Update Date</th>
					<td style="text-align: center;">${article.updateDate}</td>
				</tr>
				<tr>
					<th style="text-align: center;">Writer</th>
					<td style="text-align: center;">${article.extra__writer }</td>
				</tr>
				<tr>
					<th style="text-align: center;">BoardId</th>
					<td style="text-align: center;">${article.boardId }</td>
				</tr>
				<tr>
					<th style="text-align: center;">LIKE</th>
					<td style="text-align: center;">${article.goodReactionPoint }</td>
				</tr>
				<tr>
					<th style="text-align: center;">DISLIKE</th>
					<td style="text-align: center;">${article.badReactionPoint }</td>
				</tr>
				<tr>
					<th style="text-align: center;">VIEWS</th>
					<td style="text-align: center;">
						<span class="article-detail__hit-count">${article.hitCount }</span>
					</td>
				</tr>
				<tr>
					<th style="text-align: center;">Title</th>
					<td style="text-align: center;">${article.title }</td>
				</tr>
				<tr>
					<th style="text-align: center;">Body</th>
					<td style="text-align: center;">${article.body }</td>
				</tr>
			</tbody>
		</table>
		<div class="btns">
			<button class="btn btn-ghost" type="button" onclick="history.back();">뒤로가기</button>
			<c:if test="${article.userCanModify }">
				<a class="btn btn-ghost" href="../article/modify?id=${article.id}">수정</a>
			</c:if>
			<c:if test="${article.userCanDelete }">
				<a class="btn btn-ghost" href="../article/doDelete?id=${article.id}">삭제</a>
			</c:if>
		</div>
	</div>
	<div>${rq.currentUri}</div>
	<div>LIKE / DISLIKE / ${usrReaction}</div>
	<div class="mx-20 flex">
		<button id="goodBtn" class="btn btn-outline btn-success" onclick="doGoodReaction(${param.id})">
			👍 LIKE
			<span class="likeCount">${article.goodReactionPoint}</span>
		</button>
		<button id="badBtn" class="btn btn-outline btn-error" onclick="doBad(${param.id})">
			👎 DISLIKE
			<span class="DislikeCount">${article.badReactionPoint}</span>
		</button>
	</div>
	<div class="mx-20 flex">
		<a href="/usr/reactionPoint/doGoodReaction?relTypeCode=article&relId=${param.id }&replaceUri=${rq.currentUri}"
			class="btn btn-outline btn-success">👍 LIKE</a>
		<a href="/usr/reactionPoint/doBadReaction?relTypeCode=article&relId=${param.id }&replaceUri=${rq.currentUri}"
			class="btn btn-outline btn-error">👎 DISLIKE</a>
	</div>
</section>
</body>
</html>
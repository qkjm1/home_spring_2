<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="${board.code } LIST"></c:set>
<%@ include file="../common/head.jspf"%>


<section class="mt-8 text-xl px-4">
	<div class="mx-auto">
		<div>총 게시물: ${articlesCntByboard }개</div>
		<div>${page}페이지</div>
		<div>${boardId}보드값</div>
		<div>${totalPage}토탈페이지</div>
			<div class="flex-grow"></div>
			<!-- 			<form action="../article/list"> -->
			<form action="">
				<input type="hidden" name="boardId" value="${param.boardId }" />
				<div class="flex">
					<select class="select select-sm select-bordered
						max-w-xs" name="searchKeywordTypeCode"
						data-value="${param.searchKeywordTypeCode } ">
						<option value="title">title</option>
						<option value="body">body</option>
						<option value="title,body">title+body</option>
						<option value="nickname">nicnkname</option>
					</select>
					<label class="ml-3 input input-bordered input-sm flex items-center gap-2">
						<input type="text" placeholder="Search" name="searchKeyword" value="${param.searchKeyword }" />
						<button type="submit">
							<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 16 16" fill="currentColor" class="h-4 w-4 opacity-70">
    <path fill-rule="evenodd" d="M9.965 11.026a5 5 0 1 1 1.06-1.06l2.755 2.754a.75.75 0 1 1-1.06 1.06l-2.755-2.754ZM10.5 7a3.5 3.5 0 1 1-7 0 3.5 3.5 0 0 1 7 0Z"
									clip-rule="evenodd" /></svg>
						</button>
					</label>
				</div>
			</form>
		</div>
		<table border="1" cellspacing="0" cellpadding="5" style="width: 100%; border-collapse: collapse;">

			<thead>
				<tr>
					<th style="text-align: center;">BOARD</th>
					<th style="text-align: center;">ID</th>
					<th style="text-align: center;">Registration Date</th>
					<th style="text-align: center;">Title</th>
					<th style="text-align: center;">Writer</th>
					<th style="text-align: center;">VIEWS</th>
					<th style="text-align: center;">sumRP</th>
					<th style="text-align: center;">goodRP</th>
					<th style="text-align: center;">badRP</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="article" items="${articles}">
					<tr>
						<td style="text-align: center;">${article.this__board}</td>
						<td style="text-align: center;">${article.id}</td>
						<td style="text-align: center;">${article.regDate.substring(0,10)}</td>
						<td style="text-align: center;">
							<a class="hover:underline" href="detail?id=${article.id }">${article.title }</a>
						</td>
						<td style="text-align: center;">${article.extra__writer }</td>
						<td style="text-align: center;">${article.hitCount }</td>
						<td style="text-align: center;">${article.extra__sumReactionPoint }</td>
						<td style="text-align: center;">${article.extra__goodReactionPoint }</td>
						<td style="text-align: center;">${article.extra__badReactionPoint }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="flex justify-center">
			<c:if test="${empty articles }">
				<tr style="text-align: center;">게시글이 없습니다</tr>
			</c:if>
		</div>
	</div>
	<!-- 	동적 페이징 -->
	<div class="flex justify-center mt-4">
		<div class="btn-group join ">
			<c:set var="paginationLen" value="5" />
			<c:set var="startPage" value="${page - paginationLen >= 1 ? page - paginationLen : 1 }" />
			<c:set var="endPage" value="${page + paginationLen <= totalPage ? page + paginationLen : totalPage}" />
			
			<c:set var="baseUri" value="?boardId=${boardId}"/>
			<c:set var="baseUri" value="?searchKeywordTypeCode=${searchKeywordTypeCode}"/>
			<c:set var="baseUri" value="?searchKeyword=${searchKeyword}"/>

			<c:if test="${startPage > 1}">
				<a class="join-item btn btn-sm" href="${baseUri}&page=1&boardId=${boardId}">1</a>
			</c:if>

			<c:if test="${startPage > 2}">
				<button class="join-item btn btn-sm btn-disabled">...</button>
			</c:if>

			<c:forEach begin="${startPage }" end="${endPage }" var="i">
				<a class="join-item btn btn-sm ${param.page == i ? 'btn-active' : ''}" href="${baseUri}&page=${i }&boardId=${boardId}">${i }</a>
			</c:forEach>

			<c:if test="${endPage < totalPage - 1}">
				<button class="join-item btn-sm btn btn-disabled">...</button>
			</c:if>

			<c:if test="${endPage < totalPage}">
				<a class="join-item btn btn-sm" href="${baseUri}&page=${totalPage }&boardId=${boardId}">${totalPage }</a>
			</c:if>
		</div>
	</div>


</section>



<%@ include file="../common/foot.jspf"%>
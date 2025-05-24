package com.example.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Article {

	private int id;
	private String regDate;
	private String updateDate;
	private int memberId;
	private int boardId;
	private String title;
	private String body;
	private int hitCount;
	private int goodReactionPoint;
	private int badReactionPoint;
	

	private String extra__writer;
	private String this__board;
	private boolean userCanModify; // 수정가능한 유저인지
	private boolean userCanDelete; // 삭제가능한 유저인지
	
	private int extra__sumReactionPoint;
}
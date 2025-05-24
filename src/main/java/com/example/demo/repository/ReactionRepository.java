package com.example.demo.repository;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.vo.ResultData;

@Mapper
public interface ReactionRepository {

	public int userReaction(int MemberId, String relTypeCode, int relId);

	public int delReaction(int MemberId, String relTypeCode, int relId);

	public int goodReaction(int MemberId, String relTypeCode, int relId);


}

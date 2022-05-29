package com.ssafy.happyhouse.service;

import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ssafy.happyhouse.dto.CommunityDto;
import com.ssafy.happyhouse.dto.CommunityParamDto;
import com.ssafy.happyhouse.dto.CommunityResultDto;
import com.ssafy.happyhouse.dto.ReplyDto;

public interface CommunityService {

    // 게시글 등록
    public CommunityResultDto communityInsert(CommunityDto dto, MultipartHttpServletRequest request);

    // 게시글 조회
    public CommunityResultDto communityDetail(CommunityParamDto communityParamDto);

    // 게시글 삭제
    public CommunityResultDto communityDelete(int boardId);

    // 게시글 수정
    public CommunityResultDto communityUpdate(CommunityDto dto, MultipartHttpServletRequest request);

    // 게시글 목록
    public CommunityResultDto communityList(CommunityParamDto communityParamDto);

    // 게시글 검색 결과
    public CommunityResultDto communityListKeyword(CommunityParamDto communityParamDto);


    /* 댓글 처리 */
    // 댓글 등록
    public int replyInsert(ReplyDto replyDto);

    // 특정 댓글 조회
    public ReplyDto replyDetail(CommunityParamDto communityParamDto);

    // 댓글 수정
    public int replyUpdate(ReplyDto replyDto);

    // 댓글 삭제
    public int replyDelete(int replyId);

}

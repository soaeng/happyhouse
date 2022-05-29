package com.ssafy.happyhouse.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.happyhouse.dto.CommunityDto;
import com.ssafy.happyhouse.dto.CommunityFileDto;
import com.ssafy.happyhouse.dto.CommunityParamDto;
import com.ssafy.happyhouse.dto.ReplyDto;

@Mapper
public interface CommunityDao {

    /* 게시글 등록 시 처리 */
    // 게시글 등록
    public int communityInsert(CommunityDto dto);

    // 게시글 파일 등록
    public int communityFileInsert(CommunityFileDto dto);


    /* 게시글 조회 시 처리 */
    // 게시글 상세조회
    public CommunityDto communityDetail(CommunityParamDto communityParamDto);

    // 해당 게시글 파일 목록
    public List<CommunityFileDto> communityDetailFileList(int boardId);

    // 해당 게시글 조회수
    public int communityUserReadCount(CommunityParamDto communityParamDto);

    // 해당 게시글 읽음 처리
    public int communityUserReadInsert(@Param("boardId") int boardId, @Param("userSeq") int userSeq);

    // 게시글 조회수 수정
    public int communityReadCountUpdate(int boardId);


    /* 게시글 삭제 시 처리 */
    // 게시글 삭제
    public int communityDelete(int boardId);

    // 해당 게시글 파일 삭제
    public int communityFileDelete(int boardId);

    // 해당 게시글 업로드된 파일 전체 삭제를 위해 url 가져오기
    public List<String> communityFileUrlDeleteList(int boardId);

    // 게시글 조회수 삭제
    public int communityReadCountDelete(int boardId);


    /* 게시글 수정 시 처리 */
    // 게시글 수정
    public int communityUpdate(CommunityDto dto);


    /* 게시판 목록 처리 */
    // 총 게시글 목록
    public List<CommunityDto> communityList(CommunityParamDto communityParamDto);

    // 총 게시글 수
    public int communityListTotalCount();

    // 검색어 입력 시 게시글 목록
    public List<CommunityDto> communityListKeyword(CommunityParamDto communityParamDto);

    // 검색한 게시글 총 수
    public int communityListKeywordTotalCount(CommunityParamDto communityParamDto);


    /* 댓글 처리 */
    // 댓글 등록
    public int replyInsert(ReplyDto dto);

    // 특정 댓글 조회
    public ReplyDto replyDetail(CommunityParamDto communityParamDto);

    // 댓글 수정
    public int replyUpdate(ReplyDto dto);

    // 댓글 삭제
    public int replyDelete(int replyId);

    // 댓글 목록 삭제
    public int replyListDelete(int boardId);

    // 댓글 목록
    public List<ReplyDto> replyList(int boardId);

}

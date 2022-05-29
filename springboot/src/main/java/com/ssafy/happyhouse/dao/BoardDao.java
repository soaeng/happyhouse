package com.ssafy.happyhouse.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.happyhouse.dto.BoardDto;
import com.ssafy.happyhouse.dto.BoardFileDto;
import com.ssafy.happyhouse.dto.BoardParamDto;

@Mapper
public interface BoardDao {

    /* 게시글 등록 시 처리 */
    // 게시글 등록
    public int boardInsert(BoardDto dto);

    // 게시글 파일 등록
    public int boardFileInsert(BoardFileDto dto);


    /* 게시글 조회 시 처리 */
    // 게시글 상세조회
    public BoardDto boardDetail(BoardParamDto boardParamDto);

    // 해당 게시글 파일 목록
    public List<BoardFileDto> boardDetailFileList(int boardId);

    // 해당 게시글 조회수
    public int boardUserReadCount(BoardParamDto boardParamDto);

    // 해당 게시글 읽음 처리
    public int boardUserReadInsert(@Param("boardId") int boardId, @Param("userSeq") int userSeq);

    // 게시글 조회수 수정
    public int boardReadCountUpdate(int boardId);


    /* 게시글 삭제 시 처리 */
    // 게시글 삭제
    public int boardDelete(int boardId);

    // 해당 게시글 파일 삭제
    public int boardFileDelete(int boardId);

    // 해당 게시글 업로드된 파일 전체 삭제를 위해 url 가져오기
    public List<String> boardFileUrlDeleteList(int boardId);

    // 게시글 조회수 삭제
    public int boardReadCountDelete(int boardId);


    /* 게시글 수정 시 처리 */
    // 게시글 수정
    public int boardUpdate(BoardDto dto);


    /* 게시판 목록 처리 */
    // 총 게시글 목록
    public List<BoardDto> boardList(BoardParamDto boardParamDto);

    // 총 게시글 수
    public int boardListTotalCount();

    // 검색어 입력 시 게시글 목록
    public List<BoardDto> boardListKeyword(BoardParamDto boardParamDto);

    // 검색한 게시글 총 수
    public int boardListKeywordTotalCount(BoardParamDto boardParamDto);

}

package com.ssafy.happyhouse.service;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ssafy.happyhouse.dto.BoardDto;
import com.ssafy.happyhouse.dto.BoardParamDto;
import com.ssafy.happyhouse.dto.BoardResultDto;

public interface BoardService {

    // 게시글 등록
    public BoardResultDto boardInsert(BoardDto dto, MultipartHttpServletRequest request);

    // 게시글 조회
    public BoardResultDto boardDetail(BoardParamDto boardParamDto);

    // 게시글 삭제
    public BoardResultDto boardDelete(int boardId);

    // 게시글 수정
    public BoardResultDto boardUpdate(BoardDto dto, MultipartHttpServletRequest request);

    // 게시글 목록
    public BoardResultDto boardList(BoardParamDto boardParamDto);

    // 게시글 검색 결과
    public BoardResultDto boardListKeyword(BoardParamDto boardParamDto);

}

package com.ssafy.happyhouse.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ssafy.happyhouse.dao.BoardDao;
import com.ssafy.happyhouse.dto.BoardDto;
import com.ssafy.happyhouse.dto.BoardFileDto;
import com.ssafy.happyhouse.dto.BoardParamDto;
import com.ssafy.happyhouse.dto.BoardResultDto;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class BoardServiceImpl implements BoardService {

    @Autowired
    BoardDao dao;
    String uploadFolder = "upload" + File.separator + "board";

    String uploadPath = "C:" + File.separator + "study" + File.separator + "happyhouse"
            + File.separator + "vue"
            + File.separator + "public"
            + File.separator + "assets";

    private static final int SUCCESS = 1;
    private static final int FAIL = -1;


    // 게시글 등록
    @Override
    @Transactional
    public BoardResultDto boardInsert(BoardDto dto, MultipartHttpServletRequest request) {

        // log.info("===== 게시글 등록 service =====");

        BoardResultDto boardResultDto = new BoardResultDto();

        try {
            dao.boardInsert(dto); // useGeneratedKeys="true" keyProperty="boardId"
            // log.info("게시글 DB 등록");
            List<MultipartFile> fileList = request.getFiles("file");

            File uploadDir = new File(uploadPath + File.separator + uploadFolder);
            if (!uploadDir.exists()) uploadDir.mkdir();

            for (MultipartFile partFile : fileList) {

                int boardId = dto.getBoardId();

                String fileName = partFile.getOriginalFilename();

                //Random File Id
                UUID uuid = UUID.randomUUID();

                //file extension
                String extension = FilenameUtils.getExtension(fileName); // vs FilenameUtils.getBaseName()

                String savingFileName = uuid + "." + extension;

                File destFile = new File(uploadPath + File.separator + uploadFolder + File.separator + savingFileName);

                System.out.println(uploadPath + File.separator + uploadFolder + File.separator + savingFileName);
                partFile.transferTo(destFile);

                // Table Insert
                BoardFileDto boardFileDto = new BoardFileDto();
                boardFileDto.setBoardId(boardId);
                boardFileDto.setFileName(fileName);
                boardFileDto.setFileSize(partFile.getSize());
                boardFileDto.setFileContentType(partFile.getContentType());
                String boardFileUrl = uploadFolder + "/" + savingFileName;
                boardFileDto.setFileUrl(boardFileUrl);

                dao.boardFileInsert(boardFileDto);
            }

            boardResultDto.setResult(SUCCESS);

        } catch (IOException e) {
            e.printStackTrace();
            boardResultDto.setResult(FAIL);
        }
        return boardResultDto;
    }


    // 게시글 조회
    @Override
    public BoardResultDto boardDetail(BoardParamDto boardParamDto) {
        // log.info("===== 게시글 조회 service - 확인 완료 =====");
        BoardResultDto boardResultDto = new BoardResultDto();

        try {

            BoardDto boardDto = dao.boardDetail(boardParamDto);
            log.info("게시글 상세 조회: " + boardDto);

            if (boardDto.getUserSeq() == boardParamDto.getUserSeq()) {
                boardDto.setSameUser(true);
            } else {
                boardDto.setSameUser(false);
            }
            log.info("게시글 상세 조회: " + boardDto);

            int userReadCnt = dao.boardUserReadCount(boardParamDto);
            // log.info("게시글 조회수 조회: " + userReadCnt);
            if (userReadCnt == 0 && !boardDto.isSameUser()) {
                dao.boardUserReadInsert(boardParamDto.getBoardId(), boardParamDto.getUserSeq());
                dao.boardReadCountUpdate(boardParamDto.getBoardId());
            }

            List<BoardFileDto> fileList = dao.boardDetailFileList(boardDto.getBoardId());

            boardDto.setFileList(fileList);
            boardResultDto.setDto(boardDto);
            boardResultDto.setResult(SUCCESS);

        } catch (Exception e) {
            e.printStackTrace();
            boardResultDto.setResult(FAIL);
        }

        return boardResultDto;
    }


    // 게시글 수정
    @Override
    @Transactional
    public BoardResultDto boardUpdate(BoardDto dto, MultipartHttpServletRequest request) {
        // log.info("===== 게시글 수정 service =====");
        BoardResultDto boardResultDto = new BoardResultDto();


        try {
            dao.boardUpdate(dto);
            // log.info("게시글 DB 수정");
            List<MultipartFile> fileList = request.getFiles("file");

            File uploadDir = new File(uploadPath + File.separator + uploadFolder);
            if (!uploadDir.exists()) uploadDir.mkdir();

            if (fileList.size() > 0) {
                List<String> fileUrlList = dao.boardFileUrlDeleteList(dto.getBoardId());
                // log.info("----- 게시판에 저장된 삭제될 첨부파일 주소 -----");
                // fileUrlList.forEach(url -> log.info(url));

                for (String fileUrl : fileUrlList) {
                    File file = new File(uploadPath + File.separator, fileUrl);
                    if (file.exists()) {
                        file.delete();
                    }
                }

                dao.boardFileDelete(dto.getBoardId()); // 테이블 파일 삭제
                // log.info("해당 게시물에 있는 파일 DB 삭제");

                for (MultipartFile part : fileList) {
                    int boardId = dto.getBoardId();

                    String fileName = part.getOriginalFilename();

                    //Random File Id
                    UUID uuid = UUID.randomUUID();

                    //file extension
                    String extension = FilenameUtils.getExtension(fileName); // vs FilenameUtils.getBaseName()

                    String savingFileName = uuid + "." + extension;

                    File destFile = new File(uploadPath + File.separator + uploadFolder + File.separator + savingFileName);
                    part.transferTo(destFile);

                    // Table Insert
                    BoardFileDto boardFileDto = new BoardFileDto();
                    boardFileDto.setBoardId(boardId);
                    boardFileDto.setFileName(fileName);
                    boardFileDto.setFileSize(part.getSize());
                    boardFileDto.setFileContentType(part.getContentType());
                    String boardFileUrl = uploadFolder + "/" + savingFileName;
                    boardFileDto.setFileUrl(boardFileUrl);

                    dao.boardFileInsert(boardFileDto);
                }
            }
            boardResultDto.setResult(SUCCESS);

        } catch (IOException e) {
            e.printStackTrace();
            boardResultDto.setResult(FAIL);
        }

        return boardResultDto;
    }


    // 게시글 삭제
    @Override
    @Transactional
    public BoardResultDto boardDelete(int boardId) {
        // log.info("===== 게시글 삭제 service =====");
        BoardResultDto boardResultDto = new BoardResultDto();

        try {
            List<String> fileUrlList = dao.boardFileUrlDeleteList(boardId);

            // log.info("----- 게시판에 저장된 삭제될 첨부파일 주소 -----");
            fileUrlList.forEach(url -> log.info(url));

            for (String fileUrl : fileUrlList) {
                File file = new File(uploadPath + File.separator, fileUrl);
                if (file.exists()) {
                    file.delete();
                }
            }

            dao.boardFileDelete(boardId);
            // log.info("게시글 파일 DB 삭제");
            dao.boardReadCountDelete(boardId);
            // log.info("게시글 조휘수 삭제");
            dao.boardDelete(boardId);
            // log.info("게시글 DB 삭제");

            boardResultDto.setResult(SUCCESS);

        } catch (Exception e) {
            e.printStackTrace();
            boardResultDto.setResult(FAIL);
        }

        return boardResultDto;
    }


    // 게시글 목록
    @Override
    public BoardResultDto boardList(BoardParamDto boardParamDto) {
        log.info("===== 게시글 목록 service =====");
        BoardResultDto boardResultDto = new BoardResultDto();
        try {
            log.info("boardParamDto: " + boardParamDto);
            List<BoardDto> list = dao.boardList(boardParamDto);
            log.info("----- 게시글 -----");
            int len = list.size();
            for (int i = 0; i < len; i++) {
                List<BoardFileDto> fileList = dao.boardDetailFileList(list.get(i).getBoardId());
                list.get(i).setFileList(fileList);
                log.info(list.get(i));
            }

            int count = dao.boardListTotalCount();
            log.info("게시글 총 수: " + count);

            boardResultDto.setList(list);
            boardResultDto.setCount(count);
            boardResultDto.setResult(SUCCESS);

        } catch (Exception e) {
            e.printStackTrace();
            boardResultDto.setResult(FAIL);
        }

        return boardResultDto;
    }


    // 게시글 검색 결과
    @Override
    public BoardResultDto boardListKeyword(BoardParamDto boardParamDto) {
        log.info("===== 게시글 검색 목록 service =====");
        BoardResultDto boardResultDto = new BoardResultDto();
        log.info(boardParamDto);
        try {
            List<BoardDto> list = dao.boardListKeyword(boardParamDto);

            log.info("----- 게시글 검색 -----");
            list.forEach(board -> log.info(board));
            int len = list.size();
            for (int i = 0; i < len; i++) {
                List<BoardFileDto> fileList = dao.boardDetailFileList(list.get(i).getBoardId());
                list.get(i).setFileList(fileList);
                // log.info(list.get(i));
            }

            int count = dao.boardListKeywordTotalCount(boardParamDto);
            // log.info("검색된 게시글 총 수: " + count);

            boardResultDto.setList(list);
            boardResultDto.setCount(count);

            boardResultDto.setResult(SUCCESS);

        } catch (Exception e) {
            e.printStackTrace();
            boardResultDto.setResult(FAIL);
        }

        return boardResultDto;
    }

}
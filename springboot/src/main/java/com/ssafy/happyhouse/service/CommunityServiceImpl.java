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

import com.ssafy.happyhouse.dao.CommunityDao;
import com.ssafy.happyhouse.dto.CommunityDto;
import com.ssafy.happyhouse.dto.CommunityFileDto;
import com.ssafy.happyhouse.dto.CommunityParamDto;
import com.ssafy.happyhouse.dto.CommunityResultDto;
import com.ssafy.happyhouse.dto.ReplyDto;

import lombok.extern.log4j.Log4j2;

	@Service
	@Log4j2
	public class CommunityServiceImpl implements CommunityService {
	
		@Autowired
		CommunityDao dao;
		String uploadFolder = "upload" + File.separator + "community";
	
		String uploadPath = "C:" + File.separator + "Users" +File.separator + "sodud" +File.separator + "Desktop" +File.separator + "SSAFY"
				+ File.separator + "vue" + File.separator + "vue_cli"  
				+ File.separator + "happyhouse-final" + File.separator + "vue"  
				+ File.separator + "public" 
				+ File.separator + "assets";
		
	private static final int SUCCESS = 1;
	private static final int FAIL = -1;
	
	 
	// 게시글 등록
	@Override
	@Transactional
	public CommunityResultDto communityInsert(CommunityDto dto, MultipartHttpServletRequest request) {
		
		// log.info("===== 게시글 등록 service =====");
		
		CommunityResultDto communityResultDto = new CommunityResultDto();
		
		try {
			dao.communityInsert(dto); // useGeneratedKeys="true" keyProperty="boardId"
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
			    CommunityFileDto communityFileDto = new CommunityFileDto();
			    communityFileDto.setBoardId(boardId);
			    communityFileDto.setFileName(fileName);
			    communityFileDto.setFileSize(partFile.getSize());
				communityFileDto.setFileContentType(partFile.getContentType());
				String communityFileUrl = uploadFolder + "/" + savingFileName;
				communityFileDto.setFileUrl(communityFileUrl);
				
				dao.communityFileInsert(communityFileDto);
			}

			communityResultDto.setResult(SUCCESS);
			
		}catch(IOException e) {
			e.printStackTrace();
			communityResultDto.setResult(FAIL);
		}
		return communityResultDto;
	}

	
	// 게시글 조회
	@Override
	public CommunityResultDto communityDetail(CommunityParamDto communityParamDto) {
		// log.info("===== 게시글 조회 service - 확인 완료 =====");
		CommunityResultDto communityResultDto = new CommunityResultDto();
		
		try {
			
			CommunityDto communityDto = dao.communityDetail(communityParamDto);
			log.info("게시글 상세 조회: " + communityDto);
			
			if( communityDto.getUserSeq() == communityParamDto.getUserSeq() ) {
				communityDto.setSameUser(true);
			}else {
				communityDto.setSameUser(false);
			}
			log.info("게시글 상세 조회: " + communityDto);
			
			int userReadCnt = dao.communityUserReadCount(communityParamDto);
			// log.info("게시글 조회수 조회: " + userReadCnt);
			if( userReadCnt == 0 && !communityDto.isSameUser()) {
				dao.communityUserReadInsert(communityParamDto.getBoardId(), communityParamDto.getUserSeq());
				dao.communityReadCountUpdate(communityParamDto.getBoardId());
			}
			
			List<CommunityFileDto> fileList = dao.communityDetailFileList(communityDto.getBoardId());
			List<ReplyDto> replyList = dao.replyList(communityParamDto);
			communityDto.setFileList(fileList);
			communityDto.setReplyList(replyList);
			communityResultDto.setDto(communityDto);
			communityResultDto.setResult(SUCCESS);
			
		}catch(Exception e) {
			e.printStackTrace();
			communityResultDto.setResult(FAIL);
		}
		
		return communityResultDto;
	}

	
	// 게시글 수정
	@Override
	@Transactional
	public CommunityResultDto communityUpdate(CommunityDto dto, MultipartHttpServletRequest request){
		// log.info("===== 게시글 수정 service =====");
		CommunityResultDto communityResultDto = new CommunityResultDto();

		
		try {
			dao.communityUpdate(dto);
			// log.info("게시글 DB 수정");
			List<MultipartFile> fileList = request.getFiles("file");
			
			File uploadDir = new File(uploadPath + File.separator + uploadFolder);
			if (!uploadDir.exists()) uploadDir.mkdir();
			
			if(fileList.size() > 0) {
		    	List<String> fileUrlList = dao.communityFileUrlDeleteList(dto.getBoardId());
		    	// log.info("----- 게시판에 저장된 삭제될 첨부파일 주소 -----");
		    	// fileUrlList.forEach(url -> log.info(url));
		    	
				for(String fileUrl : fileUrlList) {	
					File file = new File(uploadPath + File.separator, fileUrl);
					if(file.exists()) {
						file.delete();
					}
				}
	
		    	dao.communityFileDelete(dto.getBoardId()); // 테이블 파일 삭제
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
				    CommunityFileDto communityFileDto = new CommunityFileDto();
				    communityFileDto.setBoardId(boardId);
				    communityFileDto.setFileName(fileName);
				    communityFileDto.setFileSize(part.getSize());
					communityFileDto.setFileContentType(part.getContentType());
					String communityFileUrl = uploadFolder + "/" + savingFileName;
					communityFileDto.setFileUrl(communityFileUrl);
					
					dao.communityFileInsert(communityFileDto);
				}
			}
			communityResultDto.setResult(SUCCESS);
			
		}catch(IOException e) {
			e.printStackTrace();
			communityResultDto.setResult(FAIL);
		}
		
		return communityResultDto;
	}
	
	
	// 게시글 삭제
	@Override
	@Transactional
	public CommunityResultDto communityDelete(int boardId) {
		// log.info("===== 게시글 삭제 service =====");
		CommunityResultDto communityResultDto = new CommunityResultDto();
		
		try {
			List<String> fileUrlList = dao.communityFileUrlDeleteList(boardId);
			
			// log.info("----- 게시판에 저장된 삭제될 첨부파일 주소 -----");
	    	fileUrlList.forEach(url -> log.info(url));
	    	
			for(String fileUrl : fileUrlList) {
				File file = new File(uploadPath + File.separator, fileUrl);				
				if(file.exists()) {
					file.delete();
				}
			}
			
			dao.communityFileDelete(boardId);
			// log.info("게시글 파일 DB 삭제");
			dao.communityReadCountDelete(boardId);
			// log.info("게시글 조휘수 삭제");
			dao.replyListDelete(boardId);
			dao.communityDelete(boardId);
			// log.info("게시글 DB 삭제");
			
			communityResultDto.setResult(SUCCESS);
			
		}catch(Exception e) {
			e.printStackTrace();
			communityResultDto.setResult(FAIL);
		}
		
		return communityResultDto;
	}
	
	
	// 게시글 목록
	@Override
	public CommunityResultDto communityList(CommunityParamDto communityParamDto) {
		log.info("===== 게시글 목록 service =====");
		CommunityResultDto communityResultDto = new CommunityResultDto();
		try {
			log.info("communityParamDto: " + communityParamDto);
			List<CommunityDto> list = dao.communityList(communityParamDto);
			log.info("----- 게시글 -----");
			int len = list.size();
			for(int i=0; i<len; i++) {
				List<CommunityFileDto> fileList = dao.communityDetailFileList(list.get(i).getBoardId());
				list.get(i).setFileList(fileList);
				log.info(list.get(i));
			}
	    	
			int count = dao.communityListTotalCount();
			log.info("게시글 총 수: " + count);
			
			communityResultDto.setList(list);
			communityResultDto.setCount(count);
			communityResultDto.setResult(SUCCESS);
			
		}catch(Exception e) {
			e.printStackTrace();
			communityResultDto.setResult(FAIL);
		}
		
		return communityResultDto;
	}

	
	// 게시글 검색 결과
	@Override
	public CommunityResultDto communityListKeyword(CommunityParamDto communityParamDto) {
		log.info("===== 게시글 검색 목록 service =====");
		CommunityResultDto communityResultDto = new CommunityResultDto();
		log.info(communityParamDto);
		try {
			List<CommunityDto> list = dao.communityListKeyword(communityParamDto);
			
			log.info("----- 게시글 검색 -----");
			list.forEach(board -> log.info(board));
			int len = list.size();
			for(int i=0; i<len; i++) {
				List<CommunityFileDto> fileList = dao.communityDetailFileList(list.get(i).getBoardId());
				list.get(i).setFileList(fileList);
				// log.info(list.get(i));
			}
	    	
			int count = dao.communityListKeywordTotalCount(communityParamDto);
			// log.info("검색된 게시글 총 수: " + count);
			
			communityResultDto.setList(list);
			communityResultDto.setCount(count);
			
			communityResultDto.setResult(SUCCESS);
		
		}catch(Exception e) {
			e.printStackTrace();
			communityResultDto.setResult(FAIL);
		}
		
		return communityResultDto;
	}


	@Override
	public int replyInsert(ReplyDto dto) {
		return dao.replyInsert(dto);
	}


	@Override
	public ReplyDto replyDetail(CommunityParamDto communityParamDto) {
		return dao.replyDetail(communityParamDto);
	}


	@Override
	public int replyUpdate(ReplyDto dto) {
		return dao.replyUpdate(dto);
	}


	@Override
	public int replyDelete(int replyId) {
		return dao.replyDelete(replyId);
	}

}
package com.ssafy.happyhouse.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.happyhouse.dto.AreaCodeDto;
import com.ssafy.happyhouse.dto.BookmarkDto;
import com.ssafy.happyhouse.dto.HouseDto;
import com.ssafy.happyhouse.dto.HouseParamDto;

@Mapper
public interface HouseDao {


    /* 지역 정보 */
    // 시도코드
    public List<AreaCodeDto> sidoList();

    // 구군코드
    public List<AreaCodeDto> gugunList(String sidoCode);

    // 동코드
    public List<AreaCodeDto> dongList(String gugunCode);

    // 동 조회
    public AreaCodeDto dongDetail(Map<String, String> param);


    /* 아파트 검색 */
    // 전체 아파트 목록
    public List<HouseDto> initAptList(HouseParamDto param);

    // 아파트 총 수
    public int initAptListTotalCount();

    // 동별 아파트 검색 목록
    public List<HouseDto> dongAptList(HouseParamDto param);

    // 동별 아파트 검색 총 수
    public int dongAptListTotalCount(String dongCode);

    // 아파트명 검색 목록
    public List<HouseDto> nameAptList(HouseParamDto param);

    // 아파트 검색 총 수
    public int nameAptListTotalCount(String keyword);

    // 동 아파트 검색 목록
    public List<HouseDto> allAptList(HouseParamDto param);

    // 동 아파트 검색 총 수
    public int allAptListTotalCount(Map<String, String> param);


    /* 거래 상세 */
    // 아파트별 거래 상세 목록
    public List<HouseDto> aptDealList(int houseNo);

    // 아파트별 거래 목록 수
    public int aptDealListTotalCount(int houseNo);


    /* 북마크 */
    // 관심지역 등록
    public int insertBookmarkArea(BookmarkDto bookmark);

    // 관심지역 조회
    public List<AreaCodeDto> bookmarkAreaList(int userSeq);

    // 관심지역 삭제
    public int deleteBookmarkArea(BookmarkDto bookmark);


    // 관심아파트 등록
    public int insertBookmarkHouse(BookmarkDto bookmark);

    // 관심아파트 조회
    public List<BookmarkDto> bookmarkHouseList(int userSeq);

    // 관심아파트 여부
    public boolean checkBookmarkHouse(BookmarkDto bookmark);

    // 관심아파트 삭제
    public int deleteBookmarkHouse(BookmarkDto bookmark);


    // 관심거래 등록
    public int insertBookmarkDeal(BookmarkDto bookmark);

    // 관심거래 조회
    public List<HouseDto> bookmarkDealList(int userSeq);

    // 관심거래 여부
    public boolean checkBookmarkDeal(BookmarkDto bookmark);

    // 관심거래 삭제
    public int deleteBookmarkDeal(BookmarkDto bookmark);

}

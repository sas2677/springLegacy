package kr.co.pro.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.pro.Entity.Board;


@Mapper //@mapper - mybatis API
public interface BoardMapper {
	//전체 리스트
	public List<Board> listAll();
	
	//글 작성 
	public void boardInsert(Board vo); 
	
	//상세보기
	public Board boardContent(int idx);
	
	//수정
	public void boardUpdate(Board vo);
	
	//삭제
	public void boardDelete(int idx);
	
	//조회수 증가
	public void boardCount(int idx);

	
	
}

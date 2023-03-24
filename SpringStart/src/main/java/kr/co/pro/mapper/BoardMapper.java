package kr.co.pro.mapper;

import java.util.List;

import kr.co.pro.Entity.Board;

//@mapper - mybatis API
public interface BoardMapper {
	//전체 리스트
	public List<Board> listAll();

	
	
}

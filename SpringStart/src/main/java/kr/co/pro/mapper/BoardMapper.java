package kr.co.pro.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.pro.Entity.Board;


@Mapper //@mapper - mybatis API
public interface BoardMapper {
	//전체 리스트
	public List<Board> listAll();

	
	
}

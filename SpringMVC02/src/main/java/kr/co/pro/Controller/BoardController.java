package kr.co.pro.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.pro.Entity.Board;
import kr.co.pro.mapper.BoardMapper;

@Controller //식별하기위해 어노테이션을 사용
public class BoardController { 			//new BoardController();
	
	@Autowired
	BoardMapper boardmapper;
	
	
	@RequestMapping("/")
	public String main() {
		return "main";
	}
	
	//@ResponseBody->jackson-databind(객체를 ->JSON 데이터포맷으로 변환)
	@RequestMapping("/boardList.do")
	public 	@ResponseBody  List<Board> boardList(){
	List<Board> list=boardmapper.listAll();
		return list; //JSON 데이터 형식으로 변환(API)해서 리턴(응답)하겠다.
	}
	
	@RequestMapping("/boardInsert.do")
	public @ResponseBody void boardInsert(Board vo) {
		boardmapper.boardInsert(vo);
		
	}
	
	@RequestMapping("/boardDelete.do")
	public @ResponseBody void boardDelete(@RequestParam("idx") int idx) {
		
		boardmapper.boardDelete(idx);
		
	}
}

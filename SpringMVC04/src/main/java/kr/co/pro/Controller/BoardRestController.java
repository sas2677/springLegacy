package kr.co.pro.Controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import kr.co.pro.Entity.Board;
import kr.co.pro.mapper.BoardMapper;

//@Responsbody가 기본으로 적용되어 있다
@RestController//ajax통신을 할때 사용하는 컨트롤러
@RequestMapping("/board")
public class BoardRestController {
	
	@Autowired
	BoardMapper boardmapper;
	
	
	//@ResponseBody->jackson-databind(객체를 ->JSON 데이터포맷으로 변환)
		@GetMapping("/all")
		public List<Board> boardList(){
		List<Board> list=boardmapper.listAll();
			return list; //JSON 데이터 형식으로 변환(API)해서 리턴(응답)하겠다.
		}
		
		//작성
		//@RequestMapping("/boardInsert.do")
		@PostMapping("/new")
		public void boardInsert(Board vo) {
			boardmapper.boardInsert(vo);
			
		}
		
		//상세보기
		@GetMapping("/{idx}")
		public Board boardContent(@PathVariable("idx") int idx) {
			Board vo=boardmapper.boardContent(idx);
			return vo;//vo->JSON
		}
		//수정
		@PutMapping("/update")
		public  void boardUpdate(@RequestBody Board vo) {
			boardmapper.boardUpdate(vo);
			
		}
		
		//삭제
		@DeleteMapping("/{idx}")
		public  void boardDelete(@PathVariable("idx") int idx) {
			
			boardmapper.boardDelete(idx);
			
		}
		
		//조회수
		@PutMapping("/count/{idx}")
		public  Board boardCount(@PathVariable("idx") int idx) {
			
			boardmapper.boardCount(idx);
			Board vo = boardmapper.boardContent(idx);
			
			return vo;
			
		}

}

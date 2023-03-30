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


import kr.co.pro.Entity.Board;
import kr.co.pro.mapper.BoardMapper;

@Controller //식별하기위해 어노테이션을 사용
public class BoardController { 			//new BoardController();
	@Autowired
	private BoardMapper mapper;
	
	
	//boardList.do
	//HandlerMapping
	@RequestMapping("/boardList.do")
	public String list(Model model) {
		
		List<Board> list = mapper.listAll();
		model.addAttribute("list", list);
		
		
		return "boardList";// /WEB-INF/views/boardList.jsp  ->forward
	}
	
	//등록 페이지 이동
	@GetMapping("/boardForm.do")
	public String boardForm() {
		return "boardForm"; // /WEB-INF/views/boardForm.jsp  ->forward
	}
	
	//등록 처리
	@PostMapping("/boardInsert.do")
	public String boardInsert(Board vo) { //파라메터수집(Board)
		mapper.boardInsert(vo); //등록 완료
		
		return "redirect:/boardList.do";//redirect경로
	}
	
	//상세보기
	@GetMapping("/boardContent.do/{idx}")
	public String boardContent(@PathVariable("idx") int idx, Model model) { //idx =6
		Board vo = mapper.boardContent(idx);
		//조회수 증가
		mapper.boardCount(idx);
		
		model.addAttribute("vo", vo);
		System.out.println(vo.toString());
		return "boardContent";// /WEB-INF/views/boardContent.jsp  ->forward
	}
	
	
	//수정 페이지 이동
	@GetMapping("/boardUpdateForm.do/{idx}")
	public String boardUpdateForm(@PathVariable("idx") int idx, Model model) {
		Board vo = mapper.boardContent(idx);
		model.addAttribute("vo", vo);
		return "boardUpdate"; // /WEB-INF/views/boardUpdate.jsp
	}
	
	//수정 처리
	@PostMapping("boardUpdate.do")
	public String boardUpdate(Board vo) {
		mapper.boardUpdate(vo);
		return "redirect:/boardList.do";
	}
	
	
	//삭제
	@GetMapping("/boardDelete.do/{idx}")
	public String boardDelete(@PathVariable("idx") int idx) {
		mapper.boardDelete(idx);
		return "redirect:/boardList.do";
	}
}

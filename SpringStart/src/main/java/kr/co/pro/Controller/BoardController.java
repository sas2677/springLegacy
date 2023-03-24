package kr.co.pro.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.pro.Entity.Board;

@Controller //식별하기위해 어노테이션을 사용
public class BoardController {
	
	//boardList.do
	//HandlerMapping
	@RequestMapping("/boardList.do")
	public String list(Model model) {
		
		Board vo = new Board();
		vo.setIdx(1);
		vo.setTitle("게시판 실습");
		vo.setContent("게시판실습");
		vo.setWriter("HARRIS");
		vo.setIndate("2022-03-24");
		vo.setCount(0);
		
		List<Board>list=new ArrayList<Board>();
		list.add(vo);
		list.add(vo);
		list.add(vo);
		
		model.addAttribute("list", list);
		
		
		return "boardList";// /WEB-INF/views/boardList.jsp  ->forward
	}
	
	
	
}

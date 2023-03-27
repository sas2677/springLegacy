package kr.co.pro.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.pro.Entity.Board;
import kr.co.pro.mapper.BoardMapper;

@Controller //식별하기위해 어노테이션을 사용
public class BoardController {
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
	
	
	
}

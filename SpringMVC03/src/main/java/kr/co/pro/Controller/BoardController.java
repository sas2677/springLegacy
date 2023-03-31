package kr.co.pro.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller //식별하기위해 어노테이션을 사용
public class BoardController { 			//new BoardController();
	

	
	
	@RequestMapping("/boardMain.do")
	public String main() {
		return "/board/main";
	}
	
	
}

package kr.co.pro.Controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import kr.co.pro.Entity.Member;
import kr.co.pro.mapper.MemberMapper;


@Controller
public class MemberController {
	
	@Autowired
	MemberMapper membermapper;
	
	@RequestMapping("/memJoin.do")
	public String meberForm() {
		
		
		return "/member/join";
	}
	
	@RequestMapping("/memregisterCheck.do")
	public @ResponseBody int memregisterCheck(@RequestParam("memID") String memID) {
		Member m=membermapper.registerCheck(memID);
		if(m !=null||memID.equals("")) {
			
			return 0; //이미 존재하는 회원, 입력불가;
		}
		return 1;//사용가능한 아이디 
	}
	
	//회원가입 처리
	@RequestMapping("/memRegister.do")
	public String memregister(Member m,String memPassword, String memPassword2,
								RedirectAttributes rttr, HttpSession session) {
		if(m.getMemID()==null||m.getMemID().equals("")||
		   memPassword==null||memPassword.equals("")||
		   memPassword2==null||memPassword2.equals("")||
		   m.getMemName()==null||m.getMemName().equals("")||	
		   m.getMemAge()== 0||
		   m.getMemGender()==null||m.getMemGender().equals("")||	
		   m.getMemEmail()==null||m.getMemEmail().equals("")) {
			//누락메세지를 가지고 가기? => 객체바인딩(Model, HttpServletRequest, HttpSession)
			rttr.addFlashAttribute("msgType", "실패 메세지");
			rttr.addFlashAttribute("msg", "모든 내용을 입력하세요");
			return "redirect:/memJoin.do"; //${msgType}, ${msg}
		}
		
	
		
		if(!memPassword.equals(memPassword2)) {
			rttr.addFlashAttribute("msgType", "실패 메세지");
			rttr.addFlashAttribute("msg", "비밀번호가 서로 다릅니다.");
			return "redirect:/memJoin.do"; //${msgType}, ${msg}
			
		}
		
		m.setMemProfile(""); //사진 이미지는 없는 상태 
		//회원을 테이블에 저장하기
		int result=membermapper.register(m);
		if(result==1) { //회원가입 성공 메세지 
			rttr.addFlashAttribute("msgType", "성공 메세지");
			rttr.addFlashAttribute("msg", "회원가입을 축하합니다.");
			System.out.println(m.toString());
			//회원가입이 성공하면 =>로그인처리하기
			session.setAttribute("loginuser", m);//로그인
			return "redirect:/";
		}else {
			rttr.addFlashAttribute("msgType", "에러 메세지");
			rttr.addFlashAttribute("msg", "이미 존재하는 회원입니다.");
			return "redirect:/memJoin.do";
		}
	}
	
	
	//로그아웃
	@RequestMapping("/memLogout.do")
	public String memLogout(HttpSession session) {
		session.invalidate();
		
		return "redirect:/";
	}
	
	//로그인 화면 이동
	@RequestMapping("/memLoginForm.do")
	public String memLoginForm() {
		
		return "member/memLoginForm";
	}
	
	//로그인 처리 
	@RequestMapping("/memLogin.do")
	public String memLogin(Member m, RedirectAttributes rttr, HttpSession session) {
		if(m.getMemID()==null||m.getMemID().equals("")||
		   m.getMemPassword()==null||m.getMemPassword().equals("")) {
			
			rttr.addFlashAttribute("msgType","실패메세지");
			rttr.addFlashAttribute("msg","모든 내용을 입력해주세요.");
			return "redirect:/memLoginForm.do";
		}
			Member mvo=membermapper.memLogin(m);
			if(mvo!=null) {  //로그인 성공
				rttr.addFlashAttribute("msgType","성공메세지");
				rttr.addFlashAttribute("msg","로그인이 되었습니다.");
				session.setAttribute("loginuser", mvo);
				return "redirect:/";
				
			}else { //실패
				rttr.addFlashAttribute("msgType","실패메세지");
				rttr.addFlashAttribute("msg","다시 로그인을 해주세요.");
				return "redirect:/memLoginForm.do";
				
			}	
	}
	
	
	//회원정보수정화면
	@RequestMapping("/memUpdateForm.do")
	public String memUpdateForm() {
		
		
		return "member/memUpdateForm";
	}
}



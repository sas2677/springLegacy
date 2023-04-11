package kr.co.pro.Controller;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import kr.co.pro.Entity.AuthVO;
import kr.co.pro.Entity.Member;
import kr.co.pro.mapper.MemberMapper;


@Controller
public class MemberController {
	
	@Autowired
	MemberMapper membermapper;
	
	@Autowired
	PasswordEncoder pwEncoder;
	
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
		   m.getMemAge()== 0||m.getAuthList().size()==0	||
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
		//회원을 테이블에 저장하기(추가:비밀번호를 암호화(API))
		String encyptPw=pwEncoder.encode(m.getMemPassword());
		m.setMemPassword(encyptPw);
		//register() 수정
		int result=membermapper.register(m);
		if(result==1) { //회원가입 성공 메세지 
			
			//추가: 권한테이블에 회원의 권한을 저장하기 
			List<AuthVO> list= m.getAuthList();
			for(AuthVO authVO:list) {
				if(authVO.getAuth()!=null) {
					AuthVO saveVO=new AuthVO();
					saveVO.setMemID(m.getMemID());//회원ID
					saveVO.setAuth(authVO.getAuth());//회원권한 
					membermapper.authInsert(saveVO);
				}
			}
			rttr.addFlashAttribute("msgType", "성공 메세지");
			rttr.addFlashAttribute("msg", "회원가입을 축하합니다.");
			System.out.println(m.toString());
			//회원가입이 성공하면 =>로그인처리하기
		Member mvo= membermapper.getMember(m.getMemID());
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
	
	//회원정보 수정
	@RequestMapping("/memUpdate.do")
	public String memUpdate(Member m , RedirectAttributes rttr, 
							String memPassword, String memPassword2, HttpSession session) {
		
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
					return "redirect:/memUpdateForm.do"; //${msgType}, ${msg}
				}
				
			
				
				if(!memPassword.equals(memPassword2)) {
					rttr.addFlashAttribute("msgType", "실패 메세지");
					rttr.addFlashAttribute("msg", "비밀번호가 서로 다릅니다.");
					return "redirect:/memUpdateForm.do"; //${msgType}, ${msg}
					
				}
				
			
				//회원 수정하기
				int result=membermapper.memUpdate(m);
				if(result==1) { //회원 수정성공 메세지 
					rttr.addFlashAttribute("msgType", "성공 메세지");
					rttr.addFlashAttribute("msg", "회원정보가 수정되었습니다.");
					System.out.println(m.toString());
					//회원수정이 성공하면 =>로그인처리하기
					Member mvo = membermapper.getMember(m.getMemID());
					session.setAttribute("loginuser", mvo);//로그인
					return "redirect:/";
				}else {
					rttr.addFlashAttribute("msgType", "에러 메세지");
					rttr.addFlashAttribute("msg", "회원정보 수정이 실패하였습니다.");
					return "redirect:/memUpdateForm.do"; 
				}
	}
	
	//회원의 사진등록 화면
	@RequestMapping("/memImageForm.do")
	public String memImageForm() {
		
		return "member/memImageForm";
	}
	
	//회원사진 이미지 업로드(upload.DB저장)
	@RequestMapping("/memImageUpdate.do")
	public String memImageUpdate(HttpServletRequest request,HttpSession session, RedirectAttributes rttr) {
		//파일업로드 API(cos.jar,3가지)
		MultipartRequest multi=null;
		
		int fileMaxSize =10*1024*1024; //10MB
		String savePath=request.getRealPath("resources/upload");
		
		try {
			//이미지 업로드
			multi=new MultipartRequest(request, savePath, fileMaxSize,"UTF-8", new DefaultFileRenamePolicy());
			
		}catch (Exception e) {
			e.printStackTrace();
			rttr.addFlashAttribute("msgType", "에러 메세지");
			rttr.addFlashAttribute("msg", "파일의 크기는 10MB를 넘을 수 없습니다.");
			return "redirect:/memImageForm.do";
		
		}
		//데이터베이스 테이블에 회원이미지를 업데이트
		String memID=multi.getParameter("memID");
		String newProfile="";
		File file = multi.getFile("memProfile");
		System.out.println();
		if(file !=null) { //업로드가 된 상태(.png, .jpg, .gif)
			//이미지 파일 여부를 체크 => 이미지 파일이 아니면 실패
		String ext=file.getName().substring(file.getName().lastIndexOf(".")+1);
		ext=ext.toUpperCase(); //PNG, JPG, GIF	
		if(ext.equals("PNG")||ext.equals("JPG")||ext.equals("GIF")) {
			//새로 업로드 된 이미지가(new), 현재DB에 있는 이미지(old)
				String oldProfile=membermapper.getMember(memID).getMemProfile();
				File oldFile=new File(savePath+"/"+oldProfile);
				if(oldFile.exists()) {
					oldFile.delete();
				}
				newProfile=file.getName();
		}else { //이미지 파일이 아니면
			if(file.exists()) {
				file.delete();//삭제
			}
			rttr.addFlashAttribute("msgType", "실패 메세지");
			rttr.addFlashAttribute("msg", "이미지 파일만 업로드 가능합니다.");
			return "redirect:/memImageForm.do";
			}
		}
		//새로운 이미지를 테이블에 업데이트
		Member mvo = new Member();
		mvo.setMemID(memID);
		mvo.setMemProfile(newProfile);
		membermapper.memProfileUpdate(mvo);//이미지 업데이트 
		Member m=membermapper.getMember(memID);
		//세션을 새롭게 생성한다.
		session.setAttribute("loginuser", m);
		rttr.addFlashAttribute("msgType", "성공 메세지");
		rttr.addFlashAttribute("msg", "이미지가 변경되었습니다.");
		return "redirect:/";
	}
}



package kr.co.pro.mapper;



import org.apache.ibatis.annotations.Mapper;
import kr.co.pro.Entity.Member;


@Mapper //@mapper - mybatis API
public interface MemberMapper {
	//로그인 중복 체크
	public Member registerCheck(String memID);
	
	//회원가입 (1,0)
	public int register(Member m);
	
	//로그인
	public Member memLogin(Member mvo);
	
}

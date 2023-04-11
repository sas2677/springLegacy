package kr.co.pro.Entity;

import lombok.Data;

@Data
public class AuthVO {

	private int no;
	private String memID;
	private String auth; //회원권한(3가지=유저, 매니저, 관리자)
}

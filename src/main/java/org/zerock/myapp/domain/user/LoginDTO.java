package org.zerock.myapp.domain.user;

import lombok.Data;

@Data
public class LoginDTO {

	private String userid;
	private String userpw;
	
	//-- 로그인 화면 "자동 로그인" 체크여부
	private boolean autoLogin;
	
} // end class

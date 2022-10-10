package org.zerock.myapp.service.user;

import java.sql.Timestamp;

import org.zerock.myapp.domain.user.LoginDTO;
import org.zerock.myapp.domain.user.UserVO;

public interface UserService {

	// 1. 전송받은 사용자의 아이디와 암호로 (LoginDTO)로 사용자 정보를 획득
	public abstract UserVO findUser(LoginDTO dto) throws Exception;
	
	// 2. RememberMe 수정
	public boolean modifyUserWithRememberMe(String userid, String rememberme, Timestamp rememberage) throws Exception;
	
	// 3. User의 RememberMeCookie 얻기
	public UserVO findUserByRememberMeCookie(String rememberMeCookie) throws Exception;
} // end interface

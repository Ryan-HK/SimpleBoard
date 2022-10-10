package org.zerock.myapp.mapper;

import java.sql.Timestamp;

import org.apache.ibatis.annotations.Param;
import org.zerock.myapp.domain.user.LoginDTO;
import org.zerock.myapp.domain.user.UserVO;
import org.zerock.myapp.exception.DAOException;

public interface UserMapper {

	// 1. User정보를 얻어온다.
	public abstract UserVO selectUser(LoginDTO dto) throws DAOException;
	
	// 2. User에게 rememberme와 rememberage 업데이트
	public abstract int updateUserWithRememberMe(@Param("userid")String userid, @Param("rememberme")String rememberme, @Param("rememberage")Timestamp rememberage)
			throws DAOException;
	
	// 3. User의 RememberMe Key얻기
	public abstract UserVO selectUserByRememberMe(String rememberMe) throws DAOException;
	
} // end interface

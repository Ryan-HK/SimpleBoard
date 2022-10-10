package org.zerock.myapp.service.user;

import java.sql.Timestamp;

import org.springframework.stereotype.Service;
import org.zerock.myapp.domain.user.LoginDTO;
import org.zerock.myapp.domain.user.UserVO;
import org.zerock.myapp.exception.ServiceException;
import org.zerock.myapp.mapper.UserMapper;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@AllArgsConstructor
@Log4j2

@Service
public class UserServiceImpl implements UserService {

	
	private final UserMapper mapper;
	
	@Override
	public UserVO findUser(LoginDTO dto) throws ServiceException {
		log.trace("findUser({}) invoked.");
		
		try {
			
			return this.mapper.selectUser(dto);
			
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
		
		
	} // findUser
	
	@Override
	public boolean modifyUserWithRememberMe(String userid, String rememberme, Timestamp rememberage) throws Exception {
		return this.mapper.updateUserWithRememberMe(userid, rememberme, rememberage) == 1;
	} // findUser


	@Override
	public UserVO findUserByRememberMeCookie(String rememberMeCookie) throws Exception {
		
		return this.mapper.selectUserByRememberMe(rememberMeCookie);
		
	} // findUser


}

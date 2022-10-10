package org.zerock.myapp.interceptor.user;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;
import org.zerock.myapp.common.SharedScopeKeys;
import org.zerock.myapp.domain.user.UserVO;
import org.zerock.myapp.service.user.UserService;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@Component
public class AuthInterceptor implements HandlerInterceptor {
	
	@Setter(onMethod_= {@Autowired})
	private UserService service;
	
	// 1. HTTP request가 Controller의 Handler method로 위임되기 직전에 콜백
	
	// 컨트롤러의 핸들러 메소드로 요청이 위임되기 직전에, 기본적인 퀀한 체크("인증여부")를 수행
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
		log.trace("============================================");
		log.trace("1. preHandle(req, res, {}) invoked.", handler);
		log.trace("============================================");
		
		// Step.1 : Session Scope에 접근할 수 있는 HttpSession 객체 획득
		HttpSession session = req.getSession();
		log.info("\t+ 1. session Id : {}", session.getId());

		// Step.2 : Session Scope에 인증정보(즉, UserVO 객체)가 없으면 => 로그인 창으로 Re-direct하고,
		//														있으면 => 요청을 그대로 컨트롤러로 전달
		UserVO vo = (UserVO) session.getAttribute("USER_KEY");
		
		if(vo != null) {		// 인증된 사용자! (웹브라우저)
			log.info("\t+ 2. Authentication Found.");
			return true;
		} else {				// 인증안된 사용자! (웹브라우저)
			// 자동로그인 처리
			// 필요처리 2가지 :
			// (1) 자동로그인 쿠키가 들어온 웹브라우저라면, 이 쿠키값으로 tbl_user 테이블을 조회하고,
			//	그 결과 특정 user 정보가 획득되고, 이 정보로 인증객체인 UserVO객체를 생성해서, SEssion Scope에 바인딩 시킴
			// (2) (1)에 의해서, Session Scope에 UserVO가 있다면, 무사통과 시켜줘야 됨
			
			// Step1 : 현재 요청을 보내온 웹브라우저의 요청메시지의 헤더(Cookie)에, 자동로그인 쿠키를 획득
			Cookie rememberMeCookie = WebUtils.getCookie(req, SharedScopeKeys.REMEMBERME_KEY);
			
			if(rememberMeCookie != null) {		// 자동로그인 처리 대상 웹브라우저가 된다! => 인증정보(UserVO객체)를 복구
				// Step.2 : Step.1에서 얻어낸 자동로그인 쿠키값으로, 인증객체인 UserVO객체를 복구하고, 이를 Session Scope에 바인딩 => 인증 복구
				String cookieName = rememberMeCookie.getName();
				String cookieValue = rememberMeCookie.getValue();
				log.info("\t+ 2. cookieName : {}, cookieValue = {}", cookieName, cookieValue);
				
				// Step.3 : 웹브라우저가 보내온 자동로그인 쿠키값으로 인증정보 객체 UserVO 객체 획득
				UserVO repairedUserVO = this.service.findUserByRememberMeCookie(cookieValue);
				
				// Step.4 : 현재의 웹브라우저에 할당 된 Session Scope에 인증정보 객체 (UserVO)를 바인딩 => 인증복구
				session.setAttribute("USER_KEY", repairedUserVO);
				
				if(repairedUserVO != null) return true;
			}
			
			log.info("\t+ 2. No Authentication Found");
			
			// 자동로그인 옵션이 켜져있는 웹브라우저라면, 로그인 창으로 밀면(Re-direct)하면 안됨.
			res.sendRedirect("/board/list");
			
			return false;		// 컨트롤러로 위임하지 않음
		} // if-else

	} // preHandle

	// 2. Controller의 Handler method 가 수행완료된 직후에 콜백
	// 		(*주의*) 단, Controller's handler method에서 예외가 발생하면, 아래의 메소드는 호출되지 못함
	@Override
	public void postHandle(HttpServletRequest req, HttpServletResponse res, Object handler,
			ModelAndView modelAndView) throws Exception {
		log.trace("============================================");
		log.trace("2. postHandle(req, res, {}, {}) invoked.", handler, modelAndView);
		log.trace("============================================");

		
	} // postHandle
	

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
		
	}
	

} // end class

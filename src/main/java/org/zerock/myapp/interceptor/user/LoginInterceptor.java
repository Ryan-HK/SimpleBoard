package org.zerock.myapp.interceptor.user;

import java.sql.Timestamp;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.zerock.myapp.common.SharedScopeKeys;
import org.zerock.myapp.domain.user.UserVO;
import org.zerock.myapp.service.user.UserService;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@Component
public class LoginInterceptor implements HandlerInterceptor {
	
	@Setter(onMethod_={@Autowired})
	private UserService service;
	
	
	// 1. HTTP Request Controller의 Handler method로 위임되기 직전 콜백
	// Session Scope에 저장되어 있는 모든 정보 파괴 수행
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler)
			throws Exception {
		log.trace("1. preHandle(req, res, {}", handler);
		
		// Step.1 : Session Scope에 접근할 수 있는 HttpSession 객체를 획득
		HttpSession session = req.getSession();
		
		// Step.2 : Session Scope에서 UserVO 인증객체를 파괴!
		session.removeAttribute("USER_KEY");

		// Controller의 Handler method로 요청이 가도록 설정
		return true;
	} // preHandle

	
	// 2. Controller의 Handler method가 수행 된 이후, 콜백되는 메소드
	// (*주의*) 단, Controller's handler method에서 예외발생되면, 아래 메소드는 실행되지 않는다.
	@Override
	public void postHandle(HttpServletRequest req, HttpServletResponse res, Object handler,
			ModelAndView modelAndView) throws Exception {
		log.trace("2. postHandle(req, res, {}", handler, modelAndView);
		
		// Step.1 : Session Scope에 접근할 수 있는 HttpSession 객체를 획득
		HttpSession session = req.getSession();
		
		// Step.2 : 매개변수 modelAndView에서 UserVO가 들어있는 지, 확인
		ModelMap model = modelAndView.getModelMap();
		UserVO vo = (UserVO) model.get("USER_KEY");
		log.info("vo : {}", vo);
		
		// 로그인 성공 시.....
		if(vo != null) {
			
			// (1) 세션영역에 인증객체인 UserVO를 바인딩 처리
			session.setAttribute("USER_KEY", vo);
			log.info("\t+ Authentication Succed. USER : {}", vo);
			
			// (2) 자동로그인 옵션체크
			boolean isRememberMe = checkRememberMeOption(req);
			
			if(isRememberMe) {
				String sessionId = req.getSession().getId();
				Cookie rememberMeCookie = new Cookie(SharedScopeKeys.REMEMBERME_KEY, sessionId);
				
				final int maxAge = 1 * 60 * 60 * 24 * 7;
				rememberMeCookie.setMaxAge(maxAge); // 쿠키의 만료시간 설정
				rememberMeCookie.setPath("/");
				
				// (3) (2)에서 생성한 자동로그인 쿠기를 응답메시지의 헤더로 저장
				res.addCookie(rememberMeCookie);
				
				// (4) 자동 로그인 쿠키의 값 + 만료일자 2개의 값을 tbl_user 테이블에 저장
				long now = System.currentTimeMillis();
				long expiry = now + (maxAge * 1000L);
				
				Timestamp expiryTs = new Timestamp(expiry);
				
				boolean isModifiedWithRememberMe = this.service.modifyUserWithRememberMe(vo.getUserid(), sessionId, expiryTs);
				log.info("\t+ isModified : {}", isModifiedWithRememberMe);
				
			} // if : 자동로그인 옵션이 on
			
		} // if : 로그인 성공 시...
		
	} // postHandle

	

	
	
	// 자동 로그인 옵션이 on/off되어있는지 체크해서 반환
	private boolean checkRememberMeOption(HttpServletRequest req) {
		log.trace("checkRememberMeOption(req) invoked.");
		
		String rememberMe = req.getParameter("rememberMe");
		
		return rememberMe != null;
	}
	
	@Override
	public void afterCompletion(HttpServletRequest req, HttpServletResponse res, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
				HandlerInterceptor.super.afterCompletion(req, res, handler, ex);
	} // afterCompletion
	

	
} // end class

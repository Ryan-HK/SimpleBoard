package org.zerock.myapp.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.myapp.domain.user.LoginDTO;
import org.zerock.myapp.domain.user.UserVO;
import org.zerock.myapp.exception.ControllerException;
import org.zerock.myapp.service.user.UserService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@AllArgsConstructor

@RequestMapping("/user")
@Controller
public class LoginController {

	private final UserService service;
	
	@PostMapping("/loginPost")
	public String loginPost(LoginDTO dto,Model model, RedirectAttributes rttrs)
			throws ControllerException {
		log.trace("loginPost({}) invoked.", dto);
		
		try {
			UserVO vo = this.service.findUser(dto);
			
			// 로그인 성공시....
			if(vo != null) {
				log.trace("\t+ 로그인성공");
				
				rttrs.addFlashAttribute("LOGIN_RESULT", "Login Success");
				model.addAttribute("USER_KEY", vo);
				
				// 로그인 성공 시, 게시판으로 이동
				return "/board/list";
			} 
			
			// 로그인 실패시....
			else {
				log.trace("\t+ 로그인실패");
				
				rttrs.addFlashAttribute("LOGIN_RESULT", "Login Failed");
				
				return "/board/list";
			} // if-else
			
		} catch(Exception e) {
			throw new ControllerException(e);
		}
		
	} // loginPost
	
	
} // end class

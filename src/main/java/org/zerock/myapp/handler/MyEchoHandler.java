package org.zerock.myapp.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lombok.extern.log4j.Log4j2;


@Log4j2
//@Component
public class MyEchoHandler extends TextWebSocketHandler {
	List<WebSocketSession> sessions = new ArrayList<>();
	
	
	// Client가 Server에 Connection이 연결되었을 때,
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		log.trace("afterConnectionEstablished({}) invoked.", session);
		
		// 접속되는 모든 Session정보를 ArrayList에 저장
		sessions.add(session);
		
		
	}

	// Client가 어떤 "메시지"를 발송했을 때
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		log.trace("handleTextMessage({}, {}) invoked.", session, message);
		
//		String senderId = session.getId();
		Map<String, Object> map = session.getAttributes();
		String userid = (String)map.get("user_id");
		
		for(WebSocketSession sess : sessions) {
			sess.sendMessage(new TextMessage(userid + " : " + message.getPayload()));
		} // enhanced for
		
	} // handleTextMessage
	
	

	// Client가 Server에서 DisConnection할때
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		log.trace("afterConnectionClosed({},{}) invoked.", session, status);
	}
	

} // end class













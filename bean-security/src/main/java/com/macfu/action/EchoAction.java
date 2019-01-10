package com.macfu.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/pages/message/*")
public class EchoAction {
	@RequestMapping("/show")
	public ModelAndView echo(String msg) {
		ModelAndView mav = new ModelAndView("message/message_show") ;
		mav.addObject("echoMessage", "【ECHO】msg = " + msg) ;
		return mav ;
	}
	@RequestMapping("/input")
	public String input() {
		return "message/message_input" ; 
	}
}


